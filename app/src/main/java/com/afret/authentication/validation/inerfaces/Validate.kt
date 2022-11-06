package com.afret.authentication.validation.inerfaces

import com.afret.authentication.validation.state.ValidationResultState

interface Validate {

    fun execute(text: String): ValidationResultState
}

