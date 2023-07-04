package foundation.mee.android_client.views.consent

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.MeeAgentViewModel
import foundation.mee.android_client.utils.linkToWebpage
import foundation.mee.android_client.models.ConsentRequest
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.navigation.MeeDestinations
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.utils.showConsentToast
import foundation.mee.android_client.views.animations.ConsentPageAnimation
import foundation.mee.android_client.service.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URI

@Composable
fun ConsentPage(
    consentRequest: ConsentRequest,
    meeAgentStore: MeeAgentStore = hiltViewModel<MeeAgentViewModel>().meeAgentStore,
    viewModel: NavViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val activity = context as Activity

    val navigator = viewModel.navigator

    fun navigateToMainScreen() {
        navigator.navigate(MeeDestinations.CONNECTIONS.route)
    }

    fun cleanConsent() {
        if (consentRequest.isCrossDeviceFlow) navigateToMainScreen() else activity.finishAffinity()
    }

    val authorizeRequest = { data: ConsentRequest ->
        val request = clearConsentsListFromDisabledOptionals(data)
        meeAgentStore.authorize(request)
    }

    val isReturningUser by rememberSaveable {
        mutableStateOf(meeAgentStore.isReturningUser(consentRequest.id))
    }

    if (isReturningUser) {
        val response = meeAgentStore.recoverRequest(consentRequest)
        if (response != null) {
            ConsentPageAnimation {
                try {
                    onNext(response.openidResponse.idToken, consentRequest.redirectUri, context, consentRequest.nonce, consentRequest.isCrossDeviceFlow, false)
                    cleanConsent()
                } catch (e: Exception) {
                    Log.e("Connection failed", e.message.orEmpty())
                    showConsentToast(context, "Connection failed. Please try again")
                }
            }
        } else {
            showConsentToast(context, "Connection failed. Please try again")
        }
    } else {
        ConsentPageNew(
            onAccept = authorizeRequest,
            consentViewModel = ConsentViewModel(consentRequest)
        )
    }
}

fun onNext(
    idToken: String?,
    redirectUri: String,
    context: Context,
    nonce: String,
    isCrossDeviceFlow: Boolean,
    isDecline: Boolean
) {
    if (!isCrossDeviceFlow) {
        val uri = URI(redirectUri)
        val builder = Uri.Builder()
        val result = builder.scheme(uri.scheme)
            .authority(uri.authority)
            .path(uri.path)
            .appendQueryParameter("id_token", idToken)
            .encodedFragment(uri.fragment)
            .build()
        linkToWebpage(context, result)
    } else {
        if (idToken == null) {
            showConsentToast(
                context,
                "Connection failed. Please try again."
            )
            return
        }
        GlobalScope.launch {
            try {
                WebService().passConsentOverRelay(nonce, idToken)
                withContext(Dispatchers.Main) {
                    showConsentToast(
                        context,
                        if (isDecline) "Connection declined." else "The connection has been set up!"
                    )
                }

            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    showConsentToast(context, "Connection failed. Please try again.")
                }

            }

        }
    }
}

fun clearConsentsListFromDisabledOptionals(data: ConsentRequest): ConsentRequest {
    data.claims.map { claim ->
        if (!claim.isRequired && !claim.isOn) {
            claim.value = null
        }
        claim
    }
    return data
}