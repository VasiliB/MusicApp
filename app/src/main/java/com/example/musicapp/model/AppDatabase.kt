package com.example.musicapp.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicapp.model.dao.TrackDao
import com.example.musicapp.model.entity.Track

@Database(entities = [Track::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val trackDao: TrackDao
}