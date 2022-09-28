package com.example.musicapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class Track(
    @PrimaryKey val title: String,
val artist: String,
val bitmapUri: String?,
val trackUri: String?
)

//@Serializable
//data class Track(
//    @SerialName("title")
//    val title: String,
//    @SerialName("artist")
//    val artist: String,
//    @SerialName("bitmapUri")
//    val bitmapUri: String?,
//    @SerialName("trackUri")
//    val trackUri: String?
//)