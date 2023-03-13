package foundation.mee.android_client

import uniffi.mee_agent.MeeAgent
import uniffi.mee_agent.MeeAgentConfig
import uniffi.mee_agent.MeeAgentDidRegistryConfig
import uniffi.mee_agent.getAgent
import java.io.File
import kotlin.system.exitProcess

//class MeeAgentStore {
//    private lateinit var agent: MeeAgent
//    init {
//        val folderURL = File("")
//        if (folderURL.exists() && folderURL.isDirectory) {
//            val dbURL = File(folderURL.absolutePath + "mee.sqlite")
//             try {
//                 agent = getAgent(config = MeeAgentConfig(dsUrl = dbURL.absolutePath, dsEncryptionPassword = null, didRegistryConfig = MeeAgentDidRegistryConfig.DidKey))
//            } catch (e: Exception) {
//                // Print something like in Swift
//                error("Failed to init MeeAgent")
////                exitProcess(1)
//            }
//        }
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

//    fun getAllItems(): Array<Context>? {
//        try {
//            val contextsCore = agent.listMaterializedContexts()
////            val contexts: Array<Context>? = contextsCore.reduce<Context>(
////                return Context()
////            )
//        }
//        catch (e: Exception) {
//            println("error getting all contexts: ${e.toString()}")
//            return null
//        }
//        return null
//    }
}
