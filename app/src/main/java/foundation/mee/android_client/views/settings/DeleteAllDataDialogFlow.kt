package foundation.mee.android_client.views.settings

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.MeeAgentViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.navigation.Navigator
import foundation.mee.android_client.ui.components.BottomDialogHeader
import foundation.mee.android_client.ui.components.NotificationPopup
import foundation.mee.android_client.ui.theme.AccessibleSystemRedLight
import foundation.mee.android_client.utils.goToSystemSettings
import foundation.mee.android_client.views.connections.WarningPopup

enum class DeleteAllDataSteps {
    Initial,
    Error,
    Success,
}

@Composable
fun DeleteAllDataDialogFlow(
    meeAgentStore: MeeAgentStore = hiltViewModel<MeeAgentViewModel>().meeAgentStore,
    navigator: Navigator = hiltViewModel<NavViewModel>().navigator,
    onClose: () -> Unit
) {

    var currentStep by remember {
        mutableStateOf(DeleteAllDataSteps.Initial)
    }

    when (currentStep) {
        DeleteAllDataSteps.Initial -> {
            WarningPopup(
                icon = R.drawable.exclamation_mark,
                title = R.string.settings_delete_user_data,
                messageText = R.string.delete_popup_message_text,
                buttonText = R.string.delete_popup_button_text,
                buttonColor = AccessibleSystemRedLight,
                additionalButtonText = R.string.negative_button_text,
                onAdditionalButtonClick = onClose
            ) {
                currentStep = try {
                    meeAgentStore.resetMeeAgent()
                    DeleteAllDataSteps.Success
                } catch (e: Exception) {
                    Log.e("Unable to clear Mee Agent data", e.message.orEmpty())
                    DeleteAllDataSteps.Error
                }
            }
        }
        DeleteAllDataSteps.Success -> {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                NotificationPopup(
                    message = stringResource(R.string.settings_data_deletion_success),
                    primaryButtonTitle = stringResource(R.string.settings_data_deletion_success_back)
                ) {
                    navigator.navigateToMainScreen()
                }
            }
        }
        DeleteAllDataSteps.Error -> {
            val context = LocalContext.current
            WarningPopup(
                icon = R.drawable.mee_compatible_sign,
                title = R.string.settings_data_deletion_error_title,
                messageText = R.string.settings_data_deletion_error,
                buttonText = R.string.settings_data_deletion_error_button_title
            ) {
                goToSystemSettings(context)
            }
        }
    }
}