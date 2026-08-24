package demo.param;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

public class ParamDemoTest {

    @ParameterizedTest
    @ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
    void palindromes(String candidate) {
        assertTrue(StringUtils.isPalindrome(candidate));
    }

    @DisplayName("Just testing integer is between 0 and 4")
    @ParameterizedTest (name ="{0} is between 0 and 4")
    @ValueSource(ints = { 1, 2, 3 })
    void testWithValueSource(int argument) {
        assertTrue(argument > 0 && argument < 4);
    }

    @ParameterizedTest(name = "{0} is prime")
    @ValueSource(ints={2,3,5,7,11})
    void testPrime(int argument) {
        assertTrue(MathUtils.isPrime((argument)));
    }

    @ParameterizedTest(name = "{0} is not prime")
    @ValueSource(ints={1,4,6,9,51})
    void testNotPrime(int argument) {
        assertFalse(MathUtils.isPrime(argument));
    }

    // csv 是用 , 分開
    // 如果內容是有空格的字串，要用 '' 來框起來
    // 注意型態：第二欄位的資料是數字
    @ParameterizedTest
    @CsvSource({
            "apple,         1",
            "banana,        2",
            "'lemon, lime', 0xF1",
            "strawberry,    700_000"
    })
    void testWithCsvSource(String fruit, int rank) {
        assertNotNull(fruit);
        assertNotEquals(0, rank);
    }

    // 把資料放在 test/resources下的 two-columns.csv
    // 第一行是提示字，不會當成測試資料
    // CSV 是用 , 分開
    @ParameterizedTest
    @CsvFileSource(resources = "/two-columns.csv", numLinesToSkip = 1)
    void testWithCsvFileSourceFromClasspath(String country, int reference) {
        assertNotNull(country);
        assertNotEquals(0, reference);
    }

    // 分別會輸入 null, empty, 還有空格、等等資料進去測試
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void nullEmptyAndBlankStrings(String text) {
        System.out.println(text);
        assertTrue(text == null || text.trim().isEmpty());
    }
}
