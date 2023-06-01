package foundation.mee.android_client.views.connections

import androidx.compose.runtime.*
import foundation.mee.android_client.models.*
import foundation.mee.android_client.utils.getHostname

@Composable
fun rememberConnectionsState(): ConnectionsState {

    val data = remember {
        MeeAgentStore.getAllItems()
    }
    val existingPartnersWebApp = remember {
        getExistingPartnersWeb(data)
    }
    val existingPartnersMobileApp =
        remember {
            getExistingPartnersMobile(data)
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
    val existingPartnersWebApp: List<MeeConnection>,
    val existingPartnersMobileApp: List<MeeConnection>,
    val otherPartnersWebApp: List<MeeConnection>
)

fun getExistingPartnersWeb(
    data: List<MeeConnection>?
): List<MeeConnection>? {
    return data?.filter { x ->
        when (val connType = x.value) {
            is MeeConnectionType.Siop -> when (connType.value.clientMetadata.type) {
                ClientType.web -> MeeAgentStore.getLastConnectionConsentById(x.id) != null
                else -> false
            }
            else -> false
        }
    }
}

fun getExistingPartnersMobile(
    data: List<MeeConnection>?
): List<MeeConnection>? {
    return data?.filter { x ->
        when (val connType = x.value) {
            is MeeConnectionType.Siop -> when (connType.value.clientMetadata.type) {
                ClientType.mobile -> MeeAgentStore.getLastConnectionConsentById(x.id) != null
                else -> false
            }
            else -> false
        }
    }
}

fun getOtherPartners(existingPartnersWebApp: List<MeeConnection>?): List<MeeConnection> {
    return PartnersRegistry.shared.filter { x ->
        existingPartnersWebApp?.find { getHostname(it.id) == getHostname(x.id) } == null
    }
}