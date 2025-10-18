package com.example.abecerramusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.abecerramusicapp.screens.AlbumDetailScreen
import com.example.abecerramusicapp.screens.AlbumDetailScreenRoute
import com.example.abecerramusicapp.screens.AlbumListScreen
import com.example.abecerramusicapp.screens.HomeScreenRoute
import com.example.abecerramusicapp.ui.theme.ABecerraMusicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ABecerraMusicAppTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = HomeScreenRoute // puedes mantener el nombre de ruta
                    ) {
                        // Lista de álbumes
                        composable<HomeScreenRoute> {
                            AlbumListScreen(
                                paddingValues = innerPadding,
                                navController = navController
                            )
                        }

                        // Detalle
                        composable<AlbumDetailScreenRoute> { entry ->
                            val args = entry.toRoute<AlbumDetailScreenRoute>()
                            AlbumDetailScreen(
                                id = args.id,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
