package foundation.mee.android_client.models

import android.util.Log
import uniffi.mee_agent.MeeAgent
import uniffi.mee_agent.MeeAgentConfig
import uniffi.mee_agent.MeeAgentDidRegistryConfig
import uniffi.mee_agent.getAgent
import androidx.activity.ComponentActivity
import foundation.mee.android_client.helpers.RpAuthRequest
import uniffi.mee_agent.*
import java.io.File
import java.net.URL


class MeeAgentStore(appDir: String) {
    private val agent: MeeAgent

    init {

//        var appDir = ComponentActivity().applicationInfo.dataDir + "/mee"


        agent = getAgent(
            MeeAgentConfig(
                appDir,
                null,
                MeeAgentDidRegistryConfig.DidKey
            )
        )
        //TODO
        agent.initSelfCtx()
    }

    fun getAllItems(): List<MeeContext> {
        val contextsCore = agent.listMaterializedContexts();
        val contexts = contextsCore.filterIsInstance<MaterializedContext.RelyingParty>()
            .mapNotNull { rec ->
                val currentProtocol = rec.data.protocol;
                var claims: MutableList<ConsentRequestClaim> = mutableListOf()
                if (currentProtocol is ContextProtocol.Siop) {
                    val claimsArrayLast = currentProtocol.value.claims.size - 1
                    if (claimsArrayLast > 0) {
                        var coreClaims = currentProtocol.value.claims[claimsArrayLast]

                        for (coreClaim in coreClaims) {
                            val value = coreClaim.value
                            val key = coreClaim.key
                            if (value is OidcClaimParams) {
                                val claim = ConsentRequestClaim(value, key)
                                claims.add(claim)
                            }

                        }
                    }

                    val meeContext = MeeContext(
                        currentProtocol.value.redirectUri,
                        rec.did,
                        claims,
                        PartnerMetadata(currentProtocol.value.clientMetadata),
                    )
                    return@mapNotNull meeContext
                }

                return@mapNotNull null
            }
        return contexts
    }

    // TODO multithread
    fun authorize(id: String, item: ConsentRequest): RpAuthResponseWrapper? {
        Log.d("RpAuthRequest", RpAuthRequest(item).toString())
        return try {
            agent.authRelyingParty(RpAuthRequest(item))
        } catch (e: java.lang.Exception) { // TODO
            null
        }
    }
}
