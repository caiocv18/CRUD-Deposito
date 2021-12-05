package br.com.projetorecuperacao.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import br.com.projetorecuperacao.R

class VideoViewActivity : AppCompatActivity() {
    private var mediaController: MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)
        runVideo()
    }

    fun runVideo(){
        val videoView : VideoView = findViewById(R.id.activity_video_view)
        videoView.setVideoPath("https://media.istockphoto.com/videos/falling-dollar-banknotes-in-4k-loopable-video-id826661764")
        mediaController = MediaController(this)
        mediaController?.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()
    }
}