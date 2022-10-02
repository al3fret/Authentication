package com.afret.authentication.presentation.authentication.signup


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.afret.authentication.R
import com.afret.authentication.theme.ColorGunmetal
import com.afret.authentication.theme.IbarraNovaBoldPlatinum18
import com.afret.authentication.theme.IbarraNovaBoldPlatinum25
import com.afret.authentication.util.TextFieldType
import com.afret.authentication.widget.button.AuthenticationButton
import com.afret.authentication.widget.textfield.AuthenticationTextField


@Composable
fun SignUpScreen() {


    var fullName by remember {
        mutableStateOf("")
    }


    var email by remember {
        mutableStateOf("")
    }


    var password by remember {
        mutableStateOf("")
    }

    Column(

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(ColorGunmetal)

    ) {

        Spacer(modifier = Modifier.height(40.dp))

        //Back Image
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            modifier = Modifier
                .align(Alignment.Start)
                .width(40.dp)
                .height(30.dp),
            contentDescription = "back"
        )

        Spacer(modifier = Modifier.height(20.dp))

        //Create Account Text
        Text(
            text = stringResource(id = R.string.create_account),
            style = IbarraNovaBoldPlatinum25
        )

        Spacer(modifier = Modifier.height(5.dp))

        //Please Fill Text
        Text(
            text = stringResource(id = R.string.please_sign_in_to_continue),
            style = IbarraNovaBoldPlatinum18
        )

        Spacer(modifier = Modifier.height(40.dp))

        //FullName TextField
        AuthenticationTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            text = fullName,
            hint = R.string.full_name,
            onValueChange = {
                fullName = it
            },
            type = TextFieldType.Text
        )
        Spacer(modifier = Modifier.height(20.dp))

        //Email TextField
        AuthenticationTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            text = email,
            hint = R.string.email,
            onValueChange = {
                email = it
            },
            type = TextFieldType.Email
        )
        Spacer(modifier = Modifier.height(20.dp))


        //Password TextField
        AuthenticationTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            text = password,
            hint = R.string.password,
            onValueChange = {
                password = it
            },
            type = TextFieldType.Password
        )
        Spacer(modifier = Modifier.height(40.dp))


        AuthenticationButton(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp), textId = R.string.sign_up,
            onClick = {

                println("FullName $fullName")
                println("Email $email")
                println("Password $password")
            }
        )
    }
}


@Composable
@Preview
fun SignUpPreview() {

    SignUpScreen()
}


