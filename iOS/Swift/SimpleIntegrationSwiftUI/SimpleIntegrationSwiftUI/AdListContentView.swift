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


struct AdListContentView: View {
    let adTypes = ["BannerAd", "InterstitialAd", "NativeAd", "VideoAd"]

    var body: some View {
        NavigationView {
            List {
                ForEach(adTypes, id: \.self) { adType in
                    NavigationLink(destination: detailView(for: adType)) {
                        Text(adType)
                    }
                }
            }
            .navigationTitle("Ad Types")
        }
    }

    @ViewBuilder
    private func detailView(for adType: String) -> some View {
        switch adType {
        case "BannerAd":
            BannerAdDetailView()
        case "InterstitialAd":
            InterstitialAdDetailView()
        case "NativeAd":
            NativeAdDetailView()
        case "VideoAd":
            VideoAdDetailView()
        default:
            Text("Unknown Ad Type")
        }
    }
}







#Preview {
    AdListContentView()
}
