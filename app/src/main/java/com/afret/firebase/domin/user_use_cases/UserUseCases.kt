package com.afret.firebase.domin.user_use_cases



data class UserUseCases(
    val getUserDetails: GetUserDetails,
    val setUserDetails: SetUserDetails,
    val uploadUserImage: UploadUserImage,
)