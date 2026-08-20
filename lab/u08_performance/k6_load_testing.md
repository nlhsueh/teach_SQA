# 實習 12：k6 現代程式化壓測 (Load as Code) 與高併發效能工程

> 🎯 **實習目標**：
> 1. 告別傳統 JMeter 笨重的 GUI XML 設定檔，掌握現代雲原生標準——**以代碼定義負載 (Load as Code with k6)**。
> 2. 設計高併發負載情境（**負載測試 Load、突波測試 Spike、耐力測試 Soak**）。
> 3. 分析高併發下的 **P95 / P99 延遲**、吞吐量 (RPS)、錯誤率與 HTTP 500 熔斷現象。

---

## 1. 為什麼選擇 k6 作為現代壓測工具？

* **以 JavaScript/TypeScript 撰寫測試腳本**：完全版本控管 (Git)，像寫業務代碼一樣寫壓測。
* **Go 語言底層引擎**：單台筆電即可輕鬆產生 10,000+ 虛擬使用者 (VUs) 的超高並發流量，CPU 與記憶體佔用極低。
* **CI/CD 自動化品質門檻 (Thresholds)**：可直接設定「若 P99 延遲 > 200ms 則自動讓 GitHub Actions CI 失敗」。

---

## 2. 安裝 k6
```bash
# macOS (Homebrew)
brew install k6

# 驗證安裝
k6 version
```

---

## 3. 實戰演練：撰寫程式化壓測腳本 (`load_test.js`)

```javascript
import http from 'k6/http';
import { check, sleep } from 'k6';

// 1. 定義壓測階段與品質門檻 (Thresholds)
export const options = {
  stages: [
    { duration: '30s', target: 20 },  // 30 秒內緩慢加壓至 20 位並發使用者 (Ramp-up)
    { duration: '1m',  target: 100 }, // 1 分鐘內高壓衝刺至 100 位並發使用者 (Peak Load)
    { duration: '20s', target: 0 },   // 20 秒內降壓散場 (Ramp-down)
  ],
  thresholds: {
    // 品質門檻 1：95% 的請求必須在 250ms 內完成
    http_req_duration: ['p(95)<250', 'p(99)<500'],
    // 品質門檻 2：失敗請求率必須小於 1%
    http_req_failed: ['rate<0.01'],
  },
};

// 2. 虛擬使用者 (VU) 執行的情境邏輯
export default function () {
  const url = 'http://localhost:8080/api/v1/orders';
  const payload = JSON.stringify({
    productId: 101,
    quantity: 1,
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const res = http.post(url, payload, params);

  // 驗證回應狀態與內容
  check(res, {
    '狀態碼為 200': (r) => r.status === 200,
    '交易耗時 < 300ms': (r) => r.timings.duration < 300,
  });

  sleep(0.1); // 模擬人類使用者思考時間
}
```

---

## 4. 執行壓測與分析報告

```bash
k6 run load_test.js
```

### 📊 終端分析指標解讀：
* `http_req_duration`: 包含 `avg`, `min`, `med`, `max`, `p(90)`, `p(95)`, `p(99)` 響應時間。
* `http_req_failed`: 失敗率百分比（高並發下若發生連線池耗盡會飆高）。
* `iterations`: 總共成功執行的交易次數。

---

## 📋 實習成果驗收標準
1. [ ] 撰寫一份 `k6` 壓測腳本，包含至少 2 個 stages 與明確的 `thresholds` 門檻。
2. [ ] 對本機 Spring Boot 服務發動至少 50 VUs 並發壓測。
3. [ ] 記錄並分析 P95/P99 延遲與 RPS 數據，若超出門檻需討論系統瓶頸可能原因（如資料庫連線池大小、缺乏快取、慢查詢）。
