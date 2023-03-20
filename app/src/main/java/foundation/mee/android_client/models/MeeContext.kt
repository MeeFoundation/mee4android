package foundation.mee.android_client.models

import foundation.mee.android_client.ConsentRequestClaim

data class MeeContext(val id: String, val did: String, var claims: List<ConsentRequestClaim>, val clientMetadata: PartnerMetadata)
