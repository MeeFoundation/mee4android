package foundation.mee.android_client.views.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.MeeCheckbox
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.DarkText
import foundation.mee.android_client.ui.theme.TextActive
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun TagSearchEntry(
    tag: String,
    isExactMatch: Boolean,
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = if (isExactMatch) 24.dp else 15.5.dp)
            .padding(start = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "#$tag",
            color = TextActive,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
            modifier = Modifier.weight(1f)
        )
        if (isExactMatch) {
            MeeCheckbox(checked = isChecked, onCheckedChange = {
                onClick()
            }, modifier = Modifier.padding(end = 39.dp))
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 24.dp)
                    .clickableWithoutRipple {
                        onClick()
                    }
            ) {
                Text(
                    text = stringResource(id = R.string.add_tag_text),
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight(500),
                    color = DarkText,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_plus
                    ),
                    contentDescription = null,
                    tint = DarkText,
                    modifier = Modifier
                        .width(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchTagEntryPreview() {
    TagSearchEntry(
        tag = "Entertainment",
        isExactMatch = false,/* isChecked = true, */
        onClick = {})
}
