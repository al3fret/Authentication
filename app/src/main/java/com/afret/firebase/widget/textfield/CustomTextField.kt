package com.afret.firebase.widget.textfield


import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.afret.firebase.R
import com.afret.firebase.theme.ColorPlatinum
import com.afret.firebase.theme.IbarraNovaNormalError13
import com.afret.firebase.util.TextFieldType
import com.afret.firebase.validation.state.ValidationState


@Composable
fun CustomTextField(
    modifier: Modifier,
    state: ValidationState,
    textStyle: TextStyle,
    @StringRes hint: Int,
    hintTextStyle: TextStyle,
    onValueChange: (String) -> Unit,
    color: Color,
    cornerRadius: Dp = 0.dp,
    type: TextFieldType

) {


    var passwordVisible by remember {
        mutableStateOf(true)
    }

    var trailingId = when (type) {

        TextFieldType.Password -> com.afret.firebase.R.drawable.ic_visibility_on
        else -> null
    }


    if (trailingId != null) {

        trailingId =
            if (passwordVisible) com.afret.firebase.R.drawable.ic_visibility_off else R.drawable.ic_visibility_on
    }


    Column {


        TextField(
            modifier = modifier
                .background(color = color, shape = RoundedCornerShape(cornerRadius))
                .fillMaxWidth(),
            value = state.text,
            textStyle = textStyle,
            onValueChange = onValueChange,
            label = {
                Text(text = stringResource(id = hint), style = hintTextStyle, softWrap = true)
            },
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = ColorPlatinum,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            visualTransformation =
            if (type == TextFieldType.Password) {

                if (passwordVisible) PasswordVisualTransformation() else VisualTransformation.None

            } else VisualTransformation.None,
            trailingIcon = {

                if (trailingId != null) {

                    Icon(
                        modifier = Modifier.clickable {
                            passwordVisible = !passwordVisible
                        },
                        painter = painterResource(id = trailingId), contentDescription = "password",
                        tint = ColorPlatinum
                    )
                }
            }
        )

        if (state.hasError && state.errorMessageId != null) {

            Text(
                text = stringResource(id = state.errorMessageId),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 10.dp),
                style = IbarraNovaNormalError13
            )
        }

    }


}

