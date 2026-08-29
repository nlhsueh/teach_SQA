# Ch02 Bugs, Faults, and Debugging

Chapter 02: Bugs, Faults, and Debugging

> A group of programmers were presenting their accomplishments to the Emperor. "What great achievements have you made this year?" asked the Emperor.
> 
> The programmers discussed among themselves for a moment, then replied: "Compared to last year, we fixed 50% more bugs this year." 😊😊
> 
> The Emperor looked at them with utter confusion. He clearly did not know what a "bug" was. After whispering with his Prime Minister for a moment, he turned back to the programmers, looking displeased.
> 
> "You have committed the crime of poor quality control. From next year on, there shall be no more bugs!"
> 
> He declared this imperial decree in court. 😤
> 
> Naturally, when the programmers reported to the Emperor the following year, they did not mention bugs at all. 🤷🤷🤷
> 
> (Adapted from Gerald M. Weinberg's *Quality Software Management: Volume 1 System Thinking*).

<a href="https://g.co/gemini/share/fdd83982f1a8"><img src = "../../img/ch02/SyPN4Bpcex.png" width=200></a>

---

## 2.1 Bugs and Errors

### 2.1.1 The Origin of Bugs and the Causality Chain

At 3:45 PM on September 9, 1947, **Grace Murray Hopper** recorded the first actual computer bug in her logbook — a moth trapped in the Harvard Mark II relay computer. She taped the moth to her diary and wrote: "*First actual case of bug being found*". This discovery established the term "Bug" in the computer world. Grace Murray Hopper was the first full-time programmer on the Harvard Mark I, created the modern first compiler A-0 system, and the first high-level commercial programming language "COBOL," earning her the title "Mother of COBOL" and the nickname "Amazing Grace."

Colloquially, "Bug" is used to refer to all software issues, but in international software engineering standards (IEEE 610.12), there is a strict four-stage causality chain for errors:

<img src="../../img/ch02/bug_causality_chain.jpg" width="650">

**Diagram Explanation: IEEE 610.12 Four-Stage Bug Causality Chain**
1.  **1. Human Mistake / Error**: A mental slip, misunderstanding of requirements, or typo by an analyst, architect, or developer.
2.  **2. Code Fault / Defect / Bug**: The mistake materialized in software artifacts (e.g., incorrect operator in code, off-by-one missing equal sign).
3.  **3. Internal Error State**: When the CPU executes the code containing the Fault, the memory data or system state becomes inconsistent (e.g., a counter becomes negative).
4.  **4. System Failure**: The observable external behavior of the system deviates from its intended specification (e.g., throwing a 500 Crash, ATM dispensing the wrong amount, system freezing).

> 📌 **Key Theorem**:
> * A **Fault** in the system does not always lead to immediate **Failure** (if the line of code is never executed, or the error state is masked).
> * However, if a **Failure** occurs, it always implies the presence of a **Fault** or an environmental exception!

#### **Concept Check Question (CCQ 1)**

**Question**

While writing a bank transfer algorithm, an engineer mistakenly wrote a minus sign instead of a plus sign in the transaction fee formula and deployed the compiled code to the server. In that day's routine operations, no customer transfers reached the threshold to trigger transaction fees, so no customer noticed any transaction issues. According to the IEEE software engineering definitions, what state is the system currently in?

A) The system has experienced a Failure.  
B) A Fault/Defect exists in the code, but it has not manifested as a system Failure.  
C) The engineer did not make a Mistake because the system is running normally.  
D) The code perfectly conforms to the definition of software correctness.  

<details>
<summary>Click to view Concept Check Question Answer and Explanation</summary>

**Correct Answer: B**

* **Explanation**:
  * **Option B is correct**: The engineer's Mistake has introduced incorrect logic into the code, forming a Fault. Since the specific branch was not executed or did not cause external behavior to deviate on that day, it has not yet manifested as an observable system Failure.
  * **Option A is incorrect**: The customers did not observe any abnormal behavior, so no Failure occurred.
  * **Options C and D are incorrect**: A latent logical error does exist in the code.

</details>

---

### 2.1.2 Defects Caused by Specifications

Not all errors arise from "incorrect coding"; many times they stem from "**Ambiguous or Missing Specifications**".

For a calculator, are the following calculation results or behaviors bugs?
- `5 / 2 = 2` (Integer division vs. floating-point division?)
- `1/3 * 3 = 0.999999` (Floating-point precision limits)
- A power function is needed, but the system does not provide it.
- A square root function is not needed at all, but the system implemented it anyway.
- Inputting `88888888 * 88888888` triggers integer overflow and displays a negative number.
- Inputting `1 / 0` causes an unhandled Crash.

> 📌 **"No spec stands before me, error forms behind me."**

Consider these three specifications:
- *Spec 1:* Design a divider where the user inputs a dividend and divisor, displaying the result to two decimal places.
- *Spec 2:* Design a divider where the user inputs a dividend and divisor. The user must not input a divisor of 0. (*Defect: Unclear how the system should handle it if 0 is input*)
- *Spec 3:* Design a divider. If the user inputs a divisor of 0, the system should clear the result field and return HTTP 400 with a user-friendly error message "Divisor cannot be zero." (*An excellent contract spec*)

<img src="../../img/ch02/spec_fault_failure_venn.jpg" width="650">

**Diagram Explanation: Intersection of Specifications (Spec), Code Faults, and System Failures**
*   **Latent Fault**: A bug in the code (e.g., a memory leak or a specific boundary condition overflow) that has not triggered an external failure under general scenarios.
*   **Specification Gap / Missing Spec Bug**: The specification fails to define exception handling (e.g., user inputs divisor 0 or negative age), leading directly to a system crash.
*   **Observable System Crash**: A defect is triggered and crosses the boundary, resulting in an externally observable functional anomaly or crash.

The absence of failures does not mean there are no defects; meeting explicit specifications does not guarantee high quality. Professional software engineers must have the defensive mindset of "**completing boundary exceptions for specifications**".

#### **Concept Check Question (CCQ 2)**

**Question**

A project manager complained to a client: "The user entered a negative age, which crashed the server. That's a user operation error, not a bug in our code, because the specification doesn't say age can be negative!" From the perspective of modern software engineering and SQA, which of the following statements is most correct?

A) The project manager is entirely correct. The development team is not responsible for any input scenarios not specified in the requirements.  
B) This is a typical case of "missing specification" and "lack of defensive design." Professional software should validate invalid inputs and return friendly errors gracefully instead of crashing.  
C) As long as the database field is set to Integer, any numerical input should not be considered a bug.  
D) Unspecified requirements only need to be fixed if the client is willing to pay extra.  

<details>
<summary>Click to view Concept Check Question Answer and Explanation</summary>

**Correct Answer: B**

* **Explanation**:
  * **Option B is correct**: Professional software quality assurance emphasizes defensive architecture (Robustness & Input Validation). Even if the specification does not list all invalid values, the system must never throw unhandled exceptions or crash due to unvalidated inputs.

</details>

---

### 2.1.3 Classification of Common Coding Errors

1. **Arithmetic and Precision Errors**:
   * Division by zero (Divide by Zero)
   * Integer overflow (Integer Overflow, e.g., `MAX_INT + 1` turning negative)
   * Floating-point rounding and cumulative errors (Floating-point Imprecision)
2. **Logical and Loop Errors**:
   * Infinite loops (Infinite Loop)
   * **Off-by-one errors (OBOB)**:
     ```java
     // A typical off-by-one error: array index out of bounds
     for (int i = 0; i <= array.length; i++) {
         System.out.println(array[i]);
     }
     ```
3. **Resource-related Bugs (Resource Leaks)**:
   * `NullPointerException` (Missing null checks)
   * Memory and connection leaks (Memory / Connection Leak, where streams or DB connections are opened but not closed)
   * Use-after-free errors
4. **Multi-threading and Concurrency Bugs**:
   * **Deadlock**: Thread A waits for B, while B waits for A.
   * **Race Condition**: Lacking proper synchronization, random execution order causes data inconsistency.

---

## 2.2 Debugging Mindset and Methods

> "Finding a bug in your own code is extremely hard; and when you believe your code has absolutely no bugs, it is even harder." — *Steve McConnell*

Debugging is not "changing code randomly to try one's luck (Shotgun Debugging)," but a rigorous **scientific detective process**:
* **Do not just fix the symptoms**: Find the root cause before taking action. Treating the symptoms rather than the root cause only invites more bugs.
* **Defects cluster (Defect Clustering)**: Finding a bug in one place often implies that neighboring logic in the same module or written by the same person has issues as well.
* **Regression testing protection**: When fixing a bug, you must ensure that **existing functionality is not broken** (protected by automated test suites).

### 2.2.1 Five Steps of Scientific Debugging

<img src="../../img/ch02/scientific_debugging_steps.jpg" width="650">

**Diagram Explanation: Five Steps of the Scientific Debugging Flow**
1.  **1. Reproduce**: Establish a Minimal Failing Test Case that reproduces the bug 100% of the time.
2.  **2. Hypothesize**: Propose 1 or 2 root-cause hypotheses based on symptoms, logs, and the call stack.
3.  **3. Experiment**: Set breakpoints or inspect log traces to verify or disprove the hypotheses.
4.  **4. Fix**: Modify the core architecture or algorithmic logic for a clean refactoring, instead of simply wrapping it in a try-catch to swallow the exception.
5.  **5. Regression Test**: Execute the automated test suite to ensure the failing test turns green and existing features remain 100% green without regressions.

---

### 2.2.2 Logical Inference and Debugging

Debugging requires rigorous propositional logic reasoning to avoid common logical fallacies:

* **Confusing Sufficient and Necessary Conditions**:
  * If $p \Rightarrow q$ (Enabling cache causes data errors), you **cannot** infer $q \Rightarrow p$ (Data errors must be caused by the cache).
  * You certainly cannot infer $\neg p \Rightarrow \neg q$ (Disabling cache guarantees no data errors).
* **Contraposition for Multiple Causes**:
  * If $p_1 \wedge p_2 \Rightarrow \text{Crash}$ (Crash only occurs when running in Win10 AND Kaspersky antivirus is installed).
  * Its contrapositive is: $\neg\text{Crash} \Rightarrow \neg p_1 \vee \neg p_2$ (If the system did not crash, at least one of the conditions is not met).

---

### 2.2.3 AI-Assisted Debugging in the AI Era

In 2026, students use LLMs (ChatGPT, Claude, Copilot) daily to help locate bugs. However, **AI-assisted debugging contains significant traps and requires a proper SOP**:

#### ⚠️ Two Common Traps in AI Debugging
1. **"Band-aid / Patch Fixes"**:
   * If you paste a `NullPointerException` error message to an AI, it often suggests a surface fix like `if (obj != null) { ... }`.
   * **Problem**: This merely hides the symptom. The root cause of why `obj` is null (e.g., upstream initialization failure, empty DB query) remains unresolved, delaying the error to a harder-to-debug place!
2. **Confirmation Bias and Regression Damage**:
   * While modifying code, the AI might violate implicit system invariants, introducing hidden **Regression Defects**.

#### 🛡️ Golden SOP for Human-AI Collaborative Debugging (AI Debugging Protocol)
1. **Provide Ample Context**: Do not just paste a single error line. Provide the complete **stack trace, related method code, input data, and expected business rules**.
2. **Ask for Root Causes, Not Just Code**: Prompt: "*Please analyze 3 possible root causes for this exception, and point out whether this fix violates any preconditions.*"
3. **Test-First Bug Fix**: Ask the AI to generate a **"failing unit test that specifically reproduces the bug."** Once fixed, the test should turn green, and you should run the full CI suite to confirm no regressions.

#### **Concept Check Question (CCQ 3)**

**Question**

When a `ConcurrentModificationException` was thrown in production, an engineer pasted the entire code block to an AI. The AI recommended wrapping the failing loop directly in an empty `try-catch` block to swallow the exception. Which of the following evaluations of this approach is most precise?

A) This is an excellent quick fix because the system will no longer throw exceptions to interrupt the service.  
B) This is a dangerous "symptom-only fix (Swallowing Exception)." Although the error is hidden, the underlying concurrent conflict and data inconsistency still exist, leading to more severe data corruption later.  
C) As long as the code compiled, it means it has passed software quality validation.  
D) Concurrency issues only existed prior to Java 8; modern Java frameworks do not need to worry about this exception.  

<details>
<summary>Click to view Concept Check Question Answer and Explanation</summary>

**Correct Answer: B**

* **Explanation**:
  * **Option B is correct**: Swallowing exceptions is a severe anti-pattern. It merely hides the error symptom while the underlying concurrent race condition still exists, corrupting data silently.

</details>

---

## 2.3 Practical Debugging Tools (Debuggers)

Debugging tools are the stethoscope and scalpel of engineers. Modern IDEs (like IntelliJ IDEA) provide extremely powerful features:
* **Conditional Breakpoints**: Pause only when variables meet specific conditions (e.g., `i == 999` or `user.getBalance() < 0`).
* **Exception Breakpoints**: Pause the system and pin the call stack immediately whenever a specific exception (e.g., `NullPointerException`) is thrown.
* **Evaluate Expression**: Execute expressions on the fly to verify hypotheses when execution is paused.

> 🛠️ **Lab Practice Guides**: Please refer to [`Lab/u02_debug/debug.md`](../Lab/u02_debug/debug.md) and [`Lab/u02_debug/Intellij.md`](../Lab/u02_debug/Intellij.md) for hands-on debugging practices.

---

## 2.4 Defensive Programming and Design by Contract (DbC)

Even under green lights, experienced drivers slow down and look both ways because they cannot guarantee others won't run red lights. Programming follows the same principle. **Defensive Programming** is an active engineering attitude to prevent errors from propagating.

### 2.4.1 Three Core Elements of Design by Contract (Bertrand Meyer)

<img src="../../img/ch02/design_by_contract_simplified.jpg" width="650">

**Diagram Explanation: Bertrand Meyer's Design by Contract (DbC) Three Core Laws**
1.  **Preconditions (`requires`)**: Conditions that the caller must satisfy. If they are not met, the callee has the right to refuse execution.
2.  **Postconditions (`ensures`)**: State and outputs that the callee guarantees to achieve after successful execution.
3.  **Class Invariants (`maintains`)**: Core business rules that an object must keep true at all times before and after any public method calls (e.g., `balance >= 0`).

* **Importance of Invariants**:
  * If any operation violates an invariant, the system should fail-fast immediately, preventing dirty data from being written to the database. This is also the foundation of **Property-Based Testing**!

### 2.4.2 Assertions vs. Exception Handling

| Mechanism     | Purpose                                                       | When to Use                                                                         | Production Environment Behavior                  |
| :--------------| :--------------------------------------------------------------| :------------------------------------------------------------------------------------| :-------------------------------------------------|
| **Assertion** | Catching "programmer's own logic bugs" or internal invariants | Private method parameter validation, internal algorithm state, unreachable branches | Can be disabled via `-ea` / `-da` flags          |
| **Exception** | Handling "expected external runtime anomalies"                | Public API parameter validation, network outages, missing files, user input errors  | Always active; must be explicitly handled/caught |

> 🛠️ **Lab Guides Links**:
> * Assertions: [`Lab/u03_preventive/assertion.md`](../Lab/u03_preventive/assertion.md)
> * Exceptions: [`Lab/u03_preventive/exception.md`](../Lab/u03_preventive/exception.md)
> * Structured Logging: [`Lab/u03_preventive/logging.md`](../Lab/u03_preventive/logging.md)

---

## 2.5 Defect Management & Issue Tracking (BTS)

### 📖 2.5.1 Metaphor: The Building Light

<a href="https://g.co/gemini/share/c381192abfd4"><img src = "../../img/ch02/rJuA7H6qxl.png" width=200></a>

"The light in the 26th-floor conference room is on. It should be turned off." The bug report noted: "Please resolve in 5 minutes, just press the switch."

I went to the 26th-floor conference room. **The light was indeed on, but there was no light switch in the room 😳😳**.

I needed to install a switch, but the designer said it ruined the aesthetics, and the wall was concrete. No one would approve buying the tools. The email chain started to panic; the deadline was today. So, I crawled into the ceiling, found the wire, **and snipped it. Problem solved! 😎**

Then everyone worried about how executives would hold meetings, so they asked me to route the wire to the basement. When I went to the basement, **I found dozens of wires left by predecessors hanging on the wall 😲**. I connected the wire and returned to my seat. QA reopened the bug: "The room is still bright!"

I protested, saying the bulb was definitely off. QA said: "**The bug I reported wasn't the bulb, it was the light in the room! It's not dark enough. You should pull down the blinds!**"

> 💡 **Metaphor Analysis**:
> 1. "Band-aid fixes" like cutting wires inevitably lead to larger technical debt (the dozens of wires hanging in the basement).
> 2. If defect definition lacks specification standards, it often degrades into futile arguments like "the bulb vs. the light."

---

### 2.5.2 Defect Tracking Lifecycle State Machine (Bug Workflow)

In professional software teams, defect tracking and management follow a rigorous state transition flow:

<img src="../../img/ch02/defect_lifecycle_complete.jpg" width="650">

**Diagram Explanation: Complete Defect Tracking Lifecycle (Bug Workflow)**
*   **Main Flow States**:
    1.  **New**: Tester or user reports a new defect, waiting for Triage review.
    2.  **Assigned**: Assigned to the responsible engineer and scheduled for fix.
    3.  **Open / In Progress**: Engineer is diagnosing the root cause and writing fix code.
    4.  **Fixed / Resolved**: Engineer submits a PR, passes CI, and waits for QA verification.
    5.  **QA Retest / Verified**: QA retests according to acceptance criteria and regression suites.
    6.  **Closed**: Fix verified, no regressions, issue formally closed.
*   **Branch Flow States**:
    *   **Rejected / Duplicate**: Not a bug, environment configuration error, or duplicate report ➔ Closed directly.
    *   **Deferred**: Non-critical defect for current release ➔ Moved to backlog for future versions.
    *   **Reopened**: QA retest failed ➔ Pushed back to Assigned for redesign/refactoring.

---

### 2.5.3 Severity vs. Priority Decision Matrix

In issue tracking systems (like Jira / GitHub Issues), **Severity** (technical impact) and **Priority** (business urgency) are two orthogonal dimensions:

<img src="../../img/ch02/defect_severity_vs_priority.jpg" width="650">

**Diagram Explanation: Severity vs. Priority 2x2 Decision Matrix**
1.  **1. High Severity + High Priority (Critical Impact - Fix Immediately)**:
    *   *Example*: Core payment service crash, site-wide 500 error, major personal data leakage.
    *   *Strategy*: Deploy hotfix immediately; block the release pipeline.
2.  **2. Low Severity + High Priority (Visibility / Prompt Fix - Fix Rapidly)**:
    *   *Example*: Typo in the company logo on the homepage (e.g., `Compnay`), misleading copy on the main screen.
    *   *Strategy*: Although it doesn't affect the system core, it severely damages reputation and must be fixed with high priority.
3.  **3. High Severity + Low Priority (Major Defect - Schedule Fix)**:
    *   *Example*: Crash occurring only in a rare legacy Windows 95 environment, query failure for a single inactive user.
    *   *Strategy*: Large impact but extremely low probability. Schedule in subsequent sprints.
4.  **4. Low Severity + Low Priority (Minor Issues - Future Optimization)**:
    *   *Example*: Tiny pixel alignment deviation in a rarely-used internal admin panel.
    *   *Strategy*: Optimize when free or handle alongside UI redesigns.

---

## ✍️ 2.6 Comprehensive Practice

1.  **Bug / Fault / Failure Analysis**:
    *   Give an example of a Mistake, Fault, Error State, and Failure in software development, and draw their causal relationships.
2.  **Logical Reasoning in Debugging**:
    *   For a system, it is known: If (out of memory $\vee$ network timeout), then (transaction will roll back $\wedge$ log recorded). If the transaction succeeded and did not roll back today, deduce the states of memory and network.
3.  **MaxHeap Debugging Practice**:
    *   Examine the following `MaxHeap` implementation, identify 3 potential bugs (including index calculation and boundary conditions), and add appropriate `assert` statements to guarantee Heap Invariants:

```java
public class MaxHeap {
    private int[] heap;
    private int size;
    private int capacity;

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private void swap(int index1, int index2) {
        int temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    public void insert(int value) {
        if (size >= capacity) {
            throw new IllegalStateException("Heap is full.");
        }
        heap[size] = value;
        int currentIndex = size;
        size++;

        while (currentIndex > 0 && heap[currentIndex] > heap[getParentIndex(currentIndex)]) {
            swap(currentIndex, getParentIndex(currentIndex));
            currentIndex = getParentIndex(currentIndex);
        }
    }
}
```
