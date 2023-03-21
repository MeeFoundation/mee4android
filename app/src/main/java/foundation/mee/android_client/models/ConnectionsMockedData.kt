package foundation.mee.android_client.models

import foundation.mee.android_client.models.MeeContext
import foundation.mee.android_client.models.PartnerMetadata
import uniffi.mee_agent.OidcClientMetadata

val meeContextMock = MeeContext(
    id = "https://mee.foundation/",
    did = "", // TODO: think about Option when time comes
    claims = emptyList(),
    clientMetadata = PartnerMetadata(
        from = OidcClientMetadata(
            applicationType = "web",
            clientName = "Mee Foundation",
            logoUri = "https://mee.foundation/favicon.png",
            contacts = emptyList(),
            jwks = null
        )
    )
)


val sites: List<MeeContext> = listOf(
    MeeContext(
        id = "https://www.nytimes.com",
        did = "",
        claims = emptyList(),
        clientMetadata = PartnerMetadata(
            from = OidcClientMetadata(
                applicationType = null,
                clientName = "New York Times",
                logoUri = "https://www.nytimes.com/favicon.ico",
                contacts = emptyList(),
                jwks = null
            )
        )
    )
)
val mobileApps: List<MeeContext> = listOf(
    MeeContext(
        id = "https://www.washingtonpost.com",
        did = "",
        claims = emptyList(),
        clientMetadata = PartnerMetadata(
            from = OidcClientMetadata(
                applicationType = null,
                clientName = "The Washington Post",
                logoUri = "https://www.washingtonpost.com/favicon.ico",
                contacts = emptyList(),
                jwks = null
            )
        )
    ),
    MeeContext(
        id = "https://www.theguardian.com",
        did = "",
        claims = emptyList(),
        clientMetadata = PartnerMetadata(
            from = OidcClientMetadata(
                applicationType = null,
                clientName = "The Guardian",
                logoUri = "https://www.theguardian.com/favicon.ico",
                contacts = emptyList(),
                jwks = null
            )
        )
    )
)
