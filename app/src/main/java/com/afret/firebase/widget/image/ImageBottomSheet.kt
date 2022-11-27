package com.afret.firebase.widget.image

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.afret.firebase.R
import com.afret.firebase.theme.ColorGunmetal
import com.afret.firebase.theme.ColorPlatinum
import com.afret.firebase.theme.IbarraNovaBoldGunmetal20
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch


@ExperimentalPermissionsApi
@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterialApi
@Composable
fun ImageBottomSheet(
    onCameraClick: () -> Unit,
    onGalleryClick: (uri: Uri?) -> Unit,
) {

    val scope = rememberCoroutineScope()
    val cameraPermissionsState = rememberPermissionState(Manifest.permission.CAMERA)

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->


        onGalleryClick(uri)

    }


    Column(

        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = ColorPlatinum
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(15.dp))


        Text(text = stringResource(id = R.string.select_photo), style = IbarraNovaBoldGunmetal20)

        Spacer(modifier = Modifier.height(25.dp))


        Row {


            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable {

                        if (!cameraPermissionsState.status.isGranted) {
                            cameraPermissionsState.launchPermissionRequest()
                        } else {
                            onCameraClick()
                        }

                    }, contentAlignment = Alignment.Center
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "camera_icon",
                    modifier = Modifier.size(50.dp),
                    tint = ColorGunmetal
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        scope.launch {
                            launcher.launch("image/*")
                        }

                    }, contentAlignment = Alignment.Center
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.gallery),
                    contentDescription = "gallery_icon",
                    modifier = Modifier.size(50.dp),
                    tint = ColorGunmetal
                )
            }


        }

        Spacer(modifier = Modifier.height(25.dp))

    }

}

