package foundation.mee.android_client.views.connections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.models.MeeConnection
import foundation.mee.android_client.models.mobileApps
import foundation.mee.android_client.models.PartnersRegistry
import foundation.mee.android_client.models.sites
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme


@Composable
fun ConnectionsContent(
    modifier: Modifier = Modifier,
    connections: List<MeeConnection>,
    mobileConnections: List<MeeConnection>,
    partnerConnections: List<MeeConnection> = emptyList()
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        if (connections.isNotEmpty()) {
            ConsentsList(title = "Sites", meeConnections = connections, hasEntry = true)
        }
        if (mobileConnections.isNotEmpty()) {
            ConsentsList(title = "Mobile Apps", meeConnections = mobileConnections, hasEntry = true)
        }
        if (partnerConnections.isNotEmpty()) {
            ConsentsList(title = "Other Sites You Might Like", meeConnections = partnerConnections)
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 800)
@Composable
fun ConnectionsContentPreview() {
    val partnerConnections: List<MeeConnection> = PartnersRegistry.shared
    MeeIdentityAgentTheme {
        ConnectionsContent(
            connections = sites,
            mobileConnections = mobileApps,
            partnerConnections = partnerConnections
        )
    }
}
