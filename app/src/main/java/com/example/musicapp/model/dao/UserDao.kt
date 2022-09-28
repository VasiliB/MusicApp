package com.example.musicapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicapp.model.entity.Track

@Dao
interface TrackDao {

    @Query("SELECT * FROM tracks")
    fun findAll(): LiveData<List<Track>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(tracks: List<Track>)
}