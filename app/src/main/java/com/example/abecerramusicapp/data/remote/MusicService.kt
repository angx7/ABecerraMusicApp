package com.example.abecerramusicapp.data.remote

import com.example.abecerramusicapp.data.Album
import retrofit2.http.GET

interface MusicService {
    @GET("api/albums")
    suspend fun getAlbums(): List<Album>
}