package foundation.mee.android_client.controller.biometry

import android.os.Build
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import foundation.mee.android_client.R

fun biometryPromptBuilder(
    activityContext: FragmentActivity
): BiometricPrompt.PromptInfo {
    return with(BiometricPrompt.PromptInfo.Builder()) {
        setTitle(activityContext.getString(R.string.biometry_title))
        setSubtitle(activityContext.getString(R.string.biometry_subtitle))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setAllowedAuthenticators(DEVICE_CREDENTIAL or BIOMETRIC_STRONG)
        } else {
            setDeviceCredentialAllowed(true)
        }

        build()
    }
}