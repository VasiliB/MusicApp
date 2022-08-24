package com.example.musicapp


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import coil.load
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.retrofit.RetrofitHelper
import com.example.musicapp.retrofit.TracksAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException


class MainActivity : AppCompatActivity(), View.OnClickListener,
    View.OnTouchListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID"
    }

    private lateinit var binding: ActivityMainBinding
    private val scopeMain = CoroutineScope(Dispatchers.Main)
    private val scopeIO = CoroutineScope(Dispatchers.IO)
    private val retrofitHelper = RetrofitHelper()
    private val handler = Handler()
    private var mediaPlayer = MediaPlayer()

    private lateinit var title: String
    private lateinit var artist: String
    private var trackNumber = 1
    private var tracksQuantity = 1
    private var trackLengthInMs: Int? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mediaPlayer.setOnCompletionListener(this)

        val seekBarProgress: SeekBar =  findViewById<View>(R.id.SeekBarTestPlay) as SeekBar
        seekBarProgress.max = 99 // It means 100% .0-99
        seekBarProgress.setOnTouchListener(this)

        scopeIO.launch {
            getJsonResult()
//            tracksQuantity = getJsonResult().body()?.size!!
            //Update your UI
        }

        fun getTracksQuantity(my_result: Response<List<Track>>): Int {
            tracksQuantity = my_result.body()?.size!!
            return tracksQuantity
        }

        scopeMain.launch { getTracksQuantity(getJsonResult()) }

        // Notification intent
        val intent = Intent(this, MainActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        fun trackNotification() {
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(getString(R.string.notification_content_title))
                .setContentText("$artist-$title")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)

            with(NotificationManagerCompat.from(this)) {
                notify(NOTIFICATION_ID, builder.build()) // посылаем уведомление
            }
        }




        //Play Button click listener
        binding.playBtn.setOnClickListener { // calling method to play audio.
            scopeMain.launch { setName(getJsonResult()) }
            scopeMain.launch { playTrack(getJsonResult(), seekBarProgress) }
            scopeMain.launch { loadCover(getJsonResult()) }

            trackLengthInMs = mediaPlayer.duration
//            primarySeekBarProgressUpdater(seekBarProgress, trackLengthInMs!!)
            trackNotification()
        }

        //Pause Button click listener
        binding.pauseBtn.setOnClickListener {
            // checking the media player
            // if the audio is playing or not.
            pauseTrack()
        }

        //Stop Button click listener
        binding.stopBtn.setOnClickListener {
            // checking the media player
            // if the audio is playing or not.
            stopTrack()
        }


        binding.nextBtn.setOnClickListener { // calling method to play audio.
            stopTrack()
            if (trackNumber < tracksQuantity - 1)
                trackNumber++
            else trackNumber = 0
            scopeMain.launch { setName(getJsonResult()) }
            scopeMain.launch { playTrack(getJsonResult(), seekBarProgress) }
            scopeMain.launch { loadCover(getJsonResult()) }
        }

        //Previous Button click listener
        binding.previousBtn.setOnClickListener {
            stopTrack()
            if (trackNumber > 0)
                trackNumber--
            else trackNumber = tracksQuantity - 1
            scopeMain.launch { setName(getJsonResult()) }
            scopeMain.launch { playTrack(getJsonResult(), seekBarProgress) }
            scopeMain.launch { loadCover(getJsonResult()) }
        }
    }


    private suspend fun playTrack(json: Response<List<Track>>, seekBarProgress: SeekBar) = withContext(Dispatchers.IO) {

        val trackUrl = json.body()!![trackNumber].trackUri.toString()

        // initializing media player
//        mediaPlayer = MediaPlayer()

        // below line is use to set the audio
        // stream type for our media player.
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(trackUrl)
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare()
                        mediaPlayer.start()

            trackLengthInMs = mediaPlayer.duration

             fun primarySeekBarProgressUpdater(seekBarProgress: SeekBar, trackLengthInMs: Int) {
                seekBarProgress.progress =
                    (mediaPlayer.currentPosition.toFloat() / trackLengthInMs * 100).toInt() // This math construction give a percentage of "was playing"/"song length"
                if (mediaPlayer.isPlaying) {
                    val notification =
                        Runnable { primarySeekBarProgressUpdater(seekBarProgress, trackLengthInMs) }
                    handler.postDelayed(notification, 1000)
                }
            }
            primarySeekBarProgressUpdater(seekBarProgress, trackLengthInMs!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        // below line is use to display a toast message.
//        Toast.makeText(this@MainActivity, "Audio started playing..", Toast.LENGTH_SHORT).show()

    }

    private fun pauseTrack() {
        if (mediaPlayer.isPlaying)
            // pausing the media player if media player
            // is playing we are calling below line to
            // stop our media player.
            mediaPlayer.pause()

//            // below line is to display a message
//            // when media player is paused.
//            Toast.makeText(this@MainActivity, "Audio has been paused", Toast.LENGTH_SHORT)
//                .show()
//        } else {
//            // this method is called when media
//            // player is not playing.
//            Toast.makeText(this@MainActivity, "Audio has not played", Toast.LENGTH_SHORT).show()
//        }
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

    private fun loadCover(json: Response<List<Track>>) {

        val bitmapUri = json.body()!![trackNumber].bitmapUri
        val cover = binding.cover

        try {
            cover.load(bitmapUri) {
                crossfade(true)
                placeholder(R.drawable.benbois_vinyl_records)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setName(json: Response<List<Track>>) {

        artist = json.body()!![trackNumber].artist
        title = json.body()!![trackNumber].title

        binding.artist.text = artist
        binding.title.text = title

        val intent = Intent(this, MainActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.r5d_cassette_jolly_roger)
            .setContentTitle(getString(R.string.notification_content_title))
            .setContentText("$artist-$title")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build()) // посылаем уведомление
        }

    }

    private suspend fun getJsonResult(): Response<List<Track>> = withContext(Dispatchers.IO) {
        val tracksApi = retrofitHelper.getInstance().create(TracksAPI::class.java)
        val result = tracksApi.getTracks()

        if (result != null) {
            Log.d("Playlist result: ", result.body()!![0].title)
        }
        return@withContext result
    }

//    private suspend fun getTracks () = withContext(Dispatchers.IO)  {
//        val result = tracksApi.getTracks()
//        lol = result
//        if (result != null) {
//            Log.d("Playlist result: ", result.body()!![0].title.toString())
//        }
//    }

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

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }



    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (v.id == R.id.SeekBarTestPlay) {
            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position */
            if (mediaPlayer.isPlaying) {
                val sb = v as SeekBar
                val playPositionInMillisecconds = trackLengthInMs!! / 100 * sb.progress
                mediaPlayer.seekTo(playPositionInMillisecconds)
            }
        }
        return false
    }

        /** Method which updates the SeekBar primary progress by current song playing position */
//    private fun primarySeekBarProgressUpdater(seekBarProgress: SeekBar, trackLengthInMs: Int) {
//        seekBarProgress.progress =
//            (mediaPlayer.currentPosition.toFloat() / trackLengthInMs * 100).toInt() // This math construction give a percentage of "was playing"/"song length"
//        if (mediaPlayer.isPlaying) {
//            val notification = Runnable { primarySeekBarProgressUpdater(seekBarProgress, trackLengthInMs) }
//            handler.postDelayed(notification, 1000)
//        }
//    }

    override fun onCompletion(mP: MediaPlayer?) {
//        TODO("Not yet implemented")
//        if (trackNumber < tracksQuantity - 1)
//            trackNumber++
//        else trackNumber = 0
//        scopeMain.launch { setName(getJsonResult()) }
//        scopeMain.launch { playTrack(getJsonResult(), seekBarProgress) }
//        scopeMain.launch { loadCover(getJsonResult()) }
    }

    override fun onBufferingUpdate(p0: MediaPlayer?, p1: Int) {
        TODO("Not yet implemented")
    }

}
//
//import com.hrupin.media.R
//
//
//class StreamingMp3Player : Activity(), View.OnClickListener,
//    OnTouchListener, OnCompletionListener, OnBufferingUpdateListener {
//    private var buttonPlayPause: ImageButton? = null
//    private var seekBarProgress: SeekBar? = null
//    var editTextSongURL: EditText? = null
//    private var mediaPlayer: MediaPlayer? = null
//    private var mediaFileLengthInMilliseconds // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class
//            = 0
//    private val handler = Handler()
//
//    /** Called when the activity is first created.  */
//    public override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.main)
//        initView()
//    }
//
//    /** This method initialise all the views in project */
//    private fun initView() {
//        buttonPlayPause = findViewById<View>(R.id.ButtonTestPlayPause) as ImageButton
//        buttonPlayPause!!.setOnClickListener(this)
//        seekBarProgress = findViewById<View>(R.id.SeekBarTestPlay) as SeekBar
//        seekBarProgress!!.max = 99 // It means 100% .0-99
//        seekBarProgress!!.setOnTouchListener(this)
//        editTextSongURL = findViewById<View>(R.id.EditTextSongURL) as EditText
//        editTextSongURL.setText(R.string.testsong_20_sec)
//        mediaPlayer = MediaPlayer()
//        mediaPlayer!!.setOnBufferingUpdateListener(this)
//        mediaPlayer!!.setOnCompletionListener(this)
//    }
//
//    /** Method which updates the SeekBar primary progress by current song playing position */
//    private fun primarySeekBarProgressUpdater() {
//        seekBarProgress!!.progress =
//            (mediaPlayer!!.currentPosition.toFloat() / mediaFileLengthInMilliseconds * 100).toInt() // This math construction give a percentage of "was playing"/"song length"
//        if (mediaPlayer!!.isPlaying) {
//            val notification = Runnable { primarySeekBarProgressUpdater() }
//            handler.postDelayed(notification, 1000)
//        }
//    }
//
//    override fun onClick(v: View) {
//        if (v.id == R.id.ButtonTestPlayPause) {
//            /** ImageButton onClick event handler. Method which start/pause mediaplayer playing  */
//            try {
//                mediaPlayer!!.setDataSource(editTextSongURL!!.text.toString()) // setup song from https://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
//                mediaPlayer!!.prepare() // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            mediaFileLengthInMilliseconds =
//                mediaPlayer!!.duration // gets the song length in milliseconds from URL
//            if (!mediaPlayer!!.isPlaying) {
//                mediaPlayer!!.start()
//                buttonPlayPause!!.setImageResource(R.drawable.button_pause)
//            } else {
//                mediaPlayer!!.pause()
//                buttonPlayPause!!.setImageResource(R.drawable.button_play)
//            }
//            primarySeekBarProgressUpdater()
//        }
//    }
//
//    override fun onTouch(v: View, event: MotionEvent): Boolean {
//        if (v.id == R.id.SeekBarTestPlay) {
//            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position */
//            if (mediaPlayer!!.isPlaying) {
//                val sb = v as SeekBar
//                val playPositionInMillisecconds = mediaFileLengthInMilliseconds / 100 * sb.progress
//                mediaPlayer!!.seekTo(playPositionInMillisecconds)
//            }
//        }
//        return false
//    }
//
//    override fun onCompletion(mp: MediaPlayer) {
//        /** MediaPlayer onCompletion event handler. Method which calls then song playing is complete */
//        buttonPlayPause!!.setImageResource(R.drawable.button_play)
//    }
//
//    override fun onBufferingUpdate(mp: MediaPlayer, percent: Int) {
//        /** Method which updates the SeekBar secondary progress by current song loading from URL position */
//        seekBarProgress!!.secondaryProgress = percent
//    }
//}