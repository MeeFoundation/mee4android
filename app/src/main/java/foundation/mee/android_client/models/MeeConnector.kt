package foundation.mee.android_client.models

import uniffi.mee_agent.OpConnectorProtocolUniffi
import uniffi.mee_agent.OtherPartyConnectorUniffi
import uniffi.mee_agent.SubjectSyntaxType

sealed class ConnectorProtocolSubject {
    data class DidKey(
        val value: String
    ) : ConnectorProtocolSubject()

    data class JwkThumbprint(
        val value: String
    ) : ConnectorProtocolSubject()
}


data class SiopConnectorProtocol(
    var redirectUri: String,
    var clientMetadata: PartnerMetadata,
    var subjectSyntaxType: ConnectorProtocolSubject
)

data class GapiConnectorProtocol(
    var scopes: List<String>
)

data class OpenId4VcConnectorProtocol(
    var issuerId: String
)

sealed class MeeConnectorProtocol {
    data class Siop(
        val value: SiopConnectorProtocol
    ) : MeeConnectorProtocol()

    data class Gapi(
        val value: GapiConnectorProtocol
    ) : MeeConnectorProtocol()

    object MeeTalk : MeeConnectorProtocol()

    data class OpenId4Vc(
        val value: OpenId4VcConnectorProtocol
    ) : MeeConnectorProtocol()

    object MeeBrowserExtension : MeeConnectorProtocol()
}

data class MeeConnector(
    var id: String,
    var name: String,
    var otherPartyConnectionId: String,
    var connectorProtocol: MeeConnectorProtocol
) {
    constructor(from: OtherPartyConnectorUniffi) : this(
        id = from.id,
        name = from.name,
        otherPartyConnectionId = from.otherPartyConnectionId,
        connectorProtocol = meeConnectorProtocol(from)
    )

    fun isGapi(): Boolean {
        return this.connectorProtocol is MeeConnectorProtocol.Gapi
    }

    companion object {
        private fun meeConnectorProtocol(from: OtherPartyConnectorUniffi): MeeConnectorProtocol {
            return when (val protocol = from.protocol) {
                is OpConnectorProtocolUniffi.MeeTalk -> MeeConnectorProtocol.MeeTalk
                is OpConnectorProtocolUniffi.Gapi -> MeeConnectorProtocol.Gapi(
                    GapiConnectorProtocol(protocol.value.scopes)
                )

                is OpConnectorProtocolUniffi.Siop -> {
                    val value = protocol.value
                    MeeConnectorProtocol.Siop(
                        SiopConnectorProtocol(
                            value.redirectUri,
                            PartnerMetadata(value.clientMetadata),
                            when (val subjectSyntaxType = value.subjectSyntaxType) {
                                is SubjectSyntaxType.DidKey -> ConnectorProtocolSubject.DidKey(
                                    subjectSyntaxType.value
                                )

                                is SubjectSyntaxType.JwkThumbprint -> ConnectorProtocolSubject.JwkThumbprint(
                                    subjectSyntaxType.value
                                )
                            }
                        )
                    )
                }

                is OpConnectorProtocolUniffi.OpenId4Vc -> MeeConnectorProtocol.OpenId4Vc(
                    OpenId4VcConnectorProtocol(protocol.value.issuerUrl)
                )

                is OpConnectorProtocolUniffi.MeeBrowserExtension -> MeeConnectorProtocol.MeeBrowserExtension
            }
        }
    }
}