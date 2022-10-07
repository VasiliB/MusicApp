package com.example.musicapp.model.api

import com.example.musicapp.model.Track
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("playlist.json")
//    @GET("tracks")
    suspend fun getUsers(): Response<List<Track>>

}