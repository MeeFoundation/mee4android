package foundation.mee.android_client.views.connections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.NoRippleInteractionSource

@Composable
fun ConnectionsScreen(
    onClickMenu: () -> Unit
) {

    val connectionsState = rememberConnectionsState()
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(topBar = {
        ConnectionsScreenTitle { onClickMenu() }
    }) { padding ->
        Box {
            ConnectionsContent(
                modifier = Modifier.padding(padding),
            )
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(
                    onClick = { openDialog.value = true },
                    interactionSource = NoRippleInteractionSource()
                ) {
                    Image(
                        painter = painterResource(R.drawable.connection_add),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(78.dp)
                            .width(78.dp)
                            .padding(end = 16.dp, bottom = 16.dp)
                    )
                }
            }
        }
    }
    if (openDialog.value) {
        SitesToConnectDialog(
            partners = connectionsState.otherPartnersWebApp,
        ) {
            openDialog.value = false
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812) // Figma design dimensions
@Composable
fun PreviewConnectionScreen() {
    MeeIdentityAgentTheme {
        ConnectionsScreen {}
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConnectionScreenDynamic() {
    MeeIdentityAgentTheme {
        ConnectionsScreen {}
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConnectionScreenAnother() {
    MeeIdentityAgentTheme {
        ConnectionsScreen {}
    }
}
