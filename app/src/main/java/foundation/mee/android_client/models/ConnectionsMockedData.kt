package foundation.mee.android_client.models

import uniffi.mee_agent.OidcClientMetadata


val meConnectionMock = MeeConnection(
    id = "mee.foundation",
    name = "Mee Foundation"
)


val sites: List<MeeConnector> = listOf(
    MeeConnector(
        id = "https://www.nytimes.com",
        name = "New York Times",
        otherPartyConnectionId = "nytimes.com",
        value = MeeConnectorType.Siop(
            SiopConnectorType(
                redirectUri = "https://www.nytimes.com",
                clientMetadata = PartnerMetadata(
                    from = OidcClientMetadata(
                        applicationType = null,
                        clientName = "New York Times",
                        logoUri = "https://www.nytimes.com/favicon.ico",
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
    )
)
val mobileApps: List<MeeConnector> = listOf(
    MeeConnector(
        id = "https://www.washingtonpost.com",
        name = "The Washington Post",
        otherPartyConnectionId = "washingtonpost.com",
        value = MeeConnectorType.Siop(
            SiopConnectorType(
                redirectUri = "https://www.washingtonpost.com",
                clientMetadata = PartnerMetadata(
                    from = OidcClientMetadata(
                        applicationType = null,
                        clientName = "The Washington Post",
                        logoUri = "https://www.washingtonpost.com/favicon.ico",
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
        id = "https://www.theguardian.com",
        name = "The Guardian",
        otherPartyConnectionId = "theguardian.com",
        value = MeeConnectorType.Siop(
            SiopConnectorType(
                redirectUri = "https://www.theguardian.com",
                clientMetadata = PartnerMetadata(
                    from = OidcClientMetadata(
                        applicationType = null,
                        clientName = "The Guardian",
                        logoUri = "https://www.theguardian.com/favicon.ico",
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
    )
)
