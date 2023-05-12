package foundation.mee.android_client.models

import uniffi.mee_agent.OidcClientMetadata


val meConnectionMock = MeeConnection(
    id = "https://mee.foundation/",
    name = "Mee Foundation",
    value = MeeConnectionType.Siop(
        SiopConnectionType(
            redirectUri = "https://mee.foundation/",
            clientMetadata = PartnerMetadata(
                from = OidcClientMetadata(
                    applicationType = "web",
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


val sites: List<MeeConnection> = listOf(
    MeeConnection(
        id = "https://www.nytimes.com",
        name = "New York Times",
        value = MeeConnectionType.Siop(
            SiopConnectionType(
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
                subjectSyntaxType = ConnectionTypeSubject.DidKey("")
            )
        )
    )
)
val mobileApps: List<MeeConnection> = listOf(
    MeeConnection(
        id = "https://www.washingtonpost.com",
        name = "The Washington Post",
        value = MeeConnectionType.Siop(
            SiopConnectionType(
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
                subjectSyntaxType = ConnectionTypeSubject.DidKey("")
            )
        )
    ),
    MeeConnection(
        id = "https://www.theguardian.com",
        name = "The Guardian",
        value = MeeConnectionType.Siop(
            SiopConnectionType(
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
                subjectSyntaxType = ConnectionTypeSubject.DidKey("")
            )
        )
    )
)
