package foundation.mee.android_client.views.consent

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import foundation.mee.android_client.models.ConsentRequest
import foundation.mee.android_client.models.MeeAgentStore
import uniffi.mee_agent.RpAuthResponseWrapper
import java.net.URI
import java.net.URL

@Composable
fun ConsentPage() {

    val context = LocalContext.current

    val appDir = context.applicationInfo.dataDir

    val agentStore = MeeAgentStore("$appDir/mee")

    val authorizeRequest = { data: ConsentRequest ->
        val request = clearConsentsListFromDisabledOptionals(data)
        agentStore.authorize(data.clientId, request)
    }

    ConsentPageNew(onAccept = authorizeRequest)
}

fun onNext(coreData: RpAuthResponseWrapper?, url: String, context: Context) {
    val ur = URI(url)
    Log.d("url", ur.toString())
    val builder = Uri.Builder()
    val result = builder.scheme(ur.scheme)
        .authority(ur.authority)
        .path(ur.path)
        .appendQueryParameter("mee_auth_token", coreData?.openidResponse?.idToken)
        .build()

    linkToWebpage(context, result)
    Log.d("result", result.toString())
}

/*
func onNext (_ coreData: RpAuthResponseWrapper, _ url: String) {

    if var urlComponents = URLComponents(string: url) {
        urlComponents.queryItems = [URLQueryItem(name: "mee_auth_token", value: coreData.openidResponse.idToken)]
        if let url = urlComponents.url {
            hadConnectionsBefore = true
            state.isReturningUser = true
            openURL(url)
            navigationState.currentPage = .mainPage
        }
    }
}*/


fun clearConsentsListFromDisabledOptionals(data: ConsentRequest): ConsentRequest {
    data.claims.map { claim ->
        if (!claim.isRequired && !claim.isOn) {
            claim.value = null
        }
        claim
    }
    return data
}