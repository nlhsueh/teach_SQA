package CucumberJava;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)                    // 告知要用 Cucumber 來進行測試
@CucumberOptions(
        features = {"classpath:features"},  // features 的路徑
        glue = {"CucumberJava"},            // 指定 step def 的路徑

        // 指定報告名稱
        plugin = {"pretty", "html:target/cucumber-html-report.html",
                "json:target/cucumber.json",
                "junit:target/cucumber.xml",
                "rerun:target/rerun.txt"
        })

/*
    啟動測試的入口點。
    指定要運行的 feature 文件的位置、step definitions 的包路徑以及生成報告的方式等。
    這個類的存在是為了配置和啟動 Cucumber 測試框架。
 */
public class RunTest {
}