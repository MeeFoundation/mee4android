package foundation.mee.android_client.views.connections

import android.net.Uri
import android.view.Gravity
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.MeeAgentViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.models.MeeConnector
import foundation.mee.android_client.models.MeeConnectorType
import foundation.mee.android_client.ui.components.BottomDialogHeader
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.DurationPopupBackground
import foundation.mee.android_client.utils.linkToWebpage

val dialogShape = RoundedCornerShape(topEnd = 13.dp, topStart = 13.dp)

@Composable
fun SitesToConnectDialog(
    partners: List<MeeConnector>,
    meeAgentStore: MeeAgentStore = hiltViewModel<MeeAgentViewModel>().meeAgentStore,
    onClose: () -> Unit
) {

    var showCompatibleWarning by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current

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
                .clip(dialogShape)
                .background(
                    DurationPopupBackground,
                    dialogShape
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                BottomDialogHeader(title = R.string.sites_to_connect_dialog_header) {
                    onClose()
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    partners.mapIndexed { i, connector ->
                        ConnectToEntry(
                            connector = connector,
                            isLight = false,
                            modifier = Modifier.clickableWithoutRipple {
                                when (connector.value) {
                                    is MeeConnectorType.Gapi -> showCompatibleWarning = true
                                    else -> {
                                        val uri = Uri.parse(connector.id)
                                        linkToWebpage(context, uri)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    if (showCompatibleWarning) {
        WarningPopup(
            icon = R.drawable.ic_google,
            iconSize = 60.dp,
            messageText = R.string.warning_popup_message_text,
            additionalButtonText = R.string.negative_button_text,
            onAdditionalButtonClick = { showCompatibleWarning = false },
            onDismiss = { showCompatibleWarning = false }) {
            showCompatibleWarning = false
            val url = meeAgentStore.getGoogleIntegrationUrl()
            url?.let { linkToWebpage(context, Uri.parse(it)) }
        }
    }

}