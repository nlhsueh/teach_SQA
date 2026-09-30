package u02_robust.exception;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 例外處理基礎教學展示範例：ExceptionBasicsDemo
 *
 * <p>本範例展示軟體品質保證 (SQA) 中的例外處理核心機制：</p>
 * <ol>
 *   <li><b>例外層次結構 (Hierarchy)</b>：
 *       Throwable -> Exception (Checked) / RuntimeException (Unchecked) / Error。</li>
 *   <li><b>捕捉或宣告原則 (Catch or Declare Rule - CDR)</b>：
 *       受檢例外必須在方法簽名宣告 <code>throws</code> 或在內部以 <code>try-catch</code> 妥善捕捉。</li>
 *   <li><b>try-catch-finally 語意</b>：
 *       無論正常或拋出例外，<code>finally</code> 區塊皆保證執行。</li>
 *   <li><b>現代資源管理：try-with-resources</b>：
 *       利用 <code>AutoCloseable</code> 介面自動關閉檔案或網路流，徹底消除資源洩漏。</li>
 * </ol>
 */
public class ExceptionBasicsDemo {

    /**
     * 示範 1：未檢例外 (Unchecked Exception / RuntimeException)
     * 通常源於程式邏輯缺陷（如除以零、空指標、陣列越界），編譯期不強制處理。
     */
    public static void demoUncheckedException() {
        System.out.println("\n--- [示範 1] 未檢例外 (Unchecked Exception: ArithmeticException) ---");
        try {
            int numerator = 10;
            int denominator = 0;
            System.out.printf("嘗試執行 %d / %d ...%n", numerator, denominator);
            int result = numerator / denominator;
            System.out.println("計算結果: " + result);
        } catch (ArithmeticException e) {
            System.out.println(">> 成功捕捉未檢例外: " + e.getMessage());
        } finally {
            System.out.println(">> [Finally 區塊] 無論是否發生例外，此區塊保證執行！");
        }
    }

    /**
     * 示範 2：受檢例外 (Checked Exception) 與捕捉或宣告原則 (CDR)
     * 此方法選擇使用 throws 宣告向上拋出，由呼叫端處理。
     *
     * @param filename 檔案路徑
     * @throws IOException 當檔案讀取發生錯誤時
     */
    public static String readFirstLine(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new IOException("指定的檔案不存在: " + filename);
        }
        // 使用 try-with-resources 自動關閉資源
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        }
    }

    /**
     * 示範 3：自訂資源類別驗證 try-with-resources 的自動關閉行為
     */
    static class DatabaseConnection implements AutoCloseable {
        private final String connectionId;

        public DatabaseConnection(String connectionId) {
            this.connectionId = connectionId;
            System.out.printf("[連線建立] 資料庫連線 %s 已開啟%n", connectionId);
        }

        public void executeQuery(String sql) {
            System.out.printf("[執行查詢] 連線 %s 執行 SQL: %s%n", connectionId, sql);
        }

        @Override
        public void close() {
            System.out.printf("[連線釋放] 資料庫連線 %s 已自動關閉 (close() 觸發)%n", connectionId);
        }
    }

    public static void demoTryWithResources() {
        System.out.println("\n--- [示範 2] 現代資源管理：try-with-resources ---");
        try (DatabaseConnection conn = new DatabaseConnection("DB-POOL-01")) {
            conn.executeQuery("SELECT * FROM students WHERE grade >= 60");
            System.out.println(">> 業務操作順利完成");
        } // 離開 try 區塊時，conn.close() 會被自動呼叫，即使拋出例外也不會外洩連線
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" 軟體品質保證 (SQA) 實習示範：Java 例外處理基礎");
        System.out.println("==================================================");

        // 1. 執行未檢例外示範
        demoUncheckedException();

        // 2. 執行現代 try-with-resources 自動資源釋放示範
        demoTryWithResources();

        // 3. 示範受檢例外由呼叫者以 try-catch 處理
        System.out.println("\n--- [示範 3] 呼叫端遵循 CDR 處理受檢例外 ---");
        try {
            System.out.println("嘗試讀取不存在的檔案 missing_file.txt ...");
            readFirstLine("missing_file.txt");
        } catch (IOException e) {
            System.out.println(">> 呼叫端成功捕捉受檢例外 (IOException): " + e.getMessage());
        }

        System.out.println("\n==================================================");
        System.out.println(" 示範執行完成。");
        System.out.println("==================================================");
    }
}
