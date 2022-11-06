package com.afret.authentication.validation.event

import com.afret.authentication.validation.state.ValidationState

sealed class ValidationEvent{

    object Submit: ValidationEvent()
    data class TextFieldValueChange(val state:ValidationState):ValidationEvent()
}
