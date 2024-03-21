package foundation.mee.android_client.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import foundation.mee.android_client.R

@Composable
fun MeeCheckbox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Crossfade(targetState = checked, animationSpec = tween(100), label = "") { isChecked ->
        if (isChecked) {
            Image(
                painter = painterResource(id = R.drawable.ic_is_checked),
                contentDescription = null,
                modifier = modifier.clickableWithoutRipple {
                    onCheckedChange()
                })
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_checkbox_container),
                contentDescription = null,
                modifier.clickableWithoutRipple {
                    onCheckedChange()
                })
        }
    }
}

@Composable
@Preview
fun PreviewMeeCheckbox() {
    var isChecked by remember {
        mutableStateOf(false)
    }
    Column {
        MeeCheckbox(
            checked = isChecked
        ) {
            isChecked = !isChecked
        }
    }
}