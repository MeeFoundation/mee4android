package foundation.mee.android_client.views.tag

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.Expander

// TODO remove after refactoring
@SuppressLint("UnrememberedMutableState")
@Composable
fun ManageConnectionTags(
    modifier: Modifier = Modifier
) {

    var isExpanded by remember {
        mutableStateOf(true)
    }

    // TODO mock data
    var tagList by remember {
        mutableStateOf(listOf("Tag 1", "Tag 2", "Tag 3"))
    }


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

            }
            RemovableTagRow(tags = tagList) {
                tagList = tagList.toMutableList().apply {
                    removeAt(it)
                }
            }
        }
    }
}


@Preview
@Composable
fun ManageConnectionTagsPreview() {
    ManageConnectionTags()
}
