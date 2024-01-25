package foundation.mee.android_client.models

import uniffi.mee_agent.*

data class ConsentRequest(
    val id: String,
    val scope: OidcScopeList?,
    val claims: List<ConsentRequestClaim>,
    val clientId: String = "",
    val nonce: String = "",
    val state: String?,
    val redirectUri: Url = "",
    val responseUri: Url?,
    val isCrossDeviceFlow: Boolean = false,
    var presentationDefinition: PresentationDefinition?,
    val clientMetadata: PartnerMetadata?,
    val clientIdScheme: ClientIdScheme?,
    val presentationDefinitionUri: String?,
    val responseType: OidcResponseType,
    val responseMode: OidcResponseMode?
) {
    constructor(
        from: MeeContext,
        consentRequest: ConsentRequest
    ) : this(
        id = from.id,
        scope = OidcScopeList(listOf()),
        claims = from.attributes,
        clientId = consentRequest.clientId,
        nonce = consentRequest.nonce,
        state = consentRequest.state,
        redirectUri = consentRequest.redirectUri,
        responseUri = consentRequest.responseUri,
        isCrossDeviceFlow = consentRequest.isCrossDeviceFlow,
        presentationDefinition = consentRequest.presentationDefinition,
        clientMetadata = consentRequest.clientMetadata,
        clientIdScheme = consentRequest.clientIdScheme,
        presentationDefinitionUri = consentRequest.presentationDefinitionUri,
        responseType = OidcResponseType.ID_TOKEN,
        responseMode = consentRequest.responseMode
    )

    constructor(
        from: OidcAuthRequest,
        isCrossDeviceFlow: Boolean
    ) : this(
        id = from.clientId,
        scope = from.scope,
        claims = from.claims?.idToken?.let { claimsMapper(it) } ?: listOf(),
        clientId = from.clientId,
        nonce = from.nonce,
        state = from.state,
        redirectUri = from.redirectUri ?: "",
        responseUri = from.responseUri,
        isCrossDeviceFlow = isCrossDeviceFlow,
        presentationDefinition = from.presentationDefinition,
        clientMetadata = from.clientMetadata?.let { PartnerMetadata(it) },
        clientIdScheme = from.clientIdScheme,
        presentationDefinitionUri = from.presentationDefinitionUri,
        responseType = from.responseType,
        responseMode = from.responseMode
    )
}

private fun claimsMapper(idToken: Map<String, OidcClaimParams?>): List<ConsentRequestClaim> {
    return idToken.entries.mapNotNull { (key, value) ->
        value?.let { ConsentRequestClaim(value, key) }
    }.sortedBy { it.order }
}

