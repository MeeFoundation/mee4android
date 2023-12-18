package foundation.mee.android_client.views.manage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
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
import foundation.mee.android_client.models.MeeConnector
import foundation.mee.android_client.ui.components.Expander
import foundation.mee.android_client.ui.components.NotificationPopup
import foundation.mee.android_client.ui.components.advancedShadow
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.utils.goToSystemSettings
import foundation.mee.android_client.views.connections.PartnerEntry
import foundation.mee.android_client.views.connections.WarningPopup
import foundation.mee.android_client.views.consent.ConsentEntry
import foundation.mee.android_client.views.consent.ExternalConsentEntry
import uniffi.mee_agent.GapiUserInfo
import uniffi.mee_agent.OtherPartyContextDataUniffi
import kotlin.reflect.full.memberProperties

@Composable
fun ManageConnectionContent(
    modifier: Modifier = Modifier,
    consentEntriesType: ConsentEntriesType,
    meeConnection: MeeConnector,
    onRemoveConnection: (String) -> Unit
) {
    var showRemoveConnectionWarning by remember {
        mutableStateOf(false)
    }
    val state = rememberManageConnectionContentState(consentEntries = consentEntriesType)
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
            connection = meeConnection,
            modifier = Modifier
                .padding(bottom = 48.dp, top = 16.dp)
                .border(border = BorderStroke(2.dp, MeeGreenPrimaryColor))
        )
        when (consentEntriesType) {
            is ConsentEntriesType.SiopClaims -> {
                Expander(
                    title = stringResource(R.string.manage_connection_required_expander),
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp),
                    isExpanded = state.isRequiredSectionOpened,
                    onChangeExpanded = {
                        state.isRequiredSectionOpened = !state.isRequiredSectionOpened
                    }
                ) {
                    state.requiredClaims?.forEach {
                        ConsentEntry(
                            it,
                            isReadOnly = true,
                            modifier = Modifier.padding(top = 16.dp, start = 3.dp)
                        )
                    }
                }

                if (state.hasOptionalFields) {
                    Expander(
                        title = stringResource(R.string.manage_connection_optional_expander),
                        modifier = Modifier.padding(top = 16.dp),
                        color = Color.White,
                        isExpanded = state.isOptionalSectionOpened,
                        onChangeExpanded = {
                            state.isOptionalSectionOpened = !state.isOptionalSectionOpened
                        }
                    ) {
                        state.optionalClaims?.forEach {
                            ConsentEntry(
                                it,
                                isReadOnly = true,
                                modifier = Modifier.padding(top = 16.dp, start = 3.dp)
                            )
                        }
                    }
                }
            }
            is ConsentEntriesType.GapiEntries -> {
                val entries: List<Pair<String, String>> =
                    when (val data = consentEntriesType.value.data) {
                        is OtherPartyContextDataUniffi.Gapi -> {
                            val copy = arrayListOf<Pair<String, String>>()
                            for (prop in GapiUserInfo::class.memberProperties) {
                                when (prop.name) {
                                    "familyName", "givenName", "email" -> copy.add(
                                        Pair(
                                            prop.name,
                                            prop.get(data.value.userInfo).toString()
                                        )
                                    )
                                }
                            }
                            copy
                        }
                        else -> listOf()
                    }
                entries.forEach { ExternalConsentEntry(it.first, it.second) }
            }
        }

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
            onRemoveConnection(meeConnection.otherPartyConnectionId)
        }

}