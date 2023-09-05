package foundation.mee.android_client.models

import foundation.mee.android_client.R

enum class MeeSidebarOption(val title: Int, val icon: Int) {
    SETTINGS(
        R.string.sidebar_settings_title,
        R.drawable.ic_cog
    ),
    SEND_FEEDBACK(
        R.string.sidebar_send_feedback_title, R.drawable.ic_paper_airplane,
    )
}