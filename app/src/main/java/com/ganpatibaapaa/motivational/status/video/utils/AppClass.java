package com.ganpatibaapaa.motivational.status.video.utils;
//
//import static com.krishna.motivational.status.video.BuildConfig.DEBUG;
//
//import android.app.Application;
//import android.content.Context;
//import android.util.Log;
//
//import com.applovin.sdk.AppLovinMediationProvider;
//import com.applovin.sdk.AppLovinSdk;
//import com.applovin.sdk.AppLovinSdkConfiguration;
//import com.facebook.ads.AdSettings;
////import com.facebook.ads.AudienceNetworkAds;
//
//public class AppClass extends Application {
//
//    private Context context;
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        context = this;
//        AudienceNetworkAds.initialize(this);
//        AudienceNetworkInitializeHelper.initialize(this);
//
//     /*   AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                AdvertisingIdClient.Info idInfo = null;
//                try {
//                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    e.printStackTrace();
//                } catch (GooglePlayServicesRepairableException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                String advertId = null;
//                try{
//                    advertId = idInfo.getId();
//                    AppLovinSdk.getInstance(context).getSettings().setTestDeviceAdvertisingIds(Arrays.asList(advertId));
//
//                }catch (NullPointerException e){
//                    e.printStackTrace();
//                }
//
//                return advertId;
//            }
//
//            @Override
//            protected void onPostExecute(String advertId) {
//                //Toast.makeText(getApplicationContext(), advertId, Toast.LENGTH_SHORT).show();
//            }
//
//        };
//        task.execute();*/
//        // Initialize the AppLovin SDK
//        AppLovinSdk.getInstance( this ).setMediationProvider( AppLovinMediationProvider.MAX );
//        AppLovinSdk.getInstance( this ).initializeSdk( new AppLovinSdk.SdkInitializationListener()
//        {
//            @Override
//            public void onSdkInitialized(final AppLovinSdkConfiguration config)
//            {
//                Log.d("TAG_INIT", "AppLovinSdk INIT");
//                // AppLovin SDK is initialized, start loading ads now or later if ad gate is reached
//            }
//        } );
//    }
//    private static class AudienceNetworkInitializeHelper implements AudienceNetworkAds.InitListener {
//
//        static void initialize(Context context) {
//            if (!AudienceNetworkAds.isInitialized(context)) {
//                if (!DEBUG) {
//                    AdSettings.turnOnSDKDebugger(context);
//                }
//
//                AudienceNetworkAds
//                        .buildInitSettings(context)
//                        .withInitListener(new AudienceNetworkInitializeHelper())
//                        .initialize();
//            }
//        }
//
//        @Override
//        public void onInitialized(AudienceNetworkAds.InitResult result) {
//            Log.d(AudienceNetworkAds.TAG, result.getMessage());
//        }
//
//    }
//
//}
