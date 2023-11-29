package foundation.mee.android_client.views.manage

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.models.MeeConnector
import foundation.mee.android_client.navigation.MeeDestinations
import foundation.mee.android_client.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ConnectionDataState<out T> {

    data class Success<T>(val data: T) : ConnectionDataState<T>()

    object None : ConnectionDataState<Nothing>()
}

@HiltViewModel
class ManageConnectionViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val meeAgentStore: MeeAgentStore
) : ViewModel() {

    private val _screenData = MutableStateFlow<ConnectionDataState<Pair<MeeConnector, ConsentEntriesType>>>(ConnectionDataState.None)
    val screenData: StateFlow<ConnectionDataState<Pair<MeeConnector, ConsentEntriesType>>> = _screenData

    init {
        viewModelScope.launch {
            loadData()
        }
    }

    private fun loadData() {
        try {
            val hostname: String = checkNotNull(savedStateHandle["connectionHostname"])
            val meeConnector = meeAgentStore.getConnectorByConnectionId(hostname)
            if (meeConnector != null) {
                val meeContext = meeAgentStore.getLastConnectionConsentById(meeConnector.otherPartyConnectionId)
                if (meeContext != null) {
                    _screenData.value =
                        ConnectionDataState.Success(
                            Pair(
                                meeConnector,
                                ConsentEntriesType.SiopClaims(meeContext.attributes)
                            )
                        )
                } else {
                    val external = meeAgentStore.getLastExternalConsentById(hostname)
                    if (external != null) {
                        _screenData.value =
                            ConnectionDataState.Success(
                                Pair(
                                    meeConnector,
                                    ConsentEntriesType.GapiEntries(external)
                                )
                            )
                    } else {
                        _screenData.value = ConnectionDataState.None
                    }
                }
            } else {
                _screenData.value = ConnectionDataState.None
            }
        } catch (e: Exception) {
            Log.e("loadData", e.message.orEmpty())
            _screenData.value = ConnectionDataState.None
        }
    }

    fun removeConnection(id: String, navigator: Navigator) {
        meeAgentStore.removeItemByConnectionId(id)
        navigator.navController.navigate(MeeDestinations.CONNECTIONS.route) {
            popUpTo(MeeDestinations.CONNECTIONS.route) {
                inclusive = true
            }
        }
    }
}