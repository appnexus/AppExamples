//
// Copyright (c) Microsoft Corporation.
// Licensed under the MIT License.
//

import UIKit
import AppNexusSDK

class BannerAdViewController: UIViewController , ANBannerAdViewDelegate{
    var banner: ANBannerAdView?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        ANLogManager.setANLogLevel(ANLogLevel.all)
        

        self.title = "Banner Ad"

        let adWidth: Int = 300
        let adHeight: Int = 250
        let adID = "17058950"
        
        // We want to center our ad on the screen.
        let screenRect: CGRect = UIScreen.main.bounds
        let originX: CGFloat = (screenRect.size.width / 2) - CGFloat((adWidth / 2))
        let originY: CGFloat = (screenRect.size.height / 2) - CGFloat((adHeight / 2))
        // Needed for when we create our ad view.
        
        let rect = CGRect(origin: CGPoint(x: originX,y :originY), size: CGSize(width: adWidth, height: adHeight))
        
        let size = CGSize(width: adWidth, height: adHeight)
        
        // Make a banner ad view.
        let banner = ANBannerAdView(frame: rect, placementId: adID, adSize: size)
        banner.rootViewController = self
        banner.delegate = self
        view.addSubview(banner)
        // Load an ad.
        banner.loadAd()
        
    }
    
    func adDidReceiveAd(_ ad: Any) {
        print("Ad did receive ad")
    }
  
    func ad(_ ad: Any, requestFailedWithError error: Error) {
        print("Ad request Failed With Error")
    }
}

