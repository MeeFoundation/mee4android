package foundation.mee.android_client.views.manage

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.navigation.Navigator
import foundation.mee.android_client.ui.components.MeeTopAppBar
import foundation.mee.android_client.views.tag.TagSearchViewModel

@Composable
fun ManageConnection(
    manageConnectionViewModel: ManageConnectionViewModel = hiltViewModel(),
    tagSearchViewModel: TagSearchViewModel = hiltViewModel(),
    navigator: Navigator = hiltViewModel<NavViewModel>().navigator
) {
    val loadState = manageConnectionViewModel.screenData.collectAsState()

    when (loadState.value) {
        is ConnectionDataState.None -> navigator.navigateToMainScreen()
        is ConnectionDataState.Success -> {
            val manageConnectionData = (loadState.value as ConnectionDataState.Success).data
            tagSearchViewModel.addTagsToSelected(manageConnectionData.meeConnection.tags)
            Scaffold(drawerElevation = 0.dp, topBar = {
                MeeTopAppBar(title = R.string.manage_connection_title) { navigator.popBackStack() }
            }) { padding ->
                ManageConnectionContent(
                    modifier = Modifier.padding(padding),
                    manageConnectionData = manageConnectionData,
                    onRemoveConnector = {
                        manageConnectionViewModel.removeConnector(it)
                        manageConnectionViewModel.updateViewmodel()
                    },
                    onRemoveConnection = {
                        manageConnectionViewModel.removeConnection(it, navigator)
                    }
                )
            }
        }
    }
}