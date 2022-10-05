package com.example.musicapp.app.di


import com.example.musicapp.model.api.TrackApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideTrackApi(retrofit: Retrofit): TrackApi {
        return retrofit.create(TrackApi::class.java)
    }
    single { provideTrackApi(get()) }

}