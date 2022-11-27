package com.afret.authentication.domin.use_case

import com.afret.firebase.domin.repository.AuthenticationRepository
import javax.inject.Inject

class FirebaseIsUserAuthenticated @Inject constructor(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke() = repository.firebaseIsUserAuthenticated()
}