package com.afret.firebase.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afret.firebase.domin.authentication_use_case.AuthenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    var isUserAuthenticated = false

    private val _isLoadingSplash = MutableStateFlow(true)
    val isLoadingSplash = _isLoadingSplash.asStateFlow()

    init {

        viewModelScope.launch {
            isUserAuthenticated = authenticationUseCase.isUserAuthenticated()

            _isLoadingSplash.value = false
        }

    }


}