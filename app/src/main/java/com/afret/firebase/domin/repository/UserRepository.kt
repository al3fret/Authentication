package com.afret.firebase.domin.repository

import android.net.Uri
import com.afret.firebase.domin.entities.Response
import com.afret.firebase.domin.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUserInformation(userID: String): Flow<Response<User>>
    suspend fun updateUserInformation(user: User): Flow<Response<Boolean>>
    suspend fun uploadUserImage(uri: Uri, userId: String): Flow<Response<Boolean>>

}