package com.example.abecerramusicapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.abecerramusicapp.components.AlbumHeroCard
import com.example.abecerramusicapp.components.BottomMiniPlayer
import com.example.abecerramusicapp.components.GreetingCard
import com.example.abecerramusicapp.components.RecentlyPlayedItem
import com.example.abecerramusicapp.components.SectionHeader
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