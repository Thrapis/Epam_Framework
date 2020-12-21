package wait;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public abstract class WaitElementMethods {

    public static WebElement waitForElementLocatedBy(WebDriver driver, By by, long time) {
        return new WebDriverWait(driver, time)
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static WebElement fluentWaitForElementLocatedBy(WebDriver driver, By by, long time, long pollingEvery) {
        Wait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(time))
                .pollingEvery(Duration.ofSeconds(pollingEvery))
                .ignoring(NoSuchElementException.class);
        return  (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static List<WebElement> waitForElementsLocatedBy(WebDriver driver, By by, long time) {
        return new WebDriverWait(driver, time)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    public static List<WebElement> fluentWaitForElementsLocatedBy(WebDriver driver, By by, long time, long pollingEvery) {
        Wait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(time))
                .pollingEvery(Duration.ofSeconds(pollingEvery))
                .ignoring(NoSuchElementException.class);
        return  (List<WebElement>) wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }
}
