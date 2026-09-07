package u01_codebreak;

/**
 * 模擬由 AI（如 GPT-4 / Claude）秒速生成的電子錢包提款與轉帳服務。
 * 表面上邏輯完備、代碼簡潔，但在多執行緒與浮點數精度情境下隱藏嚴重缺陷。
 */
public class WalletService {
    private double balance;

    public WalletService(double initialBalance) {
        this.balance = initialBalance;
    }

    /**
     * 扣款/提款方法 (由 AI 生成)
     *
     * @param amount 提款金額
     * @return 提款成功回傳 true，餘額不足或金額不合法回傳 false
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }
        if (balance >= amount) {
            // 模擬真實資料庫存取或網路微小延遲 (10ms)
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            balance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }
}
