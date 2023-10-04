package foundation.mee.android_client

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import foundation.mee.android_client.models.MeeAgentStore
import uniffi.mee_agent.MeeException
import uniffi.mee_agent.MeeStorageException
import javax.inject.Inject

sealed class Result<out R>(val type: Type) {
    enum class Type {
        SUCCESS, INIT_AGENT_ERROR, MIGRATION_ERROR
    }

    object Success : Result<Nothing>(Type.SUCCESS)
    open class InitAgentError(open val exception: Exception, type: Type = Type.INIT_AGENT_ERROR) :
        Result<Nothing>(type)

    open class DbError(override val exception: Exception, type: Type = Type.INIT_AGENT_ERROR) :
        InitAgentError(exception, type)

    open class MigrationError(
        override val exception: Exception,
        type: Type = Type.MIGRATION_ERROR
    ) : DbError(exception, type)
}

@HiltViewModel
class MeeAgentViewModel @Inject constructor(
    val meeAgentStore: MeeAgentStore
) : ViewModel() {
    fun initMeeAgent(): Result<Nothing> {
        return try {
            meeAgentStore.initMeeAgent()
            Result.Success
        } catch (e: MeeException) {
            Log.e("Mee Agent exception", e.message.orEmpty())
            when (e) {
                is MeeException.MeeStorage -> {
                    when (e.error) {
                        is MeeStorageException.AppLevelMigration -> Result.MigrationError(e)
                        else -> Result.DbError(e)
                    }
                }
                else -> Result.InitAgentError(e)
            }
        } catch (e: Exception) {
            Log.e("Unable to init Mee Agent", e.message.orEmpty())
            Result.InitAgentError(e)
        }
    }
}
