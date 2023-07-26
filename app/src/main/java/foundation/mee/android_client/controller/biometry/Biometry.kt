package foundation.mee.android_client.controller.biometry

import android.widget.Toast
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
                        "${activityContext.getString(R.string.biometry_auth_error_toast)} $errString",
                        Toast.LENGTH_LONG
                    ).show()
                }

                if (errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                    activityContext.finishAffinity()
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
                    activityContext.getString(R.string.biometry_unrecognized_error_toast),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    )

    biometricPrompt.authenticate(promptInfo)
}
