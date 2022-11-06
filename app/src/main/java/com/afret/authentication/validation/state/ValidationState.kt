package com.afret.authentication.validation.state

import androidx.annotation.StringRes
import com.afret.authentication.util.TextFieldType
import com.afret.authentication.validation.inerfaces.TextFieldId

data class ValidationState(
    var text:String = "",
    val type:TextFieldType = TextFieldType.Text,
    val id:TextFieldId,
    val isRequired:Boolean = true,
    var hasError:Boolean = true,
    @StringRes val errorMessageId: Int? = null,
)
