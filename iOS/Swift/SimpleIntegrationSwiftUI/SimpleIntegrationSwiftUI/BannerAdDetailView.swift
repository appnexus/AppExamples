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

struct BannerAdView: UIViewRepresentable {
    let adWidth: Int = 300
    let adHeight: Int = 250
    let adID = "17058950"

    func makeUIView(context: Context) -> ANBannerAdView {
       
        ANSDKSettings.sharedInstance().enableOMIDOptimization = true
        ANLogManager.setANLogLevel(ANLogLevel.all)
        XandrAd.sharedInstance().initWithMemberID(10094,preCacheRequestObjects: true, completionHandler: nil)

        
        let size = CGSize(width: adWidth, height: adHeight)
        let banner = ANBannerAdView(frame: .zero, placementId: adID, adSize: size)
        banner.delegate = context.coordinator
        banner.loadAd()
        return banner
    }

    func updateUIView(_ uiView: ANBannerAdView, context: Context) {
        // Update the view if needed.
    }

    func makeCoordinator() -> Coordinator {
        Coordinator()
    }

    class Coordinator: NSObject, ANBannerAdViewDelegate {
        func adDidReceiveAd(_ ad: Any) {
            print("Ad did receive ad")
        }

        func ad(_ ad: Any, requestFailedWithError error: Error) {
            print("Ad request Failed With Error")
        }
    }
}

struct BannerAdDetailView: View {

    var body: some View {
            // Use GeometryReader to center the ad
            GeometryReader { geometry in
                BannerAdView()
            }
        }
}

struct BannerAdView_Previews: PreviewProvider {
    static var previews: some View {
        BannerAdDetailView()
    }
}
