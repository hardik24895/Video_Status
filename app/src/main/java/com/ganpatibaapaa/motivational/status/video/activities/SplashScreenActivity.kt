package com.ganpatibaapaa.motivational.status.video.activities

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateManager
import com.ganpatibaapaa.motivational.status.video.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var mUpdateManager: AppUpdateManager
    lateinit var builder: AlertDialog.Builder
    var reqCode = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        imageView = findViewById(R.id.splashImageView)

        builder = AlertDialog.Builder(this)
        builder.setTitle("Update Alert")
        builder.setMessage("New update is available.")

        builder.setPositiveButton("INSTALL") { dialog, which ->

            val appPackageName = packageName

            try {
                startActivityIfNeeded(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    ),
                    reqCode
                )
            } catch (anfe: ActivityNotFoundException) {
                startActivityIfNeeded(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    ),
                    reqCode
                )
            }

        }

        builder.setNegativeButton("LATER") { dialog, which ->

            imageView.postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)
        }
        imageView.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
        /* mUpdateManager = AppUpdateManagerFactory.create(this)
         mUpdateManager.appUpdateInfo.addOnSuccessListener {
             if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && it.isUpdateTypeAllowed(
                     AppUpdateType.IMMEDIATE
                 )
             ) {
                 builder.show()
             } else {
                 imageView.postDelayed({
                     startActivity(Intent(this, MainActivity::class.java))
                     finish()
                 }, 2000)
             }
         }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == reqCode) {
            imageView.postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)
        }
    }
}