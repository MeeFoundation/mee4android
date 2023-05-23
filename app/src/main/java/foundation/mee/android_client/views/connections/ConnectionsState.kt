package foundation.mee.android_client.views.connections

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import foundation.mee.android_client.models.*
import foundation.mee.android_client.utils.getHostname

@Composable
fun rememberConnectionsState(
): ConnectionsState {
    val context = LocalContext.current
    val appDir = context.applicationInfo.dataDir
    val agentStore = MeeAgentStore("$appDir/mee")

    val data = agentStore.getAllItems()
    val existingPartnersWebApp = getExistingPartnersWeb(data, agentStore)
    val existingPartnersMobileApp = getExistingPartnersMobile(data, agentStore)
    val otherPartnersWebApp = getOtherPartners(existingPartnersWebApp)


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
    data: List<MeeConnection>?,
    agentStore: MeeAgentStore
): List<MeeConnection>? {
    return data?.filter { x ->
        when (val connType = x.value) {
            is MeeConnectionType.Siop -> when (connType.value.clientMetadata.type) {
                ClientType.web -> agentStore.getLastConnectionConsentById(x.id) != null
                else -> false
            }
            else -> false
        }
    }
}

fun getExistingPartnersMobile(
    data: List<MeeConnection>?,
    agentStore: MeeAgentStore
): List<MeeConnection>? {
    return data?.filter { x ->
        when (val connType = x.value) {
            is MeeConnectionType.Siop -> when (connType.value.clientMetadata.type) {
                ClientType.mobile -> agentStore.getLastConnectionConsentById(x.id) != null
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