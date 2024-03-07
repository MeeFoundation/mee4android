package foundation.mee.android_client.views.connections

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.MeeAgentViewModel
import foundation.mee.android_client.models.*

@Composable
fun rememberConnectionsState(meeAgentStore: MeeAgentStore = hiltViewModel<MeeAgentViewModel>().meeAgentStore): ConnectionsState {
    val otherPartnersWebApp =
        remember {
            getOtherPartners(meeAgentStore)
        }

    return remember {
        ConnectionsState(otherPartnersWebApp)
    }
}

@Stable
class ConnectionsState(
    val otherPartnersWebApp: List<MeeConnector>
)

fun getOtherPartners(meeAgentStore: MeeAgentStore): List<MeeConnector> {
    val data = meeAgentStore.getConnectionsWithConnectors()
    return PartnersRegistry.shared.filter { x ->
        val isNotPresentedInExistingList =
            data?.find { it.id == x.otherPartyConnectionId } == null
        isNotPresentedInExistingList || x.isGapi()
    }
}