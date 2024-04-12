package foundation.mee.android_client.views.tag

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import dagger.hilt.android.lifecycle.HiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.models.MeeTag
import foundation.mee.android_client.utils.showConsentToast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TagSearchAndCreateViewModel @Inject constructor(
    context: Application,
    meeAgentStore: MeeAgentStore
) : TagSearchViewModel(context, meeAgentStore) {

    val newTagText = _searchState.map {
        removeSpecialSymbols(it)
    }

    private var _recentlyAddedTags = mutableStateMapOf<String, Unit>()

    val selectedTagsList: List<MeeTag> get() = _selectedTagsMap.keys.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })

    fun addTagToConnection(connectionId: String, tag: String, context: Context) {
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

    val isShowAddTagElement = searchState.combine(_allTags) { query, tags ->
        val preparedNewTag = removeSpecialSymbols(query)
        preparedNewTag.isNotBlank() &&
                tags.none { tag -> preparedNewTag.equals(tag.name, ignoreCase = true) }
    }

    val isShowEntireList = searchState.combine(_foundTags) { query, tag ->
        query.isNotBlank() && tag.isEmpty() && _allTags.value.isNotEmpty()
    }


    override fun getTagsFlow(): Flow<List<MeeTag>> {
        return _foundTags.map { tags ->
            tags.sortedWith(getComparator())
        }
    }

    override fun getAllTagsFlow(): Flow<List<MeeTag>> {
        return _allTags.map { tags ->
            tags.sortedWith(getComparator())
        }
    }

    override fun getAllTags() = meeAgentStore.getAllTags() ?: listOf()

    fun updateConnectionTag(connId: String) {
        meeAgentStore.updateTags(connId, selectedTagsList.map { it.id })
    }

    private fun getComparator(): Comparator<MeeTag> {
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