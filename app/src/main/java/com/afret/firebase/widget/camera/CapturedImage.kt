package com.afret.firebase.widget.camera

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.afret.firebase.R
import com.afret.firebase.theme.IbarraNovaBoldPlatinum25


@Composable
fun CapturedImage(uri: Uri?, onRetry: () -> Unit, onOk: () -> Unit) {


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {


        val (imageConstraint, rowConstraint) = createRefs()


        val topGuidLine = createGuidelineFromBottom(0.15f)


        Image(
            painter = rememberAsyncImagePainter(model = uri),
            contentDescription = "captured_image",
            modifier = Modifier.constrainAs(imageConstraint) {

                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )


        Row(modifier = Modifier
            .constrainAs(rowConstraint) {

                top.linkTo(topGuidLine)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                start.linkTo(parent.start)

            }) {


            // On Retry
            Text(
                text = stringResource(id = R.string.retry), style = IbarraNovaBoldPlatinum25,
                textAlign = TextAlign.Center, modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onRetry()
                    }
            )

            //OK
            Text(
                text = stringResource(id = R.string.ok), style = IbarraNovaBoldPlatinum25,
                textAlign = TextAlign.Center, modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onOk()
                    }
            )

        }
    }


}