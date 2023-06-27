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
import foundation.mee.android_client.views.initial_flow.InitialFlow
import foundation.mee.android_client.views.manage.ManageConnection
import foundation.mee.android_client.views.welcome_pages.WelcomePage
import uniffi.mee_agent.siopRpAuthRequestFromUrl

@Composable
fun MeeNavGraph(
    defaultStartDestination: String = CONNECTIONS.route, viewModel: NavViewModel = hiltViewModel(),
    initialFlowDone: Boolean
) {
    val controller = rememberNavController()
    viewModel.navigator.navController = controller

    // TODO: change in the future when mee-core integration happens
    val connectionsNum: Int = 0 // TODO: sites.size + mobileApps.size to remove WelcomeScreen

    val startDestination: String =
        if (!initialFlowDone) INITIAL_FLOW.route
        else if (connectionsNum == 0) WELCOME_SCREEN.route
        else defaultStartDestination

    NavHost(
        navController = controller,
        startDestination = startDestination
    ) {
        composable(WELCOME_SCREEN.route) {
            WelcomePage()
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
            CONSENT.route,
            arguments = listOf(
                navArgument("params") { type = NavType.StringType }),
            deepLinks = listOf(
                navDeepLink { uriPattern = "${DEEP_LINK_URL_STRING}/authorize?{params}" },
                navDeepLink { uriPattern = "${DEEP_LINK_URL_STRING}/#/consent/{params}" }
            )
        ) { backStackEntry ->
            val consentRequest = try {
                val data = backStackEntry.arguments?.getString("params")
                if (data != null) {
                    // TODO refactor when Olde York Times will be supporting siop authorization
                    val siopUrl = if (data.contains("request"))
                        "${DEEP_LINK_URL_STRING}/authorize?${data}"
                    else buildLegacySiopUrl("${DEEP_LINK_URL_STRING}/#/consent/", data)
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

        composable(route = INITIAL_FLOW.route) {
            InitialFlow()
        }
    }
}
