package foundation.mee.android_client

import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import uniffi.mee_agent.*


class MeeAgentStore {
    private val agent: MeeAgent
    init {

        var appDir = ComponentActivity().applicationInfo.dataDir + "/mee"
        agent = getAgent(
            MeeAgentConfig(
                appDir,
                null,
                MeeAgentDidRegistryConfig.DidKey
            )
        )
    }

    fun getAllItems (): List<Context> {
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

                    val context = Context(
                        currentProtocol.value.redirectUri,
                        rec.did,
                        claims,
                        PartnerMetadata(currentProtocol.value.clientMetadata),
                    )
                    return@mapNotNull context
                }

            return@mapNotNull null
        }
        return contexts
    }
}