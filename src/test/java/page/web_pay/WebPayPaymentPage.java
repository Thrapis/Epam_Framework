package page.web_pay;

import driver.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import wait.WaitElementMethods;

public class WebPayPaymentPage {

    protected WebDriver driver;

    protected static final long WAIT_TIME_SECONDS = 10;

    private final static String INPUT_WITH_ID_TEMPLATE = "//input[@id='%s']";

    public WebPayPaymentPage() {
        this.driver = DriverSingleton.getDriver();
    }

    public String getEmail() {
        By submitButtonLocator = By.xpath(String.format(INPUT_WITH_ID_TEMPLATE, "cc_email"));

        return WaitElementMethods.fluentWaitForElementLocatedBy(driver, submitButtonLocator,
                WAIT_TIME_SECONDS, 1).getAttribute("value");
    }
}
