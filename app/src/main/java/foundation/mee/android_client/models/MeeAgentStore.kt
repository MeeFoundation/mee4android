package foundation.mee.android_client.models

import android.app.Application
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.scopes.ActivityRetainedScoped
import foundation.mee.android_client.R
import foundation.mee.android_client.models.settings.MeeAndroidSettingsDataStore
import foundation.mee.android_client.models.settings.SharedPreferencesHelper
import foundation.mee.android_client.utils.generateSecret
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uniffi.mee_agent.*
import java.io.File
import javax.inject.Inject
import foundation.mee.android_client.utils.OidcAuthRequest
import foundation.mee.android_client.utils.getHostname

private const val MEE_KEYCHAIN_SECRET_NAME = "MEE_KEYCHAIN_SECRET_NAME"

@ActivityRetainedScoped
class MeeAgentStore @Inject constructor(
    val context: Application
) {
    private lateinit var agent: MeeAgent

    private val docsAbsolutePath = context.applicationInfo.dataDir
    private val dsUrl = "$docsAbsolutePath${File.separator}mee.sqlite"

    fun initMeeAgent() {
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

    fun getConnectionConnectors(id: String): List<MeeConnector>? {
        return try {
            val connectors = agent.getOtherPartyConnectionConnectors(id)
            connectors.map { MeeConnector(it) }
        } catch (e: Exception) {
            Log.e("error getting all connectors: ", e.message.orEmpty())
            null
        }
    }

    private fun getAllConnections(): List<MeeConnection>? {
        return try {
            agent.otherPartyConnections().map { MeeConnection(it) }
        } catch (e: Exception) {
            Log.e("error getting all connections: ", e.message.orEmpty())
            null
        }
    }

    fun getConnectionsWithConnectors(): List<MeeConnection>? {
        return getAllConnections()?.filter { !getConnectionConnectors(it.id).isNullOrEmpty() }
    }

     fun getLastConsentByConnectorId(id: String): MeeContext? {
        return try {
            val coreConsent = agent.siopLastConsentByConnectorId(connId = id)
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

    fun removeConnectorById(id: String): String? {
        return try {
            agent.deleteOtherPartyConnector(id)
            id
        } catch (e: Exception) {
            Log.e("removeItem", e.message.orEmpty())
            null
        }
    }

    fun removeConnectionById(id: String): String? {
        return try {
            agent.deleteOtherPartyConnection(id)
            id
        } catch (e: Exception) {
            Log.e("removeItem", e.message.orEmpty())
            null
        }
    }

    fun isReturningUser(redirectUri: String): Boolean {
        return getLastSiopConsentByRedirectUri(redirectUri) != null
    }

    fun recoverRequest(consentRequest: ConsentRequest): OidcAuthResponseWrapper? {
        val contextData = getLastSiopConsentByRedirectUri(consentRequest.redirectUri)
        return contextData?.let {
            ConsentRequest(it, consentRequest)
        }?.let { request -> authorize(request) }
    }

    private fun getLastSiopConsentByRedirectUri(redirectUri: String): MeeContext? {
        val connectors = getConnectionConnectors(getHostname(redirectUri))
        val consent = connectors?.firstOrNull {
            when (val protocol = it.connectorProtocol) {
                is MeeConnectorProtocol.Siop -> {
                    protocol.value.redirectUri == redirectUri
                }

                else -> false
            }
        }
        return consent?.let { getLastConsentByConnectorId(it.id) }
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

    fun getExternalConsentsByConnectorId(id: String): ExternalMeeContext? {
        return try {
            val coreConsent = agent.otherPartyContextsByConnectorId(id)
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

    // TODO mock
    // TODO comment everything to check emptiness
    fun getAllConnectionsTags(): List<String> {
        return listOf(
            "#Music",
            "#Cinema",
            "#Explore",
            "#Entertainment",
            "#Art",
            "#News",
            "#Tech",
            "#Science",
            "#Other"
        )
    }
}
