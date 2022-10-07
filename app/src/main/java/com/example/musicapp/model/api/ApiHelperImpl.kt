package com.example.musicapp.model.api

import com.example.musicapp.model.Track
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getTracks(): Response<List<Track>> = apiService.getUsers()

}