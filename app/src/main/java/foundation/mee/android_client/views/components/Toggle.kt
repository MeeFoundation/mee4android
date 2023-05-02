package foundation.mee.android_client.views.components

import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import foundation.mee.android_client.ui.theme.DefaultGray200
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.MeePrimary
import foundation.mee.android_client.ui.theme.PartnerEntryBackgroundColor

@Composable
fun Toggle(/*modifier: Modifier = Modifier, */isReadOnly: Boolean, onChange: () -> Unit) {
    //var isChecked by remember { mutableStateOf(true) }
    Switch(
        checked = !isReadOnly,
        onCheckedChange = { onChange() },
        colors = SwitchDefaults.colors(
            checkedTrackColor = MeePrimary,
            uncheckedTrackColor = DefaultGray200,
            checkedThumbColor = PartnerEntryBackgroundColor,
            uncheckedThumbColor = PartnerEntryBackgroundColor,
            checkedTrackAlpha = 1.0f,
            uncheckedTrackAlpha = 1.0f
        )
    )
}

@Preview
@Composable
fun TogglePreview() {
    MeeIdentityAgentTheme {
        Toggle(false, {})
    }
}