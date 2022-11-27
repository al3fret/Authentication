package com.afret.firebase.validation.state

import androidx.annotation.StringRes
import com.afret.firebase.util.TextFieldType
import com.afret.firebase.validation.inerfaces.TextFieldId

data class ValidationState(
    var text:String = "",
    val type:TextFieldType = TextFieldType.Text,
    val id:TextFieldId,
    val isRequired:Boolean = true,
    var hasError:Boolean = true,
    @StringRes val errorMessageId: Int? = null,
)
