package foundation.mee.android_client.views.tag

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.models.MeeTag
import foundation.mee.android_client.utils.FuzzySearchHelper
import foundation.mee.android_client.utils.showConsentToast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TagSearchViewModel @Inject constructor(
    val context: Application,
    val meeAgentStore: MeeAgentStore
) : ViewModel() {

    private val _searchState = MutableStateFlow("")
    val searchState = _searchState.asStateFlow()
    val newTagText = _searchState.map {
        removeSpecialSymbols(it)
    }

    private val _searchMenu = MutableStateFlow(false)
    val searchMenu = _searchMenu.asStateFlow()

    private var _selectedTagsMap = mutableStateMapOf<MeeTag, Unit>()
    val selectedTagsList: List<MeeTag> get() = _selectedTagsMap.keys.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })

    private var _recentlyAddedTags = mutableStateMapOf<String, Unit>()

    val isTagSelected = { tag: MeeTag -> tag in _selectedTagsMap }

    private var _allTags = MutableStateFlow(getAllTags())
    private val _foundTags = searchState.combine(_allTags) { query, tag ->
        if (query.isBlank()) {
            tag
        } else {
            FuzzySearchHelper.getFoundTags(query, tag)
        }
    }

    /**
     * Fuzzy search ignores those symbols in the current implementation
     */
    private fun removeSpecialSymbols(string: String): String {
        return string.lowercase().trim().replace(
            "[^a-z0-9 ]+".toRegex(),
            ""
        )
    }

    fun addTag(connectionId: String, tag: String, context: Context) {
        val preparedTag = removeSpecialSymbols(tag)
        if (preparedTag.isNotBlank() && _allTags.value.none {
                it.name.equals(
                    preparedTag,
                    ignoreCase = true
                )
            }) {
            val newTag =
                meeAgentStore.createTag(connectionId, selectedTagsList.map { it.id }, preparedTag)
            if (newTag == null) {
                showConsentToast(context, R.string.tag_add_failed)
            } else {
                _searchState.value = ""
                _recentlyAddedTags[newTag.name] = Unit
                _selectedTagsMap[newTag] = Unit
                _allTags.value = _allTags.value.toMutableList().apply { add(newTag) }
            }
        }
    }

    fun addTagsToSelected(tags: List<MeeTag>) {
        _selectedTagsMap.putAll(tags.map { it to Unit })
    }

    val isShowAddTagElement = searchState.combine(_allTags) { query, tags ->
        val preparedNewTag = removeSpecialSymbols(query)
        preparedNewTag.isNotBlank() &&
                tags.none { tag -> preparedNewTag.equals(tag.name, ignoreCase = true) }
    }

    val isShowEntireList = searchState.combine(_foundTags) { query, tag ->
        query.isNotBlank() && tag.isEmpty() && _allTags.value.isNotEmpty()
    }

    val isQueryEmpty = _searchState.map { value ->
        removeSpecialSymbols(value).isBlank()
    }

    fun getTagsFlow(): Flow<List<MeeTag>> {
        return _foundTags.map { tags ->
            tags.sortedWith(getComparator())
        }
    }

    fun getAllTagsFlow(): Flow<List<MeeTag>> {
        return _allTags.map { tags ->
            tags.sortedWith(getComparator())
        }
    }

    private fun getAllTags(): List<MeeTag> {
        return meeAgentStore.getAllTags() ?: listOf()
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
        _recentlyAddedTags.clear()
    }

    fun updateTag(connId: String, tag: MeeTag) {
        if (tag in _selectedTagsMap) {
            _selectedTagsMap.remove(tag)
        } else {
            _selectedTagsMap[tag] = Unit
        }
        meeAgentStore.updateTags(connId, selectedTagsList.map { it.id })
    }

    private fun getComparator(
    ): Comparator<MeeTag> {
        return Comparator { t1: MeeTag, t2: MeeTag ->
            val rec1 = t1.name in _recentlyAddedTags
            val rec2 = t2.name in _recentlyAddedTags
            if (rec1 || rec2) {
                if (rec1 == rec2) {
                    return@Comparator t1.name.compareTo(t2.name, ignoreCase = true)
                } else {
                    return@Comparator if (rec2) 1 else -1
                }
            } else {
                return@Comparator t1.name.compareTo(t2.name, ignoreCase = true)
            }
        }
    }
}