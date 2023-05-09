package foundation.mee.android_client.views.consent

import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import foundation.mee.android_client.models.ConsentRequestClaim
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.URI


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
    var showCertified by mutableStateOf(false)

    val shouldShowDurationPopup: Boolean
        get() = durationPopupId != null

    fun findIncorrectClaim(claims: List<ConsentRequestClaim>): ConsentRequestClaim? {
        val incorrectClaim = claims.find { x -> x.isIncorrect() } ?: return null

        val isIncorrectRequired = incorrectClaim.isRequired

        if (isIncorrectRequired) {
            isRequiredSectionOpened = true
        } else {
            isRequiredSectionOpened = false // TODO вопрос
            isOptionalSectionOpened = true
        }
        return incorrectClaim

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

    fun onDeclineBuildUri(redirectUri: String?): Uri? {
        if (URLUtil.isValidUrl(redirectUri)) {
            val uri = URI(redirectUri)
            val builder = Uri.Builder()
            return builder.scheme(uri.scheme)
                .authority(uri.authority)
                .path(uri.path)
                .appendQueryParameter(
                    "mee_auth_token",
                    "error:user_cancelled,error_description:user%20declined%20the%20request"
                )
                .build()
        }
        return null
    }
}