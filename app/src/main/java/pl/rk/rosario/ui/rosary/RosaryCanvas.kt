package pl.rk.rosario.ui.rosary

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import pl.rk.rosario.enums.BeadType
import pl.rk.rosario.ui.parts.generateRosaryBeads
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Suppress("MagicNumber")
@Composable
fun RosaryCanvas(
    currentIndex: Int,
    modifier: Modifier = Modifier
) {

    val rosary = generateRosaryBeads()
    val rosarySize = rosary.maxOf { it.index } + 1
    val angleStep = (2 * PI / rosarySize).toFloat()

    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.minDimension / 2.5f

        rosary.forEach { bead ->
            val angle = angleStep * bead.index - PI / 2 // start at top
            val x = centerX + radius * cos(angle)
            val y = centerY + radius * sin(angle)

            val color = when {
                bead.index == currentIndex -> Color.Magenta
                bead.type == BeadType.LARGE -> Color.Gray
                bead.type == BeadType.CROSS -> Color.Black
                else -> Color.LightGray
            }

            val beadRadius = when (bead.type) {
                BeadType.CROSS -> 8f
                BeadType.LARGE -> 12f
                BeadType.SMALL -> 6f
            }

            drawCircle(
                color = color,
                radius = beadRadius,
                center = Offset(x.toFloat(), y.toFloat())
            )
        }
    }
}
