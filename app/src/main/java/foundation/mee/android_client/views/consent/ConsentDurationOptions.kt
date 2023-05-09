package foundation.mee.android_client.views.consent

import uniffi.mee_agent.RetentionDuration

data class ConsentDurationOption(
    val name: String,
    val description: String,
    val value: RetentionDuration
)


val ConsentDurationOptions = listOf(
    ConsentDurationOption(
        "While using app",
        "Shared with provider’s app during usage;\ndeleted by provider afterwards",
        RetentionDuration.WHILE_USING_APP
    ),
    ConsentDurationOption(
        "Until connection deletion",
        "Shared with provider’s app until connection\nis deleted; removed by provider afterwards",
        RetentionDuration.UNTIL_CONNECTION_DELETION
    )
)