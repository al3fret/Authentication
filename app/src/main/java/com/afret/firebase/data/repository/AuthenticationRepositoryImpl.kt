package com.afret.firebase.data.repository

import com.afret.firebase.domin.repository.AuthenticationRepository
import com.afret.firebase.domin.entities.Response
import com.afret.firebase.domin.entities.User
import com.afret.firebase.network.NetworkConstant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.BuildConfig
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@ExperimentalCoroutinesApi
class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
) : AuthenticationRepository {


    override suspend fun firebaseSignUp(
        fullName: String,
        email: String,
        password: String,

        ): Flow<Response<Boolean>> = flow {

        var signUpSuccessful = false

        try {


            if (BuildConfig.DEBUG) {
                println("Bilalof email $email")
                println("Bilalof password $password")
            }

            emit(Response.Loading)

            firebaseAuth.createUserWithEmailAndPassword(email.trim(), password)
                .addOnSuccessListener {
                    signUpSuccessful = true
                }.await()




            if (signUpSuccessful) {
                val userId = firebaseAuth.currentUser?.uid!!
                val obj = User(
                    fullName = fullName, email = email, password = password,
                    userId = userId
                )

                firebaseFirestore.collection(NetworkConstant.COLLECTION_NAME_USERS).document(userId)
                    .set(obj)
                    .addOnSuccessListener { }.await()
                emit(Response.Success(true))

            } else {
                emit(Response.Error("Failed To SignUp"))
            }


        } catch (e: Exception) {

            emit(Response.Error(e.localizedMessage ?: "An Unexpected Error"))
        }

    }


    override suspend fun firebaseSignOut(): Flow<Response<Boolean>> =
        flow {


            emit(Response.Loading)

            try {
                firebaseAuth.signOut()

                emit(Response.Success(true))

            } catch (e: Exception) {

                emit(Response.Error(e.localizedMessage ?: "An Unexpected Error"))
            }

        }





    override suspend fun firebaseIsUserAuthenticated(): Boolean {


        return firebaseAuth.currentUser != null
    }


    override suspend fun firebaseSignIn(email: String, password: String): Flow<Response<Boolean>> =
        flow {

            var signInSuccessful = false


            if (BuildConfig.DEBUG) {
                println("Bilalof email $email")
                println("Bilalof password $password")
            }

            emit(Response.Loading)


            try {

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    signInSuccessful = true
                }.await()

                if (signInSuccessful) {
                    emit(Response.Success(true))
                } else {
                    emit(Response.Error("Failed To SignIn"))
                }
            } catch (e: Exception) {
                emit(Response.Error(e.localizedMessage ?: "An Unexpected Error"))

            }
        }


}
