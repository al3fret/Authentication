package com.afret.firebase.domin.user_use_cases

import com.afret.firebase.domin.entities.User
import com.afret.firebase.domin.repository.UserRepository
import javax.inject.Inject


class SetUserDetails @Inject constructor(

    private val repository: UserRepository
) {

    suspend operator fun invoke(user: User) = repository.updateUserInformation(user)
}