package foundation.mee.android_client.models

import foundation.mee.android_client.R

interface DropdownMenuOption {
    val title: Int
    val icon: Int
}

enum class ManageConnectorMenuOption(override val title: Int, override val icon: Int) :
    DropdownMenuOption {
    DELETE(R.string.delete_connection, R.drawable.trash),
}