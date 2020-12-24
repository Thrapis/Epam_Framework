package wait;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public abstract class WaitElementMethods {

    public static WebElement waitForElementLocatedBy(WebDriver driver, By by, long time) {
        return new WebDriverWait(driver, time)
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static boolean waitUntilInvisibilityOfElementLocatedBy(WebDriver driver, By by, long time) {
        return new WebDriverWait(driver, time)
                .until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static WebElement waitUntilElementToBeClickable(WebDriver driver, By by, long time){
        return new WebDriverWait(driver, time)
                .until(ExpectedConditions.elementToBeClickable(by));
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

    public static void waitUntilAjaxCompleted(WebDriver driver, long time) {
        new WebDriverWait(driver, time)
                .until(jQueryAJAXCompleted());
    }

    private static ExpectedCondition<Boolean> jQueryAJAXCompleted() {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return (Boolean) ((JavascriptExecutor)
                        driver).executeScript("return (window.jQuery != null) && (jQuery.active == 0);");
            }
        };
    }
}
