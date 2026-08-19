Debug Lab
===

- 中斷點 Breakpoint。Resume 可以讓程式執行在下一個中斷點。
- 看變數 Watch variable，例如觀看 i 目前的值為何。
- 逐步執行 Step by step tracking code。Step into, Step over 可以逐步執行程式碼，前者會進入副程式的內部追蹤程式碼，後者會跳過副程式。
- 看表示式的值 (Expression)，例如 i+j 目前的值。
- 進階中斷點應用。在迴圈中中斷停太多次怎麼辦？透過 Hit count 來跳過許多可能的中斷。當 Hit count 設為 n 時，第 n 次的中斷點程式才會停下來。

## 程式碼中斷點（Breakpoint）

程式碼中斷點（Breakpoint）是程式開發和調試中的一個重要工具，用於在執行程式時暫停執行，以便開發者可以檢查程式的狀態、變數的值以及程式流程，以幫助偵錯和調試。

以下是程式碼中斷點的主要特徵和功能：

1. **暫停程式執行：** 中斷點可在程式碼中的特定行號或條件滿足時暫停程式的執行，使開發者能夠觀察程式的狀態。
2. **變數值觀察：** 在中斷點處，開發者可以查看和監視變數的值。這有助於了解變數如何在程式執行過程中變化，有助於偵錯。
3. **堆疊追蹤：** 中斷點也允許開發者查看呼叫堆疊（call stack），即程式中當前的函數調用層次結構。這對於理解程式的執行流程和函數之間的相互作用很有幫助。
4. **條件斷點：** 開發者可以設置條件斷點，即只有當某個特定條件滿足時，中斷點才會觸發。這對於在特定情況下調試程式很有用。
5. **單步執行：** 一旦程式停在中斷點處，開發者可以逐步執行程式，進行一步一步的調試，以確保程式的每個部分都按預期運行。
6. **修改程式狀態：** 在某些調試工具中，您還可以在中斷點處修改變數的值，以測試不同的情景和修復問題。

程式碼中斷點是開發者在尋找程式中的錯誤或進行性能分析時的強大工具。通過使用中斷點，開發者可以更有效地理解和改進他們的程式。

## IntelliJ 使用中斷點
在 IntelliJ IDEA 中，使用中斷點是一個相對簡單的過程。以下是在 IntelliJ 中設置和使用中斷點的基本步驟：

1. **打開專案：** 首先，打開您的 IntelliJ IDEA 項目，然後打開您想要設置中斷點的 Java 類或其他程式文件。
2. **選擇行號：** 在您的程式碼中找到您希望設置中斷點的行號。您可以在行號的左側編輯器邊欄中單擊一次，以在該行號上設置一個常規中斷點。中斷點將顯示為==紅色圓點==。
3. **選擇條件（選擇性）：** 如果您想要設置條件中斷點，右鍵單擊中斷點並選擇 "Edit Breakpoint"（編輯中斷點）。在對話框中，您可以指定中斷點的條件，只有當條件成立時才會觸發中斷點。
4. **運行程式：** 接下來，運行您的程式。您可以使用 "Run" 菜單或按快捷鍵 `Shift + F10` 來運行程式。
5. **中斷點觸發：** 當程式執行到您設置的中斷點時，它將自動暫停執行。此時，您可以檢查變數、查看堆疊追蹤，並使用調試工具欄上的控制按鈕（例如，單步執行、繼續、中止等）來控制程式的執行流程。
6. **解除中斷點（選擇性）：** 一旦您完成了調試，您可以選擇是否保留中斷點。您可以右鍵單擊中斷點，然後選擇 "Toggle Breakpoint"（切換中斷點）來解除或重新設置中斷點。

## 🖥️ Demo
設計一個程式，要求使用者輸入一個直徑，然後輸出其面積

```java
import java.util.Scanner;

public class BreakpointDemo {

    public static void main(String[] args) {
        computeArea();
    }

    public static void computeArea() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter diameter: ");
        int diameter = input.nextInt();

        double area = Math.PI * Math.pow(diameter / 2, 2);
        System.out.println(area);
    }
}
```

大家覺得上面的程式有沒有問題？請用中斷點來除錯。

### 🚌 Lab01: bubble sort

請利用 [BubbleSort](../../Intellij/DemoDebug/src/main/java/xdemo/BubbleSort.java) 的程式操作 breakpoint, 熟悉相關的工具操作

* 使用 watch，觀察 length-path-1 的變化
* 使用 breakpoint 將程式中斷在兩個 for loop 的地方，透過 resume 快速的觀察每一個 path 的差異
* 使用 conditional breakpoint 將程式中斷在接近迴圈尾端的地方，快速觀察變數的變化與執行

### 🚌 Lab02: GCD
* 請用 while 和遞迴的方式寫一個最大公因數的程式
* [參考程式](../../Intellij/DemoDebug/src/main/java/xdemo/GCD.java) （此程式有錯）

### 🚌 Lab03: LowestCommonAncestor
* 寫一個 binary tree (TreeNode)
* 給定兩個點，求這兩個點的最低共同祖先節點
* [參考程式](../../Intellij/DemoDebug/src/main/java/xdemo/LowestCommonAncestor.java) （此程式有錯）

### 🚌 Lab04: Sin
* 透過泰勒展展開式計算 Sin(30)
* [參考程式](../../Intellij/DemoDebug/src/main/java/xdemo/Sin.java) （此程式有錯）

---

❓以下程式，當我們把 breakpoint 設在第 2 行時，variable 的視窗呈現 a 的值是多少？(1) 100 (2) 101

```java
int a = 100;
a = a + 1;
```
