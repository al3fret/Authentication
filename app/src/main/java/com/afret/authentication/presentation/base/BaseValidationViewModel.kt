package com.afret.authentication.presentation.base

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afret.authentication.util.TextFieldType
import com.afret.authentication.validation.event.ValidationEvent
import com.afret.authentication.validation.event.ValidationResultEvent
import com.afret.authentication.validation.inerfaces.TextFieldId
import com.afret.authentication.validation.state.ValidationState
import com.afret.authentication.validation.use_case.ValidateEmail
import com.afret.authentication.validation.use_case.ValidatePassword
import com.afret.authentication.validation.use_case.ValidateText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
open class BaseValidationViewModel @Inject constructor() : ViewModel() {


    val forms = mutableStateMapOf<TextFieldId, ValidationState>()


    private val validationEventChannel = Channel<ValidationResultEvent>()
    val validationEvent = validationEventChannel.receiveAsFlow()


    fun onEvent(event: ValidationEvent) {


        when (event) {

            is ValidationEvent.TextFieldValueChange -> {

                val validate = when (event.state.type) {

                    TextFieldType.Text -> ValidateText()
                    TextFieldType.Email -> ValidateEmail()
                    TextFieldType.Password -> ValidatePassword()
                }


                val result = validate.execute(text = event.state.text.trim())


                forms[event.state.id] = if (result.isValid) {

                    event.state.copy(hasError = false, errorMessageId = null)
                } else {
                    event.state.copy(hasError = true, errorMessageId = result.errorMessageId)

                }
            }

            is ValidationEvent.Submit -> isValidForm()
        }
    }


    private fun isValidForm() {


        var isValid = true

        for (state in forms.values) {

            onEvent(ValidationEvent.TextFieldValueChange(state = state))

            if (state.isRequired && state.hasError) {
                isValid = false
            }
        }



        if (isValid) {

            viewModelScope.launch {

                validationEventChannel.send(ValidationResultEvent.Success)
            }

        }
    }


}