package foundation.mee.android_client.views.tag

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset

enum class TrailingElementState {
    SHOW_MORE,
    SHOW_LESS,
    NONE
}

@Composable
fun MeeFlowRow(
    spacedBy: Dp?,
    modifier: Modifier = Modifier,
    trailingElementState: TrailingElementState = TrailingElementState.NONE,
    maxRowCount: Int?,
    showMore: @Composable (Int) -> Unit,
    showLess: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    SubcomposeLayout(
        modifier = modifier
    ) { constraints ->
        val layoutConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val spacedByValue = spacedBy?.toPx()?.toInt() ?: 0
        var currentOffset = IntOffset.Zero
        val maxWidth = layoutConstraints.maxWidth
        val measurableList = subcompose("content", content)
        val calculateShowMorePlaceable = { remainingCount: Int, minHeight: Int ->
            subcompose(remainingCount) {
                showMore(remainingCount)
            }[0].measure(layoutConstraints.copy(minHeight = minHeight))
        }
        var remainingCount = measurableList.size
        val placeableToOffset = mutableListOf<Pair<Placeable, IntOffset>>()
        var nextRow = 0

        if ((maxRowCount ?: 0) <= 0 || measurableList.isEmpty()) {
            return@SubcomposeLayout layout(0, 0) {}
        }
        for (measurable in measurableList) {
            val placeable = measurable.measure(layoutConstraints)

            if (currentOffset.x + placeable.width > maxWidth) {
                nextRow += 1
                val nextRowVerticalOffset = currentOffset.y + placeable.height + spacedByValue

                if (maxRowCount != null && nextRow >= maxRowCount && trailingElementState != TrailingElementState.SHOW_MORE) {
                    var showMorePlaceable =
                        calculateShowMorePlaceable(remainingCount, placeable.height)

                    while (currentOffset.x + showMorePlaceable.width > maxWidth) {
                        val removed = placeableToOffset.removeLast()
                        currentOffset = removed.second
                        remainingCount += 1
                        showMorePlaceable =
                            calculateShowMorePlaceable(remainingCount, placeable.height)
                    }

                    placeableToOffset.add(showMorePlaceable to currentOffset)
                    break
                }

                currentOffset = currentOffset.copy(x = 0, y = nextRowVerticalOffset)
            }

            placeableToOffset.add(placeable to currentOffset)
            remainingCount -= 1

            currentOffset =
                currentOffset.copy(x = currentOffset.x + placeable.width + spacedByValue)
        }

        if (maxRowCount != null && nextRow >= maxRowCount && trailingElementState == TrailingElementState.SHOW_MORE) {
            val showLessPlaceable = subcompose("showLess") {
                showLess()
            }[0].measure(layoutConstraints.copy(minHeight = placeableToOffset[0].first.height))

            val maxWidthIsOverflowed = { currentOffset.x + showLessPlaceable.width > maxWidth }
            if (maxWidthIsOverflowed()) {
                val nextRowVerticalOffset =
                    currentOffset.y + showLessPlaceable.height + spacedByValue
                currentOffset = currentOffset.copy(x = 0, y = nextRowVerticalOffset)
            }

            placeableToOffset.add(showLessPlaceable to currentOffset)
        }

        layout(
            width = maxOf(constraints.minWidth,
                placeableToOffset.maxOfOrNull { it.first.width + it.second.x } ?: 0),
            height = maxOf(constraints.minHeight,
                placeableToOffset.maxOfOrNull { it.first.height + it.second.y } ?: 0),
        ) {
            placeableToOffset.forEach {
                val (placeable, offset) = it
                placeable.place(offset.x, offset.y)
            }
        }
    }
}