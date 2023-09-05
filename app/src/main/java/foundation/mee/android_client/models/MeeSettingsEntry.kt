package foundation.mee.android_client.models


data class MeeSettingsEntry(
    val icon: Int,
    val title: Int,
    val description: Int? = null,
    val transition: Boolean? = null,
    var onClick: () -> Unit = {}
)