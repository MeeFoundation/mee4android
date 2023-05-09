package foundation.mee.android_client.ui.components

import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import foundation.mee.android_client.ui.theme.DefaultGray200
import foundation.mee.android_client.ui.theme.MeePrimary
import foundation.mee.android_client.ui.theme.PartnerEntryBackgroundColor

@Composable
fun Toggle(isOn: Boolean, onChange: () -> Unit, modifier: Modifier = Modifier) {
    Switch(
        modifier = modifier,
        checked = isOn,
        onCheckedChange = { onChange() },
        colors = SwitchDefaults.colors(
            checkedTrackColor = MeePrimary,
            uncheckedTrackColor = DefaultGray200,
            checkedThumbColor = PartnerEntryBackgroundColor,
            uncheckedThumbColor = PartnerEntryBackgroundColor,
            checkedTrackAlpha = 1.0f,
            uncheckedTrackAlpha = 1.0f
        ),
        interactionSource = NoRippleInteractionSource()
    )
}