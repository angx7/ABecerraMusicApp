package com.example.abecerramusicapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.abecerramusicapp.data.Album
import com.example.abecerramusicapp.screens.AlbumDetailScreenRoute
import com.example.abecerramusicapp.viewmodel.AlbumListUIState
import com.example.abecerramusicapp.viewmodel.AlbumListViewModel

@Composable
fun AlbumListScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    vm: AlbumListViewModel = viewModel()
) {
    LaunchedEffect(Unit) { vm.load() }

    val gradient = Brush.verticalGradient(
        listOf(Color(0xFF1F1C2C), Color(0xFF928DAB))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(horizontal = 12.dp) // padding lateral fijo
            .padding(paddingValues)      // respeta insets del Scaffold
    ) {
        when (val state = vm.uiState) {
            is AlbumListUIState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White)
            }
            is AlbumListUIState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.message}", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
            is AlbumListUIState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.albums) { album ->
                        AlbumCard(
                            album = album,
                            onClick = { navController.navigate(AlbumDetailScreenRoute(album.id)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AlbumCard(album: Album, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, shape = MaterialTheme.shapes.large),
        shape = MaterialTheme.shapes.large,
        onClick = onClick
    ) {
        Row(Modifier.background(Color.White)) {
            Image(
                painter = rememberAsyncImagePainter(album.image),
                contentDescription = album.title,
                modifier = Modifier
                    .size(96.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = album.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = album.artist,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = album.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
