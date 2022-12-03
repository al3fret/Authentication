package com.afret.authentication.domin.use_cases.authentication

import com.afret.authentication.domin.repositories.AuthenticationRepository
import javax.inject.Inject

class FirebaseSignIn @Inject constructor(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(email: String, password: String) =
        repository.firebaseSignIn(email = email, password = password)
}