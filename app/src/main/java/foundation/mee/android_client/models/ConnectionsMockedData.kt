package foundation.mee.android_client.models

import foundation.mee.android_client.views.manage.ConsentEntriesType
import uniffi.mee_agent.OidcClientMetadata
import uniffi.mee_agent.RetentionDuration


val meConnectionMock = MeeConnection(
    id = "mee.foundation",
    name = "Mee Foundation"
)

val manageConnectionDataMock = ManageConnectionData(
    meeConnection = MeeConnection(
        id = "oldeyorktimes.com",
        name = "Olde York Times"
    ),
    connectorToEntries = listOf(
        ConnectorToEntries(
            meeConnector = MeeConnector(
                id = "41bc4ae8-8175-453b-8212-f8ddc3d51e61",
                name = "Olde York Times",
                otherPartyConnectionId = "oldeyorktimes.com",
                connectorProtocol = MeeConnectorProtocol.Siop(
                    value = SiopConnectorProtocol(
                        redirectUri = "https://oldeyorktimes.com/",
                        clientMetadata = PartnerMetadata(
                            name = "Olde York Times",
                            displayUrl = "Olde York Times",
                            logoUrl = "https://oldeyorktimes.com/favicon.png",
                            contacts = null,
                            jwks = null,
                            jwksUri = null,
                            idTokenSignedResponseAlg = null,
                            idTokenEncryptedResponseAlg = null,
                            idTokenEncryptedResponseEnc = null,
                            subjectSyntaxTypesSupported = null
                        ),
                        subjectSyntaxType = ConnectorProtocolSubject.DidKey(
                            value = ""
                        )
                    )
                )
            ),
            consentEntriesType = ConsentEntriesType.SiopClaims(
                value = listOf(
                    ConsentRequestClaim(
                        id = "Last Name",
                        name = "Last Name",
                        code = "lastName",
                        attributeType = "https://schema.org/name",
                        businessPurpose = null,
                        isSensitive = true,
                        value = "Test",
                        retentionDuration = RetentionDuration.UNTIL_CONNECTION_DELETION,
                        isRequired = true,
                        type = ConsentEntryType.string,
                        isOn = true,
                        isOpen = true,
                        order = null
                    ), ConsentRequestClaim(
                        id = "Email",
                        name = "Email",
                        code = "email",
                        attributeType = "https://schema.org/email",
                        businessPurpose = null,
                        isSensitive = true,
                        value = "a",
                        retentionDuration = RetentionDuration.UNTIL_CONNECTION_DELETION,
                        isRequired = true,
                        type = ConsentEntryType.email,
                        isOn = true,
                        isOpen = true,
                        order = null
                    ), ConsentRequestClaim(
                        id = "First Name",
                        name = "First Name",
                        code = "firstName",
                        attributeType = "https://schema.org/name",
                        businessPurpose = null,
                        isSensitive = true,
                        value = "a",
                        retentionDuration = RetentionDuration.UNTIL_CONNECTION_DELETION,
                        isRequired = true,
                        type = ConsentEntryType.string,
                        isOn = true,
                        isOpen = true,
                        order = null
                    )
                )
            )
        ),
        ConnectorToEntries(
            meeConnector = MeeConnector(
                id = "181c4d27-0e6c-4497-a57b-dc5f4aa97817",
                name = "The Olde York Times",
                otherPartyConnectionId = "oldeyorktimes.com",
                connectorProtocol = MeeConnectorProtocol.Siop(
                    value = SiopConnectorProtocol(
                        redirectUri = "https://oldeyorktimes.com/#/login",
                        clientMetadata = PartnerMetadata(
                            name = "The Olde York Times",
                            displayUrl = "The Olde York Times",
                            logoUrl = "https://oldeyorktimes.com/favicon.png",
                            contacts = null,
                            jwks = null,
                            jwksUri = null,
                            idTokenSignedResponseAlg = null,
                            idTokenEncryptedResponseAlg = null,
                            idTokenEncryptedResponseEnc = null,
                            subjectSyntaxTypesSupported = null
                        ),
                        subjectSyntaxType = ConnectorProtocolSubject.DidKey(
                            value = ""
                        )
                    )
                )
            ),
            consentEntriesType = ConsentEntriesType.SiopClaims(
                value = listOf(
                    ConsentRequestClaim(
                        id = "First name",
                        name = "First name",
                        code = "first_name",
                        attributeType = "https://schema.org/name",
                        businessPurpose = null,
                        isSensitive = true,
                        value = "df",
                        retentionDuration = RetentionDuration.WHILE_USING_APP,
                        isRequired = true,
                        type = ConsentEntryType.string,
                        isOn = true,
                        isOpen = true,
                        order = null
                    ), ConsentRequestClaim(
                        id = "Email",
                        name = "Email",
                        code = "email",
                        attributeType = "https://schema.org/email",
                        businessPurpose = null,
                        isSensitive = true,
                        value = "a",
                        retentionDuration = RetentionDuration.WHILE_USING_APP,
                        isRequired = true,
                        type = ConsentEntryType.email,
                        isOn = true,
                        isOpen = true,
                        order = null
                    ), ConsentRequestClaim(
                        id = "Last name",
                        name = "Last name",
                        code = "last_name",
                        attributeType = "https://schema.org/name",
                        businessPurpose = null,
                        isSensitive = true,
                        value = "s",
                        retentionDuration = RetentionDuration.WHILE_USING_APP,
                        isRequired = true,
                        type = ConsentEntryType.string,
                        isOn = true,
                        isOpen = true,
                        order = null
                    ), ConsentRequestClaim(
                        id = "Credit or Debit card",
                        name = "Credit or Debit card",
                        code = "card",
                        attributeType = "https://schema.org/name",
                        businessPurpose = null,
                        isSensitive = true,
                        value = null,
                        retentionDuration = RetentionDuration.WHILE_USING_APP,
                        isRequired = false,
                        type = ConsentEntryType.card,
                        isOn = false,
                        isOpen = true,
                        order = null
                    )
                )
            )
        )
    )
)
