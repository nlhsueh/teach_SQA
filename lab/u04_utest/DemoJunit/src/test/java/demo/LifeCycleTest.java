package demo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class LifeCycleTest {

    // 類別級別的設定/初始化 (在所有測試方法之前執行一次)
    @BeforeAll
    static void setupAll() {
        System.out.println("---- @BeforeAll: 執行一次，用於設定共用資源 (例如：資料庫連線、載入測試資料)。");
    }

    // 每個測試方法執行前的設定 (在每個 @Test 方法之前執行)
    @BeforeEach
    void setupEach() {
        System.out.println("  > @BeforeEach: 在每個測試方法執行前執行 (例如：初始化物件、重置狀態)。");
    }

    // 第一個測試方法
    @Test
    @DisplayName("測試方法一：檢查加法運算")
    void testMethodOne() {
        System.out.println("    >> @Test: 執行測試方法一...");
        // 實際的測試邏輯，例如：Assertions.assertEquals(4, 2 + 2);
    }

    // 第二個測試方法
    @Test
    @DisplayName("測試方法二：檢查減法運算")
    void testMethodTwo() {
        System.out.println("    >> @Test: 執行測試方法二...");
        // 實際的測試邏輯，例如：Assertions.assertEquals(1, 3 - 2);
    }

    // 每個測試方法執行後的清理 (在每個 @Test 方法之後執行)
    @AfterEach
    void cleanupEach() {
        System.out.println("  < @AfterEach: 在每個測試方法執行後執行 (例如：清理資源、釋放記憶體)。");
    }

    // 類別級別的清理/釋放資源 (在所有測試方法之後執行一次)
    @AfterAll
    static void cleanupAll() {
        System.out.println("---- @AfterAll: 執行一次，用於清理共用資源 (例如：關閉資料庫連線)。");
    }
}

