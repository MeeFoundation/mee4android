package foundation.mee.android_client.models

import android.util.Log
import uniffi.mee_agent.MeeAgent
import uniffi.mee_agent.MeeAgentConfig
import uniffi.mee_agent.MeeAgentDidRegistryConfig
import uniffi.mee_agent.getAgent
import foundation.mee.android_client.utils.RpAuthRequest
import uniffi.mee_agent.*


class MeeAgentStore(appDir: String) {
    private val agent: MeeAgent

    init {
        agent = getAgent(
            MeeAgentConfig(
                appDir,
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

    private fun getLastConnectionConsentById(id: String): MeeContext? {
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
}
