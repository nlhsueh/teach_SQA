package demo.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyService {

    // 取得 Logger 實例
    private static final Logger logger = LogManager.getLogger(demo.log4j.MyService.class);

    public void runLogic() {
        logger.trace("這是 Trace 等級的日誌"); // 級別太低，可能被忽略
        logger.debug("這是 Debug 等級的日誌"); // 級別太低，可能被忽略
        logger.info("應用程式啟動完成，執行中..."); // 會被 Root Logger 記錄 (level="info")

        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            // 使用 error() 記錄例外，Log4j 會自動處理堆疊追蹤
            logger.error("發生數學錯誤", e);
        }
    }

    public static void main (String[] args) {
        MyService service = new MyService();
        service.runLogic();
    }
}