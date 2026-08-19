package demo.param;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class ParamEnumDemoTest {

    // @EnumSource provides a convenient way to use Enum constants.
    // ChronoUnit- 時間單位
    @ParameterizedTest
    @EnumSource(ChronoUnit.class)
    void testWithEnumSource(TemporalUnit unit) {
//        System.out.println(unit);
        assertNotNull(unit);
    }

    // 同上，可以省略 EnumSource 的指定
    @ParameterizedTest
    @EnumSource
    void testWithEnumSourceWithAutoDetection(ChronoUnit unit) {
//        System.out.println(unit);
        assertNotNull(unit);
    }

    @ParameterizedTest
    @EnumSource(names = { "DAYS", "HOURS" })
    void testWithEnumSourceInclude(ChronoUnit unit) {
//        System.out.println(unit);
        assertTrue(EnumSet.of(ChronoUnit.DAYS, ChronoUnit.HOURS).contains(unit));
    }
}
