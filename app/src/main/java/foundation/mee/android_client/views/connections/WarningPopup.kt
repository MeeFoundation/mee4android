package foundation.mee.android_client.views.connections

import android.view.Gravity
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.zIndex
import foundation.mee.android_client.views.initial_flow.BottomMessage

@Composable
fun WarningPopup(
    icon: Int? = null,
    iconSize: Dp? = null,
    messageText: Int,
    buttonText: Int? = null,
    additionalButtonText: Int? = null,
    onAdditionalButtonClick: (() -> Unit)? = null,
    bottomMessageHeader: @Composable () -> Unit = {},
    title: Int,
    onDismiss: () -> Unit = {},
    onNext: () -> Unit
) {
    return WarningPopup(
        icon = icon,
        iconSize = iconSize,
        messageText = messageText,
        buttonText = buttonText,
        additionalButtonText = additionalButtonText,
        onAdditionalButtonClick = onAdditionalButtonClick,
        bottomMessageHeader = bottomMessageHeader,
        title = stringResource(title),
        onDismiss = onDismiss,
        onNext = onNext
    )
}

@Composable
fun WarningPopup(
    icon: Int? = null,
    iconSize: Dp? = null,
    messageText: Int,
    buttonText: Int? = null,
    additionalButtonText: Int? = null,
    onAdditionalButtonClick: (() -> Unit)? = null,
    bottomMessageHeader: @Composable () -> Unit = {},
    title: String? = null,
    onDismiss: () -> Unit = {},
    onNext: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        with(dialogWindowProvider.window) {
            setGravity(Gravity.BOTTOM)
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                LocalView.current.layoutParams.height
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .zIndex(1f)
                .padding(horizontal = 50.dp)
                .fillMaxHeight(),
        ) {

            BottomMessage(
                icon = icon,
                iconSize = iconSize,
                message = messageText,
                buttonText = buttonText,
                additionalButtonText = additionalButtonText,
                onAdditionalButtonClick = onAdditionalButtonClick,
                bottomMessageHeader = bottomMessageHeader,
                title = title,
                textModifier = Modifier.padding(bottom = 16.dp)
            ) {
                onNext()
            }
        }
    }
}