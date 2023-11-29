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


class InstreamVideoAdAdManager: NSObject, ObservableObject, ANInstreamVideoAdLoadDelegate, ANInstreamVideoAdPlayDelegate {
    
    var videoAd: ANInstreamVideoAd?
    override init() {
        super.init()
        ANSDKSettings.sharedInstance().enableOMIDOptimization = true
        ANLogManager.setANLogLevel(ANLogLevel.all)
        XandrAd.sharedInstance().initWithMemberID(10094,preCacheRequestObjects: true, completionHandler: nil)
        videoAd = ANInstreamVideoAd(placementId: "17058950")

        videoAd?.load(with: self)
    }


    func showAd(in container: UIView) {
        videoAd?.play(withContainer: container, with: self)
    }

    
    
    func adDidReceiveAd(_ ad: Any) {
   
    }
    
    func ad(_ ad: ANAdProtocol?) throws {
    }
    
    //----------------------------- -o-
    func adCompletedFirstQuartile(_ ad: ANAdProtocol) {
    }
    
    func adCompletedMidQuartile(_ ad: ANAdProtocol) {
        
    }
    //----------------------------- -o-
    func adPlayStarted(_ ad: ANAdProtocol) {
        
    }
    
    func adCompletedThirdQuartile(_ ad: ANAdProtocol) {
    }
    
    
    func adWasClicked(_ ad: ANAdProtocol) {
        
    }
    
    func adMute(_ ad: ANAdProtocol, withStatus muteStatus: Bool) {
        if muteStatus == true {
            print("adMuteOn")
        } else {
            print("adMuteOff")
        }
    }
    
    func adDidComplete(_ ad: ANAdProtocol, with state: ANInstreamVideoPlaybackStateType) {
        if state == ANInstreamVideoPlaybackStateType.skipped {
            print("adWasSkipped")
        } else if state == ANInstreamVideoPlaybackStateType.error {
            print("adplaybackFailedWithError")
        } else if state == ANInstreamVideoPlaybackStateType.completed {
            print("adPlayCompleted")
        }
        
    }
    
    func clear(){
        videoAd?.pause()
        videoAd?.removeFromSuperview()
        videoAd = nil
    }
    

    
}
struct VideoAdDetailView: View {
    @StateObject private var adManager = InstreamVideoAdAdManager()
    @StateObject private var videoContainerHolder = VideoContainerHolder()

    var body: some View {
        VStack {
            VideoContainerView(holder: videoContainerHolder)
                .frame(width: 300, height: 250) // Set desired size

            Button("Show Video Ad") {
                if let container = videoContainerHolder.videoContainer {
                    adManager.showAd(in: container)
                }
            }
        }.onDisappear{
            adManager.clear()
        }
    }
}

struct VideoAdDetailView_Previews: PreviewProvider {
    static var previews: some View {
        VideoAdDetailView()
    }
}

class VideoContainerHolder: ObservableObject {
    var videoContainer: UIView?
}

struct VideoContainerView: UIViewRepresentable {
    @ObservedObject var holder: VideoContainerHolder

    func makeUIView(context: Context) -> UIView {
        let view = UIView(frame: .zero)
        view.backgroundColor = .clear // or any other background color
        DispatchQueue.main.async {
            self.holder.videoContainer = view
        }
        return view
    }

    func updateUIView(_ uiView: UIView, context: Context) {
        // Update the view if needed
    }
}

