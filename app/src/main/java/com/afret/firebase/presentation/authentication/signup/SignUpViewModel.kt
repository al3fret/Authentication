package com.afret.firebase.presentation.authentication.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.afret.firebase.domin.authentication_use_case.AuthenticationUseCase
import com.afret.firebase.domin.entities.Response
import com.afret.firebase.presentation.base.BaseValidationViewModel
import com.afret.firebase.util.TextFieldType
import com.afret.firebase.validation.inerfaces.TextFieldId
import com.afret.firebase.validation.state.ValidationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase

):BaseValidationViewModel(){


    private var fullNameValidationState =
        ValidationState(type = TextFieldType.Text, id = SignUpTextFieldId.FULL_NAME)

    private var emailValidationState =
        ValidationState(type = TextFieldType.Email, id = SignUpTextFieldId.EMAIL)

    private var passwordValidationState =
        ValidationState(type = TextFieldType.Password, id = SignUpTextFieldId.PASSWORD)



    private val _signUpState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signUpState: State<Response<Boolean>> = _signUpState


    fun signUp(fullName: String, email: String, password: String) {


        viewModelScope.launch {


            authenticationUseCase.firebaseSignUp(
                email = email,
                password = password,
                fullName = fullName
            ).collect {

                _signUpState.value = it
            }
        }

    }
    init {
        forms[SignUpTextFieldId.FULL_NAME] = fullNameValidationState
        forms[SignUpTextFieldId.EMAIL] = emailValidationState
        forms[SignUpTextFieldId.PASSWORD] = passwordValidationState
    }
}


enum class SignUpTextFieldId : TextFieldId {
    FULL_NAME, EMAIL, PASSWORD
}
