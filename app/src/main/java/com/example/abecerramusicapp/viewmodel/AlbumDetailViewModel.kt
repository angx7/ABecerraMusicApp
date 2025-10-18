package com.example.abecerramusicapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abecerramusicapp.data.Album
import com.example.abecerramusicapp.data.repository.AlbumRepository
import kotlinx.coroutines.launch

sealed interface AlbumDetailUiState {
    object Loading: AlbumDetailUiState
    data class Success(val album: Album): AlbumDetailUiState
    data class Error(val message: String): AlbumDetailUiState
}

class AlbumDetailViewModel(
    private val repo: AlbumRepository = AlbumRepository()
) : ViewModel(){
    var uiState: AlbumDetailUiState by mutableStateOf(AlbumDetailUiState.Loading)
        private set

    fun load(id: String){
        uiState = AlbumDetailUiState.Loading
        viewModelScope.launch {
            uiState = repo.fetchAlbum(id).fold(
                onSuccess = { AlbumDetailUiState.Success(it) },
                onFailure = { AlbumDetailUiState.Error(it.message ?: "Unknown Error") }
            )
        }
    }
}