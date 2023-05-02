package foundation.mee.android_client.views.consent

import uniffi.mee_agent.RetentionDuration

// TODO id = name
val ConsentDurationOptions = listOf(
    ConsentDurationOption(
        "While using app",
        "While using app",
        "Shared with provider’s app during usage;\ndeleted by provider afterwards",
//        ConsentStorageDuration.WHILE_USING_APP
        RetentionDuration.WHILE_USING_APP
    ),
    ConsentDurationOption(
        "Until connection deletion",
        "Until connection deletion",
        "Shared with provider’s app until connection\nis deleted; removed by provider afterwards",
//        ConsentStorageDuration.UNTIL_CONNECTION_DELETION
        RetentionDuration.UNTIL_CONNECTION_DELETION
    )
)