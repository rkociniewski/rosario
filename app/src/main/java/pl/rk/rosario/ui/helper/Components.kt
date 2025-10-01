package pl.rk.rosario.ui.helper

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipDefaults.rememberPlainTooltipPositionProvider
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import pl.rk.rosario.R
import pl.rk.rosario.enums.DisplayText
import pl.rk.rosario.enums.Language
import pl.rk.rosario.util.Dimensions
import kotlin.enums.EnumEntries

/**
 * A composable function that creates a row of selectable filter chips for enum options.
 *
 * This generic component creates a horizontal row of filter chips based on the provided enum options.
 * It highlights the currently selected option and invokes the onSelect callback when a different option is chosen.
 *
 * @param T The enum type that implements DisplayText interface
 * @param options The enum entries to display as options
 * @param selected The currently selected enum value
 * @param onSelect Callback invoked when a different option is selected
 */
@Composable
fun <T> EnumSelector(
    options: EnumEntries<T>,
    selected: T,
    onSelect: (T) -> Unit
) where T : Enum<T>, T : DisplayText =
    Row(horizontalArrangement = Arrangement.spacedBy(Dimensions.height)) {
        options.forEach {
            FilterChip(
                selected == it,
                { onSelect(it) },
                { Text(stringResource(it.label)) },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.onSurface,
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }

/**
 * A composable function that displays a label with an information tooltip.
 *
 * This component pairs a text label with an information icon. When the icon is clicked,
 * it displays a tooltip with additional helpful information.
 *
 * @param label The primary text to display as the label
 * @param tooltip The text to display in the tooltip when the info icon is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpLabel(label: String, tooltip: String) {
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.width(Dimensions.minHeight))
        TooltipBox(
            TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Above), {
                PlainTooltip { Text(tooltip) }
            }, tooltipState
        ) {
            Icon(
                Icons.Outlined.Info, stringResource(R.string.tooltip_icon_description),
                Modifier
                    .padding(Dimensions.minHeight)
                    .clickable {
                        scope.launch {
                            if (!tooltipState.isVisible) {
                                tooltipState.show()
                            } else {
                                tooltipState.dismiss()
                            }
                        }
                    },
                MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun BooleanSelector(
    boolean: Boolean,
    label: String,
    onCheckedChange: ((Boolean) -> Unit)?
) = Row(verticalAlignment = Alignment.CenterVertically) {
    Checkbox(boolean, onCheckedChange)
    Spacer(Modifier.width(Dimensions.height))
    Text(label)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelector(
    options: EnumEntries<Language>,
    selected: Language,
    onSelect: (Language) -> Unit
) {
    LazyVerticalGrid(
        GridCells.Adaptive(minSize = Dimensions.adaptiveMinSize), Modifier
            .fillMaxWidth()
            .heightIn(max = Dimensions.maxHeightIn),
        verticalArrangement = Arrangement.spacedBy(Dimensions.height),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.height)
    ) {
        items(options.size) {
            val opt = options[it]

            FilterChip(
                selected == opt, { onSelect(opt) }, {
                    TooltipBox(
                        TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Above),
                        { Text(stringResource(opt.label)) },
                        rememberTooltipState()
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = Dimensions.minHeight),
                            Alignment.Center
                        ) {
                            Icon(
                                painterResource(opt.flagIcon),
                                stringResource(opt.label),
                                Modifier.size(Dimensions.size),
                                Color.Unspecified
                            )
                        }
                    }
                }, colors = FilterChipDefaults.filterChipColors(
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.colorScheme.onSurface,
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                border = null,
                shape = RoundedCornerShape(Dimensions.dialogPadding)
            )
        }
    }
}

@Composable
fun <T> ColumnSelector(
    options: EnumEntries<T>,
    selected: T,
    onSelect: (T) -> Unit
) where T : Enum<T>, T : DisplayText {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dimensions.minHeight),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(Dimensions.dialogPadding)
    ) {
        items(options.size) {
            val option = options[it]

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(option) }
                    .padding(Dimensions.itemSpacing)
                    .background(
                        if (option == selected) MaterialTheme.colorScheme.primaryContainer
                        else Color.Transparent,
                        RoundedCornerShape(Dimensions.minHeight)
                    )
            ) {
                Text(
                    text = stringResource(option.label),
                    modifier = Modifier.weight(1f),
                    color = if (option == selected) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSurface
                )
                if (option == selected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.selected_item),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
