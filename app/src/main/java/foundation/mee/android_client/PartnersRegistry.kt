package foundation.mee.android_client

object PartnersRegistry {
    var shared: List<Context>

    init {
        this.shared = listOf(
            Context(
                id = "https://mee.foundation/",
                did = "",
                claims = emptyList<ConsentRequestClaim>(),
                clientMetadata = PartnerMetadata(
                    name = "Mee Foundation",
                    displayUrl = "mee.foundation",
                    logoUrl = "https://mee.foundation/favicon.png",
                    contacts = emptyList()
                )
            )
        )
    }

}
