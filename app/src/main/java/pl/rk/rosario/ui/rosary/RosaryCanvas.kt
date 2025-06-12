package pl.rk.rosario.ui.rosary

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
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

const val TAIL_SPACING = 48f
const val TWO_AND_HALF = 2.5f
const val THREE = 3f
const val FIVE = 5f
const val SIX_AND_HALF = 6.5f
const val SIX = 6f
const val TEN = 10f
const val FOURTEEN = 14f
const val CROSS_RADIUS = 16f

@Composable
fun RosaryCanvas(
    currentIndex: Int,
    modifier: Modifier = Modifier,
    beads: List<Bead>
) {
    // Safety check - don't draw if beads are empty or currentIndex is invalid
    if (beads.isEmpty() || currentIndex >= beads.size) {
        return
    }

    val currentBead = beads[currentIndex]

    val primary = MaterialTheme.colorScheme.primary
    val onSurface = MaterialTheme.colorScheme.onSurface

    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = size.minDimension / THREE

        beads.filterBead(currentBead) { it.type == BeadType.CROSS }.forEach {
            val crossY = centerY + radius + TAIL_SPACING * SIX_AND_HALF

            val color = if (it == currentBead) {
                primary
            } else {
                onSurface
            }

            drawCross(color, Offset(centerX, crossY))
        }

        beads.filterBead(currentBead) { it.type.name.startsWith("tail", true) }.forEach {
            val y = centerY + radius + TAIL_SPACING * (SIX - (it.index + 1))

            val color = if (it == currentBead) {
                primary
            } else {
                onSurface
            }

            drawBead(it, color, Offset(centerX, y))
        }

        val circularBeads =
            beads.filterBead(currentBead) { it.type.name.startsWith("bead", true) }
        val angleStep = (2 * PI / circularBeads.distinctBy { it.index }.size).toFloat()
        val startAngle = -PI / 2

        circularBeads.forEach {
            val index = it.index - FIVE
            val angle = startAngle - angleStep * index

            val x = centerX - radius * cos(angle)
            val y = centerY - radius * sin(angle)

            val color = if (it == currentBead) {
                primary
            } else {
                onSurface
            }

            drawBead(it, color, Offset(x.toFloat(), y.toFloat()))
        }
    }
}

private fun Iterable<Bead>.filterBead(currentBead: Bead, predicate: (Bead) -> Boolean) =
    this.asSequence().filter(predicate).sortedBy { it == currentBead }.toList()

private fun DrawScope.drawCross(color: Color, center: Offset) {
    drawLine(
        color = color,
        start = Offset(center.x, center.y - CROSS_RADIUS * FIVE),
        end = Offset(center.x, center.y + CROSS_RADIUS * FIVE),
        strokeWidth = 15f
    )

    drawLine(
        color = color,
        start = Offset(
            center.x - CROSS_RADIUS * TWO_AND_HALF,
            center.y - CROSS_RADIUS * TWO_AND_HALF
        ),
        end = Offset(
            center.x + CROSS_RADIUS * TWO_AND_HALF,
            center.y - CROSS_RADIUS * TWO_AND_HALF
        ),
        strokeWidth = 15f
    )
}

private fun DrawScope.drawBead(bead: Bead, color: Color, offset: Offset) {
    val radius = if (bead.type.name.endsWith("large", true)) {
        FOURTEEN
    } else {
        TEN
    }

    drawCircle(color = color, radius = radius, center = offset)
}
