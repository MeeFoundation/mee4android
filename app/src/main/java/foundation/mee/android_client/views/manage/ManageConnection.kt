package foundation.mee.android_client.views.manage

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.navigation.MeeDestinations
import foundation.mee.android_client.navigation.NavViewModel

@Composable
fun ManageConnection(
    manageConnectionViewModel: ManageConnectionViewModel = hiltViewModel(),
    navViewModel: NavViewModel = hiltViewModel()
) {
    val loadState = manageConnectionViewModel.screenData.collectAsState()
    val navigator = navViewModel.navigator

    when (loadState.value) {
        is ConnectionDataState.None -> navigator.navigate(MeeDestinations.CONNECTIONS.route)
        is ConnectionDataState.Success -> {
            val pair = (loadState.value as ConnectionDataState.Success).data
            Scaffold(topBar = {
                ManageConnectionScreenTitle { navigator.popBackStack() }
            }) { padding ->
                ManageConnectionContent(
                    modifier = Modifier.padding(padding),
                    meeConnection = pair.first,
                    meeContext = pair.second,
                    onRemoveConnection = {
                        manageConnectionViewModel.removeConnection(it, navigator)
                    }
                )
            }
        }
    }
}