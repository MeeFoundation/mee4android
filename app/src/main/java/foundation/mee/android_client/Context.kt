package foundation.mee.android_client

data class Context(var id: String, val did: String, var claims: List<ConsentRequestClaim>, val clientMetadata: PartnerMetadata)
