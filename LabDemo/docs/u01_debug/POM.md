## Maven 與 `pom.xml` 的介紹

**Maven** 是一個基於 Java 的專案管理工具，主要用來自動化處理專案的構建、依賴管理以及專案生命周期的管理。它支援專案從編譯、測試到打包等各個階段的自動化處理，使得開發者可以更專注於程式碼本身，而不必花費太多時間處理複雜的建構流程。

Maven 使用一個稱為 **`pom.xml`**（Project Object Model）檔案來描述專案。這個檔案包含專案的基本資訊、構建配置、外掛、依賴庫等，是 Maven 專案管理的核心。

### `pom.xml` 的作用

- **專案資訊管理**：包括專案名稱、版本、開發者資訊、專案描述等。
- **依賴管理**：管理外部庫（如第三方框架或工具）的版本與下載。Maven 根據 `pom.xml` 中的依賴設定，自動從中央倉庫或本地倉庫下載所需的 jar 檔案。
- **外掛管理**：Maven 可以使用外掛來進行編譯、測試、打包等動作。`pom.xml` 記錄了哪些外掛需要運行，以及運行時的配置。
- **專案生命周期管理**：Maven 通過不同的構建生命周期（如編譯、測試、打包等）來自動執行對應的任務，並在 `pom.xml` 中指定不同階段應該做的事情。

### 常用的 `pom.xml` 語法

#### 1. **專案基本資訊**
```xml
<groupId>com.example</groupId>  <!-- 組別 ID，通常是公司或組織的名稱 -->
<artifactId>my-project</artifactId>  <!-- 專案 ID，專案的唯一識別名稱 -->
<version>1.0.0</version>  <!-- 專案的版本號 -->
```

#### 2. **依賴管理**
在 `pom.xml` 中，透過 `dependencies` 區塊來管理外部依賴，如：
```xml
<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>  <!-- 只在測試階段使用此依賴 -->
    </dependency>
</dependencies>
```
Maven 會根據設定自動下載相應的依賴到本地倉庫，並將它們加入到專案的類路徑中。

#### 3. **外掛管理**
外掛通常用來自動化處理某些構建過程中的操作。例如，使用 `maven-compiler-plugin` 來進行 Java 的編譯：
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>11</source>  <!-- 指定編譯時使用的 Java 版本 -->
                <target>11</target>  <!-- 指定編譯後的 Java bytecode 版本 -->
            </configuration>
        </plugin>
    </plugins>
</build>
```

#### 4. **構建設定**
透過 `build` 區塊可以設定專案的構建行為，比如打包類型：
```xml
<build>
    <finalName>my-app</finalName>  <!-- 最終生成的 jar 或 war 檔案名稱 -->
    <plugins>
        <!-- 外掛設定放置於此 -->
    </plugins>
</build>
```

#### 5. **配置 Profile**
有時不同環境需要不同的配置，`profiles` 可以根據環境動態調整專案的構建行為：
```xml
<profiles>
    <profile>
        <id>development</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <environment>dev</environment>
        </properties>
    </profile>
    
    <profile>
        <id>production</id>
        <properties>
            <environment>prod</environment>
        </properties>
    </profile>
</profiles>
```

### 常見的 Maven 應用場景

1. **依賴管理**：Maven 自動管理第三方庫及其版本，避免開發者手動下載 jar 檔案。例如，加入 Spring Framework 或 Hibernate 等依賴。
   
2. **多模組專案**：Maven 支援多模組專案管理，可以定義一個父 POM 檔案，讓多個子專案共享相同的配置和依賴。

3. **自動化測試**：使用測試框架（如 JUnit）自動執行單元測試，並將測試報告生成在 `target` 目錄下。

4. **持續整合與構建**：Maven 常被集成到 CI/CD（如 Jenkins）流程中，來自動化編譯、測試和部署。

### 結論

Maven 是一個強大的專案管理工具，通過 `pom.xml`，開發者可以輕鬆管理專案的依賴、外掛和構建過程。學會使用 Maven 和理解 `pom.xml` 的配置語法後，能夠大幅簡化 Java 專案的開發和維護流程。

### 編譯的設定
```xml
    <properties>
        <maven.compiler.source>18</maven.compiler.source>
        <maven.compiler.target>18</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
```
即便你的 jdk是 version 23, 但此設定要求你的語法必須符合 18 的標準，產出必須能在 18 上執行。