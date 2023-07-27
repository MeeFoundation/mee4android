package foundation.mee.android_client.models

import android.app.Application
import android.util.Log
import dagger.hilt.android.scopes.ActivityRetainedScoped
import foundation.mee.android_client.models.settings.SharedPreferencesHelper
import foundation.mee.android_client.utils.RpAuthRequest
import foundation.mee.android_client.utils.generateSecret
import foundation.mee.android_client.utils.getHostname
import uniffi.mee_agent.*
import java.io.File
import javax.inject.Inject

private const val MEE_KEYCHAIN_SECRET_NAME = "MEE_KEYCHAIN_SECRET_NAME"

@ActivityRetainedScoped
class MeeAgentStore @Inject constructor(
    context: Application
) {
    private lateinit var agent: MeeAgent

    init {
        try {
            val docsAbsolutePath = context.applicationInfo.dataDir
            val dsUrl = "$docsAbsolutePath${File.separator}mee.sqlite"
            val file = File(dsUrl)
            if (!file.exists()) {
                file.createNewFile()
            }

            val prefs = SharedPreferencesHelper(context)
            var value = prefs.getString(MEE_KEYCHAIN_SECRET_NAME)
            if (value == null) {
                value = generateSecret(30)
                prefs.setString(MEE_KEYCHAIN_SECRET_NAME, value)
            }
            agent = getAgent(
                MeeAgentConfig(
                    dsUrl,
                    value,
                    null,
                    MeeAgentDidRegistryConfig.DidKey
                )
            )
            agent.initUserAccount()
        } catch (e: Exception) {
            Log.e("Unable to init Mee Agent", e.message.orEmpty())
        }
    }

    fun getAllItems(): List<MeeConnection>? {
        return try {
            val connectionsCore = agent.otherPartyConnections()
            connectionsCore.map { MeeConnection(it) }
        } catch (e: Exception) {
            Log.e("error getting all connections: ", e.message.orEmpty())
            null
        }
    }

    fun getLastConnectionConsentById(id: String): MeeContext? {
        return try {
            val coreConsent = agent.siopLastConsentByConnectionId(connId = id)
            if (coreConsent != null) {
                MeeContext(coreConsent)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    fun getLastMeeContextById(id: String): MeeContext? {
        return try {
            val coreConsent = agent.siopLastConsentByConnectionId(connId = id)
            if (coreConsent != null) {
                MeeContext(coreConsent)
            } else null
        } catch (e: Exception) {
            Log.e("getLastMeeContext", e.message.orEmpty())
            null
        }
    }

    fun authorize(item: ConsentRequest): RpAuthResponseWrapper? {
        return try {
            agent.siopAuthRelyingParty(RpAuthRequest(item))
        } catch (e: Exception) {
            Log.e("authorize", e.message.orEmpty())
            null
        }
    }

    private fun getConnectionById(id: String): MeeConnection? {
        val items = getAllItems() ?: return null
        return items.firstOrNull { it.id == id }
    }

    fun getConnectionByHostname(hostname: String): MeeConnection? {
        val items = getAllItems() ?: return null
        return items.firstOrNull { getHostname(it.id) == hostname }
    }

    fun removeItemByName(id: String): String? {
        val item = getConnectionById(id)
        if (item != null) {
            return try {
                agent.deleteOtherPartyConnection(id)
                id
            } catch (e: Exception) {
                Log.e("removeItem", e.message.orEmpty())
                null
            }
        }
        return null
    }

    fun isReturningUser(id: String): Boolean = getConnectionById(id) != null

    fun recoverRequest(consentRequest: ConsentRequest): RpAuthResponseWrapper? {
        return getLastConnectionConsentById(consentRequest.id)?.let { contextData ->
            ConsentRequest(contextData, consentRequest)
        }?.let { request -> authorize(request) }
    }
}
