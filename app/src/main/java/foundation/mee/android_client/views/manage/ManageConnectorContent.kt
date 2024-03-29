package foundation.mee.android_client.views.manage

import android.view.Gravity
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import foundation.mee.android_client.R
import foundation.mee.android_client.models.manageConnectionDataMock
import foundation.mee.android_client.ui.components.Expander
import foundation.mee.android_client.ui.theme.Border
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.views.consent.ConsentDuration
import foundation.mee.android_client.views.consent.ConsentEntry
import foundation.mee.android_client.views.consent.ExternalConsentEntry
import uniffi.mee_agent.GapiUserInfo
import uniffi.mee_agent.OtherPartyContextDataUniffi
import kotlin.reflect.full.memberProperties

@Composable
fun ManageConnectorContent(
    entries: ConsentEntriesType
) {
    val state = rememberManageConnectorNewState()

    val optionalClaims = when (entries) {
        is ConsentEntriesType.SiopClaims -> entries.value.filter { x -> !x.isRequired && !x.value.isNullOrEmpty() }
        else -> null
    }

    val requiredClaims = when (entries) {
        is ConsentEntriesType.SiopClaims -> entries.value.filter { x -> x.isRequired }
        else -> null
    }

    val consentEntry = requiredClaims?.firstOrNull { it.id == state.durationPopupId }

    Column {
        when (entries) {
            is ConsentEntriesType.SiopClaims -> {
                if (!requiredClaims.isNullOrEmpty()) {
                    Expander(
                        title = stringResource(R.string.manage_connection_required_expander),
                        color = Color.White,
                        isExpanded = state.isRequiredSectionOpened,
                        onChangeExpanded = {
                            state.isRequiredSectionOpened = !state.isRequiredSectionOpened
                        }
                    ) {
                        LazyColumn {
                            items(items = requiredClaims) { claim ->
                                ConsentEntry(
                                    claim,
                                    isReadOnly = true,
                                    modifier = Modifier.padding(top = 16.dp),
                                    onDurationPopupShow = {
                                        state.durationPopupId = claim.id
                                    }
                                )
                            }
                        }
                    }
                }

                if (!optionalClaims.isNullOrEmpty()) {
                    Divider(
                        color = Border,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 24.dp)
                    )
                    Expander(
                        title = stringResource(R.string.manage_connection_optional_expander),
                        color = Color.White,
                        isExpanded = state.isOptionalSectionOpened,
                        onChangeExpanded = {
                            state.isOptionalSectionOpened = !state.isOptionalSectionOpened
                        }
                    ) {
                        LazyColumn {
                            items(items = optionalClaims) { claim ->
                                ConsentEntry(
                                    claim,
                                    isReadOnly = true,
                                    modifier = Modifier.padding(top = 16.dp)
                                )
                            }
                        }
                    }
                }
            }

            is ConsentEntriesType.GapiEntries -> {
                val gapiEntries: List<Pair<String, String>> =
                    when (val data = entries.value.data) {
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
                gapiEntries.map {
                    Row(modifier = Modifier.padding(bottom = 16.dp)) {
                        ExternalConsentEntry(it.first, it.second)
                    }
                }
            }
        }
    }

    if (state.shouldShowDurationPopup && consentEntry != null) {
        Dialog(
            onDismissRequest = {},
        ) {
            val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
            dialogWindowProvider.window.setGravity(Gravity.BOTTOM)
            dialogWindowProvider.window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                LocalView.current.layoutParams.height
            )

            Surface(
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                ConsentDuration(
                    consentEntry = consentEntry,
                    isReadOnly = true,
                    onSave = {}
                ) {
                    state.durationPopupId = null
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewManageConnectorContent() {
    MeeIdentityAgentTheme {
        ManageConnectorContent(
            manageConnectionDataMock.connectorToEntries[0].consentEntriesType
        )
    }
}
