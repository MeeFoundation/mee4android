package foundation.mee.android_client.models

import uniffi.mee_agent.OidcScope
import uniffi.mee_agent.OidcScopeWrapper
import uniffi.mee_agent.Url

// TODO: Think about removing "" and adding Option type
data class ConsentRequest(
    val id: String,
    val scope: OidcScopeWrapper,
    val claims: List<ConsentRequestClaim>,
    val clientId: String = "",
    val nonce: String = "",
    val redirectUri: Url = "",
    val isCrossDeviceFlow: Boolean = false,
    var presentationDefinition: String? = "",
    val clientMetadata: PartnerMetadata
) {
    // TODO: check for shallow-copy / deep-copy later on
    constructor(
        from: MeeContext,
        clientId: String = "",
        nonce: String = "",
        redirectUri: String = "",
        isCrossDeviceFlow: Boolean = false,
        clientMetadata: PartnerMetadata? = null
    ) : this(
        from.id,
        OidcScopeWrapper.Set(scope = listOf(OidcScope.OPENID)),
        from.claims,
        clientId,
        nonce,
        redirectUri,
        isCrossDeviceFlow,
        "",
        clientMetadata ?: from.clientMetadata
    )
}
