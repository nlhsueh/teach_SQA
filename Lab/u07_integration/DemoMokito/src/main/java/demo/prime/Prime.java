package demo.prime;

import java.util.ArrayList;

/**
 * The Prime class calculates all prime numbers up to a given limit
 * using a provided PrimeChecker dependency.
 * Constructor injection is used to provide the PrimeChecker,
 * enabling flexible and testable prime validation.
 * The allPrime(int n) method returns an array of all primes from 2 to n (inclusive).
 */
public class Prime {
    private final PrimeChecker primeChecker;

    // 透過建構函數注入依賴
    public Prime(PrimeChecker primeChecker) {
        this.primeChecker = primeChecker;
    }

    public int[] allPrime(int n) {
        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (primeChecker.isPrime(i)) {
                primes.add(i);
            }
        }
        return primes.stream().mapToInt(Integer::intValue).toArray();
    }
}
