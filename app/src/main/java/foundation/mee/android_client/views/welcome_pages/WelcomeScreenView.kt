package foundation.mee.android_client.views.welcome_pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.R
import foundation.mee.android_client.models.WelcomePageEnum
import foundation.mee.android_client.models.utils.*
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.MeeYellowColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {
    Surface(
        color = MeeYellowColor
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(28.dp),
            modifier = modifier
                .padding( // Figma paddings
                    top = 60.dp,
                    bottom = 52.dp
                )
        ) {
            val pageCount = size<WelcomePageEnum>()
            val pagerState = rememberPagerState()

            HorizontalPager(
                pageCount = pageCount,
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) { page ->
                WelcomePageSelector(page)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(start = 33.33.dp, end = 33.33.dp) // Figma paddings
            ) {
                val coroutineScope = rememberCoroutineScope()

                if (pagerState.currentPage != head<WelcomePageEnum>().pageNum) {
                    WelcomePageIconButton(
                        imageIcon = R.drawable.welcome_screen_chevron_left,
                        action = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    fromInt<WelcomePageEnum>(pagerState.currentPage)
                                        .previousOrFirst().pageNum
                                )
                            }
                        }
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )

                Row(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 4.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(pageCount) { iteration ->
                        if (pagerState.currentPage == iteration) {
                            WelcomePageIconBullet(
                                imageIcon = R.drawable.welcome_screen_bullet_wide,
                                width = 20.dp, height = 8.dp
                            )
                        } else {
                            WelcomePageIconBullet(
                                imageIcon = R.drawable.welcome_screen_bullet,
                                width = 8.dp, height = 8.dp
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )

                if (pagerState.currentPage != tail<WelcomePageEnum>().pageNum) {
                    WelcomePageIconButton(
                        imageIcon = R.drawable.welcome_screen_chevron_right,
                        action = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    fromInt<WelcomePageEnum>(pagerState.currentPage)
                                        .nextOrLast().pageNum
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PagerIndicatorPreview() {
    MeeIdentityAgentTheme {
        WelcomeScreen()
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812) // Figma design dimensions
@Composable
fun PagerIndicatorFigmaScreenPreview() {
    MeeIdentityAgentTheme {
        WelcomeScreen()
    }
}
