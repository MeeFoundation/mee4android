package foundation.mee.android_client.views.connections

import android.view.Gravity
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import foundation.mee.android_client.views.initial_flow.BottomMessage

@Composable
fun WarningPopup(
    icon: Int? = null,
    iconSize: Dp? = null,
    messageText: Int,
    buttonText: Int? = null,
    buttonColor: Color? = null,
    bottomMessageHeader: @Composable () -> Unit = {},
    onDismiss: () -> Unit,
    onNext: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.BOTTOM)
        dialogWindowProvider.window.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            LocalView.current.layoutParams.height
        )
        BottomMessage(
            icon = icon,
            iconSize = iconSize,
            message = stringResource(messageText),
            buttonText = buttonText,
            buttonColor = buttonColor,
            bottomMessageHeader = bottomMessageHeader,
            textModifier = Modifier.padding(bottom = 16.dp)
        ) {
            onNext()
        }
    }
}