package foundation.mee.android_client.views.connections

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import foundation.mee.android_client.ui.theme.*

@Composable
fun ConnectionsScreen() {

    val connectionsState = rememberConnectionsState()

    Scaffold(topBar = {
        ConnectionsScreenTitle()
    }) { padding ->
        ConnectionsContent(
            connections = connectionsState.existingPartnersWebApp,
            mobileConnections = connectionsState.existingPartnersMobileApp,
            partnerConnections = connectionsState.otherPartnersWebApp,
            modifier = Modifier.padding(padding),
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812) // Figma design dimensions
@Composable
fun PreviewConnectionScreen() {
    MeeIdentityAgentTheme {
        ConnectionsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConnectionScreenDynamic() {
    MeeIdentityAgentTheme {
        ConnectionsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConnectionScreenAnother() {
    MeeIdentityAgentTheme {
        ConnectionsScreen()
    }
}
