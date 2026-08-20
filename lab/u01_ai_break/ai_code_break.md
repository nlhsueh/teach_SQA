# 實習 01：AI 代碼破壞實驗 (AI Code Attack & Reliability Lab)

> 🎯 **實習目標**：
> 1. 體驗生成式 AI 產出的「看似完美、語法正確」之程式碼，在生產環境中潛伏的致命陷阱。
> 2. 透過多執行緒並發注入（Concurrency Race Condition）與浮點數精度累加（Precision Loss），親手「攻破」AI 代碼。
> 3. 理解為什麼「軟體品質保證 (SQA)」是 AI 時代資工系工程師最無可取代的核心能力。

---

## 1. 實驗背景：AI 生成的銀行電子錢包服務

假設某開發團隊使用 AI（GPT-4 / Claude）秒速生成了一套電商系統的「電子錢包提款與轉帳服務 (`WalletService.java`)」：

```java
package lab.sqa;

public class WalletService {
    private double balance;

    public WalletService(double initialBalance) {
        this.balance = initialBalance;
    }

    /**
     * 扣款/提款方法 (由 AI 秒速生成)
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
```

---

## 2. 實驗任務一：破壞 AI 代碼的 Happy Path 單元測試

如果你讓 AI 幫你寫單元測試，AI 通常會產生如下的測試：

```java
@Test
void testWithdrawHappyPath() {
    WalletService wallet = new WalletService(1000.0);
    boolean result = wallet.withdraw(200.0);
    assertTrue(result);
    assertEquals(800.0, wallet.getBalance());
}
```

* **現象**：單元測試 100% 綠燈通過！代碼覆蓋率 100%！
* **致命盲區**：這是一個單執行緒順序測試，完全忽略了並發環境下的狀態保護。

---

## 3. 實驗任務二：發動多執行緒並發攻擊 (Concurrency Race Attack)

請撰寫並執行破壞性測試腳本 [`ConcurrentAttackTest.java`](#)：

```java
package lab.sqa;

import org.junit.jupiter.api.Test;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

public class ConcurrentAttackTest {

    @Test
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
        doneLatch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        System.out.println("====== 攻擊結果 ======");
        System.out.println("初始餘額：$1000.0");
        System.out.println("預期合法最終餘額：$0.0 (最多只能被提領 10 次)");
        System.out.println("實際最終餘額：$" + wallet.getBalance());

        // 狀態不變量 (Class Invariant)：帳戶餘額絕不能小於 0！
        assertTrue(wallet.getBalance() >= 0.0, "💥 攻擊成功！帳戶發生超賣穿透，餘額小於 0！");
    }
}
```

### 💣 觀察攻擊現象：
* 執行測試後，你會發現測試紅燈報錯，最終餘額變成了 **$-3000.0$ 甚至 $-4000.0$**！
* 50 個執行緒同時讀到了 `balance >= amount` 為 true，並發扣款導致公司被掏空資產。

---

## 4. 實驗任務三：浮點數精度累積破壞 (Precision Loss Attack)

現代金融系統若使用 `double` 進行金額累加，會發生愛國者飛彈等級的浮點數截斷偏差：

```java
@Test
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
```

---

## 5. 學生動手修復與防禦 (Defensive Fixes)

請同學重構 `WalletService.java`，建立雙重安全防線：
1. **並發防禦**：使用 `synchronized`、`ReentrantLock` 或原子變數 (`AtomicReference` / 資料庫樂觀鎖)。
2. **精度防禦**：將金額型態全面重構為 `BigDecimal` 或整數分（Cents）。
3. **前置條件與狀態不變量**：加入 `Preconditions.checkArgument` 與 `assert getBalance() >= 0`。

---

## 📋 實習成果驗收標準
1. [ ] 成功執行並發破壞腳本，觀察到負數餘額（截圖記錄）。
2. [ ] 成功執行浮點數累加腳本，觀察到截斷偏差（截圖記錄）。
3. [ ] 完成代碼重構，使多執行緒攻擊測試與精度測試全部轉為「穩定綠燈」。
4. [ ] 撰寫簡短實驗心得：討論 AI 寫代碼的優勢與致命盲點。
