package com.ganpatibaapaa.motivational.status.video.activities

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.ganpatibaapaa.motivational.status.video.R
import com.ganpatibaapaa.motivational.status.video.adapters.VideoShowFragmentAdapter
import com.ganpatibaapaa.motivational.status.video.api.ApiInterface
import com.ganpatibaapaa.motivational.status.video.model.Video
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoShowActivity : AppCompatActivity() {


    private lateinit var videoShowViewPager: ViewPager2
//    private var mInterstitialAd: InterstitialAd? = null
//    var TAG = "VideoShowActivity"
//    private lateinit var appLovinFullScreen: FullscreenAdHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_show)
//        appLovinFullScreen = FullscreenAdHelper(this)


        val progressBar = findViewById<ProgressBar>(R.id.videoShowActivityProgressBar)
        progressBar.visibility = View.VISIBLE
        videoShowViewPager = findViewById(R.id.videoShowViewPager)

        val videoListUrl = intent.getSerializableExtra("List") as ArrayList<Video>
        val pos = intent.getIntExtra("Pos", 0)
        var loadedVideo = intent.getIntExtra("LoadedVideo", 30)

        videoShowViewPager.offscreenPageLimit = 3

//        MobileAds.initialize(this) {}

//        val adRequest = AdRequest.Builder().build()

        val authHeader =
            "Basic " + Base64.encodeToString("Ridham:Ridham".toByteArray(), Base64.NO_WRAP)

//        InterstitialAd.load(this,resources.getString(R.string.admob_interstitial), adRequest, object : InterstitialAdLoadCallback() {
//            override fun onAdFailedToLoad(adError: LoadAdError) {
//                Log.d(TAG, adError.message)
//                mInterstitialAd = null
//
//
//
//
//                appLovinFullScreen.loadInterstitialAds(object : FullscreenListener {
//                    override fun isDismissed() {
//                        videoShowViewPager.post {
//                            videoShowViewPager.setCurrentItem(pos, false)
//                        }
//                    }
//
//                    override fun isFailed() {
//                        progressBar.visibility = View.GONE
//
//                        val adapter =
//                            VideoShowFragmentAdapter(this@VideoShowActivity, videoListUrl)
//                        videoShowViewPager.adapter = adapter
//                        videoShowViewPager.post {
//                            videoShowViewPager.setCurrentItem(pos, false)
//                        }
//                    }
//
//                    override fun isShown() {
//                        progressBar.visibility = View.GONE
//                        mInterstitialAd = null
        val adapter =
            VideoShowFragmentAdapter(this@VideoShowActivity, videoListUrl)
        videoShowViewPager.adapter = adapter
        videoShowViewPager.post {
            progressBar.visibility = View.GONE
            videoShowViewPager.setCurrentItem(pos, false)
        }
        videoShowViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (loadedVideo == position + 1) {
                    val apiInterface = ApiInterface.create()
                        .randomVideoList(
                            perPage = 30,
                            user = authHeader,
                            page = loadedVideo
                        )
                    apiInterface.enqueue(object :
                        Callback<ArrayList<Video>> {
                        override fun onFailure(
                            call: Call<ArrayList<Video>>,
                            t: Throwable
                        ) {
                            t.message?.let { Log.e("ViewAllResError", it) }
                        }

                        override fun onResponse(
                            call: Call<ArrayList<Video>>,
                            response: Response<ArrayList<Video>>
                        ) {
                            if (response.isSuccessful) {
                                loadedVideo += 30
                                adapter.addItems(response.body()!!)
                            }
                        }
                    })
                }
            }
        })
//                    }
//                })
//
//            }
//
//            override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                Log.d(TAG, "Ad was loaded.")
//                mInterstitialAd = interstitialAd
//                mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
//                    override fun onAdDismissedFullScreenContent() {
//                        Log.d(TAG, "Ad was dismissed.")
//                        videoShowViewPager.post {
//                            videoShowViewPager.setCurrentItem(pos, false)
//                        }
//                    }
//
//                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
//                        Log.d(TAG, "Ad failed to show.")
//                        progressBar.visibility = View.GONE
//                        videoShowViewPager.post {
//                            videoShowViewPager.setCurrentItem(pos, false)
//                        }
//                    }
//
//                    override fun onAdShowedFullScreenContent() {
//                        Log.d(TAG, "Ad showed fullscreen content.")
//                        progressBar.visibility = View.GONE
//                        mInterstitialAd = null
//                        val adapter = VideoShowFragmentAdapter(this@VideoShowActivity, videoListUrl)
//                        videoShowViewPager.adapter = adapter
//                        videoShowViewPager.post {
//                            videoShowViewPager.setCurrentItem(pos, false)
//                        }
//                        videoShowViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
//                            override fun onPageSelected(position: Int) {
//                                super.onPageSelected(position)
//
//                                if (loadedVideo == position+1) {
//                                    val apiInterface = ApiInterface.create()
//                                        .randomVideoList(
//                                            perPage = 30,
//                                            user = authHeader,
//                                            page = loadedVideo
//                                        )
//                                    apiInterface.enqueue(object : Callback<ArrayList<Video>> {
//                                        override fun onFailure(
//                                            call: Call<ArrayList<Video>>,
//                                            t: Throwable
//                                        ) {
//                                            t.message?.let { Log.e("ViewAllResError", it) }
//                                        }
//
//                                        override fun onResponse(
//                                            call: Call<ArrayList<Video>>,
//                                            response: Response<ArrayList<Video>>
//                                        ) {
//                                            if (response.isSuccessful) {
//                                                loadedVideo += 30
//                                                adapter.addItems(response.body()!!)
//                                            }
//                                        }
//                                    })
//                                }
//                            }
//                        })
//
//                    }
//                }
//
//                if (mInterstitialAd != null) {
//                    progressBar.visibility = View.GONE
//                    mInterstitialAd?.show(parent)
//                } else {
//                    Log.d("TAG", "The interstitial ad wasn't ready yet.")
//                }
//            }
//        })


    }
}