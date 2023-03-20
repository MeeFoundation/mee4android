package foundation.mee.android_client.models

import foundation.mee.android_client.ConsentRequestClaim
import uniffi.mee_agent.OidcScope
import uniffi.mee_agent.OidcScopeWrapper
import uniffi.mee_agent.Url

// TODO: Think about removing "" and adding Option type
data class ConsentRequest(
    val clientId: String = "",
    val nonce: String = "",
    val redirectUri: Url = "",
    val isCrossDeviceFlow: Boolean = false,
    val clientMetadata: PartnerMetadata
) {
    lateinit var id: String
        private set
    lateinit var scope: OidcScopeWrapper
        private set
    private lateinit var claims: List<ConsentRequestClaim>
    var presentationDefinition: String? = ""

    // TODO: check for shallow-copy / deep-copy later on
    constructor(
        from: MeeContext,
        clientId: String = "",
        nonce: String = "",
        redirectUri: String = "",
        isCrossDeviceFlow: Boolean = false,
        clientMetadata: PartnerMetadata? = null
    ) : this(
        clientId,
        nonce,
        redirectUri,
        isCrossDeviceFlow,
        clientMetadata ?: from.clientMetadata
    ) {
        this.scope = OidcScopeWrapper.Set(scope = listOf(OidcScope.OPENID))
        this.claims = from.claims
        this.id = from.id
    }
}