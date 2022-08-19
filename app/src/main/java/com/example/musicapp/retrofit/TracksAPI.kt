package com.example.musicapp.retrofit

import com.example.musicapp.Track
import retrofit2.Response
import retrofit2.http.GET

interface TracksAPI {
    @GET("playlist.json")
    suspend fun getTracks(): Response<List<Track>>
}