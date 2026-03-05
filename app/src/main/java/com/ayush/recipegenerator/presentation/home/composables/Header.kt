package com.ayush.recipegenerator.presentation.home.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayush.recipegenerator.R
import com.ayush.recipegenerator.common.util.getMealByTime

// Composable function for displaying the header in Home Screen
@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Display a text element with padding and styled text
        Text(
            text = stringResource(id = R.string.home_header, getMealByTime()),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight(700)),
            modifier = Modifier.weight(1f)
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        IconButton(onClick = onLogoutClick) {
            Icon(
                imageVector = Icons.Default.Logout,
                contentDescription = "Logout",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// Preview function for the HomeHeader composable
// Shows system UI and specifies the device for preview
@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun HomeHeaderPreview() {
    // Calls the HomeHeader composable for preview
    HomeHeader()
}