package rk.powermilk.rosario.model

import androidx.annotation.StringRes
import rk.powermilk.rosario.enums.BeadType

data class Bead(
    val index: Int,
    val type: BeadType,
    @get:StringRes
    val prayerId: Int = 0
)
