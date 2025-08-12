package com.chicken.two.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chicken.two.R
import com.chicken.two.ui.theme.GreenBtn

@Composable
fun DefaultButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        enabled = enabled,
        modifier = modifier
            .shadow(8.dp)
            .background(GreenBtn)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.helvetica)),
                fontSize = 24.sp,
                color = White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun DefaultIconButton(iconRes: Int = R.drawable.ic_settings, onClick: () -> Unit) {
    IconButton(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .size(40.dp)
            .shadow(8.dp)
            .background(GreenBtn)
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = iconRes.toString(),
            modifier = Modifier.size(24.dp)
        )
    }
}
