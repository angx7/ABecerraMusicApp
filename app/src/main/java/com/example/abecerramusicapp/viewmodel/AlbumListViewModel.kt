package com.example.abecerramusicapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abecerramusicapp.data.Album
import com.example.abecerramusicapp.data.repository.AlbumRepository
import kotlinx.coroutines.launch

sealed interface AlbumListUIState {
    object Loading: AlbumListUIState
    data class Success(val albums: List<Album>): AlbumListUIState
    data class Error(val message: String): AlbumListUIState
}

class AlbumListViewModel(
    private val repo: AlbumRepository = AlbumRepository()
) : ViewModel(){
    var uiState: AlbumListUIState by mutableStateOf(AlbumListUIState.Loading)
        private set

    fun load(){
        uiState = AlbumListUIState.Loading
        viewModelScope.launch {
            val res = repo.fetchAlbums()
            uiState = res.fold(
                onSuccess = { AlbumListUIState.Success(it) },
                onFailure = { AlbumListUIState.Error(it.message ?: "Unknown Error") }
            )
        }
    }
}