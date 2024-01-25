package foundation.mee.android_client.utils

import foundation.mee.android_client.models.ConsentRequest
import foundation.mee.android_client.models.ConsentRequestClaim
import foundation.mee.android_client.models.PartnerMetadata
import uniffi.mee_agent.*
import uniffi.mee_agent.OidcClaimParams


fun OidcAuthRequest(from: ConsentRequest): OidcAuthRequest {
    val claims = OidcRequestClaimsWrapper(null, emptyMap())
    mapIdToken(claims, from)

    return OidcAuthRequest(
        scope = from.scope,
        claims = claims,
        clientMetadata = from.clientMetadata?.let { OidcClientMetadata(it) },
        nonce = from.nonce,
        clientId = from.clientId,
        redirectUri = from.redirectUri,
        responseUri = from.responseUri,
        state = from.state,
        presentationDefinition = from.presentationDefinition,
        clientIdScheme = from.clientIdScheme,
        presentationDefinitionUri = from.presentationDefinitionUri,
        responseType = from.responseType,
        responseMode = from.responseMode
    )

}

fun OidcClientMetadata(from: PartnerMetadata): OidcClientMetadata =
    OidcClientMetadata(
        from.type.name,
        from.name,
        from.logoUrl,
        from.contacts,
        from.jwks,
        from.jwksUri,
        from.idTokenSignedResponseAlg,
        from.idTokenEncryptedResponseAlg,
        from.idTokenEncryptedResponseEnc,
        from.subjectSyntaxTypesSupported
    )


fun mapIdToken(claims: OidcRequestClaimsWrapper, from: ConsentRequest) {
    claims.idToken = from.claims.associate { x -> Pair(x.code, OidcClaimParams(x)) }
}


fun OidcClaimParams(from: ConsentRequestClaim): OidcClaimParams =
    OidcClaimParams(
        from.isRequired,
        from.attributeType,
        from.name,
        from.type.name,
        from.order,
        if (from.retentionDuration == RetentionDuration.WHILE_USING_APP) RetentionDuration.WHILE_USING_APP
        else RetentionDuration.UNTIL_CONNECTION_DELETION,
        from.businessPurpose,
        from.isSensitive,
        from.value,
    )