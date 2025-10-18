package com.example.abecerramusicapp.data.remote

import com.example.abecerramusicapp.data.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicService {
    @GET("api/albums")
    suspend fun getAlbums(): List<Album>
    @GET("api/albums/{id}")
    suspend fun getAlbums(@Path("id") id: String): Album
}