package foundation.mee.android_client.views.connections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import foundation.mee.android_client.ui.theme.DefaultGray200
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.TextActive
import foundation.mee.android_client.ui.theme.publicSansFamily
import foundation.mee.android_client.views.search.SearchViewModel


@Composable
fun ConnectionsContent(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
    navigator: Navigator = hiltViewModel<NavViewModel>().navigator
) {
    val connectionsWeb by searchViewModel.getWebConnectors().collectAsState(listOf())
    val connectionsMobile by searchViewModel.getMobileConnectors().collectAsState(listOf())
    val searchMenu by searchViewModel.searchMenu.collectAsState()

    if (connectionsWeb.isEmpty() && connectionsMobile.isEmpty() && searchMenu) {
        Column() {
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

    }
    LazyColumn(
        modifier = modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (connectionsWeb.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.connections_list_sites_title),
                    style = MaterialTheme.typography.h6,
                    modifier = modifier.padding(horizontal = 4.dp)
                )
            }
            items(items = connectionsWeb,
                key = { connection ->
                    connection.name
                }) { connection ->
                PartnerEntry(
                    hasEntry = true,
                    connection = connection,
                    modifier = Modifier.clickableWithoutRipple {
                        navigator.navigateToManageScreen(connection.otherPartyConnectionId)
                    }
                )
            }
        }
        if (connectionsMobile.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.connections_list_sites_mobile_apps),
                    style = MaterialTheme.typography.h6,
                    modifier = modifier
                        .padding(
                            horizontal = 4.dp
                        )
                        .padding(top = 24.dp)
                )
            }
            items(items = connectionsMobile,
                key = { connection ->
                    connection.name
                }) { connection ->
                PartnerEntry(
                    hasEntry = true,
                    connection = connection,
                    modifier = Modifier.clickableWithoutRipple {
                        navigator.navigateToManageScreen(connection.otherPartyConnectionId)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 800)
@Composable
fun ConnectionsContentPreview() {
    MeeIdentityAgentTheme {
        ConnectionsContent()
    }
}
