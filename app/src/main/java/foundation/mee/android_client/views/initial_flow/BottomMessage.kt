package foundation.mee.android_client.views.initial_flow

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.material.Surface
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.DeclineButton
import foundation.mee.android_client.ui.components.MainButton
import foundation.mee.android_client.ui.components.PrimaryButton
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.utils.goToSystemSettings
import foundation.mee.android_client.utils.sendFeedback
import foundation.mee.android_client.views.MeeWhiteScreen

@Composable
fun BottomMessage(
    icon: Int?,
    iconSize: Dp?,
    buttonText: Int? = null,
    buttonColor: Color? = null,
    bottomMessageHeader: @Composable () -> Unit = {},
    message: Int,
    @SuppressLint("ModifierParameter") textModifier: Modifier = Modifier.padding(bottom = 64.dp),
    title: Int? = null,
    onNext: () -> Unit
) {
    BaseBottomMessage(
        icon = icon,
        iconSize = iconSize,
        title = title,
        message = message,
        textModifier = textModifier,
        bottomMessageHeader = bottomMessageHeader
    ) {
        MainButton(
            text = buttonText ?: R.string.continue_button_text,
            textColor = buttonColor
        ) { onNext() }
    }
}

@Composable
fun RestrictBottomMessage(
    icon: Int,
    iconSize: Dp,
    title: Int,
    message: Int,
    secondaryButtonTitle: Int? = null,
    primaryButtonTitle: Int? = null,
    onNextSecondaryButton: () -> Unit,
    onNextPrimaryButton: () -> Unit
) {
    BaseBottomMessage(
        icon = icon,
        iconSize = iconSize,
        title = title,
        message = message,
        textModifier = Modifier.padding(bottom = 64.dp)
    ) {
        DeclineButton(
            modifier = Modifier
                .padding(0.dp)
                .height(60.dp)
                .fillMaxWidth(),
            title = stringResource(secondaryButtonTitle ?: R.string.close_button_text),
            fontWeight = FontWeight(600),
            fontSize = 20.sp,
            backgroundColor = Color.Transparent
        ) {
            onNextSecondaryButton()
        }
        PrimaryButton(
            title = stringResource(primaryButtonTitle ?: R.string.settings_button_text),
            modifier = Modifier
                .padding(0.dp)
                .height(60.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight(600),
            fontSize = 20.sp,
            shape = RoundedCornerShape(size = 13.dp)
        ) {
            onNextPrimaryButton()
        }
    }
}


@Composable
fun BaseBottomMessage(
    icon: Int?,
    iconSize: Dp?,
    title: Int?,
    message: Int,
    bottomMessageHeader: @Composable () -> Unit = {},
    @SuppressLint("ModifierParameter") textModifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        color = DurationPopupBackground,
        modifier = Modifier.clip(RoundedCornerShape(topEnd = 13.dp, topStart = 13.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            bottomMessageHeader()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(bottom = 50.dp)
                    .padding(horizontal = 16.dp)
            ) {
                icon?.let {
                    Image(
                        painter = painterResource(icon),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(iconSize ?: 60.dp)
                            .padding(top = 8.dp)
                    )
                }
                title?.let {
                    Text(
                        text = stringResource(title),
                        fontFamily = publicSansFamily,
                        fontWeight = FontWeight(700),
                        color = Color.Black,
                        fontSize = 34.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Text(
                    text = stringResource(message),
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight(400),
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = textModifier.padding(top = 8.dp)
                )
                content()
            }
        }
    }
}

@Composable
fun ErrorMessage(
    title: Int,
    message: Int,
    primaryButtonTitle: Int,
    secondaryButtonTitle: Int?,
    onNextSecondaryButton: () -> Unit,
    onNext: () -> Unit
) {
    Box {
        MeeWhiteScreen(modifier = Modifier.zIndex(2f), isFaded = true)
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .zIndex(1f)
                .fillMaxHeight(),
        ) {
            RestrictBottomMessage(
                icon = R.drawable.mee_compatible_sign,
                iconSize = 60.dp,
                title = title,
                message = message,
                secondaryButtonTitle = secondaryButtonTitle,
                primaryButtonTitle = primaryButtonTitle,
                onNextSecondaryButton = onNextSecondaryButton
            ) {
                onNext()
            }
        }
    }
}

@Composable
fun MigrationErrorMessage() {
    val context = LocalContext.current
    val activity = (context as? Activity)
    ErrorMessage(
        title = R.string.init_agent_db_error_title,
        message = R.string.init_agent_db_error_message,
        primaryButtonTitle = R.string.settings_data_deletion_error_button_title,
        secondaryButtonTitle = R.string.close_button_text,
        onNextSecondaryButton = { activity?.finishAffinity() }
    ) {
        goToSystemSettings(context)
    }
}

@Composable
fun InitAgentErrorMessage() {
    val context = LocalContext.current
    ErrorMessage(
        title = R.string.init_agent_error_title,
        message = R.string.init_agent_error_message,
        primaryButtonTitle = R.string.settings_data_deletion_error_button_title,
        secondaryButtonTitle = R.string.sidebar_send_feedback_title,
        onNextSecondaryButton = { sendFeedback(context) }
    ) {
        goToSystemSettings(context)
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
fun BottomMessagePreview() {
    MeeIdentityAgentTheme {
        Box {
            MeeWhiteScreen(modifier = Modifier.zIndex(2f), isFaded = true)
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .zIndex(1f)
                    .fillMaxHeight(),
            ) {
                BottomMessage(
                    icon = R.drawable.mee_guy_icon,
                    iconSize = 60.dp,
                    title = R.string.biometry_initial_step_title,
                    message = R.string.biometry_initial_step_message,
                    onNext = {}
                )
            }
        }
    }
}