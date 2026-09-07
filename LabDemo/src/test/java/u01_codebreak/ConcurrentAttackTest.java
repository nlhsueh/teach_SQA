package u01_codebreak;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 實習 01：AI 代碼破壞實驗測試腳本
 */
public class ConcurrentAttackTest {

    @Test
    @DisplayName("任務一：Happy Path 單元測試 (AI 通常生成的測試)")
    void testWithdrawHappyPath() {
        WalletService wallet = new WalletService(1000.0);
        boolean result = wallet.withdraw(200.0);
        assertTrue(result, "提款 200 應該成功");
        assertEquals(800.0, wallet.getBalance(), 0.001, "餘額應為 800.0");
    }

    @Test
    @DisplayName("任務二：多執行緒並發攻擊 (Race Condition Attack)")
    void launchConcurrencyAttack() throws InterruptedException {
        // 初始資金只有 $1,000
        WalletService wallet = new WalletService(1000.0);

        int threadCount = 50;
        double withdrawAmount = 100.0; // 50 個人同時搶提 $100 (總需求 $5,000)

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await(); // 讓 50 個執行緒在同一個毫秒瞬間同時起跑！
                    wallet.withdraw(withdrawAmount);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown(); // 鳴槍起跑！
        boolean completed = doneLatch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        System.out.println("====== 攻擊結果 ======");
        System.out.println("是否所有執行緒皆執行完畢：" + completed);
        System.out.println("初始餘額：$1000.0");
        System.out.println("預期合法最終餘額：$0.0 (最多只能被提領 10 次)");
        System.out.println("實際最終餘額：$" + wallet.getBalance());

        // 狀態不變量 (Class Invariant)：帳戶餘額絕不能小於 0！
        assertTrue(wallet.getBalance() >= 0.0, "💥 攻擊成功！帳戶發生超賣穿透，餘額小於 0！實際餘額: " + wallet.getBalance());
    }

    @Test
    @DisplayName("任務三：浮點數精度累積破壞 (Precision Loss Attack)")
    void launchFloatingPointAttack() {
        double sum = 0.0;
        // 連續存入 0.1 元 1,000,000 次
        for (int i = 0; i < 1_000_000; i++) {
            sum += 0.1;
        }
        System.out.println("預期金額：100000.0");
        System.out.println("實際累計金額：" + sum);
        System.out.println("浮點數偏差：" + (sum - 100000.0));

        // 在金融系統中，0.1 在二進位中是無限循環小數，累計一百萬次將產生顯著差額！
        assertEquals(100000.0, sum, "💥 浮點數精度丟失，帳目不平！");
    }
}
