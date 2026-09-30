package u02_robust.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BankAccountPractice 銀行帳戶自訂例外測試")
class BankAccountPracticeTest {

    @Test
    @DisplayName("測試正常存提款流程")
    void testDepositAndWithdraw() throws BankAccountPractice.InsufficientFundsException {
        BankAccountPractice.BankAccount account = new BankAccountPractice.BankAccount("ACC-001", 1000.0);
        account.deposit(500.0);
        assertEquals(1500.0, account.getBalance());

        account.withdraw(800.0);
        assertEquals(700.0, account.getBalance());
    }

    @Test
    @DisplayName("測試提款超額拋出 InsufficientFundsException")
    void testOverdrawThrowsException() {
        BankAccountPractice.BankAccount account = new BankAccountPractice.BankAccount("ACC-002", 500.0);

        BankAccountPractice.InsufficientFundsException ex = assertThrows(
                BankAccountPractice.InsufficientFundsException.class,
                () -> account.withdraw(1200.0)
        );

        assertEquals("ACC-002", ex.getAccountNumber());
        assertEquals(500.0, ex.getCurrentBalance());
        assertEquals(1200.0, ex.getAttemptedAmount());
        assertEquals(700.0, ex.getDeficit());
    }

    @Test
    @DisplayName("測試非法金額參數防禦")
    void testInvalidAmountThrowsException() {
        BankAccountPractice.BankAccount account = new BankAccountPractice.BankAccount("ACC-003", 1000.0);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100.0));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0.0));
    }
}
