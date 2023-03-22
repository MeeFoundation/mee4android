package foundation.mee.android_client.views.connections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.models.mobileApps
import foundation.mee.android_client.models.MeeContext
import foundation.mee.android_client.models.PartnersRegistry
import foundation.mee.android_client.models.sites
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme


@Composable
fun ConnectionsContent(
    modifier: Modifier = Modifier,
    connections: List<MeeContext>,
    mobileConnections: List<MeeContext>,
    partnerConnections: List<MeeContext> = emptyList(),
    navigateToManage: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        if (connections.isNotEmpty()) {
            ConsentsList(title = "Sites", meeContexts = connections, hasEntry = true, navigateToManage = navigateToManage)
        }
        if (mobileConnections.isNotEmpty()) {
            ConsentsList(title = "Mobile Apps", meeContexts = mobileConnections, hasEntry = true, navigateToManage = navigateToManage)
        }
        if (partnerConnections.isNotEmpty()) {
            ConsentsList(title = "Other Sites You Might Like", meeContexts = partnerConnections)
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 800)
@Composable
fun ConnectionsContentPreview() {
    val partnerConnections: List<MeeContext> = PartnersRegistry.shared
    MeeIdentityAgentTheme {
        ConnectionsContent(
            connections = sites,
            mobileConnections = mobileApps,
            partnerConnections = partnerConnections
        )
    }
}
