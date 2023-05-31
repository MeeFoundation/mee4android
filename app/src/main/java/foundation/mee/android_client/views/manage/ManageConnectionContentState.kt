package foundation.mee.android_client.views.manage

import androidx.compose.runtime.*
import foundation.mee.android_client.models.ConsentRequestClaim

@Composable
fun rememberManageConnectionContentState(claims: List<ConsentRequestClaim>): ManageConnectionContentState {
    return remember {
        ManageConnectionContentState(claims)
    }
}

@Stable
class ManageConnectionContentState(
    claims: List<ConsentRequestClaim>
) {
    var isRequiredSectionOpened by mutableStateOf(true)
    var isOptionalSectionOpened by mutableStateOf(false)

    val hasOptionalFields = claims.any { x -> !x.isRequired && !x.value.isNullOrEmpty() }
    val optionalClaims = claims.filter { x -> !x.isRequired && !x.value.isNullOrEmpty() }
    val requiredClaims = claims.filter { x -> x.isRequired }

}