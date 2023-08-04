package foundation.mee.android_client.models

import uniffi.mee_agent.OtherPartyContext
import uniffi.mee_agent.OtherPartyContextData
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

data class ExternalMeeContext(
    var id: String,
    var name: String,
    var data: OtherPartyContextData,
) {
    constructor(
        from: OtherPartyContext
    ) : this(
        id = from.id,
        name = from.name,
        data = from.data
    )
}