package foundation.mee.android_client.views.welcome_pages

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.RejectButton

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
            RejectButton(title = stringResource(R.string.get_started_button_title), action = onNext)
        }
    }
}

