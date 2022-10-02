package com.afret.authentication.widget.textfield

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.afret.authentication.theme.ColorAmericanPurple
import com.afret.authentication.theme.IbarraNovaNormalGray14
import com.afret.authentication.theme.IbarraNovaSemiBoldPlatinum16
import com.afret.testauthentication.util.TextFieldType


@Composable
fun AuthenticationTextField(
    modifier: Modifier,
    text: String,
    @StringRes hint: Int,
    onValueChange: (String) -> Unit,
    type: TextFieldType

) {

    CustomTextField(
        modifier = modifier,
        text = text,
        hint = hint,
        onValueChange = onValueChange,
        textStyle = IbarraNovaSemiBoldPlatinum16,
        hintTextStyle = IbarraNovaNormalGray14,
        color = ColorAmericanPurple,
        cornerRadius = 15.dp,
        type = type
    )

}