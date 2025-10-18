package com.example.abecerramusicapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.abecerramusicapp.data.Album
import com.example.abecerramusicapp.viewmodel.AlbumListUIState
import com.example.abecerramusicapp.viewmodel.AlbumListViewModel
import com.example.abecerramusicapp.ui.theme.*

@Composable
fun AlbumListScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    vm: AlbumListViewModel = viewModel()
) {
    LaunchedEffect(Unit) { vm.load() }

    val gradient = Brush.verticalGradient(listOf(HardDarkBlue, DarkBlue, LightBlue))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(paddingValues)
    ) {
        when (val state = vm.uiState) {
            is AlbumListUIState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AccentBlue)
            }
            is AlbumListUIState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.message}", color = TextWhite, fontWeight = FontWeight.SemiBold)
            }
            is AlbumListUIState.Success -> {
                val albums = state.albums
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        GreetingCard(
                            title = "Good Morning!",
                            userName = "Alex Taco"
                        )
                    }

                    if (albums.isNotEmpty()) {
                        item {
                            SectionHeader("Albums")
                        }
                        item {
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                items(albums) { album ->
                                    AlbumHeroCard(album = album, onClick ={navController.navigate(
                                        AlbumDetailScreenRoute(album.id))})
                                }
                            }
                        }

                        item { SectionHeader("Recently Played") }
                        items(albums) { album ->
                            RecentlyPlayedItem(album) {
                                navController.navigate(AlbumDetailScreenRoute(album.id))
                            }
                        }

                        item { Spacer(Modifier.height(80.dp)) }
                    }
                }

                if (albums.isNotEmpty()) {
                    BottomMiniPlayer(
                        album = albums.first(),
                        onPlay = { navController.navigate(AlbumDetailScreenRoute(albums.first().id)) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

// ----- COMPONENTES -----

@Composable
private fun GreetingCard(title: String, userName: String) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.elevatedCardColors(containerColor = CardGray)
    ) {
        Box(
            modifier = Modifier
                .background(Brush.linearGradient(listOf(AccentBlue, LightBlue)))
                .padding(20.dp)
        ) {
            Column {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(Icons.Default.Menu, contentDescription = null, tint = TextWhite)
                    Icon(Icons.Default.Search, contentDescription = null, tint = TextWhite)
                }
                Spacer(Modifier.height(16.dp))
                Text(title, color = TextWhite.copy(alpha = 0.8f), style = MaterialTheme.typography.bodyLarge)
                Text(
                    userName,
                    color = TextWhite,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = TextWhite
        )
        TextButton(onClick = { }) {
            Text("See more", color = AccentBlue)
        }
    }
}

@Composable
private fun AlbumHeroCard(album: Album, onClick: () -> Unit, width: Dp = 220.dp, height: Dp = 160.dp) {
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

@Composable
private fun RecentlyPlayedItem(album: Album, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.elevatedCardColors(containerColor = CardGray),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(album.image),
                contentDescription = album.title,
                modifier = Modifier
                    .size(48.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(album.title, color = TextWhite, fontWeight = FontWeight.Bold)
                Text("${album.artist} • Popular Song", color = TextWhite.copy(alpha = 0.8f))
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.MoreVert, contentDescription = "more", tint = TextWhite)
            }
        }
    }
}

@Composable
private fun BottomMiniPlayer(album: Album, onPlay: () -> Unit, modifier: Modifier = Modifier) {
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
                Text(album.artist, color = TextWhite.copy(alpha = 0.8f))
            }
            IconButton(onClick = onPlay) {
                Icon(Icons.Default.PlayArrow, contentDescription = "play", tint = AccentBlue)
            }
        }
    }
}
