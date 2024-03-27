package foundation.mee.android_client.views.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.views.search.SearchViewModel

@Composable
fun TagConnectionsScreenBar(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()

) {
    val tagList by remember {
        mutableStateOf(searchViewModel.tagList)
    }

    val selectedTagList by remember {
        mutableStateOf(searchViewModel.selectedTagList)
    }

    Column(
        modifier = modifier.padding(top = 18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (selectedTagList.isNotEmpty()) {
            TagTitle(
                text = stringResource(id = R.string.filter_by_tag)
            )
            SelectableTagRow(
                tags = selectedTagList,
                isTagSelected = true,
            ) { index, tag ->
                searchViewModel.unselectTag(index, tag)
            }
        }
        if (tagList.isNotEmpty()) {
            TagTitle(
                text = stringResource(id = R.string.connections_existing_tags_header)
            )
            SelectableTagRow(
                tags = tagList,
                isTagSelected = false,
            ) { index, tag ->
                searchViewModel.selectTag(index, tag)
            }
        }
    }
}

@Preview
@Composable
fun TagConnectionsScreenBarPreview() {
    TagConnectionsScreenBar()
}