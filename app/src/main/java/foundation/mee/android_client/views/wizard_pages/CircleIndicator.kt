package foundation.mee.android_client.views.wizard_pages

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

@Composable
fun CircleIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke
) {
    val diameterOffset = stroke.width / 2
//    val arcDimen = size.width - 2 * diameterOffset
//    drawArc(
//        color = color,
//        startAngle = startAngle,
//        sweepAngle = sweep,
//        useCenter = false,
//        topLeft = Offset(diameterOffset, diameterOffset),
//        size = Size(arcDimen, arcDimen),
//        style = stroke
//    )
}

@Preview(showBackground = true)
@Composable
fun TestCircularIndicator(
    progress: Float = 0.5f,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth,
    backgroundColor: Color = Color.Transparent,
    strokeCap: StrokeCap = StrokeCap.Butt,
    size: Dp = 540.dp
) {
    val coercedProgress = progress.coerceIn(0f, 1f)
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = strokeCap)
    }
    Canvas(
        modifier
            .progressSemantics(coercedProgress)
            .size(size)
    ) {
//        drawCircle(Color.Blue, radius = 300.dp.toPx())
        // Start at 12 O'clock
        val startAngle = 270f
        val sweep = coercedProgress * 360f
//        drawCircularIndicatorBackground(backgroundColor, stroke)
        val diameterOffset = stroke.width / 2
//        val arcDimen = size.width - 2 * diameterOffset
        drawArc(
            color = backgroundColor,
            startAngle = startAngle,
            sweepAngle = sweep,
            useCenter = false,
//            topLeft = Offset(diameterOffset, diameterOffset),
            size = Size(size.toPx(), size.toPx()),
            style = stroke
        )

        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = sweep,
            useCenter = false,
//            topLeft = Offset(diameterOffset, diameterOffset),
//            size = Size(size.width, size.width),
            size = Size(size.toPx(), size.toPx()),
//            size = Size(arcDimen, arcDimen),
            style = stroke
        )


    }




    // [START android_compose_graphics_canvas_translate]
//    Canvas(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        drawCircle(Color.Blue, radius = 300.dp.toPx())
////        translate(left = 100f, top = -300f) {
////            drawCircle(Color.Blue, radius = 200.dp.toPx())
////        }
//    }
    // [END android_compose_graphics_canvas_translate]
}