package foundation.mee.android_client.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import foundation.mee.android_client.navigation.MeeDestinations.*
import foundation.mee.android_client.views.connections.ConnectionsScreen
import foundation.mee.android_client.views.manage.ManageConnection


@Composable
fun MeeNavGraph(
    navController: NavHostController,
    startDestination: String = CONNECTIONS.route
) {
    NavHost(
        navController = navController,
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
    }
}
