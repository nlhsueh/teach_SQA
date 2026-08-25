package demo.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class) // 告訴 JUnit 使用 MockitoExtension 來啟用 Mockito 的功能。
// MockitoExtension 負責初始化 @Mock 和 @InjectMocks 註解的物件，並確保 Mock 行為正確。

public class CalculatorTest {

    @InjectMocks
    Calculator calculator;
    // 1. @InjectMocks：負責將 @Mock 註解的物件自動注入到被測試的類別（Calculator）的相應屬性或建構子中。
    // 2. 當使用建構子注入（如 Calculator(CalculatorService service)），Mockito 會嘗試匹配並注入所需的 mock。

    @Mock
    CalculatorService calculatorService;
    // 1. @Mock：建立一個假的（mock）的 CalculatorService 物件。
    // 2. 這個物件是模擬的，所有方法默認返回 null、基本類型的默認值（例如 0）、空集合等，除非有特別定義行為。
    // 3. 這允許我們控制 `calculatorService` 的行為，而不依賴其實際實現。

    @BeforeEach
    void setUp() {
        calculator = new Calculator(calculatorService);
        // 1. 這一行手動完成了 mock 物件的注入，但通常可以省略，因為 @InjectMocks 已經自動完成了這部分工作。
    }

    @Test
    public void testAdd() {
        when(calculatorService.add(2, 3)).thenReturn(5);
        // 1. when-thenReturn：指定當 mock 物件 `calculatorService` 的 `add(2, 3)` 方法被調用時，返回值為 5。
        // 2. 我們不需要關心 `CalculatorService` 的實際邏輯，只需模擬所需行為。

        assertEquals(5, calculator.add(2, 3));
        // 測試 `calculator` 是否正確調用了 `calculatorService.add` 並返回結果。

        verify(calculatorService).add(2, 3);
        // verify：驗證 `calculatorService.add(2, 3)` 是否被調用，確認方法行為是否符合預期。
    }

    @Test
    public void testSubtract() {
        when(calculatorService.subtract(5, 3)).thenReturn(2);
        // 模擬 subtract 方法的行為，返回值為 2。

        assertEquals(2, calculator.subtract(5, 3));
        // 測試 `calculator` 的 `subtract` 方法是否返回正確結果。

        verify(calculatorService).subtract(5, 3);
        // 驗證 `calculatorService.subtract(5, 3)` 是否被正確調用。
    }

    @Test
    public void testMultiply() {
        when(calculatorService.multiply(2, 3)).thenReturn(6);
        // 模擬 multiply 方法的行為，返回值為 6。

        assertEquals(6, calculator.multiply(2, 3));
        // 測試 `calculator` 的 `multiply` 方法是否返回正確結果。

        verify(calculatorService).multiply(2, 3);
        // 驗證 `calculatorService.multiply(2, 3)` 是否被正確調用。
    }

    @Test
    public void testDivide() {
        when(calculatorService.divide(6, 3)).thenReturn(2);
        // 模擬 divide 方法的行為，返回值為 2。

        assertEquals(2, calculator.divide(6, 3));
        // 測試 `calculator` 的 `divide` 方法是否返回正確結果。

        verify(calculatorService).divide(6, 3);
        // 驗證 `calculatorService.divide(6, 3)` 是否被正確調用。
    }

    @Test
    public void testDivideByZeroThrowsException() {
        when(calculatorService.divide(6, 0)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> calculator.divide(6, 0));
    }
}
