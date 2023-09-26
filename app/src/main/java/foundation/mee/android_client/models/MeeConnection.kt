package foundation.mee.android_client.models

import uniffi.mee_agent.*


sealed class ConnectorTypeSubject {
    data class DidKey(
        val value: String
    ) : ConnectorTypeSubject()

    data class JwkThumbprint(
        val value: String
    ) : ConnectorTypeSubject()
}


data class SiopConnectorType(
    var redirectUri: String,
    var clientMetadata: PartnerMetadata,
    var subjectSyntaxType: ConnectorTypeSubject
)

data class GapiConnectorType(
    var scopes: List<String>
)

data class OpenId4VcConnectorType(
    var issuerId: String
)

sealed class MeeConnectorType {
    data class Siop(
        val value: SiopConnectorType
    ) : MeeConnectorType()

    data class Gapi(
        val value: GapiConnectorType
    ) : MeeConnectorType()

    object MeeTalk : MeeConnectorType()

    data class OpenId4Vc(
        val value: OpenId4VcConnectorType
    ) : MeeConnectorType()

    object MeeBrowserExtension : MeeConnectorType()
}

data class MeeConnector(
    var id: String,
    var name: String,
    var otherPartyConnectionId: String,
    var value: MeeConnectorType
) {
    constructor(from: OtherPartyConnectorUniffi) : this(
        id = from.id,
        name = from.name,
        otherPartyConnectionId = from.otherPartyConnectionId,
        value = meeConnectorType(from)
    )

    companion object {
        private fun meeConnectorType(from: OtherPartyConnectorUniffi): MeeConnectorType {
            return when (val protocol = from.protocol) {
                is OpConnectorProtocolUniffi.MeeTalk -> MeeConnectorType.MeeTalk
                is OpConnectorProtocolUniffi.Gapi -> MeeConnectorType.Gapi(
                    GapiConnectorType(protocol.value.scopes)
                )
                is OpConnectorProtocolUniffi.Siop -> {
                    val value = protocol.value
                    MeeConnectorType.Siop(
                        SiopConnectorType(
                            value.redirectUri,
                            PartnerMetadata(value.clientMetadata),
                            when (val subjectSyntaxType = value.subjectSyntaxType) {
                                is SubjectSyntaxType.DidKey -> ConnectorTypeSubject.DidKey(
                                    subjectSyntaxType.value
                                )
                                is SubjectSyntaxType.JwkThumbprint -> ConnectorTypeSubject.JwkThumbprint(
                                    subjectSyntaxType.value
                                )
                            }
                        )
                    )
                }
                is OpConnectorProtocolUniffi.OpenId4Vc -> MeeConnectorType.OpenId4Vc(
                    OpenId4VcConnectorType(protocol.value.issuerUrl)
                )
                is OpConnectorProtocolUniffi.MeeBrowserExtension -> MeeConnectorType.MeeBrowserExtension
            }
        }
    }
}