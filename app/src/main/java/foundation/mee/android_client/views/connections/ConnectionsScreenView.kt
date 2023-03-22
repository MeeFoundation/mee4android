package foundation.mee.android_client.views.connections

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import foundation.mee.android_client.models.mobileApps
import foundation.mee.android_client.models.PartnersRegistry
import foundation.mee.android_client.models.sites
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme

@Composable
fun ConnectionsScreen(
    navigateToManage: (String) -> Unit = {}
) {
    MeeIdentityAgentTheme {
        Scaffold(
            topBar = {
                ConnectionsScreenTitle()
            }
        ) { padding ->
            ConnectionsContent(
                connections = sites,
                mobileConnections = mobileApps,
                partnerConnections = PartnersRegistry.shared,
                modifier = Modifier.padding(padding),
                navigateToManage = navigateToManage
            )

        }
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
