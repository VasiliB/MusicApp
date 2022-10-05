package com.example.musicapp.model.api

import com.example.musicapp.model.entity.Track
import retrofit2.Response
import retrofit2.http.GET

interface TrackApi {
    @GET("tracks")
    suspend fun getAllTracks(): Response<List<Track>>
}