package foundation.mee.android_client.views.consent

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import foundation.mee.android_client.models.ConsentRequestClaim
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun rememberConsentPageNewState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    optionalListState: LazyListState = rememberLazyListState(),
    requiredListState: LazyListState = rememberLazyListState(),
): ConsentPageNewState {
    return remember(coroutineScope, optionalListState, requiredListState) {
        ConsentPageNewState(
            coroutineScope,
            optionalListState,
            requiredListState
        )
    }
}


@Stable
class ConsentPageNewState(
    private val coroutineScope: CoroutineScope,
    val optionalListState: LazyListState,
    val requiredListState: LazyListState
) {

    var durationPopupId by mutableStateOf<String?>(null)
    var isRequiredSectionOpened by mutableStateOf(true)
    var isOptionalSectionOpened by mutableStateOf(false)

    // TODO var showCertified = false

    val shouldShowDurationPopup: Boolean
        get() = durationPopupId != null

    fun incorrectClaimId(claims: List<ConsentRequestClaim>): String? {
        // Keyboard
        val incorrectFieldIndex = claims.indexOfFirst { x -> x.isIncorrect() }
        return if (incorrectFieldIndex == -1) {
            null
        } else {
            if (claims[incorrectFieldIndex].isRequired) {
                isRequiredSectionOpened = true
            } else {
                isRequiredSectionOpened = false // TODO
                isOptionalSectionOpened = true
            }
            return claims[incorrectFieldIndex].id
        }
    }

    fun scrollToPosition(index: Int, isRequired: Boolean) {
        coroutineScope.launch {
            if (isRequired) {
                requiredListState.scrollToItem(index)
            } else {
                optionalListState.scrollToItem(index)
            }
        }
    }

    fun findIndexById(list: List<ConsentRequestClaim>, incorrectId: String): Int {
        return list.indexOfFirst { it.id == incorrectId }
    }
}