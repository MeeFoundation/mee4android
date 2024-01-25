package foundation.mee.android_client.views.manage

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