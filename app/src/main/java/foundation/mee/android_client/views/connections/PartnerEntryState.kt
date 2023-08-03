package foundation.mee.android_client.views.connections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import foundation.mee.android_client.models.MeeConnection
import foundation.mee.android_client.models.MeeConnectionType
import foundation.mee.android_client.utils.getURLFromString

@Composable
fun rememberPartnerEntryState(connection: MeeConnection): PartnerEntryState {
    return remember {
        PartnerEntryState(connection)
    }
}

@Stable
class PartnerEntryState(
    connection: MeeConnection
) {
    val name: String
    val logoUri: String
    val hostname: String

    init {
        when (val conn = connection.value) {
            is MeeConnectionType.Siop -> {
                val clientMetadata = conn.value.clientMetadata
                name = clientMetadata.name
                logoUri = clientMetadata.logoUrl
                hostname = getURLFromString(connection.id)?.host ?: connection.id
            }
            is MeeConnectionType.Gapi -> {
                name = "Google Account"
                logoUri = "https://google.com/favicon.ico"
                hostname = "google.com"
            }
            is MeeConnectionType.MeeTalk -> {
                name = "Mee Talk"
                logoUri = "https://mee.foundation/favicon.png"
                hostname = "mee.foundation"
            }
        }
    }
}