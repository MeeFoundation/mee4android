package foundation.mee.android_client.views.initial_flow

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.models.loadingScreenTexts
import foundation.mee.android_client.views.wizard_pages.LoadingScreenWhitePage
import kotlinx.coroutines.delay

@Composable
fun InitialFlowLoadingScreen(onNext: () -> Unit) {
    var currentProgress by remember {
        mutableStateOf(0f)
    }
    var currentIndex by remember {
        mutableStateOf(0)
    }
    var isFinished by remember {
        mutableStateOf(false)
    }
    val sizeBox by animateDpAsState(
        targetValue = if (isFinished) 220.dp * 6 else 220.dp,
        animationSpec = tween(durationMillis = 2000)
    )

    val alpha by animateFloatAsState(
        targetValue = if (isFinished) 0f else 1f,
        animationSpec = tween(durationMillis = 1000)
    )

    val scaleFactor by animateFloatAsState(
        targetValue = if (isFinished) 6f else 1f,
        animationSpec = tween(durationMillis = 2000)
    )

    LoadingScreenWhitePage(
        progress = currentProgress,
        text = loadingScreenTexts[currentIndex]?.let { stringResource(id = it) },
        modifierBox = Modifier
            .size(sizeBox),
        modifierLogo = Modifier
            .graphicsLayer(scaleX = scaleFactor, scaleY = scaleFactor)
            .alpha(alpha = alpha),
        modifierCircle = Modifier
            .graphicsLayer(scaleX = scaleFactor, scaleY = scaleFactor)
            .alpha(alpha = alpha)
    )
    LaunchedEffect(key1 = currentIndex) {
        while (currentProgress < 1) {
            currentIndex = if (currentProgress < 0.1) {
                0
            } else if (currentProgress < 0.4) {
                1
            } else if (currentProgress < 0.7) {
                2
            } else if (currentProgress < 0.9) {
                3
            } else {
                4
            }
            delay(100)
            currentProgress += 0.02f
        }
        if (currentProgress >= 1) {
            isFinished = true
            delay(1500)
            onNext()
        }

    }
}