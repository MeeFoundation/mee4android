package foundation.mee.android_client.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import java.net.URI
import java.net.URL

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
    ContextCompat.startActivity(context, openURL, null)
}

fun buildLegacySiopUrl(uriPattern: String, data: String): String {
    val uri = URI(uriPattern)
    val builder = Uri.Builder()
    return builder.scheme(uri.scheme)
        .authority(uri.authority)
        .path("consent")
        .appendPath("authorize")
        .appendQueryParameter("scope", "openid")
        .appendQueryParameter("request", data)
        .build().toString()
}