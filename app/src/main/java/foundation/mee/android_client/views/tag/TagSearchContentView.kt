package foundation.mee.android_client.views.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeTag
import foundation.mee.android_client.ui.theme.Border
import foundation.mee.android_client.ui.theme.DarkText
import foundation.mee.android_client.ui.theme.LightSurface
import foundation.mee.android_client.ui.theme.TextActive
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun TagSearchContentView(
    connectionId: String,
    modifier: Modifier = Modifier,
    searchViewModel: TagSearchViewModel = hiltViewModel(),
) {
    val foundTags by searchViewModel.getTagsFlow().collectAsState(listOf())
    val allTags by searchViewModel.getAllTagsFlow().collectAsState(listOf())
    val isQueryEmpty by searchViewModel.isQueryEmpty.collectAsState(true)
    val newTagText by searchViewModel.newTagText.collectAsState("")
    val isShowAddTagElement by searchViewModel.isShowAddTagElement.collectAsState(false)
    val isShowEntireList by searchViewModel.isShowEntireList.collectAsState(false)

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .verticalScroll(state = scrollState)
    ) {
        if (foundTags.isEmpty()) {
            if (!isQueryEmpty) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .background(LightSurface)
                        .sizeIn(minHeight = 55.dp)
                        .padding(vertical = 15.5.dp)
                        .padding(start = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.empty_search_result),
                        color = TextActive,
                        fontFamily = publicSansFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
                AddTagElement(newTagText) {
                    searchViewModel.addTag(
                        connectionId,
                        newTagText,
                        context
                    )
                }
            }

            if (isShowEntireList) {
                FoundTagsResult(
                    true,
                    allTags,
                    { searchViewModel.isTagSelected(it) },
                    { searchViewModel.updateTag(connectionId, it) }
                )
            }

        } else {
            FoundTagsResult(isQueryEmpty, foundTags, { searchViewModel.isTagSelected(it) },
                { searchViewModel.updateTag(connectionId, it) })
            if (isShowAddTagElement) {
                AddTagElement(newTagText) {
                    searchViewModel.addTag(connectionId, newTagText, context)
                }
            }
        }
    }
}

@Composable
fun AddTagElement(searchState: String, onAddTag: () -> Unit) {
    Divider(
        color = Border,
        thickness = 1.dp,
    )
    TagSearchEntry(
        tag = searchState,
        isExactMatch = false,
        onClick = { onAddTag() },
    )
    Divider(
        color = Border,
        thickness = 1.dp,
    )
}


@Composable
private fun FoundTagsResult(
    isQueryEmpty: Boolean,
    foundTags: List<MeeTag>,
    isChecked: (MeeTag) -> Boolean,
    onClick: (MeeTag) -> Unit
) {
    Row(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
        Text(
            text = stringResource(if (isQueryEmpty) R.string.choose_from_existing_tags else R.string.tags_found),
            color = DarkText,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        )
    }
    Column {
        foundTags.forEach { tag ->
            TagSearchEntry(
                tag = tag.name,
                isExactMatch = true,
                isChecked = isChecked(tag),
                onClick = { onClick(tag) },
            )
        }
    }
}