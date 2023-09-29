package foundation.mee.android_client.models

import uniffi.mee_agent.*

data class ConsentRequest(
    val id: String,
    val scope: OidcScopeList,
    val claims: List<ConsentRequestClaim>,
    val clientId: String = "",
    val nonce: String = "",
    val redirectUri: Url = "",
    val isCrossDeviceFlow: Boolean = false,
    var presentationDefinition: PresentationDefinition?,
    val clientMetadata: PartnerMetadata,
    val clientIdScheme: ClientIdScheme?,
    val presentation_definition_uri: String?,
    val responseType: OidcResponseType,
    val responseMode: OidcResponseMode?
) {
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
        consentRequest.presentationDefinition,
        consentRequest.clientMetadata,
        consentRequest.clientIdScheme,
        consentRequest.presentation_definition_uri,
        OidcResponseType.ID_TOKEN,
        consentRequest.responseMode
    )

    constructor(
        from: OidcAuthRequest,
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
        from.clientIdScheme,
        from.presentationDefinitionUri,
        from.responseType,
        from.responseMode
    )
}

private fun claimsMapper(idToken: Map<String, OidcClaimParams?>): List<ConsentRequestClaim> {
    return idToken.entries.mapNotNull { (key, value) ->
        value?.let { ConsentRequestClaim(value, key) }
    }.sortedBy { it.order }
}

