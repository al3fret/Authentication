package com.afret.authentication.presentation.authentication.signin

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
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(

    private val authenticationUseCases: AuthenticationUseCases
) : BaseValidationViewModel() {


    private val _signInState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signInState: State<Response<Boolean>> = _signInState


    private var emailValidationState =
        ValidationState(id = SignInTextFieldId.EMAIL, type = TextFieldType.Email)

    private var passwordValidationState =
        ValidationState(id = SignInTextFieldId.PASSWORD, type = TextFieldType.Password)


    init {

        forms[SignInTextFieldId.EMAIL] = emailValidationState
        forms[SignInTextFieldId.PASSWORD] = passwordValidationState
    }

    fun firebaseSignIn(email: String, password: String) {

        viewModelScope.launch {

            authenticationUseCases.firebaseSignIn(email = email, password = password).collect {
                _signInState.value = it
            }

        }
    }
}

enum class SignInTextFieldId : TextFieldId {
    EMAIL, PASSWORD
}