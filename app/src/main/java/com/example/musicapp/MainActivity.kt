package com.example.musicapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.musicapp.utils.LoadingState
import com.example.musicapp.view_model.TrackViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel



class MainActivity : AppCompatActivity(){

    private val trackViewModel by viewModel<TrackViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trackViewModel.data.observe(this, Observer {
            // Todo: Populate the recyclerView here
            it.forEach { track ->
                Toast.makeText(baseContext, track.title, Toast.LENGTH_SHORT).show()
            }
        })

        trackViewModel.loadingState.observe(this, Observer {
            when (it.status) {
                LoadingState.Status.FAILED -> Toast.makeText(baseContext, it.msg, Toast.LENGTH_SHORT).show()
                LoadingState.Status.RUNNING -> Toast.makeText(baseContext, "Loading", Toast.LENGTH_SHORT).show()
                LoadingState.Status.SUCCESS -> Toast.makeText(baseContext, "Success", Toast.LENGTH_SHORT).show()
            }
        })
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