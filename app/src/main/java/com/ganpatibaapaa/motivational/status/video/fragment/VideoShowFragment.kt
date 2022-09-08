package com.GanpatiBappiMoriya.motivational.status.video.fragment

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.ganpatibaapaa.motivational.status.video.R
import com.ganpatibaapaa.motivational.status.video.dbhelper.DBHelper
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.math.pow


private const val ARG_PARAM1 = "position"
private const val ARG_PARAM2 = "url"
private const val ARG_PARAM3 = "thumb_url"
private const val ARG_PARAM4 = "is_shown"

class VideoShowFragment : Fragment(), MaxAdListener {
    private var position: Int? = null
    private var url: String? = null
    private var thumb_url: String? = null
    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 10000
    private var isShown: Boolean? = false
    private lateinit var videoShowFragmentVideoView: VideoView
    private lateinit var downloadImageView: ImageView
    private var downloadID: Long = 0
    lateinit var db: DBHelper
    var isFav: Boolean = false
    lateinit var progressBar: ProgressBar

//    private lateinit var appLovinFullScreen: FullscreenAdHelper

    //    private var mInterstitialAd: InterstitialAd? = null
    var TAG = "VideoShowFragment"

    private var adView: MaxAdView? = null

    private lateinit var interstitialAd: MaxInterstitialAd
    private var retryAttempt = 0.0

    private var buttonClickPosition: Int = 0
    private  lateinit var favImageView: ImageView

    private fun createBannerAd(view: View) {
//        adView = MaxAdView("", this)

        adView = MaxAdView("58539388829deec1", requireActivity())
//        adView?.setListener(this)

        // Stretch to the width of the screen for banners to be fully functional
        val width = ViewGroup.LayoutParams.MATCH_PARENT

        // Banner height on phones and tablets is 50 and 90, respectively
        val heightPx = resources.getDimensionPixelSize(R.dimen.banner_height)

        adView?.layoutParams = FrameLayout.LayoutParams(width, heightPx)

        // Set background or background color for banners to be fully functional
        adView?.setBackgroundColor(android.R.color.black)

        val rootView = view.findViewById<ViewGroup>(R.id.bannerContainer)
        rootView.addView(adView)

        // Load the ad
        adView?.loadAd()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getString(ARG_PARAM1)?.toInt()
            url = it.getString(ARG_PARAM2)
            thumb_url = it.getString(ARG_PARAM3)
            isShown = it.getBoolean(ARG_PARAM4)
        }
        db = DBHelper(requireActivity(), null)
//        appLovinFullScreen = FullscreenAdHelper(requireActivity())

        if (position == 20) {
            val reviewManager = ReviewManagerFactory.create(requireActivity())
            var reviewInfo: ReviewInfo? = null
            val requestFlow = reviewManager.requestReviewFlow()
            requestFlow.addOnCompleteListener { request ->
                if (request.isSuccessful) {
                    reviewInfo = request.result
                    reviewInfo?.let {
                        val flow = reviewManager.launchReviewFlow(requireActivity(), it)
                        flow.addOnCompleteListener {

                        }
                    }
                } else {
                    reviewInfo = null
                    reviewInfo?.let {
                        val flow = reviewManager.launchReviewFlow(requireActivity(), it)
                        flow.addOnCompleteListener {
                        }
                    }
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createBannerAd(view)

        progressBar = view.findViewById(R.id.videoShowFragmentProgressBar)
        progressBar.visibility = View.VISIBLE
        videoShowFragmentVideoView = view.findViewById(R.id.videoShowFragmentVideoView)
        videoShowFragmentVideoView.setVideoPath(url)
        videoShowFragmentVideoView.requestFocus()
        if (isShown!!) {
            videoShowFragmentVideoView.setOnPreparedListener {
                it.start()
                it.pause()
            }
        }
        progressBar.visibility = View.GONE

        downloadImageView = view.findViewById(R.id.downloadImageView)
        downloadImageView.setOnClickListener {
            onDownloadClick()
            showInterstitialAd(1)
        }

        favImageView = view.findViewById<ImageView>(R.id.saveStatusImageView)
        val mainHandlers = Handler(Looper.getMainLooper())

        mainHandlers.post(object : Runnable {
            override fun run() {
                createInterstitialAd()

                showInterstitialAd(0);
                mainHandlers.postDelayed(this, 30000)
            }
        })
        val cur = db.checkFavStatus(url!!)
        if (cur?.count != 0) {
            favImageView.setImageResource(R.drawable.baseline_favorite_white_36)
            isFav = true
        }
        val mainHandler = Handler(Looper.getMainLooper())

//        mainHandler.post(object : Runnable {
//            override fun run() {
//                if (interstitialAd.isReady) {
//                    interstitialAd.showAd()
//                }
//                mainHandler.postDelayed(this, 100000)
//            }
//        })
        favImageView.setOnClickListener {
            onFavoriteClick()
            showInterstitialAd(2)
        }

        val shareVideoImageView = view.findViewById<ImageView>(R.id.shareImageView)
        shareVideoImageView.setOnClickListener {
            onShareVideoClick()
            showInterstitialAd(3)
        }

        val whatsappImageView = view.findViewById<ImageView>(R.id.whatsAppImageView)
        whatsappImageView.setOnClickListener {
            onWhatsappShareClick()
            showInterstitialAd(4)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun onDownloadClick() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                Array(1) { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                0
            )
        } else {
            statusDownload()
        }
    }

    private fun onWhatsappShareClick() {
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Ganpati Bappa HD Status Video")
            .setTitle("Ganpati Bappa Video Status Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setAllowedOverMetered(true)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$ts.mp4")

        val dm = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadID = dm.enqueue(request)

        val br = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 2)
                if (id == downloadID) {
                    val file = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .toString() + "/$ts.mp4"
                    )

                    val videoUri = FileProvider.getUriForFile(
                        requireActivity(),
                        "com.krishna.motivational.status.video.provider",
                        file
                    )
                    val sendIntent = Intent()
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.putExtra(Intent.EXTRA_STREAM, videoUri)
                    sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    sendIntent.type = "*/*"
                    sendIntent.setPackage("com.whatsapp")
                    startActivity(sendIntent)

                }
            }

        }
        activity?.registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun onShareVideoClick() {
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Ganpati Bappa Video Status Status")
            .setTitle("Ganpati Bappa Video Status Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setAllowedOverMetered(true)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$ts.mp4")

        val dm = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadID = dm.enqueue(request)

        val br = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 1)
                if (id == downloadID) {
                    val file = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .toString() + "/$ts.mp4"
                    )

                    val videoUri = FileProvider.getUriForFile(
                        requireActivity(),
                        "com.krishna.motivational.status.video.provider",
                        file
                    )
                    val sendIntent = Intent()
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.putExtra(Intent.EXTRA_STREAM, videoUri)
                    sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    sendIntent.type = "*/*"
                    startActivity(Intent.createChooser(sendIntent, "Share Status"))
                }
            }

        }
        activity?.registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun onFavoriteClick() {
        if (isFav) {
            db.deleteFavStatus(url!!)
            favImageView.setImageResource(R.drawable.baseline_favorite_border_white_36)
            isFav = false
        } else {
            db.addFavStatus(url!!, thumb_url!!)
            favImageView.setImageResource(R.drawable.baseline_favorite_white_36)
            isFav = true
        }
    }

    private fun showInterstitialAd(buttonPosition: Int) {
        buttonClickPosition = buttonPosition;

        if (interstitialAd.isReady) {
            interstitialAd.showAd()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        statusDownload()
    }

    private fun statusDownload() {
        Toast.makeText(activity, "Downloading Has Started", Toast.LENGTH_LONG)
            .show()
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Ganpati Bappa Video Status Video")
            .setTitle("Ganpati Bappa Video Status Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setAllowedOverMetered(true)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$ts.mp4")

        val dm = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadID = dm.enqueue(request)

        val br = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == downloadID) {
                    Toast.makeText(activity, "Status Downloaded Successfully.", Toast.LENGTH_LONG)
                        .show()
                }
            }

        }
        activity?.registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onResume() {
        createInterstitialAd()

//        if (!isShown!!) {
//            if (mInterstitialAd != null) {
//                mInterstitialAd?.show(requireActivity())
//            } else {
//                Log.d("TAG", "The interstitial ad wasn't ready yet.")
//            }
//        } else {
        videoShowFragmentVideoView.start()
        videoShowFragmentVideoView.setOnCompletionListener {
            it.start()
        }
//        }

        super.onResume()
    }

    override fun onPause() {
        videoShowFragmentVideoView.pause()
        super.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video_show, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int, URL: String, ThumbURL: String, isShown: Boolean) =
            VideoShowFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, position.toString())
                    putString(ARG_PARAM2, URL)
                    putString(ARG_PARAM3, ThumbURL)
                    putBoolean(ARG_PARAM4, isShown)
                }
            }
    }

    private fun createInterstitialAd() {
        interstitialAd = MaxInterstitialAd("6512a03dbc4c5c50", requireActivity())
        interstitialAd.setListener(this)

//
//        interstitialAd.setListener(object : MaxAdListener {
//            override fun onAdLoaded(ad: MaxAd?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onAdDisplayed(ad: MaxAd) {
//            }
//
//            override fun onAdHidden(ad: MaxAd?) {
//            }
//
//            override fun onAdClicked(ad: MaxAd?) {
//            }
//
//            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
//            }
//
//            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
//            }
//        })

        // Load the first ad
        interstitialAd.loadAd()
    }

    // MAX Ad Listener
    override fun onAdLoaded(maxAd: MaxAd) {
        // Interstitial ad is ready to be shown. interstitialAd.isReady() will now return 'true'

        // Reset retry attempt
        retryAttempt = 0.0
    }

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
        // Interstitial ad failed to load
        // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)

        performPendingAction()

        retryAttempt++
        val delayMillis =
            TimeUnit.SECONDS.toMillis(2.0.pow(6.0.coerceAtMost(retryAttempt)).toLong())

        Handler(Looper.getMainLooper()).postDelayed({
            interstitialAd.loadAd()
        }, delayMillis)
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {

        performPendingAction()

        // Interstitial ad failed to display. AppLovin recommends that you load the next ad.
        interstitialAd.loadAd()
    }

    override fun onAdDisplayed(maxAd: MaxAd) {
        Log.e(TAG, "onAdDisplayed: ")
    }

    override fun onAdClicked(maxAd: MaxAd) {
        Log.e(TAG, "onAdClicked: ")
    }

    override fun onAdHidden(maxAd: MaxAd) {

        performPendingAction()

        // Interstitial ad is hidden. Pre-load the next ad
        interstitialAd.loadAd()
    }

    private fun performPendingAction() {
        when (buttonClickPosition) {
            0 -> nothing()
            1 -> onDownloadClick()
            2 -> onFavoriteClick()
            3 -> onShareVideoClick()
            4 -> onWhatsappShareClick()
        }
    }

    private fun nothing() {
        TODO("Not yet implemented")
    }
}