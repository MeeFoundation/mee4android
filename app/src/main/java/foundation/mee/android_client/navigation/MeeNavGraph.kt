package foundation.mee.android_client.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import foundation.mee.android_client.models.ConsentRequest
import foundation.mee.android_client.utils.DEEP_LINK_URL_STRING
import foundation.mee.android_client.navigation.MeeDestinations.*
import foundation.mee.android_client.utils.buildLegacySiopUrl
import foundation.mee.android_client.views.connections.ConnectionsScreen
import foundation.mee.android_client.views.consent.ConsentPage
import foundation.mee.android_client.views.manage.ManageConnection
import foundation.mee.android_client.views.welcome_pages.WelcomeScreen
import uniffi.mee_agent.siopRpAuthRequestFromUrl

const val AUTH_URI_PATTERN = "${DEEP_LINK_URL_STRING}/#/consent"

@Composable
fun MeeNavGraph(
    defaultStartDestination: String = CONNECTIONS.route, viewModel: NavViewModel = hiltViewModel()
) {
    val controller = rememberNavController()
    viewModel.navigator.navController = controller

    // TODO: change in the future when mee-core integration happens
    val connectionsNum: Int = 0 // TODO: sites.size + mobileApps.size to remove WelcomeScreen

    val startDestination: String =
        if (connectionsNum == 0) WELCOME_SCREEN.route else defaultStartDestination

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
            "${MANAGE.route}/{connectionHostname}",
            arguments = listOf(navArgument("connectionHostname") {
                type = NavType.StringType
            })
        ) {
            ManageConnection()
        }

        composable(
            "${CONSENT.route}/{consentData}",
            arguments = listOf(navArgument("consentData") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink {
                uriPattern = "${AUTH_URI_PATTERN}/{consentData}"
            })
        ) { backStackEntry ->
            val consentRequest = try {
                val data = backStackEntry.arguments?.getString("consentData")
                if (data != null) {
                    val siopUrl = buildLegacySiopUrl(AUTH_URI_PATTERN, data)
                    val res = siopRpAuthRequestFromUrl(siopUrl)
                    ConsentRequest(res)
                } else null
            } catch (e: Exception) {
                null
            }

            if (consentRequest != null) {
                ConsentPage(consentRequest)
            } else ConnectionsScreen()
        }
    }
}
