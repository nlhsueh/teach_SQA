package u02_robust.exception;

/**
 * 實習練習題：銀行帳戶提款與自訂例外 (BankAccountPractice)
 *
 * <p>【練習主題】：對應實習手冊 Ex03 帳戶提款異常。</p>
 * <p>【核心任務】：</p>
 * <ol>
 *   <li>設計自訂受檢例外 <code>InsufficientFundsException</code>（餘額不足）。</li>
 *   <li>在 <code>withdraw(double amount)</code> 方法中，若提領金額大於現有餘額，拋出自訂例外。</li>
 *   <li>在 <code>main</code> 方法中展示嘗試超額提領並捕捉處理自訂例外的完整流程。</li>
 * </ol>
 */
public class BankAccountPractice {

    /**
     * 自訂受檢例外：餘額不足例外
     */
    public static class InsufficientFundsException extends Exception {
        private final String accountNumber;
        private final double currentBalance;
        private final double attemptedAmount;

        public InsufficientFundsException(String accountNumber, double currentBalance, double attemptedAmount) {
            super(String.format("帳號 %s 提款失敗：欲提領金額 %.2f 超過目前餘額 %.2f (短缺 %.2f 元)",
                    accountNumber, attemptedAmount, currentBalance, (attemptedAmount - currentBalance)));
            this.accountNumber = accountNumber;
            this.currentBalance = currentBalance;
            this.attemptedAmount = attemptedAmount;
        }

        public String getAccountNumber() { return accountNumber; }
        public double getCurrentBalance() { return currentBalance; }
        public double getAttemptedAmount() { return attemptedAmount; }
        public double getDeficit() { return attemptedAmount - currentBalance; }
    }

    /**
     * 銀行帳戶類別
     */
    public static class BankAccount {
        private final String accountNumber;
        private double balance;

        public BankAccount(String accountNumber, double initialBalance) {
            if (accountNumber == null || accountNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("帳號名稱不可為空");
            }
            if (initialBalance < 0) {
                throw new IllegalArgumentException("初始餘額不能為負數");
            }
            this.accountNumber = accountNumber;
            this.balance = initialBalance;
        }

        public synchronized void deposit(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("存款金額必須大於 0");
            }
            this.balance += amount;
        }

        /**
         * 提款操作。
         *
         * @param amount 提領金額
         * @throws IllegalArgumentException 若提領金額 <= 0
         * @throws InsufficientFundsException 若提領金額大於現有餘額
         */
        public synchronized void withdraw(double amount) throws InsufficientFundsException {
            // 公開參數防禦
            if (amount <= 0) {
                throw new IllegalArgumentException("提款金額必須大於 0，輸入值: " + amount);
            }

            // TODO: 練習 - 檢查餘額
            // 若提領金額 amount 大於現有餘額 this.balance，請拋出自訂例外 InsufficientFundsException
            // 請在此撰寫檢查與拋出例外的程式碼...


            this.balance -= amount;
        }

        public String getAccountNumber() { return accountNumber; }
        public synchronized double getBalance() { return balance; }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" 實習練習：銀行帳戶提款自訂例外處理");
        System.out.println("==================================================");

        BankAccount account = new BankAccount("ACC-9527", 5000.0);
        System.out.printf("開戶完成：帳號 %s，初始餘額: %.2f 元%n",
                account.getAccountNumber(), account.getBalance());

        // 測試 1：正常存款
        account.deposit(1000.0);
        System.out.printf("存款 1000 元成功，最新餘額: %.2f 元%n", account.getBalance());

        // 測試 2：正常提款
        try {
            System.out.println("\n[操作 1] 嘗試提款 2000 元...");
            account.withdraw(2000.0);
            System.out.printf(">> 提款成功！最新餘額: %.2f 元%n", account.getBalance());
        } catch (InsufficientFundsException e) {
            System.err.println(">> 提款失敗: " + e.getMessage());
        }

        // 測試 3：超額提款（預期觸發 InsufficientFundsException）
        try {
            System.out.println("\n[操作 2] 嘗試提款 6000 元（餘額只有 4000 元）...");
            account.withdraw(6000.0);
            System.out.println(">> 提款成功！");
        } catch (InsufficientFundsException e) {
            System.err.println("🛑 成功捕捉自訂受檢例外 (InsufficientFundsException)！");
            System.err.println("   錯誤說明: " + e.getMessage());
            System.err.printf("   [除錯資訊] 目前餘額: %.2f 元，短缺金額: %.2f 元%n",
                    e.getCurrentBalance(), e.getDeficit());
        }

        // 測試 4：非法負數參數防禦
        try {
            System.out.println("\n[操作 3] 嘗試提款負數金額 -500 元...");
            account.withdraw(-500.0);
        } catch (IllegalArgumentException e) {
            System.out.println(">> 成功攔截非法參數 (IllegalArgumentException): " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.err.println("不應走到此處: " + e.getMessage());
        }
    }
}
