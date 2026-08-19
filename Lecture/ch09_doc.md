Ch09 測試文件
===

:::info
Tiger 把報告放在雄太的桌上，「老實說，我覺得寫這個文件沒有什麼用」他有點神氣的說，「還是寫程式比較實在」。

雄太沒有回答，靜靜的看了兩三分鐘。他抬頭說：「的確是沒什麼用」。Tiger 於是高興著。

「那就重寫吧，讓它有用」。雄太接著說。
:::

> 不要輕忽文件的撰寫，他和寫程式一樣，需要清楚的邏輯。

## IEEE 829

IEEE 829 是IEEE 針對測試文件所提出的建議。它包含幾個主要的文件：測試計劃書、測試規格書與測試報告。測試計劃書描述整體的測試計畫、包含時程、人員、範圍、工具等安排。測試規格書包含測試設計規格（test design specification）、測試案例規格（test case specification）與測試程序（test procedure）。測試報告包含項目移轉報告（test transmittal report）、測試日誌報告（test log report）、測試事件報告（test incident report）、測試總結報告（test summary report）等。

圖 [fig:IEEE829](#fig_IEEE829) 描述整體的流程。**測試計畫** 產出後，若干個**測試設計規格書}依據計劃書而產生，把細部的測試設計寫出來，接著每個測試設計可以展開若干個**測試案例規格書}，用以描述可以造成執行的測試資料。如果你的專案沒有很大的時候，測試設計與測試案例是可以合併成為一個規格書的。測試執行的過程中所產生的紀錄就成為**測試日誌報告}（test log）。

注意測試不可能一次就完成，即便您規劃的很仔細，還是有可能發生錯誤而終止，後續就需要重新準備環境、撰寫程式、安排人員等。這些過程則記錄在**測試事件報告}。最後，我們需要做一個總結報告，整體性的說明系統是否是否通過測試。

> Standard for Software and System Test Documentation, is an IEEE standard that specifies the form of a set of documents for use in eight defined stages of software testing and system testing, each stage potentially producing its own separate type of document.

一些其他的標準：
-  SQA – Software quality assurance IEEE 730
-  SCM – Software configuration management IEEE 828
-  STD – Software test documentation IEEE 829
-  SRS – Software requirements specification IEEE 830
-  VV – Software verification and validation IEEE 1012
-  SDD – Software design description IEEE 1016
-  SPM – Software project management IEEE 1058
-  SUD – Software user documentation IEEE 1063

#### fig_IEEE829
![IEEE829](https://hackmd.io/_uploads/r1QWVpvvT.png)


### 測試計畫
測試計劃書的目的在描述測試活動的範圍、方法、資源與時間的安排。計劃書並需明確指出受測項目、測試哪些特性或功能（feature）、執行哪些測試的活動、哪些人應該負責哪些工作以及相關的風險。

-  計劃書編號;
-  簡介; 
-  測試項目;
-  測試的特性與功能;
-  不測試的特性與功能;
-  測試方法;
-  通過的準則;
-  終止與繼續的條件;
-  交付物;
-  測試工作;
-  需要的環境;
-  工作分配;
-  訓練計畫;
-  時程;
-  風險;
-  簽名。

### 測試設計規格書
測試計劃書的目的在仔細的描述測試的方法：

-  測試設計規格書編號; 
-  預計測試的功能特色; 
-  測試方法說明; 
-  測試的編碼; 
-  測試通過與失敗的準則; 

### 測試案例規格書
依據測試設計規格書所描述的測試案例進行細部的描述，包含：

-  測試案例規格編號; 
-  測試項目; 
-  輸入規格; 
-  輸出規格; 
-  環境需求; 
-  特別程序的需求; 
-  測試案例之間的相依性。例如某個測試案例測試後才可以進行另一個案例。


## 測試程序規格書
說明測試案例的執行的程序與步驟。

-  測試程序規格編碼; 
-  目的; 
-  特別需求; 
-  步驟。

### 測試記錄
記錄測試過程中所發生的事件與處理方法，這些記錄是後續分析測試是否通過的重要依據。

-  測試記錄編碼; 
-  說明; 
-  活動與事件說明; 

### 檢查表

可以建立一個檢查表檢查各文件是否適合。

-  人員工作時程與責任規劃
-  測試工作是否清楚定義、分配人力、規劃時程
-  測試工作的責任分配與時程規劃，是否能夠配合專案時程
-  必要的訓練是否在時程規劃內完成
-  測試環境的描述是否清楚、完整；
-  是否包含軟體、硬體、實驗室空間、資料庫建置
-  是否有足夠時間建置所規劃配置的環境
-  是否考慮所有測試成本
-  測試成本是否在測試計畫容許範圍內
-  測試計畫書是否確認、並分析所有可能發生風險
-  測試工具的使用是否適當？
-  所有測試需求與特性是否完整測試規劃？
-  測試程序與目標是否合理、可滿足軟體需求規範？
-  測試程序是否完整？符合標準？
-  測試方法、技術的描述是否清楚完整？
-  所有測試項目是否完整規劃在測試計畫書？
-  所有測試項目是否描述清楚？
-  測試項目的配置、時程是否適當？
-  所有測試項目是否有相對應的測試設計？
-  測試案例設計是否完整、正確？
-  測試案例是否有相對應的測試方法？適當的測程序？
-  暫停與重新起始測試的條件是否清楚描述？
-  pass/fail criteria的設定是否清楚，能夠滿足整體品質需求？
-  是否規劃整合測試方法？規劃是否清楚？
-  是否設計追蹤矩陣(Traceability matrix)？
-  測試報告需求是否有規範？
-  使用者是否提供足夠多的輸入，以執行接受測試？


## 範例
以下幾頁為 IEEE 829 中的兩個範例，第一個是一個薪資系統的測試計劃書、測試記錄。第二個是一個數值正規化的例子，著重在測試設計與測試案例：


* [IEEE 829](https://drive.google.com/file/d/12-HCU3oj-9rMjNpxLgzQSQyZ5xFzZ5mo/view?usp=sharing)
* [IEEE 829 example](https://drive.google.com/file/d/1puCjamWm7JsA8XI-wT0X6DdCXA6krxms/view?usp=sharing)


## 練習

-  以下何者正確：	 
	-  IEEE 829 主要說明軟體測試相關的文件，1012 說明軟體驗證程序。
	-  IEEE 829 說明軟體設計文件如何撰寫。
	-  測試計劃書應該包含何時暫停測試的標準、何時重新啟動測試的標準。
	-  當今測試應該以程式自動化的方式進行，測試文件是沒效率且不必要的。
-  測試計畫應包含以下哪些項目：	
	-  測試範圍 
	-  測試方法 
	-  測試資源 
	-  測試時程規劃 
	-  測試案例 
	-  測試報告
-  在 IEEE 829 中，測試設計 (test design) 與測試案例 (test case) 文件有和差別？
-  測試計畫與測試設計中都要說明「測試方法」，有和差異？
-  為何測試計畫要即早進行。
-  針對一個線上考試系統，依循 IEEE 829 的規範撰寫其測試相關文件。