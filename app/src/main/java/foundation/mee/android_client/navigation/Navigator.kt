package foundation.mee.android_client.navigation

import android.util.Log
import androidx.navigation.NavHostController

object Navigator {

    var navController: NavHostController? = null

    fun navigate(path: String) {
        navController?.navigate(path) ?: Log.d("Navigator", "navController is null")
    }
}