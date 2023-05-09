package foundation.mee.android_client.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import foundation.mee.android_client.helpers.DEEP_LINK_URL_STRING
import foundation.mee.android_client.navigation.MeeDestinations.*
import foundation.mee.android_client.views.connections.ConnectionsScreen
import foundation.mee.android_client.views.consent.ConsentPage
import foundation.mee.android_client.views.manage.ManageConnection

@Composable
fun MeeNavGraph(
    startDestination: String = CONNECTIONS.route,
    viewModel: NavViewModel = hiltViewModel()
) {
    val controller = rememberNavController()
    viewModel.navigator.navController = controller

    NavHost(
        navController = controller,
        startDestination = startDestination
    ) {
        composable(CONNECTIONS.route) {
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
