package foundation.mee.android_client

import uniffi.mee_agent.OidcClientMetadata

object PartnersRegistry {
    var shared: List<Context>

    init {
        this.shared = listOf(
            Context(
                id = "https://mee.foundation/",
                did = "",
                claims = emptyList<ConsentRequestClaim>(),
                clientMetadata = PartnerMetadata(
                    from = OidcClientMetadata(
                        applicationType = null,

                        clientName = "Mee Foundation",
//            displayUrl = "mee.foundation",
                        logoUri = "https://mee.foundation/favicon.png",
                        contacts = emptyList(),
                        jwks = null
                    )
                )
            )
        )
    }

}
