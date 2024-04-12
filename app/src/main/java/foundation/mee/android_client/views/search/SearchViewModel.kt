package foundation.mee.android_client.views.search

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.models.MeeConnection
import foundation.mee.android_client.models.MeeTag
import foundation.mee.android_client.utils.FuzzySearchHelper.getFoundConnections
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
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

    private var _connections = MutableStateFlow(getAllConnections())
    private val connections = searchState.combine(_connections) { query, conn ->
        if (query.isBlank()) {
            conn
        } else {
            getFoundConnections(query, conn)
        }
    }

    private var _selectedTagList = mutableStateListOf<MeeTag>()
    val selectedTagList: List<MeeTag> = _selectedTagList

    fun update() {
        viewModelScope.launch {
            val allTagsWithConnections = meeAgentStore.getAllTagsWithConnectors() ?: listOf()
            with(_selectedTagList) {
                val refreshedSelected = filter { allTagsWithConnections.contains(it) }
                clear()
                addAll(refreshedSelected)
            }
            with(_connections) {
                value = if (selectedTagList.isEmpty()) {
                    getAllConnections()
                } else {
                    selectedTagList.flatMap { getConnectionsByTagId(it.id) }.distinct()
                }
            }
        }
    }

    init {
        update()
    }

    private fun getAllConnections(): List<MeeConnection> {
        return meeAgentStore.getConnectionsWithConnectors() ?: listOf()
    }

    private fun getConnectionsByTagId(id: String): List<MeeConnection> {
        return meeAgentStore.getConnectionsByTagId(id) ?: listOf()
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

    fun getConnectionsFlow(): Flow<List<MeeConnection>> {
        return connections
    }

    fun onChangeIsSpeaking(value: Boolean) {
        _isSpeaking.value = value
    }

    fun updateTag(tag: MeeTag) {
        if (tag in _selectedTagList) {
            with(_selectedTagList) {
                remove(tag)
            }
            with(_connections) {
                value = if (selectedTagList.isEmpty()) {
                    getAllConnections()
                } else {
                    selectedTagList.flatMap { getConnectionsByTagId(it.id) }.distinct()
                }
            }
        } else {
            with(_selectedTagList) {
                add(tag)
                sortBy { it.name }
            }
            with(_connections) {
                value = selectedTagList.flatMap { getConnectionsByTagId(it.id) }.distinct()
            }
        }
    }

    fun unselectTag(index: Int) {
        with(_selectedTagList) {
            removeAt(index)
        }
        with(_connections) {
            value = if (selectedTagList.isEmpty()) {
                getAllConnections()
            } else {
                selectedTagList.flatMap { getConnectionsByTagId(it.id) }.distinct()
            }
        }
    }
}