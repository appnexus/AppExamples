package appnexus.example.composesample

import android.os.Bundle
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
import appnexus.example.composesample.ui.theme.ComposeSampleTheme
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.appnexus.opensdk.*


class BannerNativeActivity : ComponentActivity() {
    private var nativeAdResponse: NativeAdResponse? = null
    private lateinit var composeView: ComposeView
    private lateinit var composeViewClickThrough: ComposeView

    private val bannerAdView: BannerAdView by lazy {
        BannerAdView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bannerAdView.placementID=Constants.PLACEMENT_ID
        bannerAdView.setAdSize(300,250)

        val nativeAdEventListener= object :NativeAdEventListener{
            override fun onAdWasClicked() {
            }

            override fun onAdWasClicked(p0: String?, p1: String?) {
            }

            override fun onAdWillLeaveApplication() {
            }

            override fun onAdImpression() {
            }

            override fun onAdAboutToExpire() {
            }

            override fun onAdExpired() {
            }
        }

        val adListener = object : AdListener {
            override fun onAdLoaded(p0: AdView?) {

            }

            override fun onAdLoaded(p0: NativeAdResponse?) {
                showToast("Add has been loaded successfully.")

                nativeAdResponse = p0

                composeViewClickThrough = ComposeView(this@BannerNativeActivity)
                composeViewClickThrough.apply {
                    setContent {
                        Button(
                            onClick = {

                            },
                            enabled = true,
                            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            nativeAdResponse?.callToAction?.let { Text(text = it, color = Color.White) }
                        }
                    }
                }
                composeView.setContent {

                    Row() {
                        Image(
                            painter = rememberImagePainter(nativeAdResponse?.iconUrl),
                            contentDescription = null,
                            modifier = Modifier.size(128.dp)
                        )

                        Column {
                            Text(text = " ${nativeAdResponse?.title}")
                            Text(text = " ${nativeAdResponse?.description}")
                            Text(text = " ${nativeAdResponse?.sponsoredBy}")
                            Image(painter =  rememberImagePainter(data = nativeAdResponse?.imageUrl), contentDescription = "Add Image")
                            Image(painter =  rememberImagePainter(data = nativeAdResponse?.iconUrl), contentDescription = "Add Image")


                            AndroidView(factory = {
                                composeViewClickThrough.apply { }
                            })


                        }


                    }

                }


                NativeAdSDK.unRegisterTracking(composeView)
                NativeAdSDK.registerTracking(
                    nativeAdResponse,
                    composeView,
                    nativeAdEventListener

                )



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
        bannerAdView.allowNativeDemand = true
        bannerAdView.setAllowBannerDemand(false)
        bannerAdView.loadAd()

        setContent {
            composeView = ComposeView(this)

            ComposeSampleTheme {
                // A surface container using the 'background' color from the theme
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
                            Text(text = " Banner Native Ad", color = MaterialTheme.colors.background)


                        }
                        AndroidView(factory = {
                            composeView.apply {
                                setContent {
                                    Text(text = "Loading Ad...")

                                }
                            }
                        })

                    }


                }
            }
        }



    }


    override fun onDestroy() {
        NativeAdSDK.unRegisterTracking(composeView)

        super.onDestroy()

    }
}