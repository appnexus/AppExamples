package appnexus.example.composesample

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import appnexus.example.composesample.ui.theme.ComposeSampleTheme
import com.appnexus.opensdk.*

class BannerLazyColumnActivity : ComponentActivity(), AdListener {

    // List of banner ads and member ids list items that populate the LazyColumn.
    private var listDataItems: ArrayList<Any> = ArrayList()

    // List of cached banner ads list that populate the LazyColumn.
    private var cachedBannersList: ArrayList<BannerAdView> = ArrayList()

    // A banner ad is placed in every 50th position in the LazyColumn.
    private val adDisplayPos = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Update the LazyColumn item's list with member ids items and banner ads.
        populateListData()
        cacheBannerAds()

        setContent {
            ComposeSampleTheme {
                Surface(color = MaterialTheme.colors.background) {

                    //Load LazyColumn Data
                    LoadLazyData()
                }
            }
        }
    }

    @Composable
    fun LoadLazyData() {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(1f)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.primary)
                ) {
                    //Add title
                    Text(text = "Banner Lazy Column Ad", color = MaterialTheme.colors.background)
                }
            }
            itemsIndexed(listDataItems) { index, item ->

                if (index % adDisplayPos == 0) {

                    //Get List index
                    val listData : Any = listDataItems[index]

                    //Check if list index is string object or banner object and add banner in list

                    if (listData is BannerAdView) {
                        //On scrolling up check whether visible index has already a BannerAdView then load same banner
                        val bannerHasAd: BannerAdView = listDataItems[index] as BannerAdView
                        DisplayBannerAd(bannerHasAd)
                    }else if(listData is String) {
                        // This is while scrolling down. pop banner from the cached list and add it at the index postion and display the banner ad.
                        val bannerAdView: BannerAdView = pop(cachedBannersList)
                        listDataItems.add(index, bannerAdView)
                        DisplayBannerAd(bannerAdView)
                    }
                } else {
                    MemberIdItemsStyle(listDataItems[index] as String)
                }
            }
        }
    }

    /**
     * Remove old banner ads from the list and add new one.
     * @return
     */
    private fun pop(cachedBannersList: ArrayList<BannerAdView>): BannerAdView {

        // Cache a new banner Ad so that its ready when we come here next time.
        cacheBannerAds()
        return cachedBannersList.removeAt(0)
    }

    /**
     * Display banner ads from the list.
     */
    @Composable
    fun DisplayBannerAd(bannerAdView: BannerAdView) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // The BannerAdView recycled by the LazyColumn may be a different
            // instance than the one used previously for this position. Clear the
            // BannerAdView of any subviews in case it has a different
            // BannerAd associated with it, and make sure the BannerAd for this position doesn't
            // already have a parent of a different recycled BannerAdView.
            if (bannerAdView.parent != null) {
                (bannerAdView.parent as ViewGroup).removeView(bannerAdView)
            }

            //Returns the BannerAd
            AndroidView(factory = { context ->
                bannerAdView.apply {
                }
            })
        }
    }

    /**
     * Display member id from the list.
     */
    @Composable
    fun MemberIdItemsStyle(memberId: String) {

        //Displaying member id in Box style
        Box(
            modifier = Modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp)
                ) {

                    // Text that shows the member id
                    Text(
                        modifier = Modifier
                            .padding(top = 2.dp),
                        text = memberId,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 16.sp,
                        )
                    )
                }
            }
        }
    }

    /**
     * Add banner ads in the cached list.
     */
    private fun cacheBannerAds() {

        // create an instance of banner.
        val bannerAdView = BannerAdView(this)

        // Set a placement id.
        bannerAdView.placementID = Constants.PLACEMENT_ID

        // Get a 300x50 ad.
        bannerAdView.setAdSize(320, 50)

        // Set to 0 to disable auto-refresh.
        bannerAdView.autoRefreshInterval = 0

        // Turning this on so we always get an ad during testing.
        //bannerAdView.shouldServePSAs = true

        // Set whether ads will expand to fit the screen width.
        bannerAdView.expandsToFitScreenWidth = true

        bannerAdView.adListener = this

        //Set ad listener and load ad.
        bannerAdView.loadAd()

        cachedBannersList.add(bannerAdView)
    }

    /**
     * Add 200 member ids in list.
     */
    private fun populateListData() {
        for (i in 1..2000) {
            listDataItems.add("Member Id $i")
        }
    }

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