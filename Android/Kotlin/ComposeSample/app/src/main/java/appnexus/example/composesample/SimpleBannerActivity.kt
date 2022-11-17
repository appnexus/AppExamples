package appnexus.example.composesample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import appnexus.example.composesample.ui.theme.ComposeSampleTheme
import com.appnexus.opensdk.*

class SimpleBannerActivity : ComponentActivity() {
  private  val bannerAdView: BannerAdView by lazy {
        BannerAdView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set a placement id.
        bannerAdView.placementID = Constants.PLACEMENT_ID
        bannerAdView.autoRefreshInterval = 60000
        bannerAdView.loadAd(Constants.PLACEMENT_ID, 300, 250)

        //Set ad listener
        val adListener = object : AdListener {
            override fun onAdLoaded(p0: AdView?) {
                showToast("Ad has been loaded successfully.")
            }

            override fun onAdLoaded(p0: NativeAdResponse?) {
                showToast("Ad has been loaded successfully.")
            }

            override fun onAdRequestFailed(p0: AdView?, p1: ResultCode?) {
                showToast("${p1?.message}")
            }

            override fun onAdExpanded(p0: AdView?) {
            }

            override fun onAdCollapsed(p0: AdView?) {
            }

            override fun onAdClicked(p0: AdView?) {
            }

            override fun onAdClicked(p0: AdView?, p1: String?) {
            }

            override fun onLazyAdLoaded(p0: AdView?) {
            }
        }
        bannerAdView.adListener = adListener

        setContent {
            ComposeSampleTheme {
                Surface(color = MaterialTheme.colors.background) {

                    Column() {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .height(40.dp)
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colors.primary)
                        ) {
                            //Add title
                            Text(text = "Simple Banner Ad", color = MaterialTheme.colors.background)
                        }
                        AndroidView(factory = { context ->
                            bannerAdView.apply {
                                this.loadAd()
                            }
                        })
                    }
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        bannerAdView.activityOnDestroy()
    }
}