package foundation.mee.android_client.helpers

import foundation.mee.android_client.models.ConsentRequest
import foundation.mee.android_client.models.ConsentRequestClaim
import foundation.mee.android_client.models.PartnerMetadata
import uniffi.mee_agent.*
import uniffi.mee_agent.OidcClaimParams


fun RpAuthRequest(from: ConsentRequest): RpAuthRequest {
    val claims = OidcRequestClaimsWrapper(null, emptyMap())
    mapIdToken(claims, from)

    return RpAuthRequest(
        from.scope,
        claims,
        OidcClientMetadata(from.clientMetadata),
        from.nonce,
        from.clientId,
        from.redirectUri,
        from.presentationDefinition
    )

}

fun OidcClientMetadata(from: PartnerMetadata): OidcClientMetadata =
    OidcClientMetadata(
        from.type.name.lowercase(), //TODO вопрос lowercase
        from.name,
        from.logoUrl,
        from.contacts,
        from.jwks
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
        if (from.retentionDuration == RetentionDuration.WHILE_USING_APP) RetentionDuration.WHILE_USING_APP
        else RetentionDuration.UNTIL_CONNECTION_DELETION,
        from.businessPurpose,
        from.isSensitive,
        from.value,
    )