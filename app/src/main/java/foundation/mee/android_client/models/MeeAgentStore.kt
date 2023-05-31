package foundation.mee.android_client.models

import android.os.Environment
import android.util.Log
import foundation.mee.android_client.utils.RpAuthRequest
import foundation.mee.android_client.utils.getHostname
import uniffi.mee_agent.*
import java.io.File

object MeeAgentStore {
    private val agent: MeeAgent

    init {
        // TODO come up with the isolated path later and use MeeAndroidSettingsDataStore to save it
        val docsAbsolutePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath
        val dsUrl = "$docsAbsolutePath${File.separator}mee.sqlite"
        val file = File(dsUrl)
        if (!file.exists()) {
            file.createNewFile()
        }
        agent = getAgent(
            MeeAgentConfig(
                dsUrl,
                "ShouldBeReplacedWithGeneratedPassword",
                null,
                MeeAgentDidRegistryConfig.DidKey
            )
        )
        agent.initUserAccount()
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

    fun getAllContexts(): List<MeeContext>? {
        return try {
            val connectionsCore = agent.otherPartyConnections()
            val contexts = connectionsCore
                .mapNotNull { getLastConnectionConsentById(it.id) }
            contexts
        } catch (e: Exception) {
            Log.e("error getting all contexts: ", e.message.orEmpty())
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
                null
            }
        }
        return null
    }
}
