package pl.rk.rosario.model

import pl.rk.rosario.enums.BeadRole
import pl.rk.rosario.enums.BeadType

data class Bead(
    val index: Int,
    val type: BeadType,
    val role: BeadRole,
    val active: Boolean = true
)

