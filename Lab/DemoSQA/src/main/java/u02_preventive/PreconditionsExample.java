package u02_preventive;

import com.google.common.base.Preconditions;

/**
 * 範例：使用 Google Guava Preconditions 進行防禦性參數與狀態校驗
 */
public class PreconditionsExample {
    private boolean initialized = false;
    private String name;

    public void init(String name) {
        // 1. 檢查參數不可為 null (若為 null 則拋出 NullPointerException)
        this.name = Preconditions.checkNotNull(name, "名稱不可為空 (name must not be null)");
        this.initialized = true;
    }

    public double calculateArea(int width, int height) {
        // 2. 檢查物件狀態 (若狀態不符則拋出 IllegalStateException)
        Preconditions.checkState(initialized, "服務尚未初始化，請先呼叫 init()");

        // 3. 檢查參數合規性 (若不符則拋出 IllegalArgumentException)
        // 支援與 String.format 相似的佔位符，但僅支援 %s
        Preconditions.checkArgument(width > 0, "寬度必須大於 0，實際值: %s", width);
        Preconditions.checkArgument(height > 0, "高度必須大於 0，實際值: %s", height);

        return width * height;
    }

    public static void main(String[] args) {
        PreconditionsExample example = new PreconditionsExample();

        System.out.println("=== 開始執行 Google Guava Preconditions 驗證實驗 ===");

        // 實驗 1：未初始化時呼叫業務邏輯，預期觸發 IllegalStateException
        try {
            System.out.println("[實驗 1] 嘗試計算面積...");
            example.calculateArea(10, 5);
        } catch (IllegalStateException e) {
            System.err.println(">> 捕獲預期狀態異常 (State Exception): " + e.getMessage());
        }

        // 實驗 2：傳入 null 進行初始化，預期觸發 NullPointerException
        try {
            System.out.println("\n[實驗 2] 嘗試傳入 null 進行初始化...");
            example.init(null);
        } catch (NullPointerException e) {
            System.err.println(">> 捕獲預期空指針異常 (NullPointer Exception): " + e.getMessage());
        }

        // 正確初始化
        System.out.println("\n[正常操作] 進行初始化...");
        example.init("AreaCalculator");

        // 實驗 3：傳入不合法參數，預期觸發 IllegalArgumentException
        try {
            System.out.println("\n[實驗 3] 嘗試傳入負數寬度...");
            example.calculateArea(-5, 10);
        } catch (IllegalArgumentException e) {
            System.err.println(">> 捕獲預期參數異常 (Argument Exception): " + e.getMessage());
        }

        // 正常呼叫
        System.out.println("\n[正常操作] 傳入合法參數...");
        double area = example.calculateArea(10, 5);
        System.out.println(">> 計算面積結果: " + area);
    }
}
