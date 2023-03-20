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

data class ConsentRequestClaim(
    var code: String,
    var attributeType: String?,
    var businessPurpose: String?,
    var isSensitive: Boolean?,
    var value: String?,
    var retentionDuration: RetentionDuration? = RetentionDuration.UNTIL_CONNECTION_DELETION,
    var isRequired: Boolean = false
) {
    private lateinit var id: String
    private lateinit var name: String
    private lateinit var type: ConsentEntryType

    constructor(
        from: OidcClaimParams,
        code: String
    ) : this(
        code = code,
        attributeType = from.attributeType,
        businessPurpose = from.businessPurpose,
        isSensitive = from.isSensitive,
        value = from.value,
        retentionDuration = from.retentionDuration,
        isRequired = from.essential
    ) {
        val name = from.name
        require(name is String)
        this.id = name
        this.name = name
        this.type =
            ConsentEntryType.values().find { it.name == from.typ } ?: ConsentEntryType.string
    }
}
