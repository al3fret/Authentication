package com.afret.firebase.route

sealed class AppScreen(val route:String){

    object SignInScreen : AppScreen(ConstantAppScreenName.SIGN_IN_SCREEN)
    object SignUpScreen : AppScreen(ConstantAppScreenName.SIGN_UP_SCREEN)
    object ProfileScreen : AppScreen(ConstantAppScreenName.PROFILE_SCREEN)
}
