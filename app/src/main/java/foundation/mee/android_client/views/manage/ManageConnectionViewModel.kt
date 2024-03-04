package foundation.mee.android_client.views.manage

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import foundation.mee.android_client.models.ConnectorToEntries
import foundation.mee.android_client.models.ManageConnectionData
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.models.MeeConnector
import foundation.mee.android_client.models.MeeConnectorType
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

    private val _screenData =
        MutableStateFlow<ConnectionDataState<ManageConnectionData>>(
            ConnectionDataState.None
        )
    val screenData: StateFlow<ConnectionDataState<ManageConnectionData>> =
        _screenData

    init {
        viewModelScope.launch {
            loadData()
        }
    }

    fun updateViewmodel() {
        viewModelScope.launch {
            loadData()
        }
    }

    private fun loadData() {
        try {
            val hostname: String = checkNotNull(savedStateHandle["connectionHostname"])
            val meeConnection =
                meeAgentStore.getConnectionsWithConnectors()?.first { it.id == hostname }
            if (meeConnection != null) {
                val meeConnectors = meeAgentStore.getConnectionConnectors(hostname)
                if (meeConnectors != null) {
                    val res = meeConnectors.mapNotNull { connector ->
                        toConsentEntriesType(connector)?.let {
                            ConnectorToEntries(
                                connector,
                                it
                            )
                        }
                    }
                    _screenData.value =
                        ConnectionDataState.Success(
                            ManageConnectionData(meeConnection, res)
                        )
                } else {
                    _screenData.value = ConnectionDataState.None
                }
            } else {
                _screenData.value = ConnectionDataState.None
            }
        } catch (e: Exception) {
            Log.e("loadData", e.message.orEmpty())
            _screenData.value = ConnectionDataState.None
        }
    }

    private fun toConsentEntriesType(connector: MeeConnector): ConsentEntriesType? {
        return when (connector.value) {
            is MeeConnectorType.Siop -> {
                meeAgentStore.getLastConnectionConsentByConnectorId(connector.id)
                    ?.let { meeContext ->
                        ConsentEntriesType.SiopClaims(meeContext.attributes)
                    }
            }

            is MeeConnectorType.Gapi -> {
                meeAgentStore.getExternalConsentsByConnectorId(connector.id)
                    ?.let { ConsentEntriesType.GapiEntries(it) }
            }

            else -> null
        }
    }

    fun removeConnection(id: String, navigator: Navigator) {
        meeAgentStore.removeConnectionById(id)
        navigator.navController.navigate(MeeDestinations.CONNECTIONS.route) {
            popUpTo(MeeDestinations.CONNECTIONS.route) {
                inclusive = true
            }
        }
    }

    fun removeConnector(id: String) {
        meeAgentStore.removeConnectorById(id)
    }
}