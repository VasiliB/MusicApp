package com.example.musicapp.model

import com.squareup.moshi.Json


data class Track(
    @Json(name = "title")
    val title: String,
    @Json(name = "artist")
    val artist: String,
    @Json(name = "bitmapUri")
    val bitmapUri: String?,
    @Json(name = "trackUri")
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