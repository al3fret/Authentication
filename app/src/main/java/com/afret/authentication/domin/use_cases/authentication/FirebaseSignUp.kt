package com.afret.authentication.domin.use_cases.authentication

import com.afret.authentication.domin.repositories.AuthenticationRepository
import javax.inject.Inject

class FirebaseSignUp @Inject constructor(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(fullName: String, email: String, password: String) =
        repository.firebaseSignUp(
            fullName = fullName, email = email, password = password
        )
}