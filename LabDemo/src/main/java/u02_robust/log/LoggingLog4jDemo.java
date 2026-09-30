package u02_robust.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 現代企業級日誌框架展示範例：LoggingLog4jDemo (Log4j 2 / SLF4J)
 *
 * 【核心學習目標】：
 * 1. LogManager 取得 Logger：LogManager.getLogger(LoggingLog4jDemo.class) 業界標準模式。
 * 2. 參數化日誌 (Parameterized Logging)：
 *    - 使用 logger.info("使用者 {} 登入", userId)，由底層按需替換佔位符，避免字串拼接開銷。
 * 3. 企業分級記錄策略：
 *    - DEBUG：開發階段排查專用（狀態細節、變數值）。
 *    - INFO：重要業務流程節點（訂單建立、付款完成）。
 *    - WARN：潛在異常警訊但系統可自癒或降級處理。
 *    - ERROR：業務失敗或拋出未處理例外，記錄堆疊追蹤。
 *
 * 【測試執行方式】：
 * - 執行主程式檢驗流程：java -cp target/classes:$(mvn dependency:build-classpath | grep -v '\[INFO\]') u02_robust.log.LoggingLog4jDemo
 * - 執行單元測試：mvn test -Dtest=LoggingLog4jDemoTest
 *
 * 【對應講義與手冊】：
 * - 講義：Ch 02 防禦性程式設計 / Log4j 2 與現代日誌架構
 * - 實習文件：LabDemo/docs/u02_robust/logging.md
 */
public class LoggingLog4jDemo {

    private static final Logger logger = LogManager.getLogger(LoggingLog4jDemo.class);

    public void processPayment(String orderId, double amount, String paymentMethod) {
        logger.debug("準備處理訂單 {} 之付款請求，金額: {}，支付管道: {}", orderId, amount, paymentMethod);

        if (amount <= 0) {
            logger.warn("訂單 {} 收到不尋常金額: {} 元，已拒絕此筆交易", orderId, amount);
            return;
        }

        logger.info("訂單 {} 付款處理中，授權額度: {} 元", orderId, amount);

        // 模擬呼叫第三方支付閘道
        try {
            if ("FAIL_GATEWAY".equals(paymentMethod)) {
                throw new java.net.ConnectException("無法連線至第三方金流中心 (Connection Timeout)");
            }
            logger.info("訂單 {} 付款成功！發送電子發票通知", orderId);
        } catch (java.net.ConnectException e) {
            // 記錄 ERROR 並帶上例外堆疊追蹤
            logger.error("訂單 {} 金流處理失敗！支付閘道連線異常: {}", orderId, e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" 軟體品質保證 (SQA) 實習示範：Log4j 2 企業級日誌");
        System.out.println("==================================================");

        LoggingLog4jDemo demo = new LoggingLog4jDemo();

        // 正常支付
        demo.processPayment("ORD-2026-001", 1250.0, "CREDIT_CARD");

        // 異常金額
        demo.processPayment("ORD-2026-002", -50.0, "LINE_PAY");

        // 金流中心連線失敗
        demo.processPayment("ORD-2026-003", 8800.0, "FAIL_GATEWAY");

        System.out.println("\n請查看 Console 輸出與 log4j2 產生的日誌紀錄！");
        System.out.println("==================================================");
    }
}
