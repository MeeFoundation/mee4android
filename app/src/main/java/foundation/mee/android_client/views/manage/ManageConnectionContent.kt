package foundation.mee.android_client.views.manage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeConnection
import foundation.mee.android_client.models.MeeContext
import foundation.mee.android_client.ui.components.Expander
import foundation.mee.android_client.ui.components.advancedShadow
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.views.connections.PartnerEntry
import foundation.mee.android_client.views.consent.ConsentEntry

@Composable
fun ManageConnectionContent(
    modifier: Modifier = Modifier,
    meeContext: MeeContext?,
    meeConnection: MeeConnection,
    onRemoveConnection: (String) -> Unit
) {

    val state = rememberManageConnectionContentState(claims = meeContext?.attributes ?: listOf())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 10.dp)
    ) {
        PartnerEntry(
            connection = meeConnection,
            modifier = Modifier
                .padding(bottom = 48.dp, top = 16.dp)
                .border(border = BorderStroke(2.dp, MeeGreenPrimaryColor))
        )
        Expander(
            title = "Required info shared",
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp),
            isExpanded = state.isRequiredSectionOpened,
            onChangeExpanded = {
                state.isRequiredSectionOpened = !state.isRequiredSectionOpened
            }
        ) {
            state.requiredClaims.forEach {
                ConsentEntry(
                    it,
                    isReadOnly = true,
                    modifier = Modifier.padding(top = 16.dp, start = 3.dp)
                )
                Divider(
                    color = DefaultGray200,
                    thickness = 1.dp,
                )
            }
        }

        if (state.hasOptionalFields) {
            Expander(
                title = "Optional info shared",
                modifier = Modifier.padding(top = 16.dp),
                color = Color.White,
                isExpanded = state.isOptionalSectionOpened,
                onChangeExpanded = {
                    state.isOptionalSectionOpened = !state.isOptionalSectionOpened
                }
            ) {
                state.optionalClaims.forEach {
                    ConsentEntry(
                        it,
                        isReadOnly = true,
                        modifier = Modifier.padding(top = 16.dp, start = 3.dp)
                    )
                    Divider(
                        color = DefaultGray200,
                        thickness = 1.dp,
                    )
                }
            }
        }
        Row(
            Modifier
                .padding(top = 80.dp, bottom = 16.dp)
                .advancedShadow(
                    alpha = 0.1f,
                    shadowBlurRadius = 64.dp,
                    offsetX = 0.dp,
                    offsetY = 8.dp
                )
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .clickableWithoutRipple
                { onRemoveConnection(meeConnection.id) }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 12.dp, end = 19.dp, bottom = 12.dp)
            ) {
                Text(
                    text = "Delete Connection",
                    fontFamily = publicSansFamily,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    color = DefaultRedLight,
                )
                Icon(
                    imageVector =
                    ImageVector.vectorResource(
                        id = R.drawable.ic_trash
                    ),
                    contentDescription = null,
                    tint = DefaultRedLight,
                    modifier = Modifier.width(17.dp)
                )
            }
        }
    }
}