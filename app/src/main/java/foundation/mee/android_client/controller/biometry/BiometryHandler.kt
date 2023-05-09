package foundation.mee.android_client.controller.biometry

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
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

    var showedBiometry by remember {
        mutableStateOf(false)
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

    if (biometryEnabled and !showedBiometry) {
        showBiometricPrompt(activityContext, onSuccess = onSuccessfulAuth)
        showedBiometry = true
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