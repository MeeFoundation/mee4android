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
            ),
            MeeConnection(
                id = "https://oldeyorktimes.com/",
                name = "The Olde York Times",
                value = MeeConnectionType.Siop(
                    SiopConnectionType(
                        redirectUri = "https://oldeyorktimes.com/",
                        clientMetadata = PartnerMetadata(
                            from = OidcClientMetadata(
                                applicationType = null,
                                clientName = "The Olde York Times",
                                logoUri = "https://oldeyorktimes.com/favicon.png",
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
            ),
//            MeeConnection(
//                id = "https://google.com",
//                name = "Google Account",
//                value = MeeConnectionType.Gapi(
//                    GapiConnectionType(scopes = listOf())
//                )
//            )
        )
    }

}