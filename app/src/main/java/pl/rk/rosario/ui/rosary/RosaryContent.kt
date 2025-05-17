package pl.rk.rosario.ui.rosary

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import pl.rk.rosario.enums.NavigationMode
import pl.rk.rosario.model.Bead
import pl.rk.rosario.model.Settings

@Suppress("LongParameterList")
@Composable
fun RosaryContent(
    beads: List<Bead>,
    currentSetting: Settings,
    currentIndex: Int,
    modifier: Modifier,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Box(
        modifier = modifier
            .pointerInput(currentSetting.navigationMode) {
                detectTapGestures { offset ->
                    if (currentSetting.navigationMode != NavigationMode.BUTTON) {
                        if (offset.x < size.width / 2) {
                            if (currentSetting.allowRewind) onPreviousClick
                        } else {
                            onNextClick
                        }
                    }
                }
            }
    ) {
        RosaryCanvas(
            beads = beads,
            currentIndex = currentIndex,
            modifier = Modifier.fillMaxSize()
        )
    }
}