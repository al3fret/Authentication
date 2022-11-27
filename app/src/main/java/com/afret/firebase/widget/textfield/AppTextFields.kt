package com.afret.firebase.widget.textfield

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.afret.firebase.theme.ColorAmericanPurple
import com.afret.firebase.theme.IbarraNovaNormalGray14
import com.afret.firebase.theme.IbarraNovaSemiBoldPlatinum16
import com.afret.firebase.util.TextFieldType
import com.afret.firebase.validation.state.ValidationState


@Composable
fun AuthenticationTextField(
    modifier: Modifier,
    state: ValidationState,
    @StringRes hint: Int,
    onValueChange: (String) -> Unit,
    type: TextFieldType = TextFieldType.Text

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