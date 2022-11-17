package appnexus.example.bannerlistsample.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import com.appnexus.opensdk.BannerAdView;
import java.util.List;
import appnexus.example.bannerlistsample.R;

public class ListViewAdapter extends ArrayAdapter<Object> {

    // A menu item view type.
    private static final int MEMBER_ID_ITEM_VIEW_TYPE = 0;

    // The banner ad view type.
    private static final int BANNER_AD_VIEW_TYPE = 1;

    // An Activity's Context.
    private final Context context;

    // The list of banner ads and member id items.
    private final List<Object> listViewItems;

    // invoke the suitable constructor of the Adapter class
    public ListViewAdapter(@NonNull Context context, List<Object> listViewItems) {
        super(context, 0, listViewItems);

        this.context = context;
        this.listViewItems = listViewItems;
    }

    /**
     * Returns list items count.
     */
    @Override
    public int getCount() {

        return listViewItems.size();
    }

    /**
     * Returns info which layout(view) type you should use based on position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * Returns info how many types of rows do you have in your list
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    /**
     * Perform modulus operation
     * If value is 0 then return type as banner ad else return member id view.
     * @return
     */
    public int getItemPosition(int position)
    {
        return (position % ListViewActivity.AD_DISPLAY_POS == 0) ? BANNER_AD_VIEW_TYPE
                : MEMBER_ID_ITEM_VIEW_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MemberIdViewHolder memberIdViewHolder = null;
        BannerViewHolder bannerViewHolder = null;

        int type = getItemPosition(position);
        switch (type) {

            //If view type is banner ad
            case BANNER_AD_VIEW_TYPE:

                if (convertView == null) {

                    //Insert banner ad layout.
                    convertView = LayoutInflater.from(context).inflate(R.layout.banner_ad_container, parent, false);
                    bannerViewHolder = new BannerViewHolder();
                    convertView.setTag(bannerViewHolder);

                    //Get banner ad position from list
                    BannerAdView bannerAdView = (BannerAdView) listViewItems.get(position);
                    bannerViewHolder.adCardView = convertView.findViewById(R.id.ad_card_view);

                    // The BannerViewHolder recycled by the ListView may be a different
                    // instance than the one used previously for this position. Clear the
                    // BannerViewHolder of any subviews in case it has a different
                    // BannerAd associated with it, and make sure the BannerAd for this position doesn't
                    // already have a parent of a different recycled BannerViewHolder.
                    if (bannerViewHolder.adCardView.getParent() != null) {
                        ((ViewGroup) bannerViewHolder.adCardView.getParent()).removeView(bannerAdView);
                    }
                    // Add the banner ad to the ad view.
                    bannerViewHolder.adCardView.addView(bannerAdView);
                }
                else {
                    bannerViewHolder = (BannerViewHolder) convertView.getTag();
                }
                break;

            //If view type is member id
            case MEMBER_ID_ITEM_VIEW_TYPE:

                if (convertView == null) {

                    //Insert member id layout.
                    convertView = LayoutInflater.from(context).inflate(R.layout.member_id_item_container, parent, false);
                    memberIdViewHolder = new MemberIdViewHolder();
                    convertView.setTag(memberIdViewHolder);

                    //Set member id from list into textview
                    memberIdViewHolder.memberIdItemText = convertView.findViewById(R.id.member_id);
                    memberIdViewHolder.memberIdItemText.setText((String) listViewItems.get(position));
                }
                else {
                    memberIdViewHolder = (MemberIdViewHolder) convertView.getTag();
                }
                break;
        }
        return convertView;
    }

    /**
     * The {@link BannerViewHolder} class.
     */
    protected static class BannerViewHolder {
        private CardView adCardView;
    }

    /**
     * The {@link MemberIdViewHolder} class.
     * Provides a reference to each view in the member id item view.
     */
    protected static class MemberIdViewHolder {
        private TextView memberIdItemText;
    }
}
