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
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.navigation.Navigator
import foundation.mee.android_client.R
import foundation.mee.android_client.utils.showConsentToast
import foundation.mee.android_client.views.animations.ConsentPageAnimation
import foundation.mee.android_client.service.WebService
import kotlinx.coroutines.*
import java.io.IOException
import java.net.URI

@Composable
fun ConsentPage(
    consentRequest: ConsentRequest,
    meeAgentStore: MeeAgentStore = hiltViewModel<MeeAgentViewModel>().meeAgentStore,
    navigator: Navigator = hiltViewModel<NavViewModel>().navigator
) {
    val context = LocalContext.current
    val activity = context as Activity

    val cleanConsent =
        { if (consentRequest.isCrossDeviceFlow) navigator.navigateToMainScreen() else activity.finishAffinity() }

    val authorizeRequest = { data: ConsentRequest ->
        val request = clearConsentsListFromDisabledOptionals(data)
        meeAgentStore.authorize(request)
    }

    val isReturningUser by rememberSaveable {
        mutableStateOf(meeAgentStore.isReturningUser(consentRequest.id))
    }

    // TODO rethink returning user flow in case of 2 connectors
    if (/*isReturningUser*/false) {
        val response = meeAgentStore.recoverRequest(consentRequest)
        if (response != null) {
            ConsentPageAnimation {
                try {
                    onNext(
                        response.openidResponse.idToken,
                        consentRequest.redirectUri,
                        context,
                        consentRequest.nonce,
                        consentRequest.isCrossDeviceFlow,
                        false
                    )
                    cleanConsent()
                } catch (e: Exception) {
                    Log.e("Connection failed", e.message.orEmpty())
                    showConsentToast(context, R.string.connection_failed_toast)
                }
            }
        } else {
            showConsentToast(context, R.string.connection_failed_toast)
        }
    } else {
        ConsentPageNew(
            onAccept = authorizeRequest,
            onCleanConsent = cleanConsent,
            consentViewModel = ConsentViewModel(consentRequest)
        )
    }
}

@OptIn(DelicateCoroutinesApi::class)
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
                R.string.connection_failed_toast
            )
            return
        }
        GlobalScope.launch {
            try {
                WebService().passConsentOverRelay(nonce, idToken)
                withContext(Dispatchers.Main) {
                    showConsentToast(
                        context,
                        if (isDecline) R.string.connection_declined_toast else R.string.connection_success_toast
                    )
                }

            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    showConsentToast(context, R.string.connection_failed_toast)
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