package foundation.mee.android_client

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme

@Composable
fun ConnectionsScreen() {
    MeeIdentityAgentTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .fillMaxWidth(),
//                        .paddingFromBaseline(bottom = 10.dp)
//                        .padding(16.dp)
//                    contentPadding = PaddingValues(bottom = 10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            // TODO: extract to string resource???
                            text = "Connections",
                            color = MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.paddingFromBaseline(bottom = 10.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }


                }
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
