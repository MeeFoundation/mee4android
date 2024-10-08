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

enum class ConsentEntryType {
    string,
    date,
    boolean,
    email,
    address,
    card
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
    var isOpen: Boolean = true,
    var order: ULong?
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
        isOn = from.essential,
        type = ConsentEntryType.values().find { it.name == from.typ } ?: ConsentEntryType.string,
        order = from.order
    )

    fun isIncorrect(): Boolean {
        if (type == ConsentEntryType.card) {
            val cardFields = getCardTypeFields()
            return (isRequired || isOn) && (cardFields?.number.isNullOrEmpty() || cardFields?.cvc.isNullOrEmpty() || cardFields?.expirationDate.isNullOrEmpty())
        }
        return (isRequired || isOn) && value.isNullOrEmpty()
    }

    fun getCardTypeFields(): CreditCard? {
        return try {
            val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

            @OptIn(ExperimentalStdlibApi::class)
            val jsonAdapter: JsonAdapter<CreditCard> = moshi.adapter()
            value?.let { jsonAdapter.fromJson(it) }
        } catch (e: Exception) {
            null
        }
    }

    fun getFieldName(): String {
        var primaryValue = value
        if (type == ConsentEntryType.card) {
            val cardFields = getCardTypeFields()
            primaryValue = cardFields?.number
        }

        return if (primaryValue.isNullOrEmpty()) name else primaryValue
    }
}

