package br.com.projetorecuperacao.ui.activity

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import br.com.projetorecuperacao.R

//Classe activity para visualização do conteúdo de áudio multimidia
class AudioViewActivity : AppCompatActivity() {
    var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_view)
        playAudio()
        stopAudio()
    }

    //Método para reproduzir o áudio
    fun playAudio(){
        val button : Button = findViewById(R.id.button_play)
        button.setOnClickListener{
            mediaPlayer = MediaPlayer.create(this, Uri.parse("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"))
            mediaPlayer?.start()
        }
    }

    //Método para parar o áudio
    fun stopAudio(){
        val button : Button = findViewById(R.id.button_stop)
        button.setOnClickListener{
            mediaPlayer?.stop()
        }
    }
}