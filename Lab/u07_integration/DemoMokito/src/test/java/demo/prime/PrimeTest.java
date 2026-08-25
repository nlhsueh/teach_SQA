package demo.prime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PrimeTest {

    /**
     * 沒有使用 Mockito
     */
    @Test
    public void testAllPrimeWithMyMock() {
        PrimeChecker mockedCheck = new MockPrimeChecker();
        Prime prime = new Prime(mockedCheck);
        int[] result = prime.allPrime(5);
        assertArrayEquals(new int[]{2, 3, 5}, result);
    }

    /**
     * 使用 Mokito
     */
    @Test
    public void testAllPrimeWithMockedIsPrime() {
        // 建立 mock 的 PrimeChecker
        PrimeChecker mockChecker = mock(PrimeChecker.class);

        // 設置模擬行為
        when(mockChecker.isPrime(2)).thenReturn(true);
        when(mockChecker.isPrime(3)).thenReturn(true);
        when(mockChecker.isPrime(4)).thenReturn(false);
        when(mockChecker.isPrime(5)).thenReturn(true);
        when(mockChecker.isPrime(7)).thenReturn(true);

        // 注入 mock 的依賴
        Prime prime = new Prime(mockChecker);

        // 測試結果
        int[] result = prime.allPrime(10);
        assertArrayEquals(new int[]{2, 3, 5, 7}, result);

        // 驗證 mock 的方法是否被正確呼叫
        verify(mockChecker, times(1)).isPrime(1); // !!
        verify(mockChecker, times(1)).isPrime(2);
        verify(mockChecker, times(1)).isPrime(3);
        verify(mockChecker, times(1)).isPrime(4);
        verify(mockChecker, times(1)).isPrime(5);
    }

    /**
     * 使用 Mokito-
     */
    @Test
    public void testAllPrime() {
        // 建立 mock 的 PrimeChecker
        PrimeChecker mockChecker = mock(PrimeChecker.class);

        // 設置模擬行為; 但模擬的數量不夠
        when(mockChecker.isPrime(2)).thenReturn(true);
        when(mockChecker.isPrime(3)).thenReturn(true);
        when(mockChecker.isPrime(4)).thenReturn(false);
        when(mockChecker.isPrime(5)).thenReturn(true);

        // 注入 mock 的依賴
        Prime prime = new Prime(mockChecker);

        // 測試結果
        int[] result = prime.allPrime(5);
        assertArrayEquals(new int[]{2, 3, 5}, result);
    }

    /**
     * 假設範圍限定在 100 內，如果超過會拋出例外
     * Mock 要去模擬拋出例外的狀況
     */
    @Test
    public void testMockException() {
        PrimeChecker mockChecker = mock(PrimeChecker.class);

        // 模擬拋出例外
        when(mockChecker.isPrime(101)).thenThrow(new IllegalArgumentException("Should less/equal than 100"));
        when(mockChecker.isPrime(2)).thenReturn(true);

        Prime prime = new Prime(mockChecker);

        assertThrows(IllegalArgumentException.class, ()-> prime.allPrime(101));
        // 測試 100 以下不會拋出例外
        int[] result = prime.allPrime(100);
        assertArrayEquals(new int[]{2}, result);
    }

    /**
     * 測試真的程式碼，沒有使用 Mock
     */
    @Test
    public void testAllPrimeWithRealPrimeChecker() {
        PrimeChecker realCheck = new RealPrimeChecker();
        Prime prime = new Prime(realCheck);
        int[] result = prime.allPrime(10);
        assertArrayEquals(new int[]{2, 3, 5, 7}, result);
    }
}
