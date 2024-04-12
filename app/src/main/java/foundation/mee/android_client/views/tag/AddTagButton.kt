package foundation.mee.android_client.views.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.DarkText
import foundation.mee.android_client.ui.theme.InactiveBorder
import foundation.mee.android_client.ui.theme.publicSansFamily

val shape = RoundedCornerShape(4.dp)

@Composable
fun AddTagButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .sizeIn(minHeight = 56.dp)
            .fillMaxWidth()
            .clickableWithoutRipple { onClick() }
            .background(Color.Transparent, shape)
            .border(1.dp, InactiveBorder, shape)
    ) {
        Text(
            text = stringResource(id = R.string.add_tag_button_text),
            fontFamily = publicSansFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight(400),
            textAlign = TextAlign.Left,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
            color = DarkText,
            modifier = Modifier
                .padding(16.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_glass),
            contentDescription = null,
            tint = DarkText,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(width = 18.dp, height = 18.dp)
        )
    }
}

@Preview
@Composable
fun AddTagButtonPreview() {
    AddTagButton {

    }
}