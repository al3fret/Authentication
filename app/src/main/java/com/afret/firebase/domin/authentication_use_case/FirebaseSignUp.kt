package com.afret.firebase.domin.authentication_use_case

import com.afret.firebase.domin.repository.AuthenticationRepository
import javax.inject.Inject

class FirebaseSignUp @Inject constructor(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(fullName: String, email: String, password: String) =
        repository.firebaseSignUp(fullName = fullName, email = email, password = password)
}