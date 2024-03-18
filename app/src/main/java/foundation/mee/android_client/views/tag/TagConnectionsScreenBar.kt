package foundation.mee.android_client.views.tag

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

@Composable
fun TagConnectionsScreenBar(
    modifier: Modifier = Modifier,
    tagConnectionsViewModel: TagConnectionsViewModel = hiltViewModel()
) {

    val tagList by remember {
        mutableStateOf(tagConnectionsViewModel.tagList)
    }

    val isTagSelected by remember {
        mutableStateOf(tagConnectionsViewModel.isTagSelected)
    }

    Column(
        modifier = modifier
    ) {
        TagTitle(
            text = stringResource(id = R.string.filter_by_tag)
        )
        SelectableTagRow(
            tags = tagList,
            isTagSelected = isTagSelected,
            modifier = Modifier.padding(top = 8.dp),
        ) { index, tag ->
            tagConnectionsViewModel.updateTagAtIndex(index, tag)
        }
    }
}

@Preview
@Composable
fun TagConnectionsScreenBarPreview() {
    TagConnectionsScreenBar()
}