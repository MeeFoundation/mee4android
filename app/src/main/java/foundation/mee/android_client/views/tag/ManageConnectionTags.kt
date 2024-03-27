package foundation.mee.android_client.views.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.Expander

@Composable
fun ManageConnectionTags(
    connectionId: String,
    modifier: Modifier = Modifier,
    tagsSearchViewModel: TagSearchViewModel = hiltViewModel(),
) {

    var isExpanded by remember {
        mutableStateOf(true)
    }

    val isShowSearch by tagsSearchViewModel.searchMenu.collectAsState()

    Expander(
        title = stringResource(R.string.tags_title),
        modifier = modifier,
        isExpanded = isExpanded,
        onChangeExpanded = {
            isExpanded = !isExpanded
        }
    ) {
        Column(
            modifier = Modifier.padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AddTagButton {
                tagsSearchViewModel.showSearchMenu()
            }
            RemovableTagRow(tags = tagsSearchViewModel.selectedTagsList) {
                tagsSearchViewModel.updateTag(connectionId, it)
            }
        }
    }
    if (isShowSearch) {
        TagSearchDialog(connectionId = connectionId) {
            tagsSearchViewModel.hideSearchMenu()
            tagsSearchViewModel.onCancelClick()
        }
    }
}


@Preview
@Composable
fun ManageConnectionTagsPreview() {
    ManageConnectionTags("")
}
