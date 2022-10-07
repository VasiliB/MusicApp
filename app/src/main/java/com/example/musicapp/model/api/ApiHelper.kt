package com.example.musicapp.model.api

import com.example.musicapp.model.Track
import retrofit2.Response

interface ApiHelper {

    suspend fun getTracks(): Response<List<Track>>
}