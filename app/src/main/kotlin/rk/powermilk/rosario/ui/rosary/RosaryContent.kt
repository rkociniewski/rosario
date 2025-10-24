package rk.powermilk.rosario.ui.rosary

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import rk.powermilk.rosario.enums.NavigationMode
import rk.powermilk.rosario.model.Bead
import rk.powermilk.rosario.model.Settings

@Suppress("LongParameterList")
@Composable
fun RosaryContent(
    beads: List<Bead>,
    settings: Settings,
    currentIndex: Int,
    modifier: Modifier,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Box(modifier.pointerInput(settings.navigationMode) {
        detectTapGestures {
            if (settings.navigationMode != NavigationMode.BUTTON) {
                if (it.x < size.width / 2) {
                    if (settings.allowRewind) onPreviousClick()
                } else {
                    onNextClick()
                }
            }
        }
    }) {
        if (settings.showBeadNumber) {
            Text(
                text = (currentIndex + 1).toString(),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = modifier.align(Alignment.Center)
            )
        }

        // Safety check - don't draw if beads are empty or currentIndex is invalid
        if (beads.isNotEmpty() && currentIndex < beads.size) {
            RosaryCanvas(currentIndex, modifier, beads)
        }
    }
}
