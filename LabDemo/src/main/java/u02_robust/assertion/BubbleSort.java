package u02_robust.assertion;

import java.util.Arrays;

/**
 * 氣泡排序 (Bubble Sort) 示範：
 * 示範契約前置條件防禦 (Precondition)、迴圈不變量斷言 (Loop Invariant) 與後置條件斷言 (Postcondition)。
 */
public class BubbleSort {

    /**
     * 對整數陣列進行氣泡排序（升冪排序）
     *
     * @param data 待排序陣列（公開 API 前置條件：不可為 null）
     * @throws IllegalArgumentException 若陣列為 null
     */
    public static void bubbleSort(int[] data) {
        // 1. 公開 API 防禦性檢查：外部輸入非法時拋出 RuntimeException，嚴禁依賴隨時可能被關閉的 assert
        if (data == null) {
            throw new IllegalArgumentException("待排序陣列不可為 null");
        }

        int length = data.length;
        if (length <= 1) {
            return;
        }

        // 保存原始陣列長度，供後置條件驗證不變量
        int originalLength = length;

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

            // 2. 迴圈不變量斷言 (Loop Invariant)：確保每一輪 pass 結束後，末端已就定位的子陣列嚴格有序
            assert isSuffixSorted(data, length - pass - 1) :
                    String.format("第 %d 輪排序後末端元素未正確就定位: %s", pass, Arrays.toString(data));

            // 若本輪無任何交換，表示陣列已經完全就緒，提早結束
            if (!swapped) {
                break;
            }
        }

        // 3. 後置條件斷言 (Postcondition)：驗證長度未失真且整體呈非遞減順序
        assert data.length == originalLength : "後置條件失敗：排序後陣列長度改變";
        assert isSorted(data) : "後置條件失敗：排序後陣列未正確遞增排序: " + Arrays.toString(data);
    }

    /**
     * 刻意帶有 off-by-one 缺陷的氣泡排序實作，用於示範斷言如何於執行期定格缺陷現場
     *
     * @param data 待排序陣列
     */
    public static void buggyBubbleSort(int[] data) {
        if (data == null) {
            throw new IllegalArgumentException("待排序陣列不可為 null");
        }

        int length = data.length;
        if (length <= 1) {
            return;
        }

        // 刻意減少一輪迴圈 (缺陷：尚未完成全部交換便提早終止)
        for (int pass = 0; pass < length - 2; pass++) {
            for (int i = 0; i < length - pass - 2; i++) {
                if (data[i] > data[i + 1]) {
                    int temp = data[i];
                    data[i] = data[i + 1];
                    data[i + 1] = temp;
                }
            }
        }

        // 後置條件斷言捕捉缺陷
        assert isSorted(data) : "缺陷捕捉：排序邏輯錯誤，陣列未完全排序！現況: " + Arrays.toString(data);
    }

    /**
     * 檢查陣列是否已完成升冪（非遞減）排序
     *
     * @param data 欲檢查的陣列
     * @return true 若陣列依序遞增；false 否則
     */
    public static boolean isSorted(int[] data) {
        if (data == null || data.length <= 1) {
            return true;
        }
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i] > data[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 檢查陣列從 startIndex 開始到最後的後綴區段是否已完成排序
     *
     * @param data 陣列
     * @param startIndex 起始索引
     * @return true 若該區段有序
     */
    public static boolean isSuffixSorted(int[] data, int startIndex) {
        if (data == null || startIndex < 0 || startIndex >= data.length - 1) {
            return true;
        }
        for (int i = startIndex; i < data.length - 1; i++) {
            if (data[i] > data[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] data = { 64, 34, 25, 12, 22, 11, 90 };
        System.out.println("排序前: " + Arrays.toString(data));
        bubbleSort(data);
        System.out.println("排序後: " + Arrays.toString(data));
    }
}
