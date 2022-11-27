package com.afret.firebase.di

import com.afret.firebase.domin.repository.AuthenticationRepository
import com.afret.authentication.domin.use_case.*
import com.afret.firebase.data.repository.AuthenticationRepositoryImpl
import com.afret.firebase.data.repository.UserRepositoryImpl
import com.afret.firebase.domin.authentication_use_case.AuthenticationUseCase
import com.afret.firebase.domin.authentication_use_case.FirebaseSignUp
import com.afret.firebase.domin.repository.UserRepository
import com.afret.firebase.domin.user_use_cases.GetUserDetails
import com.afret.firebase.domin.user_use_cases.SetUserDetails
import com.afret.firebase.domin.user_use_cases.UploadUserImage
import com.afret.firebase.domin.user_use_cases.UserUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideFirebaseStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }




    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideAuthenticationRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(
            firebaseAuth = firebaseAuth,
            firebaseFirestore = firebaseFirestore,
        )
    }


    @Singleton
    @Provides
    fun provideUserRepository(firebaseFirestore: FirebaseFirestore,firebaseStorage: FirebaseStorage): UserRepository {
        return UserRepositoryImpl(firebaseFirestore = firebaseFirestore,
            firebaseStorage = firebaseStorage)
    }

    @Singleton
    @Provides
    fun provideUserUseCases(repository: UserRepository) = UserUseCases(
        getUserDetails = GetUserDetails(repository = repository),
        setUserDetails = SetUserDetails(repository = repository),
        uploadUserImage = UploadUserImage(repository = repository),
    )

    @Singleton
    @Provides
    fun provideAuthenticationUseCase(repository: AuthenticationRepository) =
        AuthenticationUseCase(
            firebaseSignUp = FirebaseSignUp(repository = repository),
            firebaseSignIn = FirebaseSignIn(repository = repository),
            isUserAuthenticated = FirebaseIsUserAuthenticated(repository = repository),
            firebaseSignOut = FirebaseSignOut(repository = repository)
        )


}