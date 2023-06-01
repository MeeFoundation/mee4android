package foundation.mee.android_client.views.welcome_pages

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.navigation.MeeDestinations
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.ui.components.RejectButton
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme

@Composable
fun WelcomePageTab(@DrawableRes image: Int, isLast: Boolean = false, onNext: () -> Unit) {
    ScreenScaffold(image = image) {
        if (!isLast) {
            Spacer(
                modifier = Modifier
                    .sizeIn(maxHeight = 51.dp)
                    .fillMaxSize(),
            )
        } else {
            RejectButton(title = "Get Started", action = onNext)
        }
    }
}

