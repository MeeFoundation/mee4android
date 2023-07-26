package foundation.mee.android_client.views.manage

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.navigation.Navigator

@Composable
fun ManageConnection(
    manageConnectionViewModel: ManageConnectionViewModel = hiltViewModel(),
    navigator: Navigator = hiltViewModel<NavViewModel>().navigator
) {
    val loadState = manageConnectionViewModel.screenData.collectAsState()

    when (loadState.value) {
        is ConnectionDataState.None -> navigator.navigateToMainScreen()
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