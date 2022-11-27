package com.afret.firebase.presentation.authentication.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.afret.firebase.domin.authentication_use_case.AuthenticationUseCase
import com.afret.firebase.domin.entities.Response
import com.afret.firebase.presentation.base.BaseValidationViewModel
import com.afret.firebase.util.TextFieldType
import com.afret.firebase.validation.inerfaces.TextFieldId
import com.afret.firebase.validation.state.ValidationState
import com.google.firebase.firestore.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(private val authenticationUseCase: AuthenticationUseCase) :
    BaseValidationViewModel() {


    private var emailValidationState =
        ValidationState(id = SignInTextFieldId.EMAIL, type = TextFieldType.Email)

    private var passwordValidationState =
        ValidationState(id = SignInTextFieldId.PASSWORD, type = TextFieldType.Password)


    private val _signInState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signInState: State<Response<Boolean>> = _signInState


    fun signIn(email: String, password: String) {

        viewModelScope.launch {

            authenticationUseCase.firebaseSignIn(email = email, password = password).collect { result ->

                when (result) {
                    is Response.Success -> {

                        //  authenticationUseCase.setUserStateInDataStore(isLoggedIn = true)
                    }

                    is Response.Error -> {
                        if (BuildConfig.DEBUG)
                            println(result.message)
                    }
                    is Response.Loading -> {

                    }

                }

                _signInState.value = result

            }
        }

    }
    init {

        forms[SignInTextFieldId.EMAIL] = emailValidationState
        forms[SignInTextFieldId.PASSWORD] = passwordValidationState
    }
}

enum class SignInTextFieldId : TextFieldId {
    EMAIL, PASSWORD
}