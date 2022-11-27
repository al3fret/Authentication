package com.afret.firebase.data.repository


import android.net.Uri
import com.afret.firebase.domin.entities.Response
import com.afret.firebase.domin.entities.User
import com.afret.firebase.domin.repository.UserRepository
import com.afret.firebase.network.NetworkConstant.COLLECTION_NAME_USERS
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : UserRepository {

    private var operationSuccessful = false
    override suspend fun getUserInformation(userID: String): Flow<Response<User>> = callbackFlow {

        Response.Loading

        val snapshotListener = firebaseFirestore.collection(COLLECTION_NAME_USERS)
            .document(userID)
            .addSnapshotListener { snapshot, error ->

                val response = if (snapshot != null) {


                    println("bilalss $snapshot");
                    val userInfo = snapshot.toObject(User::class.java)
                    if (userInfo != null) Response.Success(userInfo) else Response.Error("Un Expected Error")
                } else {
                    Response.Error(error!!.message.toString())
                }

                trySend(response).isSuccess
            }

        awaitClose {
            snapshotListener.remove()
        }

    }

    override suspend fun updateUserInformation(
        user: User
    ): Flow<Response<Boolean>> = flow {


        operationSuccessful = false

        try {
            emit(Response.Loading)

            val userObj = mutableMapOf<String, String>()

            if (user.fullName != null)
                userObj["fullName"] = user.fullName!!

            if (user.bio != null)
                userObj["bio"] = user.bio!!

            if (user.email != null)
                userObj["email"] = user.email!!

            firebaseFirestore.collection(COLLECTION_NAME_USERS).document(user.userId!!)
                .update(userObj as Map<String, Any>)
                .addOnSuccessListener {
                    operationSuccessful = true

                }.await()

            if (operationSuccessful) {
                println("asdasdasd operationSuccessful")

                emit(Response.Success(operationSuccessful))
            } else {
                println("asdasdasd not operationSuccessful")

                emit(Response.Error("Edit Failed"))
            }

        } catch (e: Exception) {

            Response.Error(e.localizedMessage ?: "An Unexpected Error")
        }

    }

    override suspend fun uploadUserImage(
        uri: Uri,
        userId: String
    ): Flow<Response<Boolean>> = flow {


        operationSuccessful = false

        try {
            emit(Response.Loading)

            val storageReference = firebaseStorage.reference

            val uuid = UUID.randomUUID()

            val imageRef = storageReference.child("/images/$uuid")
            val uploadTask = imageRef.putFile(uri)
            var uploadedImagePath = ""
            var result: Task<Uri>? = null


            //image uploaded Successfully
            uploadTask.addOnSuccessListener {
                operationSuccessful = true
                uploadedImagePath = it.toString()
                result = it.metadata?.reference?.downloadUrl


            }.addOnFailureListener {
                operationSuccessful = false
            }.await()


            if (operationSuccessful) {

                operationSuccessful = false
                // get url of uploaded image
                result?.addOnSuccessListener {
                    operationSuccessful = true
                    uploadedImagePath = it.toString()
                }?.await()


                if (operationSuccessful) {
                    operationSuccessful = false
                    val userObj = mutableMapOf<String, String>()
                    userObj["imageUrl"] = uploadedImagePath

                    firebaseFirestore.collection(COLLECTION_NAME_USERS).document(userId)
                        .update(userObj as Map<String, Any>)
                        .addOnSuccessListener {
                            operationSuccessful = true

                        }.await()

                    if (operationSuccessful) {

                        emit(Response.Success(operationSuccessful))
                    } else {
                        emit(Response.Error("Set User Image Failed"))
                    }
                } else {
                    emit(Response.Error("Get Url from Image Failed"))

                }
            } else {
                emit(Response.Error("Upload Image Failed"))
            }

        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "An Unexpected Error")
        }

    }


}