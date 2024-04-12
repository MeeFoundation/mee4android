package foundation.mee.android_client.views.tag

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.Expander

@Composable
fun TagConnectionsScreenBar(
    tagsCount: Int,
    modifier: Modifier = Modifier
) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.padding(top = 16.dp),
    ) {
        Expander(
            title = {
                TagTitle(
                    tagsCount = tagsCount,
                    text = stringResource(id = R.string.filter_by_tag),
                    modifier = Modifier.weight(1f)
                )
            },
            isExpanded = isExpanded,
            onChangeExpanded = {
                isExpanded = !isExpanded
            }
        ) {
            SelectedTagsBox()
        }
    }
}

@Preview
@Composable
fun TagConnectionsScreenBarPreview() {
    TagConnectionsScreenBar(2)
}