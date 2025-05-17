package pl.rk.rosario.model

import androidx.annotation.StringRes
import pl.rk.rosario.enums.BeadType

data class Bead(
    val index: Int,
    val type: BeadType,
    @get:StringRes
    val prayerId: Int = 0
)
