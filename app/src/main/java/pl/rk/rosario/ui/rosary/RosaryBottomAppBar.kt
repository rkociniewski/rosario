package pl.rk.rosario.ui.rosary

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun RosaryBottomAppBar(prayer: String) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        content = {
            Text(
                text = prayer
            )
        }
    )
}