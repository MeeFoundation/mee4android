package foundation.mee.android_client.views.manage

import androidx.compose.runtime.*
import foundation.mee.android_client.models.ConsentRequestClaim
import foundation.mee.android_client.models.ExternalMeeContext

sealed class ConsentEntriesType {
    data class SiopClaims(
        val value: List<ConsentRequestClaim>
    ) : ConsentEntriesType()

    data class GapiEntries(
        val value: ExternalMeeContext
    ) : ConsentEntriesType()
}

@Composable
fun  rememberManageConnectionContentState(consentEntries: ConsentEntriesType): ManageConnectionContentState {
    return remember {
        ManageConnectionContentState(consentEntries)
    }
}

@Stable
class ManageConnectionContentState(
    consentEntries: ConsentEntriesType
) {
    var isRequiredSectionOpened by mutableStateOf(true)
    var isOptionalSectionOpened by mutableStateOf(false)

    val hasOptionalFields = when (consentEntries) {
        is ConsentEntriesType.SiopClaims -> consentEntries.value.any { x -> !x.isRequired && !x.value.isNullOrEmpty() }
        else -> false
    }
    val optionalClaims = when (consentEntries) {
        is ConsentEntriesType.SiopClaims -> consentEntries.value.filter { x -> !x.isRequired && !x.value.isNullOrEmpty() }
        else -> null
    }

    val requiredClaims = when (consentEntries) {
        is ConsentEntriesType.SiopClaims -> consentEntries.value.filter { x -> x.isRequired }
        else -> null
    }

}