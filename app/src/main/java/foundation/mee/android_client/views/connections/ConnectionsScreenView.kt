package foundation.mee.android_client.views.connections

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.ui.components.MeeCertifiedButton
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.utils.CERTIFIED_URL_STRING
import foundation.mee.android_client.views.consent.WebViewComposable

@Composable
fun ConnectionsScreen() {

    val connectionsState = rememberConnectionsState()

    var showCertified by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(topBar = {
        ConnectionsScreenTitle()
    }, bottomBar = {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            MeeCertifiedButton {
                showCertified = !showCertified
            }

        }
    }) { padding ->
        ConnectionsContent(
            connections = connectionsState.existingPartnersWebApp,
            mobileConnections = connectionsState.existingPartnersMobileApp,
            partnerConnections = connectionsState.otherPartnersWebApp,
            modifier = Modifier.padding(padding),
        )
    }

    if (showCertified) {
        WebViewComposable(CERTIFIED_URL_STRING) {
            showCertified = !showCertified
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

@Preview(showBackground = true)
@Composable
fun PreviewConnectionScreenAnother() {
    MeeIdentityAgentTheme {
        ConnectionsScreen()
    }
}
