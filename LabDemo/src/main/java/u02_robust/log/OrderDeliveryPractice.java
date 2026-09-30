package u02_robust.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 實習練習題：美食外送平台訂單日誌實戰 (OrderDeliveryPractice)
 *
 * <p>【練習主題】：對應實習手冊 Ex04 外送平台題目。</p>
 * <p>【核心任務】：在訂單生命週期的各關鍵階段，使用合適的 Log4j 2 日誌等級與參數化語法進行記錄：</p>
 * <ol>
 *   <li><b>INFO</b>：正常里程碑事件（如建立訂單、餐廳接單、配送完成）。</li>
 *   <li><b>WARN</b>：存貨警戒線、延遲等非致命警訊。</li>
 *   <li><b>ERROR</b>：業務邏輯失敗（庫存不足）、連線中斷或系統異常，並附帶例外資訊。</li>
 * </ol>
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
            // TODO: 練習 1 - 請使用 logger.warn(...) 記錄非法訂單金額警訊
            // 請在此撰寫日誌記錄程式碼...

            throw new IllegalArgumentException("訂單金額必須大於 0");
        }

        Order order = new Order(orderId, customer, amount);

        // TODO: 練習 2 - 請使用 logger.info(...) 記錄訂單成功建立之重要里程碑
        // 請在此撰寫日誌記錄程式碼...

        return order;
    }

    /**
     * 階段 2：餐廳接單審核
     */
    public boolean restaurantAccept(Order order, int currentStock) {
        logger.debug("餐廳正在審核訂單 {}，當前可用庫存: {}", order.getOrderId(), currentStock);

        if (currentStock <= 0) {
            // TODO: 練習 3 - 庫存耗盡導致接單失敗，請使用 logger.error(...) 記錄業務失敗
            // 請在此撰寫日誌記錄程式碼...

            order.setStatus(OrderStatus.CANCELLED);
            return false;
        }

        if (currentStock <= 2) {
            // TODO: 練習 4 - 庫存偏低，請使用 logger.warn(...) 記錄存貨警訊
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
            // TODO: 練習 5 - 捕捉未預期例外，請使用 logger.error(msg, e) 記錄錯誤訊息並保留完整堆疊追蹤
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
