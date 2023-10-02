package com.assignments.geektrust.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.assignments.geektrust.models.FindFalconStatus

@Composable
fun FinalResultScreen(findFalconStatus: FindFalconStatus, onHomeClicked: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val playAgainVisible = remember {
            mutableStateOf(findFalconStatus != FindFalconStatus.Finding)
        }

        when (findFalconStatus) {
            is FindFalconStatus.Error -> {
                playAgainVisible.value = true
                Text(
                    findFalconStatus.errorType.errorMsg
                        ?: "Sorry! unable to find as of now try again later"
                )
            }

            FindFalconStatus.Finding -> Loader()

            is FindFalconStatus.Found -> {
                playAgainVisible.value = true
                Text("Falcon found on planet ${findFalconStatus.planet}")
            }

            FindFalconStatus.NotFound -> {
                playAgainVisible.value = true
                Text("Falcon not found on selected planets, try again")
            }

            FindFalconStatus.NotFinding -> {}
        }

        Spacer(modifier = Modifier.height(12.dp))

        AnimatedVisibility(visible = playAgainVisible.value) {
            Button(onClick = onHomeClicked) {
                Text("Play again!")
            }
        }
    }
}

@Composable
fun Loader() {
    Row {
        CircularProgressIndicator()

        Spacer(Modifier.width(5.dp))

        Text("Finding falcon please wait.....")
    }
}