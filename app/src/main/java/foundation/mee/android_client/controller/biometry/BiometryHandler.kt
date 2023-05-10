package foundation.mee.android_client.controller.biometry

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import foundation.mee.android_client.effects.OnLifecycleEvent
import foundation.mee.android_client.models.settings.MeeAndroidSettingsDataStore
import foundation.mee.android_client.views.BiometryDialog
import kotlinx.coroutines.launch

@Composable
fun BiometryHandler(
    activityContext: FragmentActivity,
    onSuccessfulAuth: () -> Unit = {},
) {
    val settingsDataStore = MeeAndroidSettingsDataStore(context = LocalContext.current)

    val biometryAsked by settingsDataStore.getBiometricAuthAskedSetting().collectAsState(
        initial = true
    )

    var biometryAttempts by rememberSaveable {
        mutableStateOf(0)
    }

    var appIsActive by remember {
        mutableStateOf(true)
    }

    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                appIsActive = true
                biometryAttempts += 1
            }

            Lifecycle.Event.ON_PAUSE -> {
                appIsActive = false
            }

            else -> null
        }
    }

    val coroutineScope = rememberCoroutineScope()
    if (!biometryAsked) {
        BiometryDialog(
            onDismiss = {
                coroutineScope.launch {
                    settingsDataStore.saveBiometricAuthAskedSetting(false)
                }
            },
            onAccept = {
                coroutineScope.launch {
                    settingsDataStore.saveBiometricAuthAskedSetting(true)
                    settingsDataStore.saveBiometricAuthSetting(true)
                }
            },
            onReject = {
                coroutineScope.launch {
                    settingsDataStore.saveBiometricAuthAskedSetting(true)
                    settingsDataStore.saveBiometricAuthSetting(false)
                }
            },
        )
    }

    val biometryEnabled by settingsDataStore.getBiometricAuthEnabledSetting().collectAsState(
        initial = false
    )

    fun auth() {
//        if (biometryEnabled) {
        showBiometricPrompt(activityContext, onSuccess = {
            if (it) {
                onSuccessfulAuth()
            } else {
                biometryAttempts += 1
            }
        })
//        }
    }

    LaunchedEffect(biometryAttempts) {
        if (appIsActive) {
            auth()
        }
    }



//    TODO: uncomment in case of debugging
//    println("biometryAsked is $biometryAsked")
//    println("biometryEnabled is $biometryEnabled")

//    TODO: uncomment in case of resetting the flags
//    LaunchedEffect(Unit) {
//        settingsDataStore.saveBiometricAuthAskedSetting(false)
//        settingsDataStore.saveBiometricAuthSetting(false)
//    }
}

