package foundation.mee.android_client.views.sidebar

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.models.MeeSidebarOption
import foundation.mee.android_client.navigation.MeeDestinations
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.navigation.Navigator
import foundation.mee.android_client.ui.components.Sidebar
import foundation.mee.android_client.utils.sendFeedback

@Composable
fun MeeSidebarMenu(
    navigator: Navigator = hiltViewModel<NavViewModel>().navigator,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    Sidebar(options = enumValues(), onClick = {
        when (it) {
            MeeSidebarOption.SEND_FEEDBACK -> sendFeedback(context)
            MeeSidebarOption.SETTINGS -> navigator.navigate(MeeDestinations.SETTINGS.route)
        }
    }) {
        content()
    }
}
