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
import foundation.mee.android_client.models.MeeTag
import foundation.mee.android_client.ui.theme.DarkText
import foundation.mee.android_client.ui.theme.publicSansFamily

const val DEFAULT_MAX_ROW_COUNT = 2

@Composable
fun SelectableTagRow(
    tags: List<MeeTag>,
    modifier: Modifier = Modifier,
    isTagSelected: Boolean,
    onSelectTag: (Int, MeeTag) -> Unit
) {

    var trailingElementState by remember { mutableStateOf(TrailingElementState.NONE) }

    MeeFlowRow(
        spacedBy = 8.dp,
        trailingElementState = trailingElementState,
        showMore = {
            TrailingElement(text = R.string.more_tags) {
                trailingElementState = TrailingElementState.SHOW_MORE
            }
        },
        showLess = {
            TrailingElement(text = R.string.less_tags) {
                trailingElementState = TrailingElementState.SHOW_LESS
            }
        },
        maxRowCount = DEFAULT_MAX_ROW_COUNT,
        modifier = modifier
            .fillMaxWidth()
    ) {
        tags.forEachIndexed { index, it ->
            Tag(
                text = it.name,
                onClick = {
                    onSelectTag(index, it)
                },
                isRemovable = isTagSelected,
                isSelected = isTagSelected
            )
        }
    }
}

@Composable
fun RemovableTagRow(
    tags: List<MeeTag>,
    onRemoveTag: (MeeTag) -> Unit
) {
    var trailingElementState by remember { mutableStateOf(TrailingElementState.NONE) }

    MeeFlowRow(
        spacedBy = 8.dp,
        trailingElementState = trailingElementState,
        showMore = {
            TrailingElement(text = R.string.more_tags) {
                trailingElementState = TrailingElementState.SHOW_MORE
            }
        },
        showLess = {
            TrailingElement(text = R.string.less_tags) {
                trailingElementState = TrailingElementState.SHOW_LESS
            }
        },
        maxRowCount = DEFAULT_MAX_ROW_COUNT,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        tags.forEach {
            Tag(
                text = it.name,
                onClick = {
                    onRemoveTag(it)
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
        letterSpacing = 0.1.sp,
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
                "Entertainment",
                "News",
                "Other",
                "Music",
                "Cinema",
                "Art",
                "Science",
                "Tech"
            ).map { MeeTag("", it) },
            isTagSelected = true
        ) { _, _ -> }
        RemovableTagRow(
            listOf(
                "Entertainment",
                "News",
                "Other",
                "Music",
                "Cinema",
                "Art",
                "Science",
                "Tech"
            ).map { MeeTag("", it) }
        ) {}
    }
}