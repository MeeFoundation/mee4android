package foundation.mee.android_client.views.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun RejectButton(action: () -> Unit = {}, title: String) {
    Button(
        onClick = action,
        border = BorderStroke(2.dp, MeeGreenPrimaryColor),
        shape = AbsoluteRoundedCornerShape(10.dp),
        modifier = Modifier
            .sizeIn(maxHeight = 51.dp)
            .fillMaxSize(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
    ) {
        Text(
            text = title,
            color = MeeGreenPrimaryColor,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            letterSpacing = 0.45.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RejectButtonPreview() {
    MeeIdentityAgentTheme() {
        RejectButton(title = "Get Started")
    }
}
