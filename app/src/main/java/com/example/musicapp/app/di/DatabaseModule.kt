package com.example.musicapp.app.di

import android.app.Application
import androidx.room.Room
import com.example.musicapp.model.AppDatabase
import com.example.musicapp.model.dao.TrackDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
       return Room.databaseBuilder(application, AppDatabase::class.java, "tracks")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideTracksDao(database: AppDatabase): TrackDao {
        return  database.trackDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideTracksDao(get()) }


}
