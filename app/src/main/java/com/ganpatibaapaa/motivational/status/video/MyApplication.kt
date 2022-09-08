package com.ganpatibaapaa.motivational.status.video

import android.app.Application
import android.util.Log
import com.applovin.sdk.AppLovinSdk

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Make sure to set the mediation provider value to "max" to ensure proper functionality
        AppLovinSdk.getInstance(this).mediationProvider = "max"
        AppLovinSdk.getInstance(this).initializeSdk {
            // AppLovin SDK is initialized, start loading ads
            Log.e("TAG", "AppLovinSdk onCreate: loaded", )
        }

    }
}