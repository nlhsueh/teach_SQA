package u04_utest;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangleBlackBoxTest {

    @Nested
    class EquilateralTest {

    }

    @Nested
    class IsoscelesTest {

    }

    @Nested
    class RightAngleTest {

    }

    @Nested
    class IsoscelesRightAngleTest {

    }

    @Nested
    class GeneralTriangleTest {

    }

    @Nested
    class NotATriangleTest {
        @Test
        void not_valid_triangle() {
            assertAll("not validate",
                    ()-> assertEquals("Not a valid triangle", Triangle.getTriangleType(-1, 2, 3)),
                    ()-> assertEquals("Not a valid triangle", Triangle.getTriangleType(2, -2, 3)),
                    ()-> assertEquals("Not a valid triangle", Triangle.getTriangleType(2, 2, -3))
            )
            ;
        }
    }

    @Nested
    class BorderTest {


        @Nested
        class BorderMinTest {

        }

        @Nested
        class BorderMinPlusTest {

        }

        @Nested
        class BorderMaxTest {

        }

        @Nested
        class BorderMaxMinusTest {

        }

    }

}