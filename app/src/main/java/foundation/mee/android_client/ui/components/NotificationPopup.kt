package foundation.mee.android_client.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.ui.theme.DurationPopupBackground
import foundation.mee.android_client.ui.theme.GrayText
import foundation.mee.android_client.ui.theme.NotificationBackground
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun NotificationPopup(
    message: String,
    primaryButtonTitle: String,
    onNextPrimaryButton: () -> Unit
) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.clip(
                RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp, bottomEnd = 4.dp, bottomStart = 4.dp)
            )
                .background(color = NotificationBackground)
                .heightIn(min = 32.dp)

        ) {
            Text(
                text = message,
                fontFamily = publicSansFamily,
                fontWeight = FontWeight(400),
                color = GrayText,
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 14.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier.padding(top = 14.dp)
            ) {
                Spacer(Modifier.weight(1f))
                PopupButton(
                    title = primaryButtonTitle,
                ) {
                    onNextPrimaryButton()
                }
            }
        }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
fun NotificationPopupPreview() {
    NotificationPopup(message = "Test message", primaryButtonTitle = "Dismiss") {

    }
}