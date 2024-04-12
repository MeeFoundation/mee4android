package foundation.mee.android_client.views.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.DarkText

@Composable
fun TagSearchTopBar(
    onCancelClick: () -> Unit,
    textFieldValue: String,
    onChangeText: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector =
            ImageVector.vectorResource(
                id = R.drawable.back
            ),
            contentDescription = null,
            tint = DarkText,
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .clickableWithoutRipple
                { onCancelClick() }
        )
        TagSearchField(
            textFieldValue = textFieldValue,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            onChangeText(it)
        }
    }
}

@Preview
@Composable
fun PreviewTagSearchTopBar() {
    TagSearchTopBar(onCancelClick = {}, "") {}
}