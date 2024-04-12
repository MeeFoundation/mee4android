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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeTag
import foundation.mee.android_client.ui.theme.LightSurface
import foundation.mee.android_client.ui.theme.TextActive
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun ConnectionScreenTagSearch(
    modifier: Modifier = Modifier,
    tagSearchViewModel: TagSearchViewModel,
    onClickFoundTag: (MeeTag) -> Unit
) {
    val foundTags by tagSearchViewModel.getTagsFlow().collectAsState(listOf())
    val allTags by tagSearchViewModel.getAllTagsFlow().collectAsState(listOf())
    val isQueryEmpty by tagSearchViewModel.isQueryEmpty.collectAsState(true)
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(state = scrollState)
    ) {
        if (foundTags.isEmpty()) {
            if (isQueryEmpty) {
                FoundTagsResult(
                    true,
                    allTags,
                    { tagSearchViewModel.isTagSelected(it) },
                    {
                        tagSearchViewModel.updateTag(it)
                        onClickFoundTag(it)
                    }
                )
            } else {
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
            }
        } else {
            FoundTagsResult(isQueryEmpty, foundTags, { tagSearchViewModel.isTagSelected(it) },
                {
                    tagSearchViewModel.updateTag(it)
                    onClickFoundTag(it)
                })
        }
    }
}