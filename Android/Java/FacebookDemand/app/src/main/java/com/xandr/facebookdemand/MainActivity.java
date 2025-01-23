package com.xandr.facebookdemand;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.appnexus.opensdk.ANAdResponseInfo;
import com.appnexus.opensdk.ANGDPRSettings;
import com.appnexus.opensdk.InitListener;
import com.appnexus.opensdk.NativeAdEventListener;
import com.appnexus.opensdk.NativeAdRequest;
import com.appnexus.opensdk.NativeAdRequestListener;
import com.appnexus.opensdk.NativeAdResponse;
import com.appnexus.opensdk.NativeAdSDK;
import com.appnexus.opensdk.ResultCode;
import com.appnexus.opensdk.SDKSettings;
import com.appnexus.opensdk.XandrAd;
import com.appnexus.opensdk.csr.FBNativeBannerAdResponse;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NativeAdRequest request;
    private NativeAdResponse response;
    private final String LOG_TAG = "FacebookDemand";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupSDKs();
    }

    public void setupSDKs() {
       // SDKSettings.useHttps(true);
        AudienceNetworkAds.buildInitSettings(this).withInitListener(new AudienceNetworkAds.InitListener() {
            @Override
            public void onInitialized(AudienceNetworkAds.InitResult initResult) {
                // Do something or remove this listener
            }
        }).initialize();

        XandrAd.init(10094, this, true, true, new InitListener() {
            @Override
            public void onInitFinished(boolean b) {

            }
        });
    }

    private void removePreviousAd() {
        if (this.response != null) {
            if (this.response instanceof FBNativeBannerAdResponse) {
                FBNativeBannerAdResponse fbresponse = (FBNativeBannerAdResponse) response;
                fbresponse.unregisterView();
                this.response = null;
            } else {
                NativeAdSDK.unRegisterTracking(findViewById(R.id.native_ad_view));
            }
        }
        NativeAdLayout nativeAdLayout = findViewById(R.id.native_banner);
        nativeAdLayout.removeAllViews();
    }

    public void loadAd(View view) {
        String placemendId = "17823252";
        if (view.getId() != R.id.load_fan) {
            placemendId = "18626248";
        }
        removePreviousAd();
        request = new NativeAdRequest(MainActivity.this, placemendId);
        request.shouldLoadImage(true);
        request.shouldLoadIcon(true);
        request.setListener(new NativeAdRequestListener() {
            @Override
            public void onAdLoaded(NativeAdResponse response) {
                Log.d(LOG_TAG, "loaded");
                MainActivity.this.response = response;
                if (response instanceof FBNativeBannerAdResponse) {
                    Log.d("NativeBanner", "FB ad loaded");
                    FBNativeBannerAdResponse fbResponse = (FBNativeBannerAdResponse) response;
                    inflateAndRegisterFB(fbResponse);
                } else {
                    inflateAndRegisterNonFB(response);
                }
            }

            @Override
            public void onAdFailed(ResultCode errorcode, ANAdResponseInfo info) {
                TextView noAd = new TextView(MainActivity.this);
                noAd.setText("Request failed due to " + errorcode.getMessage());
                noAd.setTextSize(20);
                noAd.setGravity(Gravity.CENTER);
                noAd.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                NativeAdLayout nativeAdLayout = findViewById(R.id.native_banner);
                nativeAdLayout.addView(noAd);
            }
        });
        request.loadAd();
    }

    private void inflateAndRegisterNonFB(NativeAdResponse response) {
        FrameLayout adFrame = findViewById(R.id.native_banner);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.native_banner, adFrame, false);
        adView.setId(R.id.native_ad_view);
        adFrame.addView(adView);

        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        ImageView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);


        nativeAdTitle.setText(response.getTitle());
        sponsoredLabel.setText(response.getSponsoredBy());
        nativeAdIconView.setImageBitmap(response.getIcon());
        nativeAdCallToAction.setText(response.getCallToAction());
        nativeAdCallToAction.setVisibility(!TextUtils.isEmpty(response.getCallToAction()) ? View.VISIBLE : View.INVISIBLE);

        NativeAdSDK.registerTracking(response, adView, new NativeAdEventListener() {
            @Override
            public void onAdWasClicked() {

            }

            @Override
            public void onAdWillLeaveApplication() {

            }

            @Override
            public void onAdWasClicked(String clickUrl, String fallbackURL) {

            }

            @Override
            public void onAdImpression() {

            }

            @Override
            public void onAdAboutToExpire() {

            }

            @Override
            public void onAdExpired() {

            }
        });
    }

    private void inflateAndRegisterFB(FBNativeBannerAdResponse response) {

        // Add the Ad view into the ad container.

        NativeAdLayout nativeAdLayout = findViewById(R.id.native_banner);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.native_banner, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdChoices icon
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(MainActivity.this, (NativeAdBase) response.getNativeElements().get(NativeAdResponse.NATIVE_ELEMENT_OBJECT), nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        ImageView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdCallToAction.setText(response.getCallToAction());
        nativeAdCallToAction.setVisibility(!TextUtils.isEmpty(response.getCallToAction()) ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(((NativeAdBase) response.getNativeElements().get(NativeAdResponse.NATIVE_ELEMENT_OBJECT)).getAdvertiserName());
        nativeAdSocialContext.setText(((NativeAdBase) response.getNativeElements().get(NativeAdResponse.NATIVE_ELEMENT_OBJECT)).getAdSocialContext());
        sponsoredLabel.setText(((NativeAdBase) response.getNativeElements().get(NativeAdResponse.NATIVE_ELEMENT_OBJECT)).getSponsoredTranslation());

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        response.registerView(adView, nativeAdIconView, clickableViews, new NativeAdEventListener() {
            @Override
            public void onAdWasClicked() {
                Log.d("NativeBannerTest", "FB ad was clicked");
            }

            @Override
            public void onAdWillLeaveApplication() {

            }

            @Override
            public void onAdWasClicked(String clickUrl, String fallbackURL) {

            }

            @Override
            public void onAdImpression() {

            }

            @Override
            public void onAdAboutToExpire() {

            }

            @Override
            public void onAdExpired() {

            }
        });

    }
}
