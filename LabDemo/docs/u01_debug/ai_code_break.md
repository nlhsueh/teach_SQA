# AI 程式碼破壞實驗 (AI Code Attack & Reliability Lab)

> 🎯 **實習目標**：
> 1. 體驗生成式 AI 產出的「看似完美、語法正確」之程式碼，在生產環境中潛伏的致命陷阱。
> 2. 透過多執行緒並發注入（Concurrency Race Condition）與浮點數精度累加（Precision Loss），親手「攻破」AI 程式碼。
> 3. 理解為什麼「軟體品質保證 (SQA)」是 AI 時代資工系工程師最無可取代的核心能力。

---

## 1. 實驗背景：AI 生成的銀行電子錢包服務

假設某開發團隊使用 AI（GPT-4 / Claude）秒速生成了一套電商系統的「電子錢包提款與轉帳服務 (`WalletService.java`)」：

```java
package u01_codebreak;

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

## 2. 實驗任務一：破壞 AI 程式碼的 Happy Path 單元測試

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

* **現象**：單元測試 100% 綠燈通過！程式碼覆蓋率 100%！
* **致命盲區**：這是一個單執行緒順序測試，完全忽略了並發環境下的狀態保護。

---

## 3. 實驗任務二：發動多執行緒並發攻擊 (Concurrency Race Attack)

請撰寫並執行破壞性測試腳本 [`ConcurrentAttackTest.java`](#)：

```java
package u01_codebreak;

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
3. [ ] 完成程式碼重構，使多執行緒攻擊測試與精度測試全部轉為「穩定綠燈」。
4. [ ] 撰寫簡短實驗心得：討論 AI 寫程式的優勢與致命盲點。

---

## 課堂互動與概念檢核

<!-- id: sqa-u01-codebreak-ccq1 -->
#### 🙋 **概念核對問答 (CCQ 1)：AI 寫程式與單元測試的「自我印證盲區」**



**問題**

工程師使用 LLM 快速生成了電子錢包扣款邏輯，接著又請同一個 AI 為該方法生成單元測試。測試執行結果呈現 100% 綠燈通過，且涵蓋率高達 100%。然而一上線面對促銷搶購的高並發情境，帳戶卻瞬間被超賣穿透、餘額變成負數破產。依據軟體測試與 SQA 原則，這主要體現了何種核心問題？

A) 殺蟲劑悖論與 Happy Path 偏誤：AI 依據自身單執行緒、循序的靜態語意設計測試，導致測試案例與被測程式碼「共同錯在同一個並發與時間交錯盲區」，帶來極度危險的假安全感  
B) 測試原則宣告「窮盡測試是不可能的」，因此線上故障純屬無法預防的偶發機率  
C) 單元測試執行次數太少，若在單執行緒環境下重複跑 10 萬次 Happy Path 就必定能測出並發問題  
D) 這是作業系統與 CPU 硬體的暫存器故障，與軟體測試品質無關  

<details>
<summary>點擊查看【概念核對問答】答案與解析</summary>

**正確答案：A**

* **解析**：
  * **選項 A 正確**：這正是 AI 輔助開發最嚴重的技術債盲區。AI 生成的程式碼在單執行緒（Happy Path）下看似無懈可擊，若再由 AI 自己出測試，AI 只會針對它所預想的正常路徑設計測試案例。測試與程式碼存在相同的盲點，產生「100% 綠燈的假安全感」，唯有人類工程師主動注入多執行緒競爭（Race Condition）與邊界攻擊，才能破除此盲區。
  * **選項 B/C/D 錯誤**：並發問題需要專門的並發測試（如利用 `CountDownLatch` 瞬間鳴槍起跑）才能觸發，單純重複單執行緒 Happy Path 永遠無法重現問題。

</details>

---

[課堂互動](https://nlhsueh.github.io/nickedupocket/#/student/sqa-u01-codebreak-ccq1)

<!-- id: sqa-u01-codebreak-ccq2 -->
#### 🙋 **概念核對問答 (CCQ 2)：並發防禦順序（Check-Then-Act 與 TOCTOU 漏洞）**



**問題**

在修復 `WalletService` 的並發扣款缺陷時，某同學將程式碼改成如下：
```java
public boolean withdraw(double amount) {
    if (amount <= 0) return false;
    if (balance >= amount) { // 👈 步驟 1：先在鎖定外面檢查餘額
        synchronized (this) { // 👈 步驟 2：只在扣款瞬間加鎖
            balance -= amount;
            return true;
        }
    }
    return false;
}
```
請問這段程式碼在面對多執行緒並發攻擊時，能否有效防止帳戶餘額被扣成負數？

A) 可以，因為 `balance -= amount` 已經被 `synchronized` 區塊保護，保證了記憶體寫入的原子性  
B) 不能，因為「檢查餘額」發生在取得鎖定之前，多個執行緒仍可同時通過 `balance >= amount` 的檢查，隨後依序排隊進去把餘額扣成負數（典型 TOCTOU 漏洞）  
C) 可以，因為 JVM 會自動將外層的 `if` 條件與內層的 `synchronized` 區塊智慧合併鎖定  
D) 不能，因為 `synchronized` 只能修飾整個方法，不能以程式碼區塊（Block）形式使用  

<details>
<summary>點擊查看【概念核對問答】答案與解析</summary>

**正確答案：B**

* **解析**：
  * **選項 B 正確**：這屬於經典的 **TOCTOU（Time-of-Check to Time-of-Use）** 漏洞。當帳戶餘額為 $100，有 5 個執行緒同時進入該方法時，大家都在鎖定區之外檢查並判定「餘額 >= $100 成立」；接著這 5 個執行緒雖然排隊進入 `synchronized`，但因為已經通過檢查，每個執行緒都會扣款一次，最終餘額變成 -$400！**正確做法必須「先鎖定 ➔ 再檢查 ➔ 後修改」**，將檢查與修改完整包進同一個臨界區段。
  * **選項 A 錯誤**：雖然扣款本身不會有變數寫入衝突，但業務邏輯狀態（餘額不為負）已被破壞。
  * **選項 C/D 錯誤**：JVM 不會自動合併鎖定；`synchronized (this)` 是合法且標準的區塊語法。

</details>

---

[課堂互動](https://nlhsueh.github.io/nickedupocket/#/student/sqa-u01-codebreak-ccq2)

<!-- id: sqa-u01-codebreak-ccq3 -->
#### 🙋 **概念核對問答 (CCQ 3)：金融數值精度與型別防禦**



**問題**

在 Java 金融交易、電子錢包與電商購物車系統中，處理金額加減與結算時，為什麼業界軟體工程標準「強烈禁止」直接使用 `double` 或 `float`？其品質工程防禦方案為何？

A) 因為 `double` 只能儲存正數，無法表示扣款後的負數餘額  
B) 因為 IEEE 754 二進位浮點數無法精確表示 `0.1` 等十進位小數，頻繁累加會產生微小截斷偏差導致帳目不平；應全面改採 `BigDecimal`（且須使用字串建構子 `new BigDecimal("0.1")`）或以最小貨幣單位（如整數分、厘）的 `long` 儲存  
C) 因為 `double` 運算需要龐大 CPU 浮點數處理單元，在高並發時會導致作業系統當機  
D) 因為主流關聯式資料庫（如 PostgreSQL、MySQL）不支援儲存任何小數型別  

<details>
<summary>點擊查看【概念核對問答】答案與解析</summary>

**正確答案：B**

* **解析**：
  * **選項 B 正確**：十進位小數（如 0.1、0.2）轉換成二進位時是無限循環小數，以 `double`（64-bit IEEE 754）儲存必定存在微小的截斷捨入誤差。百萬次累加或跨幣別換算後會產生實質差額，造成嚴重的稽核帳目不符。防禦方案是使用專門的高精度數值類別 `BigDecimal`，或是統一將金額以最小貨幣單位（例如：台幣以「元」、美金以「分 (Cent)」）轉為整數 `long` 進行計算。
  * **選項 A/C/D 錯誤**：皆非禁止使用浮點數的真實原因。

</details>

[課堂互動](https://nlhsueh.github.io/nickedupocket/#/student/sqa-u01-codebreak-ccq3)
