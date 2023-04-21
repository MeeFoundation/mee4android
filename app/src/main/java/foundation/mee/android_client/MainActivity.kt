package foundation.mee.android_client

import android.os.Bundle
import androidx.activity.compose.setContent
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
import dagger.hilt.android.AndroidEntryPoint
import foundation.mee.android_client.controller.biometry.BiometryHandler
import foundation.mee.android_client.navigation.MeeNavGraph
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.views.MeeWhiteScreen
import foundation.mee.android_client.views.wizard_pages.LoadingScreenWithMeeLogo

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
//    @Inject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MeeIdentityAgentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var loginSuccess by rememberSaveable { mutableStateOf(false) }

                    val ctx = LocalContext.current as FragmentActivity

                    val biometricManager = BiometricManager.from(this)

                    if (biometricManager.canAuthenticate(BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
                        BiometryHandler(activityContext = ctx, onSuccessfulAuth = {loginSuccess = true})
                    } else {
                        loginSuccess = true
                    }

                    var showLoadingScreen by remember { mutableStateOf(true) }

                    if (loginSuccess) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (!showLoadingScreen) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .zIndex(1f)
                                ) {
                                    MeeNavGraph()
                                }
                            }
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .zIndex(2f)) {
                                LoadingScreenWithMeeLogo(endAction = {showLoadingScreen = false})
                            }
                        }
                    } else {
                        MeeWhiteScreen()
                    }
//
                }
            }
        }
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
