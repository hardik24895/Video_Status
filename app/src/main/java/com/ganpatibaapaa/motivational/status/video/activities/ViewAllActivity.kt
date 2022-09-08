package com.ganpatibaapaa.motivational.status.video.activities

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applovin.mediation.ads.MaxAdView
import com.ganpatibaapaa.motivational.status.video.R
import com.ganpatibaapaa.motivational.status.video.adapters.ViewAllAdapter
import com.ganpatibaapaa.motivational.status.video.api.ApiInterface
import com.ganpatibaapaa.motivational.status.video.model.Video
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewAllActivity : AppCompatActivity() {

    lateinit var list: ArrayList<Video>
    var page = 0
    private var adView: MaxAdView? = null

    private fun createBannerAd() {
//        adView = MaxAdView("", this)

        adView = MaxAdView("20c1a54031720f98", this)
//        adView?.setListener(this)

        // Stretch to the width of the screen for banners to be fully functional
        val width = ViewGroup.LayoutParams.MATCH_PARENT

        // Banner height on phones and tablets is 50 and 90, respectively
        val heightPx = resources.getDimensionPixelSize(R.dimen.banner_height)

        adView?.layoutParams = FrameLayout.LayoutParams(width, heightPx)

        // Set background or background color for banners to be fully functional
        adView?.setBackgroundColor(android.R.color.black)

        val rootView = findViewById<ViewGroup>(R.id.bannerContainer)
        rootView.addView(adView)

        // Load the ad
        adView?.loadAd()
    }

    //    private var mInterstitialAd: InterstitialAd? = null
    var TAG = "ViewAllActivity"
    private lateinit var activity: Activity
//    lateinit var bannerAdHelper: BannerAdHelper
//    private lateinit var appLovinFullScreen: FullscreenAdHelper


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)

        createBannerAd()
        activity = this
        val image_view = findViewById<ImageView>(R.id.viewAllBackButtonImageView)
// set on-click listener for ImageView
        image_view.setOnClickListener{
            // your code here
            startActivity(Intent(this, MainActivity::class.java))

        }
//        var btn_submit = findViewById(R.id.favBackButtonImageView) as ImageView
//
//        btn_submit.setOnClickListener{
//            intent = Intent(applicationContext, MainActivity::class.java)
//            startActivity(intent)
//        }
//        appLovinFullScreen = FullscreenAdHelper(this)
//
//        bannerAdHelper = BannerAdHelper(activity)
        val progressBar = findViewById<ProgressBar>(R.id.viewAllActivityProgressBar)
        progressBar.visibility = View.VISIBLE

        list = ArrayList()
        val titleRecyclerView = findViewById<RecyclerView>(R.id.viewAllRecyclerView)
        val gridLayoutManager = GridLayoutManager(applicationContext, 3)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        var titleAdapter = ViewAllAdapter(list)
        titleRecyclerView.adapter = titleAdapter

        val authHeader =
            "Basic " + Base64.encodeToString("Ridham:Ridham".toByteArray(), Base64.NO_WRAP)

//        MobileAds.initialize(this) {}
//
//        val adRequest = AdRequest.Builder().build()

//        InterstitialAd.load(this,resources.getString(R.string.admob_interstitial), adRequest, object : InterstitialAdLoadCallback() {
//            override fun onAdFailedToLoad(adError: LoadAdError) {
//                Log.d(TAG, adError.message)
//                mInterstitialAd = null
////                progressBar.visibility = View.GONE
//                appLovinFullScreen.loadInterstitialAds(object : FullscreenListener {
//                    override fun isDismissed() {
//                        progressBar.visibility = View.GONE
//
//
//                    }
//
//                    override fun isFailed() {
//                        progressBar.visibility = View.GONE
//                        val apiInterface = ApiInterface.create().randomVideoList(perPage = 30, user = authHeader)
//                        apiInterface.enqueue(object : Callback<ArrayList<Video>> {
//                            override fun onFailure(call: Call<ArrayList<Video>>, t: Throwable) {
//                                t.message?.let { Log.e("ViewAllResError", it) }
//                                progressBar.visibility = View.GONE
//                            }
//
//                            override fun onResponse(
//                                call: Call<ArrayList<Video>>,
//                                response: Response<ArrayList<Video>>
//                            ) {
//                                if (response.isSuccessful) {
//                                    page += 30
//                                    response.body()?.forEach {
//                                        list.add(it)
//                                    }
//                                    titleAdapter = ViewAllAdapter(list)
//                                    titleRecyclerView.adapter = titleAdapter
//                                    progressBar.visibility = View.GONE
//                                } else {
//                                    progressBar.visibility = View.GONE
//                                }
//                            }
//                        })
//                    }
//
//                    override fun isShown() {
//
//                    }
//                })
//
//
//            }
//
//            override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                Log.d(TAG, "Ad was loaded.")
//                mInterstitialAd = interstitialAd
//                mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
//                    override fun onAdDismissedFullScreenContent() {
//                        Log.d(TAG, "Ad was dismissed.")
//                    }
//
//                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
//                        Log.d(TAG, "Ad failed to show.")
//                        progressBar.visibility = View.GONE
//                    }
//
//                    override fun onAdShowedFullScreenContent() {
//                        Log.d(TAG, "Ad showed fullscreen content.")
//                        mInterstitialAd = null
//                    }
//                }
//
//                if (mInterstitialAd != null) {
//                    progressBar.visibility = View.GONE
//                    mInterstitialAd?.show(parent)
//                    progressBar.visibility = View.VISIBLE

        val apiInterface = ApiInterface.create().randomVideoList(perPage = 30, user = authHeader)
        apiInterface.enqueue(object : Callback<ArrayList<Video>> {
            override fun onFailure(call: Call<ArrayList<Video>>, t: Throwable) {
                t.message?.let { Log.e("ViewAllResError", it) }
                progressBar.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<ArrayList<Video>>,
                response: Response<ArrayList<Video>>
            ) {
                if (response.isSuccessful) {
                    page += 30
                    response.body()?.forEach {
                        list.add(it)
                    }
                    titleAdapter = ViewAllAdapter(list)
                    titleRecyclerView.adapter = titleAdapter
                    progressBar.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        })

//                    val adRequest = AdRequest.Builder().build()
//                    val adViewLayout = findViewById<LinearLayout>(R.id.viewAllBannerAdView)
//                    val adview = AdView(activity)
//                    adview.adSize = AdSize.BANNER
//                    adview.adUnitId = getString(R.string.admob_banner)
//                    adview.loadAd(adRequest)
//                    adViewLayout.addView(adview)
//                    adview.adListener = object : AdListener() {
//                        override fun onAdFailedToLoad(p0: LoadAdError) {
//                            super.onAdFailedToLoad(p0)
//                            bannerAdHelper.loadBannerAds(adViewLayout)
//                        }
//                    }


//                } else {
//                    Log.d("TAG", "The interstitial ad wasn't ready yet.")
//                }
//            }
//        })
//
//        val backButton = findViewById<ImageView>(R.id.viewAllBackButtonImageView)
//        backButton.setOnClickListener {
//            finish()
//        }
//

        titleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == (list.size - 1)) {
                    val apiInterface = ApiInterface.create()
                        .randomVideoList(perPage = 30, user = authHeader, page = page)
                    apiInterface.enqueue(object : Callback<ArrayList<Video>> {

                        override fun onFailure(call: Call<ArrayList<Video>>, t: Throwable) {
                            t.message?.let { Log.e("ViewAllResError", it) }
                        }

                        override fun onResponse(
                            call: Call<ArrayList<Video>>,
                            response: Response<ArrayList<Video>>
                        ) {
                            if (response.isSuccessful) {
                                page += 30
                                titleAdapter.addItems(response.body()!!)
                            }
                        }
                    })
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
        titleRecyclerView.layoutManager = gridLayoutManager

    }
}