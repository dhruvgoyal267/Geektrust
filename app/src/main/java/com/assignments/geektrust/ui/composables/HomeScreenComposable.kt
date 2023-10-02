package com.assignments.geektrust.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreenComposable(onClickNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to the world of Falcon",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Our problem is set in the planet of Lengaburu…in the distant " +
                    "distant galaxy of Tara B. After the recent war with neighbouring " +
                    "planet Falicornia, King Shan has exiled the Queen of Falicornia " +
                    "for 15 years",
            textAlign = TextAlign.Left
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Queen Al Falcone is now in hiding. But if King Shan can find\n" +
                    "her before the years are up, she will be exiled for another 15\n" +
                    "years….",
            textAlign = TextAlign.Left
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onClickNext) {
            Text(text = "Find Falcon")
        }

    }
}

@Preview
@Composable
fun HomeComposablePreview() {
    HomeScreenComposable {}
}