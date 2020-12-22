package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import wait.WaitElementMethods;

public class WebPayPaymentPage {

    protected WebDriver driver;

    protected static final long WAIT_TIME_SECONDS = 10;

    private final static String inputWithIdTemplate = "//input[@id='%s']";

    public WebPayPaymentPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getEmail() {
        By submitButtonLocator = By.xpath(String.format(inputWithIdTemplate, "cc_email"));
        return WaitElementMethods.fluentWaitForElementLocatedBy(driver, submitButtonLocator,
                WAIT_TIME_SECONDS, 1).getAttribute("value");
    }
}
