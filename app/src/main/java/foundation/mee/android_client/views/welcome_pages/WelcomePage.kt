package foundation.mee.android_client.views.welcome_pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.navigation.MeeDestinations
import foundation.mee.android_client.navigation.NavViewModel

@Composable
fun WelcomePage(
    viewModel: NavViewModel = hiltViewModel(),
) {
    val navigator = viewModel.navigator
    WelcomeScreen(
        screenImages = arrayOf(
            R.drawable.welcome_screen1,
            R.drawable.welcome_screen2,
        )) {
        navigator.navController.popBackStack()
        navigator.navigate(MeeDestinations.CONNECTIONS.route)
    }
}
