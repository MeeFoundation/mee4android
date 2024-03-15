package foundation.mee.android_client.views.tag

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.DarkText
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun SelectableTagRow(
    tags: List<String>,
    modifier: Modifier = Modifier,
    isTagSelected: (String) -> Boolean,
    onSelectTag: (Int, String) -> Unit
) {
    var rowCount by remember { mutableStateOf<MeeFlowMaxRowCount?>(MeeFlowMaxRowCount.DEFAULT) }

    MeeFlowRow(
        spacedBy = 8.dp,
        showMore = {
            TrailingElement(text = R.string.more_tags) {
                rowCount = MeeFlowMaxRowCount.MAX
            }
        },
        showLess = {
            TrailingElement(text = R.string.less_tags) {
                rowCount = MeeFlowMaxRowCount.DEFAULT
            }
        },
        maxRowCount = rowCount,
        modifier = modifier
            .fillMaxWidth()
    ) {
        tags.forEachIndexed { index, it ->
            Tag(
                text = it,
                onClick = {
                    onSelectTag(index, it)
                },
                isRemovable = false,
                isSelected = isTagSelected(it)
            )
        }
    }
}

@Composable
fun RemovableTagRow(
    tags: List<String>,
    onRemoveTag: (Int) -> Unit
) {
    var rowCount by remember { mutableStateOf<MeeFlowMaxRowCount?>(MeeFlowMaxRowCount.DEFAULT) }

    MeeFlowRow(
        spacedBy = 8.dp,
        showMore = {
            TrailingElement(text = R.string.more_tags) {
                rowCount = MeeFlowMaxRowCount.MAX
            }
        },
        showLess = {
            TrailingElement(text = R.string.less_tags) {
                rowCount = MeeFlowMaxRowCount.DEFAULT
            }
        },
        maxRowCount = MeeFlowMaxRowCount.DEFAULT,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        tags.forEachIndexed { index, it ->
            Tag(
                text = it,
                onClick = {
                    onRemoveTag(index)
                },
                isRemovable = true,
            )
        }
    }
}

@Composable
fun TrailingElement(
    text: Int,
    onClick: () -> Unit
) {
    Text(
        stringResource(text),
        fontFamily = publicSansFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight(500),
        textAlign = TextAlign.Center,
        lineHeight = 20.sp,
        color = DarkText,
        modifier = Modifier
            .clickable {
                onClick()
            }
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Preview
@Composable
fun TagRowPreview() {
    Column {
        SelectableTagRow(
            listOf(
                "#Entertainment",
                "#News",
                "#Other",
                "#Music",
                "#Cinema",
                "#Art",
                "#Science",
                "#Tech"
            ),
            isTagSelected = { it == "#Entertainment" }
        ) { _, _ -> }
        RemovableTagRow(
            listOf(
                "#Entertainment",
                "#News",
                "#Other",
                "#Music",
                "#Cinema",
                "#Art",
                "#Science",
                "#Tech"
            )
        ) {}
    }
}