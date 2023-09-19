package foundation.mee.android_client.views.connections

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.MeeAgentViewModel
import foundation.mee.android_client.models.*
import foundation.mee.android_client.utils.getHostname

@Composable
fun rememberConnectionsState(meeAgentStore: MeeAgentStore = hiltViewModel<MeeAgentViewModel>().meeAgentStore): ConnectionsState {

    val data = remember {
        meeAgentStore.getAllItems()
    }
    val existingPartnersWebApp = remember {
        getExistingPartnersWeb(data, meeAgentStore)
    }
    val existingPartnersMobileApp =
        remember {
            getExistingPartnersMobile(data, meeAgentStore)
        }
    val otherPartnersWebApp =
        remember {
            getOtherPartners(existingPartnersWebApp)
        }


    return remember {
        ConnectionsState(
            existingPartnersWebApp ?: listOf(),
            existingPartnersMobileApp ?: listOf(),
            otherPartnersWebApp
        )
    }
}

@Stable
class ConnectionsState(
    val existingPartnersWebApp: List<MeeConnector>,
    val existingPartnersMobileApp: List<MeeConnector>,
    val otherPartnersWebApp: List<MeeConnector>
)

fun getExistingPartnersWeb(
    data: List<MeeConnector>?,
    meeAgentStore: MeeAgentStore
): List<MeeConnector>? {
    return data?.filter { x ->
        when (val connType = x.value) {
            is MeeConnectorType.Siop -> when (connType.value.clientMetadata.type) {
                ClientType.web -> meeAgentStore.getLastConnectionConsentByConnectorId(x.id) != null
                else -> false
            }
            is MeeConnectorType.Gapi -> true
            else -> false
        }
    }
}

fun getExistingPartnersMobile(
    data: List<MeeConnector>?,
    meeAgentStore: MeeAgentStore
): List<MeeConnector>? {
    return data?.filter { x ->
        when (val connType = x.value) {
            is MeeConnectorType.Siop -> when (connType.value.clientMetadata.type) {
                ClientType.mobile -> meeAgentStore.getLastConnectionConsentByConnectorId(x.id) != null
                else -> false
            }
            else -> false
        }
    }
}

fun getOtherPartners(existingPartnersWebApp: List<MeeConnector>?): List<MeeConnector> {
    return PartnersRegistry.shared.filter { x ->
        val isNotPresentedInExistingList = existingPartnersWebApp?.find { getHostname(it.otherPartyConnectionId) == x.otherPartyConnectionId } == null
        val isGapiInList = existingPartnersWebApp?.find { it.value is MeeConnectorType.Gapi } != null
        val isGapiInListAndEntryIsGapi = isGapiInList && x.value is MeeConnectorType.Gapi
        isNotPresentedInExistingList && !isGapiInListAndEntryIsGapi
    }
}