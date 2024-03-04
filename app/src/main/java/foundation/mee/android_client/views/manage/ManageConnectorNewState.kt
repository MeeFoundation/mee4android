package foundation.mee.android_client.views.manage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberManageConnectorNewState(): ManageConnectorNewState {
    return remember {
        ManageConnectorNewState()
    }
}

@Stable
class ManageConnectorNewState {
    var durationPopupId by mutableStateOf<String?>(null)
    var isRequiredSectionOpened by mutableStateOf(true)
    var isOptionalSectionOpened by mutableStateOf(false)

    val shouldShowDurationPopup: Boolean
        get() = durationPopupId != null
}