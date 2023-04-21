package foundation.mee.android_client.views.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.views.wizard_pages.LoadingScreenWhitePage
import kotlinx.coroutines.delay

@Composable
fun ConsentPageAnimation(
    startDelayMilliseconds: Int = 0,
    firstPartAnimationMilliseconds: Int = 2000,
    secondPartAnimationMilliseconds: Int = 250,
    endAction: () -> Unit = {}
) {
    var firstPartStartSwitch by remember {
        mutableStateOf(false)
    }

    var isFirstPartFinished by remember {
        mutableStateOf(false)
    }
    val sizeBox by animateDpAsState(
        targetValue = if (firstPartStartSwitch) 220.dp * 6 else 220.dp,
        animationSpec = tween(durationMillis = firstPartAnimationMilliseconds)
    )

    val alphaFactor by animateFloatAsState(
        targetValue = if (firstPartStartSwitch) 0f else 1f,
        animationSpec = tween(durationMillis = firstPartAnimationMilliseconds)
    )

    val alphaBoxFactor by animateFloatAsState(
        targetValue = if (isFirstPartFinished) 0f else 1f,
        animationSpec = tween(durationMillis = secondPartAnimationMilliseconds)
    )

    val scaleFactor by animateFloatAsState(
        targetValue = if (firstPartStartSwitch) 4f else 1f,
        animationSpec = tween(durationMillis = firstPartAnimationMilliseconds)
    )


    LoadingScreenWhitePage(
        progress = 1f,
        fillTextBar = false,
        text = null,
        modifierBox = Modifier
            .size(sizeBox)
            .alpha(alpha = alphaBoxFactor),
        modifierLogo = Modifier
            .graphicsLayer(scaleX = scaleFactor, scaleY = scaleFactor)
            .alpha(alpha = alphaFactor),
        modifierCircle = Modifier
            .graphicsLayer(scaleX = scaleFactor, scaleY = scaleFactor)
            .alpha(alpha = alphaFactor)
    )

    LaunchedEffect(Unit) {
        delay(startDelayMilliseconds.toLong())
        firstPartStartSwitch = true
        delay(firstPartAnimationMilliseconds.toLong())
        isFirstPartFinished = true
        endAction()
    }
}

@Preview(showBackground = true)
@Composable
fun ConsentPageAnimationPreview() {
    MeeIdentityAgentTheme {
        ConsentPageAnimation(
            startDelayMilliseconds = 1000,
            firstPartAnimationMilliseconds = 4000,
            secondPartAnimationMilliseconds = 1500
        )
    }
}