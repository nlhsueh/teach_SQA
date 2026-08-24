# 實習 06：屬性基礎測試 (Property-Based Testing with jqwik)

> 🎯 **實習目標**：
> 1. 告別傳統手寫 `assertEquals(expected, actual)` 的單點測資思維。
> 2. 學習定義系統的**「狀態不變量 (Invariants)」與「數學代數性質」**。
> 3. 使用現代 Java 屬性測試框架 **`jqwik`**，讓電腦自動生成 **10,000 組極端隨機測資** 進行暴力破壞，並親身體驗自動**「縮小化 (Shrinking)」** 找出最小重現案例！

---

## 1. 什麼是屬性基礎測試 (Property-Based Testing)?

| 傳統基於範例的測試 (Example-Based) | 現代屬性基礎測試 (Property-Based) |
| :--- | :--- |
| 工程師手動想 3~5 組測試資料（如 1, 2, 5） | 工程師定義**「不管輸入什麼，系統都必須滿足的通用法則」** |
| 容易遺漏未知的極端邊界（如 `-1`, `Integer.MIN_VALUE`, 空字串, Emoji） | 測試框架隨機生成 **1,000 ~ 10,000 組** 邊界資料進行模糊破壞 |
| 抓出 Bug 時給出長達數萬字的崩潰資料，難以排查 | 框架自動進行 **Shrinking（縮小化）**，精簡出導致錯誤的最小輸入 |

---

## 2. Maven 依賴配置 (`pom.xml`)

在專案的 `pom.xml` 中引入 `jqwik`（支援 JUnit 5 引擎）：

```xml
<dependency>
    <groupId>net.jqwik</groupId>
    <artifactId>jqwik</artifactId>
    <version>1.8.5</version>
    <scope>test</scope>
</dependency>
```

---

## 3. 核心概念實戰：四大不變量設計模式

### 模式 1：可逆性 (Round-trip / Inverse Property)
> **法則**：資料編碼後再解碼，必須等於原本的資料。

```java
package lab.sqa.pbt;

import net.jqwik.api.*;
import java.util.Base64;
import static org.junit.jupiter.api.Assertions.*;

public class Base64Properties {

    @Property
    void encodeThenDecodeShouldYieldOriginalString(@ForAll String original) {
        String encoded = Base64.getEncoder().encodeToString(original.getBytes());
        String decoded = new String(Base64.getDecoder().decode(encoded));
        
        assertEquals(original, decoded, "解碼後必須與原字串完全一致！");
    }
}
```

---

### 模式 2：排序不變量 (Sorting Invariants)
> **法則**：
> 1. 排序後的長度必須等於原始陣列長度。
> 2. 排序後的相鄰元素必須滿足 $arr[i] \le arr[i+1]$。
> 3. 排序後的元素多重集合 (Multiset) 必須與原始元素完全相同（不可憑空捏造或丟失數字）。

```java
package lab.sqa.pbt;

import net.jqwik.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class SortProperties {

    @Property
    void sortedListMustBeOrderedAndSameSize(@ForAll List<Integer> originalList) {
        List<Integer> sortedList = new ArrayList<>(originalList);
        Collections.sort(sortedList);

        // 不變量 1：長度相等
        assertEquals(originalList.size(), sortedList.size());

        // 不變量 2：相鄰元素單調遞增
        for (int i = 0; i < sortedList.size() - 1; i++) {
            assertTrue(sortedList.get(i) <= sortedList.get(i + 1), "排序未滿足遞增法則！");
        }
    }
}
```

---

### 模式 3：冪等性 (Idempotence)
> **法則**：執行一次與連續執行兩次的結果完全相同（例如 `cleanString(cleanString(s)) == cleanString(s)` 或購物車結算去重）。

```java
@Property
void trimmingIsIdempotent(@ForAll String s) {
    String once = s.trim();
    String twice = once.trim();
    assertEquals(once, twice);
}
```

---

## 4. 實習任務：用 jqwik 抓出隱藏演算法 Bug ＆ 體驗 Shrinking

### 🐛 缺陷演算法：錯誤的二分搜尋法
給定一個看似正確但在大數字運算時會發生整數溢位的 `BinarySearch` 實作：

```java
public class BrokenBinarySearch {
    public static int search(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            // 💥 致命 Bug：當 low + high 超過 Integer.MAX_VALUE 時會溢位變成負數！
            int mid = (low + high) / 2; 
            if (arr[mid] == target) return mid;
            else if (arr[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }
}
```

### 🧪 撰寫屬性測試驗證：
```java
@Property
void ifElementExistsInSortedArrayItMustBeFound(
    @ForAll("sortedHugeArrays") int[] arr,
    @ForAll("validIndex") int targetIndex
) {
    int target = arr[targetIndex];
    int foundIndex = BrokenBinarySearch.search(arr, target);
    
    assertTrue(foundIndex >= 0);
    assertEquals(target, arr[foundIndex]);
}

@Provide
Arbitrary<int[]> sortedHugeArrays() {
    return Arbitraries.integers().between(0, 1000)
        .array(int[].class).ofMinSize(10).ofMaxSize(100_000)
        .map(a -> { Arrays.sort(a); return a; });
}
```

### 🔍 觀察 Shrinking 縮小化神奇過程：
* 執行測試時，`jqwik` 隨機產生了包含數萬個元素的陣列引爆了 `(low + high) / 2` 溢位崩潰。
* 隨後 `jqwik` 自動進行數十次二分縮減，最終印出簡潔的最小失敗測資：
  $$\text{Shrunk sample: } \text{low}=1073741824, \text{high}=1073741824 \implies \text{IndexOutOfBoundsException}$$

---

## 📋 實習成果驗收標準
1. [ ] 成功配置 `jqwik` 並執行 Base64 與 List 排序的 1,000 組隨機屬性測試。
2. [ ] 為專案中的「購物車折扣計算器」或「區間重疊判斷函式」設計至少 3 個數學不變量屬性。
3. [ ] 成功使用 `jqwik` 抓出 `BrokenBinarySearch` 的整數溢位 Bug，並截圖 Shrinking 縮小化輸出日誌。
4. [ ] 修正 `BrokenBinarySearch` 中的 `(low + high) >>> 1`，使 10,000 筆屬性測試全部綠燈通過！
