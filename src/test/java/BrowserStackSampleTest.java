import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class BrowserStackSampleTest extends TestBase
{
    @Test
    public void verifyBSSiteTitle()
    {
        driver.get("https://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("BrowserStack");
        element.submit();
        wait.until(ExpectedConditions.titleContains("BrowserStack"));
    }
}
