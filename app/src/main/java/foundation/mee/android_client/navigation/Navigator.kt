package foundation.mee.android_client.navigation

import androidx.navigation.NavHostController
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Navigator @Inject constructor() {

    lateinit var navController: NavHostController

    fun navigate(path: String) {
        navController.navigate(path)
    }

    fun popBackStack(): Boolean {
        return navController.popBackStack()
    }

    fun navigateToMainScreen() {
        navController.navigate(MeeDestinations.CONNECTIONS.route)
    }
}