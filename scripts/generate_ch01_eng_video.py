import asyncio
import os
import subprocess
import json
import fitz  # PyMuPDF
import edge_tts
from PIL import Image, ImageDraw
import re

# English Slide Voiceover Scripts
SCRIPTS = {
    1: "Hello everyone, and... well... welcome to the course on Software Quality Assurance. Today, we'll introduce Chapter 1: Introduction and Software Quality Concepts, to help you... uh... build a core understanding of software quality engineering.",
    
    2: "First, let's look at the chapter highlights. In the first section, we'll explore the Software Crisis through some major historical cases. In the second section, we'll define software quality and discuss Garvin's five quality views. In the third section, we will study quality models... like ISO 9126 and the modern ISO 25010. And finally, in the fourth section, we'll clarify Quality Control, QA, and the Cost of Quality.",
    
    3: "Now, let's enter Section 1.1: The Software Crisis. We all know the law of conservation of matter in physics... but... in the software industry, engineers often joke about... well... the law of conservation of bugs.",
    
    4: "What exactly is the Software Crisis? In the late 1960s, hardware computing power advanced rapidly, but software development techniques failed to keep up. As software grew in size and complexity, projects frequently suffered from severe delays, huge budget overruns, and poor quality. This is because software is invisible and intangible, making progress hard to measure without standardized engineering workflows.",
    
    5: "Let's look at our first major case: the Patriot Missile incident in 1991. An Iraqi Scud missile hit a US barracks in Dhahran, killing 28 soldiers and injuring over a hundred. Investigation revealed that the Patriot system's internal clock used a 24-bit floating-point register, which introduced a tiny truncation error every hour. Running continuously for over 100 hours accumulated a 0.33-second delay, which caused the radar to miss the Scud missile by 687 meters.",
    
    6: "Our second case is the 1998 NASA Mars Climate Orbiter. This spacecraft, costing nearly 200 million dollars, lost contact and burned up in the Martian atmosphere. The root cause was a unit mismatch: the contractor, Lockheed Martin, used imperial units of pound-force seconds, while NASA JPL expected metric Newton seconds. This incompatible unit interface led to a massive calculation error.",
    
    7: "As we can see in this interface contract breakdown diagram, Lockheed Martin output data in imperial units, which the NASA JPL receiving side parsed as metric Newton seconds. This 4.45 times discrepancy pushed the spacecraft down to 57 kilometers instead of 140, burning it up in the Martian atmosphere.",
    
    8: "Our third case is the 1994 China Airlines Nagoya crash, which killed 264 people. The co-pilot accidentally triggered the Go-Around mode. The pilot tried to manually push the nose down, but the flight control computer, still in Go-Around mode, automatically trimmed the tail stabilizer up to raise the nose. Man and machine fought for control, causing the aircraft to pitch up too steeply, stall, and crash.",
    
    9: "This HMI mode confusion diagram illustrates the pilot pushing down manually while the autopilot was automatically climbing and trimming the tail stabilizer up. The pilot was unaware of the computer's state, leading to a fatal control fight and aerodynamic stall. This shows why status visibility and override priority are critical in safety-critical systems.",
    
    10: "Our fourth case is the 1994 Disney Lion King compatibility crisis. Released during Christmas, this game was bundled with Compaq PCs, but crashed or blue-screened on thousands of home computers. It was built using Microsoft's new WinG graphics library, but was never tested on a wide range of hardware. This compatibility disaster prompted Microsoft to create the standardized DirectX API.",
    
    11: "In the fifth case, we look at several public system failures in Taiwan. The new household registration system in 2014 suffered severe lag on day one due to load testing and database query issues. In 2021, an outsourcing engineer's mistake during VM migration deleted 25,000 students' learning portfolios due to backup validation failure. In 2014, the highway electronic toll collection system suffered frequent double-billing and phantom charges during early rollout.",
    
    12: "What do these cases teach us? As Sam Redwine noted: software and cathedrals are much the same, first we build them, then we pray. Software quality is not just about finding bugs after writing code; it is a full lifecycle engineering discipline involving requirements, architecture, communication, and systematic verification.",
    
    13: "Let's review Concept Check Question 1. What was the fundamental software cause of the Patriot Missile system intercept failure in 1991? Option A: Communication network outage. Option B: Floating-point rounding error in the 24-bit clock register accumulating after 100 hours. Option C: Memory leak in the code. Option D: Radar algorithm target misidentification. Think about which option is correct.",
    
    14: "Let's look at the explanation. Option B is correct. The Patriot system used a 24-bit floating-point value to record time. Continuous operation for 100 hours accumulated a 0.33-second delay, causing the radar search window to miss the Scud missile by about 687 meters.",
    
    15: "Concept Check Question 2. What is the most important lesson from the NASA Mars Climate Orbiter crash? Option A: Use multi-threading to prevent blocking. Option B: Interface specifications and measurement units between modules must be strictly defined and validated. Option C: Avoid third-party libraries. Option D: Small calculation errors do not affect orbit. Take a moment to consider.",
    
    16: "Let's look at the answer. Option B is correct. Lockheed Martin output imperial units while JPL expected metric units. This incompatible interface contract led to thrust miscalculation, reminding us that interface specification validation is critical.",
    
    17: "Concept Check Question 3. What was the critical design flaw in the 1994 China Airlines Nagoya crash? Option A: Computer virus. Option B: Lack of control override arbitration and clear status indications when manual control conflicted with Go-Around mode. Option C: Divide-by-zero exception in fuel calculation. Option D: Autopilot lacked altitude sensing. Which one is correct?",
    
    18: "Let's look at the explanation. Option B is correct. The pilot manually pushed down while the computer executed a Go-Around climb, leading to a stall. This highlights the importance of status visibility and priority override logic.",
    
    19: "Now, let's enter Section 1.2: Software Quality and Definitions. As Howard Newton once said: people forget how fast you did a job, but they remember how well it was done.",
    
    20: "What is software? According to the IEEE standard definition, software is not just the executable code. It consists of four major components: programs, operational procedures, system documentation, and associated initialization data. All four elements are essential to form a complete software product.",
    
    21: "This diagram shows the four elements of software. We have programs containing code and algorithms, procedures defining user manuals and deployment rules, documentation containing requirements and design specs, and data containing configurations and test datasets.",
    
    22: "Concept Check Question 4. According to the IEEE definition, which of the following does NOT fall under software? Option A: System design documents and test cases. Option B: Source code and executable files. Option C: Installation manuals and procedures. Option D: Central processing unit chip hardware on the motherboard. Which one is correct?",
    
    23: "The correct answer is Option D. The IEEE definition states that software includes programs, procedures, documentation, and data. The CPU chip is physical hardware and does not belong to software.",
    
    24: "Next, let's look at David Garvin's five quality views: Transcendental, User view, Manufacturing view, Product view, and Value-based view. Each view represents a different perspective on quality from different stakeholders.",
    
    25: "This matrix summarizes Garvin's five views. The transcendental view is about subjective elegance. The user view focuses on fitness for use. The manufacturing view focuses on process conformance and zero bugs. The product view is about clean code and technical architecture. The value-based view measures business value and subscription willingness.",
    
    26: "Concept Check Question 5. A system perfectly meets every requirement in the contract, but its architecture is messy and difficult to maintain. Which view would deem this software of poor quality? Option A: Manufacturing View. Option B: Product View. Option C: Legal Contract View. Option D: Outsourcing Billing View. Think about it.",
    
    27: "The correct answer is Option B, the Product View. This view focuses on the internal structure of the software, such as modularity and maintainability. Even if it meets specifications, a messy architecture represents poor product-view quality.",
    
    28: "Now, let's look at the three levels of software quality definition. Level one is conformance to specifications. Level two is fitness for use and meeting user expectations. Level three is Pressman's definition, which emphasizes explicit requirements, development standards, and implicit professional characteristics.",
    
    29: "Concept Check Question 6. Which statement best aligns with Pressman's view on software quality? Option A: Running without bugs is high quality. Option B: Meeting spec requirements is enough. Option C: Quality includes explicit functional requirements, explicit development standards, and implicit characteristics expected of professional software. Option D: Quality depends entirely on UI appearance. Choose the best answer.",
    
    30: "The correct answer is Option C. Pressman's definition emphasizes explicit requirements, development standards, and implicit professional characteristics like maintainability and reliability.",
    
    31: "Next, we enter Section 1.3: Quality Models, focusing on ISO 9126 and the modern ISO 25010 standard. Quality models serve as the compass of software engineering, defining what good software is.",
    
    32: "This comparison chart shows how different products have unique quality models. A car focuses on safety and maintainability. A luxury watch focuses on precision and transcendental beauty. Fast food focuses on speed and consistency. In contrast, software systems prioritize scalability, security, portability, and fault tolerance.",
    
    33: "ISO 9126 defines six core quality characteristics: Functionality, Reliability, Usability, Efficiency, Maintainability, and Portability. Each characteristic is further divided into sub-characteristics, forming a classic framework for software quality evaluation.",
    
    34: "This tech map links ISO 25010 quality characteristics to the practical testing tools we will use this semester, such as JUnit 5, jqwik for property-based testing, SonarQube, mutation testing with PITest, load testing with k6, and Pact contract tests.",
    
    35: "Here is the first-level dimension of the ISO 25010 quality model. It expands the original six characteristics into eight: functional suitability, reliability, performance efficiency, usability, security, compatibility, maintainability, and portability.",
    
    36: "Concept Check Question 7. A server automatically reconnects after a network disconnect without losing in-progress transaction data. Which quality characteristic does this represent? Option A: Fault tolerance and recoverability under Reliability. Option B: Installability under Portability. Option C: Attractiveness under Usability. Option D: Compliance under Functionality. Which one is correct?",
    
    37: "The correct answer is Option A, Reliability. A network disconnect is an environmental exception. The system's ability to maintain operation and quickly recover data falls under reliability's sub-characteristics: Fault Tolerance and Recoverability.",
    
    38: "Concept Check Question 8. Modifying a field length in a personnel module accidentally causes a completely unrelated financial module to fail. Which characteristic is performing poorly? Option A: Analyzability. Option B: Stability. Option C: Fault tolerance. Option D: Interoperability. Think about this scenario.",
    
    39: "The correct answer is Option B, Stability. Stability evaluates the system's sensitivity to side effects when changes are made. A change in personnel breaking the financial module shows high coupling and poor stability.",
    
    40: "Concept Check Question 9. Evaluating the ease of transferring a software system from one hardware, software, or execution environment to another represents which quality characteristic? Option A: Functionality. Option B: Maintainability. Option C: Portability. Option D: Efficiency. Choose the correct option.",
    
    41: "The correct answer is Option C, Portability. Portability is defined as the software's ability to be transferred from one environment to another.",
    
    42: "Now, let's enter Section 1.4: Quality Control and Quality Assurance.",
    
    43: "Quality Control, or QC, is product-oriented and focuses on finding defects through post-inspection, like testing and reviews. Quality Assurance, or QA, is process-oriented and focuses on preventing defects by defining development standards, CI/CD pipelines, and audits.",
    
    44: "The V-Model represents the symmetric alignment between verification activities on the left and validation on the right. In this model, we design our acceptance tests parallel to requirements analysis, system tests parallel to system architecture, and integration tests parallel to component design, ensuring early quality planning.",
    
    45: "As shown in this V-Model diagram, development phases on the left are symmetrically mapped to validation levels on the right. Requirements map to acceptance tests, design maps to integration tests, and coding maps to unit tests. This ensures test planning starts long before writing the actual code.",
    
    46: "In modern Agile and DevOps environments, quality checks are automated as Continuous Quality Gates in the CI/CD pipeline. These include commit gates, static code analysis SAST, unit test coverage, containerized integration tests, E2E tests, and production observability.",
    
    47: "This DevOps pipeline diagram details the six continuous quality gates: pre-commit format checks, SonarQube static analysis, JUnit unit tests and JaCoCo coverage, Testcontainers integration testing, Playwright E2E UI tests, and final production observability monitoring.",
    
    48: "Let's look at the Cost of Quality, or CoQ model. It is split into conformance costs, which include prevention and appraisal, and non-conformance costs, which include internal and external failures. The 1 to 10 to 100 rule shows that fixing bugs early in requirements costs 1 dollar, in development costs 10 dollars, but after release costs over 100 dollars.",
    
    49: "This CoQ diagram shows how prevention and appraisal costs represent active investments in quality, while internal and external failures are the cost of poor quality. By shifting testing left, we can catch defects early and avoid expensive production failures.",
    
    50: "Concept Check Question 10. Introducing automated static code analysis and engineer quality training courses fall under which categories in the CoQ model respectively? Option A: Internal Failure Cost, External Failure Cost. Option B: Appraisal Cost, Prevention Cost. Option C: External Failure Cost, Appraisal Cost. Option D: Liability Cost, Maintenance Cost. What do you think?",
    
    51: "The correct answer is Option B. Static analysis checks existing quality and falls under Appraisal Cost, while engineering training prevents defects and falls under Prevention Cost. Both are part of Conformance Costs.",
    
    52: "Finally, let's review the highlights of this chapter.",
    
    53: "In summary, the software crisis shows that defects are costly. Software comprises code, procedures, documentation, and data. SQA involves ISO quality models and cost-of-quality prevention. QA focuses on processes, while QC focuses on products.",
    
    54: "That concludes Chapter 1. Thank you for your attention, and I will see you in the next chapter!"
}

# Image page blacklist (skip showing pointing hand on full screen diagram slides)
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

# Load keywords map for English slides
KEYWORDS_MAP = extract_bold_keywords("Slide/source/ch01s-eng.md")

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
        
    # Clean up non-alphanumeric and try prefix match (for English, word split matching)
    clean_kw = re.sub(r'[^a-zA-Z0-9\s]', '', keyword).strip()
    words = clean_kw.split()
    if words:
        # Search for first word or first 2 words
        search_term = words[0]
        if len(words) > 1:
            search_term = words[0] + " " + words[1]
        rects = page.search_for(search_term)
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
    
    # Lowercase clean strings
    clean_script = re.sub(r'[^a-z0-9]', '', script_text.lower())
    clean_kw = re.sub(r'[^a-z0-9]', '', keyword.lower())
    
    # Try exact match
    idx = clean_script.find(clean_kw)
    if idx != -1:
        return idx / len(clean_script)
        
    # Match prefix of 8 chars
    if len(clean_kw) >= 8:
        idx = clean_script.find(clean_kw[:8])
        if idx != -1:
            return idx / len(clean_script)
            
    # Match prefix of 5 chars
    if len(clean_kw) >= 5:
        idx = clean_script.find(clean_kw[:5])
        if idx != -1:
            return idx / len(clean_script)
            
    # Match prefix of 3 chars
    if len(clean_kw) >= 3:
        idx = clean_script.find(clean_kw[:3])
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
    communicate = edge_tts.Communicate(text, "en-US-BrianNeural", rate="-2%")
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
    os.makedirs("/tmp/ch01s_eng_build/slides", exist_ok=True)
    os.makedirs("/tmp/ch01s_eng_build/audio", exist_ok=True)
    os.makedirs("/tmp/ch01s_eng_build/clips", exist_ok=True)
    os.makedirs("MP4", exist_ok=True)
    hand_asset_path = "img/video_assets/hand_pointer.png"
    if not os.path.exists(hand_asset_path):
        print(f"Error: Hand pointer asset not found at {hand_asset_path}!")
        return

    print("\n=== Step 2: Converting PDF pages to 1080p images ===")
    doc = fitz.open("Slide/ch01s-eng.pdf")
    zoom_x = 1920 / doc[0].rect.width
    zoom_y = 1080 / doc[0].rect.height
    mat = fitz.Matrix(zoom_x, zoom_y)

    for i in range(len(doc)):
        page = doc[i]
        pix = page.get_pixmap(matrix=mat)
        img_path = f"/tmp/ch01s_eng_build/slides/slide_{i+1:02d}.png"
        pix.save(img_path)
    print(f"Rendered {len(doc)} slide images successfully.")

    print("\n=== Step 3: Generating TTS Audio (en-US-AndrewNeural) ===")
    durations = {}
    for page_num in range(1, len(doc) + 1):
        text = SCRIPTS.get(page_num, f"Slide {page_num}.")
        audio_path = f"/tmp/ch01s_eng_build/audio/audio_{page_num:02d}.mp3"
        print(f"Synthesizing page {page_num:02d} audio...")
        await generate_audio(page_num, text, audio_path)
        durations[page_num] = get_audio_duration(audio_path)
    print("All audio files generated and durations measured.")

    print("\n=== Step 4: Generating individual video clips with Pointing Hand ===")
    concat_list_path = "/tmp/ch01s_eng_build/concat_list.txt"
    with open(concat_list_path, "w") as f:
        for page_num in range(1, len(doc) + 1):
            img_path = f"/tmp/ch01s_eng_build/slides/slide_{page_num:02d}.png"
            audio_path = f"/tmp/ch01s_eng_build/audio/audio_{page_num:02d}.mp3"
            clip_path = f"/tmp/ch01s_eng_build/clips/clip_{page_num:02d}.mp4"
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
    final_output = "MP4/ch01s-eng.mp4"
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
    print(f"\n🎉 Successfully created pointing-hand-guided, perfectly synced English lecture video at: {final_output}")

if __name__ == "__main__":
    asyncio.run(main())
