package u02_robust.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 現代企業級日誌框架展示範例：LoggingLog4jDemo (Log4j 2 / SLF4J)
 *
 * <p>本範例展示 Log4j 2 的標準實務：</p>
 * <ol>
 *   <li><b>LogManager 取得 Logger</b>：業界標準模式。</li>
 *   <li><b>參數化日誌 (Parameterized Logging)</b>：
 *       使用 <code>logger.info("使用者 {} 登入", userId)</code>，避免無謂的字串拼接開銷。</li>
 *   <li><b>分級記錄策略</b>：
 *       <ul>
 *         <li>DEBUG：開發排查專用（變數狀態、SQL 語句）。</li>
 *         <li>INFO：重要業務流程節點。</li>
 *         <li>WARN：潛在異常但系統可自動恢復（如重試、超時）。</li>
 *         <li>ERROR：業務失敗或拋出例外。</li>
 *       </ul>
 *   </li>
 * </ol>
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
