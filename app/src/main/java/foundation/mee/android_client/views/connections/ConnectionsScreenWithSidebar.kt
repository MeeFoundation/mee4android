package foundation.mee.android_client.views.connections

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import foundation.mee.android_client.views.sidebar.MeeSidebarMenu
import kotlinx.coroutines.launch

@Composable
fun ConnectionsScreenWithSidebar() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    MeeSidebarMenu(drawerState = drawerState) {
        ConnectionsScreen {
            scope.launch {
                drawerState.open()
            }
        }
    }
}