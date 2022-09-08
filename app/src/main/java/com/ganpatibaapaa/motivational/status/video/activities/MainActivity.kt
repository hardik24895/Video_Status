package com.ganpatibaapaa.motivational.status.video.activities

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applovin.mediation.ads.MaxAdView
import com.ganpatibaapaa.motivational.status.video.R
import com.ganpatibaapaa.motivational.status.video.adapters.TitleAdapter
import com.ganpatibaapaa.motivational.status.video.api.ApiInterface
import com.ganpatibaapaa.motivational.status.video.model.Video
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var list: ArrayList<String>

    private var adView: MaxAdView? = null

    private fun createBannerAd()
    {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createBannerAd()
        val progressBar = findViewById<ProgressBar>(R.id.mainActivityProgressBar)
        progressBar.visibility = View.VISIBLE

        list = ArrayList()

        val viewAll = findViewById<TextView>(R.id.viewAllTextView)
        viewAll.setOnClickListener {
            startActivity(Intent(this, ViewAllActivity::class.java))
        }

        val settingView = findViewById<ImageView>(R.id.mainActivitySettingImageView)
        settingView.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        val favoriteImageView = findViewById<ImageView>(R.id.mainActivityFavoriteImageView)
        favoriteImageView.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        val authHeader =
            "Basic " + Base64.encodeToString("Ridham:Ridham".toByteArray(), Base64.NO_WRAP)

        val apiInterface = ApiInterface.create().randomVideoList(perPage = 6, user = authHeader)
        apiInterface.enqueue(object : Callback<ArrayList<Video>> {
            override fun onFailure(call: Call<ArrayList<Video>>, t: Throwable) {
                t.message?.let { Log.e("MainActivityResError", it) }
                progressBar.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<ArrayList<Video>>,
                response: Response<ArrayList<Video>>
            ) {
                if (response.isSuccessful) {

                    response.body()?.forEach {
                        list.add(it.ThumbURL)
                    }
                    val titleAdapter = TitleAdapter(list)
                    val titleRecyclerView = findViewById<RecyclerView>(R.id.titleRecyclerView)
                    val gridLayoutManager = GridLayoutManager(applicationContext, 3)
                    gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    titleRecyclerView.layoutManager = gridLayoutManager
                    titleRecyclerView.adapter = titleAdapter
                    progressBar.visibility = View.GONE

                } else {
                    progressBar.visibility = View.GONE
                }
            }
        })
    }
}