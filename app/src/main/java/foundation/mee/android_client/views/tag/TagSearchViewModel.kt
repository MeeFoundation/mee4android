package foundation.mee.android_client.views.tag

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.models.MeeTag
import foundation.mee.android_client.utils.FuzzySearchHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

abstract class TagSearchViewModel constructor(
    val context: Application,
    val meeAgentStore: MeeAgentStore
) : ViewModel() {

    protected val _searchState = MutableStateFlow("")
    val searchState = _searchState.asStateFlow()

    private val _searchMenu = MutableStateFlow(false)
    val searchMenu = _searchMenu.asStateFlow()

    protected var _selectedTagsMap = mutableStateMapOf<MeeTag, Unit>()

    val isTagSelected = { tag: MeeTag -> tag in _selectedTagsMap }

    protected var _allTags = MutableStateFlow(getAllTags())
    protected val _foundTags = searchState.combine(_allTags) { query, tag ->
        if (query.isBlank()) {
            tag
        } else {
            FuzzySearchHelper.getFoundTags(query, tag)
        }
    }

    val showTagsPanel: Boolean
        get() = _allTags.value.isNotEmpty()

    fun addTagsToSelected(tags: List<MeeTag>) {
        _selectedTagsMap.putAll(tags.map { it to Unit })
    }

    val isQueryEmpty = _searchState.map { value ->
        removeSpecialSymbols(value).isBlank()
    }

    fun onCancelClick() {
        _searchState.value = ""
    }

    fun onChange(query: String) {
        _searchState.value = query
    }

    fun showSearchMenu() {
        _searchMenu.value = true
    }

    fun hideSearchMenu() {
        _searchMenu.value = false
    }

    open fun getTagsFlow(): Flow<List<MeeTag>> {
        return _foundTags.map { tags ->
            tags.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
        }
    }

    open fun getAllTagsFlow(): Flow<List<MeeTag>> {
        return _allTags.map { tags ->
            tags.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
        }
    }

    open fun getAllTags(): List<MeeTag> {
        return meeAgentStore.getAllTagsWithConnectors() ?: listOf()
    }

    fun updateTag(tag: MeeTag) {
        if (tag in _selectedTagsMap) {
            _selectedTagsMap.remove(tag)
        } else {
            _selectedTagsMap[tag] = Unit
        }
    }

    fun removeSpecialSymbols(string: String): String {
        return string.lowercase().trim().replace(
            "[^a-z0-9 ]+".toRegex(),
            ""
        )
    }
}