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

// Define your Coordinator as an ObservableObject
class Coordinator: NSObject, ObservableObject, ANNativeAdRequestDelegate, ANNativeAdDelegate {
    @Published var nativeAdResponse: ANNativeAdResponse?
    @Published var nativeAdRequest: ANNativeAdRequest?
    @Published var isLoading = true
    @Published var adView: ANNativeAdView?

    func clear(){
        nativeAdResponse = nil
        nativeAdRequest = nil
    }
    func loadAd() {
        
        ANSDKSettings.sharedInstance().enableOMIDOptimization = true
        ANLogManager.setANLogLevel(ANLogLevel.all)
        XandrAd.sharedInstance().initWithMemberID(10094,preCacheRequestObjects: true, completionHandler: nil)

        nativeAdRequest = ANNativeAdRequest()
        nativeAdRequest!.placementId = "17058950"
        nativeAdRequest!.shouldLoadIconImage = true
        nativeAdRequest!.shouldLoadMainImage = true
        nativeAdRequest!.delegate = self
        nativeAdRequest!.loadAd()
    }

    func adRequest(_ request: ANNativeAdRequest, didReceive response: ANNativeAdResponse) {
        DispatchQueue.main.async {
            self.nativeAdResponse = response
            self.isLoading = false

        }
    }

    func adRequest(_ request: ANNativeAdRequest, didFailToLoadWithError error: Error, with adResponseInfo: ANAdResponseInfo?) {
        DispatchQueue.main.async {
            self.isLoading = false
            // Handle the error
            print("Ad request failed with error: \(error.localizedDescription)")
        }
    }

    
    func registerAdViewForTracking() {
        guard let adView = self.adView else {
            print("Ad View is not available for tracking")
            return
        }

        let rootViewController = UIApplication.shared.windows.first(where: { $0.isKeyWindow })?.rootViewController

        do {
            self.nativeAdResponse?.delegate = self
            try self.nativeAdResponse!.registerView(forTracking: adView, withRootViewController: rootViewController!, clickableViews: [adView.callToActionButton, adView.mainImageView].compactMap { $0 })
        } catch {
            print("Failed to registerView for Tracking: \(error)")
        }
    }

    
    func setAdView(_ view: ANNativeAdView) {
            self.adView = view
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
    
    }

// Define your UIViewRepresentable
struct ANNativeAdViewRepresentable: UIViewRepresentable {
    @ObservedObject var coordinator: Coordinator
    
    func makeUIView(context: Context) -> ANNativeAdView {
        let adNib = UINib(nibName: "ANNativeAdView", bundle: nil)
        let array = adNib.instantiate(withOwner: nil, options: nil)
        guard let adView = array.first as? ANNativeAdView else {
            fatalError("Expected `ANNativeAdView` type for UIViewRepresentable.")
        }
        coordinator.adView = adView // Set the adView here
        coordinator.registerAdViewForTracking()

        return adView
    }

    func updateUIView(_ uiView: ANNativeAdView, context: Context) {
        uiView.titleLabel.text = coordinator.nativeAdResponse?.title
        uiView.bodyLabel.text = coordinator.nativeAdResponse?.body
        uiView.iconImageView.image = coordinator.nativeAdResponse?.iconImage
        uiView.mainImageView.image = coordinator.nativeAdResponse?.mainImage
        uiView.sponsoredLabel.text = coordinator.nativeAdResponse?.sponsoredBy
        uiView.callToActionButton.setTitle(coordinator.nativeAdResponse?.callToAction, for: .normal)
    }
}

// Define your SwiftUI view
struct NativeAdView: View {
    @StateObject private var coordinator = Coordinator()

    var body: some View {
        ZStack {
            if coordinator.isLoading {
                ProgressView()
            } else if let _ = coordinator.nativeAdResponse {
                ANNativeAdViewRepresentable(coordinator: coordinator)
            } else {
                Text("Ad failed to load or was not yet requested.")
            }
        }
        .onAppear {
            coordinator.loadAd()
        }.onDisappear {
            coordinator.clear()
        }
    }
}

struct NativeAdDetailView: View {
   
    var body: some View {
        NativeAdView()
            .frame(height: 300) // Set the frame as needed
    }

}
