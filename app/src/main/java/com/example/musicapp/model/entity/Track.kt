package com.example.musicapp.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "tracks")
@Parcelize
data class Track(
    @PrimaryKey (autoGenerate = true)
    val title: String,
    val artist: String,
    val bitmapUri: String?,
    val trackUri: String?
): Parcelable

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