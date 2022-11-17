package appnexus.example.composesample

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.MediaController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import appnexus.example.composesample.databinding.VideoAdAdctivtyBinding
import appnexus.example.composesample.ui.theme.ComposeSampleTheme
import com.appnexus.opensdk.*
import com.appnexus.opensdk.instreamvideo.Quartile
import com.appnexus.opensdk.instreamvideo.VideoAd
import com.appnexus.opensdk.instreamvideo.VideoAdLoadListener
import com.appnexus.opensdk.instreamvideo.VideoAdPlaybackListener

class VideoAdActivity : ComponentActivity(), VideoAdLoadListener, VideoAdPlaybackListener {

    // The Ad Video instance.
    // Its important to create this as a Instance variable to make sure its not removed Garbage Collected.
    private val instreamVideoAd: VideoAd by lazy {
        VideoAd(this, Constants.PLACEMENT_ID)
    }
    lateinit var  playBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            // Set the Ad-Load Listener
            instreamVideoAd.adLoadListener = this

            // Set PlayBack Listener.
            instreamVideoAd.videoPlaybackListener = this

            //Load the Ad.
            instreamVideoAd.loadAd()

            ComposeSampleTheme {
                Surface(color = MaterialTheme.colors.background) {

                    Column {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .height(40.dp)
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colors.primary)
                        ) {
                            //Add title
                            Text(text = "Instream Video Ad", color = MaterialTheme.colors.background)
                        }

                        // Content video player.
                        AndroidViewBinding(VideoAdAdctivtyBinding::inflate) {
                            videoPlayer.setVideoURI(Uri.parse("http://acdn.adnxs.com/mobile/video_test/content/Scenario.mp4"))
                            val controller = MediaController(this@VideoAdActivity)

                            videoPlayer.setMediaController(controller)
                            playBtn= playImgBtn
                            playImgBtn.setOnClickListener {
                                if (instreamVideoAd.isReady) {

                                    // Play the VideoAd by passing the container.
                                    instreamVideoAd.playAd(videoContainerRl)

                                } else {
                                    videoPlayer.start()
                                }
                                playImgBtn.visibility = View.INVISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onAdLoaded(p0: VideoAd?) {
        playBtn.visibility= View.VISIBLE
    }

    override fun onAdRequestFailed(p0: VideoAd?, p1: ResultCode?) {
        playBtn.visibility= View.VISIBLE
    }

    override fun onAdPlaying(p0: VideoAd?) {
    }

    override fun onQuartile(p0: VideoAd?, p1: Quartile?) {
    }

    override fun onAdCompleted(p0: VideoAd?, p1: VideoAdPlaybackListener.PlaybackCompletionState?) {
    }

    override fun onAdMuted(p0: VideoAd?, p1: Boolean) {
    }

    override fun onAdClicked(p0: VideoAd?) {
    }

    override fun onAdClicked(p0: VideoAd?, p1: String?) {
    }
}