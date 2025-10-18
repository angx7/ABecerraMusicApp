package com.example.abecerramusicapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.abecerramusicapp.ui.theme.AccentBlue
import com.example.abecerramusicapp.ui.theme.CardGray
import com.example.abecerramusicapp.ui.theme.LightBlue
import com.example.abecerramusicapp.ui.theme.TextWhite

@Composable
fun GreetingCard(title: String, userName: String) {
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