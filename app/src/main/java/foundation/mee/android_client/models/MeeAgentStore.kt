package foundation.mee.android_client.models

import uniffi.mee_agent.MeeAgent
import uniffi.mee_agent.MeeAgentConfig
import uniffi.mee_agent.MeeAgentDidRegistryConfig
import uniffi.mee_agent.getAgent
import foundation.mee.android_client.helpers.RpAuthRequest
import uniffi.mee_agent.*


class MeeAgentStore(appDir: String) {
    private val agent: MeeAgent

    init {
        agent = getAgent(
            MeeAgentConfig(
                appDir,
                null,
                MeeAgentDidRegistryConfig.DidKey
            )
        )
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

    fun authorize(item: ConsentRequest): RpAuthResponseWrapper? {
        return try {
            agent.authRelyingParty(RpAuthRequest(item))
        } catch (e: java.lang.Exception) {
            null
        }
    }
}
