package appnexus.example.bannerlistsample.listview;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.appnexus.opensdk.AdListener;
import com.appnexus.opensdk.AdView;
import com.appnexus.opensdk.BannerAdView;
import com.appnexus.opensdk.NativeAdResponse;
import com.appnexus.opensdk.ResultCode;
import java.util.ArrayList;
import java.util.List;
import appnexus.example.bannerlistsample.R;
import appnexus.example.bannerlistsample.utils.Constants;

public class ListViewActivity extends AppCompatActivity {

    // A banner ad is placed in every 11th position in the ListView.
    public static final int AD_DISPLAY_POS = 11;

    // List of banner ads and member ids list items that populate the ListView.
    private final List<Object> listViewItems = new ArrayList<>();

    //ListView adapter.
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_activity);

        ListView listView = findViewById(R.id.list_view);

        // Update the ListView item's list with member ids items and banner ads.
        addMemberId();
        addBannerAds();

        //Specify an adapter.
        adapter = new ListViewAdapter(this, listViewItems);
        listView.setAdapter(adapter);

    }

    /**
     * Adds banner ads to the items list.
     */
    private void addBannerAds() {
        // Loop through the items array and place a new banner ad in every ith position in
        // the items List.
        for (int i = 0; i <= listViewItems.size(); i += AD_DISPLAY_POS) {

            // create an instance of banner.
            final BannerAdView bannerAdView = new BannerAdView(this);

            // Set a placement id.
            bannerAdView.setPlacementID(Constants.PLACEMENT_ID);

            // Get a 300x50 ad.
            bannerAdView.setAdSize(300, 50);

            // Set to 0 to disable auto-refresh.
            bannerAdView.setAutoRefreshInterval(0);

            // Turning this on so we always get an ad during testing.
            bannerAdView.setShouldServePSAs(true);

            // Set whether ads will expand to fit the screen width.
            bannerAdView.setExpandsToFitScreenWidth(true);

            //Set ad listener and load ad.
            loadBannerAd(bannerAdView);

            //Add banner ads in list.
            listViewItems.add(i, bannerAdView);
        }
    }

    /**
     * Adds listener and load ad.
     */
    private void loadBannerAd(BannerAdView loadBannerAdView) {

        // Set up a listener on this ad view that logs events.
        loadBannerAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded(AdView adView) {}

            @Override
            public void onAdLoaded(NativeAdResponse nativeAdResponse) {}

            @Override
            public void onAdRequestFailed(AdView adView, ResultCode resultCode) {}

            @Override
            public void onAdExpanded(AdView adView) {}

            @Override
            public void onAdCollapsed(AdView adView) {}

            @Override
            public void onAdClicked(AdView adView) {}

            @Override
            public void onAdClicked(AdView adView, String s) {}

            @Override
            public void onLazyAdLoaded(AdView adView) {}

            @Override
            public void onAdImpression(AdView adView) {}
        });

        // Load the banner ad.
        loadBannerAdView.loadAd();
    }

    /**
     * Add 20 member ids in list.
     */
    private void addMemberId() {
        for (int i = 1; i <= 20; i++) {
            listViewItems.add("Member Id " + i);
        }
    }
}
