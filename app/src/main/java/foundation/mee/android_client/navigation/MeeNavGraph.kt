package foundation.mee.android_client.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import foundation.mee.android_client.navigation.MeeDestinations.*
import foundation.mee.android_client.views.connections.ConnectionsScreen
import foundation.mee.android_client.views.manage.ManageConnection
import foundation.mee.android_client.views.welcome_pages.WelcomeScreen
import foundation.mee.android_client.models.mobileApps
import foundation.mee.android_client.models.sites


@Composable
fun MeeNavGraph(
    defaultStartDestination: String = CONNECTIONS.route,
    viewModel: NavViewModel = hiltViewModel()
) {
    val controller = rememberNavController()
    viewModel.navigator.navController = controller

    // TODO: change in the future when mee-core integration happens
    val connectionsNum: Int = 0 // TODO: sites.size + mobileApps.size to remove WelcomeScreen

    val startDestination: String = if (connectionsNum == 0) WELCOME_SCREEN.route else defaultStartDestination

    NavHost(
        navController = controller,
        startDestination = startDestination
    ) {
        composable(WELCOME_SCREEN.route) {
            WelcomeScreen()
        }

        composable(route = CONNECTIONS.route) {
            ConnectionsScreen()
        }

        composable(
            "${MANAGE.route}/{connectionId}",
            arguments = listOf(navArgument("connectionId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            ManageConnection(
                backStackEntry.arguments?.getString("connectionId") ?: "empty connectionId"
            )
        }
    }
}
