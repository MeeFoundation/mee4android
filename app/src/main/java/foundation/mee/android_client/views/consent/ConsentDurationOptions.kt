package foundation.mee.android_client.views.consent

import foundation.mee.android_client.R
import uniffi.mee_agent.RetentionDuration

data class ConsentDurationOption(
    val name: Int,
    val description: Int,
    val value: RetentionDuration
)


val ConsentDurationOptions = listOf(
    ConsentDurationOption(
        R.string.consent_duration_while_using_name,
        R.string.consent_duration_while_using_desc,
        RetentionDuration.WHILE_USING_APP
    ),
    ConsentDurationOption(
        R.string.consent_duration_until_deletion_name,
        R.string.consent_duration_until_deletion_desc,
        RetentionDuration.UNTIL_CONNECTION_DELETION
    )
)