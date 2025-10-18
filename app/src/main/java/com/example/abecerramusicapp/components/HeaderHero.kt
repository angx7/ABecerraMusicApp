package com.example.abecerramusicapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.example.abecerramusicapp.data.Album
import com.example.abecerramusicapp.ui.theme.AccentBlue
import com.example.abecerramusicapp.ui.theme.TextWhite


@Composable
fun HeaderHero(
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