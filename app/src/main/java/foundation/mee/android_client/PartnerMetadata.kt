package foundation.mee.android_client

class PartnerMetadata(val name: String, val displayUrl: String, val logoUrl: String, val contacts: List<String>) {
    private var type: ClientType = ClientType.WEB
    private var jwks: List<String>? = null
}
