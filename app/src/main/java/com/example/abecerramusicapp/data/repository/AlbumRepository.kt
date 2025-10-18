package com.example.abecerramusicapp.data.repository

import com.example.abecerramusicapp.data.Album
import com.example.abecerramusicapp.data.remote.MusicService
import com.example.abecerramusicapp.data.remote.RetrofitInstance

class AlbumRepository(
    private val api: MusicService = RetrofitInstance.retrofit.create(MusicService::class.java)
){
    suspend fun fetchAlbums(): Result<List<Album>> = runCatching { api.getAlbums() }
}
