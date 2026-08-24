# 實習 13：模糊測試 (Fuzzing with Jazzer) ＆ 混沌工程故障注入 (Chaos Engineering)

> 🎯 **實習目標**：
> 1. 掌握主動破壞的極致技術——**以模糊測試 (Fuzzing) 自動尋找讓 Java 虛擬機 (JVM) 崩潰或產生未受檢驗例外的惡意輸入**。
> 2. 實踐 **混沌工程 (Chaos Engineering)** 理念，在微服務中動態注入網路延遲、隨機故障與異常中斷，檢驗系統的**容錯度 (Fault Tolerance) 與自癒力 (Resilience)**。

---

## Part 1：JVM 覆蓋率導向模糊測試 (Coverage-guided Fuzzing with Jazzer)

### 1. 什麼是 Jazzer？
Jazzer 是由 Google 開源、專門針對 Java/JVM 生態系的覆蓋率導向模糊測試工具（基於 libFuzzer）。
* 它能利用位元組碼插樁（Bytecode Instrumentation），**自動分析程式碼分支走向，精準突變出能觸發深層隱藏路徑與例外崩潰的輸入 Byte 陣列**！

### 2. 實戰演練：對解析器 (Parser) 進行模糊測試

假設我們有一個字串與 JSON 解析模組：

```java
package lab.sqa.fuzz;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public class SecurityParserFuzzer {

    // Jazzer 模糊測試入口點
    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String input = data.consumeRemainingAsString();
        
        try {
            // 呼叫受測方法：自訂複雜字串/XML/JSON 解析器
            MyCustomParser.parse(input);
        } catch (IllegalArgumentException expected) {
            // 業務預期的合法例外，Pass
        }
        // 💥 若拋出 NullPointerException, ArrayIndexOutOfBoundsException, 
        // 甚至是 OutOfMemoryError / StackOverflowError，Jazzer 會立刻攔截並產出 Crash Payload！
    }
}
```

---

## Part 2：微服務混沌故障注入 (Chaos Engineering & Fault Injection)

在期末紅藍攻防對抗中，紅軍將扮演 Chaos Monkey 主動注入混亂，藍軍系統必須具備自我保護防線（重試、斷路器 Circuit Breaker、降級）。

### 1. 使用 Resilience4j 與 AOP 模擬混沌故障

```java
@Service
public class OrderProcessingService {

    @Autowired
    private PaymentGatewayClient paymentClient;

    // 設定斷路器與重試防禦
    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    @Retry(name = "paymentService")
    public OrderResult processOrder(Order order) {
        return paymentClient.charge(order);
    }

    // 降級處理：當外部支付系統崩潰時，系統不能直接掛掉，而是進入待確認隊列
    public OrderResult paymentFallback(Order order, Throwable t) {
        return new OrderResult(order.getId(), OrderStatus.PENDING_ASYNC_VERIFICATION, "支付閘道繁忙，已進入非同步隊列");
    }
}
```

### 2. 混沌攻擊情境演練：
1. **注入 3000ms 網路延遲**：驗證系統是否能及時觸發 Timeout 並降級，而非卡死全部執行緒。
2. **模擬 50% 隨機拋出 `SocketTimeoutException`**：驗證重試機制 (`@Retry`) 是否能平穩自癒。

---

## 📋 實習成果驗收標準
1. [ ] 撰寫一個 Jazzer Fuzzer 入口點，針對包含字串分割或正則表達式的函式進行 100,000 次模糊攻擊。
2. [ ] 在服務中配置 Resilience4j Circuit Breaker 斷路器，並撰寫測試驗證當下游客戶端注入異常時，系統能自動熔斷並執行 Fallback 降級處理。
