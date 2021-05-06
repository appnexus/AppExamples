/*
 *    Copyright 2021 APPNEXUS INC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.example.nativerendererbasicexample

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.appnexus.opensdk.*


class MainActivity : AppCompatActivity(), AdListener {

    private lateinit var banner: BannerAdView
    private lateinit var bannerContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bannerContainer = findViewById(R.id.bannerContainer)

        // create an instance of banner
        banner = BannerAdView(this)

        // Set placement id
        banner.placementID = "17058950" // Set PlacementID

        //banner.autoRefreshInterval=0; // turn off autorefresh

        // Allow Native Demand for this banner placement.
        banner.allowNativeDemand = true

        // Enable Native rendering in the SDK. Default is turned off and native renderer will not be used even if returned
        // If native rendering is not enabled in the SDK, even if a renderer is present it wont be used. Instead NativeAdResponse will be returned via onAdLoaded(NativeAdResponse) and the app needs to display it natively
        banner.enableNativeRendering(true)


        banner.allowVideoDemand = false // Disable video for this example. By default it is turned off
        banner.setAllowBannerDemand(false) // Disable HTML ads for this example. By default it is turned on



        // If only size is set then it is used as the primary size for native rendering.
        // banner.setAdSize(300, 250)

        val adSizeArrayList = ArrayList<AdSize>()
        adSizeArrayList.add(AdSize(300, 250)) // If using multiple sizes, the first size in the list is used as primary size for ad request and for native rendering
        adSizeArrayList.add(AdSize(320, 250))
        adSizeArrayList.add(AdSize(300, 50))
        adSizeArrayList.add(AdSize(320, 50))
        banner.adSizes = adSizeArrayList;


        // Set whether ads will expand to fit the BannerAdView.
        //banner.resizeAdToFitContainer = true;


        // Set whether ads will expand to fit the screen width.
        banner.expandsToFitScreenWidth = true;

        banner.adListener = this // AdListener

        bannerContainer.addView(banner) // Add the banner to container, This can be done later as well in the onAdLoad

        banner.loadAd() // Load Ad

    }


    override fun onAdLoaded(ad: AdView?) {
        // Check for AdType in onAdLoaded to figure out which ad has loaded.
        if(banner.adResponseInfo.adType == AdType.NATIVE){
            log("Banner Native ad rendered using native assembly")
        }

        if(banner.adResponseInfo.adType == AdType.VIDEO){
            log("OutStream Ad Loaded")
        }

        if(banner.adResponseInfo.adType == AdType.BANNER){
            log("Banner Ad Loaded")
        }
    }

    override fun onAdLoaded(nativeAdResponse: NativeAdResponse?) {
        log("Native Ad Loaded")
    }

    override fun onAdClicked(adView: AdView?) {
        log("Ad Clicked")
    }

    override fun onAdClicked(adView: AdView?, clickUrl: String?) {
        log("Ad Clicked with URL: $clickUrl")
    }

    override fun onAdExpanded(adView: AdView?) {
        log("Ad Expanded")
    }

    override fun onAdCollapsed(adView: AdView?) {
        log("Ad Collapsed")
    }

    override fun onAdRequestFailed(adView: AdView?, resultCode: ResultCode?) {
        log("Ad Failed: " + resultCode?.message)
    }

    override fun onLazyAdLoaded(adView: AdView?) {
        log("Ad onLazyAdLoaded")
    }

    private fun log(msg: String){
        Log.d("BannerActivity", msg)
        Toast.makeText(this.applicationContext, msg, Toast.LENGTH_LONG).show()
    }
}