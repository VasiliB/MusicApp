package com.example.musicapp.app.di

import com.example.musicapp.model.repository.TrackRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        TrackRepository(get())
    }
}