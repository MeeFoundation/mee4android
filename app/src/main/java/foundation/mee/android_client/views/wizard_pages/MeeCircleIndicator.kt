package foundation.mee.android_client.views.wizard_pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.MeeProgressCircleColor
import kotlinx.coroutines.delay

@Composable
fun MeeCircleIndicator(
    progress: Float,
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
//    TestCircularIndicator(
        progress = progress,
        color = MeeProgressCircleColor,
        strokeWidth = 3.dp,
        modifier = modifier
//            .fillMaxSize()
            .size(width = 171.dp, height = 171.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF4E868E)
@Composable
fun MeeCircleIndicatorPreview() {
    var currentProgress by remember {
        mutableStateOf(0f)
    }
    MeeIdentityAgentTheme {
        MeeCircleIndicator(currentProgress)
    }
    LaunchedEffect(key1 = currentProgress) {
        while (currentProgress < 1f) {
            currentProgress += 0.02f
            delay(100)
        }
    }
}
