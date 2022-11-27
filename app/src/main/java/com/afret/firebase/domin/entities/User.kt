package com.afret.firebase.domin.entities

data class User(
    var userId: String? = null,
    var fullName: String? = null,
    var email: String? = null,
    var password: String? = null,
    var bio: String? = null,
    var imageUrl: String? = null,
)
