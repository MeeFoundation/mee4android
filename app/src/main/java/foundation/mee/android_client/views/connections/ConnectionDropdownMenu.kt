package foundation.mee.android_client.views.connections

import androidx.compose.runtime.Composable
import foundation.mee.android_client.models.ManageConnectorMenuOption
import foundation.mee.android_client.ui.components.MeeDropdownMenu

@Composable
fun ConnectionDropdownMenu(
    isDropdownOpen: Boolean,
    onClickMenu: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    MeeDropdownMenu(
        isDropdownOpen = isDropdownOpen,
        onClickMenu = onClickMenu,
        options = enumValues<ManageConnectorMenuOption>(),
        onClickMenuItem = {
            when (it) {
                ManageConnectorMenuOption.DELETE -> {
                    onDelete()
                }
            }
        })
}