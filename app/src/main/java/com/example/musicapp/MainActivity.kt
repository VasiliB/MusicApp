package com.example.musicapp


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import coil.load
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.retrofit.RetrofitHelper
import com.example.musicapp.retrofit.TracksAPI
import kotlinx.coroutines.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID"
    }

    // creating a variable for
    // button and media player
    private lateinit var binding: ActivityMainBinding
    private val scope = CoroutineScope(Dispatchers.Main)
    private val retrofitHelper = RetrofitHelper()

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
        createNotificationChannel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val tracksApi = retrofitHelper.getInstance().create(TracksAPI::class.java)
        // launching a new coroutine
        GlobalScope.launch {
            val result = tracksApi.getTracks()
            Log.d("ayush:? ", result.body()!![0].title.toString())
        }


        // initializing our buttons
        val intent = Intent(this, MainActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        binding.previousBtn.setOnClickListener {
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Напоминание")
                .setContentText("Пора покормить кота")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)

            with(NotificationManagerCompat.from(this)) {
                notify(NOTIFICATION_ID, builder.build()) // посылаем уведомление
            }

        }

//        activityMainBinding = ActivityMainBinding

        // setting on click listener for our play and pause buttons.
        binding.playBtn.setOnClickListener { // calling method to play audio.
            scope.launch { playTrack() }
            scope.launch { loadCover() }
            setName()

//            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.sym_def_app_icon)
//                .setContentTitle("Title")
//                .setContentText("Notification text")
//
//            val notification: Notification = builder.build()
//
//            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.notify(1, notification)

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

    private fun stopTrack() {
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

// Create Notification Channel

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
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
