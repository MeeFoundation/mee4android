package foundation.mee.android_client.views.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
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
import foundation.mee.android_client.ui.theme.Border
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
        modifier = modifier.padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TagTitle(
            text = stringResource(id = R.string.filter_by_tag)
        )
        if (tagList.isNotEmpty()) {
            SelectableTagRow(
                tags = tagList,
                isTagSelected = false,
            ) { index, tag ->
                searchViewModel.selectTag(index, tag)
            }
        }
        if (selectedTagList.isNotEmpty() && tagList.isNotEmpty()) {
            Divider(
                color = Border,
                thickness = 1.dp
            )
        }
        if (selectedTagList.isNotEmpty()) {
            SelectableTagRow(
                tags = selectedTagList,
                isTagSelected = true,
            ) { index, tag ->
                searchViewModel.unselectTag(index, tag)
            }
        }
    }
}

@Preview
@Composable
fun TagConnectionsScreenBarPreview() {
    TagConnectionsScreenBar()
}