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
import foundation.mee.android_client.utils.buildConsentRequestFromUrl
import foundation.mee.android_client.views.connections.ConnectionsScreen
import foundation.mee.android_client.views.consent.ConsentPage
import foundation.mee.android_client.views.initial_flow.InitialFlow
import foundation.mee.android_client.views.manage.ManageConnection
import foundation.mee.android_client.views.welcome_pages.WelcomePage

@Composable
fun MeeNavGraph(
    defaultStartDestination: String = CONNECTIONS.route,
    navigator: Navigator = hiltViewModel<NavViewModel>().navigator,
    initialFlowDone: Boolean,
    hadConnectionsBefore: Boolean
) {
    val controller = rememberNavController()
    navigator.navController = controller

    val startDestination: String =
        if (!initialFlowDone) INITIAL_FLOW.route
        else if (!hadConnectionsBefore) WELCOME_SCREEN.route
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
                navDeepLink { uriPattern = "${DEEP_LINK_URL_STRING}/authorize?{params}" }
            )
        ) { backStackEntry ->
            val params = backStackEntry.arguments?.getString("params")
            val siopUrl = "${DEEP_LINK_URL_STRING}/authorize?${params}"
            val consentRequest = buildConsentRequestFromUrl(siopUrl)

            if (consentRequest != null) {
                ConsentPage(consentRequest)
            } else ConnectionsScreen()
        }

        composable(
            route = "${CONTEXT_RECOVERY_FLOW.route}/{params}", arguments = listOf(
                navArgument("params") { type = NavType.StringType })
        ) {
            val params = it.arguments?.getString("params")
            val consentRequest = params?.let { buildConsentRequestFromUrl(it) }

            if (consentRequest != null) {
                ConsentPage(consentRequest)
            } else ConnectionsScreen()
        }

        composable(route = INITIAL_FLOW.route) {
            InitialFlow()
        }
    }
}
