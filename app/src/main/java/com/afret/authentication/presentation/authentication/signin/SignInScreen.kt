package com.afret.authentication.presentation.authentication.signin


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


import com.afret.authentication.theme.*
import com.afret.authentication.util.TextFieldType
import com.afret.authentication.widget.button.AuthenticationButton
import com.afret.authentication.widget.textfield.AuthenticationTextField

@Composable
fun SignInScreen() {


    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(ColorGunmetal)
    ) {

        //Login Image
        Image(
            painter = painterResource(id = R.drawable.login),
            modifier = Modifier.width(250.dp), contentDescription = "login"
        )


        Spacer(modifier = Modifier.height(10.dp))

        //Login Text
        Text(
            text = stringResource(id = R.string.login),
            style = IbarraNovaBoldPlatinum25
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Please Text
        Text(
            text = stringResource(id = R.string.please_sign_in_to_continue),
            style = IbarraNovaBoldPlatinum18
        )

        Spacer(modifier = Modifier.height(15.dp))


        //Email Text Field
        AuthenticationTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            text = email, hint = R.string.email,
            onValueChange = {
                email = it
            }, type = TextFieldType.Email
        )

        Spacer(modifier = Modifier.height(20.dp))

        //Password Text Field
        AuthenticationTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            text = password, hint = R.string.password,
            onValueChange = {
                password = it
            }, type = TextFieldType.Password
        )

        Spacer(modifier = Modifier.height(40.dp))

        //Login Button
        AuthenticationButton(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp),
            textId = R.string.login,
            onClick = {

                println("email: $email")
                println("password: $password")
            }
        )
        Spacer(modifier = Modifier.height(15.dp))

        //Forget Password Text
        Text(
            text = stringResource(id = R.string.forget_password),
            style = IbarraNovaSemiBoldColorVerdigris17
        )

        Spacer(modifier = Modifier.height(75.dp))

        Row{

            //Don't have an account Text
            Text(text = stringResource(id = R.string.do_not_have_an_account),
                style = IbarraNovaSemiBoldColorVerdigris17 )

            Spacer(modifier = Modifier.width(10.dp))
            //Sign Up Text
            Text(text = stringResource(id = R.string.sign_up),
                style = IbarraNovaSemiBoldPlatinum17 )

        }

    }


}


@Composable
@Preview
fun SignInPreview() {
    SignInScreen()
}


