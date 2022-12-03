package com.afret.authentication.presentation.authentication.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.afret.authentication.domin.entites.Response
import com.afret.authentication.domin.use_cases.authentication.AuthenticationUseCases
import com.afret.authentication.presentation.base.BaseValidationViewModel
import com.afret.authentication.util.TextFieldType
import com.afret.authentication.validation.inerfaces.TextFieldId
import com.afret.authentication.validation.state.ValidationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(

    private val authenticationUseCases: AuthenticationUseCases
) : BaseValidationViewModel() {


    private val _signUpState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signUpState: State<Response<Boolean>> = _signUpState

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

    fun firebaseSignUp(fullName: String, email: String, password: String) {

        viewModelScope.launch {

            authenticationUseCases.firebaseSignUp(
                fullName = fullName,
                email = email,
                password = password
            ).collect {
                _signUpState.value = it
            }
        }

    }

}


enum class SignUpTextFieldId : TextFieldId {
    FULL_NAME, EMAIL, PASSWORD
}
