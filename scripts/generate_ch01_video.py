import asyncio
import os
import subprocess
import json
import fitz  # PyMuPDF
import edge_tts
from PIL import Image, ImageDraw
import re

# Slide voiceover scripts in natural, conversational Taiwanese Mandarin
SCRIPTS = {
    1: "各位同學好，歡迎來到軟體品質保證課程。今天我們要為大家介紹第一章：簡介與軟體品質概念，帶大家建立對軟體品質工程的核心認知。",
    
    2: "首先我們來看一下本章重點。第一單元是軟體危機，我們會探討歷史上幾個付出慘痛代價的案例；第二單元探討軟體品質的定義與五大觀點；第三單元介紹 ISO 9126 與現代 ISO 25010 品質模型；最後第四單元，我們會釐清品質控制 QC、品質保證 QA 以及軟體品質成本 CoQ 的觀念。",
    
    3: "好，我們進入第一單元：軟體危機。大家都知道物理學上有物質不滅定律，但很多工程師常開玩笑說，我們在軟體界最熟悉的其實是 Bug 不滅定律。",
    
    4: "到底什麼是軟體危機呢？1960 年代末期，硬體效能突飛猛進，但軟體開發技術卻遠遠跟不上。軟體規模與複雜度呈指數級暴增，導致專案頻繁出現嚴重延期、大幅超支、甚至是品質低劣而崩潰。這主要是因為軟體具有無形性，而且缺乏標準化的工程方法與品質管理體系。",
    
    5: "我們來看第一個重大歷史案例：1991 年波斯灣戰爭的愛國者反導彈事件。伊拉克發射飛毛腿飛彈擊中美軍達蘭基地，造成 28 人死亡、上百人受傷。事後調查發現，愛國者系統時鐘採用 24 位元浮點數，每開機一小時就會累積微小的截斷誤差。連開 100 小時後累積了 0.33 秒的延遲，導致雷達計算飛毛腿飛彈位置偏離了 687 公尺，攔截落空。",
    
    6: "第二個案例是 1998 年 NASA 的火星氣候軌道探測器。這顆探測器造價將近兩億美元，抵達火星後卻直接失聯墜毀。根本原因在於，洛克希德馬丁公司撰寫的地面控制軟體使用的是英制單位「磅力秒」，而 NASA 噴射推進實驗室預設使用的是公制單位「牛頓秒」。單位不相容導致推力計算錯誤，造成重大慘劇。",
    
    7: "從這張單位不相容示意圖可以看到，洛克希德馬丁輸出了英制磅力秒，而 NASA 接收端軟體當成公制牛頓秒運算，使得推力產生四點四五倍的落差，直接讓探測器降到了五十七公里，在火星大氣層中摩擦解體。",
    
    8: "第三個案例是 1994 年華航名古屋空難，造成 264 人罹難。副駕駛進場時不小心觸發了「重飛模式」，隨後駕駛員嘗試手動下壓機首降落，但飛控電腦仍持續自動推升機尾配平「糾正」駕駛員。人機爭奪控制權，最終飛機仰角過大失速墜毀。這凸顯了人機互動與狀態一致性設計的重要性。",
    
    9: "這張人機衝突示意圖顯示，駕駛員手動操作操縱桿往下，但飛控電腦因為重飛指令，不斷將安定面配平朝上。駕駛員對電腦的工作狀態並不清楚，人機爭奪控制權，最終在低空失速墜毀。這啟示我們系統狀態指示必須透明，且需要有明確的優先級仲裁邏輯。",
    
    10: "第四個案例是 1994 年迪士尼《獅子王》PC 遊戲事件。當時數百萬家長買這款遊戲給孩子當聖誕禮物，安裝後卻頻繁藍屏崩潰。原因是微軟新推出的 WinG 圖形驅動程式，與市面上許多音效卡和顯示卡有嚴重的相容性問題。這個慘痛教訓也促成了微軟後來全力發展 DirectX 標準。",
    
    11: "第五個案例是台灣本土的重大公共資訊系統案例。2014 年新戶政系統上線首日，全台戶政大塞車，民眾無法請領戶籍謄本與身分證。原因在於架構相容性、壓力測試不足以及缺少多層備援機制，給社會帶來巨大衝擊。",
    
    12: "這些歷史案例給我們帶來了什麼省思呢？正如學者所說：「軟體和教堂非常相似——建成之後我們就開始祈禱。」軟體品質絕不是寫完程式再來找蟲，而是涉及需求、架構、溝通與驗證的全生命週期工程課題。",
    
    13: "好，我們來看第一題觀念檢核題。題目是：愛國者反導彈系統在達蘭基地攔截失效的根本軟體原因為何？選項 A：通訊網路中斷導致雷達無法傳送資料；選項 B：系統時鐘暫存器採用 24 位元浮點數，累積時間轉換誤差導致雷達測距失真；選項 C：微處理器硬體過熱燒毀；選項 D：被敵方發動網路資安攻擊癱瘓。大家覺得哪一個選項才是正確的呢？",
    
    14: "我們來看解答。正確答案是 B。愛國者系統採用 24 位元浮點數記錄時間，每小時有微小截斷誤差。連開 100 小時後累積了 0.33 秒延遲，對高速飛行的飛毛腿飛彈來說，雷達預測位置偏離了 687 公尺，因此攔截失敗。",
    
    15: "接著看第二題觀念檢核題。題目是：NASA 火星氣候軌道探測器墜毀事件給軟體工程師最重要的啟示是什麼？選項 A：必須使用多執行緒並發處理軌道計算；選項 B：模組間的介面契約與單位定義必須嚴格一致且明確驗證；選項 C：探測器應完全依賴人工即時手動操控，不應使用自動化軟體；選項 D：太空專案應採用瀑布模型而非敏捷開發。請大家思考一下。",
    
    16: "我們來看解答。正確答案是 B。洛克希德馬丁輸出英制單位磅力秒，而 NASA 輸入端預設公制單位牛頓秒，介面契約不相容導致推力計算錯誤。這啟示我們，模組介面契約與單位驗證必須非常嚴謹。",
    
    17: "第三題觀念檢核題。題目是：1994 年華航名古屋空難中，飛控軟體與人機互動設計的關鍵缺失為何？選項 A：機載電腦中毒導致控制面板死當；選項 B：人機介面未清楚呈現系統當前運作模式，且自動控制與手動操作產生邏輯衝突時缺乏清晰的覆蓋裁決機制；選項 C：高度計感測器硬體線路短路；選項 D：客艙加壓系統軟體異常。大家覺得哪一個是根本原因呢？",
    
    18: "我們來看解答。正確答案是 B。駕駛員不知道電腦仍處於重飛自動控制狀態下手動下壓機首，電腦持續推升機尾配平編排糾正駕駛員，人機爭奪控制權導致失速墜毀。這顯示出模式提示與人機覆蓋裁決機制的關鍵性。",
    
    19: "接下來進入第二單元：軟體品質與定義。正如這句名言說的：「人們會忘記你做得多快，但總會記得你做得多好。」",
    
    20: "到底什麼是軟體呢？根據 IEEE 的標準定義，軟體不單單只是編譯後的程式碼，它更包含了四大要件：程式碼、操作程序、系統文件以及相關資料。這四個要件缺一不可，共同構成完整的軟體產品。",
    
    21: "這張圖表展示了軟體四要素的關係：包括含有代碼的程式，含有操作說明的程序，含有需求與架構的文件，以及含有設定檔與測試資料的相關資料。",
    
    22: "我們來看第四題觀念檢核題。題目是：根據 IEEE 的定義與課程教材，下列何者不屬於「軟體」的範疇？選項 A：開發過程中的系統設計文件與架構圖；選項 B：包含演算法邏輯的原始碼與可執行檔；選項 C：系統安裝手冊與操作程序規範；選項 D：電腦主機板上的中央處理器晶片硬體。大家覺得哪一個不屬於軟體呢？",
    
    23: "我們來看解答.正確答案是 D。IEEE 定義明確指出軟體包含程式、程序、文件與資料，而中央處理器晶片屬於實體硬體，不屬於軟體範疇。",
    
    24: "接下來看哈佛學者 David Garvin 提出的五大品質觀點：超自然觀點、使用者觀點、製造觀點、產品觀點以及價值觀點。在軟體工程中，每一種觀點都代表著不同利害關係人對品質的期待與評估維度。",
    
    25: "這張品質觀點關係圖整合了 Garvin 的五大觀點：超自然觀點著重內隱藝術與優雅度；使用者觀點著重適用性；製造觀點著重符合規格與低缺陷；產品觀點著重結構與乾淨架構；價值觀點著重性價比與商業利益。",
    
    26: "來看第五題觀念檢核題。題目是：某系統完全符合合約規格書的每一項功能要求，但架構混亂極難維護、擴充。依 Garvin 觀點，該軟體在何種觀點上品質不佳？選項 A：製造觀點；選項 B：產品觀點；選項 C：超自然觀點；選項 D：價值觀點。請大家思考看看。",
    
    27: "我們來看解答。正確答案是 B，產品觀點。產品觀點著重於軟體的內在結構特性，像是模組化、架構整潔度與可維護性。雖然符合製造規格，但內在架構品質差，所以屬於產品觀點不佳。",
    
    28: "接著看軟體品質的三層次定義：第一層次是符合規格需求；第二層次是符合適用性與使用者期待；第三層次則是 Pressman 提出的全面品質定義，強調明訂需求、開發標準與專業隱含特性三者並重。",
    
    29: "來看第六題觀念檢核題。題目是：以下關於軟體品質定義的敘述，何者最符合 Pressman 對於全面軟體品質的觀點？選項 A：只要程式碼沒有編譯錯誤就算高品質軟體；選項 B：只要系統能跑、客戶沒有當場抱怨就是好品質；選項 C：軟體必須符合明訂的功能與效能需求、遵循明訂的開發標準，並具備所有專業軟體應有的隱含特性；選項 D：只有不計成本打造出市面上最華麗 UI 的系統才叫高品質。請大家選出最適當的答案。",
    
    30: "我們來看解答。正確答案是 C。Pressman 定義強調了明訂需求、開發標準以及專業軟體隱含特性，像是可維護性、可靠性等，這三者必須兼備。",
    
    31: "接下來進入第三單元：品質模型，特別是經典的 ISO 9126 與現代的 ISO 25010 標準。品質模型就像軟體工程的指北針，為好軟體建立了具體評量依據。",
    
    32: "從不同產業的品質模型對比可以看到，汽車產業最看重安全性與可維護性；機械錶重視走時精準度與超自然觀點；速食餐廳看重速度與一致性；而我們的軟體系統則高度重視擴充性、安全性、移植性與容錯力。",
    
    33: "ISO 9126 定義了六大核心品質特性：功能性、可靠性、可用性、效率性、可維護性以及可攜性。每一項特性下又細分了多個子特性，是評估系統品質的經典框架。",
    
    34: "這張圖表展示了現代 ISO 25010 與本學期核心測試技術的對應地圖。我們將透過單元測試、Testcontainers、效能壓測與動態分析工具，全面守護各項品質特性。",
    
    35: "這張八大特性示意圖是 ISO 25010 的核心分類，包含功能適合性、可靠性、效能效率、易用性、安全性、相容性、可維護性以及可移植性這八個主要維度。",
    
    36: "來看第七題觀念檢核題。題目是：伺服器在網路斷線後能自動重連，且不遺失正在處理的交易資料，這屬於 ISO 9126 的哪一項品質特性？選項 A：可靠性；選項 B：可攜性；選項 C：功能性；選項 D：可用性。請大家想一想。",
    
    37: "我們來看解答。正確答案是 A，可靠性。網路斷線屬於環境異常，系統能在異常下維持運作並迅速恢復資料狀態，屬於可靠性中的容錯度與回復性要求。",
    
    38: "來看第八題觀念檢核題。題目是：某軟體系統在進行人事模組的欄位長度修改時，意外導致完全無關的財務結算模組產生運行錯誤。這代表該軟體在 ISO 9126 中哪項子特性不足？選項 A：易理解性；選項 B：穩定性；選項 C：順應性；選項 D：可取代性。大家覺得是哪一項呢？",
    
    39: "我們來看解答。正確答案是 B，穩定性。穩定性評估系統在受到變更時，避免對其他無關模組造成負面衝擊或副作用的能力。",
    
    40: "來看第九題觀念檢核題。題目是：在 ISO 9126 品質模型中，當我們要評估「軟體系統在不同的硬體、軟體或執行環境間進行轉移的難易程度」時，應檢驗哪一項特性？選項 A：效率性；選項 B：功能性；選項 C：可攜性；選項 D：可靠性。請大家選擇。",
    
    41: "我們來看解答。正確答案是 C，可攜性。可攜性正是定義軟體從一個運行環境移轉到另一個環境的能力。",
    
    42: "接下來進入第四單元：品質控制 QC 與品質保證 QA 的本質區別。",
    
    43: "QC 著重在產品導向與事後檢驗，目的是抓出不良品；而 QA 著重在流程導向與事前預防，目的是改善開發流程，確保產出高品質的軟體。",
    
    44: "而 V 模型則是開發與測試之間的對稱性對應：需求分析對應驗收測試，系統架構對應系統測試，元件設計對應整合測試，編寫代碼對應單元測試。它的價值在於及早規劃測試活動。",
    
    45: "這張 V 模型對稱圖展示了左邊的驗證開發與右邊的確認測試的映射關係：需求規格、架構設計與元件設計分別與驗收測試、系統測試和整合測試平行對稱，在寫代碼前就建立了完整的測試合約。",
    
    46: "而在現代敏捷與 DevOps 流程中，品質是透過自動化流水線上的連續品質門檻來守護的，包括提交門檻、靜態掃描門檻、單元測試門檻、容器化測試門檻、E2E與安全掃描門檻以及部署監控門檻。",
    
    47: "這張 DevOps 門檻圖詳細說明了六大品質檢驗關卡：分別是本地提交、SonarQube靜態分析、JUnit單元測試、Testcontainers容器整合測試、Playwright端到端驗收測試，以及金絲雀部署監控。",
    
    48: "接著看軟體品質成本 CoQ 模型，包含預防成本、評估成本以及內部與外部失敗成本。在軟體工程中，越早投入預防與評估，就能大幅降低代價高昂的外部失敗成本。",
    
    49: "這張品質成本架構與一比十比一百定律圖形說明了，預防與評估是一致性成本，而內部與外部失敗是非一致性成本。提早左移進行測試，可以將修復缺陷的成本維持在需求設計階段的一元，避免生產環境上的一百元巨額損失。",
    
    50: "來看第十題觀念檢核題。題目是：導入自動化靜態程式碼分析（如 SonarQube）與工程師品質培訓課程，在品質成本 CoQ 模型中分別屬於哪兩類？選項 A：內部失敗成本、外部失敗成本；選項 B：評估成本、預防成本；選項 C：預防成本、評估成本；選項 D：皆屬於失敗成本。請大家思考一下。",
    
    51: "我們來看解答。正確答案是 B。靜態分析工具屬於檢查與評估現有品質的評估成本；而人員教育培訓與標準制定則屬於事前預防缺陷發生的預防成本。",
    
    52: "最後我們進行本章重點回顧。",
    
    53: "本章總結：軟體危機提醒我們缺陷代價高昂；軟體包含程式、程序、文件與資料；品質需兼顧規格、用戶與內在架構；並落實 ISO 品質模型與品質成本預防思維。",
    
    54: "以上就是第一章的完整內容。感謝各位同學的認真聆聽，我們下一章再見！"
}

# Image page blacklist (skip showing pointing hand on full screen diagram slides)
# PDF slides are 54 pages in total. Let's find full screen image slide indices:
# Pages 7, 9, 21, 25, 32, 35, 45, 47, 49 are full screen images.
# Title, transition, and CCQ answers can also be skipped.
SKIP_PAGES = {1, 3, 7, 9, 14, 16, 18, 19, 21, 23, 25, 27, 30, 31, 32, 35, 37, 39, 41, 42, 45, 47, 49, 51, 52, 54}

def extract_bold_keywords(md_path):
    if not os.path.exists(md_path):
        return {}
    with open(md_path, "r", encoding="utf-8") as f:
        content = f.read()
    
    parts = content.split("\n---")
    keywords_map = {}
    
    for i in range(1, len(parts)):
        page_num = i
        page_text = parts[i]
        
        # Match only bold keywords at the beginning of a line (bullet items)
        bolds = re.findall(r"^\s*[\*\-\+#\d\.]*\s*\*\*(.*?)\*\*(?::|：)?", page_text, re.MULTILINE)
        cleaned = []
        for b in bolds:
            cleaned_b = b.strip()
            if len(cleaned_b) > 1 and not re.match(r"^[A-D]\.?$", cleaned_b):
                cleaned.append(cleaned_b)
        keywords_map[page_num] = cleaned
    return keywords_map

# Load keywords map
KEYWORDS_MAP = extract_bold_keywords("Slide/source/ch01s_intro.md")

def locate_keyword(page, keyword, scale_x, scale_y):
    # Try exact match search
    rects = page.search_for(keyword)
    if rects:
        x0 = min(r.x0 for r in rects)
        y0 = min(r.y0 for r in rects)
        y1 = max(r.y1 for r in rects)
        y_center = (y0 + y1) / 2
        # Bullet hand pointer position is to the left of the item text (offset left)
        return (x0 - 30) * scale_x, y_center * scale_y - 24 # 24 is half of hand height (48)
        
    # Fallback prefix matching
    clean_kw = re.sub(r'[^\w\u4e00-\u9fff]', '', keyword).strip()
    if len(clean_kw) >= 3:
        for length in range(len(clean_kw), 1, -1):
            sub_kw = clean_kw[:length]
            rects = page.search_for(sub_kw)
            if rects:
                x0 = min(r.x0 for r in rects)
                y0 = min(r.y0 for r in rects)
                y1 = max(r.y1 for r in rects)
                y_center = (y0 + y1) / 2
                return (x0 - 30) * scale_x, y_center * scale_y - 24
                
    return None

def find_keyword_offset_ratio(script_text, keyword):
    if not script_text or not keyword:
        return None
    clean_script = re.sub(r'[^\w\u4e00-\u9fff]', '', script_text)
    clean_kw = re.sub(r'[^\w\u4e00-\u9fff]', '', keyword)
    
    # 1. 嘗試完整匹配
    idx = clean_script.find(clean_kw)
    if idx != -1:
        return idx / len(clean_script)
        
    # 2. 嘗試前 3 個字匹配
    if len(clean_kw) >= 3:
        idx = clean_script.find(clean_kw[:3])
        if idx != -1:
            return idx / len(clean_script)
            
    # 3. 嘗試前 2 個字匹配
    if len(clean_kw) >= 2:
        idx = clean_script.find(clean_kw[:2])
        if idx != -1:
            return idx / len(clean_script)
            
    return None

def get_laser_expression(page_num, duration, page):
    # Disable pointing hand/laser pointer (always off-screen)
    return "-100", "-100"

    D = max(duration, 1.0)
    keywords = KEYWORDS_MAP.get(page_num, [])
    script_text = SCRIPTS.get(page_num, "")
    
    scale_x = 1920 / page.rect.width
    scale_y = 1080 / page.rect.height
    
    targets = []
    for kw in keywords:
        ratio = find_keyword_offset_ratio(script_text, kw)
        if ratio is None:
            continue
        loc = locate_keyword(page, kw, scale_x, scale_y)
        if loc:
            x_pos, y_pos = loc
            targets.append((x_pos, y_pos, ratio))
            
    if not targets:
        return "-100", "-100"

    # Sort targets by their speaking ratio order
    targets.sort(key=lambda t: t[2])
    
    # Build continuous interpolation expressions for X and Y coordinates
    # During the 0.4s transition window, the hand slides smoothly from previous item to next.
    # Otherwise, it stays stationary at the current item.
    x_expr = ""
    y_expr = ""
    
    # Pre-calculate transition intervals
    intervals = []
    for i in range(len(targets)):
        x_curr, y_curr, r_curr = targets[i]
        t_curr = r_curr * D
        
        if i == 0:
            intervals.append((0.0, t_curr, x_curr, y_curr, x_curr, y_curr, False))
            
        if i < len(targets) - 1:
            x_next, y_next, r_next = targets[i+1]
            t_next = r_next * D
            # Transition window is 0.4s or half the interval, whichever is smaller
            trans_d = min(0.4, (t_next - t_curr) / 2)
            t_trans_end = t_curr + trans_d
            
            # Stationary interval at current item
            intervals.append((t_curr, t_trans_end, x_curr, y_curr, x_next, y_next, True))
            # Stationary interval at next item until next transition
            intervals.append((t_trans_end, t_next, x_next, y_next, x_next, y_next, False))
        else:
            # Last item remains active until the end of the slide
            intervals.append((t_curr, D, x_curr, y_curr, x_curr, y_curr, False))

    # Construct FFmpeg expression strings
    x_parts = []
    y_parts = []
    
    # Initial state: before the first item starts speaking, hide the pointer
    t0 = targets[0][2] * D
    x_parts.append(f"between(t,0,{t0:.3f})*(-100)")
    y_parts.append(f"between(t,0,{t0:.3f})*(-100)")
    
    for t_start, t_end, xs, ys, xe, ye, is_trans in intervals:
        cond = f"between(t,{t_start:.3f},{t_end:.3f})"
        if is_trans:
            L = t_end - t_start
            x_parts.append(f"{cond}*({xs:.2f}+({xe - xs:.2f})*(t-{t_start:.3f})/{L:.3f})")
            y_parts.append(f"{cond}*({ys:.2f}+({ye - ys:.2f})*(t-{t_start:.3f})/{L:.3f})")
        else:
            x_parts.append(f"{cond}*({xs:.2f})")
            y_parts.append(f"{cond}*({ys:.2f})")
            
    # Combine expressions
    x_expr = "+".join(x_parts)
    y_expr = "+".join(y_parts)
    
    # Final fallback if t is beyond duration
    x_expr = f"if(between(t,0,{D:.3f}), {x_expr}, -100)"
    y_expr = f"if(between(t,0,{D:.3f}), {y_expr}, -100)"

    return x_expr, y_expr

async def generate_audio(page_num, text, output_path):
    communicate = edge_tts.Communicate(text, "zh-TW-YunJheNeural", rate="+6%")
    await communicate.save(output_path)

def get_audio_duration(audio_path):
    cmd = [
        "/opt/homebrew/bin/ffprobe", "-v", "error",
        "-show_entries", "format=duration",
        "-of", "default=noprint_wrappers=1:nokey=1",
        audio_path
    ]
    res = subprocess.run(cmd, capture_output=True, text=True, check=True)
    return float(res.stdout.strip())

async def main():
    print("=== Step 1: Preparing directories and Pointing Hand Asset ===")
    os.makedirs("/tmp/ch01s_build/slides", exist_ok=True)
    os.makedirs("/tmp/ch01s_build/audio", exist_ok=True)
    os.makedirs("/tmp/ch01s_build/clips", exist_ok=True)
    os.makedirs("MP4", exist_ok=True)
    hand_asset_path = "img/video_assets/hand_pointer.png"
    if not os.path.exists(hand_asset_path):
        print(f"Error: Hand pointer asset not found at {hand_asset_path}!")
        return

    print("\n=== Step 2: Converting PDF pages to 1080p images ===")
    doc = fitz.open("Slide/ch01s_intro.pdf")
    zoom_x = 1920 / doc[0].rect.width
    zoom_y = 1080 / doc[0].rect.height
    mat = fitz.Matrix(zoom_x, zoom_y)

    for i in range(len(doc)):
        page = doc[i]
        pix = page.get_pixmap(matrix=mat)
        img_path = f"/tmp/ch01s_build/slides/slide_{i+1:02d}.png"
        pix.save(img_path)
    print(f"Rendered {len(doc)} slide images successfully.")

    print("\n=== Step 3: Generating TTS Audio (zh-TW-YunJheNeural) ===")
    durations = {}
    for page_num in range(1, len(doc) + 1):
        text = SCRIPTS.get(page_num, f"第 {page_num} 頁。")
        audio_path = f"/tmp/ch01s_build/audio/audio_{page_num:02d}.mp3"
        print(f"Synthesizing page {page_num:02d} audio...")
        await generate_audio(page_num, text, audio_path)
        durations[page_num] = get_audio_duration(audio_path)
    print("All audio files generated and durations measured.")

    print("\n=== Step 4: Generating individual video clips with Pointing Hand ===")
    concat_list_path = "/tmp/ch01s_build/concat_list.txt"
    with open(concat_list_path, "w") as f:
        for page_num in range(1, len(doc) + 1):
            img_path = f"/tmp/ch01s_build/slides/slide_{page_num:02d}.png"
            audio_path = f"/tmp/ch01s_build/audio/audio_{page_num:02d}.mp3"
            clip_path = f"/tmp/ch01s_build/clips/clip_{page_num:02d}.mp4"
            duration = durations[page_num]

            x_expr, y_expr = get_laser_expression(page_num, duration, doc[page_num - 1])

            # Overlay pointing hand image
            filter_str = f"[0:v][1:v]overlay=x='{x_expr}':y='{y_expr}':eval=frame[outv]"

            cmd = [
                "/opt/homebrew/bin/ffmpeg", "-y",
                "-loop", "1", "-i", img_path,
                "-i", hand_asset_path,
                "-i", audio_path,
                "-filter_complex", filter_str,
                "-map", "[outv]",
                "-map", "2:a",
                "-c:v", "libx264", "-preset", "ultrafast", "-crf", "18",
                "-c:a", "aac", "-b:a", "192k",
                "-pix_fmt", "yuv420p",
                "-shortest",
                clip_path
            ]
            subprocess.run(cmd, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, check=True)
            f.write(f"file '{clip_path}'\n")
            print(f"Rendered clip {page_num:02d}/{len(doc)} (Duration: {duration:.2f}s)")

    print("\n=== Step 5: Full Re-encode Concatenation (Zero Drift Guaranteed) ===")
    final_output = "MP4/ch01s_intro.mp4"
    concat_cmd = [
        "/opt/homebrew/bin/ffmpeg", "-y",
        "-f", "concat", "-safe", "0",
        "-i", concat_list_path,
        "-c:v", "libx264", "-preset", "fast", "-crf", "19",
        "-c:a", "aac", "-b:a", "192k",
        "-movflags", "+faststart",
        final_output
    ]
    subprocess.run(concat_cmd, check=True)
    print(f"\n🎉 Successfully created pointing-hand-guided, perfectly synced lecture video at: {final_output}")

if __name__ == "__main__":
    asyncio.run(main())
