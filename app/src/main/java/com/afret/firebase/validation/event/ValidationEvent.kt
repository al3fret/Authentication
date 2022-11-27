package com.afret.firebase.validation.event

import com.afret.firebase.validation.state.ValidationState

sealed class ValidationEvent{

    object Submit: ValidationEvent()
    data class TextFieldValueChange(val state:ValidationState):ValidationEvent()
}
