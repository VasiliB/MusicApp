package com.example.musicapp

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.musicapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class MainActivity : AppCompatActivity() {

    // creating a variable for
    // button and media player
    private lateinit var binding: ActivityMainBinding
    private val scope = CoroutineScope(Dispatchers.Main)

    //    var previousBtn: Button? = null
//    var playBtn = null
//    var pauseBtn = null
//    var stopBtn: Button? = null
//    var nextBtn: Button? = null
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var title: String
    private lateinit var artist: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


//        fun onResponse(call: Call, response: Response) {
//            val content = response.body?.string() ?: return
//            val json = Json { ignoreUnknownKeys = true }
//            val tracks = json.decodeFromString<List<TrackResponse>>(content)
//        }

        // initializing our buttons


//        activityMainBinding = ActivityMainBinding

        // setting on click listener for our play and pause buttons.
        binding.playBtn.setOnClickListener { // calling method to play audio.
            scope.launch { playTrack() }
            scope.launch { loadCover() }
            setName()
        }
        binding.pauseBtn.setOnClickListener {
            // checking the media player
            // if the audio is playing or not.
            stopTrack()
        }
    }

    private suspend fun playTrack() = withContext(Dispatchers.IO) {
        val trackUrl =
            "https://music-2021.ru/uploads/files/2021-04/1619469591_rick-astley-never-gonna-give-you-up.mp3"

        // initializing media player
        mediaPlayer = MediaPlayer()

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(trackUrl)
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        // below line is use to display a toast message.
        Toast.makeText(this@MainActivity, "Audio started playing..", Toast.LENGTH_SHORT).show()

    }

     private fun stopTrack()  {
        if (mediaPlayer.isPlaying) {
            // pausing the media player if media player
            // is playing we are calling below line to
            // stop our media player.
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()

            // below line is to display a message
            // when media player is paused.
            Toast.makeText(this@MainActivity, "Audio has been paused", Toast.LENGTH_SHORT)
                .show()
        } else {
            // this method is called when media
            // player is not playing.
            Toast.makeText(this@MainActivity, "Audio has not played", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun loadCover() = withContext(Dispatchers.IO) {

        val coverUrl = "https://i1.sndcdn.com/artworks-qyrckKJE1mdut7kS-6IJvzQ-t500x500.jpg"
        val cover = binding.cover

        try {
            cover.load(coverUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun setName() {
        artist = "Rick Astley"
        title = "Never gonna give you up"

        binding.artist.text = artist
        binding.title.text = title

    }


}

//
//class MainActivity : AppCompatActivity() {
//    // creating a variable for
//    // button and media player
//    var playBtn: Button? = null
//    var pauseBtn: Button? = null
//    var mediaPlayer: MediaPlayer? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // initializing our buttons
//        playBtn = findViewById(R.id.idBtnPlay)
//        pauseBtn = findViewById(R.id.idBtnPause)
//
//        // setting on click listener for our play and pause buttons.
//        playBtn.setOnClickListener(View.OnClickListener { // calling method to play audio.
//            playAudio()
//        })
//        pauseBtn.setOnClickListener(View.OnClickListener {
//            // checking the media player
//            // if the audio is playing or not.
//            if (mediaPlayer!!.isPlaying) {
//                // pausing the media player if media player
//                // is playing we are calling below line to
//                // stop our media player.
//                mediaPlayer!!.stop()
//                mediaPlayer!!.reset()
//                mediaPlayer!!.release()
//
//                // below line is to display a message
//                // when media player is paused.
//                Toast.makeText(this@MainActivity, "Audio has been paused", Toast.LENGTH_SHORT)
//                    .show()
//            } else {
//                // this method is called when media
//                // player is not playing.
//                Toast.makeText(this@MainActivity, "Audio has not played", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun playAudio() {
//        val audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
//
//        // initializing media player
//        mediaPlayer = MediaPlayer()
//
//        // below line is use to set the audio
//        // stream type for our media player.
//        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
//
//        // below line is use to set our
//        // url to our media player.
//        try {
//            mediaPlayer!!.setDataSource(audioUrl)
//            // below line is use to prepare
//            // and start our media player.
//            mediaPlayer!!.prepare()
//            mediaPlayer!!.start()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        // below line is use to display a toast message.
//        Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show()
//    }
//}
