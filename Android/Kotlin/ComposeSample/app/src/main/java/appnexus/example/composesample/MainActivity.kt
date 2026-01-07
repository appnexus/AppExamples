package appnexus.example.composesample

import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import appnexus.example.composesample.ui.theme.ComposeSampleTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        setContent {
            ComposeSampleTheme {

                //Add items in list.
                val adTypes= listOf("Simple Banner", "Native Ad", "Instream Video", "Banner Native Ad", "Banner Video Ad", "Banner LazyColumn")
                val adTypeList by  remember { mutableStateOf(adTypes) }
                Surface(color = MaterialTheme.colors.background) {

                    //Add click listener on list.
                    SelectAdTypeView(adTypeList)
                }
            }
        }
    }
}
