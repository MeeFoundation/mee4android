package foundation.mee.android_client.controller.biometry

import android.util.Log
import androidx.compose.foundation.layout.Box
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
import foundation.mee.android_client.views.MeeWhiteScreen
import kotlinx.coroutines.launch


enum class BiometryState(val state: String) {
    disabled("disabled"),
    enabled("enabled"),
    unsaved("unsaved"),
    uninitialized("uninitialized")
}
@Composable
fun BiometryHandler(
    activityContext: FragmentActivity,
    onSuccessfulAuth: () -> Unit = {},
) {
    val settingsDataStore = MeeAndroidSettingsDataStore(context = LocalContext.current)

    val biometryEnabled by settingsDataStore.getBiometricAuthEnabledSetting().collectAsState(
        initial = BiometryState.uninitialized
    )

    val coroutineScope = rememberCoroutineScope()
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
    Box {
        MeeWhiteScreen()

        if (biometryEnabled == BiometryState.unsaved) {
            BiometryDialog(
                onDismiss = {
                    coroutineScope.launch {
                        settingsDataStore.saveBiometricAuthSetting(BiometryState.disabled)
                    }
                },
                onAccept = {
                    coroutineScope.launch {
                        settingsDataStore.saveBiometricAuthSetting(BiometryState.enabled)
                        biometryAttempts += 1
                    }

                },
                onReject = {
                    coroutineScope.launch {
                        settingsDataStore.saveBiometricAuthSetting(BiometryState.disabled)
                    }
                },
            )
        }
    }


    fun auth() {
        if (biometryEnabled == BiometryState.enabled) {
        showBiometricPrompt(activityContext, onSuccess = {
            if (it) {
                onSuccessfulAuth()
            } else {
                biometryAttempts += 1
            }
        })
        }
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

