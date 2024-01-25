package foundation.mee.android_client.models

import foundation.mee.android_client.views.manage.ConsentEntriesType

data class ManageConnectionData(
    val meeConnection: MeeConnection,
    val connectorToEntries: List<ConnectorToEntries>
)


data class ConnectorToEntries(
    val meeConnector: MeeConnector,
    val consentEntriesType: ConsentEntriesType
)