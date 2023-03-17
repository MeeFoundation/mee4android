package foundation.mee.android_client

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import uniffi.mee_agent.OidcClientMetadata


@Composable
fun ConnectionsContent(
    modifier: Modifier = Modifier,
//    connectionsComposable: @Composable () -> Unit,
    connections: List<Context>,
//    connectionsComposable: @Composable () -> Unit,
    mobileConnections: List<Context>,
//    connectionsComposable: @Composable () -> Unit,
    partnerConnections: List<Context> = emptyList()
) {
    Column(
        modifier = modifier
//            .fillMaxSize(),
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        if (connections.isNotEmpty()) {
            ConsentsList(title = "Sites", contexts = connections, hasEntry = true)
        }
        if (mobileConnections.isNotEmpty()) {
            ConsentsList(title = "Mobile Apps", contexts = mobileConnections, hasEntry = true)
        }
        if (partnerConnections.isNotEmpty()) {
            ConsentsList(title = "Other Sites You Might Like", contexts = partnerConnections)
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 800)
@Composable
fun ConnectionsPreview() {

    val partnerConnections: List<Context> = PartnersRegistry.shared
//    var agent: MeeAgent = getAgent(
//        MeeAgentConfig(
//            "/Users/vamuzing/projects/Mee/mee-core/target/test_create_delete_ctx.sqlite",
//            null,
//            MeeAgentDidRegistryConfig.DidKey
//        )
//    )
//
//    var conns: List<MaterializedContext> = agent.listMaterializedContexts()
    MeeIdentityAgentTheme {
        ConnectionsContent(
            connections = sites,
            mobileConnections = mobileApps,
            partnerConnections = partnerConnections
        )
    }
}
