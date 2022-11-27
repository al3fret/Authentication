package com.afret.firebase.widget.button

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.afret.firebase.theme.*


@Composable
fun AuthenticationButton(
    modifier: Modifier,
    @StringRes textId: Int,
    onClick: () -> Unit,
) {

    CustomButton(
        modifier = modifier,
        color = ColorVerdigris,
        onClick = onClick,
        textId = textId,
        textStyle = IbarraNovaSemiBoldGraniteGray18,
        cornerRadius = 25.dp
    )
}



