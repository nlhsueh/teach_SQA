package demo.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// 類別一：需要詳細日誌的驗證器 (DEBUG level in log4j2.xml)
class OrderValidator {
    private static final Logger logger = LogManager.getLogger(OrderValidator.class);

    public boolean validate() {
        logger.debug("OValidator: 開始檢查訂單結構...");

        // ... 複雜的驗證邏輯 ...

        logger.info("OValidator: 訂單驗證通過。");

        logger.fatal("OValidator: 訂單驗證出現了重大錯誤！");
        return true;
    }

    public static void main (String[] args) {
        OrderValidator orderValidator = new OrderValidator();
        if (orderValidator.validate())
            System.out.println("訂單驗證通過");
    }
}


