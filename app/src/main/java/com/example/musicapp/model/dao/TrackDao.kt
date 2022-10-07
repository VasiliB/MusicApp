package com.example.musicapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicapp.model.Track

@Dao
interface TrackDao {

    @Query("SELECT * FROM tracks")
    fun findAll(): List<Track>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(tracks: List<Track>)
}