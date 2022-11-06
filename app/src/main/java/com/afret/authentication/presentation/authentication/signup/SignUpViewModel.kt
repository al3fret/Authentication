package com.afret.authentication.presentation.authentication.signup

import com.afret.authentication.presentation.base.BaseValidationViewModel
import com.afret.authentication.util.TextFieldType
import com.afret.authentication.validation.inerfaces.TextFieldId
import com.afret.authentication.validation.state.ValidationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor():BaseValidationViewModel(){


    private var fullNameValidationState =
        ValidationState(type = TextFieldType.Text, id = SignUpTextFieldId.FULL_NAME)

    private var emailValidationState =
        ValidationState(type = TextFieldType.Email, id = SignUpTextFieldId.EMAIL)

    private var passwordValidationState =
        ValidationState(type = TextFieldType.Password, id = SignUpTextFieldId.PASSWORD)


    init {
        forms[SignUpTextFieldId.FULL_NAME] = fullNameValidationState
        forms[SignUpTextFieldId.EMAIL] = emailValidationState
        forms[SignUpTextFieldId.PASSWORD] = passwordValidationState
    }
}


enum class SignUpTextFieldId : TextFieldId {
    FULL_NAME, EMAIL, PASSWORD
}
