package foundation.mee.android_client.models

import uniffi.mee_agent.OidcClientMetadata
import uniffi.mee_agent.SubjectSyntaxTypesSupported
import uniffi.mee_agent.Url

data class PartnerMetadata(
    val name: String,
    var displayUrl: String,
    var logoUrl: String,
    var type: ClientType = ClientType.web,
    var contacts: List<String>? = null,
    var jwks: List<String>? = null,
    var jwksUri: Url? = null,
    var idTokenSignedResponseAlg: String? = null,
    var idTokenEncryptedResponseAlg: String? = null,
    var idTokenEncryptedResponseEnc: String? = null,
    var subjectSyntaxTypesSupported: List<SubjectSyntaxTypesSupported>? = listOf(),

    ) {
    constructor(from: OidcClientMetadata) :
            this(
                name = from.clientName!!,
                displayUrl = from.clientName!!,
                logoUrl = from.logoUri!!,
                contacts = from.contacts,
                jwks = from.jwks,
                jwksUri = from.jwksUri,
                idTokenSignedResponseAlg = from.idTokenSignedResponseAlg,
                idTokenEncryptedResponseAlg = from.idTokenEncryptedResponseAlg,
                idTokenEncryptedResponseEnc = from.idTokenEncryptedResponseEnc,
                subjectSyntaxTypesSupported = from.subjectSyntaxTypesSupported
            ) {
        val type = from.applicationType
//        TODO: uncomment when type is ready
//        require(type is String)
        this.type = ClientType.values().find { it.name == type } ?: ClientType.web
    }
}
