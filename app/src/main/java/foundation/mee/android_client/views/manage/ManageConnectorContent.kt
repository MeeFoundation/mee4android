package foundation.mee.android_client.views.manage

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.Expander
import foundation.mee.android_client.views.consent.ConsentEntry
import foundation.mee.android_client.views.consent.ExternalConsentEntry
import uniffi.mee_agent.GapiUserInfo
import uniffi.mee_agent.OtherPartyContextDataUniffi
import kotlin.reflect.full.memberProperties

@Composable
fun ManageConnectorContent(
    entries: ConsentEntriesType
) {

    var isRequiredSectionOpened by remember {
        mutableStateOf(true)
    }

    var isOptionalSectionOpened by remember {
        mutableStateOf(false)
    }

    // TODO move to viewmodel
    val hasOptionalFields = when (entries) {
        is ConsentEntriesType.SiopClaims -> entries.value.any { x -> !x.isRequired && !x.value.isNullOrEmpty() }
        else -> false
    }
    val optionalClaims = when (entries) {
        is ConsentEntriesType.SiopClaims -> entries.value.filter { x -> !x.isRequired && !x.value.isNullOrEmpty() }
        else -> null
    }

    val requiredClaims = when (entries) {
        is ConsentEntriesType.SiopClaims -> entries .value.filter { x -> x.isRequired }
        else -> null
    }

    when (entries) {
        is ConsentEntriesType.SiopClaims -> {
            Expander(
                title = stringResource(R.string.manage_connection_required_expander),
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp),
                isExpanded = isRequiredSectionOpened,
                onChangeExpanded = {
                    isRequiredSectionOpened = !isRequiredSectionOpened
                }
            ) {
                LazyColumn {
                    items(items = requiredClaims!!) { claim ->
                        ConsentEntry(
                            claim,
                            isReadOnly = true,
                            modifier = Modifier.padding(top = 16.dp, start = 3.dp)
                        )
                    }
                }
            }

            if (hasOptionalFields) {
                Expander(
                    title = stringResource(R.string.manage_connection_optional_expander),
                    modifier = Modifier.padding(top = 16.dp),
                    color = Color.White,
                    isExpanded = isOptionalSectionOpened,
                    onChangeExpanded = {
                        isOptionalSectionOpened = !isOptionalSectionOpened
                    }
                ) {
                    LazyColumn {
                        items(items = optionalClaims!!) { claim ->
                            ConsentEntry(
                                claim,
                                isReadOnly = true,
                                modifier = Modifier.padding(top = 16.dp, start = 3.dp)
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
            gapiEntries.map { ExternalConsentEntry(it.first, it.second) }
        }
    }
}