package com.afret.firebase.presentation.authentication.signin


import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.afret.firebase.R
import com.afret.firebase.domin.entities.Response
import com.afret.firebase.route.AppScreen
import com.afret.firebase.theme.*
import com.afret.firebase.util.TextFieldType
import com.afret.firebase.validation.event.ValidationEvent
import com.afret.firebase.validation.event.ValidationResultEvent
import com.afret.firebase.widget.LoadingScreen
import com.afret.firebase.widget.button.AuthenticationButton
import com.afret.firebase.widget.textfield.AuthenticationTextField

@Composable
fun SignInScreen(navController: NavController) {


    val viewModel: SignInViewModel = hiltViewModel()
    val context = LocalContext.current
    val state = viewModel.signInState.value




    LaunchedEffect(key1 = context) {


        viewModel.validationEvent.collect { event ->

            when (event) {

                is ValidationResultEvent.Success -> {

                    viewModel.signIn(
                        email = viewModel.forms[SignInTextFieldId.EMAIL]!!.text.trim(),
                        password = viewModel.forms[SignInTextFieldId.PASSWORD]!!.text.trim()
                    )
                }
            }
        }
    }



    if (state is Response.Success)
        if (state.data) {
            LaunchedEffect(true) {

                Toast.makeText(context, "Sign In Successfully", Toast.LENGTH_LONG).show()

                navController.navigate(AppScreen.ProfileScreen.route) {
                    popUpTo(AppScreen.SignInScreen.route) {
                        inclusive = true
                    }
                }
            }
        }

    if (state is Response.Error) {
        LaunchedEffect(true) {
            Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
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



            Spacer(modifier = Modifier.height(60.dp))


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
        if (state == Response.Loading) {
            LoadingScreen(modifier = Modifier)
        }
    }
}


@Composable
@Preview
fun SignInPreview() {
    //   SignInScreen()
}


