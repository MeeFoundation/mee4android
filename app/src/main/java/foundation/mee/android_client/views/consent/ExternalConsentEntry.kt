package foundation.mee.android_client.views.consent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.*

fun getExternalConsentEntryIconByName(name: String): Int {
    return when (name) {
        "familyName", "givenName", "name" -> R.drawable.ic_person
        "email" -> R.drawable.ic_letter
        else -> R.drawable.ic_key
    }
}

@Composable
fun ExternalConsentEntry(
    name: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box {
        Row(
            modifier = modifier
                .padding(start = 12.dp)
                .zIndex(0.5f)
        ) {
            Text(
                text = name,
                fontFamily = publicSansFamily,
                color = TextActive.copy(0.75f),
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(3.dp))
                    .padding(horizontal = 4.dp)
            )
        }

        Box(
            modifier = Modifier
                .padding(start = 0.dp, top = 8.dp)
                .clip(
                    RoundedCornerShape(4.dp)
                )
                .background(GrayText)
                .border(
                    1.dp,
                    InactiveCover.copy(alpha = 0.12f),
                    RoundedCornerShape(4.dp)
                )

        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BasicTextField(
                        readOnly = true,
                        value = value,
                        onValueChange = {

                        },
                        textStyle = TextStyle(
                            fontFamily = publicSansFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = TextActive.copy(0.75f),
                            textAlign = TextAlign.Left
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .weight(1f),
                        decorationBox = { innerTextField ->
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column(
                                    modifier = Modifier.padding(end = 16.dp),
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(
                                            id = getExternalConsentEntryIconByName(name),
                                        ),
                                        contentDescription = null,
                                        tint = TextActive.copy(0.38f),
                                        modifier = Modifier
                                            .height(16.dp)
                                    )
                                }
                                innerTextField()
                            }

                        }
                    )
                }

            }
        }
    }
}

@Composable
@Preview
fun PreviewExternalConsentEntry() {
    ExternalConsentEntry(
        name = "familyName",
        value = "Doe"
    )
}