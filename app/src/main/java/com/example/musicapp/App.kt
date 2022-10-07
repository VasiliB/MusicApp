package com.example.musicapp

import android.app.Application
import com.example.musicapp.app.di.appModule
import com.example.musicapp.app.di.repoModule
import com.example.musicapp.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}
