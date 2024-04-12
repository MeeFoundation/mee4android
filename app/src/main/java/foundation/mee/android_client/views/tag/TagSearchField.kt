package foundation.mee.android_client.views.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.DarkText
import foundation.mee.android_client.ui.theme.TextActive
import foundation.mee.android_client.ui.theme.publicSansFamily

val searchTagsStyle = TextStyle(
    fontFamily = publicSansFamily,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp,
    fontWeight = FontWeight(400),
    textAlign = TextAlign.Left,
)

@Composable
fun TagSearchField(
    textFieldValue: String,
    modifier: Modifier = Modifier,
    onChangeText: (String) -> Unit
) {

    val focusRequester = remember { FocusRequester() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(value = textFieldValue,
                onValueChange = {
                    onChangeText(it)
                },
                modifier = Modifier.focusRequester(focusRequester),
                textStyle = searchTagsStyle.copy(color = TextActive),
                decorationBox = { innerTextField ->
                    if (textFieldValue.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_tags_text),
                            style = searchTagsStyle,
                            color = DarkText
                        )
                    }
                    innerTextField()
                })
        }
        if (textFieldValue != "") {
            Icon(imageVector = ImageVector.vectorResource(
                id = R.drawable.closeicon,
            ),
                contentDescription = null,
                tint = DarkText,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clickableWithoutRipple {
                        onChangeText("")
                    })
        }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
@Preview
fun PreviewTagsSearchField() {
    TagSearchField("") {}
}