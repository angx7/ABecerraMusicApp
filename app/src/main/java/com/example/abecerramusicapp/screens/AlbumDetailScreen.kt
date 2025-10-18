package com.example.abecerramusicapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abecerramusicapp.components.DetailContent
import com.example.abecerramusicapp.ui.theme.*
import com.example.abecerramusicapp.viewmodel.AlbumDetailUiState
import com.example.abecerramusicapp.viewmodel.AlbumDetailViewModel


@Composable
fun AlbumDetailScreen(
    id: String,
    onBack: () -> Unit,
    vm: AlbumDetailViewModel = viewModel(),
    paddingValues: PaddingValues
) {
    LaunchedEffect(id) { vm.load(id) }

    val bg = Brush.verticalGradient(listOf(DarkBlue, LightBlue))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(paddingValues)
    ) {
        when (val state = vm.uiState) {
            is AlbumDetailUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AccentBlue)
            }
            is AlbumDetailUiState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.message}", color = TextWhite, fontWeight = FontWeight.SemiBold)
            }
            is AlbumDetailUiState.Success -> DetailContent(
                album = state.album,
                onBack = onBack
            )
        }
    }
}