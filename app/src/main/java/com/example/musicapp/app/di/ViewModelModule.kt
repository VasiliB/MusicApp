package com.example.musicapp.app.di

import com.example.musicapp.view_model.TrackViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    // Specific viewModel pattern to tell Koin how to build CountriesViewModel
    viewModel {
        TrackViewModel(trackRepository = get())
    }

}