package foundation.mee.android_client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import uniffi.mee_agent.*


@Preview(showBackground = true, widthDp = 375, heightDp = 800)
@Composable
fun ConnectionsPreview() {
    val connections: List<MaterializedContext.RelyingParty> = listOf(
        MaterializedContext.RelyingParty(
            "",
            "connection_name1",
            RpContextData(ContextProtocol.GoogleAccount)
        ),
        MaterializedContext.RelyingParty(
            "",
            "connection_name2",
            RpContextData(ContextProtocol.GoogleAccount)
        )
    )
    var partnerConnections: List<Context> = PartnersRegistry.shared
    var agent: MeeAgent = getAgent(
        MeeAgentConfig(
            "/Users/vamuzing/projects/Mee/mee-core/target/test_create_delete_ctx.sqlite",
            null,
            MeeAgentDidRegistryConfig.DidKey
        )
    )
//
//    var conns: List<MaterializedContext> = agent.listMaterializedContexts()
    MeeIdentityAgentTheme() {
        ConnectionsScreen(connections = connections, partner_connections = partnerConnections)
    }
}

@Composable
fun ConnectionsScreen(
    modifier: Modifier = Modifier,
    connections: List<MaterializedContext>,
    partner_connections: List<MaterializedContext.RelyingParty> = listOf()
) {
    MeeIdentityAgentTheme() {
        Column(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface() {
                    Text(text = "Connections")
                }

            }
            Column() {
                Text(text = "Sites")
                LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
                    items(items = connections) { connection ->
                        when (connection) {
                            is MaterializedContext.RelyingParty -> PrintConnectionSummary(connection = connection as MaterializedContext.RelyingParty)

                            else -> {}
                        }

                    }
                }
            }
            Column() {
                Text(text = "Other Sites You Might Like")
                LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
                    items(items = partner_connections) { partner_connection ->
                        PrintConnectionSummary(connection = partner_connection)

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPrintConnectionSummary() {
    val connection: MaterializedContext = MaterializedContext.RelyingParty(
        "",
        "connection_name",
        RpContextData(ContextProtocol.GoogleAccount)
    )
    MeeIdentityAgentTheme() {
//        coroutineScope {
//
//        }
        PrintConnectionSummary(connection = connection as MaterializedContext.RelyingParty)
    }
}

@Composable
fun PrintConnectionSummary(
    modifier: Modifier = Modifier,
    connection: MaterializedContext.RelyingParty
) {
    Row(
        horizontalArrangement = Arrangement.Center,
//    modifier = Modifier.fillMaxWidth()
    ) {
        // Add icon from metadata
        Box(
            Modifier
                .size(50.dp)
                .padding(10.dp)
                .background(Color.Cyan)
        ) {
            Text(text = "Ic")
        }
        Column(verticalArrangement = Arrangement.Center) {
            Row() {
                Text(text = connection.name)
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(5.dp)
                        .background(Color.Blue)
                )
            }
            Text(text = "connection_name.com")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "test"
            )
        }
    }
}

@Composable
fun PrintConnectionDetailed() {
    MeeIdentityAgentTheme() {

    }
}