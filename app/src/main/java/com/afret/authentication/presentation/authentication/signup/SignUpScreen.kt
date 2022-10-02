package com.afret.authentication.presentation.authentication.signup


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.afret.authentication.theme.ColorGunmetal


@Composable
fun SignUpScreen() {


    Column(

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(ColorGunmetal)

    ) {


    }
}


@Composable
@Preview
fun SignUpPreview() {

    SignUpScreen()
}


