package com.afret.authentication.presentation.authentication.signin

import com.afret.authentication.presentation.base.BaseValidationViewModel
import com.afret.authentication.util.TextFieldType
import com.afret.authentication.validation.inerfaces.TextFieldId
import com.afret.authentication.validation.state.ValidationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor() : BaseValidationViewModel() {


    private var emailValidationState =
        ValidationState(id = SignInTextFieldId.EMAIL, type = TextFieldType.Email)

    private var passwordValidationState =
        ValidationState(id = SignInTextFieldId.PASSWORD, type = TextFieldType.Password)


    init {

        forms[SignInTextFieldId.EMAIL] = emailValidationState
        forms[SignInTextFieldId.PASSWORD] = passwordValidationState
    }
}

enum class SignInTextFieldId : TextFieldId {
    EMAIL, PASSWORD
}