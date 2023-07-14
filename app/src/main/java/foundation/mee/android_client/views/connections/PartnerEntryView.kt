package foundation.mee.android_client.views.connections

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import foundation.mee.android_client.MeeAgentViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.models.*
import foundation.mee.android_client.utils.getURLFromString
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.ui.theme.ChevronRightIconColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.navigation.MeeDestinations.*
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.utils.getHostname
import foundation.mee.android_client.utils.linkToWebpage

@Composable
fun PartnerEntry(
    connection: MeeConnection,
    modifier: Modifier = Modifier,
    hasEntry: Boolean = false,
    viewModel: NavViewModel = hiltViewModel(),
    agentViewModel: MeeAgentViewModel = hiltViewModel(),
) {
    val isCertified = true
    val navigator = viewModel.navigator
    val clientMetadata = when (val conn = connection.value) {
        is MeeConnectionType.Siop -> conn.value.clientMetadata
        else -> null
    }
    val context = LocalContext.current

    var showCompatibleWarning by rememberSaveable {
        mutableStateOf(false)
    }

    Surface(
        modifier = modifier
            .fillMaxWidth(1f)
            .sizeIn(minHeight = 64.dp)
            .clickableWithoutRipple {
                if (hasEntry) {
                    navigator.navigate("${MANAGE.route}/${getHostname(connection.id)}")
                } else {
                    when (connection.value) {
                        is MeeConnectionType.Gapi -> showCompatibleWarning = true
                        else -> {
                            val uri = Uri.parse(connection.id)
                            linkToWebpage(context, uri)
                        }
                    }
                }
            },
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = clientMetadata?.logoUrl ?: "${connection.id}/favicon.ico"
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(width = 48.dp, height = 48.dp)
                        .clip(shape = CircleShape),
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Row {
                        Text(
                            text = clientMetadata?.name ?: connection.name,
                            style = MaterialTheme.typography.h6,
                        )
                        if (isCertified) {
                            Image(
                                painter = rememberAsyncImagePainter(R.drawable.mee_certified_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(horizontal = 5.dp)
                                    .requiredSize(20.dp)
                            )
                        }
                    }
                    Text(
                        text = getURLFromString(connection.id)?.host ?: connection.id,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            if (hasEntry) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.icon_chevron_right,
                    ),
                    contentDescription = "test",
                    tint = ChevronRightIconColor,
                    modifier = modifier
                        .padding(end = 8.dp)
                        .size(width = 9.dp, height = 16.dp)
                )
            }
        }
    }

    if (showCompatibleWarning) {
        WarningPopup(onDismiss = { showCompatibleWarning = false }) {
            showCompatibleWarning = false
            val url = agentViewModel.meeAgentStore.getGoogleIntegrationUrl()
            url?.let { linkToWebpage(context, Uri.parse(it)) }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewPrintConnectionSummary() {
    MeeIdentityAgentTheme {
        PartnerEntry(
            connection = meConnectionMock,
            hasEntry = true,
        )
    }
}
