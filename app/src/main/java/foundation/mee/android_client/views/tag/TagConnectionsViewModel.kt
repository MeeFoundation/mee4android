package foundation.mee.android_client.views.tag

import androidx.compose.runtime.mutableStateListOf
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

    private var _selectedTagList = mutableStateListOf<String>()
    val selectedTagList: List<String> = _selectedTagList

    init {
        viewModelScope.launch {
            with(_tagList) {
                addAll(meeAgentStore.getAllConnectionsTags())
                sort()
            }
        }
    }

    fun selectTag(index: Int, tag: String) {
        with(_selectedTagList) {
            add(tag)
            sort()
        }
        with(_tagList) {
            removeAt(index)
            sort()
        }
    }

    fun unselectTag(index: Int, tag: String) {
        with(_selectedTagList) {
            removeAt(index)
            sort()
        }
        with(_tagList) {
            add(tag)
            sort()
        }
    }
}