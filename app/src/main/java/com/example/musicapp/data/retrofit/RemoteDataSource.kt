//package com.example.musicapp.data.retrofit
//
//import android.util.Log
//import com.example.musicapp.model.Track
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import retrofit2.Response
//
//class RemoteDataSource {
//
//    private val retrofitHelper = RetrofitHelper()
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
//}