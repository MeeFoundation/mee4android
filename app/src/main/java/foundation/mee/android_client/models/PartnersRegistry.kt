package foundation.mee.android_client.models

import uniffi.mee_agent.OidcClientMetadata

object PartnersRegistry {
    var shared: List<MeeConnector>

    init {
        shared = listOf(
            MeeConnector(
                id = "https://mee.foundation/",
                name = "Mee Foundation",
                otherPartyConnectionId = "mee.foundation",
                value = MeeConnectorType.Siop(
                    SiopConnectorType(
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
                        subjectSyntaxType = ConnectorTypeSubject.DidKey("")
                    )
                )
            ),
            MeeConnector(
                id = "https://oldeyorktimes.com/",
                name = "The Olde York Times",
                otherPartyConnectionId = "oldeyorktimes.com",
                value = MeeConnectorType.Siop(
                    SiopConnectorType(
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
                        subjectSyntaxType = ConnectorTypeSubject.DidKey("")
                    )
                )
            ),
            MeeConnector(
                id = "https://google.com",
                name = "Google Account",
                otherPartyConnectionId = "google.com",
                value = MeeConnectorType.Gapi(
                    GapiConnectorType(scopes = listOf())
                )
            )
        )
    }

}