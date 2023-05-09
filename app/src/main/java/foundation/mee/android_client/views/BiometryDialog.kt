package foundation.mee.android_client.views

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BiometryDialog(
    onDismiss: () -> Unit = {},
    onAccept: () -> Unit = {},
    onReject: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onAccept,
            ) {
                Text(
                    "Accept",
                    color = Color.Black
                )
            }
        },
        dismissButton = {
            Button(onClick = onReject) {
                Text(
                    "Reject",
                    color = Color.Black
                )
            }
        },
        title = {
            Text(text = "Do you want to use biometry as a way to authenticate?")
        }
    )
}
