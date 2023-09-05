package foundation.mee.android_client.views.connections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import foundation.mee.android_client.models.MeeConnector
import foundation.mee.android_client.models.MeeConnectorType
import foundation.mee.android_client.utils.getURLFromString

@Composable
fun rememberPartnerEntryState(connection: MeeConnector): PartnerEntryState {
    return remember {
        PartnerEntryState(connection)
    }
}

@Stable
class PartnerEntryState(
    connection: MeeConnector
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
                hostname = getURLFromString(connection.id)?.host ?: connection.id
            }
            is MeeConnectorType.Gapi -> {
                name = "Google Account"
                logoUri = "https://google.com/favicon.ico"
                hostname = "google.com"
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