package u01_codebreak;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 實習 01：AI 程式碼破壞實驗示範程式 (包含 main 方法可直接執行)
 */
public class ConcurrentAttackDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(">>> 執行多執行緒並發穿透攻擊示範...");
        runConcurrencyAttack();

        System.out.println("\n>>> 執行浮點數精度偏差示範...");
        runFloatingPointAttack();
    }

    public static void runConcurrencyAttack() throws InterruptedException {
        WalletService wallet = new WalletService(1000.0);
        int threadCount = 50;
        double withdrawAmount = 100.0;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await();
                    wallet.withdraw(withdrawAmount);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        doneLatch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        System.out.println("====== 並發攻擊結果 ======");
        System.out.println("初始帳戶餘額：$1000.0");
        System.out.println("提款請求總額：$" + (threadCount * withdrawAmount) + " (50 人各提 $100)");
        System.out.println("預期合法最終餘額：$0.0 (最多只能成功 10 次)");
        System.out.println("實際最終餘額：$" + wallet.getBalance());
        if (wallet.getBalance() < 0.0) {
            System.out.println("💥 [CRITICAL] 攻擊成功！帳戶發生嚴重超賣穿透，餘額已成負數！");
        } else {
            System.out.println("✅ 防禦成功，餘額未小於 0。");
        }
    }

    public static void runFloatingPointAttack() {
        double sum = 0.0;
        for (int i = 0; i < 1_000_000; i++) {
            sum += 0.1;
        }
        System.out.println("====== 浮點數精度累加結果 ======");
        System.out.println("存入 0.1 元共 1,000,000 次");
        System.out.println("數學預期金額：100000.0");
        System.out.println("實際累計金額：" + sum);
        System.out.println("浮點數累積偏差：" + (sum - 100000.0));
    }
}
