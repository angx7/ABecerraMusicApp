package com.example.abecerramusicapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.abecerramusicapp.data.Album
import com.example.abecerramusicapp.ui.theme.AccentBlue
import com.example.abecerramusicapp.ui.theme.TextWhite

@Composable
fun AlbumHeroCard(album: Album, onClick: () -> Unit, width: Dp = 220.dp, height: Dp = 160.dp) {
    ElevatedCard(
        onClick = onClick,
        shape = MaterialTheme.shapes.extraLarge,
        modifier = Modifier
            .width(width)
            .height(height)
            .shadow(8.dp, shape = MaterialTheme.shapes.extraLarge)
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(album.image),
                contentDescription = album.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(Color(0xAA000000))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(album.title, color = TextWhite, fontWeight = FontWeight.Bold)
                        Text(album.artist, color = TextWhite.copy(alpha = 0.8f))
                    }
                    Spacer(Modifier.width(8.dp))
                    ElevatedButton(
                        onClick = onClick,
                        shape = MaterialTheme.shapes.large,
                        contentPadding = PaddingValues(6.dp),
                        colors = ButtonDefaults.elevatedButtonColors(containerColor = AccentBlue)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = TextWhite)
                    }
                }
            }
        }
    }
}