package com.example.musicapp

import java.io.Serializable

data class Track(
//    @SerialName("title")
    val title: String,
    val artist: String,
    val bitmapUri: String?,
    val trackUri: String?,
) : Serializable