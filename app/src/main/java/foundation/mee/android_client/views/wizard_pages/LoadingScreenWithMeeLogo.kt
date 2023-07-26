package foundation.mee.android_client.views.wizard_pages

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.models.loadingScreenTexts
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.views.animations.ConsentPageAnimation
import kotlinx.coroutines.delay

@Composable
fun LoadingScreenWithMeeLogo(
    startDelay: Long = 0,
    circularIndicatorLoadSpeed: Int = 5000,
    endAction: () -> Unit = {}
) {
    var circularIndicatorFinished by remember {
        mutableStateOf(false)
    }

    var currentIndex by remember {
        mutableStateOf(0)
    }

    val currentProgress = remember {
        Animatable(0f)
    }

    if (circularIndicatorFinished) {
        ConsentPageAnimation(endAction = endAction)
    } else {
        LoadingScreenWhitePage(
            progress = currentProgress.value,
            text = loadingScreenTexts[currentIndex]?.let { stringResource(id = it) },
            modifierBox = Modifier
                .size(220.dp),
            modifierLogo = Modifier
                .size(width = 104.dp, height = 72.dp),
            modifierCircle = Modifier
                .size(171.dp)
        )
    }


    LaunchedEffect(key1 = currentProgress) {
        delay(startDelay)
        currentProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(circularIndicatorLoadSpeed)
        ) {
            currentIndex = if (this.value < 0.1) {
                0
            } else if (this.value < 0.4) {
                1
            } else if (this.value < 0.7) {
                2
            } else if (this.value < 0.9) {
                3
            } else {
                4
            }
        }
        circularIndicatorFinished = true
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreensPreview() {
    MeeIdentityAgentTheme {
        LoadingScreenWithMeeLogo(
            startDelay = 1500,
            circularIndicatorLoadSpeed = 4000
        )
    }
}
