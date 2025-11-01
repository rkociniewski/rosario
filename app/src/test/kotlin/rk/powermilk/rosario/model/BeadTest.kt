package rk.powermilk.rosario.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import rk.powermilk.rosario.R
import rk.powermilk.rosario.enums.BeadType

@DisplayName("Bead Model Tests")
class BeadTest {

    @Nested
    @DisplayName("Bead Creation Tests")
    inner class BeadCreationTests {

        @Test
        @DisplayName("Should create bead with all properties")
        fun testBeadCreation() {
            val bead = Bead(
                index = 5,
                type = BeadType.BEAD_SMALL,
                prayerId = R.string.prayer_hail_mary
            )

            assertEquals(5, bead.index)
            assertEquals(BeadType.BEAD_SMALL, bead.type)
            assertEquals(R.string.prayer_hail_mary, bead.prayerId)
        }

        @Test
        @DisplayName("Should create bead with default prayerId")
        fun testBeadCreationWithDefaultPrayerId() {
            val bead = Bead(
                index = 3,
                type = BeadType.BEAD_LARGE
            )

            assertEquals(3, bead.index)
            assertEquals(BeadType.BEAD_LARGE, bead.type)
            assertEquals(0, bead.prayerId)
        }

        @ParameterizedTest
        @EnumSource(BeadType::class)
        @DisplayName("Should support all bead types")
        fun testAllBeadTypes(beadType: BeadType) {
            val bead = Bead(index = 0, type = beadType)

            assertEquals(beadType, bead.type)
        }
    }

    @Nested
    @DisplayName("Bead Equality Tests")
    inner class BeadEqualityTests {

        @Test
        @DisplayName("Should be equal when all properties match")
        fun testBeadEquality() {
            val bead1 = Bead(1, BeadType.CROSS, R.string.prayer_in_the_name)
            val bead2 = Bead(1, BeadType.CROSS, R.string.prayer_in_the_name)

            assertEquals(bead1, bead2)
        }

        @Test
        @DisplayName("Should not be equal when index differs")
        fun testBeadInequalityByIndex() {
            val bead1 = Bead(1, BeadType.CROSS, R.string.prayer_in_the_name)
            val bead2 = Bead(2, BeadType.CROSS, R.string.prayer_in_the_name)

            assertNotEquals(bead1, bead2)
        }

        @Test
        @DisplayName("Should not be equal when type differs")
        fun testBeadInequalityByType() {
            val bead1 = Bead(1, BeadType.CROSS, R.string.prayer_in_the_name)
            val bead2 = Bead(1, BeadType.BEAD_SMALL, R.string.prayer_in_the_name)

            assertNotEquals(bead1, bead2)
        }

        @Test
        @DisplayName("Should not be equal when prayerId differs")
        fun testBeadInequalityByPrayerId() {
            val bead1 = Bead(1, BeadType.CROSS, R.string.prayer_in_the_name)
            val bead2 = Bead(1, BeadType.CROSS, R.string.prayer_our_father)

            assertNotEquals(bead1, bead2)
        }
    }

    @Nested
    @DisplayName("Bead Copy Tests")
    inner class BeadCopyTests {

        @Test
        @DisplayName("Should create copy with modified index")
        fun testCopyWithIndex() {
            val original = Bead(1, BeadType.CROSS, R.string.prayer_in_the_name)
            val copy = original.copy(index = 5)

            assertEquals(5, copy.index)
            assertEquals(original.type, copy.type)
            assertEquals(original.prayerId, copy.prayerId)
        }

        @Test
        @DisplayName("Should create copy with modified type")
        fun testCopyWithType() {
            val original = Bead(1, BeadType.CROSS, R.string.prayer_in_the_name)
            val copy = original.copy(type = BeadType.BEAD_LARGE)

            assertEquals(BeadType.BEAD_LARGE, copy.type)
            assertEquals(original.index, copy.index)
            assertEquals(original.prayerId, copy.prayerId)
        }

        @Test
        @DisplayName("Should create copy with modified prayerId")
        fun testCopyWithPrayerId() {
            val original = Bead(1, BeadType.CROSS, R.string.prayer_in_the_name)
            val copy = original.copy(prayerId = R.string.prayer_our_father)

            assertEquals(R.string.prayer_our_father, copy.prayerId)
            assertEquals(original.index, copy.index)
            assertEquals(original.type, copy.type)
        }
    }

    @Nested
    @DisplayName("Bead Property Tests")
    inner class BeadPropertyTests {

        @Test
        @DisplayName("Should allow negative index")
        fun testNegativeIndex() {
            val bead = Bead(-1, BeadType.CROSS)

            assertEquals(-1, bead.index)
        }

        @Test
        @DisplayName("Should allow zero index")
        fun testZeroIndex() {
            val bead = Bead(0, BeadType.CROSS)

            assertEquals(0, bead.index)
        }

        @Test
        @DisplayName("Should allow large index values")
        fun testLargeIndex() {
            val bead = Bead(1000, BeadType.CROSS)

            assertEquals(1000, bead.index)
        }
    }
}
