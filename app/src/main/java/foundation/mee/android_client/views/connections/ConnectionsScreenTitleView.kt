package foundation.mee.android_client.views.connections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.views.search.ConnectionsSearchTopBar
import foundation.mee.android_client.views.search.SearchViewModel

@Composable
fun ConnectionsScreenTitle(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onClickMenu: () -> Unit
) {
    val showSearch by searchViewModel.searchMenu.collectAsState()

    TopAppBar(
        backgroundColor = MeeGreenPrimaryColor,
        modifier = Modifier.fillMaxWidth(),
    ) {
        AnimatedVisibility(
            visible = showSearch,
            enter = fadeIn(animationSpec = tween(600)),
            exit = fadeOut()
        ) {
            ConnectionsSearchTopBar {
                searchViewModel.hideSearchMenu()
                searchViewModel.onCancelClick()
            }
        }
        ConnectionsMainTopBar(onClickMenu) {
            searchViewModel.showSearchMenu()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConnectionsScreenTitlePreview() {
    MeeIdentityAgentTheme {
        ConnectionsScreenTitle {}
    }
}
