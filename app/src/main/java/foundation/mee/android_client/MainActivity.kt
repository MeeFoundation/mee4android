package foundation.mee.android_client

import android.os.Bundle
import androidx.activity.compose.setContent
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

//                    val dataStoreManager = MeeAndroidSettingsDataStore(context = LocalContext.current)
//
//                    val biometryAsked by dataStoreManager.getBiometricAuthAskedSetting().collectAsState(
//                        initial = true
//                    )
//
//                    var showedBiometry by remember {
//                        mutableStateOf(false)
//                    }
//
//                    val coroutineScope = rememberCoroutineScope()
//
//                    if (!biometryAsked) {
//                        BiometryDialog(
//                            onDismiss = {
//                                coroutineScope.launch {
//                                    dataStoreManager.saveBiometricAuthAskedSetting(false)
//                                }
//                            },
//                            onAccept = {
//                                coroutineScope.launch {
//                                    dataStoreManager.saveBiometricAuthAskedSetting(true)
//                                    dataStoreManager.saveBiometricAuthSetting(true)
//                                }
//                            },
//                            onReject = {
//                                coroutineScope.launch {
//                                    dataStoreManager.saveBiometricAuthAskedSetting(true)
//                                    dataStoreManager.saveBiometricAuthSetting(false)
//                                }
//                            },
//                        )
//                    }
//
//                    val biometryEnabled by dataStoreManager.getBiometricAuthEnabledSetting().collectAsState(
//                        initial = false
//                    )
//
                    var loginSuccess by rememberSaveable { mutableStateOf(false) }


                    val ctx = LocalContext.current as FragmentActivity

                    BiometryHandler(activityContext = ctx, onSuccessfulAuth = {loginSuccess = true})
//
//
//                    if (biometryEnabled and !showedBiometry) {
//                        showBiometricPrompt(ctx, onSuccess = { loginSuccess = true })
//                        showedBiometry = true
//                    }
//
//                    println("biometryAsked is $biometryAsked")
//                    println("biometryEnabled is $biometryEnabled")
//
                    var showLoadingScreen by remember { mutableStateOf(true) }

//                    var biometryDialogShow by remember {
//                        mutableStateOf(true)
//                    }

//                    var biometryEnabled by remember {
//                        mutableStateOf(false)
//                    }

                    LaunchedEffect(Unit) {
//                        dataStoreManager.saveBiometricAuthAskedSetting(false)
//                        dataStoreManager.saveBiometricAuthSetting(false)
////                        biometryDialogShow = dataStoreManager.getBiometricAuthAskedSetting().first()
////                        if (biometryDialogShow) {
////                            BiometryDialog(
////
////                            )
////                        }
//                        biometryEnabled = dataStoreManager.getBiometricAuthEnabledSetting().first()
//                        println("testVal is ${testVal}")
//                        println("biometryEnabled is $biometryEnabled")
//                        delay(2000)
//                        println("biometryEnabled is $biometryEnabled")
//                        if (testVal) {
//                            showBiometricPrompt(ctx, onSuccess = { loginSuccess = true })
//                        }
                    }

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
