package foundation.mee.android_client

import uniffi.mee_agent.OidcClaimParams
import uniffi.mee_agent.RetentionDuration

enum class ConsentEntryType(val type: String) {
    string("string"),
    date("date"),
    boolean("boolean"),
    email("email"),
    address("address"),
    card("card")
}
class ConsentRequestClaim {
    val id: String
    var code: String
    var name: String
    var attributeType: String?
    var businessPurpose: String?
    var isSensitive: Boolean?
    var type: ConsentEntryType
    var value: String?
    var retentionDuration: RetentionDuration? = RetentionDuration.UNTIL_CONNECTION_DELETION
    var isRequired: Boolean = false

    constructor(
        from: OidcClaimParams,
        code: String
    ) {
        val name = from.name
        require(name is String)
        this.id = name
        this.code = code
        this.name = name
        this.attributeType = from.attributeType
        this.businessPurpose = from.businessPurpose
        this.isSensitive = from.isSensitive
        this.type = ConsentEntryType.values().find { it.name == from.typ } ?: ConsentEntryType.string
        this.value = from.value
        this.retentionDuration = from.retentionDuration
        this.isRequired = from.essential
    }
}
