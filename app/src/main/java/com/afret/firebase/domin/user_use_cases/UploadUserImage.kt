package com.afret.firebase.domin.user_use_cases

import android.net.Uri
import com.afret.firebase.domin.entities.User
import com.afret.firebase.domin.repository.UserRepository
import javax.inject.Inject


class UploadUserImage @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(uri: Uri, userId: String) =
        repository.uploadUserImage(uri = uri, userId = userId)
}