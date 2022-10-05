package com.example.musicapp.model.repository

import com.example.musicapp.model.entity.Track
import com.example.musicapp.utils.AppResult

interface TrackRepository {
    suspend fun getAllTracks() : AppResult<List<Track>>
}


//class TrackRepository(private val trackApi: TrackApi, private val trackDao: TrackDao) {
//
//    val data = trackDao.findAll()
//
//    suspend fun refresh() {
//        withContext(Dispatchers.IO) {
//            val tracks = trackApi.getAllAsync().await()
//            trackDao.add(tracks)
//        }
//    }
//}