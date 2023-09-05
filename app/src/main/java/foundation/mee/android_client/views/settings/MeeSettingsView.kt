package foundation.mee.android_client.views.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.navigation.Navigator
import foundation.mee.android_client.ui.components.MeeTopAppBar

@Composable
fun MeeSettingsView(
    navigator: Navigator = hiltViewModel<NavViewModel>().navigator
) {
    Scaffold(topBar = {
        MeeTopAppBar(title = R.string.top_app_bar_settings_title) { navigator.popBackStack() }
    }) { padding ->
        MeeSettingsContent(modifier = Modifier.padding(padding))
    }
}