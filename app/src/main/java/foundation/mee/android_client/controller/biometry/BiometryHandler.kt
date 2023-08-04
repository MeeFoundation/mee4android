package foundation.mee.android_client.controller.biometry

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import foundation.mee.android_client.effects.OnLifecycleEvent

@Composable
fun BiometryHandler(
    activityContext: FragmentActivity,
    onSuccessfulAuth: () -> Unit = {},
) {
    var biometryAttempts by rememberSaveable {
        mutableStateOf(0)
    }

    var appIsActive by remember {
        mutableStateOf(false)
    }

    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                appIsActive = true
            }

            Lifecycle.Event.ON_STOP -> {
                appIsActive = false
            }

            else -> {}
        }
    }


    fun auth() {
        showBiometricPrompt(activityContext, onSuccess = {
            if (it) {
                onSuccessfulAuth()
            } else {
                biometryAttempts += 1
            }
        })
    }

    if (appIsActive) {
        LaunchedEffect(biometryAttempts) {
            auth()
        }
    }
}

