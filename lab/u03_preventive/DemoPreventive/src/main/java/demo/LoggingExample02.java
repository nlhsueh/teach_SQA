package demo;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingExample02 {
    private static final Logger logger = Logger.getLogger(LoggingExample.class.getName());

    public static void main(String[] args) {
        try {
            // 設定 FileHandler，將日誌寫入檔案 "app.log"
            FileHandler fileHandler = new FileHandler("app.log", true); // true 表示追加到文件中
            fileHandler.setFormatter(new SimpleFormatter()); // 設定格式為簡單格式
            logger.addHandler(fileHandler);

            logger.info("這是一條 INFO 等級的日誌訊息");
            logger.warning("這是一條 WARNING 等級的日誌訊息");

            // 故意產生一個錯誤
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            logger.log(Level.SEVERE, "發生了算術異常: " + e.getMessage(), e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "無法創建 FileHandler: " + e.getMessage(), e);
        }
    }
}

