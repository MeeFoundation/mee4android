package foundation.mee.android_client.views.welcome_pages

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
fun WelcomePageFirst() {
    ScreenScaffold(image = R.drawable.welcome_screen1) {
        Spacer(
            modifier = Modifier
                .sizeIn(maxHeight = 51.dp)
                .fillMaxSize(),
        )
    }
}

@Composable
fun WelcomePageSecond(action: () -> Unit) {
    ScreenScaffold(image = R.drawable.welcome_screen2) {
        RejectButton(title = "Get Started", action = action)
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePageFirstPreview() {
    MeeIdentityAgentTheme {
        WelcomePageFirst()
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePageSecondPreview() {
    MeeIdentityAgentTheme {
        WelcomePageSecond {}
    }
}
