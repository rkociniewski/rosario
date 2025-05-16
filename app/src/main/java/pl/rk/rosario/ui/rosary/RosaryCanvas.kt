package pl.rk.rosario.ui.rosary

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import pl.rk.rosario.enums.BeadType
import pl.rk.rosario.model.Bead
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Suppress("MagicNumber")
@Composable
fun RosaryCanvas(
    currentIndex: Int,
    modifier: Modifier = Modifier
) {
    val rosary = buildList {
        add(Bead(0, BeadType.CROSS))
        add(Bead(1, BeadType.LARGE))
        add(Bead(2, BeadType.SMALL))
        add(Bead(3, BeadType.SMALL))
        add(Bead(4, BeadType.SMALL))

        var actualIndex = 5
        repeat(5) {
            add(Bead(actualIndex++, BeadType.LARGE))
            repeat(10) {
                add(Bead(actualIndex++, BeadType.SMALL))
            }
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = size.minDimension / 3f
        val tailSpacing = 48f

        val crossY = centerY + radius + tailSpacing * 6.5f
        drawBead(rosary[0], currentIndex, Offset(centerX, crossY.toFloat()))

        rosary.subList(1, 5).forEachIndexed { index, bead ->
            val x = centerX
            val y = centerY + radius + tailSpacing * (5 - (index + 1))
            drawBead(bead, currentIndex, Offset(x, y))
        }

        val circularBeads = rosary.drop(5)
        val angleStep = (2 * PI / circularBeads.size).toFloat()
        val startAngle = -PI / 2

        circularBeads.forEachIndexed { index, bead ->
            val angle = startAngle - angleStep * index
            val x = centerX - radius * cos(angle)
            val y = centerY - radius * sin(angle)
            drawBead(bead, currentIndex, Offset(x.toFloat(), y.toFloat()))
        }
    }
}

@Suppress("MagicNumber")
private fun DrawScope.drawBead(bead: Bead, currentIndex: Int, center: Offset) {
    val color = when {
        bead.index == currentIndex -> Color.Magenta
        bead.type == BeadType.SMALL -> Color.DarkGray
        else -> Color.Black
    }

    val radius = when (bead.type) {
        BeadType.CROSS -> 16f
        BeadType.LARGE -> 14f
        BeadType.SMALL -> 10f
    }

    if (bead.type == BeadType.CROSS) {
        drawLine(
            color = color,
            start = Offset(center.x, center.y - radius * 5f),
            end = Offset(center.x, center.y + radius * 5f),
            strokeWidth = 15f
        )

        drawLine(
            color = color,
            start = Offset(center.x - radius * 2.5f, center.y - radius * 2.5f),
            end = Offset(center.x + radius * 2.5f, center.y - radius * 2.5f),
            strokeWidth = 15f
        )
    } else {
        drawCircle(color = color, radius = radius, center = center)
    }
}
