package foundation.mee.android_client.models

import uniffi.mee_agent.*


val consentRequestMock = ConsentRequest(
    "https://mee.foundation/",
    OidcScopeWrapper.Set(scope = listOf(OidcScope.OPENID)),
    listOf(
        ConsentRequestClaim(
            id = "First Name",
            name = "First Name",
            code = "name",
            attributeType = "https://schema.org/name",
            businessPurpose = null,
            isSensitive = true,
            value = null,
            retentionDuration = RetentionDuration.EPHEMERAL,
            isRequired = true,
            type = ConsentEntryType.string
        ),
        ConsentRequestClaim(
            id = "Email",
            name = "Email",
            code = "email",
            attributeType = "https://schema.org/email",
            businessPurpose = null,
            isSensitive = true,
            value = null,
            retentionDuration = RetentionDuration.EPHEMERAL,
            isRequired = false,
            type = ConsentEntryType.email
        ),
        ConsentRequestClaim(
            id = "Email",
            name = "Email",
            code = "email",
            attributeType = "https://schema.org/email",
            businessPurpose = null,
            isSensitive = true,
            value = null,
            retentionDuration = RetentionDuration.EPHEMERAL,
            isRequired = true,
            type = ConsentEntryType.email
        )
    ),
    "https://mee.foundation/",
    "4222140059d96df6706e6f3c28ee52a900a21464c21bab4f29881241f427e538",
    "https://mee.foundation/",
    false,
    null,
    PartnerMetadata(
        name = "Mee Foundation",
        displayUrl = "Mee Foundation",
        logoUrl = "https://mee.foundation/favicon.png",
        type = ClientType.WEB,
        contacts = listOf(),
        jwks = null
    )
)
