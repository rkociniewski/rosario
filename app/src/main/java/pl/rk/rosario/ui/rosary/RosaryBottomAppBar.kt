package pl.rk.rosario.ui.rosary

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import pl.rk.rosario.util.Dimensions

@Composable
fun RosaryBottomAppBar(@StringRes prayerId: Int) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Box(
            modifier = Modifier
                .heightIn(min = Dimensions.minHeightIn, max = Dimensions.maxHeightIn)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(horizontal = Dimensions.dialogPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getText(prayerId),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun getText(prayerId: Int) =
    if (prayerId != 0) stringResource(id = prayerId) else ""

