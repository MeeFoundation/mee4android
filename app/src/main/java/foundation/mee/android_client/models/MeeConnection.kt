package foundation.mee.android_client.models

import uniffi.mee_agent.*

data class MeeConnection(
    var id: String,
    var name: String,
    var tags: List<MeeTag>,
) {
    fun isDemo(): Boolean {
        return this.name == "The Olde York Times"
    }
    constructor(from: OtherPartyConnectionUniffi) : this(
        id = from.id,
        name = from.name,
        tags = from.tags.map { MeeTag(it) }
    )
}