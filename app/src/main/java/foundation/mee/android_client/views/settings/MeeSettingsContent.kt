package foundation.mee.android_client.views.settings

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeSettingsEntry
import foundation.mee.android_client.ui.components.advancedShadow
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.*

@Composable
fun MeeSettingsContent(
    modifier: Modifier = Modifier
) {
    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val settingsLock = remember {
        listOf(
            MeeSettingsEntry(
                R.drawable.settings_guy,
                R.drawable.arrow_right,
                R.string.settings_entry_credentials_title,
                R.string.settings_entry_credentials_desc,
                true,
            ) {
                context.startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))
            }
        )
    }

    val settingsData = remember {
        listOf(
            MeeSettingsEntry(
                R.drawable.database,
                R.drawable.trash,
                R.string.settings_delete_user_data,
                R.string.settings_delete_user_data_desc,
                true,
            ) {
                openDialog.value = true
            }
        )
    }


    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .padding(bottom = 16.dp),
    ) {
        MeeSettingsListView(R.string.settings_lock_type_title, settingsLock)
        Divider(
            color = LabelLightSecondary,
            thickness = 0.5.dp,
            modifier = Modifier
                .alpha(0.5f)
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
        MeeSettingsListView(R.string.settings_user_data_title, settingsData)
        Divider(
            color = LabelLightSecondary,
            thickness = 0.5.dp,
            modifier = Modifier
                .alpha(0.5f)
                .padding(horizontal = 16.dp)
        )
    }

    if (openDialog.value) {
        DeleteAllDataDialogFlow {
            openDialog.value = false
        }
    }
}