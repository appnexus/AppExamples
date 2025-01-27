//
// Copyright (c) Microsoft Corporation.
// Licensed under the MIT License.
//

import UIKit
import AppNexusSDK
@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        ANSDKSettings.sharedInstance().enableOMIDOptimization = true
        ANLogManager.setANLogLevel(ANLogLevel.all)
        XandrAd.sharedInstance().initWithMemberID(10094, preCacheRequestObjects: true ,completionHandler: { (status) in
            print("Done🔨 \(status)")
        })
        return true
    }
}

