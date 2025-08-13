package com.chicken.two.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chicken.two.R
import com.chicken.two.ui.custom.ImageButton
import com.chicken.two.util.lockOrientation
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun GameScreen(navController: NavController) {

    val chickenStep = 50f
    val chickenRadius = 25f
    val baseCarSpeed = 10f
    val context = LocalContext.current
    val activity = context as? Activity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    var chickenX by remember { mutableStateOf(0f) }
    var isGameOver by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }

    var isFirstLaunch by remember { mutableStateOf(true) }

    val carImages = listOf(
        R.drawable.car1,
        R.drawable.car2,
    )

    val cars = remember { mutableStateListOf<Pair<Offset, Int>>() }

    val lanes = listOf(
        0.05f,
        0.2f,
        0.35f,
        0.5f,
        0.65f,
        0.8f
    )

    fun resetGame() {
        chickenX = canvasSize.width / 2f
        score = 0
        isGameOver = false
        cars.clear()
    }

    // Генерация машин
    LaunchedEffect(isGameOver, isFirstLaunch) {
        while (!isGameOver && !isFirstLaunch) {
            delay(1000L)
            val laneX = canvasSize.width * lanes.random()
            val startY = -Random.nextInt(200, 800)
            val carType = carImages.random()
            cars.add(Offset(laneX, startY.toFloat()) to carType)
        }
    }

    // Движение машин
    LaunchedEffect(isGameOver, isFirstLaunch) {
        while (!isGameOver && !isFirstLaunch) {
            delay(16L)
            val carSpeed = baseCarSpeed + (score / 1000)

            for (i in cars.indices) {
                val (pos, resId) = cars[i]
                val newY = pos.y + carSpeed
                val height = canvasSize.height.toFloat()
                cars[i] = Offset(pos.x, if (newY > height) -200f else newY) to resId
            }

            val chickenY = canvasSize.height.toFloat() - 100f
            val chickenCenter = Offset(chickenX, chickenY)
            for ((pos, _) in cars) {
                val carRect = Rect(pos, Size(50f, 100f))
                if (circleIntersectsRect(chickenCenter, chickenRadius, carRect)) {
                    isGameOver = true
                    break
                }
            }
        }
    }

    // Счёт
    LaunchedEffect(isGameOver, isFirstLaunch) {
        while (!isGameOver && !isFirstLaunch) {
            delay(1L)
            score++
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    if (!isGameOver && !isFirstLaunch) {
                        if (offset.x < canvasSize.width / 2) {
                            chickenX -= chickenStep
                        } else {
                            chickenX += chickenStep
                        }
                        chickenX = chickenX.coerceIn(0f, canvasSize.width.toFloat())
                    }
                }
            }
    ) {
        Image(
            painter = painterResource(id = if (isGameOver) R.drawable.bg else R.drawable.bg_game),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged {
                    canvasSize = it
                    if (chickenX == 0f) chickenX = it.width / 2f
                }
        )

        cars.forEach { (car, resId) ->
            Image(
                painter = painterResource(resId),
                contentDescription = "Машина",
                modifier = Modifier
                    .offset { IntOffset(car.x.toInt(), car.y.toInt()) }
                    .size(width = 50.dp, height = 100.dp)
            )
        }

        Image(
            painter = painterResource(R.drawable.chicken),
            contentDescription = "Курица",
            modifier = Modifier
                .offset { IntOffset(chickenX.toInt(), canvasSize.height - 125) }
                .size(70.dp)
        )

        Text(
            text = "Score: $score",
            color = Color.White,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .background(Color(0x66000000))
        )

        if (isGameOver) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Game over!",
                    color = Color.White,
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                ImageButton("Restart Game", R.drawable.btn_bg, modifier = Modifier) {
                    resetGame()
                }

                Spacer(modifier = Modifier.height(12.dp))

                ImageButton("Menu", R.drawable.btn_bg, modifier = Modifier) {
                    navController.popBackStack()
                }
            }
        }

        if (isFirstLaunch) {
            AlertDialog(
                onDismissRequest = { },
                confirmButton = {
                    TextButton(onClick = { isFirstLaunch = false }) {
                        Text("OK", color = Color.White)
                    }
                },
                title = {
                    Text("How to play", color = Color.White)
                },
                text = {
                    Text(
                        "Control the chicken and dodge the eggs and coins. If you catch them, the game is over.",
                        color = Color.White
                    )
                },
                containerColor = Color(0xFF333333)
            )
        }
    }
}


fun circleIntersectsRect(circleCenter: Offset, radius: Float, rect: Rect): Boolean {
    val nearestX = circleCenter.x.coerceIn(rect.left, rect.right)
    val nearestY = circleCenter.y.coerceIn(rect.top, rect.bottom)
    val dx = circleCenter.x - nearestX
    val dy = circleCenter.y - nearestY
    return dx * dx + dy * dy <= radius * radius
}


