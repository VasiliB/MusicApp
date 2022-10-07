package com.example.musicapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tracki(
    @SerialName("title")
    val title: String,
    @SerialName("artist")
    val artist: String,
    @SerialName("bitmapUri")
    val bitmapUri: String?,
    @SerialName("trackUri")
    val trackUri: String?,
)