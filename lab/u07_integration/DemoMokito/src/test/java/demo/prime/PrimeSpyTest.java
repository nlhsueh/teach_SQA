package demo.prime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PrimeSpyTest {

    @Test
    public void testWithSpy() {
        // 1. 建立 spy 的 PrimeChecker (基於真實實例)
        PrimeChecker checker = new MockPrimeChecker(); // MockPrimeChecker 並不完整
        PrimeChecker spyChecker = spy(checker);

        // 2. 設置模擬行為，使用 doReturn() 來避免觸發真實方法的副作用
        doReturn(true).when(spyChecker).isPrime(7);

        // 3. 注入 spy 的依賴
        Prime prime = new Prime(spyChecker);

        // 4. 測試結果
        int[] result = prime.allPrime(10);
        // RealPrimeChecker 的結果應該是 {2, 3, 5, 7}
        assertArrayEquals(new int[]{2, 3, 5, 7}, result);
    }

}
