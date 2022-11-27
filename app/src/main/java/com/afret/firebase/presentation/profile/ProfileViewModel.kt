package com.afret.firebase.presentation.profile

import android.net.Uri
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.afret.firebase.domin.authentication_use_case.AuthenticationUseCase
import com.afret.firebase.domin.entities.Response
import com.afret.firebase.domin.entities.User
import com.afret.firebase.domin.user_use_cases.UserUseCases
import com.afret.firebase.presentation.base.BaseValidationViewModel
import com.afret.firebase.util.TextFieldType
import com.afret.firebase.validation.inerfaces.TextFieldId
import com.afret.firebase.validation.state.ValidationState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalMaterialApi
@HiltViewModel
class ProfileViewModel @Inject constructor(
    auth: FirebaseAuth,
    private val authenticationUseCases: AuthenticationUseCase,
    private val userUseCases: UserUseCases,

    ) : BaseValidationViewModel() {

    private val userId = auth.currentUser?.uid

    private val _setUserDataState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val setUserDataState: State<Response<Boolean>> = _setUserDataState

    private val _updateImageState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val updateImageState: State<Response<Boolean>> = _updateImageState

    private val fullNameValidationState = ValidationState(id = ProfileTextFieldId.FULLNAME)

    private val emailValidationState =
        ValidationState(type = TextFieldType.Email, id = ProfileTextFieldId.EMAIL)

    private val bioValidationState = ValidationState(id = ProfileTextFieldId.BIO)

    private val _bottomSheetState = MutableStateFlow(false)
    var bottomSheetState = _bottomSheetState.asStateFlow()

    private var _shouldShowLogoutDialog = MutableStateFlow(false)
    var shouldShowLogoutDialog = _shouldShowLogoutDialog.asStateFlow()

    private var _isApiLoading = MutableStateFlow(false)
    var isApiLoading = _isApiLoading.asStateFlow()


    private val _shouldShowCamera = MutableStateFlow(false)
    var shouldShowCamera = _shouldShowCamera.asStateFlow()


    private val _capturedImageUri = MutableStateFlow<Uri?>(null)
    var capturedImageUri = _capturedImageUri.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    var user = _user.asStateFlow()

    private val _shouldShowCapturedImage = MutableStateFlow(false)
    var shouldShowCapturedImage = _shouldShowCapturedImage.asStateFlow()


    private val _signOutState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signOutState: State<Response<Boolean>> = _signOutState

    init {

        forms[ProfileTextFieldId.FULLNAME] = fullNameValidationState
        forms[ProfileTextFieldId.EMAIL] = emailValidationState
        forms[ProfileTextFieldId.BIO] = bioValidationState
    }


    private val _getUserData = mutableStateOf<Response<User?>>(Response.Success(null))
    val getUserData: State<Response<User?>> = _getUserData


    fun setUserInfo(resultUsers: User) {

        _user.value = resultUsers
        forms[ProfileTextFieldId.BIO]!!.text = if (_user.value!!.bio != null) _user.value!!.bio!! else ""
        forms[ProfileTextFieldId.EMAIL]!!.text = if (_user.value!!.email != null) _user.value!!.email!! else ""
        forms[ProfileTextFieldId.FULLNAME]!!.text =
            if (_user.value != null) _user.value!!.fullName!! else ""
    }

    fun setUserInfoApi(user: User) {


        user.userId = userId!!

        if (user.userId != null) {

            viewModelScope.launch {

                _isApiLoading.value = true
                userUseCases.setUserDetails(user = user).collect {
                    _isApiLoading.value = false

                    _setUserDataState.value = it
                }
            }
        }

    }


    fun getUserInfoApi() {

        if (userId != null) {

            viewModelScope.launch {
                _isApiLoading.value = true
                userUseCases.getUserDetails(userId = userId).collect {

                    _isApiLoading.value = false
                    _getUserData.value = it
                }
            }
        }
    }

    fun updateImageToApi(uri: Uri) {
        viewModelScope.launch {
            _isApiLoading.value = true
            userUseCases.uploadUserImage(uri = uri, userId = userId!!).collect {
                _isApiLoading.value = false
                _updateImageState.value = it
            }
        }
    }

    fun onOkCapturedImage() {
        _shouldShowCamera.value = false
        _shouldShowCapturedImage.value = false
        _bottomSheetState.value = false

    }

    fun onRetryCapturedImage() {
        _shouldShowCamera.value = true
        _shouldShowCapturedImage.value = false
        _capturedImageUri.value = null

    }

    fun onImageCaptured(uri: Uri) {

        _shouldShowCamera.value = false
        _capturedImageUri.value = uri
        _shouldShowCapturedImage.value = true

        updateImageToApi(uri = uri)

    }

    fun onCameraClick() {

        _shouldShowCamera.value = true
        _shouldShowCapturedImage.value = false
        _capturedImageUri.value = null

    }

    fun onGalleryClick(uri: Uri?) {

        _bottomSheetState.value = false
        _capturedImageUri.value = uri

        if(uri !=null)
        updateImageToApi(uri = uri)


    }


    fun closeLogoutDialog() {

        _shouldShowLogoutDialog.value = false
    }

    fun showLogoutDialog() {

        _shouldShowLogoutDialog.value = true
    }

    fun signOut() {

        _isApiLoading.value = true
        viewModelScope.launch {
            authenticationUseCases.firebaseSignOut().collect {
                _isApiLoading.value = false
                _signOutState.value = it
            }
        }
    }

}

enum class ProfileTextFieldId : TextFieldId {
    FULLNAME, EMAIL, BIO
}