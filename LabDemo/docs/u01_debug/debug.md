# 除錯實務與科學假設檢驗 (Debug Lab)

- 中斷點 Breakpoint。Resume 可以讓程式執行在下一個中斷點。
- 看變數 Watch variable，例如觀看 i 目前的值為何。
- 逐步執行 Step by step tracking code。Step into, Step over 可以逐步執行程式碼，前者會進入副程式的內部追蹤程式碼，後者會跳過副程式。
- 看表示式的值 (Expression)，例如 i+j 目前的值。
- 進階中斷點應用。在迴圈中中斷停太多次怎麼辦？透過 Hit count 來跳過許多可能的中斷。當 Hit count 設為 n 時，第 n 次的中斷點程式才會停下來。

## 程式碼中斷點（Breakpoint）

程式碼中斷點（Breakpoint）是軟體開發與除錯（Debugging）的重要工具，用於在執行程式時暫停執行，以便開發者可以檢查程式的狀態、變數的值以及程式流程，以協助偵錯與問題排查。

以下是程式碼中斷點的主要特徵和功能：

1. **暫停程式執行：** 中斷點可在程式碼中的特定行號或條件滿足時暫停程式的執行，使開發者能夠觀察程式的內部狀態。
2. **變數值觀察：** 在中斷點處，開發者可以查看和監視變數的值。這有助於了解變數如何在程式執行過程中變化。
3. **堆疊追蹤：** 中斷點允許開發者查看呼叫堆疊（Call Stack），即程式當前的函式呼叫層次結構。這對於理解程式的執行流程和函式之間的相互作用極有幫助。
4. **條件中斷點：** 開發者可以設定條件中斷點，即只有當特定條件滿足時，中斷點才會觸發。這對於在迴圈大量迭代時定位特定異常情境非常有用。
5. **單步執行：** 一旦程式停在中斷點處，開發者可以逐步執行程式碼，一步一步觀察邏輯，確保程式每個部分都按預期運作。
6. **修改程式狀態：** 在現代除錯工具中，您還可以在中斷點處動態修改變數數值，以快速測試不同情境。

---

## IntelliJ / Antigravity 使用中斷點

在 IDE 中設置和使用中斷點的基本步驟：

1. **開啟專案：** 開啟包含 Java 原始碼的專案與檔案。
2. **設定中斷點：** 在程式碼行號左側邊欄按一下滑鼠左鍵，會出現一個**紅色圓點**（🔴）。
3. **條件中斷點（選擇性）：** 在紅色圓點上按右鍵選擇「Edit Breakpoint」，可設定觸發條件（例如 `i == 99`）或 Hit Count。
4. **啟動除錯：** 點擊 `main` 旁的 Debug 圖示或按快捷鍵 `F5`（或 IntelliJ 的 `Shift + F9`）啟動除錯模式。
5. **中斷點觸發與檢查：** 當程式執行至中斷點時會暫停，高亮當前行。此時可檢查 Variables 變數值、Call Stack 呼叫堆疊，並使用控制列操作單步執行。
6. **移除中斷點：** 再次點擊紅點即可移除。

---

## 🖥️ Demo
設計一個程式，要求使用者輸入一個直徑，然後輸出其面積：

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

---

### 🚌 實作練習

* **Lab01: Bubble Sort**：利用 [BubbleSort](../../src/main/java/xdemo/BubbleSort.java) 操作 Breakpoint，使用 Watch 觀察索引變化，並透過 Conditional Breakpoint 觀察特定迴圈次數。
* **Lab02: GCD (最大公因數)**：使用 Breakpoint 與 Watch 觀察 [GCD.java](../../src/main/java/xdemo/GCD.java) 在不同數值輸入下的變數變化，排查特定邊界情境下的計算缺陷。
* **Lab03: Lowest Common Ancestor (最低共同祖先)**：利用除錯器追蹤二元樹節點遍歷與堆疊 [LowestCommonAncestor.java](../../src/main/java/xdemo/LowestCommonAncestor.java)。
* **Lab04: Sin 泰勒展開式**：理解電腦如何利用泰勒多項式逼近超越函數，並排查 [Sin.java](../../src/main/java/xdemo/Sin.java) 中累加首項重複與迴圈終止條件反轉的邏輯缺陷。

---

## 課堂互動與概念檢核

<!-- id: sqa-u01-debug-ccq1 -->
#### 🙋 **概念核對問答 (CCQ 1)：中斷點暫停時機與變數狀態**




**問題**

在 Java 程式碼中：
```java
int a = 100;
a = a + 1; // 👈 在此行設定中斷點
```
當除錯器執行到該行並高亮暫停時，在 Variables 變數監視視窗中，變數 `a` 此時呈現的值是多少？

A) `100`  
B) `101`  
C) `0`  
D) 尚未宣告，無法查看  

<details>
<summary>點擊查看【概念核對問答】答案與解析</summary>

**正確答案：A**

* **解析**：
  * **選項 A 正確**：中斷點的機制是「在該行指令**執行之前**暫停（Pause before execution）」。因此，當程式停在 `a = a + 1;` 這一行時，加法與賦值運算尚未發生，變數 `a` 的值仍保留為前一行賦予的 `100`。
  * **選項 B 錯誤**：必須按下 Step Over（單步執行下一行）之後，`a` 的值才會被更新為 `101`。
  * **選項 C/D 錯誤**：變數 `a` 已經在前一行完成宣告與初始化。

</details>

---

[課堂互動](https://nlhsueh.github.io/nickedupocket/#/student/sqa-u01-debug-ccq1)

<!-- id: sqa-u01-debug-ccq2 -->
#### 🙋 **概念核對問答 (CCQ 2)：單步執行操作（Step Over vs. Step Into）**




**問題**

工程師在 `main` 方法中除錯，目前程式暫停於呼叫自訂函式的敘述：
```java
computeArea(); // 👈 目前停在這一行
```
若工程師希望「跟隨執行流程，進入 `computeArea()` 函式內部逐行追蹤其邏輯」，應在除錯工具列上選擇哪一項操作？

A) **Step Over (單步跳過 / F10)**：直接執行完該行並跳到下一行指令  
B) **Step Into (單步進入 / F11)**：進入被呼叫函式內部追蹤  
C) **Step Out (單步跳出 / Shift + F11)**：跳出當前函式返回呼叫端  
D) **Resume / Continue (繼續執行 / F5)**：忽略所有中斷點執行到程式結束  

<details>
<summary>點擊查看【概念核對問答】答案與解析</summary>

**正確答案：B**

* **解析**：
  * **選項 B 正確**：**Step Into** 的功能是跟隨呼叫流程進入函式內部，適合排查特定副程式內部的詳細運算。
  * **選項 A 錯誤**：**Step Over** 會將 `computeArea()` 作為一整個步驟直接在背景執行完畢，停在 `main` 的下一行，不會走進該函式內部。
  * **選項 C 錯誤**：**Step Out** 是當你已經在函式內部時，執行完剩餘邏輯並直接跳回外層呼叫點。
  * **選項 D 錯誤**：**Resume** 會讓程式全力跑動，直到撞到下一個中斷點或程式終止。

</details>

---

[課堂互動](https://nlhsueh.github.io/nickedupocket/#/student/sqa-u01-debug-ccq2)

<!-- id: sqa-u01-debug-ccq3 -->
#### 🙋 **概念核對問答 (CCQ 3)：整數除法截斷缺陷分析**




**問題**

檢視本單元 Demo 中的圓面積計算程式碼：
```java
int diameter = input.nextInt(); // 使用者輸入直徑
double area = Math.PI * Math.pow(diameter / 2, 2);
```
當使用者輸入奇數直徑（例如 `diameter = 5`，正確半徑應為 `2.5`，面積應約為 `19.63`）時，計算出的面積卻只有 `12.56`。透過除錯器查看發現半徑被算成了 `2.0`。這屬於下列何種根本原因？

A) `Math.pow()` 只支援整數運算，不支援小數運算  
B) `Math.PI` 精度遺失導致截斷  
C) `diameter / 2` 屬於整數除法（Integer Division），小數部分在運算當下被強制捨去截斷  
D) `Scanner.nextInt()` 無法讀取大於 4 的數值  

<details>
<summary>點擊查看【概念核對問答】答案與解析</summary>

**正確答案：C**

* **解析**：
  * **選項 C 正確**：在 Java 語法中，當運算元兩者皆為整數型別（`int / int`）時，運算結果強制為 `int`，小數點直接被截斷（`5 / 2 = 2`），隨後傳入 `Math.pow(2, 2)` 計算出的半徑平方為 `4.0` 而非預期的 `6.25`。修復方式是讓除數為浮點數（例如 `diameter / 2.0`）以觸發浮點數除法。
  * **選項 A/B/D 錯誤**：皆非引發此問題的原因。

</details>

[課堂互動](https://nlhsueh.github.io/nickedupocket/#/student/sqa-u01-debug-ccq3)
