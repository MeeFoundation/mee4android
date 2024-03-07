package foundation.mee.android_client.views.search

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.models.SearchRecognitionListener
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.DarkText
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.PartnerEntryOnBackgroundColor
import foundation.mee.android_client.ui.theme.TextActive
import foundation.mee.android_client.ui.theme.publicSansFamily
import foundation.mee.android_client.utils.showConsentToast

@Composable
fun ConnectionsSearchField(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val textFieldValue by searchViewModel.searchState.collectAsState()
    val isSpeaking by searchViewModel.isSpeaking.collectAsState()

    val recognitionListener by lazy {
        SearchRecognitionListener(
            context,
            onChangeIsSpeaking = { newVal -> searchViewModel.onChangeIsSpeaking(newVal) },
            onReceiveResult = { result -> searchViewModel.onChange(result) })
    }

    val requestRecordAudioPermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                recognitionListener.startListening()
            } else {
                showConsentToast(context, R.string.permission_not_granted)
            }
        }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(value = textFieldValue, onValueChange = {
                searchViewModel.onChange(it)
            }, textStyle = TextStyle(
                fontFamily = publicSansFamily, fontSize = 16.sp, fontWeight = FontWeight(400),
                textAlign = TextAlign.Left,
                color = TextActive
            ), modifier = Modifier.padding(start = 16.dp),
                decorationBox = { innerTextField ->
                    if (textFieldValue.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_connections_text),
                            fontFamily = publicSansFamily,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(400),
                            color = DarkText,
                            textAlign = TextAlign.Left
                        )
                    }
                    innerTextField()
                })
        }
        if (searchViewModel.searchState.collectAsState().value != "") Icon(
            imageVector = ImageVector.vectorResource(
                id = R.drawable.closeicon,
            ),
            contentDescription = null,
            tint = if (!isSpeaking) PartnerEntryOnBackgroundColor else MeeGreenPrimaryColor,
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .clickableWithoutRipple {
//                    if (isSpeaking) {
//                        recognitionListener.stopListening()
//                    } else {
//                        requestRecordAudioPermission.launch(Manifest.permission.RECORD_AUDIO)
//                    }
                    searchViewModel.onChange("")
                }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewConnectionsSearchField() {
    MeeIdentityAgentTheme {
        ConnectionsSearchField()
    }
}
