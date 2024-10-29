//
// Copyright (c) Microsoft Corporation.
// Licensed under the MIT License.
//

import UIKit
import AppNexusSDK

class InterstitialAdViewController: UIViewController , ANInterstitialAdDelegate {
    
    var interstitialAd: ANInterstitialAd?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "Interstitial Ad"
        
        interstitialAd = ANInterstitialAd(placementId: "17058950")
        interstitialAd!.delegate = self
        interstitialAd!.clickThroughAction = ANClickThroughAction.openSDKBrowser
        interstitialAd!.load()
        // Do any additional setup after loading the view.
    }
    
    // MARK: - ANInterstitialAdDelegate
    func adDidReceiveAd(_ ad: Any) {
        print("adDidReceiveAd")
        interstitialAd!.display(from: self)
    }
    
    func ad(_ ad: Any, requestFailedWithError error: Error) {
        print("Ad request Failed With Error")
    }
}

