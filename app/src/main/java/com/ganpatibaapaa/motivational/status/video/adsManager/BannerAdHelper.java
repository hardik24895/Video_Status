package com.ganpatibaapaa.motivational.status.video.adsManager;
//
//import android.app.Activity;
//import android.graphics.Color;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//
//import com.applovin.mediation.MaxAd;
//import com.applovin.mediation.MaxAdRevenueListener;
//import com.applovin.mediation.MaxAdViewAdListener;
//import com.applovin.mediation.MaxError;
//import com.applovin.mediation.ads.MaxAdView;
//import com.applovin.sdk.AppLovinSdkUtils;
//
//public class BannerAdHelper {
//
//    private static final String TAG = "BannerAdHelper";
//    private Activity activity;
//    private LinearLayout mBannerAdView;
//
//    public BannerAdHelper(Activity activity) {
//        this.activity = activity;
//    }
//
//    public void loadBannerAds(LinearLayout linearLayout) {
//        mBannerAdView = linearLayout;
//        linearLayout.setVisibility(View.INVISIBLE);
//        showAppLovinMax();
//    }
//
//    public void showAppLovinMax() {
//        MaxAdView adView = new MaxAdView("58932fecce54ff1d", activity);
//        mBannerAdView.addView(adView);
//        adView.setListener(new MaxAdViewAdListener() {
//            @Override
//            public void onAdExpanded(MaxAd ad) {
//                Log.d("onAdExpanded", "onAdExpanded");
//            }
//
//            @Override
//            public void onAdCollapsed(MaxAd ad) {
//                Log.d("onAdCollapsed", "onAdCollapsed");
//            }
//
//            @Override
//            public void onAdLoaded(MaxAd ad) {
//                mBannerAdView.setVisibility(View.VISIBLE);
//                Log.d("onAdLoaded", "onAdLoaded");
//            }
//
//            @Override
//            public void onAdDisplayed(MaxAd ad) {
//                Log.d("onAdDisplayed", "onAdDisplayed");
//
//            }
//
//            @Override
//            public void onAdHidden(MaxAd ad) {
//                Log.d("onAdHidden", "onAdHidden");
//            }
//
//            @Override
//            public void onAdClicked(MaxAd ad) {
//                Log.d("onAdClicked", "onAdClicked");
//            }
//
//            @Override
//            public void onAdLoadFailed(String adUnitId, MaxError error) {
//
//                mBannerAdView.setVisibility(View.INVISIBLE);
//
//                Log.d("onAdLoadFailed", "onAdLoadFailed");
//            }
//
//            @Override
//            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
//                Log.d("onAdDisplayFailed", "onAdDisplayFailed");
//            }
//        });
//        adView.setRevenueListener(new MaxAdRevenueListener() {
//            @Override
//            public void onAdRevenuePaid(MaxAd ad) {
//                Log.d("onAdRevenuePaid", "onAdRevenuePaid");
//            }
//        });
//
//        // Set the height of the banner ad based on the device type.
//        final boolean isTablet = AppLovinSdkUtils.isTablet(activity);
//        final int heightPx = AppLovinSdkUtils.dpToPx(activity, isTablet ? 90 : 50);
//        // Banner width must match the screen to be fully functional.
//        adView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPx));
//
//        // Need to set the background or background color for banners to be fully functional.
//        adView.setBackgroundColor(Color.TRANSPARENT);
//
//        // Load the first ad.
//        adView.loadAd();
//    }
//
//
//}
