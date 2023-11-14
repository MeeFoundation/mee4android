package foundation.mee.android_client.views.connections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.R
import foundation.mee.android_client.models.*
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme


@Composable
fun ConnectionsContent(
    modifier: Modifier = Modifier,
    connections: List<MeeConnector>,
    mobileConnections: List<MeeConnector>
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        if (connections.isNotEmpty()) {
            ConsentsList(title = stringResource(R.string.connections_list_sites_title), meeConnections = connections)
        }
        if (mobileConnections.isNotEmpty()) {
            ConsentsList(title = stringResource(R.string.connections_list_sites_mobile_apps), meeConnections = mobileConnections)
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 800)
@Composable
fun ConnectionsContentPreview() {
    MeeIdentityAgentTheme {
        ConnectionsContent(
            connections = sites,
            mobileConnections = mobileApps
        )
    }
}
