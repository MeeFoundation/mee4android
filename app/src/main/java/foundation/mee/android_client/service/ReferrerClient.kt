package foundation.mee.android_client.service

import android.content.Context
import android.util.Base64
import android.util.Log
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

const val REFERRER_LINK_PREFIX = "authorize_mee="

class ReferrerClient(val context: Context) {

    fun getReferrerUrl(onReceive: (String) -> Unit) {
        val referrerClient = InstallReferrerClient.newBuilder(context).build()
        val installReferrerStateListener = object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        try {
                            val response = referrerClient.installReferrer
                            val referrerUrl = response.installReferrer
                            if (referrerUrl != null) {
                                val params = referrerUrl.split("&")
                                val url = params.find { it.startsWith(REFERRER_LINK_PREFIX) }
                                    ?.substring(REFERRER_LINK_PREFIX.length)
                                if (url != null) {
                                    val decoded = String(Base64.decode(url, Base64.DEFAULT))
                                    val encodedUrl = URLEncoder.encode(decoded, StandardCharsets.UTF_8.toString())
                                    onReceive(encodedUrl)
                                }
                            }
                        } catch (e: java.lang.Exception) {
                            Log.e("InstallReferrerStateListener", e.message.orEmpty())
                        }
                    }
                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                        Log.e("InstallReferrerResponse", "FEATURE_NOT_SUPPORTED")
                    }
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                        Log.e("InstallReferrerResponse", "SERVICE_UNAVAILABLE")
                    }
                }
                try {
                    referrerClient.endConnection()
                } catch (e: Exception) {
                    Log.e("ReferrerClient", "was not able to end connection")
                }
            }

            override fun onInstallReferrerServiceDisconnected() {}
        }
        try {
            referrerClient.startConnection(installReferrerStateListener)
        } catch (e: Exception) {
            Log.e("ReferrerClient", "was not able to end connection")
        }
    }
}