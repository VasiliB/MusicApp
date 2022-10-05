package com.example.musicapp.app.di

import android.content.Context
import com.example.musicapp.model.api.TrackApi
import com.example.musicapp.model.dao.TrackDao
import com.example.musicapp.model.repository.TrackRepository
import com.example.musicapp.model.repository.TrackRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun provideUserRepository(api: TrackApi, context: Context, dao : TrackDao): TrackRepository {
        return TrackRepositoryImpl(api, context, dao)
    }
    single { provideUserRepository(get(), androidContext(), get()) }

}