package foundation.mee.android_client.views.tag

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import foundation.mee.android_client.models.MeeAgentStore
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagConnectionsViewModel @Inject constructor(
    private val meeAgentStore: MeeAgentStore
) : ViewModel() {

    private var _tagList = mutableStateListOf<String>()
    val tagList: List<String> = _tagList

    private var _selectedTagsMap = mutableStateMapOf<String, Int>()
    val isTagSelected = { tag: String -> tag in _selectedTagsMap }

    init {
        viewModelScope.launch {
            _tagList.addAll(meeAgentStore.getAllConnectionsTags())
        }
    }

    fun updateTagAtIndex(index: Int, tag: String) {
        if (tag in _selectedTagsMap) {
            _selectedTagsMap.remove(tag)
            _tagList.apply {
                removeAt(index)
                add(tag)
            }
        } else {
            _selectedTagsMap[tag] = index
            _tagList.apply {
                removeAt(index)
                add(0, tag)
            }
        }
    }
}