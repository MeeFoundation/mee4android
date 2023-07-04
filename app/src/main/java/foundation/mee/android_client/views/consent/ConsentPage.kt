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
import foundation.mee.android_client.utils.showConsentToast
import foundation.mee.android_client.views.animations.ConsentPageAnimation
import uniffi.mee_agent.RpAuthResponseWrapper
import java.net.URI

@Composable
fun ConsentPage(
    consentRequest: ConsentRequest,
    meeAgentStore: MeeAgentStore = hiltViewModel<MeeAgentViewModel>().meeAgentStore
) {
    val context = LocalContext.current
    val activity = context as Activity

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
                    onNext(response, consentRequest.redirectUri, context)
                } catch (e: Exception) {
                    Log.e("Connection failed", e.message.orEmpty())
                    showConsentToast(context, "Connection failed. Please try again")
                }
                activity.finishAffinity()
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

fun onNext(coreData: RpAuthResponseWrapper?, redirectUri: String, context: Context) {
    val uri = URI(redirectUri)
    val builder = Uri.Builder()
    val result = builder.scheme(uri.scheme)
        .authority(uri.authority)
        .path(uri.path)
        .appendQueryParameter("id_token", coreData?.openidResponse?.idToken)
        .encodedFragment(uri.fragment)
        .build()

    linkToWebpage(context, result)
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