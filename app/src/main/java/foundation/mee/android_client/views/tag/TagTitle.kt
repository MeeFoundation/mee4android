package foundation.mee.android_client.views.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.ui.theme.DarkText
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun TagTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 18.dp, bottom = 10.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                fontFamily = publicSansFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight(600),
                textAlign = TextAlign.Left,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
                color = DarkText
            )
        }
    }
}

@Composable
@Preview
fun TagTitlePreview() {
    TagTitle(
        text = "Filter by Tag"
    )
}