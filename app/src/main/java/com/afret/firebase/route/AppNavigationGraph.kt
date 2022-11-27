@file:OptIn(ExperimentalPermissionsApi::class)

package com.afret.firebase.route

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.afret.firebase.presentation.authentication.signin.SignInScreen
import com.afret.firebase.presentation.authentication.signup.SignUpScreen
import com.afret.firebase.presentation.profile.ProfileScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun AppNavigationGraph(navHostController: NavHostController, isUserLoggedIn: Boolean) {

    val startDes = if (isUserLoggedIn) {
        AppScreen.ProfileScreen.route
    } else {
        AppScreen.SignInScreen.route
    }

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
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
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

        composable(route = AppScreen.ProfileScreen.route) {
            ProfileScreen(navController = navHostController)
        }
    }
}