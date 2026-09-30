package u02_robust.log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Java 內建日誌框架展示範例：LoggingJulDemo (java.util.logging - JUL)
 *
 * <p>本範例展示 JUL 的核心用法與軟體品質日誌規範：</p>
 * <ol>
 *   <li><b>Logger 實例化</b>：使用類別全名作為日誌器識別名稱。</li>
 *   <li><b>日誌等級區分</b>：INFO（正常里程碑）、WARNING（非致命警訊）、SEVERE（系統嚴重異常）。</li>
 *   <li><b>多目標輸出 (Handlers)</b>：同時輸出至 Console 與持久化檔案 (logs/jul_demo.log)。</li>
 *   <li><b>例外記錄規範</b>：在 catch 區塊中記錄例外物件，由日誌器自動格式化堆疊追蹤 (Stack Trace)。</li>
 * </ol>
 */
public class LoggingJulDemo {

    private static final Logger logger = Logger.getLogger(LoggingJulDemo.class.getName());

    public static void setupFileHandler() {
        try {
            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // 建立檔案處理器 (追加模式 = true)
            FileHandler fileHandler = new FileHandler("logs/jul_demo.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);

            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "無法初始化檔案日誌處理器", e);
        }
    }

    public static void executeBusinessTask(String taskName, int divisor) {
        logger.info("開始執行任務: " + taskName);

        if (divisor == 1) {
            logger.warning("除數為 1，運算無實質縮放效果 (潛在低效或冗餘運算)");
        }

        try {
            int result = 100 / divisor;
            logger.info(String.format("任務 [%s] 運算完成，結果 = %d", taskName, result));
        } catch (ArithmeticException e) {
            // 正確做法：將例外物件 e 一併傳入，保留完整調用棧
            logger.log(Level.SEVERE, "任務 [" + taskName + "] 發生除以零致命錯誤！", e);
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" 軟體品質保證 (SQA) 實習示範：Java 內建日誌 (JUL)");
        System.out.println("==================================================");

        // 初始化檔案日誌
        setupFileHandler();

        // 執行各情境任務
        executeBusinessTask("資料分批計算-正常", 5);
        executeBusinessTask("資料分批計算-警示", 1);
        executeBusinessTask("資料分批計算-異常", 0);

        System.out.println("\n日誌記錄完成，請檢查控制台輸出與 logs/jul_demo.log 檔案！");
        System.out.println("==================================================");
    }
}
