package appnexus.example.composesample

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import appnexus.example.composesample.databinding.VideoAdAdctivtyBinding
import appnexus.example.composesample.ui.theme.ComposeSampleTheme
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.appnexus.opensdk.*
import com.appnexus.opensdk.instreamvideo.Quartile
import com.appnexus.opensdk.instreamvideo.VideoAd
import com.appnexus.opensdk.instreamvideo.VideoAdPlaybackListener


class BannerVideoActivity : ComponentActivity() {

    private val bannerAdView: BannerAdView by lazy {
        BannerAdView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        bannerAdView.placementID=Constants.PLACEMENT_ID
        bannerAdView.setAdSize(300,250)



        val adListener= object: AdListener{
            override fun onAdLoaded(p0: AdView?) {

            }

            override fun onAdLoaded(p0: NativeAdResponse?) {

            }

            override fun onAdRequestFailed(p0: AdView?, p1: ResultCode?) {
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
        bannerAdView.allowVideoDemand = true
        bannerAdView.setAllowBannerDemand(false)

        setContent {


            ComposeSampleTheme {
                Surface(color = MaterialTheme.colors.background) {

                    Column {


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
    }

}

