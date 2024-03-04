package foundation.mee.android_client.navigation

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import foundation.mee.android_client.R
import foundation.mee.android_client.utils.DEEP_LINK_URL_STRING
import foundation.mee.android_client.navigation.MeeDestinations.*
import foundation.mee.android_client.utils.buildConsentRequestFromUrl
import foundation.mee.android_client.utils.showConsentToast
import foundation.mee.android_client.views.connections.ConnectionsScreenWithSidebar
import foundation.mee.android_client.views.consent.ConsentPage
import foundation.mee.android_client.views.initial_flow.InitialFlow
import foundation.mee.android_client.views.manage.ManageConnection
import foundation.mee.android_client.views.settings.MeeSettingsView
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

    val context = LocalContext.current

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
            ConnectionsScreenWithSidebar()
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
                navDeepLink { uriPattern = "${DEEP_LINK_URL_STRING}/?{params}" }
            )
        ) { backStackEntry ->
            val consentRequest = try {
                val intent: Intent? =
                    backStackEntry.arguments?.getParcelable(
                        NavController.KEY_DEEP_LINK_INTENT
                    )
                intent?.data?.let { buildConsentRequestFromUrl(it.toString()) }
            } catch (e: Exception) {
                Log.e("navigation", e.message.orEmpty())
                null
            }
            if (consentRequest?.clientMetadata != null) { // TODO discuss with the team
                ConsentPage(consentRequest)
            } else {
                showConsentToast(
                    context,
                    R.string.connection_failed_toast
                )
                Log.e("Unable to connect", "clientMetadata is missing")
                ConnectionsScreenWithSidebar()
            }
        }

        composable(
            route = "${CONTEXT_RECOVERY_FLOW.route}/{params}", arguments = listOf(
                navArgument("params") { type = NavType.StringType })
        ) {
            val params = it.arguments?.getString("params")
            val consentRequest = params?.let { buildConsentRequestFromUrl(it) }

            if (consentRequest?.clientMetadata != null) {
                ConsentPage(consentRequest)
            } else {
                showConsentToast(
                    context,
                    R.string.connection_failed_toast
                )
                Log.e("Unable to connect", "clientMetadata is missing")
                ConnectionsScreenWithSidebar()
            }
        }

        composable(route = INITIAL_FLOW.route) {
            InitialFlow()
        }

        composable(route = SETTINGS.route) {
            MeeSettingsView()
        }
    }
}
