package foundation.mee.android_client

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun ConnectionsScreen() {
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
                modifier = Modifier.padding(padding)
            )

        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
fun PreviewConnectionScreen() {
    MeeIdentityAgentTheme {
        ConnectionsScreen()
    }
}
