package foundation.mee.android_client.controller.biometry

import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import foundation.mee.android_client.R

fun biometryPromptBuilder(
    activityContext: FragmentActivity
) : BiometricPrompt.PromptInfo {
    val promptInfoBuilder = BiometricPrompt.PromptInfo.Builder()
        .setTitle(activityContext.getString(R.string.biometry_title))
        .setSubtitle(activityContext.getString(R.string.biometry_subtitle))

    var allowedAuth = BiometricManager.Authenticators.BIOMETRIC_STRONG
    var isNegativeButtonAllowed = true

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        allowedAuth = BiometricManager.Authenticators.DEVICE_CREDENTIAL or BiometricManager.Authenticators.BIOMETRIC_STRONG
        isNegativeButtonAllowed = false
    }

    if (isNegativeButtonAllowed) {
        promptInfoBuilder
            .setNegativeButtonText(
                activityContext.getString(R.string.biometry_negative_button_text)
            )
    }

    promptInfoBuilder
        .setAllowedAuthenticators(allowedAuth)
    return promptInfoBuilder.build()
}