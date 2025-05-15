package pl.rk.rosario.ui.parts

import android.content.Context
import android.util.Log
import androidx.annotation.RawRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import pl.rk.rosario.enums.BeadRole
import pl.rk.rosario.enums.BeadType
import pl.rk.rosario.model.Bead
import java.io.InputStreamReader

@Suppress("MagicNumber")
fun generateRosaryBeads(): List<Bead> {
    val beads = mutableListOf<Bead>()
    var index = 0

    beads += Bead(index++, BeadType.CROSS, BeadRole.CROSS)
    beads += Bead(index++, BeadType.LARGE, BeadRole.INTRO_SIGN_OF_CROSS)

    beads += Bead(index++, BeadType.SMALL, BeadRole.FAITH)
    beads += Bead(index++, BeadType.SMALL, BeadRole.HOPE)
    beads += Bead(index++, BeadType.SMALL, BeadRole.LOVE)

    repeat(5) {
        beads += Bead(index++, BeadType.LARGE, BeadRole.PRE_DECADE_GLORY)
        beads += Bead(index++, BeadType.LARGE, BeadRole.PRE_DECADE_OUR_FATHER)
        repeat(10) {
            beads += Bead(index++, BeadType.SMALL, BeadRole.DECADE_HAIL_MARY)
        }
    }

    return beads
}

@Suppress("MagicNumber")
fun generateChapletBeads(): List<Bead> {
    val beads = mutableListOf<Bead>()
    var index = 0

    beads += Bead(index++, BeadType.CROSS, BeadRole.CROSS)
    beads += Bead(index++, BeadType.LARGE, BeadRole.INTRO_SIGN_OF_CROSS)

    beads += Bead(index++, BeadType.SMALL, BeadRole.CHAPLET_INTRO_OUR_FATHER)
    beads += Bead(index++, BeadType.SMALL, BeadRole.CHAPLET_INTRO_HAIL_MARY)
    beads += Bead(index++, BeadType.SMALL, BeadRole.CHAPLET_INTRO_CREED)

    repeat(5) {
        beads += Bead(index++, BeadType.LARGE, BeadRole.PRE_DECADE_OUR_FATHER)
        repeat(10) {
            beads += Bead(index++, BeadType.SMALL, BeadRole.CHAPLET_DECADE_MAIN)
        }
    }

    repeat(3) {
        beads += Bead(index++, BeadType.SMALL, BeadRole.CHAPLET_CONCLUSION_TRISAGION)
    }
    beads += Bead(index++, BeadType.SMALL, BeadRole.CHAPLET_CONCLUSION_TRUST)
    beads += Bead(index++, BeadType.SMALL, BeadRole.CHAPLET_CONCLUSION_TRUST)
    beads += Bead(index++, BeadType.SMALL, BeadRole.CHAPLET_CONCLUSION_TRUST)
    beads += Bead(index++, BeadType.LARGE, BeadRole.CHAPLET_END_SIGN_OF_CROSS)

    return beads
}

@Suppress("MagicNumber")
fun generateChotkaBeads(): List<Bead> {
    val beads = mutableListOf<Bead>()
    var index = 0

    beads += Bead(index++, BeadType.CROSS, BeadRole.CROSS, active = false)
    beads += Bead(index++, BeadType.LARGE, BeadRole.INTRO_SIGN_OF_CROSS, active = false)
    beads += Bead(index++, BeadType.SMALL, BeadRole.FAITH, active = false)
    beads += Bead(index++, BeadType.SMALL, BeadRole.HOPE, active = false)
    beads += Bead(index++, BeadType.SMALL, BeadRole.LOVE, active = false)

    repeat(50) {
        beads += Bead(index++, BeadType.SMALL, BeadRole.DECADE_JESUS_PRAYER)
    }

    return beads
}

suspend fun loadPrayerTextMap(context: Context, @RawRes rawResId: Int) =
    withContext(Dispatchers.IO) {
        val inputStream = context.resources.openRawResource(rawResId)
        val reader = InputStreamReader(inputStream)
        val text = reader.readText()
        reader.close()

        val rawMap = Json.decodeFromString<Map<String, String>>(text)
        rawMap.mapNotNull { (key, value) ->
            try {
                BeadRole.valueOf(key) to value
            } catch (e: IllegalArgumentException) {
                Log.e("pl.rk.rosario", "[ERROR] ${e.message}")
                null // Skip unknown keys
            }
        }.toMap()
    }