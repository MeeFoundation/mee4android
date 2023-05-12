package foundation.mee.android_client.views.consent

import androidx.lifecycle.ViewModel
import foundation.mee.android_client.models.ConsentRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class ConsentViewModel (
    cnsnt: ConsentRequest
) : ViewModel() {

    private val consent = cnsnt.let {
        // Uncomment to test scroll

      /*  it.claims += ConsentRequestClaim(
            id = "Date",
            name = "Date",
            code = "date",
            attributeType = "https://schema.org/date",
            businessPurpose = null,
            isSensitive = true,
            value = null,
            retentionDuration = RetentionDuration.EPHEMERAL,
            isRequired = true,
            type = ConsentEntryType.date
        )
        it.claims += ConsentRequestClaim(
            id = "sdf",
            name = "Date",
            code = "date",
            attributeType = "https://schema.org/date",
            businessPurpose = null,
            isSensitive = true,
            value = null,
            retentionDuration = RetentionDuration.UNTIL_CONNECTION_DELETION,
            isRequired = true,
            type = ConsentEntryType.string
        )
        it.claims += ConsentRequestClaim(
            id = "shr",
            name = "Date",
            code = "date",
            attributeType = "https://schema.org/date",
            businessPurpose = null,
            isSensitive = true,
            value = null,
            retentionDuration = RetentionDuration.UNTIL_CONNECTION_DELETION,
            isRequired = true,
            type = ConsentEntryType.address
        )
        it.claims += ConsentRequestClaim(
            id = "shwer",
            name = "Date",
            code = "date",
            attributeType = "https://schema.org/date",
            businessPurpose = null,
            isSensitive = true,
            value = null,
            retentionDuration = RetentionDuration.UNTIL_CONNECTION_DELETION,
            isRequired = true,
            type = ConsentEntryType.address
        )
        it.claims += ConsentRequestClaim(
            id = "shefr",
            name = "Date",
            code = "date",
            attributeType = "https://schema.org/date",
            businessPurpose = null,
            isSensitive = true,
            value = null,
            retentionDuration = RetentionDuration.UNTIL_CONNECTION_DELETION,
            isRequired = true,
            type = ConsentEntryType.address
        )
        it.claims += ConsentRequestClaim(
            id = "shsdtefr",
            name = "Date",
            code = "date",
            attributeType = "https://schema.org/date",
            businessPurpose = null,
            isSensitive = true,
            value = null,
            retentionDuration = RetentionDuration.UNTIL_CONNECTION_DELETION,
            isRequired = true,
            type = ConsentEntryType.address
        )
        it.claims += ConsentRequestClaim(
            id = "shktykefr",
            name = "Date",
            code = "date",
            attributeType = "https://schema.org/date",
            businessPurpose = null,
            isSensitive = true,
            value = null,
            retentionDuration = RetentionDuration.UNTIL_CONNECTION_DELETION,
            isRequired = false,
            type = ConsentEntryType.address
        )
        it.claims += ConsentRequestClaim(
            id = "sasdfhefr",
            name = "Date",
            code = "date",
            attributeType = "https://schema.org/date",
            businessPurpose = null,
            isSensitive = true,
            value = null,
            retentionDuration = RetentionDuration.UNTIL_CONNECTION_DELETION,
            isRequired = false,
            type = ConsentEntryType.address
        )
        it.claims += ConsentRequestClaim(
            id = "sasdfhqwrqwefr",
            name = "Date",
            code = "date",
            attributeType = "https://schema.org/date",
            businessPurpose = null,
            isSensitive = true,
            value = null,
            retentionDuration = RetentionDuration.UNTIL_CONNECTION_DELETION,
            isRequired = false,
            type = ConsentEntryType.address
        )
        it.claims += ConsentRequestClaim(
            id = "sasdsdgsdfhefr",
            name = "Date",
            code = "date",
            attributeType = "https://schema.org/date",
            businessPurpose = null,
            isSensitive = true,
            value = null,
            retentionDuration = RetentionDuration.UNTIL_CONNECTION_DELETION,
            isRequired = false,
            type = ConsentEntryType.address
        )*/
        it
    }

    private val _uiState = MutableStateFlow(consent)

    val uiState: StateFlow<ConsentRequest> = _uiState.asStateFlow()

    fun updateValue(id: String, newValue: String) {
        val index = uiState.value.claims.indexOfFirst { x -> x.id == id }
        val newClaims = uiState.value.claims.toMutableList()
        newClaims[index] = newClaims[index].copy(value = newValue)

        _uiState.update { it.copy(claims = newClaims) }
    }

    fun updateIsOn(id: String, isOn: Boolean) {
        val index = uiState.value.claims.indexOfFirst { x -> x.id == id }
        val newClaims = uiState.value.claims.toMutableList()
        newClaims[index] = newClaims[index].copy(isOn = isOn)

        _uiState.update { it.copy(claims = newClaims) }
    }

    fun updateIsOpen(id: String, isOpen: Boolean) {
        val index = uiState.value.claims.indexOfFirst { x -> x.id == id }
        val newClaims = uiState.value.claims.toMutableList()

        val newIsOpen = if (newClaims[index].isIncorrect()) true else isOpen

        newClaims[index] =
            newClaims[index].copy(isOpen = newIsOpen)

        _uiState.update { it.copy(claims = newClaims) }

    }
}