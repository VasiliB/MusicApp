package com.example.musicapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.R
import com.example.musicapp.model.Track
import com.example.musicapp.utils.Status
import com.example.musicapp.view_model.TrackViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel : TrackViewModel by viewModel()
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.users.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<Track>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

}


//trackViewModel.data.observe(this, Observer {
//    // Todo: Populate the recyclerView here
//    it.forEach { track ->
//        Toast.makeText(baseContext, track.title, Toast.LENGTH_SHORT).show()
//    }
//})
//
//trackViewModel.loadingState.observe(this, Observer {
//    when (it.status) {
//        LoadingState.Status.FAILED -> Toast.makeText(
//            baseContext,
//            it.msg,
//            Toast.LENGTH_SHORT
//        ).show()
//        LoadingState.Status.RUNNING -> Toast.makeText(
//            baseContext,
//            "Loading",
//            Toast.LENGTH_SHORT
//        ).show()
//        LoadingState.Status.SUCCESS -> Toast.makeText(
//            baseContext,
//            "Success",
//            Toast.LENGTH_SHORT
//        ).show()
//    }
//})
//}

//
//class MainActivity : AppCompatActivity(), View.OnClickListener,
//    View.OnTouchListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {
//
//    companion object {
//        const val NOTIFICATION_ID = 101
//        const val CHANNEL_ID = "channelID"
//    }
//
//    private lateinit var binding: ActivityMainBinding
//    private val scopeMain = CoroutineScope(Dispatchers.Main)
//    private val scopeIO = CoroutineScope(Dispatchers.IO)
//    private val retrofitHelper = RetrofitHelper()
//    private val handler = Handler()
//    private var mediaPlayer = MediaPlayer()
//
//    private lateinit var configuration: Response<List<Track>>
//    private lateinit var title: String
//    private lateinit var artist: String
//    private var trackNumber = 2
//    private var tracksQuantity = 1
//    private var trackLengthInMs: Int? = null
//    private lateinit var bitmapUri: String
//
//    //    private var cover = binding.cover
//    private lateinit var trackUrl: String
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        createNotificationChannel()
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)
//
////        scopeIO.launch {
////            configuration = loadConfiguration()
////            tracksQuantity = configuration.body()?.size!!
////        }
//
//        scopeMain.launch {
//            bind(getTrack(trackNumber))
//        }
//
//        mediaPlayer.setOnCompletionListener(this)
//
//        val seekBarProgress: SeekBar = binding.SeekBarTestPlay
//        seekBarProgress.max = 99 // It means 100% .0-99
//        seekBarProgress.setOnTouchListener(this)
//
//
//        fun getTracksQuantity(json: Response<List<Track>>): Int {
//            tracksQuantity = json.body()?.size!!
//            return tracksQuantity
//        }
//
//        scopeIO.launch { getTracksQuantity(loadConfiguration()) }
//
//        //Play Button click listener
//        binding.playBtn.setOnClickListener { // calling method to play audio.
////            player.setDataSource(getApplicationContext(), trackUri)
////            player.setAudioStreamType(AudioManager.STREAM_MUSIC)
////            player.prepareAsync()
//            scopeMain.launch { playTrack(seekBarProgress) }
//            trackNotification()
//
//            trackLengthInMs = mediaPlayer.duration
////            primarySeekBarProgressUpdater(seekBarProgress, trackLengthInMs!!)
//        }
//
//        //Pause Button click listener
//        binding.pauseBtn.setOnClickListener {
//            pauseTrack()
//        }
//
//        //Stop Button click listener
//        binding.stopBtn.setOnClickListener {
//            // checking the media player
//            // if the audio is playing or not.
//            stopTrack()
//        }
//
//        //Next Button click listener
//        binding.nextBtn.setOnClickListener { // calling method to play audio.
//            stopTrack()
//
//            if (trackNumber < tracksQuantity - 1)
//                trackNumber++
//            else trackNumber = 0
//            scopeMain.launch {
//                bind(getTrack(trackNumber))
//            }
//            scopeMain.launch {playTrack(seekBarProgress)}
//        }
//
//        //Previous Button click listener
//        binding.previousBtn.setOnClickListener {
//            stopTrack()
//            if (trackNumber > 0)
//                trackNumber--
//            else trackNumber = tracksQuantity - 1
//            scopeMain.launch { playTrack(seekBarProgress) }
//        }
//    }
//
//
//    private suspend fun getTrack(trackNumber: Int): Track = withContext(Dispatchers.IO) {
//
//        val track = loadConfiguration().body()!![trackNumber]
//
//        return@withContext Track(
//            title = track.title,
//            artist = track.artist,
//            bitmapUri = track.bitmapUri,
//            trackUri = track.trackUri
//        )
//    }
//
//    private fun bind(track: Track) {
////        mediaPlayer.reset()
//
//        binding.title.text = track.title
//        binding.artist.text = track.artist
//        bitmapUri = track.bitmapUri.toString()
//
//        try {
//            binding.cover.load(bitmapUri) {
//                crossfade(true)
//                placeholder(R.drawable.benbois_vinyl_records)
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        trackUrl = track.trackUri.toString()
//        try {
//            mediaPlayer.setDataSource(trackUrl)
//            mediaPlayer.prepareAsync()
//            trackLengthInMs = mediaPlayer.duration
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//
//    private suspend fun playTrack(seekBarProgress: SeekBar) =
//        withContext(Dispatchers.IO) {
//            if (!mediaPlayer.isPlaying)
//                try {
//                    mediaPlayer.start()
//                    trackLengthInMs = mediaPlayer.duration
//                    primarySeekBarProgressUpdater(seekBarProgress, trackLengthInMs!!)
//
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//        }
//
//    private fun pauseTrack() {
//        if (mediaPlayer.isPlaying)
//            mediaPlayer.pause()
//    }
//
//    private fun stopTrack() {
//        if (mediaPlayer.isPlaying) {
//            mediaPlayer.stop()
//            mediaPlayer.reset()
//            mediaPlayer.release()
//        } else {
//            Toast.makeText(this@MainActivity, "Audio has not played", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun trackNotification() {
//
//        title = binding.title.text.toString()
//        artist = binding.artist.text.toString()
//
//
//        val intent = Intent(this, MainActivity::class.java)
//        intent.apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
//
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.r5d_cassette_jolly_roger)
//            .setContentTitle(getString(R.string.notification_content_title))
//            .setContentText("$artist-$title")
//            .setAutoCancel(true)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)
//
//        with(NotificationManagerCompat.from(this)) {
//            notify(NOTIFICATION_ID, builder.build()) // посылаем уведомление
//        }
//    }
//
//    private fun primarySeekBarProgressUpdater(seekBarProgress: SeekBar, trackLengthInMs: Int) {
//        seekBarProgress.progress =
//            (mediaPlayer.currentPosition.toFloat() / trackLengthInMs * 100).toInt() // This math construction give a percentage of "was playing"/"song length"
//        if (mediaPlayer.isPlaying) {
//            val notification =
//                Runnable {
//                    primarySeekBarProgressUpdater(
//                        seekBarProgress,
//                        trackLengthInMs
//                    )
//                }
//            handler.postDelayed(notification, 1000)
//        }
//    }
//
//    private suspend fun loadConfiguration(): Response<List<Track>> = withContext(Dispatchers.IO) {
//        val tracksApi = retrofitHelper.getInstance().create(TracksAPI::class.java)
//        val result = tracksApi.getTracks()
//
//        if (result != null) {
//            Log.d("Playlist result: ", result.body()!![0].title)
//        }
//        return@withContext result
//    }
//
////    private suspend fun getTracks () = withContext(Dispatchers.IO)  {
////        val result = tracksApi.getTracks()
////        lol = result
////        if (result != null) {
////            Log.d("Playlist result: ", result.body()!![0].title.toString())
////        }
////    }
//
//// Create Notification Channel
//
//    private fun createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = getString(R.string.channel_name)
//            val descriptionText = getString(R.string.channel_description)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//
//    override fun onClick(p0: View?) {
//        TODO("Not yet implemented")
//    }
//
//
//    override fun onTouch(v: View, event: MotionEvent): Boolean {
//        if (v.id == R.id.SeekBarTestPlay) {
//            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position */
//            if (mediaPlayer.isPlaying) {
//                val sb = v as SeekBar
//                val playPositionInMillisecconds = trackLengthInMs!! / 100 * sb.progress
//                mediaPlayer.seekTo(playPositionInMillisecconds)
//            }
//        }
//        return false
//    }
//
//    /** Method which updates the SeekBar primary progress by current song playing position */
////    private fun primarySeekBarProgressUpdater(seekBarProgress: SeekBar, trackLengthInMs: Int) {
////        seekBarProgress.progress =
////            (mediaPlayer.currentPosition.toFloat() / trackLengthInMs * 100).toInt() // This math construction give a percentage of "was playing"/"song length"
////        if (mediaPlayer.isPlaying) {
////            val notification = Runnable { primarySeekBarProgressUpdater(seekBarProgress, trackLengthInMs) }
////            handler.postDelayed(notification, 1000)
////        }
////    }
//
//    override fun onCompletion(mP: MediaPlayer?) {
////        TODO("Not yet implemented")
////        if (trackNumber < tracksQuantity - 1)
////            trackNumber++
////        else trackNumber = 0
////        scopeMain.launch { setName(loadConfiguration()) }
////        scopeMain.launch { playTrack(loadConfiguration(), seekBarProgress) }
////        scopeMain.launch { loadCover(loadConfiguration()) }
//    }
//
//    override fun onBufferingUpdate(p0: MediaPlayer?, p1: Int) {
//        TODO("Not yet implemented")
//    }
//
//    class DiffCallback : DiffUtil.ItemCallback<Track>() {
//        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
//            return oldItem.title == newItem.title
//        }
//
//        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
//            return oldItem == newItem
//        }
//    }
//    }