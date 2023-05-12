package foundation.mee.android_client.models

import uniffi.mee_agent.OidcClientMetadata

object PartnersRegistry {
    var shared: List<MeeConnection>

    init {
        shared = listOf(
            MeeConnection(
                id = "https://mee.foundation/",
                name = "Mee Foundation",
                value = MeeConnectionType.Siop(
                    SiopConnectionType(
                        redirectUri = "https://mee.foundation/",
                        clientMetadata = PartnerMetadata(
                            from = OidcClientMetadata(
                                applicationType = null,
                                clientName = "Mee Foundation",
                                logoUri = "https://mee.foundation/favicon.png",
                                contacts = emptyList(),
                                jwks = null,
                                jwksUri = null,
                                idTokenSignedResponseAlg = null,
                                idTokenEncryptedResponseAlg = null,
                                idTokenEncryptedResponseEnc = null,
                                subjectSyntaxTypesSupported = listOf()
                            )
                        ),
                        subjectSyntaxType = ConnectionTypeSubject.DidKey("")
                    )
                )
            )
        )
    }

}