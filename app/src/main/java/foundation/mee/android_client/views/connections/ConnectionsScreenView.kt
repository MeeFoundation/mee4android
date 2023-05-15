package foundation.mee.android_client.views.connections

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import foundation.mee.android_client.models.*
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.utils.getHostname

@Composable
fun ConnectionsScreen() {

    val context = LocalContext.current
    val appDir = context.applicationInfo.dataDir
    val agentStore = MeeAgentStore("$appDir/mee")

    val data = agentStore.getAllItems()

    val existingPartnersWebApp =
        data?.filter { x ->
            when (val connType = x.value) {
                is MeeConnectionType.Siop -> when (connType.value.clientMetadata.type) {
                    ClientType.web -> agentStore.getLastConnectionConsentById(x.id) != null
                    else -> false
                }
                else -> false
            }
        }

    val existingPartnersMobileApp =
        data?.filter { x ->
            when (val connType = x.value) {
                is MeeConnectionType.Siop -> when (connType.value.clientMetadata.type) {
                    ClientType.mobile -> agentStore.getLastConnectionConsentById(x.id) != null
                    else -> false
                }
                else -> false
            }
        }

    val otherPartnersWebApp =
        PartnersRegistry.shared.filter { x ->
            existingPartnersWebApp?.find { getHostname(it.id) == getHostname(x.id) } == null
        }

    Scaffold(topBar = {
        ConnectionsScreenTitle()
    }) { padding ->
        ConnectionsContent(
            connections = existingPartnersWebApp ?: listOf(),
            mobileConnections = existingPartnersMobileApp ?: listOf(),
            partnerConnections = otherPartnersWebApp,
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
