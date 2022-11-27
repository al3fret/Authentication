package com.afret.firebase.presentation.authentication.signup


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
import com.afret.firebase.theme.ColorGunmetal
import com.afret.firebase.theme.IbarraNovaBoldPlatinum18
import com.afret.firebase.theme.IbarraNovaBoldPlatinum25
import com.afret.firebase.util.TextFieldType
import com.afret.firebase.validation.event.ValidationEvent
import com.afret.firebase.validation.event.ValidationResultEvent
import com.afret.firebase.widget.LoadingScreen
import com.afret.firebase.widget.button.AuthenticationButton
import com.afret.firebase.widget.textfield.AuthenticationTextField


@Composable
fun SignUpScreen(navController: NavController) {


    val viewModel: SignUpViewModel = hiltViewModel()

    val state = viewModel.signUpState.value

    val context = LocalContext.current



    LaunchedEffect(key1 = context) {


        viewModel.validationEvent.collect { event ->


            when (event) {
                ValidationResultEvent.Success -> {


                    viewModel.signUp(
                        email =
                        viewModel.forms[SignUpTextFieldId.EMAIL]!!.text.trim(),
                        password = viewModel.forms[SignUpTextFieldId.PASSWORD]!!.text,
                        fullName = viewModel.forms[SignUpTextFieldId.FULL_NAME]!!.text
                    )
                }

            }

        }
    }




    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorGunmetal)
    ) {

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
                    .height(30.dp)
                    .clickable {
                        navController.popBackStack()
                    },
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
                state = viewModel.forms[SignUpTextFieldId.FULL_NAME]!!,
                hint = R.string.full_name,
                onValueChange = {
                    viewModel.onEvent(

                        ValidationEvent.TextFieldValueChange(
                            viewModel.forms[SignUpTextFieldId.FULL_NAME]!!.copy(
                                text = it
                            )
                        )
                    )
                },
                type = TextFieldType.Text
            )
            Spacer(modifier = Modifier.height(20.dp))

            //Email TextField
            AuthenticationTextField(
                modifier = Modifier.fillMaxWidth(0.85f),
                state = viewModel.forms[SignUpTextFieldId.EMAIL]!!,
                hint = R.string.email,
                onValueChange = {

                    viewModel.onEvent(
                        ValidationEvent.TextFieldValueChange(

                            viewModel.forms[SignUpTextFieldId.EMAIL]!!.copy(text = it)
                        )
                    )

                },
                type = TextFieldType.Email
            )
            Spacer(modifier = Modifier.height(20.dp))


            //Password TextField
            AuthenticationTextField(
                modifier = Modifier.fillMaxWidth(0.85f),
                state = viewModel.forms[SignUpTextFieldId.PASSWORD]!!,
                hint = R.string.password,
                onValueChange = {


                    viewModel.onEvent(
                        ValidationEvent.TextFieldValueChange(
                            viewModel.forms[SignUpTextFieldId.PASSWORD]!!.copy(text = it)
                        )
                    )
                },
                type = TextFieldType.Password
            )
            Spacer(modifier = Modifier.height(40.dp))


            AuthenticationButton(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp), textId = R.string.sign_up,
                onClick = {

                    viewModel.onEvent(ValidationEvent.Submit)
                }
            )
        }

        if (state == Response.Loading)
            LoadingScreen(modifier = Modifier)
    }



    if (state is Response.Success)
        if (state.data) {
            LaunchedEffect(true) {

                Toast.makeText(context, "Sign Up Successfully", Toast.LENGTH_LONG).show()

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

}


@Composable
@Preview
fun SignUpPreview() {

    //   SignUpScreen()
}


