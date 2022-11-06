package com.afret.authentication.widget.textfield

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.afret.authentication.theme.ColorAmericanPurple
import com.afret.authentication.theme.IbarraNovaNormalGray14
import com.afret.authentication.theme.IbarraNovaSemiBoldPlatinum16
import com.afret.authentication.util.TextFieldType
import com.afret.authentication.validation.state.ValidationState


@Composable
fun AuthenticationTextField(
    modifier: Modifier,
    state: ValidationState,
    @StringRes hint: Int,
    onValueChange: (String) -> Unit,
    type: TextFieldType

) {

    CustomTextField(
        modifier = modifier,
        state = state,
        hint = hint,
        onValueChange = onValueChange,
        textStyle = IbarraNovaSemiBoldPlatinum16,
        hintTextStyle = IbarraNovaNormalGray14,
        color = ColorAmericanPurple,
        cornerRadius = 15.dp,
        type = type
    )

}