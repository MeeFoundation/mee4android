package foundation.mee.android_client.views.connections

import android.view.Gravity
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import foundation.mee.android_client.R
import foundation.mee.android_client.views.initial_flow.BottomMessage

@Composable
fun WarningPopup(
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
            icon = painterResource(R.drawable.ic_google),
            iconSize = 60.dp,
            message = stringResource(R.string.warning_popup_message_text),
            textModifier = Modifier.padding(bottom = 16.dp)
        ) {
            onNext()
        }
    }
}