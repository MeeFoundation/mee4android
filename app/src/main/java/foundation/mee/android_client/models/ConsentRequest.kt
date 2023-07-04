package foundation.mee.android_client.models

import uniffi.mee_agent.*

// TODO: Think about removing "" and adding Option type
data class ConsentRequest(
    val id: String,
    val scope: OidcScopeList,
    var claims: List<ConsentRequestClaim>, // TODO set var to val after scroll tests in ConsentViewModel
    val clientId: String = "",
    val nonce: String = "",
    val redirectUri: Url = "",
    val isCrossDeviceFlow: Boolean = false,
    var presentationDefinition: String? = "",
    val clientMetadata: PartnerMetadata,
    val responseType: OidcResponseType
) {
    // TODO: check for shallow-copy / deep-copy later on
    constructor(
        from: MeeContext,
        consentRequest: ConsentRequest
    ) : this(
        from.id,
        OidcScopeList(listOf()),
        from.attributes,
        consentRequest.clientId,
        consentRequest.nonce,
        consentRequest.redirectUri,
        consentRequest.isCrossDeviceFlow,
        "",
        consentRequest.clientMetadata,
        OidcResponseType.ID_TOKEN
    )

    constructor(
        from: RpAuthRequest,
        isCrossDeviceFlow: Boolean
    ) : this(
        from.redirectUri,
        from.scope,
        from.claims?.idToken?.let { claimsMapper(it) } ?: listOf(),
        from.clientId,
        from.nonce,
        from.redirectUri,
        isCrossDeviceFlow,
        from.presentationDefinition,
        PartnerMetadata(from.clientMetadata),
        from.responseType
    )
}

private fun claimsMapper(idToken: Map<String, OidcClaimParams?>): List<ConsentRequestClaim> {
    return idToken.entries.mapNotNull { (key, value) ->
        value?.let { ConsentRequestClaim(value, key) }
    }
}

