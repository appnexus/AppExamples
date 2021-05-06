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

import UIKit
import AppNexusSDK

class ViewController: UIViewController, ANBannerAdViewDelegate {

    // MARK: IBOutlets
    @IBOutlet weak var adViewContainer: UIView!
    
    // MARK: Properties
    var banner: ANBannerAdView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Logs all ad events
        ANLogManager.setANLogLevel(ANLogLevel.all)

        // Banner Native Renderer placement
        let adID = "17058950"
        
        // Make a banner ad view.
        banner = ANBannerAdView(frame: adViewContainer.bounds, placementId: adID)
        banner.rootViewController = self
        banner.delegate = self
        
        // If only size is set then it is used as the primary size for native rendering.
        //banner.adSize = CGSize(width: 300, height: 250)
        
        
        // If using multiple sizes, the first size in the list is used as primary size for ad request and for native rendering.
        // In the below case it will be 300x250
        banner.adSizes = [NSValue.init(cgSize: CGSize(width: 300, height: 250)),
                               NSValue.init(cgSize: CGSize(width: 320, height: 250)),
                               NSValue.init(cgSize: CGSize(width: 300, height: 50)),
                               NSValue.init(cgSize: CGSize(width: 320, height: 50))]
        
        
        // Allow Native Demand for this banner placement.
        banner.shouldAllowNativeDemand = true
        // Enable Native rendering in the SDK. Default is turned off and native renderer will not be used even if returned
        // If native rendering is not enabled in the SDK, even if a renderer is present it wont be used. Instead NativeAdResponse will be returned via didReceiveNativeAd and the app needs to display it natively
        banner.enableNativeRendering = true
        
        
        banner.shouldAllowVideoDemand = false; // Disable video for this example. Default is turned off
        banner.shouldAllowBannerDemand = false; // Disable HTML fi required. Default is turned on
        
        
        adViewContainer.addSubview(banner)
        // Load an ad.
        banner.loadAd()
        
    }
    
    // MARK: Delegate methods exclusively for ANBannerAdViewDelegate (ANAdDelegate)
    func adDidReceiveAd(_ ad: Any) {
        
        if(banner?.adResponseInfo?.adType == ANAdType.native){
            print("Banner Native ad rendered using native assembly")
            // Do your own custom logic here if required based on the returned AdType
        }
        
        if(banner?.adResponseInfo?.adType == ANAdType.video){
            print("OutStream Ad Loaded")
            // Do your own custom logic here if required based on the returned AdType
        }
        
        if(banner?.adResponseInfo?.adType == ANAdType.banner){
            print("Banner Ad Loaded")
            // Do your own custom logic here if required based on the returned AdType
        }
    }
  
    func ad(_ ad: Any, requestFailedWithError error: Error) {
        print("Ad request Failed With Error")
    }
    
    
    func ad(_ loadInstance: Any, didReceiveNativeAd responseInstance: Any) {
        print("Native Ad Loaded")
    }
    


}

