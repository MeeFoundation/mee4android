package foundation.mee.android_client.views.tag

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import foundation.mee.android_client.models.MeeAgentStore
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagSearchViewModelImpl @Inject constructor(
    context: Application,
    meeAgentStore: MeeAgentStore
) : TagSearchViewModel(context, meeAgentStore) {
    fun update() {
        viewModelScope.launch {
            val allTagsWithConnections = meeAgentStore.getAllTagsWithConnectors() ?: listOf()
            _allTags.value = allTagsWithConnections
            with(_selectedTagsMap) {
                val refreshedSelected = filter { allTagsWithConnections.contains(it.key) }
                clear()
                putAll(refreshedSelected)
            }
        }
    }
}