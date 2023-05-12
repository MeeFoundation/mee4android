package foundation.mee.android_client.models

import uniffi.mee_agent.*


sealed class ConnectionTypeSubject {
    data class DidKey(
        val value: String
    ) : ConnectionTypeSubject()

    data class JwkThumbprint(
        val value: String
    ) : ConnectionTypeSubject()
}


data class SiopConnectionType(
    var redirectUri: String,
    var clientMetadata: PartnerMetadata,
    var subjectSyntaxType: ConnectionTypeSubject
)

data class GapiConnectionType(
    var scopes: List<String>
)

sealed class MeeConnectionType {
    data class Siop(
        val value: SiopConnectionType
    ) : MeeConnectionType()

    data class Gapi(
        val value: GapiConnectionType
    ) : MeeConnectionType()

    object MeeTalk : MeeConnectionType()


}

data class MeeConnection(
    var id: String,
    var name: String,
    var value: MeeConnectionType
) {
    constructor(from: OtherPartyConnectionUniffi) : this(
        id = from.id,
        name = from.name,
        value = meeConnectionType(from)
    )

    companion object {
        private fun meeConnectionType(from: OtherPartyConnectionUniffi): MeeConnectionType {
            return when (val protocol = from.protocol) {
                is OpConnectionProtocolUniffi.MeeTalk -> MeeConnectionType.MeeTalk
                is OpConnectionProtocolUniffi.Gapi -> MeeConnectionType.Gapi(
                    GapiConnectionType(protocol.value.scopes)
                )
                is OpConnectionProtocolUniffi.Siop -> {
                    val value = protocol.value
                    MeeConnectionType.Siop(
                        SiopConnectionType(
                            value.redirectUri,
                            PartnerMetadata(value.clientMetadata),
                            when (val subjectSyntaxType = value.subjectSyntaxType) {
                                is SubjectSyntaxType.DidKey -> ConnectionTypeSubject.DidKey(
                                    subjectSyntaxType.value
                                )
                                is SubjectSyntaxType.JwkThumbprint -> ConnectionTypeSubject.JwkThumbprint(
                                    subjectSyntaxType.value
                                )
                            }
                        )
                    )
                }
            }
        }

    }
}