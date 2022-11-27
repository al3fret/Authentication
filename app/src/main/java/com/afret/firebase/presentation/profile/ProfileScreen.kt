package com.afret.firebase.presentation.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.afret.firebase.R
import com.afret.firebase.domin.entities.Response
import com.afret.firebase.domin.entities.User
import com.afret.firebase.route.AppScreen
import com.afret.firebase.theme.ColorGunmetal
import com.afret.firebase.theme.IbarraNovaBoldPlatinum25
import com.afret.firebase.theme.IbarraNovaSemiBoldColorRed20
import com.afret.firebase.theme.IbarraNovaSemiBoldColorVerdigris17
import com.afret.firebase.validation.event.ValidationEvent
import com.afret.firebase.validation.event.ValidationResultEvent
import com.afret.firebase.widget.LoadingScreen
import com.afret.firebase.widget.button.AuthenticationButton
import com.afret.firebase.widget.camera.CameraView
import com.afret.firebase.widget.camera.CapturedImage
import com.afret.firebase.widget.dialogs.LogoutDialog
import com.afret.firebase.widget.image.ImageBottomSheet
import com.afret.firebase.widget.image.RoundedImage
import com.afret.firebase.widget.textfield.AuthenticationTextField
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch


@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun ProfileScreen(navController: NavController) {

    val viewModel: ProfileViewModel = hiltViewModel()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val signOutState = viewModel.signOutState.value
    val getUserDataState = viewModel.getUserData.value
    val setUserDataState = viewModel.setUserDataState.value
    val updateImageState = viewModel.updateImageState.value
    val isApiLoadingState by viewModel.isApiLoading.collectAsState()

    val modelBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val shouldShowLogoutDialog by viewModel.shouldShowLogoutDialog.collectAsState()
    val shouldShowCapturedImage by viewModel.shouldShowCapturedImage.collectAsState()
    val shouldShowCamera by viewModel.shouldShowCamera.collectAsState()
    val capturedImageUri by viewModel.capturedImageUri.collectAsState()


    val activity = (LocalContext.current as? Activity)

    LaunchedEffect(key1 = context) {


        viewModel.validationEvent.collect { event ->

            when (event) {

                is ValidationResultEvent.Success -> {

                    viewModel.setUserInfoApi(
                        User(
                            fullName = viewModel.forms[ProfileTextFieldId.FULLNAME]!!.text,
                            bio = viewModel.forms[ProfileTextFieldId.BIO]!!.text,
                            email = viewModel.forms[ProfileTextFieldId.EMAIL]!!.text,
                        )
                    )
                }
            }
        }
    }


    // Get User Information
    LaunchedEffect(key1 = context) {
        viewModel.getUserInfoApi()
    }



    if (setUserDataState is Response.Success)
        if (setUserDataState.data) {
            LaunchedEffect(key1 = context) {
                if (setUserDataState.data)

                    Toast.makeText(
                        context,
                        R.string.user_information_updated_successfully,
                        Toast.LENGTH_LONG
                    ).show()
            }
        }


    if (updateImageState is Response.Success)
        if (updateImageState.data) {
            LaunchedEffect(key1 = context) {
                if (updateImageState.data)

                    Toast.makeText(
                        context,
                        R.string.user_image_updated_successfully,
                        Toast.LENGTH_LONG
                    ).show()
            }
        }

    if (signOutState is Response.Success)
        if (signOutState.data) {
            LaunchedEffect(key1 = context) {
                if (signOutState.data)
                    navController.navigate(AppScreen.SignInScreen.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
            }
        }

    if (getUserDataState is Response.Success)
        if (getUserDataState.data != null) {
            LaunchedEffect(key1 = context) {
                viewModel.setUserInfo(resultUsers = getUserDataState.data)
            }
        }

    if (signOutState is Response.Error) {
        LaunchedEffect(true) {

            Toast.makeText(context, signOutState.message, Toast.LENGTH_LONG).show()
        }

    }

    if (shouldShowCamera) {


        CameraView(

            onImageCaptured = { uri ->


                viewModel.onImageCaptured(uri)

            },
            onError = { Log.e("Bilal", "View error:", it) }
        )
    } else if (shouldShowCapturedImage) {

        CapturedImage(uri = capturedImageUri,
            onOk = {
                viewModel.onOkCapturedImage()
            }, onRetry = {

                viewModel.onRetryCapturedImage()
            })


    } else {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorGunmetal)
        ) {

            ProfileScreenContent(
                viewModel = viewModel,
                modelBottomSheetState = modelBottomSheetState,
                activity = activity,
                onCameraClick = { viewModel.onCameraClick() },
                onGalleryClick = {
                    viewModel.onGalleryClick(it)


                    scope.launch {

                        modelBottomSheetState.hide()
                    }

                }
            )
            if (isApiLoadingState)
                LoadingScreen(modifier = Modifier)

        }


        if (shouldShowLogoutDialog) {
            LogoutDialog(show = true, onConfirm = {

                scope.launch {
                    viewModel.signOut()
                }
            }, onDismiss = {

                viewModel.closeLogoutDialog()

            })
        }


    }
}

@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreenContent(
    viewModel: ProfileViewModel,
    modelBottomSheetState: ModalBottomSheetState,
    activity: Activity?,
    onCameraClick: () -> Unit,
    onGalleryClick: (uri: Uri?) -> Unit
) {

    val capturedImageUri by viewModel.capturedImageUri.collectAsState()

    val scope = rememberCoroutineScope()

    val bottomSheetState by viewModel.bottomSheetState.collectAsState()
    val context = LocalContext.current
    val updateImageState = viewModel.updateImageState.value
    val user by viewModel.user.collectAsState()


    LaunchedEffect(key1 = context) {

        if (bottomSheetState)
            modelBottomSheetState.show() else modelBottomSheetState.hide()
    }



    ModalBottomSheetLayout(
        sheetState = modelBottomSheetState,

        sheetContent = {

            ImageBottomSheet(onCameraClick = {

                onCameraClick()
            }, onGalleryClick = {
                onGalleryClick(it)


            })

        },

        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),

        ) {

        Scaffold(

            backgroundColor = ColorGunmetal,

            content = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {


                    TopAppBar(
                        backgroundColor = ColorGunmetal,
                        elevation = 15.dp,
                        title = {
                            Text(
                                text = stringResource(id = R.string.profile),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                style = IbarraNovaBoldPlatinum25
                            )
                        }, navigationIcon = {
                            //Back Image
                            Image(
                                painter = painterResource(id = R.drawable.ic_back),
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .fillMaxHeight()
                                    .width(35.dp)
                                    .height(30.dp)
                                    .clickable {
                                        activity?.finish()
                                    },
                                contentDescription = "back"
                            )

                        }, actions = {

                            Text(text = stringResource(id = R.string.logout),
                                style = IbarraNovaSemiBoldColorRed20,
                                modifier = Modifier.clickable {
                                    viewModel.showLogoutDialog()
                                })
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    )

                    Spacer(modifier = Modifier.height(50.dp))


                    //Profile Image
                    RoundedImage(
                        painter = if (capturedImageUri != null) {
                            rememberAsyncImagePainter(model = capturedImageUri)// or
                        } else if (user != null) {
                            if (user!!.imageUrl != null) {
                                rememberAsyncImagePainter(model = user!!.imageUrl)// or
                            }else{
                                painterResource(id = R.drawable.profile_pic)

                            }
                        } else {
                            painterResource(id = R.drawable.profile_pic)
                        },
                        contentDescription = "profile_pic"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = stringResource(id = R.string.change_profile_picture),
                        style = IbarraNovaSemiBoldColorVerdigris17,
                        modifier = Modifier.clickable {

                            scope.launch {
                                if (bottomSheetState) {
                                    modelBottomSheetState.hide()
                                } else {
                                    modelBottomSheetState.show()
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(50.dp))


                    /// TextField FullName
                    AuthenticationTextField(modifier = Modifier.fillMaxWidth(0.85f),
                        state = viewModel.forms[ProfileTextFieldId.FULLNAME]!!,
                        hint = R.string.full_name, onValueChange = {
                            viewModel.onEvent(
                                ValidationEvent.TextFieldValueChange(
                                    viewModel.forms[ProfileTextFieldId.FULLNAME]!!.copy(
                                        text = it
                                    )
                                )
                            )
                        })

                    Spacer(modifier = Modifier.height(20.dp))

                    /// TextField Email
                    AuthenticationTextField(modifier = Modifier.fillMaxWidth(0.85f),
                        state = viewModel.forms[ProfileTextFieldId.EMAIL]!!,
                        hint = R.string.email, onValueChange = {
                            viewModel.onEvent(
                                ValidationEvent.TextFieldValueChange(
                                    viewModel.forms[ProfileTextFieldId.EMAIL]!!.copy(
                                        text = it
                                    )
                                )
                            )
                        })


                    Spacer(modifier = Modifier.height(20.dp))


                    /// TextField Bio
                    AuthenticationTextField(modifier = Modifier.fillMaxWidth(0.85f),
                        state = viewModel.forms[ProfileTextFieldId.BIO]!!,
                        hint = R.string.bio, onValueChange = {
                            viewModel.onEvent(
                                ValidationEvent.TextFieldValueChange(
                                    viewModel.forms[ProfileTextFieldId.BIO]!!.copy(
                                        text = it
                                    )
                                )
                            )
                        })

                    Spacer(modifier = Modifier.height(50.dp))

                    AuthenticationButton(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(50.dp), textId = R.string.edit,
                        onClick = {

                            viewModel.onEvent(ValidationEvent.Submit)
                        }
                    )
                    Spacer(modifier = Modifier.height(40.dp))


                }
            },
        )
    }


}

