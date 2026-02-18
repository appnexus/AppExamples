package com.xandr.facebookdemand;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.appnexus.opensdk.ANAdResponseInfo;
import com.appnexus.opensdk.InitListener;
import com.appnexus.opensdk.NativeAdEventListener;
import com.appnexus.opensdk.NativeAdRequest;
import com.appnexus.opensdk.NativeAdRequestListener;
import com.appnexus.opensdk.NativeAdResponse;
import com.appnexus.opensdk.NativeAdSDK;
import com.appnexus.opensdk.ResultCode;
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
                View adView = findViewById(R.id.native_ad_view);
                if (adView != null) {
                    NativeAdSDK.unRegisterTracking(adView);
                }
            }
        }
        ConstraintLayout adContainer = findViewById(R.id.ad_container);
        if (adContainer != null) {
            adContainer.removeAllViews();
        }
    }

    public void loadAd(View view) {
        String placemendId = "17058950";
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
                ConstraintLayout adContainer = findViewById(R.id.ad_container);
                adContainer.addView(noAd);
            }
        });
        request.loadAd();
    }

    private void inflateAndRegisterNonFB(NativeAdResponse response) {
        ConstraintLayout adFrame = findViewById(R.id.ad_container);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        ConstraintLayout adView = (ConstraintLayout) inflater.inflate(R.layout.native_ad_layout, adFrame, false);
        adFrame.addView(adView);

        TextView nativeAdTitle = adView.findViewById(R.id.an_title);
        TextView nativeAdDescription = adView.findViewById(R.id.an_description);
        TextView sponsoredLabel = adView.findViewById(R.id.an_sponsoredBy);
        ImageView nativeAdIconView = adView.findViewById(R.id.an_icon);
        ImageView nativeImage = adView.findViewById(R.id.an_image);
        Button nativeAdCallToAction = adView.findViewById(R.id.an_clickThrough);


        nativeAdTitle.setText(response.getTitle());
        nativeAdDescription.setText(response.getDescription());
        sponsoredLabel.setText(response.getSponsoredBy());
        nativeAdIconView.setImageBitmap(response.getIcon());
        nativeImage.setImageBitmap(response.getImage());
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
        ConstraintLayout adFrame = findViewById(R.id.ad_container);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

        ConstraintLayout adView = (ConstraintLayout) inflater.inflate(R.layout.facebook_native_banner, adFrame, false);
        adFrame.addView(adView);

        // Get the NativeAdLayout from the inflated ConstraintLayout
        NativeAdLayout nativeAdLayout = adView.findViewById(R.id.fb_native_ad_layout);

        // Add the AdChoices icon
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.fb_ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(MainActivity.this, (NativeAdBase) response.getNativeElements().get(NativeAdResponse.NATIVE_ELEMENT_OBJECT), nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adView.findViewById(R.id.fb_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.fb_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.fb_sponsored_label);
        ImageView nativeAdIconView = adView.findViewById(R.id.fb_icon);
        Button nativeAdCallToAction = adView.findViewById(R.id.fb_cta);

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
