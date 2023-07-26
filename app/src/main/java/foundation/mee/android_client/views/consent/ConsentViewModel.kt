package foundation.mee.android_client.views.consent

import androidx.lifecycle.ViewModel
import foundation.mee.android_client.models.ConsentRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class ConsentViewModel (
    consent: ConsentRequest
) : ViewModel() {

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