package foundation.mee.android_client.views.connections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.ui.theme.InactiveBorder
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
    Crossfade(
        targetState = showSearch,
        animationSpec = tween(600)
    ) {
        Column {
            Column(
                modifier = Modifier
                    .background(if (it) Color.White else MeeGreenPrimaryColor)
                    .padding(vertical = 18.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
            ) {

                if (!it) ConnectionsMainTopBar(onClickMenu) {
                    searchViewModel.showSearchMenu()
                } else {
                    ConnectionsSearchTopBar {
                        searchViewModel.hideSearchMenu()
                        searchViewModel.onCancelClick()
                    }
                }
            }
            if (it) Divider(
                color = InactiveBorder,
                thickness = 1.dp,
                modifier = Modifier
                    .alpha(0.5f)
            )
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
