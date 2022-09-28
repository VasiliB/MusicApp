package com.example.musicapp.model.api

import com.example.musicapp.model.entity.Track
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface TrackApi {
    @GET("tracks")
    fun getAllAsync(): Deferred<List<Track>>
}