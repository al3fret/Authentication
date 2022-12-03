package com.afret.authentication.domin.repositories

import com.afret.authentication.domin.entites.Response
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {


    suspend fun firebaseSignUp(
        fullName: String,
        email: String,
        password: String,
    ): Flow<Response<Boolean>>


    suspend fun firebaseSignIn(
        email: String,
        password: String
    ): Flow<Response<Boolean>>
}