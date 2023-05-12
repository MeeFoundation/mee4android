package foundation.mee.android_client.models

import uniffi.mee_agent.SiopConsentUniffi

data class MeeContext(
    var id: String,
    var otherPartyConnectionId: String,
    var createdAt: String,
    var attributes: List<ConsentRequestClaim>
) {
    constructor(
        from: SiopConsentUniffi
    ) : this(
        id = from.id,
        otherPartyConnectionId = from.otherPartyConnectionId,
        createdAt = from.createdAt,
        attributes = from.attributes.mapNotNull { rec ->
            rec.value?.let {
                ConsentRequestClaim(
                    from = it,
                    code = rec.key
                )
            }
        }
    )
}
