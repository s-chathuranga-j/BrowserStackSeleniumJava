import org.bouncycastle.util.test.TestResult;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;

public class TestBase
{
    WebDriver driver;
    public static final String AUTOMATE_USERNAME = "your User Name";
    public static final String AUTOMATE_ACCESS_KEY = "your Access Key";
    public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    DesiredCapabilities caps = new DesiredCapabilities();
    WebDriverWait wait;

    @BeforeSuite
    public void configDevice()
    {
        caps.setCapability("browserName", "iPhone");
        caps.setCapability("device", "iPhone 11");
        caps.setCapability("realMobile", "true");
        caps.setCapability("os_version", "14.0");
        caps.setCapability("name", "BStack-[Java] Sample Test"); // test name
        caps.setCapability("build", "BStack Build Number 1"); // CI/CD job or build name
    }

    @BeforeTest
    public void setupTest() throws MalformedURLException
    {
        driver = new RemoteWebDriver(new URL(URL), caps);
        wait = new WebDriverWait(driver, 5);
    }

    @AfterMethod
    public void markTestStatus(ITestResult result)
    {
        String status;
        if(result.getStatus() == ITestResult.SUCCESS)
            status = "passed";
        else if(result.getStatus() == ITestResult.SKIP)
            status = "error";
        else
            status = "failed";
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""+status+"\"}}");
    }

    @AfterTest
    public void tearDown()
    {
        if (driver!=null)
            driver.quit();
    }
}
