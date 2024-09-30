/*   Copyright 2020 APPNEXUS INC
 
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

import UIKit
import AppNexusSDK

class NativeAdViewController: UIViewController , ANNativeAdRequestDelegate , ANNativeAdDelegate {
    
    var nativeAdRequest: ANNativeAdRequest?
    var nativeAdResponse: ANNativeAdResponse?
    var anNativeAdView: ANNativeAdView?

    
    var indicator = UIActivityIndicatorView()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "Native Ad"
        
        // Do any additional setup after loading the view.
        
        nativeAdRequest = ANNativeAdRequest()
        nativeAdRequest!.placementId = "17058950"
        nativeAdRequest!.shouldLoadIconImage = true
        nativeAdRequest!.shouldLoadMainImage = true
        nativeAdRequest!.delegate = self
        nativeAdRequest!.loadAd()
    }
    
    
    func adRequest(_ request: ANNativeAdRequest, didReceive response: ANNativeAdResponse) {
        self.nativeAdResponse = response
        let adNib = UINib(nibName: "ANNativeAdView", bundle: Bundle.main)
        let array = adNib.instantiate(withOwner: self, options: nil)
        anNativeAdView = array.first as? ANNativeAdView
        anNativeAdView?.titleLabel.text = nativeAdResponse?.title
        anNativeAdView?.bodyLabel.text = nativeAdResponse?.body
        anNativeAdView?.iconImageView.image = nativeAdResponse?.iconImage
        anNativeAdView?.mainImageView.image = nativeAdResponse?.mainImage
        anNativeAdView?.sponsoredLabel.text = nativeAdResponse?.sponsoredBy
        anNativeAdView?.callToActionButton.setTitle(nativeAdResponse?.callToAction, for: .normal)
        nativeAdResponse?.delegate = self
        nativeAdResponse?.clickThroughAction = ANClickThroughAction.openSDKBrowser
        view.addSubview(anNativeAdView!)
        do {
            try nativeAdResponse?.registerView(forTracking: anNativeAdView!, withRootViewController: self, clickableViews: [self.anNativeAdView?.callToActionButton! as Any, self.anNativeAdView?.mainImageView! as Any])
        } catch {
            print("Failed to registerView for Tracking")
        }        
    }
    
    func adRequest(_ request: ANNativeAdRequest, didFailToLoadWithError error: Error, with adResponseInfo: ANAdResponseInfo?) {
        print("Ad request Failed With Error")
    }
    
    // MARK: - ANNativeAdDelegate
    func adDidLogImpression(_ response: Any) {
        print("adDidLogImpression")
    }
    
    func adWillExpire(_ response: Any) {
        print("adWillExpire")
    }
    
    func adDidExpire(_ response: Any) {
        print("adDidExpire")
    }
    
    func adWasClicked(_ response: Any) {
        print("adWasClicked")
    }
    
    func adWillPresent(_ response: Any) {
        print("adWillPresent")
    }
    
    func adDidPresent(_ response: Any) {
        print("adDidPresent")
    }
    
    func adWillClose(_ response: Any) {
        print("adWillClose")
    }
    
    func adDidClose(_ response: Any) {
        print("adDidClose")
    }
    
    func adWillLeaveApplication(_ response: Any) {
        print("adWillLeaveApplication")
    }
    
    
    @IBAction func hideAds(_ sender: Any) {
        self.anNativeAdView?.removeFromSuperview()
        
    }
}

