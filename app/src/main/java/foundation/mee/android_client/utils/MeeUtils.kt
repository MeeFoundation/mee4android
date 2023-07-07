package foundation.mee.android_client.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Base64
import foundation.mee.android_client.models.ConsentRequest
import uniffi.mee_agent.siopRpAuthRequestFromUrl
import java.net.URL
import java.security.SecureRandom

fun getURLFromString(url: String): URL? {
    return try {
        URL(url)
    } catch (e: java.lang.Exception) {
        null
    }
}

fun getHostname(url: String): String {
    return getURLFromString(url)?.host ?: url
}


fun linkToWebpage(context: Context, uri: Uri) {
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = uri
    context.startActivity(openURL)
}

fun generateSecret(length: Int): String {
    val random = SecureRandom()
    val bytes = ByteArray(length)
    random.nextBytes(bytes)
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}

fun buildConsentRequestFromUrl(url: String): ConsentRequest? {
    return try {
        val res = siopRpAuthRequestFromUrl(url)
        val respondTo = Uri.parse(url).getQueryParameter("respondTo")
        val isCrossDeviceFlow = respondTo != null && respondTo == "proxy"
        ConsentRequest(res, isCrossDeviceFlow)
    } catch (e: Exception) {
        null
    }
}
