package com.afret.authentication.di

import com.afret.authentication.data.repositories.AuthenticationRepositoryImpl
import com.afret.authentication.domin.repositories.AuthenticationRepository
import com.afret.authentication.domin.use_cases.authentication.AuthenticationUseCases
import com.afret.authentication.domin.use_cases.authentication.FirebaseSignUp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {


    @Singleton
    @Provides
    fun provideAuthenticationRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): AuthenticationRepository     {

        return AuthenticationRepositoryImpl(
            firebaseAuth = firebaseAuth,
            firebaseFirestore = firebaseFirestore
        )
    }


    @Singleton
    @Provides
    fun provideAuthenticationUseCases(
        repository: AuthenticationRepository
    ) = AuthenticationUseCases(firebaseSignUp = FirebaseSignUp(repository = repository))
}