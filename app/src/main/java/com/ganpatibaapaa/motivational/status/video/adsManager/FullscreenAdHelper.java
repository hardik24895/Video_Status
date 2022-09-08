package com.ganpatibaapaa.motivational.status.video.adsManager;
//
//import android.app.Activity;
//import android.util.Log;
//
//import com.applovin.mediation.MaxAd;
//import com.applovin.mediation.MaxAdListener;
//import com.applovin.mediation.MaxError;
//import com.applovin.mediation.ads.MaxInterstitialAd;
//
//public class FullscreenAdHelper {
//
//    private static final String TAG = "FullscreenAdHelper";
//    private Activity mAactivity;
//
//    public FullscreenAdHelper(Activity activity) {
//        mAactivity = activity;
//    }
//
//    public void loadInterstitialAds(FullscreenListener listener) {
//        showApplovinMaxFullscreen(listener);
//
//    }
//
//    private void showApplovinMaxFullscreen(FullscreenListener listener){
//
//        MaxInterstitialAd interstitialAd = new MaxInterstitialAd( "12d4f241835b424e", mAactivity );
//
//        interstitialAd.setListener(new MaxAdListener() {
//            @Override
//            public void onAdLoaded(MaxAd ad) {
//                if ( interstitialAd.isReady() )
//                {
//                    interstitialAd.showAd();
//                }
//            }
//
//            @Override
//            public void onAdDisplayed(MaxAd ad) {
//                listener.isShown();
//            }
//
//            @Override
//            public void onAdHidden(MaxAd ad) {
//                listener.isDismissed();
//            }
//
//            @Override
//            public void onAdClicked(MaxAd ad) {
//
//            }
//
//            @Override
//            public void onAdLoadFailed(String adUnitId, MaxError error) {
//                    listener.isFailed();
//                    //openActivity();
//
//                Log.d("Applovin","onAdLoadFailed");
//            }
//
//            @Override
//            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
//                //openActivity();
//                listener.isFailed();
//            }
//        });
//        interstitialAd.setRevenueListener(ad -> {});
//
//        // Load the first ad.
//        interstitialAd.loadAd();
//    }
//}
