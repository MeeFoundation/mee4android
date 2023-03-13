package foundation.mee.android_client

import uniffi.mee_agent.MeeAgent
import uniffi.mee_agent.MeeAgentConfig
import uniffi.mee_agent.MeeAgentDidRegistryConfig
import uniffi.mee_agent.getAgent
import java.io.File
import kotlin.system.exitProcess

class MeeAgentStore {
    private lateinit var agent: MeeAgent
    init {
        val folderURL = File("")
        if (folderURL.exists() && folderURL.isDirectory) {
            val dbURL = File(folderURL.absolutePath + "mee.sqlite")
             try {
                 agent = getAgent(config = MeeAgentConfig(dsUrl = dbURL.absolutePath, dsEncryptionPassword = null, didRegistryConfig = MeeAgentDidRegistryConfig.DidKey))
            } catch (e: Exception) {
                // Print something like in Swift
                error("Failed to init MeeAgent")
//                exitProcess(1)
            }
        }
    }

    fun getAllItems(): Array<Context>? {
        try {
            val contextsCore = agent.listMaterializedContexts()
//            val contexts: Array<Context>? = contextsCore.reduce<Context>(
//                return Context()
//            )
        }
        catch (e: Exception) {
            println("error getting all contexts: ${e.toString()}")
            return null
        }
        return null
    }
}
