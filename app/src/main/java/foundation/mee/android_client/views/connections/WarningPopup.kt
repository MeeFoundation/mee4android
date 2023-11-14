package foundation.mee.android_client.views.connections

import android.view.Gravity
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import foundation.mee.android_client.ui.theme.DurationPopupBackground
import foundation.mee.android_client.views.initial_flow.BottomMessage

val popupShape = RoundedCornerShape(topEnd = 13.dp, topStart = 13.dp)

@Composable
fun WarningPopup(
    icon: Int? = null,
    iconSize: Dp? = null,
    messageText: Int,
    buttonText: Int? = null,
    buttonColor: Color? = null,
    bottomMessageHeader: @Composable () -> Unit = {},
    title: Int? = null,
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
        Surface(
            modifier = Modifier
                .fillMaxWidth(1f)
                .sizeIn(minHeight = 64.dp)
                .clip(popupShape)
                .background(
                    DurationPopupBackground,
                    popupShape
                )
        ) {
            BottomMessage(
                icon = icon,
                iconSize = iconSize,
                message = messageText,
                buttonText = buttonText,
                buttonColor = buttonColor,
                bottomMessageHeader = bottomMessageHeader,
                title = title,
                textModifier = Modifier.padding(bottom = 16.dp)
            ) {
                onNext()
            }
        }
    }
}