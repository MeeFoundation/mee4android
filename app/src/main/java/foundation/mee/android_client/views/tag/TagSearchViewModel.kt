package foundation.mee.android_client.views.tag

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.utils.FuzzySearchHelper
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

    private val _searchMenu = MutableStateFlow(false)
    val searchMenu = _searchMenu.asStateFlow()

    private var _selectedTagsMap = mutableStateMapOf<String, Unit>()
    val selectedTagsList: List<String> get() = _selectedTagsMap.keys.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it })

    private var _recentlyAddedTags = mutableStateMapOf<String, Unit>()

    val isTagSelected = { tag: String -> tag in _selectedTagsMap }

    private var _allTags = MutableStateFlow(getAllTags())
    private val _foundTags = searchState.combine(_allTags) { query, tag ->
        if (query.isBlank()) {
            tag
        } else {
            FuzzySearchHelper.getFoundTags(query, tag)
        }
    }

    fun addTag(tag: String) {
        if (tag.isNotBlank()) {
            _searchState.value = ""
            _selectedTagsMap[tag] = Unit
            _recentlyAddedTags[tag] = Unit
            _allTags.value = _allTags.value.toMutableList().apply { add(tag) }
        }
    }

    val isShowAddTagElement = searchState.combine(_allTags) { query, tag ->
        query.isNotBlank() && !tag.any { x -> query.equals(x, ignoreCase = true) }
    }

    val isShowEntireList = searchState.combine(_foundTags) { query, tag ->
        query.isNotBlank() && tag.isEmpty() && _allTags.value.isNotEmpty()
    }

    val isQueryEmpty = _searchState.map { value ->
        value.isBlank()
    }

    fun getTagsFlow(): Flow<List<String>> {
        return _foundTags.map { tags ->
            tags.sortedWith(getComparator())
        }
    }

    fun getAllTagsFlow(): Flow<List<String>> {
        return _allTags.map { tags ->
            tags.sortedWith(getComparator())
        }
    }

    private fun getAllTags(): List<String> {
        return meeAgentStore.getAllConnectionsTags()
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

    fun updateTag(tag: String) {
        if (tag in _selectedTagsMap) {
            _selectedTagsMap.remove(tag)
        } else {
            _selectedTagsMap[tag] = Unit
        }
    }

    private fun getComparator(
    ): Comparator<String> {
        return Comparator { c1: String, c2: String ->
            val rec1 = c1 in _recentlyAddedTags
            val rec2 = c2 in _recentlyAddedTags
            if (rec1 || rec2) {
                if (rec1 == rec2) {
                    return@Comparator c1.compareTo(c2, ignoreCase = true)
                } else {
                    return@Comparator if (rec2) 1 else -1
                }
            } else {
                return@Comparator c1.compareTo(c2, ignoreCase = true)
            }
        }
    }
}