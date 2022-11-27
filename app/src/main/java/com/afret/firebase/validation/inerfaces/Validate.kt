package com.afret.firebase.validation.inerfaces

import com.afret.firebase.validation.state.ValidationResultState

interface Validate {

    fun execute(text: String): ValidationResultState
}

