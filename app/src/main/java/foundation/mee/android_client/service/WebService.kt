package foundation.mee.android_client.service

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.net.URL


const val baseUrl: String = "https://relay-dev.mee.foundation"

class WebService {
    private var client: OkHttpClient = OkHttpClient()

    suspend fun passConsentOverRelay(id: String, data: String) {
        val url = URL("$baseUrl/put")
        val jsonObject = JSONObject()
        jsonObject.put("session_id", id)
        jsonObject.put("oidc_response", data)
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = jsonObject.toString().toRequestBody(mediaType)
        val request = Request.Builder().url(url).post(body).build()
        withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            val code = response.code
            if (!response.isSuccessful || (code != 200 && code != 201)) {
                Log.d("response: ", response.toString())
                throw IOException("Request was unsuccessful.")
            }
        }


    }
}