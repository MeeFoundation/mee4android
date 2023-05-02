package foundation.mee.android_client.models

import uniffi.mee_agent.*

// TODO: Think about removing "" and adding Option type
data class ConsentRequest(
    val id: String,
    val scope: OidcScopeWrapper,
    var claims: List<ConsentRequestClaim>,
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

    constructor(
        from: RpAuthRequest
    ) : this(
        from.redirectUri,
        from.scope,
        from.claims?.idToken?.let { claimsMapper(it) } ?: listOf(),
        from.clientId ?: "",
        from.nonce,
        from.redirectUri,
        false,
        from.presentationDefinition,
        PartnerMetadata(from.clientMetadata!!)
    )

    constructor(
        claims: List<ConsentRequestClaim>,
        clientMetadata: PartnerMetadata,
        nonce: String,
        clientId: String,
        redirectUri: String,
        presentationDefinition: String?,
        isCrossDeviceFlow: Boolean
    ) : this(
        scope = OidcScopeWrapper.Set(scope = listOf(OidcScope.OPENID)),
        claims = claims,
        clientMetadata = clientMetadata,
        nonce = nonce,
        clientId = clientId,
        redirectUri = redirectUri,
        presentationDefinition = presentationDefinition,
        isCrossDeviceFlow = isCrossDeviceFlow,
        id = redirectUri
    )
}

private fun claimsMapper(idToken: Map<String, OidcClaimParams?>): List<ConsentRequestClaim> {
    return idToken.entries.mapNotNull { (key, value) ->
        value?.let { ConsentRequestClaim(value, key) }
    }
}

