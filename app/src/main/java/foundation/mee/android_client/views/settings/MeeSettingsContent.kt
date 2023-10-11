package foundation.mee.android_client.views.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeSettingsList
import foundation.mee.android_client.ui.components.advancedShadow
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.*

@Composable
fun MeeSettingsContent(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val settings = remember { MeeSettingsList(context).shared }
    val openDialog = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        MeeSettingsListView(R.string.settings_lock_type_title, settings)
        Spacer(Modifier.weight(1f))
        Row(
            Modifier
                .padding(top = 80.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
                .advancedShadow(
                    alpha = 0.1f,
                    shadowBlurRadius = 64.dp,
                    offsetX = 0.dp,
                    offsetY = 8.dp
                )
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .clickableWithoutRipple
                { openDialog.value = true }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 12.dp, end = 19.dp, bottom = 12.dp)
            ) {
                Text(
                    text = stringResource(R.string.settings_delete_user_data),
                    fontFamily = publicSansFamily,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    color = DefaultRedLight,
                )
                Icon(
                    imageVector =
                    ImageVector.vectorResource(
                        id = R.drawable.ic_trash
                    ),
                    contentDescription = null,
                    tint = DefaultRedLight,
                    modifier = Modifier.width(17.dp)
                )
            }
        }
    }

    if (openDialog.value) {
        DeleteAllDataDialogFlow {
            openDialog.value = false
        }
    }
}