package com.ganpatibaapaa.motivational.status.video.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.applovin.mediation.ads.MaxAdView
import com.ganpatibaapaa.motivational.status.video.R

class SettingsActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_settings)
        createBannerAd()

        val backButton = findViewById<ImageView>(R.id.settingBackButtonImageView)
        backButton.setOnClickListener {
            finish()
        }

        val shareAppButton = findViewById<TextView>(R.id.settingShareAppTextView)
        shareAppButton.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody =
                "https://play.google.com/store/apps/details?id=${packageName}"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "App link")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share App Link Via :"))
        }


        val rateUs = findViewById<TextView>(R.id.settingRateUSTextView)
        rateUs.setOnClickListener {

            val appPackageName = packageName

            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }
        
        val policy = findViewById<TextView>(R.id.settingPrivacyPolicyTextView)
        policy.setOnClickListener {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://videostatus.link/Ganesha_Privacy_Policy.html")
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://www.governmentmaster.com/Privacy_Policy.html")
                    )
                )
            }
        }
    }
}