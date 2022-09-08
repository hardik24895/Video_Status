package com.ganpatibaapaa.motivational.status.video.activities

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applovin.mediation.ads.MaxAdView
import com.ganpatibaapaa.motivational.status.video.R
import com.ganpatibaapaa.motivational.status.video.adapters.FavoriteStatusAdapter
import com.ganpatibaapaa.motivational.status.video.dbhelper.DBHelper
import com.ganpatibaapaa.motivational.status.video.model.Video

class FavoriteActivity : AppCompatActivity() {

    lateinit var list: ArrayList<Video>

    //    lateinit var bannerAdHelper: BannerAdHelper
    private var adView: MaxAdView? = null

    private fun createBannerAd() {
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
        setContentView(R.layout.activity_favorite)
        // get reference to ImageView
        val image_view = findViewById<ImageView>(R.id.favBackButtonImageView)
// set on-click listener for ImageView
        image_view.setOnClickListener {
            // your code here
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
       createBannerAd()
//        var btn_submit = findViewById(R.id.favBackButtonImageView) as ImageView
//
//        btn_submit.setOnClickListener{
//            intent = Intent(applicationContext, MainActivity::class.java)
//            startActivity(intent)
//        }

//        bannerAdHelper = BannerAdHelper(this)
//        MobileAds.initialize(this) {}
//        val adRequest = AdRequest.Builder().build()
//        val adViewLayout = findViewById<LinearLayout>(R.id.favActivityBannerAdView)
//        val adview = AdView(this)
//        adview.adSize = AdSize.BANNER
//        adview.adUnitId = getString(R.string.admob_banner)
//        adview.loadAd(adRequest)
//        adViewLayout.addView(adview)
//        adview.adListener = object : AdListener() {
//            override fun onAdFailedToLoad(p0: LoadAdError) {
//                super.onAdFailedToLoad(p0)
//                bannerAdHelper.loadBannerAds(adViewLayout)
//            }
//        }

        val db = DBHelper(this, null)

        val backButton = findViewById<ImageView>(R.id.favBackButtonImageView)
        backButton.setOnClickListener {
            finish()
        }

        list = ArrayList()
        val titleRecyclerView = findViewById<RecyclerView>(R.id.favRecyclerView)
        val gridLayoutManager = GridLayoutManager(applicationContext, 3)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        titleRecyclerView.layoutManager = gridLayoutManager
        val allFavCur = db.getAllFavStatus()
        if (allFavCur?.moveToFirst()!!) {
            while (!allFavCur.isAfterLast) {
                val url: String = allFavCur.getString(1)
                val thumbUrl: String = allFavCur.getString(2)
                list.add(Video(URL = url, ThumbURL = thumbUrl, ID = 0))
                allFavCur.moveToNext()
            }
        }
        val adapter = FavoriteStatusAdapter(list)
        titleRecyclerView.adapter = adapter
    }
}