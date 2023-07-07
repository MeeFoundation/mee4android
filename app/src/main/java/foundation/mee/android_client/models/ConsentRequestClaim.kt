package foundation.mee.android_client.models

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import uniffi.mee_agent.OidcClaimParams
import uniffi.mee_agent.RetentionDuration
import java.lang.Exception

data class CreditCard(
    var number: String? = null,
    var expirationDate: String? = null,
    var cvc: String? = null,
)
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
        return (isRequired || isOn) && value.isNullOrEmpty()
    }

    fun getCardTypeFields(): CreditCard? {
        try {
            val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            @OptIn(ExperimentalStdlibApi::class)
            val jsonAdapter: JsonAdapter<CreditCard> = moshi.adapter()
            val parsedValue = value?.let { jsonAdapter.fromJson(it) }
            return parsedValue
        } catch (e: Exception) {
            return null
        }
    }
    fun getFieldName(): String {
        var primaryValue = value
        if (type == ConsentEntryType.card) {
            val cardFields = getCardTypeFields()
            primaryValue = cardFields?.number
        }

        return if (primaryValue.isNullOrEmpty()) name else primaryValue!!
    }
}

