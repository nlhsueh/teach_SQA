# 實習 10：Testcontainers 真實容器化整合測試 (Integration Testing with Testcontainers)

> 🎯 **實習目標**：
> 1. 告別 H2 In-Memory 內存資料庫的「假象相容（幻覺綠燈）」。
> 2. 學習在 JUnit 5 測試中透過 **Testcontainers** 一鍵自動拉起真實的 **Docker PostgreSQL / Redis 容器**。
> 3. 驗證資料庫交易隔離性、原生 SQL 語法與索引約束，達到真正的環境一致性（Portability & Environment Parity）。
> 4. 📖 **理論對照**：對應講義 [**Ch07 整合測試 (7.8 現代真實環境整合測試：Testcontainers)**](../../Lecture/source/ch07_integration.md)。

---

## 1. 為什麼要淘汰 H2 內存庫？

| 傳統 H2 內存測試 (舊做法) | 現代 Testcontainers 容器化測試 (SQA 2.0) |
| :--- | :--- |
| H2 的 SQL 語法與真實 PostgreSQL/MySQL 不相容（如 JSONB、全文檢索、Window Functions） | **使用 100% 生產環境完全相同的 Docker 鏡像**（版本、外掛、配置完全一致） |
| H2 無法重現真實資料庫的並發鎖定、死結 (Deadlock) 與隔離等級 | 能真實重現並發交易隔離性（`READ COMMITTED`, `REPEATABLE READ`） |
| 容易出現「本機測試全綠，上線發布時 SQL 語法錯誤崩潰」 | **測試通過即保證在生產環境能 100% 正常執行！** |

---

## 2. Maven 依賴設定 (`pom.xml`)

```xml
<dependencies>
    <!-- Spring Boot Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- Testcontainers JUnit 5 支援與 PostgreSQL 模組 -->
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>1.19.7</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>postgresql</artifactId>
        <version>1.19.7</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 3. 實戰演練：在真實 PostgreSQL 容器中執行 JPA 整合測試

```java
package lab.sqa.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers // 自動管理 Docker 容器的生命週期 (啟動 ➔ 測試 ➔ 自動銷毀清理)
public class UserRepositoryIntegrationTest {

    // 定義真實的 PostgreSQL 16 官方容器
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("sqa_test_db")
            .withUsername("test_user")
            .withPassword("test_password");

    // 動態將容器分配的隨機 Port 與連線 URL 注入到 Spring Boot 配置中
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateAndFindUserWithNativeJsonb() {
        User user = new User("Alice", "alice@example.com", "{\"role\": \"ADMIN\"}");
        userRepository.save(user);

        User found = userRepository.findByEmail("alice@example.com").orElse(null);
        assertNotNull(found);
        assertEquals("Alice", found.getName());
        assertEquals("{\"role\": \"ADMIN\"}", found.getMetadata());
    }

    @Test
    void testUniqueConstraintViolation() {
        User user1 = new User("Bob", "bob@example.com", "{}");
        userRepository.save(user1);

        // 測試真實資料庫唯一約束 (Unique Constraint) 是否能正確觸發例外
        User user2 = new User("Bob Duplicate", "bob@example.com", "{}");
        assertThrows(Exception.class, () -> {
            userRepository.saveAndFlush(user2);
        });
    }
}
```

---

## 4. 實習任務要求

1. **Docker 環境確認**：確保本機已安裝並啟動 Docker Desktop 或 OrbStack。
2. **執行整合測試**：
   * 觀察控制台日誌：Testcontainers 自動拉取 `postgres:16-alpine` 鏡像並啟動容器。
   * 測試完畢後，容器自動被 Ryuk 守護程序乾淨關閉銷毀，**不留下任何髒資料或佔用 Port**！
3. **並發交易衝突測試**：
   * 撰寫一個測試案例，模擬兩個執行緒同時扣減同一筆商品庫存（測試悲觀鎖 `@Lock(LockModeType.PESSIMISTIC_WRITE)` 或樂觀鎖 `@Version`）。

---

## 📋 實習成果驗收標準
1. [ ] 成功使用 `@Testcontainers` 啟動真實 PostgreSQL / Redis 容器進行測試。
2. [ ] 整合測試包含資料庫唯一約束（Unique Constraint）與交易回滾（Transaction Rollback）驗證。
3. [ ] 截圖包含 Testcontainers 啟動 Docker 容器的 Console 日誌與 JUnit 5 綠燈報告。
