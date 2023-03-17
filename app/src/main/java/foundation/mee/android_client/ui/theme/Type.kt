package foundation.mee.android_client.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R

val publicSansFamily = FontFamily(
    Font(R.font.publicsans_medium, FontWeight.Medium),
    Font(R.font.publicsans_semibold, FontWeight.SemiBold),
    Font(R.font.publicsans_regular, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h5 = TextStyle(
        fontFamily = publicSansFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp
    ),
    h6 = TextStyle(
        fontFamily = publicSansFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    caption = TextStyle(
        fontFamily = publicSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
