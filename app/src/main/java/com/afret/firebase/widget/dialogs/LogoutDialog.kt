package com.afret.firebase.widget.dialogs

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.afret.firebase.R
import com.afret.firebase.theme.*


@Composable
fun LogoutDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show) {
        AlertDialog(

            backgroundColor = ColorGunmetal,
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = stringResource(id = R.string.logout), style = IbarraNovaSemiBoldColorVerdigris17) }
            },
            dismissButton = {
                TextButton(onClick = onDismiss)
                { Text(text = stringResource(id = R.string.cancel), style = IbarraNovaSemiBoldColorVerdigris17) }
            },
            title = { Text(text = stringResource(id = R.string.logout), style = IbarraNovaSemiBoldPlatinum18) },
            text = {
                Text(
                    text = stringResource(id = R.string.are_you_sure_you_want_to_logout),
                    style = IbarraNovaSemiBoldPlatinum18
                )
            }
        )
    }
}