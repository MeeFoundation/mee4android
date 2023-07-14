package foundation.mee.android_client.views.connections

import android.view.Gravity
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
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
            message = "You will be redirected to your default browser to login to your Google Account and pull your data from it to Mee local storage"
        ) {
            onNext()
        }
    }
}