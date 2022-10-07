package com.example.musicapp.model.repository

import com.example.musicapp.model.api.ApiHelper

class TrackRepository(private val apiHelper: ApiHelper) {

    suspend fun getTracks() = apiHelper.getTracks()
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