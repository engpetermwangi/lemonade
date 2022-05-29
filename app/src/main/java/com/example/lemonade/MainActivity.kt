package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Composable
fun LemonadeApp() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        var screen by remember { mutableStateOf(1) }
        var expectedSqueezeCount by remember { mutableStateOf(0) }
        LemonTextAndImage(screen) {
            if (screen == 1) {
                expectedSqueezeCount = (2..5).random()
            }
            if (screen == 2) {
                expectedSqueezeCount--
                if (expectedSqueezeCount == 0) {
                    screen++
                }
            } else if (screen == 4) {
                screen = 1
            } else {
                screen++
            }
        }
    }
}

@Composable
fun LemonTextAndImage(screen: Int, onImageClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val screenResources = lemonadeResourcesGenerator(screen)
        Text(
            text = stringResource(
                id = screenResources["textResource"]!!
            ), fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(
                id = screenResources["imageResource"]!!
            ),
            contentDescription = stringResource(
                id = screenResources["imageDesc"]!!
            ),
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color(105, 205, 216),
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable { onImageClick() }
                .padding(16.dp)
        )
    }
}

fun lemonadeResourcesGenerator(screen: Int): Map<String, Int> {
    when (screen) {
        1 -> return mapOf(
            "textResource" to R.string.pick,
            "imageResource" to R.drawable.lemon_tree,
            "imageDesc" to R.string.lemon_tree
        )
        2 -> return mapOf(
            "textResource" to R.string.squeeze,
            "imageResource" to R.drawable.lemon_squeeze,
            "imageDesc" to R.string.lemon
        )
        3 -> return mapOf(
            "textResource" to R.string.drink,
            "imageResource" to R.drawable.lemon_drink,
            "imageDesc" to R.string.lemonade
        )
        else -> return mapOf(
            "textResource" to R.string.again,
            "imageResource" to R.drawable.lemon_restart,
            "imageDesc" to R.string.glass
        )
    }
}

@Preview
@Composable
fun LemonadeAppPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}