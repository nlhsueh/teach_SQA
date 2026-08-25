
# Binary search

二分搜尋 (Binary Search) 是一個純粹的業務邏輯，用來展示 Spring Boot 整合測試如何驗證 **Web 層 (Controller)** 與 **服務層 (Service)** 之間的協作是正確的。

在這個案例中，我們將使用 `MockMvc` 來模擬 HTTP 請求，並確保資料的傳輸（JSON 序列化/反序列化）和服務的邏輯結果能正確地返回給客戶端。

## 🔍 Spring Boot 整合測試範例：二分搜尋 API

我們將測試一個 `POST /api/search/binary` 接口。

### 1\. 應用程式結構（模型、服務、控制器）

#### 1.1 請求 DTO (`SearchRequest.java`)

```java
package comexample.demo.dto;

public class SearchRequest {
    private int[] data;
    private int key;
    // 構造函數、Getter 和 Setter (為簡潔省略)
    
    public SearchRequest() {}

    public SearchRequest(int[] data, int key) {
        this.data = data;
        this.key = key;
    }
    
    // ... Getters and Setters ...
    public int[] getData() { return data; }
    public int getKey() { return key; }
}
```

#### 1.2 服務層 (`SearchService.java`)

這是包含核心二分搜尋邏輯的地方。

```java
package comexample.demo.service;

import org.springframework.stereotype.Service;

@Service
public class SearchService {
    
    public int binarySearch(int[] sortedArray, int key) {
        int low = 0;
        int high = sortedArray.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (sortedArray[mid] == key) {
                return mid; // 找到，回傳索引
            } else if (sortedArray[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1; // 找不到
    }
}
```

#### 1.3 控制器 (`SearchController.java`)

```java
package comexample.demo.controller;

import comexample.demo.dto.SearchRequest;
import comexample.demo.service.SearchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/binary")
    public int findIndex(@RequestBody SearchRequest request) {
        return searchService.binarySearch(request.getData(), request.getKey());
    }
}
```

### 2\. 整合測試程式碼

我們使用 `@SpringBootTest` 啟動所有組件，並使用 `MockMvc` 來驗證 HTTP 接口的運作。

#### `BinarySearchIntegrationTest.java`

```java
package comexample.demo.test;

import comexample.demo.dto.SearchRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 關鍵註解：啟動完整的 Spring 上下文 (Controller, Service 等都會被載入)
@SpringBootTest
// 配置 MockMvc 來模擬 HTTP 請求
@AutoConfigureMockMvc
public class BinarySearchIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // 模擬 HTTP 請求的工具

    @Autowired
    private ObjectMapper objectMapper; // 用於將 Java 對象轉換為 JSON 字符串

    @Test
    void whenKeyIsFound_thenReturnCorrectIndex() throws Exception {
        // 1. 準備請求資料 (已排序的資料和要找的 Key)
        int[] data = {10, 20, 30, 40, 50};
        int key = 30; // 預期位置: 2
        SearchRequest request = new SearchRequest(data, key);

        // 2. 執行階段 (模擬 POST 請求)
        mockMvc.perform(post("/api/search/binary")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))) // 將 Java 對象轉為 JSON 放入請求體

        // 3. 驗證階段 (驗證 HTTP 狀態碼和回傳內容)
                .andExpect(status().isOk()) // 驗證 HTTP 狀態碼是 200 OK
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("2")); // 驗證回傳的內容是正確的索引 "2"
    }

    @Test
    void whenKeyIsNotFound_thenReturnNegativeOne() throws Exception {
        // 1. 準備請求資料 (Key 不存在)
        int[] data = {10, 20, 30, 40, 50};
        int key = 35; // 預期結果: -1
        SearchRequest request = new SearchRequest(data, key);

        // 2. 執行階段 (模擬 POST 請求)
        mockMvc.perform(post("/api/search/binary")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        // 3. 驗證階段
                .andExpect(status().isOk())
                .andExpect(content().string("-1")); // 驗證回傳內容是 "-1"
    }

    @Test
    void whenArrayIsEmpty_thenReturnNegativeOne() throws Exception {
        // 1. 準備請求資料 (空陣列)
        int[] data = {}; 
        int key = 100;
        SearchRequest request = new SearchRequest(data, key);

        // 2. 執行階段
        mockMvc.perform(post("/api/search/binary")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        // 3. 驗證階段
                .andExpect(status().isOk())
                .andExpect(content().string("-1"));
    }
}
```

### 整合測試的體現

這個測試被視為**整合測試**，是因為它驗證了以下組件的協作：

1.  **Web 層與服務層的整合：** 測試了 `SearchController` 能夠正確接收 `SearchRequest` JSON 請求，並成功地呼叫 `SearchService` 的實際業務邏輯。
2.  **JSON 介面相容性：** 測試了 Spring Boot 內建的 JSON 轉換器 (`ObjectMapper`) 能否正確地將 JSON 請求體反序列化為 `SearchRequest` Java 物件，確保**介面的相容性**。
3.  **整個應用程式上下文的載入：** 測試執行時，`@SpringBootTest` 載入了真實的 `SearchController` 和真實的 `SearchService` Bean，驗證它們之間的依賴注入 (Dependency Injection) 是正確的。