package foundation.mee.android_client.views.consent

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.MeeAgentViewModel
import foundation.mee.android_client.utils.linkToWebpage
import foundation.mee.android_client.models.ConsentRequest
import uniffi.mee_agent.RpAuthResponseWrapper
import java.net.URI

@Composable
fun ConsentPage(consentRequest: ConsentRequest, meeAgentViewModel: MeeAgentViewModel = hiltViewModel()) {

    val authorizeRequest = { data: ConsentRequest ->
        val request = clearConsentsListFromDisabledOptionals(data)
        meeAgentViewModel.meeAgentStore.authorize(request)
    }

    ConsentPageNew(onAccept = authorizeRequest, consentViewModel = ConsentViewModel(consentRequest))
}

fun onNext(coreData: RpAuthResponseWrapper?, redirectUri: String, context: Context) {
    val uri = URI(redirectUri)
    val builder = Uri.Builder()
    val result = builder.scheme(uri.scheme)
        .authority(uri.authority)
        .path(uri.path)
        .appendQueryParameter("id_token", coreData?.openidResponse?.idToken)
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