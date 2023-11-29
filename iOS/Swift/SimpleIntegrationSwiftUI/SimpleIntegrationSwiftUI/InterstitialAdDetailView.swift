/*   Copyright 2023 APPNEXUS INC
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import SwiftUI
import AppNexusSDK

// ObservableObject to manage the interstitial ad
class InterstitialAdManager: NSObject, ObservableObject, ANInterstitialAdDelegate {
    var interstitialAd: ANInterstitialAd?

    override init() {
        super.init()
        ANSDKSettings.sharedInstance().enableOMIDOptimization = true
        ANLogManager.setANLogLevel(ANLogLevel.all)
        XandrAd.sharedInstance().initWithMemberID(10094,preCacheRequestObjects: true, completionHandler: nil)

        
        interstitialAd = ANInterstitialAd(placementId: "17058950")
        interstitialAd?.delegate = self
        interstitialAd?.clickThroughAction = .openSDKBrowser
        interstitialAd?.load()
    }

    func showAd() {
        DispatchQueue.main.async { [self] in
            
            if interstitialAd?.isReady ?? false {
                if(UIApplication.shared.currentUIWindow()?.rootViewController != nil){
                    interstitialAd?.display(from: (UIApplication.shared.currentUIWindow()?.rootViewController)!)
                }
            }
        }
    }

    // MARK: - ANInterstitialAdDelegate
    func adDidReceiveAd(_ ad: Any) {
        print("adDidReceiveAd")
        // Ad is ready to be displayed, but we'll wait for a manual trigger
    }

    func ad(_ ad: Any, requestFailedWithError error: Error) {
        print("Ad request Failed With Error")
    }
}


struct InterstitialAdDetailView: View {
    @StateObject private var adManager = InterstitialAdManager()

    
    var body: some View {
        // Your content view
        Button("Show Interstitial Ad") {
                adManager.showAd()
        }
    }
}

public extension UIApplication {
    func currentUIWindow() -> UIWindow? {
        let connectedScenes = UIApplication.shared.connectedScenes
            .filter { $0.activationState == .foregroundActive }
            .compactMap { $0 as? UIWindowScene }
        
        let window = connectedScenes.first?
            .windows
            .first { $0.isKeyWindow }

        return window
        
    }
}
