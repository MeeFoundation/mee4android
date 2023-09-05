package foundation.mee.android_client.views.connections

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.MeeAgentViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.models.*
import foundation.mee.android_client.navigation.MeeDestinations
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.navigation.Navigator
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.utils.linkToWebpage

@Composable
fun ConsentsList(
    modifier: Modifier = Modifier,
    title: String,
    meeConnections: List<MeeConnector> = emptyList(),
    navigator: Navigator = hiltViewModel<NavViewModel>().navigator,
    meeAgentStore: MeeAgentStore = hiltViewModel<MeeAgentViewModel>().meeAgentStore,
    hasEntry: Boolean = false
) {

    val context = LocalContext.current

    var showCompatibleWarning by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = modifier.padding(horizontal = 4.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items = meeConnections) { connection ->
                PartnerEntry(
                    connection = connection,
                    hasEntry = hasEntry,
                    modifier = Modifier.clickableWithoutRipple {
                        if (hasEntry) {
                            navigator.navigate(
                                "${MeeDestinations.MANAGE.route}/${
                                    connection.otherPartyConnectionId
                                }"
                            )
                        } else {
                            when (connection.value) {
                                is MeeConnectorType.Gapi -> showCompatibleWarning = true
                                else -> {
                                    val uri = Uri.parse(connection.id)
                                    linkToWebpage(context, uri)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
    if (showCompatibleWarning) {
        WarningPopup(
            icon = R.drawable.ic_google,
            iconSize = 60.dp,
            messageText = R.string.warning_popup_message_text,
            onDismiss = { showCompatibleWarning = false }) {
            showCompatibleWarning = false
            val url = meeAgentStore.getGoogleIntegrationUrl()
            url?.let { linkToWebpage(context, Uri.parse(it)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConsentsListPreview() {
    MeeIdentityAgentTheme {
        ConsentsList(
            title = stringResource(R.string.connections_list_sites_mobile_apps),
            meeConnections = mobileApps
        )
    }
}
