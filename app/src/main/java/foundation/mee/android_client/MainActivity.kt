package foundation.mee.android_client

import android.app.KeyguardManager
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val keyguard = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK)

        setContent {
            val settingsDataStore = MeeAndroidSettingsDataStore(context = LocalContext.current)
            val initialFlowDone by settingsDataStore.getInitialFlowDoneSetting().collectAsState(
                initial = null
            )
            val coroutineScope = rememberCoroutineScope()

            MeeIdentityAgentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var loginSuccess by rememberSaveable { mutableStateOf(false) }

                    val ctx = LocalContext.current as FragmentActivity

                    OnLifecycleEvent { owner, event ->
                        Log.d("Lifecycle Event: ", event.toString())
                        when (event) {
                            Lifecycle.Event.ON_RESUME -> {
                                if (!keyguard.isDeviceSecure) {
                                    coroutineScope.launch {
                                        settingsDataStore.saveInitialFlowDoneSetting(flag = false)
                                    }
                                }
                            }

                            Lifecycle.Event.ON_STOP -> {
                                loginSuccess = false

                            }
                            else -> null
                        }
                    }

                    if (initialFlowDone != null) {
                        if (loginSuccess || initialFlowDone == false
                        ) {
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
                            BiometryHandler(
                                activityContext = ctx,
                                onSuccessfulAuth = { loginSuccess = true }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val model: NavViewModel by viewModels()
        model.navigator.navController.handleDeepLink(intent)
        setIntent(null)
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
