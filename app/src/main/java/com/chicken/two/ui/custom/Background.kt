package com.chicken.two.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.chicken.two.R

@Composable
fun Background(imageRes: Int = R.drawable.bg) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = imageRes.toString(),
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}
