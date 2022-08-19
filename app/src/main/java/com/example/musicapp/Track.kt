package com.example.musicapp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    @SerialName("title")
    val title: String,
    @SerialName("artist")
    val artist: String,
    @SerialName("bitmapUri")
    val coverUri: String?,
    @SerialName("trackUri")
    val trackUri: String?,
)