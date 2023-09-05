package foundation.mee.android_client.models

import android.app.Application
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.scopes.ActivityRetainedScoped
import foundation.mee.android_client.R
import foundation.mee.android_client.models.settings.MeeAndroidSettingsDataStore
import foundation.mee.android_client.models.settings.SharedPreferencesHelper
import foundation.mee.android_client.utils.generateSecret
import foundation.mee.android_client.utils.getHostname
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uniffi.mee_agent.*
import java.io.File
import javax.inject.Inject
import foundation.mee.android_client.utils.OidcAuthRequest

private const val MEE_KEYCHAIN_SECRET_NAME = "MEE_KEYCHAIN_SECRET_NAME"

@ActivityRetainedScoped
class MeeAgentStore @Inject constructor(
    val context: Application
) {
    private lateinit var agent: MeeAgent

    private val docsAbsolutePath = context.applicationInfo.dataDir
    private val dsUrl = "$docsAbsolutePath${File.separator}mee.sqlite"

    init {
        try {
            initMeeAgent()
        } catch (e: Exception) {
            Log.e("Unable to init Mee Agent", e.message.orEmpty())
        }
    }

    private fun initMeeAgent() {
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
    }

    fun getAllItems(): List<MeeConnector>? {
        return try {
            val connectionsCore = agent.otherPartyConnections()
            connectionsCore.map {
                MeeConnector(agent.getOtherPartyConnectionConnectors(it.id).first())
            }
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
            Log.e("getLastConnectionConsent", e.message.orEmpty())
            null
        }
    }

    fun authorize(item: ConsentRequest): OidcAuthResponseWrapper? {
        return try {
            agent.siopAuthRelyingParty(OidcAuthRequest(item))
        } catch (e: Exception) {
            Log.e("authorize", e.message.orEmpty())
            null
        }
    }

    private fun getConnectionById(id: String): MeeConnector? {
        val items = getAllItems() ?: return null
        return items.firstOrNull { it.id == id }
    }

    fun getConnectionByHostname(hostname: String): MeeConnector? {
        val items = getAllItems() ?: return null
        return items.firstOrNull { getHostname(it.otherPartyConnectionId) == hostname }
    }

    fun removeItemByName(id: String): String? {
        val item = getConnectionByHostname(id)
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

    fun recoverRequest(consentRequest: ConsentRequest): OidcAuthResponseWrapper? {
        return getLastConnectionConsentById(consentRequest.id)?.let { contextData ->
            ConsentRequest(contextData, consentRequest)
        }?.let { request -> authorize(request) }
    }

    fun getGoogleIntegrationUrl(): Url? {
        return try {
            agent.googleApiProviderCreateOauthBrowsableUrl()
        } catch (e: Exception) {
            Log.e("getGoogleIntegrationUrl", e.message.orEmpty())
            null
        }
    }

    suspend fun createGoogleConnection(url: String) {
        try {
            agent.googleApiProviderCreateOauthConnection(url)
            val settingsDataStore = MeeAndroidSettingsDataStore(context)
            settingsDataStore.saveHadConnectionsBeforeSetting(flag = true)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    context.getString(R.string.connection_success_toast),
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            val errString = e.message.orEmpty()
            Log.e("createGoogleConnection", errString)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "${context.getString(R.string.connection_failed_toast)} $errString",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun getLastExternalConsentById(id: String): ExternalMeeContext? {
        return try {
            val coreConsent = agent.otherPartyContextsByConnectionId(id)
            if (coreConsent.isNotEmpty()) {
                return ExternalMeeContext(coreConsent.last())
            } else null
        } catch (e: Exception) {
            Log.e("createGoogleConnection", e.message.orEmpty())
            null
        }
    }

    private fun clearMeeAgentData() {
        agent.deleteAllData()

        val prefs = SharedPreferencesHelper(context)
        prefs.clearPreferences()

        val file = File(dsUrl)
        if (file.exists()) {
            file.delete()
        }
    }

    fun resetMeeAgent() {
        clearMeeAgentData()
        initMeeAgent()
    }
}
