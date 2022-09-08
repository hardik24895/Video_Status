package com.ganpatibaapaa.motivational.status.video.adsManager;
//
//import android.app.Activity;
//import android.graphics.Color;
//import android.util.Log;
//import android.view.View;
//import android.widget.RelativeLayout;
//
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.core.view.ViewCompat;
////
////import com.applovin.mediation.MaxAd;
////import com.applovin.mediation.MaxAdFormat;
////import com.applovin.mediation.MaxAdRevenueListener;
////import com.applovin.mediation.MaxAdViewAdListener;
////import com.applovin.mediation.MaxError;
////import com.applovin.mediation.ads.MaxAdView;
////import com.applovin.sdk.AppLovinSdkUtils;
//import com.krishna.motivational.status.video.R;
//
//public class NativeAdsManager {
//
//    private static final String TAG = "NativeAdsManager";
//    private static final int AD_NUM = 1;
//    private Activity activity;
//    private RelativeLayout adsContainerView;
//
//
//    public static NativeAdsManager shared(Activity activity) {
//        return new NativeAdsManager(activity);
//    }
//
//    public NativeAdsManager(Activity activity) {
//        this.activity = activity;
//    }
//
//    public void loadNativeAds() {
//
//        showAppLovinMaxNative();
//
//    }
//
//
//    private void showAppLovinMaxNative() {
//
//        MaxAdView adView = new MaxAdView("88c2378fe166d1d8", MaxAdFormat.MREC, activity);
//
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
//                Log.d("onAdLoaded", "onAdLoaded");
//
//                adView.setVisibility(View.VISIBLE);
//                adsContainerView.setVisibility(View.VISIBLE);
//
//            }
//
//            @Override
//            public void onAdDisplayed(MaxAd ad) {
//                Log.d("onAdDisplayed", "onAdDisplayed");
//            }
//
//            @Override
//            public void onAdHidden(MaxAd ad) {
//                Log.d("onAdHidden", "onAdHidden");
//                hideCardView(adView, adsContainerView);
//
//                adsContainerView.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onAdClicked(MaxAd ad) {
//                Log.d("onAdClicked", "onAdClicked");
//            }
//
//            @Override
//            public void onAdLoadFailed(String adUnitId, MaxError error) {
//                Log.d("onAdLoadFailed", "onAdLoadFailed");
//
//                adsContainerView.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
//                Log.d("onAdDisplayFailed", "onAdDisplayFailed");
//
//                adsContainerView.setVisibility(View.GONE);
//
//            }
//        });
//        adView.setRevenueListener(new MaxAdRevenueListener() {
//            @Override
//            public void onAdRevenuePaid(MaxAd ad) {
//                Log.d("onAdRevenuePaid", "onAdRevenuePaid");
//            }
//        });
//
//        adView.setId(ViewCompat.generateViewId());
//
//        final int widthPx = AppLovinSdkUtils.dpToPx(activity, 300);
//        final int heightPx = AppLovinSdkUtils.dpToPx(activity, 250);
//
//
//        adView.setLayoutParams(new ConstraintLayout.LayoutParams(widthPx, heightPx));
//
//        // Need to set the background or background color for MRECs to be fully functional.
//        adView.setBackgroundColor(Color.BLACK);
//        //adView.setVisibility(View.GONE);
//
//
//        adView.setBackground(activity.getResources().getDrawable(R.color.white));
//        adView.setPadding(8, 8, 8, 8);
//        adsContainerView.removeAllViews();
//        adsContainerView.addView(adView);
//        // Load the first ad.
//        adView.loadAd();
//    }
//
//
//    private void hideCardView(MaxAdView adView, RelativeLayout layout) {
//
//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                layout.setVisibility(View.GONE);
//                adView.setVisibility(View.GONE);
//            }
//        });
//    }
//}
