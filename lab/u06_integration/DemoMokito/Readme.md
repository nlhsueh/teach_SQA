
## Mockito 簡介

**Mockito** 是一個流行的 Java 測試框架，用於撰寫單元測試中的 **模擬對象** (mock objects)。模擬對象可以用來模擬實際對象的行為，而不需要真正實現這些對象或與外部依賴（如資料庫、Web 服務等）進行交互。

---

### 特點
1. **簡單易用**：以簡潔的 API 和直觀的語法著稱。
2. **支持模擬行為**：可以定義模擬對象的行為，例如指定方法返回值或拋出例外。
3. **驗證交互**：可以檢查模擬對象的方法是否被呼叫過，以及呼叫的參數是否正確。
4. **與 JUnit 完美結合**：與主流測試框架（JUnit）整合，提升測試效率。
5. **不需要實際依賴**：只需要模擬對象即可進行單元測試，無需配置實際的外部環境。


### 為什麼使用 Mock？
1. 避免與外部依賴交互：
   - 測試時不需要真正呼叫外部服務（如付款服務），避免測試過程中實際扣款或消耗資源。
   - 減少測試時間，提高執行效率。
2. 控制測試環境：
   - 可以模擬不同場景，例如付款成功、付款失敗、服務不可用等，方便測試各種情況。
3. 隔離測試對象：
   - 聚焦於測試目標類（如處理訂單的邏輯），而不是測試外部依賴。
4. 驗證與依賴的交互：
   - 檢查測試對象是否按照預期與依賴進行交互，例如正確的方法是否被呼叫、參數是否正確。

---

## 基本使用範例

### 1. 模擬行為

```java
    PrimeChecker mockChecker = mock(PrimeChecker.class);

    when(mockChecker.isPrime(2)).thenReturn(true);
    when(mockChecker.isPrime(3)).thenReturn(true);
    when(mockChecker.isPrime(4)).thenReturn(false);
    when(mockChecker.isPrime(5)).thenReturn(true);

    // 注入 mock 的依賴
    Prime prime = new Prime(mockChecker);

    // 測試結果
    int[] result = prime.allPrime(5);
    assertArrayEquals(new int[]{2, 3, 5}, result);
```

### 2. 驗證交互
```java
    verify(mockChecker, times(1)).isPrime(2);
```

### 3. 處理異常
```java
    when(calculatorService.div(6, 0)).thenThrow(Ill);

```

---

### 核心 API
1. **`mock()`**：建立模擬對象。
2. **`when()` / `thenReturn()`**：定義模擬方法的行為和返回值。
3. **`verify()`**：驗證模擬對象的方法是否被呼叫過。
4. **`doThrow()`**：模擬方法呼叫時丟出異常。
5. **`@Mock` / `@InjectMocks`**：使用注解管理模擬對象。

---

## Verify

在測試中，**驗證是否正確呼叫過方法**（如 `verify()`）的主要目的是確保**程式碼邏輯**和**行為**符合預期
* 確保與依賴的交互符合設計: 在許多應用中，核心邏輯需要與外部依賴（如服務、API、資料庫）進行交互，例如：
    - 向外部服務發送請求。
    - 從資料庫中讀取或寫入數據。
    - 呼叫第三方 API 完成任務。
如果這些依賴的交互行為不正確（例如，方法未被呼叫、錯誤的參數被傳遞），則整體功能可能會失效。

* 驗證的作用: 確保程式確實按照預期呼叫了正確的依賴方法，以及使用了正確的參數。
    - 假設我們有一個付款系統，只有在用戶的付款資料經過驗證後才會呼叫付款服務：
```java
verify(paymentService).processPayment(userId, amount);
```

如果 `processPayment()` 未被呼叫或參數不正確，測試將失敗，幫助你發現問題。


* 測試行為驅動的程式碼: 某些程式碼的行為並不依賴於返回值，而是依賴於方法的「執行過程」。 
    - 例如，事件處理器可能需要呼叫多個子系統，而每個子系統的結果對測試並不重要，但我們需要確保它們都被觸發。
    - 確認各個子系統都正確執行，且執行次數符合預期。
    - 假設有一個通知系統，它在完成任務後應通知用戶和記錄日誌：

```java
verify(notificationService).notifyUser(userId);
verify(logger).logEvent("Task completed for user: " + userId);
```

* 驗證業務邏輯執行的分支路徑: 當程式邏輯中有條件判斷（如 `if-else` 或 `switch`），需要確認是否選擇了正確的執行路徑。
    - 檢查特定情境下，是否只呼叫了符合條件的依賴方法。
    - 假設系統會根據用戶的訂單狀態執行不同操作：
```java
if (order.isPaid()) {
    paymentService.refund(orderId);
} else {
    paymentService.cancelPayment(orderId);
}
```
測試不同的條件，驗證適當的方法被呼叫：
```java
verify(paymentService).refund(orderId); // 當 order.isPaid() 為 true
verify(paymentService).cancelPayment(orderId); // 當 order.isPaid() 為 false
```

* 避免多餘的呼叫： 未經設計的額外呼叫可能導致效率問題或錯誤結果（如重複寫入資料庫、重複發送請求）。驗證呼叫次數可以幫助確認邏輯不會多餘執行。
    - 確認方法只執行了預期的次數。
    - 假設某功能只能在用戶首次登入時初始化用戶配置：
```java
verify(userService, times(1)).initializeUserConfig(userId);
```

如果被呼叫了多次，測試會失敗。

* 幫助測試間接影響: 有時候，被測試的程式碼本身不直接返回結果，而是通過方法呼叫對外部系統產生影響。此時，無法僅依靠斷言檢查結果，而需要檢查呼叫行為。

---

## `paymentService` Example

### 測試目標
假設有一個 `OrderProcessor` 類負責處理訂單，並需要呼叫 `paymentService` 來完成付款。測試的目的是確認在不同情況下，`paymentService` 被正確地呼叫。

### 程式碼範例
1. 主程式
```java
public class OrderProcessor {
    private PaymentService paymentService;

    public OrderProcessor(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void processOrder(Order order) {
        if (order.isPaid()) {
            paymentService.refund(order.getId());
        } else {
            paymentService.cancelPayment(order.getId());
        }
    }
}

public interface PaymentService {
    void processPayment(int userId, double amount);
    void refund(int orderId);
    void cancelPayment(int orderId);
}

```

2. **測試**
```java
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

public class OrderProcessorTest {

    @Test
    public void testProcessOrder() {
        // 建立模擬的 PaymentService
        PaymentService paymentService = mock(PaymentService.class);

        // 測試對象
        OrderProcessor orderProcessor = new OrderProcessor(paymentService);

        // 模擬已付款訂單
        Order paidOrder = new Order(1, true);
        orderProcessor.processOrder(paidOrder);

        // 驗證退款方法被呼叫
        verify(paymentService).refund(1);

        // -----

        // 模擬未付款訂單
        Order unpaidOrder = new Order(2, false);
        orderProcessor.processOrder(unpaidOrder);

        // 驗證取消付款方法被呼叫
        verify(paymentService).cancelPayment(2);
    }
}
```

#### 模擬對象的作用
1. **模擬 `PaymentService` 的行為**：用 `mock(PaymentService.class)` 創建模擬對象。
2. **避免與真實系統交互**：`paymentService` 並不會真的發起付款請求。
3. **驗證呼叫行為**：
   - 確認 `refund()` 方法只在已付款訂單中被呼叫。
   - 確認 `cancelPayment()` 方法只在未付款訂單中被呼叫。

## Lab: 斜坡上的力

有一顆質量為 5kg 的球，靜止放在一個光滑的斜面上，斜面的傾角為 37。假設重力加速度 9.8。

1. 求球在斜坡上的 **法向力**。
2. 求球沿斜坡向下的 **滑動分力**。

### 題目提示：
- 假設 sin(37), cos(37) 分別為 0.6, 0.7 (用 mock)
- **法向力**：斜面對物體的支持力。(`mg*cos()`)
- **滑動分力**：重力在斜坡方向上的分量。(`m*g*sin()`)

---


