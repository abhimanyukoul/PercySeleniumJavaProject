package org.percyScripts;

import com.browserstack.local.Local;
import io.percy.selenium.Percy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class mySecondPercySeleniumScriptLocalTest {

    public static RemoteWebDriver remoteWebDriver = null;
    public static ChromeOptions chromeOptions = null;
    public static JavascriptExecutor jsExecutor = null;
    public static WebDriverWait explicitWait = null;
    public static Percy percy = null;
    public static Local bsLocal = null;
    public static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String PASSWORD = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String URL = "https://" + USERNAME + ":" + PASSWORD + "@hub-cloud.browserstack.com/wd/hub";

    @Before
    public void setExecutionEnvironmentBeforeTest() throws Exception{
        try {
            // Creates an instance of Local
            bsLocal = new Local();

            HashMap<String, String> bsLocalArgs = new HashMap<String, String>();
            bsLocalArgs.put("key", PASSWORD);
            bsLocalArgs.put("localIdentifier", "percySeleniumLocalTest");
            bsLocalArgs.put("forcelocal", "true");

            bsLocal.start(bsLocalArgs);

            chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("os", "OS X");
            chromeOptions.setCapability("os_version", "Monterey");
            chromeOptions.setCapability("browser", "Chrome");
            chromeOptions.setCapability("browser_version", "latest");
            chromeOptions.setCapability("project", "assignmentPercy");
            chromeOptions.setCapability("build", "Percy Tests On Github");
            chromeOptions.setCapability("name", "dynamicTimerDataSiteScenarioPercyTestOnGithub");
            chromeOptions.setCapability("browserstack.local", "true");
            chromeOptions.setCapability("browserstack.localIdentifier", "percySeleniumLocalTest");
            chromeOptions.setCapability("browserstack.debug", "true");
            chromeOptions.setCapability("browserstack.console", "info");
            chromeOptions.setCapability("browserstack.networkLogs", "true");
            chromeOptions.setCapability("browserstack.selenium_version", "4.1.0");
            chromeOptions.setCapability("browserstack.telemetryLogs", "true");

            remoteWebDriver = new RemoteWebDriver(new URL(URL), chromeOptions);
            jsExecutor = (JavascriptExecutor)remoteWebDriver;
            percy = new Percy(remoteWebDriver);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dynamicTimerDataSiteScenarioPercyTest () throws Exception{
        try {
            // Check if BrowserStack local instance is running
            System.out.println(bsLocal.isRunning());

            remoteWebDriver.get("http://abhimanyu:Apache15$@localhost/temp.html");
            remoteWebDriver.manage().window().maximize();
            remoteWebDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(45));

            Thread.sleep(10000);

            List<Integer> snapshotsWidth = new ArrayList<Integer>();
            snapshotsWidth.add(375);
            snapshotsWidth.add(768);
            snapshotsWidth.add(1280);

            percy.snapshot("HTML Assignment Webpage - Local - One", snapshotsWidth, 1024, false, "p#time {visibility: hidden;}");

            explicitWait = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(30));
            explicitWait.until((ExpectedCondition<Boolean>) remoteWebDriver -> (jsExecutor.executeScript("return document.readyState").toString().equalsIgnoreCase("complete")));

            remoteWebDriver.findElement(By.cssSelector("input#username")).sendKeys("<username>");
            remoteWebDriver.findElement(By.cssSelector("input#password")).sendKeys("<password>");
            remoteWebDriver.findElement(By.cssSelector("input#signIn")).click();
            explicitWait.until((ExpectedCondition<Boolean>) remoteWebDriver -> (jsExecutor.executeScript("return document.readyState").toString().equalsIgnoreCase("complete")));

            remoteWebDriver.findElement(By.cssSelector("input#Male")).click();
            Thread.sleep(5000);
            remoteWebDriver.findElement(By.cssSelector("input#Pizza")).click();
            remoteWebDriver.findElement(By.cssSelector("input#Burger")).click();
            Thread.sleep(5000);

            Select dropdown = new Select(remoteWebDriver.findElement(By.cssSelector("select#selectSports")));
            dropdown.selectByVisibleText("Basketball");
            Thread.sleep(5000);
            dropdown.selectByIndex(3);
            Thread.sleep(5000);

            jsExecutor.executeScript("window.scrollBy(0, 750)");
            Thread.sleep(5000);
            jsExecutor.executeScript("window.scrollBy(0, -750)");
            Thread.sleep(5000);

            percy.snapshot("HTML Assignment Webpage - Local - Two", snapshotsWidth, 1024, false, "p#time {visibility: hidden;}");
            Thread.sleep(5000);

            jsExecutor.executeScript("browserstack_executor:    {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"All Local Testing steps completed successfully\"}}");

            // Check if BrowserStack local instance is running
            System.out.println(bsLocal.isRunning());
        }
        catch (Exception e) {
            jsExecutor.executeScript("browserstack_executor:    {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"All Local Testing steps not completed successfully\"}}");
            e.printStackTrace();
        }
    }

    @After
    public void clearExecutionEnvironmentAfterTest() throws Exception{
        jsExecutor = null;
        remoteWebDriver.quit();

        bsLocal.stop();
    }

}
