package com.afret.authentication.presentation.authentication.signin


import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.afret.authentication.R
import com.afret.authentication.domin.entites.Response
import com.afret.authentication.route.AppScreen


import com.afret.authentication.theme.*
import com.afret.authentication.util.TextFieldType
import com.afret.authentication.validation.event.ValidationEvent
import com.afret.authentication.validation.event.ValidationResultEvent
import com.afret.authentication.widget.button.AuthenticationButton
import com.afret.authentication.widget.loading.LoadingScreen
import com.afret.authentication.widget.textfield.AuthenticationTextField
import kotlinx.coroutines.flow.collect

@Composable
fun SignInScreen(navController: NavController) {


    val viewModel: SignInViewModel = hiltViewModel()
    val context = LocalContext.current
    val signInState = viewModel.signInState.value




    LaunchedEffect(key1 = context) {


        viewModel.validationEvent.collect { event ->

            when (event) {

                is ValidationResultEvent.Success -> {


                    viewModel.firebaseSignIn(
                        email = viewModel.forms[SignInTextFieldId.EMAIL]!!.text.trim(),
                        password = viewModel.forms[SignInTextFieldId.PASSWORD]!!.text
                    )
                }
            }
        }
    }

    LaunchedEffect(signInState) {

        when (signInState) {

            is Response.Success -> {
                if (signInState.data) {
                    Toast.makeText(context, R.string.sign_in_successfully, Toast.LENGTH_LONG).show()
                }
            }

            is Response.Error -> {
                Toast.makeText(context, signInState.message, Toast.LENGTH_LONG).show()
            }

            is Response.Loading -> Unit
        }
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorGunmetal)
    ) {


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
                state = viewModel.forms[SignInTextFieldId.EMAIL]!!, hint = R.string.email,
                onValueChange = {


                    viewModel.onEvent(
                        ValidationEvent.TextFieldValueChange(

                            viewModel.forms[SignInTextFieldId.EMAIL]!!.copy(text = it)
                        )
                    )
                }, type = TextFieldType.Email
            )

            Spacer(modifier = Modifier.height(20.dp))

            //Password Text Field
            AuthenticationTextField(
                modifier = Modifier.fillMaxWidth(0.85f),
                state = viewModel.forms[SignInTextFieldId.PASSWORD]!!, hint = R.string.password,
                onValueChange = {

                    viewModel.onEvent(
                        ValidationEvent.TextFieldValueChange(

                            viewModel.forms[SignInTextFieldId.PASSWORD]!!.copy(text = it)
                        )
                    )
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

                    viewModel.onEvent(ValidationEvent.Submit)
                }
            )
            Spacer(modifier = Modifier.height(15.dp))

            //Forget Password Text
            Text(
                text = stringResource(id = R.string.forget_password),
                style = IbarraNovaSemiBoldColorVerdigris17
            )

            Spacer(modifier = Modifier.height(75.dp))

            Row(modifier = Modifier.clickable {

                navController.navigate(AppScreen.SignUpScreen.route)
            }) {

                //Don't have an account Text
                Text(
                    text = stringResource(id = R.string.do_not_have_an_account),
                    style = IbarraNovaSemiBoldColorVerdigris17
                )

                Spacer(modifier = Modifier.width(10.dp))
                //Sign Up Text
                Text(
                    text = stringResource(id = R.string.sign_up),
                    style = IbarraNovaSemiBoldPlatinum17
                )

            }

        }

        if (signInState == Response.Loading)
            LoadingScreen()
    }

}


@Composable
@Preview
fun SignInPreview() {
    //   SignInScreen()
}


