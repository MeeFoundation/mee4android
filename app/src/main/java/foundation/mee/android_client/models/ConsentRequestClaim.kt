package foundation.mee.android_client.models

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
    val id: String,
    val name: String,
    var code: String,
    var attributeType: String?,
    var businessPurpose: String?,
    var isSensitive: Boolean?,
    var value: String?,
    var retentionDuration: RetentionDuration? = RetentionDuration.UNTIL_CONNECTION_DELETION,
    var isRequired: Boolean = false,
    val type: ConsentEntryType,
    var isOn: Boolean = false,
    var isOpen: Boolean = false
) {
    constructor(
        from: OidcClaimParams,
        code: String
    ) : this(
        id = from.name!!,
        name = from.name!!,
        code = code,
        attributeType = from.attributeType,
        businessPurpose = from.businessPurpose,
        isSensitive = from.isSensitive,
        value = from.value,
        retentionDuration = from.retentionDuration,
        isRequired = from.essential,
        type = ConsentEntryType.values().find { it.name == from.typ } ?: ConsentEntryType.string
    )

    fun isIncorrect(): Boolean {
        return if (type != ConsentEntryType.card) {
            (isRequired || isOn) && value.isNullOrEmpty()
        } else
            true
    }

    fun getFieldName(): String {
        return if (value.isNullOrEmpty()) name else value!!
    }
}

