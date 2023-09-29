package foundation.mee.android_client.views.connections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.MeeAgentViewModel
import foundation.mee.android_client.models.MeeAgentStore
import foundation.mee.android_client.models.MeeConnector
import foundation.mee.android_client.models.MeeConnectorType
import foundation.mee.android_client.utils.getURLFromString
import uniffi.mee_agent.OtherPartyContextData

@Composable
fun rememberPartnerEntryState(
    connection: MeeConnector,
    meeAgentStore: MeeAgentStore = hiltViewModel<MeeAgentViewModel>().meeAgentStore
): PartnerEntryState {
    return remember {
        PartnerEntryState(connection, meeAgentStore)
    }
}

@Stable
class PartnerEntryState(
    connection: MeeConnector,
    meeAgentStore: MeeAgentStore
) {
    val name: String
    val logoUri: String
    val hostname: String

    init {
        when (val conn = connection.value) {
            is MeeConnectorType.Siop -> {
                val clientMetadata = conn.value.clientMetadata
                name = clientMetadata.name
                logoUri = clientMetadata.logoUrl
                hostname = getURLFromString(connection.otherPartyConnectionId)?.host ?: connection.otherPartyConnectionId
            }
            is MeeConnectorType.Gapi -> {
                name = "Google Account"
                logoUri = "https://google.com/favicon.ico"

                val id = "google.com"
                hostname = when (val data = meeAgentStore.getLastExternalConsentById(connection.otherPartyConnectionId)?.data) {
                    is OtherPartyContextData.Gapi -> {
                        data.value.userInfo.email ?: id
                    }
                    else -> id
                }
            }
            is MeeConnectorType.MeeTalk -> {
                name = "Mee Talk"
                logoUri = "https://mee.foundation/favicon.png"
                hostname = "mee.foundation"
            }
            is MeeConnectorType.OpenId4Vc -> {
                name = "OpenId4Vc"
                logoUri = ""
                hostname = ""
            }
            is MeeConnectorType.MeeBrowserExtension -> {
                name = "Mee Browser Extension"
                logoUri = ""
                hostname = ""
            }
        }
    }
}