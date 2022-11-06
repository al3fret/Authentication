package com.afret.authentication.validation.use_case

import com.afret.authentication.R
import com.afret.authentication.validation.inerfaces.Validate
import com.afret.authentication.validation.state.ValidationResultState

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