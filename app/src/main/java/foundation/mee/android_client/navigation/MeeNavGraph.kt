package foundation.mee.android_client.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import foundation.mee.android_client.utils.DEEP_LINK_URL_STRING
import foundation.mee.android_client.navigation.MeeDestinations.*
import foundation.mee.android_client.views.connections.ConnectionsScreen
import foundation.mee.android_client.views.consent.ConsentPage
import foundation.mee.android_client.views.manage.ManageConnection
import foundation.mee.android_client.views.welcome_pages.WelcomeScreen

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

        composable(
            "${CONSENT.route}/{consentData}",
            // arguments - list of arguments to associate with destination
            arguments = listOf(
                navArgument("consentData") { type = NavType.StringType }
            ),
            deepLinks = listOf(navDeepLink {
                uriPattern = "${DEEP_LINK_URL_STRING}/#/consent/{consentData}"
            })
        )
        { backStackEntry ->
            val data = backStackEntry.arguments?.getString("consentData")

            if (data != null) {
                ConsentPage()
            } else ConnectionsScreen()
        }
    }
}
