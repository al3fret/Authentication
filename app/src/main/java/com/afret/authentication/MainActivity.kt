package com.afret.authentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.afret.authentication.presentation.authentication.signin.SignInScreen
import com.afret.authentication.presentation.authentication.signup.SignUpScreen
import com.afret.authentication.route.AppNavigationGraph
import com.afret.authentication.theme.AuthenticationTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        FirebaseApp.initializeApp(applicationContext)

        setContent {
            AuthenticationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberAnimatedNavController()
                    AppNavigationGraph(navHostController = navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AuthenticationTheme {
        Greeting("Android")
    }
}