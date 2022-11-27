package com.afret.firebase.widget.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.afret.firebase.theme.ColorPlatinum


@Composable
fun RoundedImage(
    painter: Painter? = null,
    contentDescription: String
) {

    val modifier = Modifier
        .size(125.dp)
        .aspectRatio(1f, matchHeightConstraintsFirst = true).border(
            width = 2.dp,
            color = ColorPlatinum,
            shape = CircleShape
        ) .clip(CircleShape)


    Image(
    painter = painter!!,
    contentDescription = contentDescription,
    modifier = modifier,
    contentScale = ContentScale.Crop,
    )


}