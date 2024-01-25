package foundation.mee.android_client.views.manage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.models.ManageConnectionData
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.views.connections.PartnerEntry
import foundation.mee.android_client.views.connections.WarningPopup

@Composable
fun ManageConnectionContent(
    modifier: Modifier = Modifier,
    manageConnectionData: ManageConnectionData,
    onRemoveConnection: (String) -> Unit,
) {
    var showRemoveConnectionWarning by remember {
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
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp, top = 10.dp)
        ) {
            PartnerEntry(
                connection = manageConnectionData.meeConnection,
                modifier = Modifier
                    .padding(bottom = 48.dp, top = 16.dp)
                    .border(border = BorderStroke(2.dp, MeeGreenPrimaryColor))
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp)
            ) {
                itemsIndexed(items = entries) { index, item ->
                    when (item.consentEntriesType) {
                        is ConsentEntriesType.SiopClaims -> {
                            Button(onClick = {
                                visibleEntryIndex = index
                            }) {
                                Text(
                                    text = "User Profile",
                                    color = if (index == visibleEntryIndex) MeeGreenPrimaryColor else Color.Gray
                                )
                            }
                        }

                        is ConsentEntriesType.GapiEntries -> {
                            Button(onClick = {
                                visibleEntryIndex = index
                            }) {
                                Text(
                                    text = "Google account",
                                    color = if (index == visibleEntryIndex) MeeGreenPrimaryColor else Color.Gray
                                )
                            }
                        }
                    }
                }
            }

            ManageConnectorContent(entries = manageConnectionData.connectorToEntries[visibleEntryIndex].consentEntriesType)
        }
        Row(
            Modifier
                .padding(bottom = 26.dp)
                .clickableWithoutRipple
                { showRemoveConnectionWarning = true }
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
                    text = stringResource(R.string.manage_connection_delete_button),
                    fontFamily = publicSansFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = WarningRed,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )

            }
        }

    }
    if (showRemoveConnectionWarning)
        WarningPopup(
            icon = R.drawable.exclamation_mark,
            title = R.string.connection_delete_connection_title,
            messageText = R.string.connection_delete_connection_message,
            buttonText = R.string.delete_popup_button_text,
            additionalButtonText = R.string.negative_button_text,
            onAdditionalButtonClick = { showRemoveConnectionWarning = false }
        ) {
            showRemoveConnectionWarning = false
            onRemoveConnection(entries[visibleEntryIndex].meeConnector.id)
        }

}