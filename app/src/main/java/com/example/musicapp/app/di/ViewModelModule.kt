package com.example.musicapp.app.di

import com.example.musicapp.view_model.TrackViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        TrackViewModel(get(),get())
    }
}