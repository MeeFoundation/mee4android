package foundation.mee.android_client.models

import uniffi.mee_agent.OidcClientMetadata

object PartnersRegistry {
    var shared: List<MeeContext>

    init {
        shared = listOf(
            MeeContext(
                id = "https://mee.foundation/",
                did = "",
                claims = emptyList(),
                clientMetadata = PartnerMetadata(
                    from = OidcClientMetadata(
                        applicationType = null,
                        clientName = "Mee Foundation",
                        logoUri = "https://mee.foundation/favicon.png",
                        contacts = emptyList(),
                        jwks = null
                    )
                )
            )
        )
    }

}
