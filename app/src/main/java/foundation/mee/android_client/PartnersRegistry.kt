package foundation.mee.android_client

import uniffi.mee.*

object PartnersRegistry {
    val shared: List<MaterializedContext.RelyingParty> = listOf(
        MaterializedContext.RelyingParty(
            "", "Mee Foundation", RpContextData(
                ContextProtocol.Siop(
                    ContextProtocolSiop(
                        idToken = "Mee Foundation", redirectUri = "https://mee.foundation/",
                        clientMetadata = OidcClientMetadata(
                            clientName = "Mee Foundation",
                            logoUri = "https://mee.foundation/favicon.png",
                            contacts = listOf(),
                            applicationType = null,
                            jwks = null
                        ), claims = listOf()
                    )
                )
            )
        )
    )
}

//Context(id: "https://mee.foundation/",
// did: "", claims: [], clientMetadata: PartnerMetadata(name: "Mee Foundation", displayUrl: "mee.foundation", logoUrl: "https://mee.foundation/favicon.png", contacts: []))