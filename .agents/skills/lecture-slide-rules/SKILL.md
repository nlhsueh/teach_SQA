---
name: lecture-slide-rules
description: Principles and workflows for creating/modifying educational lecture handbooks and Marp slide presentations, including CCQ styling.
---

# Lecture Handbooks and Marp Slide Presentations Customization Guide

This skill guide provides the rules, principles, and template formats for creating or improving python educational handbooks and slide presentations in this repository.

## Terminology / Command Shortcuts

When the user gives the request:
* **`enhance chXX and make slides`** (or similar terminology):
  - **Step 1: Integrate Images**: Rebuild/enhance the existing lecture handbook `Lecture/chXX_xxxx.md` by integrating all conceptual illustration diagrams from the folder `img/chXX/gemini_nb/` into appropriate spots under header sections to aid visual learning.
  - **Step 2: Create Slide Source**: Create the slide presentation Markdown source file `Lecture/chXXs_xxxx.md` using the Gaia theme, layout, and CCQ formatting rules specified below.
  - **Step 3: Compile PDF Slides**: Compile the slide presentation to `.pdf` format only, saving it into the `Slide/` folder. **Do not generate or keep `.html` slide files.**

---

## 1. Educational Handbooks (Lecture Notes `.md`)

### Image Insertion
- Insert conceptual illustration diagrams from `img/ch0X/gemini_nb/` into appropriate spots under header sections to aid visual learning.
- Format:
  ```markdown
  ![Caption Text](../img/ch0X/gemini_nb/XX_image.jpeg)
  ```

### Concept Check Questions (CCQ)
- Insert a CCQ at the end of key sections to test student understanding.
- Answer keys and explanations must be hidden behind a `<details>` block.
- Format:
  ```markdown
  ### **2.X.X 隨堂測驗 (CCQ <N>)**

  **問題**

  [Question text and python code snippet]

  A) Option A
  B) Option B
  C) Option C
  D) Option D

  <details>
  <summary>點擊查看【隨堂測驗】答案與解析</summary>

  **正確答案：[Correct Option]**

  * **解析**：
    * [Detailed explanation of why it is correct and other options are incorrect]

  </details>
  ```

### Runnable Code Blocks
- Ensure python code blocks inside lecture notes are valid and clear.
- Comment out lines that are intended to demonstrate error types (e.g. `TypeError`, `SyntaxError`) to allow students to copy and run files easily without immediate crashes.

---

## 2. Slide Presentations (Marp `.md`)

### Layout and Stylings
- Copy frontmatter styling config from `ch01s_intro.md` to ensure layout consistency.
- Use `_class: lead` for title slides and section transition slides to center text horizontally and vertically.
- Hide header text and display the footer fixed at the bottom-left on all `lead` slides using these CSS rules:
  ```css
    footer {
      position: absolute;
      left: 40px;
      bottom: 40px;
      text-align: left;
    }
    section.lead header {
      display: none !important;
    }
  ```

### HTML Tag Restrictions
- **Do not use block-level HTML tags** (like `<div>` or `<p>`) in slide content, as they interfere with the Markdown parser and break Marp's slide separators (`---`).
- Use **inline HTML tags** (like `<span>` and `<br>`) along with `display: block` in CSS to control individual line styling and spacing.

### Diagram Slides
- Insert diagrams on their own dedicated slide with the `full-image-slide` class.
- Format:
  ```markdown
  ---
  <!-- _class: full-image-slide -->

  <div class="centered-image">
    <img src="../img/ch0X/gemini_nb/XX_image.jpeg" alt="Caption" />
  </div>
  ```

---

## 3. Slide CCQ Formatting

Use a standard two-slide sequence for every Concept Check Question in the slide deck to maintain visual consistency.

### Question Slide
```markdown
---

## Concept Check Question (CCQ <N>)

<div class="ccq-columns">
  <div class="ccq-text">

**[Question text and python code snippet]**

* **A.** Option A
* **B.** Option B
* **C.** Option C
* **D.** Option D

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/question_icon.svg" alt="Question" />
  </div>
</div>
```

### Answer Slide
```markdown
---

## CCQ <N> - 答案與解析

<div class="ccq-columns">
  <div class="ccq-text">

### **正確答案：[Correct Option]**

* **解析**：
  * [Bullet points explaining the answer]

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/answer_icon.svg" alt="Answer" />
  </div>
</div>
```

---

## 4. Compilation Workflow

- Compile Marp slide files using the workspace script:
  ```bash
  ./html-marp.sh <slide-file.md> < /dev/null
  ```
- Verify that the compiled PDF output is placed correctly in the `Slide` folder.
- **CRITICAL RULE**: When making slides, you only need to create the `.md` source and compile the `.pdf` output. **Do not create, compile, or keep `.html` slide files.**
