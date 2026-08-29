package u02_preventive.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import java.util.UUID;

/**
 * 範例：使用 SLF4J MDC (Mapped Diagnostic Context) 進行結構化日誌與請求追蹤 (Request Tracing)
 */
public class MdcExample {
    private static final Logger logger = LogManager.getLogger(MdcExample.class);

    public static void main(String[] args) {
        System.out.println("=== 開始執行 SLF4J MDC 結構化日誌實驗 ===");

        // 模擬三個獨立的用戶請求並行或序列執行
        processUserRequest("User_Alice", "BuyBook");
        processUserRequest("User_Bob", "CheckBalance");
        processUserRequest("User_Charlie", "InvalidAction");
    }

    private static void processUserRequest(String userId, String action) {
        // 1. 為此請求生成唯一的 traceId
        String traceId = UUID.randomUUID().toString().substring(0, 8);

        // 2. 將上下文資訊存入 MDC 之中
        MDC.put("traceId", traceId);
        MDC.put("userId", userId);
        MDC.put("action", action);

        try {
            logger.info("收到請求，開始處理業務邏輯...");
            
            // 執行內部業務邏輯
            performBusinessSteps(action);
            
            logger.info("請求處理成功！");
        } catch (Exception e) {
            logger.error("處理請求時發生異常: " + e.getMessage());
        } finally {
            // 3. 關鍵：務必在 finally 區塊中清除 MDC！
            // 由於 MDC 底層是綁定在 ThreadLocal 上，若不清除，執行緒被執行緒池回收再利用時，
            // 會造成日誌資訊污染以及潛在的記憶體洩漏！
            MDC.clear();
        }
    }

    private static void performBusinessSteps(String action) {
        logger.info("步驟 1：驗證操作權限...");
        
        if ("InvalidAction".equals(action)) {
            throw new IllegalArgumentException("操作行為無效：" + action);
        }
        
        logger.info("步驟 2：執行資料庫更新...");
    }
}
