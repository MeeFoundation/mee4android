package foundation.mee.android_client.views.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.DarkText
import foundation.mee.android_client.ui.theme.InactiveBorder
import foundation.mee.android_client.ui.theme.publicSansFamily
import foundation.mee.android_client.views.search.SearchViewModel

@Composable
fun SelectedTagsBox(
    searchViewModel: SearchViewModel = hiltViewModel(),
    tagSearchViewModel: TagSearchViewModelImpl = hiltViewModel()
) {
    val selectedTagList by remember {
        mutableStateOf(searchViewModel.selectedTagList)
    }

    val textFieldValue by tagSearchViewModel.searchState.collectAsState()

    val isShowSearch by tagSearchViewModel.searchMenu.collectAsState()

    Box(modifier = Modifier.padding(top = 8.dp)) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp)
                .zIndex(0.5f)
        ) {
            Text(
                text = stringResource(R.string.tags_title),
                fontFamily = publicSansFamily,
                color = DarkText,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight(400),
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(3.dp))
                    .padding(horizontal = 4.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 0.dp, top = 8.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(
                    1.dp,
                    InactiveBorder,
                    RoundedCornerShape(4.dp)
                )
                .clickableWithoutRipple {
                    if (selectedTagList.isEmpty()) {
                        tagSearchViewModel.showSearchMenu()
                    }
                }

        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .sizeIn(minHeight = 54.dp)
                    .padding(start = 16.dp)
                    .padding(vertical = 12.dp)
                    .fillMaxWidth()
            ) {
                RemovableTagRow(
                    tags = selectedTagList,
                    modifier = Modifier.weight(1f)
                ) { index, tag ->
                    tagSearchViewModel.updateTag(tag)
                    searchViewModel.unselectTag(index)
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_glass),
                    contentDescription = null,
                    tint = DarkText,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(width = 18.dp, height = 18.dp)
                        .clickableWithoutRipple {
                            tagSearchViewModel.showSearchMenu()
                        }
                )
            }
        }
    }

    if (isShowSearch) {
        TagSearchDialog {
            TagSearchScreenHeader(onClickBack = {
                tagSearchViewModel.hideSearchMenu()
                tagSearchViewModel.onCancelClick()
            }, textFieldValue = textFieldValue) {
                tagSearchViewModel.onChange(it)
            }
            ConnectionScreenTagSearch(tagSearchViewModel = tagSearchViewModel) {
                searchViewModel.updateTag(it)
            }
        }
    }
}

@Preview
@Composable
fun SelectedTagsBoxPreview() {
    SelectedTagsBox()
}