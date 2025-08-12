package com.fallsview.slotttts.game.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chicken.two.R
import com.chicken.two.ui.custom.Background
import com.chicken.two.ui.custom.DefaultIconButton
import com.chicken.two.ui.theme.DefCorner
import com.chicken.two.ui.theme.DefFont
import com.chicken.two.util.lockOrientation

@Composable
fun AboutScreen(navController: NavController, paddingValues: PaddingValues) {
    BackHandler {}
    val context = LocalContext.current
    val activity = context as? Activity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Background()
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DefaultIconButton(R.drawable.ic_back) {
                    navController.popBackStack()
                }
                Text(
                    text = "About",
                    style = TextStyle(
                        color = White,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontFamily = DefFont
                    )
                )
                Box(modifier = Modifier.size(40.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.about_text),
                style = TextStyle(
                    fontFamily = DefFont,
                    fontSize = 22.sp,
                    color = Black,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Black
                ),
                modifier = Modifier
                    .background(White, DefCorner)
                    .padding(horizontal = 8.dp, vertical = 25.dp)
            )
        }
    }
}
