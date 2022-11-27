package com.afret.firebase.widget


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.afret.firebase.R

import com.afret.firebase.theme.ColorGunmetal50
import com.airbnb.lottie.compose.*



@Composable
fun LoadingScreen(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val lottieProgress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )


    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {} // for disable click
            .background( ColorGunmetal50),

        contentAlignment = Alignment.Center,

        ) {
        LottieAnimation(
            modifier = modifier
                .size(80.dp),
            composition = composition,
            progress = lottieProgress,
        )
    }
}
