package foundation.mee.android_client.views.settings

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.MeeAgentViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.navigation.Navigator
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.AccessibleSystemRedLight
import foundation.mee.android_client.ui.theme.LabelLightSecondary
import foundation.mee.android_client.ui.theme.SystemBlueLight
import foundation.mee.android_client.ui.theme.publicSansFamily
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
                    BottomMessageHeader {
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
            WarningPopup(
                icon = R.drawable.mee_compatible_sign,
                title = R.string.settings_data_deletion_error_title,
                messageText = R.string.settings_data_deletion_error,
                buttonText = R.string.settings_data_deletion_error_back
            ) {
                onClose()
                currentStep = DeleteAllDataSteps.Initial
            }
        }
    }
}

@Composable
fun BottomMessageHeader(
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.settings_delete_user_data),
            fontFamily = publicSansFamily,
            fontSize = 17.sp,
            fontWeight = FontWeight(700),
            color = Color.Black
        )
        Text(
            text = stringResource(R.string.negative_button_text),
            fontFamily = publicSansFamily,
            fontSize = 17.sp,
            fontWeight = FontWeight(400),
            color = SystemBlueLight,
            modifier = Modifier.clickableWithoutRipple {
                onCancelClick()
            }
        )
    }
    Divider(
        color = LabelLightSecondary,
        thickness = 0.5.dp,
        modifier = Modifier.alpha(0.5f)
    )
}
