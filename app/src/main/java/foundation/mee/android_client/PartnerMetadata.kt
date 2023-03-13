package foundation.mee.android_client

import uniffi.mee_agent.OidcClientMetadata

class PartnerMetadata {
    val name: String
    val displayUrl: String
    val logoUrl: String
    val contacts: List<String>
    var type: ClientType = ClientType.WEB
    var jwks: List<String>? = null

    constructor(from: OidcClientMetadata) {
        val name = from.clientName
        val logoUrl = from.logoUri
        val type = from.applicationType
        require(name is String)
        require(logoUrl is String)
        require(type is String)
        this.name = name
        this.displayUrl = name
        this.logoUrl = logoUrl
        this.contacts = from.contacts
        this.type = ClientType.values().find { it.name == type } ?: ClientType.WEB
    }
}

