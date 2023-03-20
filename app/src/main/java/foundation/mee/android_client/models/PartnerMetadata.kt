package foundation.mee.android_client.models

import uniffi.mee_agent.OidcClientMetadata

data class PartnerMetadata(
    var contacts: List<String>,
    var jwks: List<String>? = null
) {
    lateinit var name: String
    lateinit var displayUrl: String
    lateinit var logoUrl: String
    var type: ClientType = ClientType.WEB
    constructor(from: OidcClientMetadata) : this(contacts = from.contacts) {
        val name = from.clientName
        val logoUrl = from.logoUri
        val type = from.applicationType
        require(name is String)
        require(logoUrl is String)
//        TODO: uncomment when type is ready
//        require(type is String)
        this.name = name
        this.displayUrl = name
        this.logoUrl = logoUrl
        this.contacts = from.contacts
        this.type = ClientType.values().find { it.name == type } ?: ClientType.WEB
    }
}
