package com.chicken.two.ui.screens.splash

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chicken.two.R
import com.chicken.two.domain.LoadingState
import com.chicken.two.navigation.ScreenRoutes
import com.chicken.two.ui.custom.Background
import com.chicken.two.util.lockOrientation
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    BackHandler { }
    val context = LocalContext.current
    val activity = context as? Activity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val state = viewModel.liveState.collectAsState().value


    val permission = android.Manifest.permission.POST_NOTIFICATIONS
    val permissionState = remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionState.value = isGranted
        viewModel.updatePermissionState(isGranted)
    }

    LaunchedEffect(Unit) {
        launcher.launch(permission)
    }

    LaunchedEffect(state) {
        when (state) {
            LoadingState.InitState -> {
                viewModel.load(context)
            }

            LoadingState.NoNetworkState -> {
                navController.navigate(ScreenRoutes.NoNetworkScreen.route) {
                    popUpTo(0)
                }
            }

            is LoadingState.ContentState -> {
                val url = URLEncoder.encode(state.url, StandardCharsets.UTF_8.toString())
                val route = "${ScreenRoutes.ContentScreen.route}/$url"
                navController.navigate(route)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Background(R.drawable.bg)

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.chicken))
            val progress by animateLottieCompositionAsState(
                composition,
                iterations = LottieConstants.IterateForever
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(250.dp)
            )
        }
    }
}



