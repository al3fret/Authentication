package com.afret.firebase.domin.repository

import com.afret.firebase.domin.entities.Response
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    suspend fun firebaseIsUserAuthenticated(): Boolean
    suspend fun firebaseSignIn(email: String, password: String): Flow<Response<Boolean>>
    suspend fun firebaseSignUp(
        fullName: String,
        email: String,
        password: String,
    ): Flow<Response<Boolean>>

    suspend fun firebaseSignOut(): Flow<Response<Boolean>>


}