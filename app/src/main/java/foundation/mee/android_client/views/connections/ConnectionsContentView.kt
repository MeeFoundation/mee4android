package foundation.mee.android_client.views.connections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.navigation.Navigator
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.Border
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.TextActive
import foundation.mee.android_client.ui.theme.publicSansFamily
import foundation.mee.android_client.views.search.SearchViewModel
import foundation.mee.android_client.views.tag.TagConnectionsScreenBar
import foundation.mee.android_client.views.tag.TagSearchViewModelImpl

@Composable
fun ConnectionsContent(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
    tagSearchViewModel: TagSearchViewModelImpl = hiltViewModel(),
    navigator: Navigator = hiltViewModel<NavViewModel>().navigator
) {
    val connections by searchViewModel.getConnectionsFlow().collectAsState(listOf())
    val searchMenu by searchViewModel.searchMenu.collectAsState()
    val scrollState = rememberScrollState()

    Column {
        if (tagSearchViewModel.showTagsPanel) {
            TagConnectionsScreenBar(
                tagsCount = searchViewModel.selectedTagList.size,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Divider(
                color = Border,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        if (connections.isEmpty() && (searchMenu || searchViewModel.selectedTagList.isNotEmpty())) {
            Column {
                Text(
                    text = stringResource(R.string.empty_search_result),
                    color = TextActive,
                    fontFamily = publicSansFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
                Divider(
                    color = Border,
                    thickness = 1.dp,
                )
            }

        } else if (connections.isNotEmpty()) {
            Column(
                modifier = modifier
                    .verticalScroll(state = scrollState)
            ) {
                Column {
                    connections.forEach { connection ->
                        PartnerEntry(
                            connection = connection,
                            modifier = Modifier.clickableWithoutRipple {
                                navigator.navigateToManageScreen(connection.id)
                            }
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        searchViewModel.update()
        tagSearchViewModel.update()
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 800)
@Composable
fun ConnectionsContentPreview() {
    MeeIdentityAgentTheme {
        ConnectionsContent()
    }
}
