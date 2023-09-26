package foundation.mee.android_client

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import foundation.mee.android_client.models.MeeAgentStore
import uniffi.mee_agent.MeeErr
import javax.inject.Inject

sealed class Result<out R> {
    object Success : Result<Nothing>()
    open class InitAgentError(open val exception: Exception) : Result<Nothing>()
    data class DbError(override val exception: Exception) : InitAgentError(exception)
}

@HiltViewModel
class MeeAgentViewModel @Inject constructor(
    val meeAgentStore: MeeAgentStore
) : ViewModel() {
    fun initMeeAgent(): Result<Nothing> {
        return try {
            meeAgentStore.initMeeAgent()
            Result.Success
        } catch (e: MeeErr.MeeStorageException) {
            Log.e("MeeAgent db error", e.message.orEmpty())
            Result.DbError(e)
        } catch (e: Exception) {
            Log.e("Unable to init Mee Agent", e.message.orEmpty())
            Result.InitAgentError(e)
        }
    }
}
