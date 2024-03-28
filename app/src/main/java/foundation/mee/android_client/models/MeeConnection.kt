package foundation.mee.android_client.models

import uniffi.mee_agent.*

data class MeeConnection(
    var id: String,
    var name: String,
    var tags: List<MeeTag>,
) {
    constructor(from: OtherPartyConnectionUniffi) : this(
        id = from.id,
        name = from.name,
        tags = from.tags.map { MeeTag(it) }
    )
}