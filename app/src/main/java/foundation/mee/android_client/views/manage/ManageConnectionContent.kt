package foundation.mee.android_client.views.manage

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.models.ManageConnectionData
import foundation.mee.android_client.models.MeeConnector
import foundation.mee.android_client.models.MeeConnectorProtocol
import foundation.mee.android_client.models.manageConnectionDataMock
import foundation.mee.android_client.ui.components.NoRippleInteractionSource
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.views.connections.ConnectionEntry
import foundation.mee.android_client.views.connections.WarningPopup


private fun getTabName(connector: MeeConnector): Int {
    return when (connector.connectorProtocol) {
        is MeeConnectorProtocol.Siop -> R.string.profile
        is MeeConnectorProtocol.Gapi -> R.string.google_account
        is MeeConnectorProtocol.MeeBrowserExtension -> R.string.extension
        is MeeConnectorProtocol.OpenId4Vc -> R.string.open_id_4_vc
        is MeeConnectorProtocol.MeeTalk -> R.string.mee_talk
    }
}

@Composable
fun ManageConnectionContent(
    modifier: Modifier = Modifier,
    manageConnectionData: ManageConnectionData,
    onRemoveConnector: (String) -> Unit,
    onRemoveConnection: (String) -> Unit
) {
    var showRemoveConnectionWarning by remember {
        mutableStateOf(false)
    }

    var showRemoveConnectorWarning by remember {
        mutableStateOf(false)
    }

    var visibleEntryIndex by remember { mutableStateOf(0) }

    val entries = manageConnectionData.connectorToEntries

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight(1f)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier.padding(top = 10.dp)
        ) {
            ConnectionEntry(
                connection = manageConnectionData.meeConnection,
                description = String.format(
                    pluralStringResource(
                        id = R.plurals.connection_entry_description,
                        count = entries.size
                    ), entries.size
                ),
                modifier = Modifier.padding(end = 10.dp),
                onDelete = { showRemoveConnectionWarning = true })
            Divider(
                color = DividerColor,
                thickness = 1.dp
            )
            if (entries.isNotEmpty()) {
                ScrollableTabRow(
                    selectedTabIndex = visibleEntryIndex,
                    backgroundColor = Color.Transparent,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            color = TabIndicator,
                            height = 3.dp,
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[visibleEntryIndex])
                        )
                    },
                    divider = {}) {
                    entries.forEachIndexed { index, item ->
                        Tab(
                            selected = index == visibleEntryIndex,
                            onClick = { visibleEntryIndex = index },
                            text = {
                                Text(
                                    text = stringResource(id = getTabName(item.meeConnector)),
                                    fontFamily = publicSansFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    letterSpacing = 0.1.sp
                                )
                            },
                            selectedContentColor = TextActive,
                            unselectedContentColor = DarkText,
                            modifier = Modifier
                                .height(48.dp),
                            interactionSource = NoRippleInteractionSource()
                        )
                    }
                }
                Divider(
                    color = DividerColor,
                    thickness = 1.dp
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                ) {
                    ManageConnectorContent(
                        entries = entries[visibleEntryIndex].consentEntriesType
                    )
                }
            }
        }
        if (entries.isNotEmpty()) {
            Row(
                Modifier
                    .padding(bottom = 26.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector =
                        ImageVector.vectorResource(
                            id = R.drawable.trash
                        ),
                        contentDescription = null,
                        tint = WarningRed,
                        modifier = Modifier
                            .width(18.dp)
                    )
                    Text(
                        text = String.format(
                            stringResource(R.string.delete_connector),
                            stringResource(getTabName(entries[visibleEntryIndex].meeConnector))
                        ),
                        fontFamily = publicSansFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = WarningRed,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickableWithoutRipple { showRemoveConnectorWarning = true }
                    )

                }
            }
        }

    }

    if (showRemoveConnectionWarning) {
        WarningPopup(
            icon = R.drawable.exclamation_mark,
            title = String.format(
                stringResource(R.string.connection_delete_connection_title),
                manageConnectionData.meeConnection.name
            ),
            messageText = R.string.connection_delete_connection_message,
            buttonText = R.string.delete_popup_button_text,
            additionalButtonText = R.string.negative_button_text,
            onAdditionalButtonClick = { showRemoveConnectionWarning = false }
        ) {
            showRemoveConnectionWarning = false
            onRemoveConnection(manageConnectionData.meeConnection.id)
        }
    }

    if (showRemoveConnectorWarning) {
        WarningPopup(
            icon = R.drawable.exclamation_mark,
            title = String.format(
                stringResource(R.string.delete_data_group_title),
                stringResource(getTabName(entries[visibleEntryIndex].meeConnector)),
                manageConnectionData.meeConnection.name
            ),
            messageText = R.string.delete_data_group_message,
            buttonText = R.string.delete_popup_button_text,
            additionalButtonText = R.string.negative_button_text,
            onAdditionalButtonClick = { showRemoveConnectorWarning = false }
        ) {
            showRemoveConnectorWarning = false
            onRemoveConnector(entries[visibleEntryIndex].meeConnector.id)
            visibleEntryIndex = maxOf(0, visibleEntryIndex - 1)
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewManageConnectionContent() {
    MeeIdentityAgentTheme {
        ManageConnectionContent(
            manageConnectionData = manageConnectionDataMock,
            onRemoveConnector = {}
        ) {}
    }
}