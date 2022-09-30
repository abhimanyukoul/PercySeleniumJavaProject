package org.percyScripts;

import io.percy.selenium.Percy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class myFirstPercySeleniumScriptTest {

    public static RemoteWebDriver remoteWebDriver = null;
    public static ChromeOptions chromeOptions = null;
    public static JavascriptExecutor jsExecutor = null;
    public static WebDriverWait explicitWait = null;
    public static Percy percy = null;
    public static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String PASSWORD = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String URL = "https://" + USERNAME + ":" + PASSWORD + "@hub-cloud.browserstack.com/wd/hub";

    @Before
    public void setExecutionEnvironmentBeforeTest() throws Exception{
        try {
            chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("os", "OS X");
            chromeOptions.setCapability("os_version", "Monterey");
            chromeOptions.setCapability("browser", "Chrome");
            chromeOptions.setCapability("browser_version", "latest");
            chromeOptions.setCapability("project", "assignment");
            chromeOptions.setCapability("build", "Percy Tests");
            chromeOptions.setCapability("name", "executeFileUploadScenarioPercyTest");
            chromeOptions.setCapability("browserstack.local", "false");
            chromeOptions.setCapability("browserstack.debug", "true");
            chromeOptions.setCapability("browserstack.console", "info");
            chromeOptions.setCapability("browserstack.networkLogs", "true");
            chromeOptions.setCapability("browserstack.selenium_version", "4.1.0");
            chromeOptions.setCapability("browserstack.telemetryLogs", "true");

            remoteWebDriver = new RemoteWebDriver(new URL(URL), chromeOptions);
            jsExecutor = (JavascriptExecutor)remoteWebDriver;
            remoteWebDriver.setFileDetector(new LocalFileDetector());
            percy = new Percy(remoteWebDriver);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executeFileUploadScenarioPercyTest () throws Exception{
        try {
            //Code starts here
            remoteWebDriver.get("https://the-internet.herokuapp.com/upload");
            remoteWebDriver.manage().window().maximize();
            remoteWebDriver.manage().deleteAllCookies();
            remoteWebDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

            List<Integer> snapshotsWidth = new ArrayList<Integer>();
            snapshotsWidth.add(375);
            snapshotsWidth.add(1280);

            percy.snapshot("Herokuapp Upload Page", snapshotsWidth, 1024);

            explicitWait = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(30));
            explicitWait.until((ExpectedCondition<Boolean>) remoteWebDriver -> (jsExecutor.executeScript("return document.readyState").toString().equalsIgnoreCase("complete")));

            jsExecutor.executeScript("console.log('Writing logs on Browser Console through Selenium script')");
            jsExecutor.executeScript("console.warn('Warning!')");
            jsExecutor.executeScript("console.error('Error!')");

            LogEntries logEntries = remoteWebDriver.manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logEntries) {
                System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            }

            remoteWebDriver.findElement(By.cssSelector("input#file-upload")).sendKeys("<path of the file to be uploaded including file.extension>");
            remoteWebDriver.findElement(By.cssSelector("input#file-submit")).click();
            explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='example']/h3[text()='File Uploaded!']")));

            percy.snapshot("Herokuapp File Upload Success Page", snapshotsWidth, 1024);

            if (remoteWebDriver.findElement(By.xpath("//div[@class='example']/h3[text()='File Uploaded!']")).getText().equalsIgnoreCase("File Uploaded!")) {
                jsExecutor.executeScript("browserstack_executor:    {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"File uploaded successfully\"}}");
                Assert.assertTrue(true);
            }
            else {
                jsExecutor.executeScript("browserstack_executor:    {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"File upload failed \"}}");
                Assert.fail();
            }
        }
        catch (Exception e) {
            jsExecutor.executeScript("browserstack_executor:    {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"File could not be uploaded in this session\"}}");
            e.printStackTrace();
        }
    }

    @After
    public void clearExecutionEnvironmentAfterTest() throws Exception{
        jsExecutor = null;
        remoteWebDriver.quit();
    }

}
