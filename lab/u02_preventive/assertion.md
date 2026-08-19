# 預防性程設

> 就像排骨牌一樣，我們會設許多斷點，預防錯誤的擴散。

##  斷言

本節介紹的斷言 (java assertion) 與例外處理 (exception handling) 都是一種防禦性的編程方法。

> 用鑰匙鎖上門後，我們會推一下門，確定有鎖上。

> 即便是綠燈，過馬路時，我們會預防性的左右看一下

斷言是 java 中用來檢查程式中的假設與斷言。例如你寫一個程式判斷汽車（或火箭）的速度，不管怎麼快，也不可能比光速快。所以你可以下一個斷言：計算出來的速度必定小於光速。當這個斷言被違反了，表示你的程式可能出現問題。

其語法為：

```java 
// assert 後方接 我們斷言為對的條件
assert grade <= 100; 
```

或

```java 
// 如果沒有滿足錯誤，就顯示 errMessage 訊息
// assert expression: errMessage
assert grade <= 100 : "錯誤的總成績" ; 
```

其中 Expression1 是一個 boolean 表示式。errMessage 是一個含有值的表示式，當斷言不成立時，會拋出 `java.lang.AssertionError` 的例外，並把 errMessage 的值呈現出來。

除此以外，斷言也可以幫助提升程式的可閱讀。如果你不用斷言，你的程式碼可能會出現：

不好的寫法：
```java
if (grade > 100) then
   print("奇怪！");
```

這樣的程式碼，會和原有的程式邏輯攪在一起，降低了可閱讀性，造成維護上的困難。

以下這個範例說明了 assert 的應用，假設我們自己寫一個 square (平方) 的程式，採取防禦性的開發方式每一次計算後就會斷言 a>=0，提昇程式的可靠度。

```java
Scanner sc = new Scanner(System.in);
int a = sc.nextInt();
int b = square(a);
assert (a>=0): "平方後不可能 < 0";
```


### 斷言的啟動

為了讓 assertion 不會造成程式的負擔，你可以在編譯的時候決定是否要將 assertion 啟動（enable）。

> javac -ea Calculator.java

其中 `ea` 表示 enable assertion。

> 為你的一段程式碼加上斷言，enable 斷言以查核其正確。待確定程式無誤後，disable 這些斷言

### 斷言使用時機

- 開發設計階段。使用 assertion 來測試，檢查程式中的錯誤。當此階段結束：即便沒有啟動 assertion, 也不會擔心系統錯誤。

- 內部的不變式 Internal Invariants。程式撰寫的過程中，有任何的不變式都可以加上 assertion。

    ```java
    if (i % 3 == 0) {
       ...
    } else if (i % 3 == 1) {
       ...
    } else {
       // 斷言前面的條件若不滿足，則 i%2 一定會等於 2
       assert i % 3 == 2 : i; 
       ...
    }
    ```

加上這樣的斷言好像沒有作用，但當 i 的值為負數時，你會發現你以為的 2 並不會成真。這就是利用斷言的好處。

- 類別的不變式 Class invariant。類別不變式表示該物件任何方法執行的前後都恆真的式子，例如一個 Stack 物件內部元素的個數一定介於 0 與最大值之間。

    ```java
    private boolean inv() {
        return (num >= 0 && num < capacity);
    }
    ```

所以我們可以在執行任何動作之後呼叫 inv() 來檢查是否發生異常：

    ```java
    push(x);
    assert inv();
    ...
    y = pop(x);
    assert inv();
    ```

- 不變的控制流 Control-Flow Invariants。對控制流程的斷言，我相信絕對不會到 05 行，因為迴圈內有一定會有一個滿足 if 的條件式。

```java
void foo() { 
   for (...) {
      if (...) return;
   }
   assert false; 
}
```

如此，萬一真的跑到 Line 05, 系統就會拋出例外警告。

- 後置條件 Postconditions。在一段複雜的運算後，你斷定會成為真的事情，用斷言來加強。

    ```java
    sort(SORT.INC); //遞增
    if (data.length >=2) assert data[0] <= data[1];
    sort(SORT.DEC); //遞減
    if (data.length >=2) assert data[0] >= data[1];
    ```


### 何時不該使用斷言?

- 不要使用斷言來做公開方法的參數檢查。公開方法對參數的檢查本來就應該做，不要用 斷言 來檢查。公開的方法表示會有很多其他人會呼叫此方法，傳入各種不同的參數，為了避免他們傳錯，方法本來就必須測試這些參數，所以不該用斷言的方式來檢查。

例如下面的程式，我們本來就該檢查 grade 是否正確，如果用 assert 來做，當我們 enable assert  時，成績輸入錯就檢查不出來了。

```java
public void addGrade(int grade) {
   assert x <= 100: "grade > 100 error";
   sum + = grade;    
}
```

應該改成下面：

```java
public void addGrade(int grade) {
   if (grade > 100) 
       throw new Exception("grade > 100 error");
   sum + = grade;    
}
```

相反的，若是私有方法，其參數的約定式內部的，我們應該很清楚的知道不該有那樣不正確的參數傳入，所以可以用斷言。如果真的傳入奇怪的參數，就會拋出例外。

- 不要使用斷言來執行程式邏輯相關的操作。不要把程式該做的工作放在 assert 中。因為 assert 以後可能會被 disable。例如在象棋系統中，某一段邏輯我們棋子移動或是吃子，因為移動或吃子都可能違法，所以他的 return type 是 boolean, 但切記不用 assert 去判斷這些動作：

```java
public void playGame() {
   ...
   assert chess1.move(12);
   ...
   assert chess3.eat(chess1);
   ...
}
```

原因一樣：如果你 disable 了斷言，這些動作就不能執行了。


總言而之，記得這個原則：(1) 開發階段，有斷言，就寫斷言 (2) 如果我們移除了斷言，系統邏輯還是要對的，還是能夠做適當的防呆偵錯。

### Intellij

1. Run > Configuration > Modify Option > VM > 加上 `-ea`
![](https://hackmd.io/_uploads/rJg4NE602.png)
在 intellij 上設定 -ea 

2. Run Maven: 
   1. Settings > Build, Execution, Deployment > Runner > VM options 加上 `-ea`

![](../img/maven_assert.png)

   2. 在 POM 中加入一個 build 的設定：

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.1</version> <configuration>
                <mainClass>xdemo.Sin</mainClass>
            </configuration>
            </plugin>
        </plugins>
    </build>
```    
   3. 點選右方的 Maven > Plugins > exec > exec:java 來執行

![](../img/maven_exec_java.png)

### Lab

#### Lab01: BubbleSort
* [參考程式碼](../../Intellij/DemoPreventive/src/main/java/xdemo/BubbleSort.java)
* 排序後確認一下順序

#### Lab02: Sin
* [參考程式碼](../../Intellij/DemoPreventive/src/main/java/xdemo/Sin.java)
* 每一次的 Sin 的變化不應該太大

#### Lab03: People
* [參考程式碼](../../Intellij/DemoPreventive/src/main/java/xdemo/People.java)
* BMI 的值應該有一個範圍
* 父子之間是不是有些不變的關係？

#### Lab04: Triangle
* [參考程式碼](../../Intellij/DemoPreventive/src/main/java/xdemo/Triangle.java)
* 三角形的長度可以是負的？該用 assert 嗎？

#### Lab05: MaxHeap
* [MaxHeap 解說](https://docs.google.com/presentation/d/11ajG_oQkdPvYaAa7-9oGG-bFU34YJMmq8zZZIFch-Y4/edit#slide=id.p)
* [參考程式碼](../../Intellij/DemoPreventive/src/main/java/xdemo/MaxHeap.java)
* 有滿足二元樹要求？
* 真的是最大的？
