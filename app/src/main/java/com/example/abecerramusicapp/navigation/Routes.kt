package com.example.abecerramusicapp.navigation
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

@Serializable
data class AlbumDetailScreenRoute(val id: String)