package foundation.mee.android_client.views.wizard_pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.MeeProgressCircleColor
import kotlinx.coroutines.delay

@Composable
fun MeeLogoWithCircleIndicator(
    progress: Float,
    scale: Float = 1f,
    modifierBox: Modifier = Modifier,
    modifierLogo: Modifier = Modifier,
    modifierCircle: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifierBox
//            .size(width = 220.dp * scale, height = 220.dp * scale)
            .clip(shape = RoundedCornerShape(size = 3.dp))
            .background(color = MeeGreenPrimaryColor)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.mee_m_letter),
            contentDescription = null,
            tint = MeeProgressCircleColor,
            modifier = modifierLogo
//            Modifier.scale(scale)
        )
        MeeCircleIndicator(progress, modifierCircle)
    }

}

@Preview(showBackground = true)
@Composable
fun MeeLogoWithCircleIndicatorPreview() {
    var currentProgress by remember {
        mutableStateOf(0f)
    }
    var scale by remember {
        mutableStateOf(1f)
    }
    MeeIdentityAgentTheme {
        MeeLogoWithCircleIndicator(currentProgress, scale)
    }
    LaunchedEffect(key1 = currentProgress) {
        while (currentProgress < 1f) {
            currentProgress += 0.02f
            scale += 0.02f
            delay(100)
        }
    }
}
