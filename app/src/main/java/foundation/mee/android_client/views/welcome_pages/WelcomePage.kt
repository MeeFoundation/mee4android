package foundation.mee.android_client.views.welcome_pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.models.settings.MeeAndroidSettingsDataStore
import foundation.mee.android_client.navigation.MeeDestinations.*
import foundation.mee.android_client.navigation.NavViewModel

@Composable
fun WelcomePage(
    viewModel: NavViewModel = hiltViewModel(),
) {
    val navigator = viewModel.navigator

    val settingsDataStore = MeeAndroidSettingsDataStore(context = LocalContext.current)

    val referrerUrl by settingsDataStore.getReferrerUrlSetting()
        .collectAsState(
            initial = null
        )

    val setupImage =
        if (referrerUrl != null) R.drawable.welcome_screen3 else R.drawable.welcome_screen2

    WelcomeScreen(
        screenImages = arrayOf(
            R.drawable.welcome_screen1,
            setupImage,
        )
    ) {
        navigator.navController.popBackStack()
        if (referrerUrl != null) {
            navigator.navigate("${CONTEXT_RECOVERY_FLOW.route}/${referrerUrl}")
        } else {
            navigator.navigate(CONNECTIONS.route)
        }
    }
}
