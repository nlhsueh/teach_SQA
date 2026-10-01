package u02_robust.exception;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 氣泡排序例外處理教學展示範例：BubbleSortExceptionDemo
 *
 * 【核心學習目標】：
 * 1. 未檢例外 (Unchecked Exception / RuntimeException)：
 *    - NullPointerException：傳入 null 陣列時引發。
 *    - ArrayIndexOutOfBoundsException：雙層迴圈邊界錯誤引發索引越界。
 *    - IllegalArgumentException：公開 API 主動防禦非法參數。
 * 2. 受檢例外 (Checked Exception)：
 *    - IOException / FileNotFoundException：從檔案讀取待排序陣列，示範 CDR 原則與 try-with-resources。
 *    - SortingException (自訂例外)：繼承 Exception，封裝排序演算法後置驗證失敗的詳細情境。
 * 3. 致命錯誤 (Error)：
 *    - StackOverflowError：遞迴版氣泡排序在規模過大時耗盡 JVM 堆疊。
 * 4. 現代例外處理結構：
 *    - try-catch-finally 保證資源釋放。
 *    - try-with-resources 自動關閉檔案串流。
 *    - Multi-catch 多重捕捉處理不同失敗情境。
 */
public class BubbleSortExceptionDemo {

    // =========================================================================
    // 1. 自訂受檢例外 (Custom Checked Exception)
    // =========================================================================

    /**
     * 自訂排序例外：當排序後的陣列經檢驗未符合排序規則時拋出
     */
    public static class SortingException extends Exception {
        private final int[] rawData;
        private final int failedIndex;

        public SortingException(String message, int[] rawData, int failedIndex) {
            super(String.format("%s (在索引 [%d] 處違反遞增順序: %d > %d, 現況: %s)",
                    message, failedIndex, rawData[failedIndex], rawData[failedIndex + 1],
                    Arrays.toString(rawData)));
            this.rawData = rawData.clone();
            this.failedIndex = failedIndex;
        }

        public int[] getRawData() {
            return rawData.clone();
        }

        public int getFailedIndex() {
            return failedIndex;
        }
    }

    // =========================================================================
    // 2. 核心排序方法與各類例外示範
    // =========================================================================

    /**
     * 標準穩健氣泡排序：包含 IllegalArgumentException 防禦與自訂受檢例外檢驗
     *
     * @param data 待排序陣列
     * @throws IllegalArgumentException 若傳入 null
     * @throws SortingException         若排序演算法未正確完成升冪排序
     */
    public static void bubbleSort(int[] data) throws SortingException {
        // [Unchecked Exception: IllegalArgumentException 防禦]
        if (data == null) {
            throw new IllegalArgumentException("待排序陣列不可為 null (前置條件防禦)");
        }

        int length = data.length;
        if (length <= 1) {
            return;
        }

        for (int pass = 0; pass < length - 1; pass++) {
            boolean swapped = false;
            for (int i = 0; i < length - pass - 1; i++) {
                if (data[i] > data[i + 1]) {
                    int temp = data[i];
                    data[i] = data[i + 1];
                    data[i + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }

        // 驗證排序結果，若失敗則拋出自訂受檢例外
        for (int i = 0; i < length - 1; i++) {
            if (data[i] > data[i + 1]) {
                throw new SortingException("氣泡排序後置檢驗失敗", data, i);
            }
        }
    }

    /**
     * 刻意帶有邊界缺陷的氣泡排序：示範未檢例外 (ArrayIndexOutOfBoundsException)
     *
     * @param data 待排序陣列
     */
    public static void buggyBoundarySort(int[] data) {
        if (data == null) {
            throw new IllegalArgumentException("待排序陣列不可為 null");
        }
        int length = data.length;
        // 刻意將迴圈寫成 i < length，當存取 data[i + 1] 時在最後一個元素必定超出邊界
        for (int i = 0; i < length; i++) {
            if (data[i] > data[i + 1]) { // 💥 引發 ArrayIndexOutOfBoundsException
                int temp = data[i];
                data[i] = data[i + 1];
                data[i + 1] = temp;
            }
        }
    }

    /**
     * 未防禦 null 的氣泡排序：示範未檢例外 (NullPointerException)
     */
    public static void unshieldedSort(int[] data) {
        // 未檢查 data 是否為 null，直接呼叫 data.length
        int length = data.length; // 💥 若 data 為 null 則引發 NullPointerException
        System.out.println("陣列長度: " + length);
    }

    /**
     * 從檔案讀取整數陣列並排序：示範受檢例外 (Checked Exception)、CDR 原則與 try-with-resources
     *
     * @param file 檔案物件
     * @return 排序後的陣列
     * @throws IOException 當檔案讀取失敗時向上宣告
     */
    public static int[] loadAndSort(File file) throws IOException, SortingException {
        if (!file.exists()) {
            throw new FileNotFoundException("找不到指定的排序輸入檔案: " + file.getAbsolutePath());
        }

        List<Integer> list = new ArrayList<>();
        // try-with-resources：保證 reader 在離開區塊時自動關閉
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    list.add(Integer.parseInt(line)); // 可能拋出 NumberFormatException
                }
            }
        }

        int[] result = list.stream().mapToInt(Integer::intValue).toArray();
        bubbleSort(result);
        return result;
    }

    /**
     * 帶有無窮遞迴缺陷的氣泡排序：示範致命錯誤 (StackOverflowError)
     * 若忘記將遞迴規模減 1 (如誤寫為 n)，將導致無窮遞迴耗盡呼叫堆疊
     *
     * @param data 陣列
     * @param n    待排序範圍長度
     */
    public static void recursiveBubbleSortWithBug(int[] data, int n) {
        if (n <= 1) {
            return;
        }
        for (int i = 0; i < n - 1; i++) {
            if (data[i] > data[i + 1]) {
                int temp = data[i];
                data[i] = data[i + 1];
                data[i + 1] = temp;
            }
        }
        // 💥 缺陷：誤將規模傳為 n 而非 n - 1，導致無窮遞迴
        recursiveBubbleSortWithBug(data, n);
    }

    // =========================================================================
    // 3. 展示主程式：涵蓋 try-catch-finally、多重 catch 與錯誤觀摩
    // =========================================================================

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" 軟體品質保證 (SQA) 實習示範：BubbleSort 例外處理展示");
        System.out.println("==================================================");

        // ---------------------------------------------------------------------
        // 場景 1: NullPointerException 與 IllegalArgumentException 防禦
        // ---------------------------------------------------------------------
        System.out.println("\n[場景 1] 參數防禦 (IllegalArgumentException vs NullPointerException)");
        try {
            System.out.println(">> 傳入 null 呼叫 bubbleSort...");
            bubbleSort(null);
        } catch (IllegalArgumentException e) {
            System.out.println(">> 成功捕捉主動防禦之例外: " + e.getMessage());
        } catch (SortingException e) {
            System.out.println(">> 排序例外: " + e.getMessage());
        }

        // ---------------------------------------------------------------------
        // 場景 2: ArrayIndexOutOfBoundsException 與 try-catch-finally
        // ---------------------------------------------------------------------
        System.out.println("\n[場景 2] 迴圈邊界越界 (ArrayIndexOutOfBoundsException) 與 finally 保證");
        int[] sample = { 5, 2, 9, 1 };
        try {
            System.out.println(">> 呼叫具有邊界缺陷的 buggyBoundarySort...");
            buggyBoundarySort(sample);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(">> 成功捕捉陣列越界例外: 索引超出範圍 (" + e.getMessage() + ")");
        } finally {
            System.out.println(">> [finally 區塊] 無論是否越界，資源釋放與統計記錄保證執行！");
        }

        // ---------------------------------------------------------------------
        // 場景 3: Checked Exception (FileNotFoundException) 與多重 catch
        // ---------------------------------------------------------------------
        System.out.println("\n[場景 3] 讀取檔案排序之多重 catch (Multi-catch)");
        File nonExistentFile = new File("non_existent_data.txt");
        try {
            System.out.println(">> 嘗試載入不存在的檔案: " + nonExistentFile.getName());
            loadAndSort(nonExistentFile);
        } catch (FileNotFoundException e) {
            System.out.println(">> [專屬捕捉 1] 檔案未找到: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(">> [專屬捕捉 2] 一般 IO 例外: " + e.getMessage());
        } catch (SortingException e) {
            System.out.println(">> [專屬捕捉 3] 排序後置條件異常: " + e.getMessage());
        }

        // ---------------------------------------------------------------------
        // 場景 4: 自訂受檢例外 (SortingException) 示範
        // ---------------------------------------------------------------------
        System.out.println("\n[場景 4] 自訂受檢例外 (SortingException) 拋出與欄位提取");
        try {
            int[] testData = { 10, 20, 15, 30 };
            // 模擬演算法缺陷未排序完成
            throw new SortingException("模擬演算法缺陷，部分數值未正確排序", testData, 1);
        } catch (SortingException e) {
            System.out.println(">> 捕捉到自訂例外訊息: " + e.getMessage());
            System.out.println(">> 錯誤發生索引位置: " + e.getFailedIndex());
        }

        // ---------------------------------------------------------------------
        // 場景 5: 致命錯誤 (StackOverflowError) 觀摩
        // ---------------------------------------------------------------------
        System.out.println("\n[場景 5] 致命錯誤觀摩 (StackOverflowError - 遞迴缺陷)");
        try {
            int[] testArray = { 5, 2, 9, 1 };
            System.out.println(">> 呼叫遞迴氣泡排序 (因忘記 n-1 造成無窮遞迴)...");
            recursiveBubbleSortWithBug(testArray, testArray.length);
        } catch (StackOverflowError err) {
            System.out.println(">> 捕捉到 JVM 致命錯誤 (Error): 堆疊空間溢位 (StackOverflowError)");
            System.out.println(">> 說明：Error 代表系統底層無法恢復之嚴重狀態，實務上不應使用 try-catch 掩蓋！");
        }

        System.out.println("\n==================================================");
        System.out.println(" 展示結束：BubbleSort 例外處理生命週期示範完畢");
        System.out.println("==================================================");
    }
}
