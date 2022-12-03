package com.afret.authentication.data.repositories

import com.afret.authentication.domin.entites.Response
import com.afret.authentication.domin.entites.User
import com.afret.authentication.domin.repositories.AuthenticationRepository
import com.afret.authentication.network.NetworkConstant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : AuthenticationRepository {
    override suspend fun firebaseSignUp(
        fullName: String,
        email: String,
        password: String
    ): Flow<Response<Boolean>> = flow {

        var signUpSuccessfully = false

        emit(Response.Loading)

        try {


            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                signUpSuccessfully = true
            }.await()




            if (signUpSuccessfully) {

                val userId = firebaseAuth.currentUser?.uid!!

                val obj = User(fullName = fullName, email = email, userId = userId)



                firebaseFirestore.collection(NetworkConstant.COLLECTION_NAME_USERS).document(userId)
                    .set(obj).addOnSuccessListener {
                    }.await()

                emit(Response.Success(true))
            } else {

                emit(Response.Error("Failed To Sign Up"))
            }

        } catch (e: Exception) {

            emit(Response.Error(e.localizedMessage ?: "An Unexpected Error"))
        }

    }
}