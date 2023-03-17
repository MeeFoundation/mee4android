package foundation.mee.android_client

import java.net.URL

public fun getURLFromString(url: String): URL? {
    return try {
        URL(url)
    }
    catch (e: java.lang.Exception) {
        null
    }
}