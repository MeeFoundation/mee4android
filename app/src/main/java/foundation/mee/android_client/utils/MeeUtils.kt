package foundation.mee.android_client.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import java.net.URL

fun getURLFromString(url: String): URL? {
    return try {
        URL(url)
    } catch (e: java.lang.Exception) {
        null
    }
}


fun linkToWebpage(context: Context, uri: Uri) {
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = uri
    ContextCompat.startActivity(context, openURL, null)
}