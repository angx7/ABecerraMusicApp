package com.example.abecerramusicapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.abecerramusicapp.data.Album
import com.example.abecerramusicapp.ui.theme.SurfaceLavender

@Composable
fun DetailContent(
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
        item {
            HeaderHero(
                album = album,
                onBack = onBack,
                onPlay = {  },
                onShuffle = {  },
                onFavorite = {  }
            )
        }
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

        item {
            AssistChip(
                onClick = {  },
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
            onPlay = {  },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }

}