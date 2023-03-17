package foundation.mee.android_client

import uniffi.mee_agent.OidcScope
import uniffi.mee_agent.OidcScopeWrapper
import uniffi.mee_agent.Url

class ConsentRequest(from: Context, clientId: String = "", nonce: String = "", redirectUri: String = "", isCrossDevice: Boolean = false, clientMetadata: PartnerMetadata? = null) {
    val id: String
    val scope: OidcScopeWrapper
    var claims: List<ConsentRequestClaim>
    val clientMetadata: PartnerMetadata
    val nonce: String
    val clientId: String
    val redirectUri: Url
    val presentationDefinition: String?
    val isCrossDeviceFlow: Boolean

    // TODO: check for shallow-copy / deep-copy later on
    init {
        this.scope = OidcScopeWrapper.Set(scope = listOf(OidcScope.OPENID))
        this.claims = from.claims
        this.clientMetadata = clientMetadata ?: from.clientMetadata
        this.nonce = nonce
        this.clientId = clientId
        this.redirectUri = redirectUri
        this.presentationDefinition = ""
        this.isCrossDeviceFlow = isCrossDevice
        this.id = from.id
    }

}
