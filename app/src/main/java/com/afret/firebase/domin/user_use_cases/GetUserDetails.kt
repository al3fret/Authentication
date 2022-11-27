package com.afret.firebase.domin.user_use_cases

import com.afret.firebase.domin.repository.UserRepository
import javax.inject.Inject


class GetUserDetails @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(userId: String) = repository.getUserInformation(userId)
}