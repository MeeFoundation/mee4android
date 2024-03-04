package foundation.mee.android_client.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.models.MeeSidebarOption
import foundation.mee.android_client.ui.theme.*

@Composable
fun Sidebar(
    options: Array<MeeSidebarOption>,
    onClick: (item: MeeSidebarOption) -> Unit,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp)) {
                    options.forEach { item ->
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 14.dp, vertical = 10.dp)
                                .clickableWithoutRipple {
                                    onClick(item)
                                },
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = null,
                                    tint = MeeGreenPrimaryColor,
                                    modifier = Modifier
                                        .size(width = 24.dp, height = 24.dp)
                                )
                                Text(
                                    text = stringResource(item.title),
                                    fontFamily = publicSansFamily,
                                    fontWeight = FontWeight(500),
                                    color = PartnerEntryOnBackgroundColor,
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }) {
        content()
    }
}