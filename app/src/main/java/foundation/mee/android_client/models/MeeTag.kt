package foundation.mee.android_client.models

import uniffi.mee_agent.OtherPartyTagUniffi

data class MeeTag(
    var id: String,
    var name: String,
) {
    constructor(from: OtherPartyTagUniffi) : this(
        id = from.id,
        name = from.name
    )
}