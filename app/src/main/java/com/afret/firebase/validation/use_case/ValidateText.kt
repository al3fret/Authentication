package com.afret.firebase.validation.use_case

import com.afret.firebase.R
import com.afret.firebase.validation.inerfaces.Validate
import com.afret.firebase.validation.state.ValidationResultState

class ValidateText : Validate {
    override fun execute(text: String): ValidationResultState {


        return if (text.isBlank()) {

            ValidationResultState(
                isValid = false,
                errorMessageId = R.string.the_field_can_not_be_blank
            )
        } else {

            ValidationResultState(isValid = true)
        }
    }
}