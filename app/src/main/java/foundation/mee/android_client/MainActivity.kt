package foundation.mee.android_client

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import dagger.hilt.android.AndroidEntryPoint
import foundation.mee.android_client.controller.biometry.BiometryHandler
import foundation.mee.android_client.effects.OnLifecycleEvent
import foundation.mee.android_client.models.settings.MeeAndroidSettingsDataStore
import foundation.mee.android_client.navigation.MeeNavGraph
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.views.MeeWhiteScreen

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val settingsDataStore = MeeAndroidSettingsDataStore(context = LocalContext.current)
            val initialFlowDone by settingsDataStore.getInitialFlowDoneSetting().collectAsState(
                initial = null
            )

            MeeIdentityAgentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var loginSuccess by rememberSaveable { mutableStateOf(false) }

                    val ctx = LocalContext.current as FragmentActivity

                    val biometricManager = BiometricManager.from(this)


                    OnLifecycleEvent { owner, event ->
                        Log.d("Lifecycle Event: ", event.toString())
                        when (event) {
                            Lifecycle.Event.ON_STOP -> {
                                loginSuccess = false
                            }

                            else -> null
                        }
                    }

                    if (initialFlowDone != null) {
                        if (loginSuccess || initialFlowDone == false) {
                            Box(modifier = Modifier.fillMaxSize()) {

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .zIndex(1f)
                                ) {
                                    MeeNavGraph(initialFlowDone = initialFlowDone == true)
                                }

                            }
                        } else {
                            MeeWhiteScreen()
                            if (
                                biometricManager.canAuthenticate(BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
                            ) {
                                BiometryHandler(
                                    activityContext = ctx,
                                    onSuccessfulAuth = { loginSuccess = true })
                            } else {
                                loginSuccess = true
                            }
                        }
//
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val model: NavViewModel by viewModels()
        model.navigator.navController.handleDeepLink(intent)
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MeeIdentityAgentTheme {
        Greeting("Mee")
    }
}
