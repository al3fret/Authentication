package com.afret.firebase.domin.authentication_use_case

import com.afret.authentication.domin.use_case.FirebaseIsUserAuthenticated
import com.afret.authentication.domin.use_case.FirebaseSignIn
import com.afret.authentication.domin.use_case.FirebaseSignOut

data class AuthenticationUseCase(
    val firebaseSignUp: FirebaseSignUp,
    val firebaseSignIn: FirebaseSignIn,
    val isUserAuthenticated: FirebaseIsUserAuthenticated,
    val firebaseSignOut: FirebaseSignOut
    )