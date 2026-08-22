package demo.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// 只需要主流程日誌的處理器 (INFO level in log4j2.xml)
class OrderProcessor {
    private static final Logger logger = LogManager.getLogger(demo.log4j.OrderProcessor.class);

    public void process() {
        logger.debug("OProcessor: 嘗試開始處理訂單..."); // (C) DEBUG 訊息
        logger.info("OProcessor: 成功將訂單狀態更新為 'Accepted'。"); // (D) INFO 訊息

        new OrderValidator().validate();

        logger.warn("OProcessor: 警告！顧客信用等級低。"); // (E) WARN 訊息
    }

    public static void main (String[] args) {
        OrderProcessor orderProcessor = new OrderProcessor();
        orderProcessor.process();
    }
}