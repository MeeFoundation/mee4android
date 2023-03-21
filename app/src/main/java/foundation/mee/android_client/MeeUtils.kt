package foundation.mee.android_client

import java.net.URL

fun getURLFromString(url: String): URL? {
    return try {
        URL(url)
    } catch (e: java.lang.Exception) {
        null
    }
}
