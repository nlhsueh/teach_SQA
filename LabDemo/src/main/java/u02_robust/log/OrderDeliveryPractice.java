package u02_robust.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 實習練習題：美食外送平台訂單日誌實戰 (OrderDeliveryPractice)
 *
 * 【核心學習目標】：
 * 1. 熟悉現代日誌框架 Log4j 2 的標準實務與日誌分級策略。
 * 2. 掌握參數化日誌 (Parameterized Logging) 語法（例如：logger.info("訂單 {} 已建立", id)），避免字串拼接效能損耗。
 * 3. 依據情境正確選用日誌等級：
 *    - INFO：正常商業里程碑事件（建立訂單、接單成功、外送完成）。
 *    - WARN：非致命警訊（非法輸入嘗試、庫存偏低告警）。
 *    - ERROR：業務失敗（庫存售罄拒單）或系統未預期例外（含例外物件 e 堆疊追蹤）。
 *
 * 【測試執行方式】：
 * - 執行主程式檢驗流程：java -cp target/classes:$(mvn dependency:build-classpath | grep -v '\[INFO\]') u02_robust.log.OrderDeliveryPractice
 * - 執行單元測試：mvn test -Dtest=OrderDeliveryPracticeTest
 *
 * 【對應講義與手冊】：
 * - 講義：Ch 02 防禦性程式設計 / 日誌機制與等級規範
 * - 實習文件：LabDemo/docs/u02_robust/logging.md (Ex04 美食外送平台)
 */
public class OrderDeliveryPractice {

    private static final Logger logger = LogManager.getLogger(OrderDeliveryPractice.class);

    public enum OrderStatus {
        CREATED,    // 已建立
        ACCEPTED,   // 餐廳已接單
        DELIVERING, // 外送中
        COMPLETED,  // 配送完成
        CANCELLED   // 已取消
    }

    public static class Order {
        private final String orderId;
        private final String customerName;
        private final double totalAmount;
        private OrderStatus status;

        public Order(String orderId, String customerName, double totalAmount) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.totalAmount = totalAmount;
            this.status = OrderStatus.CREATED;
        }

        public String getOrderId() { return orderId; }
        public String getCustomerName() { return customerName; }
        public double getTotalAmount() { return totalAmount; }
        public OrderStatus getStatus() { return status; }
        public void setStatus(OrderStatus status) { this.status = status; }
    }

    /**
     * 階段 1：顧客建立訂單
     */
    public Order createOrder(String orderId, String customer, double amount) {
        if (amount <= 0) {
            // --------------------------------------------------------------
            // TODO: 練習 1 - 記錄非法訂單金額警訊 (WARN)
            // 說明：請使用 logger.warn 記錄訂單建立失敗之金額警訊（建議使用參數化日誌 {}）
            // 語法範例：logger.warn("建立訂單失敗：訂單號碼 {} 金額 {} 不合法", orderId, amount);
            // --------------------------------------------------------------
            // 請在此撰寫日誌記錄程式碼...

            throw new IllegalArgumentException("訂單金額必須大於 0");
        }

        Order order = new Order(orderId, customer, amount);

        // ------------------------------------------------------------------
        // TODO: 練習 2 - 記錄訂單成功建立之里程碑 (INFO)
        // 說明：請使用 logger.info 記錄訂單建立成功之顧客名稱與金額
        // 語法範例：logger.info("訂單建立成功：訂單號碼 {}，顧客: {}，金額: {} 元", orderId, customer, amount);
        // ------------------------------------------------------------------
        // 請在此撰寫日誌記錄程式碼...

        return order;
    }

    /**
     * 階段 2：餐廳接單審核
     */
    public boolean restaurantAccept(Order order, int currentStock) {
        logger.debug("餐廳正在審核訂單 {}，當前可用庫存: {}", order.getOrderId(), currentStock);

        if (currentStock <= 0) {
            // --------------------------------------------------------------
            // TODO: 練習 3 - 庫存耗盡導致接單失敗 (ERROR)
            // 說明：請使用 logger.error 記錄業務拒單原因
            // 語法範例：logger.error("餐廳拒絕接單：訂單號碼 {} 因庫存不足 (庫存: {}) 取消", order.getOrderId(), currentStock);
            // --------------------------------------------------------------
            // 請在此撰寫日誌記錄程式碼...

            order.setStatus(OrderStatus.CANCELLED);
            return false;
        }

        if (currentStock <= 2) {
            // --------------------------------------------------------------
            // TODO: 練習 4 - 庫存偏低警訊 (WARN)
            // 說明：請使用 logger.warn 記錄存貨告警，提醒補貨
            // 語法範例：logger.warn("庫存警戒：處理訂單 {} 後剩餘庫存僅剩 {}", order.getOrderId(), currentStock);
            // --------------------------------------------------------------
            // 請在此撰寫日誌記錄程式碼...

        }

        order.setStatus(OrderStatus.ACCEPTED);
        logger.info("餐廳已確認接單：訂單號碼 {}", order.getOrderId());
        return true;
    }

    /**
     * 階段 3：外送員配送與異常處理
     */
    public void dispatchAndDeliver(Order order, boolean simulateGpsFailure) {
        if (order.getStatus() != OrderStatus.ACCEPTED) {
            logger.warn("無法派送訂單 {}：訂單狀態不是 ACCEPTED (目前狀態: {})",
                    order.getOrderId(), order.getStatus());
            return;
        }

        order.setStatus(OrderStatus.DELIVERING);
        logger.info("外送員已取餐，訂單 {} 配送中...", order.getOrderId());

        try {
            if (simulateGpsFailure) {
                throw new IllegalStateException("外送員手持設備 GPS 訊號遺失，無法回傳定位");
            }
            order.setStatus(OrderStatus.COMPLETED);
            logger.info("🎉 訂單 {} 順利送達顧客 {} 手中！", order.getOrderId(), order.getCustomerName());

        } catch (Exception e) {
            // --------------------------------------------------------------
            // TODO: 練習 5 - 捕捉未預期例外並保留完整堆疊追蹤 (ERROR)
            // 說明：請使用 logger.error(msg, e) 同時傳入錯誤訊息與例外物件 e
            // 語法範例：logger.error("訂單 {} 配送過程發生異常: {}", order.getOrderId(), e.getMessage(), e);
            // --------------------------------------------------------------
            // 請在此撰寫日誌記錄程式碼...

        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" 實習練習：美食外送訂單日誌生命週期實戰");
        System.out.println("==================================================");

        OrderDeliveryPractice service = new OrderDeliveryPractice();

        // 情境 1：正常訂單成功送達（觸發 INFO、WARN 低庫存提醒）
        System.out.println("\n[情境 1] 正常下單流程：");
        Order order1 = service.createOrder("DEL-001", "Nick", 350.0);
        service.restaurantAccept(order1, 2); // 庫存剩下 2，觸發 WARN
        service.dispatchAndDeliver(order1, false);

        // 情境 2：庫存不足接單失敗（觸發 ERROR）
        System.out.println("\n[情境 2] 餐廳庫存售罄拒單流程：");
        Order order2 = service.createOrder("DEL-002", "Bob", 180.0);
        service.restaurantAccept(order2, 0); // 庫存為 0，觸發 ERROR

        // 情境 3：外送異常中斷（觸發 ERROR 堆疊追蹤）
        System.out.println("\n[情境 3] 外送硬體 GPS 故障中斷流程：");
        Order order3 = service.createOrder("DEL-003", "Alice", 520.0);
        service.restaurantAccept(order3, 10);
        service.dispatchAndDeliver(order3, true); // 觸發 GPS 異常

        System.out.println("\n外送模擬完成，請檢視控制台與日誌輸出！");
        System.out.println("==================================================");
    }
}
