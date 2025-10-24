package rk.powermilk.rosario.ui.rosary

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import rk.powermilk.rosario.enums.BeadType
import rk.powermilk.rosario.model.Bead
import rk.powermilk.rosario.util.Dimensions
import rk.powermilk.rosario.util.Numbers
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RosaryCanvas(
    currentIndex: Int,
    modifier: Modifier = Modifier,
    beads: List<Bead>
) {
    val currentBead = beads[currentIndex]

    val primary = MaterialTheme.colorScheme.primary
    val onSurface = MaterialTheme.colorScheme.onSurface

    val beadColorFor: (Bead) -> Color = {
        when {
            it.index < currentBead.index -> primary.copy(Dimensions.PREVIOUS_BEAD)
            it.index == currentBead.index -> primary
            else -> onSurface
        }
    }

    Canvas(modifier) {
        val centerX = size.width / Dimensions.CENTER
        val centerY = size.height / Dimensions.CENTER
        val radius = size.minDimension / Dimensions.RADIUS

        beads.filterBead(currentBead) { it.type == BeadType.CROSS }.forEach {
            val crossY = centerY + radius + Dimensions.TAIL_SPACING * Dimensions.CROSS_STEP

            drawCross(beadColorFor(it), Offset(centerX, crossY))
        }

        beads.filterBead(currentBead) { it.type.name.startsWith("tail", true) }.forEach {
            val y = centerY + radius + Dimensions.TAIL_SPACING * (Dimensions.BEAD_STEP - (it.index + Numbers.ONE))

            drawBead(it, beadColorFor(it), Offset(centerX, y))
        }

        val circularBeads =
            beads.filterBead(currentBead) { it.type.name.startsWith("bead", true) }
        val angleStep = (Numbers.TWO * PI / circularBeads.distinctBy { it.index }.size).toFloat()
        val startAngle = -PI / Numbers.TWO

        circularBeads.forEach {
            val index = it.index - Dimensions.VERTICAL_BEAM
            val angle = startAngle - angleStep * index

            val x = centerX - radius * cos(angle)
            val y = centerY - radius * sin(angle)

            drawBead(it, beadColorFor(it), Offset(x.toFloat(), y.toFloat()))
        }
    }
}

private fun Iterable<Bead>.filterBead(currentBead: Bead, predicate: (Bead) -> Boolean) =
    this.asSequence().filter(predicate).sortedBy { it == currentBead }.toList()

private fun DrawScope.drawCross(color: Color, center: Offset) {
    drawLine(
        color,
        Offset(center.x, center.y - Dimensions.CROSS_RADIUS * Dimensions.VERTICAL_BEAM),
        Offset(center.x, center.y + Dimensions.CROSS_RADIUS * Dimensions.VERTICAL_BEAM),
        Dimensions.LINE,
    )

    drawLine(
        color,
        Offset(
            center.x - Dimensions.CROSS_RADIUS * Dimensions.HORIZONTAL_BEAM,
            center.y - Dimensions.CROSS_RADIUS * Dimensions.HORIZONTAL_BEAM,
        ),
        Offset(
            center.x + Dimensions.CROSS_RADIUS * Dimensions.HORIZONTAL_BEAM,
            center.y - Dimensions.CROSS_RADIUS * Dimensions.HORIZONTAL_BEAM,
        ),
        Dimensions.LINE,
    )
}

private fun DrawScope.drawBead(bead: Bead, color: Color, offset: Offset) {
    val radius = if (bead.type.name.endsWith("large", true)) Dimensions.LARGE_BEAD else Dimensions.SMALL_BEAD
    drawCircle(color, radius, offset)
}
