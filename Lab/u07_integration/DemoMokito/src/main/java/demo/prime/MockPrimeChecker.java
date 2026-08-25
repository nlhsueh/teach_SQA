package demo.prime;

/**
 * This is a stub (mock) prime checker, just for integration.
 */
public class MockPrimeChecker implements PrimeChecker {
    public boolean isPrime(int n) {
        if (n<2) return false;
        if (n==2) return true;
        if (n==3) return true;
        if (n==5) return true;

        return false;
    }
}
