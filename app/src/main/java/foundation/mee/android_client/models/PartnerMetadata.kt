package foundation.mee.android_client.models

import uniffi.mee_agent.OidcClientMetadata

data class PartnerMetadata(
    val name: String,
    var displayUrl: String,
    var logoUrl: String,
    var type: ClientType = ClientType.WEB,
    var contacts: List<String>,
    var jwks: List<String>? = null
) {
    constructor(from: OidcClientMetadata) :
            this(
                name = from.clientName!!,
                displayUrl = from.clientName!!,
                logoUrl = from.logoUri!!,
                contacts = from.contacts
            ) {
        val type = from.applicationType
//        TODO: uncomment when type is ready
//        require(type is String)
        this.type = ClientType.values().find { it.name == type } ?: ClientType.WEB
    }
}
