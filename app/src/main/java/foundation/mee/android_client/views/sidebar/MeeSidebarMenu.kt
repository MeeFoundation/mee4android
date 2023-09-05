package foundation.mee.android_client.views.sidebar

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeSidebarOption
import foundation.mee.android_client.navigation.MeeDestinations
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.navigation.Navigator
import foundation.mee.android_client.ui.components.Sidebar

const val MEE_FOUNDATION_CONTACT = "contact@mee.foundation"

@Composable
fun MeeSidebarMenu(
    navigator: Navigator = hiltViewModel<NavViewModel>().navigator,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    Sidebar(options = enumValues(), onClick = {
        when (it) {
            MeeSidebarOption.SEND_FEEDBACK -> sendFeedback(context)
            MeeSidebarOption.SETTINGS -> navigator.navigate(MeeDestinations.SETTINGS.route)
        }
    }) {
        content()
    }
}

private fun sendFeedback(context: Context) {
    val mailUriPrefix = "mailto:"
    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("$mailUriPrefix$MEE_FOUNDATION_CONTACT")
    }
    context.startActivity(
        Intent.createChooser(
            emailIntent, context.getString(R.string.send_feedback_intent_title_text)
        )
    )
}
