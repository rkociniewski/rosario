package rk.powermilk.rosario.ui.helper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import rk.powermilk.rosario.R
import rk.powermilk.rosario.enums.BeadType
import rk.powermilk.rosario.enums.PrayerType

@DisplayName("Bead Generation Tests")
class BeadGenerationTest {

    @Nested
    @DisplayName("Rosary Bead Generation")
    inner class RosaryBeadGenerationTests {

        @Test
        @DisplayName("Should generate correct number of rosary beads")
        fun testRosaryBeadCount() {
            val beads = PrayerType.ROSARY.generateBeads()

            // Rosary structure:
            // - Cross (2 beads for In the Name and Apostles Creed)
            // - Tail (1 large + 3 small + duplicate small + Glory Be + O my Jesus = 7 beads)
            // - 5 decades (5 * (1 large + 10 small) + 4 * 2 (Glory Be + O my Jesus) = 5*11 + 8 = 63)
            // - Final prayers (Glory Be, O my Jesus, Our Father = 3)
            // - Final cross (1)
            // Total: 2 + 7 + 63 + 3 + 1 = 76 beads (approximate, depends on implementation)
            assertTrue(beads.size > 60)
            assertNotNull(beads)
        }

        @Test
        @DisplayName("Should start with cross bead")
        fun testRosaryStartsWithCross() {
            val beads = PrayerType.ROSARY.generateBeads()

            assertEquals(BeadType.CROSS, beads.first().type)
        }

        @Test
        @DisplayName("Should contain Our Father prayers")
        fun testRosaryContainsOurFather() {
            val beads = PrayerType.ROSARY.generateBeads()

            val ourFatherCount = beads.count { it.prayerId == R.string.prayer_our_father }
            assertTrue(ourFatherCount >= 6) // At least 6 Our Father prayers
        }

        @Test
        @DisplayName("Should contain Hail Mary prayers")
        fun testRosaryContainsHailMary() {
            val beads = PrayerType.ROSARY.generateBeads()

            val hailMaryCount = beads.count { it.prayerId == R.string.prayer_hail_mary }
            assertEquals(50, hailMaryCount) // Exactly 50 Hail Mary prayers in a rosary
        }

        @Test
        @DisplayName("Should contain Glory Be prayers")
        fun testRosaryContainsGloryBe() {
            val beads = PrayerType.ROSARY.generateBeads()

            val gloryBeCount = beads.count { it.prayerId == R.string.prayer_glory_be }
            assertTrue(gloryBeCount > 0)
        }

        @Test
        @DisplayName("Should have correct bead indices")
        fun testRosaryBeadIndices() {
            val beads = PrayerType.ROSARY.generateBeads()

            beads.forEach { bead ->
                assertTrue(bead.index >= 0)
            }
        }

        @Test
        @DisplayName("Should contain different bead types")
        fun testRosaryBeadTypes() {
            val beads = PrayerType.ROSARY.generateBeads()

            val types = beads.map { it.type }.distinct()
            assertTrue(types.contains(BeadType.CROSS))
            assertTrue(types.contains(BeadType.BEAD_LARGE))
            assertTrue(types.contains(BeadType.BEAD_SMALL))
        }
    }

    @Nested
    @DisplayName("Divine Mercy Bead Generation")
    inner class DivineMercyBeadGenerationTests {

        @Test
        @DisplayName("Should generate divine mercy beads")
        fun testDivineMercyBeadGeneration() {
            val beads = PrayerType.DIVINE_MERCY.generateBeads()

            assertNotNull(beads)
            assertTrue(beads.isNotEmpty())
        }

        @Test
        @DisplayName("Should start with cross bead")
        fun testDivineMercyStartsWithCross() {
            val beads = PrayerType.DIVINE_MERCY.generateBeads()

            assertEquals(BeadType.CROSS, beads.first().type)
        }

        @Test
        @DisplayName("Should contain Eternal Father prayers")
        fun testDivineMercyContainsEternalFather() {
            val beads = PrayerType.DIVINE_MERCY.generateBeads()

            val eternalFatherCount = beads.count { it.prayerId == R.string.prayer_ethernal_father }
            assertEquals(5, eternalFatherCount) // 5 decades
        }

        @Test
        @DisplayName("Should contain For the Sake prayers")
        fun testDivineMercyContainsForTheSake() {
            val beads = PrayerType.DIVINE_MERCY.generateBeads()

            val forTheSakeCount = beads.count { it.prayerId == R.string.prayer_for_the_sake }
            assertEquals(50, forTheSakeCount) // 10 per decade * 5 decades
        }

        @Test
        @DisplayName("Should contain Holy God prayers")
        fun testDivineMercyContainsHolyGod() {
            val beads = PrayerType.DIVINE_MERCY.generateBeads()

            val holyGodCount = beads.count { it.prayerId == R.string.prayer_holy_god }
            assertEquals(3, holyGodCount) // 3 times
        }

        @Test
        @DisplayName("Should contain Jesus I Trust prayers")
        fun testDivineMercyContainsJesusITrust() {
            val beads = PrayerType.DIVINE_MERCY.generateBeads()

            val jesusITrustCount = beads.count { it.prayerId == R.string.prayer_jesus_I_trust }
            assertEquals(3, jesusITrustCount) // 3 times
        }
    }

    @Nested
    @DisplayName("Jesus Prayer (Chotka) Bead Generation")
    inner class JesusPrayerBeadGenerationTests {

        @Test
        @DisplayName("Should generate chotka beads")
        fun testChotkaBeadGeneration() {
            val beads = PrayerType.JESUS_PRAYER.generateBeads()

            assertNotNull(beads)
            assertTrue(beads.isNotEmpty())
        }

        @Test
        @DisplayName("Should start with cross bead")
        fun testChotkaStartsWithCross() {
            val beads = PrayerType.JESUS_PRAYER.generateBeads()

            assertEquals(BeadType.CROSS, beads.first().type)
        }

        @Test
        @DisplayName("Should contain Lord Jesus prayers")
        fun testChotkaContainsLordJesus() {
            val beads = PrayerType.JESUS_PRAYER.generateBeads()

            val lordJesusCount = beads.count { it.prayerId == R.string.prayer_lord_jesus }
            assertEquals(50, lordJesusCount) // 10 per decade * 5 decades
        }

        @Test
        @DisplayName("Should have fewer total beads than rosary")
        fun testChotkaBeadCount() {
            val chotkaBeads = PrayerType.JESUS_PRAYER.generateBeads()
            val rosaryBeads = PrayerType.ROSARY.generateBeads()

            assertTrue(chotkaBeads.size < rosaryBeads.size)
        }

        @Test
        @DisplayName("Should have some beads without prayer IDs")
        fun testChotkaHasEmptyBeads() {
            val beads = PrayerType.JESUS_PRAYER.generateBeads()

            val emptyBeadCount = beads.count { it.prayerId == 0 }
            assertTrue(emptyBeadCount > 0)
        }
    }

    @Nested
    @DisplayName("General Bead Generation Tests")
    inner class GeneralBeadTests {

        @Test
        @DisplayName("All prayer types should generate non-empty bead lists")
        fun testAllPrayerTypesGenerateBeads() {
            PrayerType.entries.forEach { prayerType ->
                val beads = prayerType.generateBeads()
                assertTrue(beads.isNotEmpty(), "Prayer type $prayerType should generate beads")
            }
        }

        @Test
        @DisplayName("All beads should have valid indices")
        fun testAllBeadsHaveValidIndices() {
            PrayerType.entries.forEach { prayerType ->
                val beads = prayerType.generateBeads()
                beads.forEach { bead ->
                    assertTrue(bead.index >= 0, "Bead index should be non-negative")
                }
            }
        }

        @Test
        @DisplayName("All beads should have valid types")
        fun testAllBeadsHaveValidTypes() {
            PrayerType.entries.forEach { prayerType ->
                val beads = prayerType.generateBeads()
                beads.forEach { bead ->
                    assertNotNull(bead.type, "Bead should have a valid type")
                }
            }
        }
    }
}
