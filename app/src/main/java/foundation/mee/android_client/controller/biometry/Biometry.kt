package foundation.mee.android_client.controller.biometry

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import foundation.mee.android_client.R

private val biometricsIgnoredErrors = listOf(
    BiometricPrompt.ERROR_NEGATIVE_BUTTON,
    BiometricPrompt.ERROR_CANCELED,
    BiometricPrompt.ERROR_USER_CANCELED,
    BiometricPrompt.ERROR_NO_BIOMETRICS
)

fun showBiometricPrompt(
    activityContext: FragmentActivity,
    onSuccess: (result: Boolean) -> Unit = {}
) {
    val promptInfo = biometryPromptBuilder(activityContext)

    val biometricPrompt = BiometricPrompt(
        activityContext,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(
                errorCode: Int,
                errString: CharSequence
            ) {
                if (errorCode !in biometricsIgnoredErrors) {
                    Toast.makeText(
                        activityContext,
                        "Failed to authenticate: $errString",
                        Toast.LENGTH_LONG
                    ).show()
                }

                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // TODO: add fallback for the low level APIs here
                }
                onSuccess(false)
            }

            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult
            ) {
                onSuccess(true)
            }

            override fun onAuthenticationFailed() {
                Toast.makeText(
                    activityContext,
                    "Unrecognized",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    )

    biometricPrompt.authenticate(promptInfo)
}

