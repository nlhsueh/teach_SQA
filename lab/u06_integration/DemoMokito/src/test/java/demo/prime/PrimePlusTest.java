package demo.prime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrimePlusTest {
    // @Mock 建立要注入的模擬物件
    @Mock
    PrimeChecker mockChecker;

    // @InjectMocks 用來建立並注入 mock 物件
    @InjectMocks
    Prime prime;

    @Test
    public void testAllPrime() {
        when(mockChecker.isPrime(2)).thenReturn(true);
        when(mockChecker.isPrime(3)).thenReturn(true);
        when(mockChecker.isPrime(4)).thenReturn(false);
        when(mockChecker.isPrime(5)).thenReturn(true);

        // 測試結果
        int[] result = prime.allPrime(5);
        assertArrayEquals(new int[]{2, 3, 5}, result);

        verify(mockChecker, times(1)).isPrime(2);
        verify(mockChecker, times(1)).isPrime(3);
        verify(mockChecker, times(1)).isPrime(4);
        verify(mockChecker, times(1)).isPrime(5);
    }
}
