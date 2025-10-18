package com.example.abecerramusicapp.screens

import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

@Serializable
data class AlbumDetailScreenRoute(val id: String)
