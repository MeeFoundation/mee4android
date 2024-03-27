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

    private var _tagList = mutableStateListOf<MeeTag>()
    val tagList: List<MeeTag> = _tagList

    private var _selectedTagList = mutableStateListOf<MeeTag>()
    val selectedTagList: List<MeeTag> = _selectedTagList

    val showTagsPanel: Boolean
        get() = tagList.isNotEmpty() || selectedTagList.isNotEmpty()

    fun update() {
        viewModelScope.launch {
            val allTagsWithConnections = meeAgentStore.getAllTagsWithConnections() ?: listOf()
            with(_selectedTagList) {
                val refreshedSelected = filter { allTagsWithConnections.contains(it) }
                clear()
                addAll(refreshedSelected)
            }
            with(_tagList) {
                clear()
                val notSelected = allTagsWithConnections.filter { !selectedTagList.contains(it) }.sortedBy { it.name }
                addAll(notSelected)
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

    fun selectTag(index: Int, tag: MeeTag) {
        with(_selectedTagList) {
            add(tag)
            sortBy { it.name }
        }
        with(_connections) {
            value = selectedTagList.flatMap { getConnectionsByTagId(it.id) }.distinct()
        }
        with(_tagList) {
            removeAt(index)
        }
    }

    fun unselectTag(index: Int, tag: MeeTag) {
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
        with(_tagList) {
            add(tag)
            sortBy { it.name }
        }
    }
}