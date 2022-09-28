package com.example.musicapp.model.api

import com.example.musicapp.model.entity.Track
import retrofit2.Response
import retrofit2.http.GET

interface TracksAPI {
    @GET("playlist.json")
    suspend fun getTracks(): Response<List<Track>>
}