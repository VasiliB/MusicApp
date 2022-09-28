package com.example.musicapp.model.repository

import com.example.musicapp.model.api.TrackApi
import com.example.musicapp.model.dao.TrackDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrackRepository(private val trackApi: TrackApi, private val trackDao: TrackDao) {

    val data = trackDao.findAll()

    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val tracks = trackApi.getAllAsync().await()
            trackDao.add(tracks)
        }
    }
}