package u02_robust.assertion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PeopleDemo 類別不變量與狀態測試")
class PeopleDemoTest {

    @Test
    @DisplayName("測試正常建立 Person 並計算合理 BMI")
    void testNormalPersonCreationAndBmi() {
        PeopleDemo person = new PeopleDemo("Bob", 1.80, 75.0, 1990);
        assertEquals("Bob", person.getName());
        assertEquals(1.80, person.getHeight());
        assertEquals(75.0, person.getWeight());
        assertEquals(1990, person.getBirthYear());

        double expectedBmi = 75.0 / (1.80 * 1.80);
        assertEquals(expectedBmi, person.calculateBmi(), 1e-4);
    }

    @Test
    @DisplayName("測試公開參數防禦：非法姓名或身高等應拋出 IllegalArgumentException")
    void testInvalidParametersThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new PeopleDemo("", 1.75, 70, 1995));
        assertThrows(IllegalArgumentException.class, () -> new PeopleDemo("Alice", -1.0, 70, 1995));
        assertThrows(IllegalArgumentException.class, () -> new PeopleDemo("Alice", 1.75, -50, 1995));
        assertThrows(IllegalArgumentException.class, () -> new PeopleDemo("Alice", 1.75, 70, 1850));
    }
}
