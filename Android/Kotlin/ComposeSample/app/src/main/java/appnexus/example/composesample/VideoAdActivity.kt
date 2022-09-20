package appnexus.example.composesample

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.MediaController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.viewinterop.AndroidViewBinding
import appnexus.example.composesample.databinding.VideoAdAdctivtyBinding
import appnexus.example.composesample.ui.theme.ComposeSampleTheme
import com.appnexus.opensdk.*
import com.appnexus.opensdk.instreamvideo.Quartile
import com.appnexus.opensdk.instreamvideo.VideoAd
import com.appnexus.opensdk.instreamvideo.VideoAdLoadListener
import com.appnexus.opensdk.instreamvideo.VideoAdPlaybackListener

class VideoAdActivity : ComponentActivity(), VideoAdLoadListener, VideoAdPlaybackListener {

    private val instreamVideoAd: VideoAd by lazy {
        VideoAd(this, Constants.PLACEMENT_ID)
    }
    lateinit var  playBtn: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            instreamVideoAd.adLoadListener = this
            instreamVideoAd.videoPlaybackListener = this
            instreamVideoAd.loadAd()

            ComposeSampleTheme {
                Surface(color = MaterialTheme.colors.background) {

                    Column {


                        AndroidViewBinding(VideoAdAdctivtyBinding::inflate) {
                            videoPlayer.setVideoURI(Uri.parse("http://acdn.adnxs.com/mobile/video_test/content/Scenario.mp4"))
                            val controller = MediaController(this@VideoAdActivity)

                            videoPlayer.setMediaController(controller)
                            playBtn= playImgBtn
                            playImgBtn.setOnClickListener {
                                if (instreamVideoAd.isReady) {
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

    override fun onDestroy() {
        super.onDestroy()
    }


}