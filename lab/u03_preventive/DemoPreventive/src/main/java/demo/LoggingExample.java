package demo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingExample {
    private static final Logger logger = Logger.getLogger(LoggingExample.class.getName());

    public static void main(String[] args) {
        logger.info("這是一條 INFO 等級的日誌訊息");
        logger.warning("這是一條 WARNING 等級的日誌訊息");

        try {
            int result = 10 / 0;  // 故意製造一個錯誤
        } catch (ArithmeticException e) {
            logger.log(Level.SEVERE, "發生了算術異常: " + e.getMessage(), e);
        }
    }
}

