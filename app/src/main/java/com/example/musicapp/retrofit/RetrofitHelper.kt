package com.example.musicapp.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    private val baseUrl =
        "https://raw.githubusercontent.com/VasiliB/RSShool2021-Android-task6-Music-App/main/data/"

    fun getInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            // Add converter factory to convert JSON object to Java object
            .build()
    }
}