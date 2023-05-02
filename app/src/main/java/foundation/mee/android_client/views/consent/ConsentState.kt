package foundation.mee.android_client.views.consent

import foundation.mee.android_client.models.ConsentRequest
import foundation.mee.android_client.models.PartnerMetadata

data class ConsentState(
    // todo unpack
    val consent: ConsentRequest = emptyConsentRequest
)

val emptyConsentRequest = ConsentRequest(
    claims = listOf(),
    clientMetadata = PartnerMetadata(name = "", displayUrl = "", logoUrl = "", contacts = listOf()),
    nonce = "",
    clientId = "",
    redirectUri = "",
    presentationDefinition = "",
    isCrossDeviceFlow = false
)
