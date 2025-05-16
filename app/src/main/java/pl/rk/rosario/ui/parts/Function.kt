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

    beads += Bead(index++, BeadType.CROSS)
    beads += Bead(index, BeadType.CROSS)
    beads += Bead(index++, BeadType.LARGE)

    beads += Bead(index++, BeadType.SMALL)
    beads += Bead(index++, BeadType.SMALL)
    beads += Bead(index++, BeadType.SMALL)

    repeat(5) {
        beads += Bead(index++, BeadType.LARGE)
        beads += Bead(index, BeadType.LARGE)
        repeat(10) {
            beads += Bead(index++, BeadType.SMALL)
        }
    }

    return beads
}

@Suppress("MagicNumber")
fun generateChapletBeads(): List<Bead> {
    val beads = mutableListOf<Bead>()
    var index = 0

    beads += Bead(index++, BeadType.CROSS)
    beads += Bead(index++, BeadType.LARGE)

    beads += Bead(index++, BeadType.SMALL)
    beads += Bead(index++, BeadType.SMALL)
    beads += Bead(index++, BeadType.SMALL)

    repeat(5) {
        beads += Bead(index++, BeadType.LARGE)
        repeat(10) {
            beads += Bead(index++, BeadType.SMALL)
        }
    }

    repeat(3) {
        beads += Bead(index++, BeadType.SMALL)
    }
    beads += Bead(index++, BeadType.SMALL)
    beads += Bead(index++, BeadType.SMALL)
    beads += Bead(index++, BeadType.SMALL)
    beads += Bead(index++, BeadType.LARGE)

    return beads
}

@Suppress("MagicNumber")
fun generateChotkaBeads(): List<Bead> {
    val beads = mutableListOf<Bead>()
    var index = 0

    beads += Bead(index++, BeadType.CROSS)
    beads += Bead(index++, BeadType.LARGE)
    beads += Bead(index++, BeadType.SMALL)
    beads += Bead(index++, BeadType.SMALL)
    beads += Bead(index++, BeadType.SMALL)

    repeat(50) {
        beads += Bead(index++, BeadType.SMALL)
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
            println("loadPrayerTextMap: $key $value")
            try {
                BeadRole.valueOf(key) to value
            } catch (e: IllegalArgumentException) {
                Log.e("pl.rk.rosario", "[ERROR] ${e.message}")
                null // Skip unknown keys
            }
        }.toMap()
    }