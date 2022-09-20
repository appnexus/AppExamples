package appnexus.example.composesample

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp

@Composable
fun SelectAdTypeView(mediaTypeList: List<String>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = MaterialTheme.colors.primary)
            ) {
                Text(text = "Ad Type", color = Color.White)
            }
        }

        items(mediaTypeList.size) { media ->

            AdTypeRow(
                modifier = Modifier,
                adType = mediaTypeList[media],
            )
            Divider(color = Color.Black)


        }


    }


}


@Composable
fun AdTypeRow(modifier: Modifier = Modifier, adType: String) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth().padding(horizontal = 10.dp)
            .clickable {

                when (adType) {
                    AppDestinations.Interstitial -> {

                    }
                    AppDestinations.SimpleBanner -> {
                        context.startActivity(Intent(context, SimpleBannerActivity::class.java))


                    }
                    AppDestinations.NativeAd -> {
                        context.startActivity(Intent(context, NativeAdActivity::class.java))


                    }
                    AppDestinations.VideoAd -> {
                        context.startActivity(Intent(context, VideoAdActivity::class.java))

                    }
                    AppDestinations.BannerNative -> {
                        context.startActivity(Intent(context, BannerNativeActivity::class.java))

                    }
                    AppDestinations.BannerVideo->{
                        context.startActivity(Intent(context, BannerVideoActivity::class.java))

                    }


                }

            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = adType,
        )

    }

}