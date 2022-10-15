package com.afret.authentication.route

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.afret.authentication.presentation.authentication.signin.SignInScreen
import com.afret.authentication.presentation.authentication.signup.SignUpScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigationGraph(navHostController: NavHostController) {


    val startDes = AppScreen.SignInScreen.route

    AnimatedNavHost(navController = navHostController,
        startDestination = startDes,
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        }

    ) {

        composable(route = AppScreen.SignUpScreen.route) {
            SignUpScreen(navController = navHostController)
        }

        composable(route = AppScreen.SignInScreen.route) {
            SignInScreen(navController = navHostController)
        }

    }
}