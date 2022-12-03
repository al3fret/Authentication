package com.afret.authentication.widget.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.afret.authentication.theme.ColorGunmetal50
import com.afret.authentication.theme.ColorVerdigris


@Composable
fun LoadingScreen() {


    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {}
            .background(ColorGunmetal50)
    ) {
        CircularProgressIndicator(modifier = Modifier.size(50.dp), color = ColorVerdigris)

    }
}