package foundation.mee.android_client

class Context {
    val id: String
    val did: String
    var claims: List<ConsentRequestClaim>
    val clientMetadata: PartnerMetadata

    constructor(
        id: String,
        did: String,
        claims: List<ConsentRequestClaim>,
        clientMetadata: PartnerMetadata) {
        this.id = id
        this.did = did
        this.claims = claims
        this.clientMetadata = clientMetadata
    }



}
