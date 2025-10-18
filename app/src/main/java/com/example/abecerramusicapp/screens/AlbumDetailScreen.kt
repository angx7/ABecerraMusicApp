package com.example.abecerramusicapp.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.abecerramusicapp.viewmodel.AlbumDetailUiState
import com.example.abecerramusicapp.viewmodel.AlbumDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    id: String,
    vm: AlbumDetailViewModel = viewModel(),
    onBack: () -> Unit
) {
    LaunchedEffect(id) { vm.load(id) }

    val gradient = Brush.verticalGradient(
        listOf(Color(0xFF1F1C2C), Color(0xFF928DAB))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Álbum", fontWeight = FontWeight.Bold) },
                navigationIcon = { TextButton(onClick = onBack) { Text("Atrás") } }
            )
        }
    ) { inner ->
        Box(
            Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(inner)
                .padding(16.dp)
        ) {
            when (val state = vm.uiState) {
                is AlbumDetailUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                is AlbumDetailUiState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${state.message}", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
                is AlbumDetailUiState.Success -> {
                    val a = state.album
                    ElevatedCard(shape = MaterialTheme.shapes.large) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Image(
                                painter = rememberAsyncImagePainter(a.image),
                                contentDescription = a.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(a.title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold))
                            Text(a.artist, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                            Text(a.description, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
