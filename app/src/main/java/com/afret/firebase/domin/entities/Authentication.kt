package com.afret.firebase.domin.entities

data class UserState(
    val user: User = User(),
    val isLoggedIn: Boolean = false
)
