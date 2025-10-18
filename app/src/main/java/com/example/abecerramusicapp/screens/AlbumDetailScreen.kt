package com.example.abecerramusicapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.abecerramusicapp.data.Album
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

@Composable
private fun DetailContent(
    album: Album,
    onBack: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Header con imagen y overlay
        item {
            HeaderHero(
                album = album,
                onBack = onBack,
                onPlay = { /* TODO reproducir */ },
                onShuffle = { /* TODO shuffle */ },
                onFavorite = { /* TODO fav */ }
            )
        }

        // About this album
        item {
            ElevatedCard(
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "About this album",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF1C133A),
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        album.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF2E2B41)
                    )
                }
            }
        }

        // Chip de artista
        item {
            AssistChip(
                onClick = { /* navegación a artista? */ },
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Artist:", fontWeight = FontWeight.ExtraBold)
                        Spacer(Modifier.width(6.dp))
                        Text(album.artist)
                    }
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = SurfaceLavender,
                    labelColor = Color(0xFF2E2B41)
                ),
                shape = MaterialTheme.shapes.large
            )
        }

        // Lista de tracks (mock simple con el título + índice)
        items((1..6).map { "Track $it" }) { track ->
            TrackRow(
                cover = album.image,
                title = "${album.title} • $track",
                subtitle = album.artist
            )
        }
    }

    // Mini player
    Box(Modifier.fillMaxSize()) {
        BottomMiniPlayer(
            album = album,
            onPlay = { /* TODO play */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }

}

@Composable
private fun HeaderHero(
    album: Album,
    onBack: () -> Unit,
    onPlay: () -> Unit,
    onShuffle: () -> Unit,
    onFavorite: () -> Unit
) {
    val shape = MaterialTheme.shapes.extraLarge
    ElevatedCard(shape = shape) {
        Box {
            SubcomposeAsyncImage(
                model = album.image,
                contentDescription = album.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = AccentBlue)
                    }
                }
            )
            // Overlay degradado para legibilidad
            Box(
                Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0x99000000))
                        )
                    )
            )

            // Botón back
            FilledIconButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(12.dp)
                    .size(40.dp),
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0x33000000))
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
            }

            // Botón favorito
            FilledIconButton(
                onClick = onFavorite,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(40.dp),
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0x33000000))
            ) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Fav", tint = TextWhite)
            }

            // Título + botones play/shuffle
            Column(
                Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    album.title,
                    color = TextWhite,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    album.artist,
                    color = TextWhite.copy(alpha = 0.9f),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ElevatedButton(
                        onClick = onPlay,
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.elevatedButtonColors(containerColor = AccentBlue)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = TextWhite)
                        Spacer(Modifier.width(6.dp))
                        Text("Play", color = TextWhite, fontWeight = FontWeight.SemiBold)
                    }
                    OutlinedButton(
                        onClick = onShuffle,
                        shape = MaterialTheme.shapes.large,
                        border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite)
                    ) {
                        Text("Shuffle", color = TextWhite, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun TrackRow(
    cover: String,
    title: String,
    subtitle: String
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(cover),
                contentDescription = title,
                modifier = Modifier
                    .size(48.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFF1C133A)
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B5A95),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = { /* menu */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "more", tint = Color(0xFF4B3D8A))
            }
        }
    }
}

@Composable
private fun BottomMiniPlayer(
    album: Album,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.elevatedCardColors(containerColor = LightBlue)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(album.image),
                contentDescription = album.title,
                modifier = Modifier
                    .size(44.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(album.title, color = TextWhite, fontWeight = FontWeight.Bold)
                Text(album.artist, color = TextWhite.copy(alpha = 0.85f))
            }
            IconButton(onClick = onPlay) {
                Icon(Icons.Default.PlayArrow, contentDescription = "play", tint = AccentBlue)
            }
        }
    }
}
