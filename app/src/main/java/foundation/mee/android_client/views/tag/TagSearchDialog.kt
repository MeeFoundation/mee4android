package foundation.mee.android_client.views.tag

import android.view.Gravity
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider

@Composable
fun TagSearchDialog(
    onClose: () -> Unit
) {
    Dialog(
        onDismissRequest = {}
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
                .fillMaxHeight(1f)
                .background(
                    Color.White
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                TagSearchScreenHeader {
                    onClose()
                }
                TagSearchContentView()
            }
        }
    }
}

@Composable
@Preview
fun SearchTagsDialogPreview() {
    TagSearchDialog(
        onClose = {}
    )
}