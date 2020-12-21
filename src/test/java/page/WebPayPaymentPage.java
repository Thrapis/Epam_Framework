package page;

import org.openqa.selenium.WebDriver;

public class WebPayPaymentPage {

    protected WebDriver driver;

    protected static final long WAIT_TIME_SECONDS = 10;

    public WebPayPaymentPage(WebDriver driver) {
        this.driver = driver;
    }
}
