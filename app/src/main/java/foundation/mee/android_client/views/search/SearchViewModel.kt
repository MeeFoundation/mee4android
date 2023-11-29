package foundation.mee.android_client.views.search

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import foundation.mee.android_client.models.ClientType
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.models.MeeConnector
import foundation.mee.android_client.models.MeeConnectorType
import foundation.mee.android_client.utils.FuzzySearchHelper.getFoundConnectors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val context: Application,
    val meeAgentStore: MeeAgentStore
) : ViewModel() {

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking = _isSpeaking.asStateFlow()

    private val _searchMenu = MutableStateFlow(false)
    val searchMenu = _searchMenu.asStateFlow()

    private val _searchState = MutableStateFlow("")
    val searchState = _searchState.asStateFlow()

    private var _connectors = MutableStateFlow(getAllConnectors())
    private val connectors = searchState.combine(_connectors) { query, conn ->
        if (query.isBlank()) {
            conn
        } else {
            getFoundConnectors(query, conn)
        }
    }

    private fun getAllConnectors(): List<MeeConnector> {
        return meeAgentStore.getAllItems() ?: listOf()
    }

    fun onChange(query: String) {
        _searchState.value = query
    }

    fun onCancelClick() {
        _searchState.value = ""
    }

    fun showSearchMenu() {
        _searchMenu.value = true
    }

    fun hideSearchMenu() {
        _searchMenu.value = false
        _isSpeaking.value = false
    }

    fun getWebConnectors(): Flow<List<MeeConnector>> {
        return getConnectorsByClientType(connectors, ClientType.web)
    }

    fun getMobileConnectors(): Flow<List<MeeConnector>> {
        return getConnectorsByClientType(connectors, ClientType.mobile)
    }

    fun onChangeIsSpeaking(value: Boolean) {
        _isSpeaking.value = value
    }

    private fun getConnectorsByClientType(
        connections: Flow<List<MeeConnector>>,
        clientType: ClientType
    ): Flow<List<MeeConnector>> {
        return connections.map { list ->
            list.filter {
                when (val connType = it.value) {
                    is MeeConnectorType.Siop -> when (connType.value.clientMetadata.type) {
                        ClientType.web -> clientType == ClientType.web
                        ClientType.mobile -> clientType == ClientType.mobile
                    }

                    is MeeConnectorType.Gapi -> when (clientType) {
                        ClientType.web -> true
                        ClientType.mobile -> false
                    }

                    else -> false
                }
            }
        }
    }
}