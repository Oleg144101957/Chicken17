package com.chicken.two.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.chicken.two.R
import com.chicken.two.navigation.ScreenRoutes
import com.chicken.two.ui.custom.Background
import com.chicken.two.ui.custom.ImageButton
import com.chicken.two.util.lockOrientation

@Composable
fun HomeScreen(navController: NavController, paddingValues: PaddingValues) {


    val context = LocalContext.current
    val activity = context as? Activity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    BackHandler {}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Background()
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ImageButton("Play", R.drawable.btn_bg, modifier = Modifier) {
                navController.navigate(ScreenRoutes.GameScreen.route)
            }


            Spacer(modifier = Modifier.height(32.dp))

            ImageButton("Settings", R.drawable.btn_bg, modifier = Modifier) {
                navController.navigate(ScreenRoutes.SettingsScreen.route)
            }

            Spacer(modifier = Modifier.height(16.dp))

            ImageButton("Exit", R.drawable.btn_bg, modifier = Modifier) {
                activity?.finish()
            }
        }
    }
}


