package foundation.mee.android_client.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import foundation.mee.android_client.views.connections.ConnectionsScreen
import foundation.mee.android_client.views.manage.ManageConnection


@Composable
fun MeeNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "connections"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("connections") {
            ConnectionsScreen(
                navigateToManage = { connectionId ->
                    if (connectionId.isNotEmpty()) {
                        navController.navigate("manage/$connectionId")
                    }
                },
            )
        }

        composable(
            "manage/{connectionId}",
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
