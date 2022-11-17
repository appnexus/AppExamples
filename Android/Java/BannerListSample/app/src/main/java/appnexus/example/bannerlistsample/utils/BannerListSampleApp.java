package appnexus.example.bannerlistsample.utils;

import android.app.Application;
import android.widget.Toast;
import com.appnexus.opensdk.InitListener;
import com.appnexus.opensdk.XandrAd;

public class BannerListSampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize the XandrAd SDK with your member id
        XandrAd.init(10094, getApplicationContext(), true, new InitListener() {
            @Override
            public void onInitFinished() {
                Toast.makeText(getApplicationContext(), "Init Finished", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
