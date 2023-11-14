package foundation.mee.android_client.views.settings

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.MeeAgentViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.navigation.Navigator
import foundation.mee.android_client.ui.components.BottomDialogHeader
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
                messageText = R.string.delete_popup_message_text,
                buttonText = R.string.delete_popup_button_text,
                buttonColor = AccessibleSystemRedLight,
                bottomMessageHeader = {
                    BottomDialogHeader(title = R.string.settings_delete_user_data) {
                        onClose()
                    }
                }
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
            WarningPopup(
                icon = R.drawable.all_set,
                title = R.string.biometry_all_set_step_title,
                messageText = R.string.settings_data_deletion_success,
                buttonText = R.string.settings_data_deletion_success_back
            ) {
                navigator.navigateToMainScreen()
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