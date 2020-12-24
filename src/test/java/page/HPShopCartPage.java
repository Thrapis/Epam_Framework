package page;

import model.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import wait.WaitElementMethods;

import java.util.ArrayList;
import java.util.List;

public class HPShopCartPage extends HPShopPage{

    private final static String productCountPathTemplate = "//a[contains(text(), '%s')]/../../..//input[@class='shk-count']";
    private final static String productDeleteButtonTemplate = "//a[contains(text(), '%s')]/../../..//td[@class='th-delate']/a";
    private final static String inputWithTypeAndValueTemplate = "//input[@type='%s' and @value='%s']";
    private final static String inputWithTypeAndNameTemplate = "//input[@type='%s' and @name='%s']";
    private final static String textareaWithIdTemplate = "//textarea[@id='%s']";
    private final static String confirmButtonPath = "//div[@id='stuffHelper']//button[@id='confirmButton']";

    public HPShopCartPage(WebDriver driver) { super(driver); }

    public List<ProductInfo> getProductsFromCart() {
        By productNamesLocator = By.xpath("//td[@class='th-details']/child::h2/child::a");
        By productPricesLocator = By.xpath("//td[@class='th-price']");
        By productCountsLocator = By.xpath("//td[@class='th-qty']//input[@name='count']");

        List<WebElement> productNames = driver.findElements(productNamesLocator);
        List<WebElement> productPrices = driver.findElements(productPricesLocator);
        List<WebElement> productCounts = driver.findElements(productCountsLocator);

        List<ProductInfo> products = new ArrayList<ProductInfo>();
        for (int i = 0; i < productNames.size(); i++) {
            String name = productNames.get(i).getText();
            int count = Integer.parseInt(productCounts.get(i).getAttribute("value"));
            double price = Double.parseDouble(productPrices.get(0).getText()
                    .substring(0, productPrices.get(0).getText().length() - 3)
                    .replace(" ", ""));
            products.add(new ProductInfo(name, count, price));
        }
        return products;
    }

    public int getProductCountFromCart(String name) {
        By productLocator = By.xpath(String.format(productCountPathTemplate, name));
        WebElement productCountElement = WaitElementMethods.waitForElementLocatedBy(driver,
                productLocator, WAIT_TIME_SECONDS);

        return Integer.parseInt(productCountElement.getAttribute("value"));
    }

    public double getCartTotalCost() {
        String cartTotalText = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//td[@id='cart_total']"), WAIT_TIME_SECONDS).getText();
        double totalCost = Double.parseDouble(cartTotalText
                .substring(0, cartTotalText.length() - 3)
                .replace(" ", ""));

        return totalCost;
    }

    public FullnessStatus getCartStatus() {
        By rowsLocator = By.xpath("//form[@id='bigCart']//tbody/tr");

        List<WebElement> rows = driver.findElements(rowsLocator);

        if (rows.size() == 0) {
            return FullnessStatus.EMPTY;
        }
        return FullnessStatus.NOT_EMPTY;
    }

    public HPShopCartPage setProductCountFromCart(String name, Integer count) {
        By productLocator = By.xpath(String.format(productCountPathTemplate, name));
        By inputCountLocator = By.xpath("//div[@id='stuffCount']/label/input");
        By confirmButton = By.xpath(confirmButtonPath);

        WaitElementMethods.waitForElementLocatedBy(driver, productLocator, WAIT_TIME_SECONDS).click();
        WebElement inputCount = WaitElementMethods.waitForElementLocatedBy(driver, inputCountLocator, WAIT_TIME_SECONDS);
        inputCount.clear();
        inputCount.sendKeys(count.toString());

        WaitElementMethods.waitForElementLocatedBy(driver, confirmButton, WAIT_TIME_SECONDS).click();
        new WebDriverWait(driver, WAIT_TIME_SECONDS).until(ExpectedConditions.invisibilityOfElementLocated(confirmButton));

        logger.info("Count of product '" + name + "' was set at '" + count.toString() + "'");
        return this;
    }

    public HPShopCartPage setDeliveryMethod(DeliveryMethod deliveryMethod) {
        By radioButtonLocator = By.xpath(String.format(inputWithTypeAndValueTemplate, "radio", deliveryMethod.getDeliveryMethod()));
        WaitElementMethods.waitForElementLocatedBy(driver, radioButtonLocator, WAIT_TIME_SECONDS).click();

        WaitElementMethods.waitUntilAjaxCompleted(driver, WAIT_TIME_SECONDS);

        logger.info("Delivery method was set at '" + deliveryMethod.getDeliveryMethod() + "'");
        return this;
    }

    public HPShopCartPage setPaymentMethod(PaymentMethod paymentMethod) {
        By radioButtonLocator = By.xpath(String.format(inputWithTypeAndValueTemplate, "radio", paymentMethod.getPaymentMethod()));
        WaitElementMethods.waitForElementLocatedBy(driver, radioButtonLocator, WAIT_TIME_SECONDS).click();

        logger.info("Payment method was set at '" + paymentMethod.getPaymentMethod() + "'");
        return this;
    }

    public HPShopCartPage setCustomerOrderData(CustomerOrderData customerOrderData) {
        By nameTextFieldLocator = By.xpath(String.format(inputWithTypeAndNameTemplate, "text", "name"));
        By emailTextFieldLocator = By.xpath(String.format(inputWithTypeAndNameTemplate, "email", "email"));
        By phoneNumberTextFieldLocator = By.xpath(String.format(inputWithTypeAndNameTemplate, "text", "phone"));
        By addressTextFieldLocator = By.xpath(String.format(inputWithTypeAndNameTemplate, "text", "address"));
        By commentTextFieldLocator = By.xpath(String.format(textareaWithIdTemplate, "message"));

        WaitElementMethods.waitForElementLocatedBy(driver, nameTextFieldLocator, WAIT_TIME_SECONDS)
                .sendKeys(customerOrderData.getFullName());
        WaitElementMethods.waitForElementLocatedBy(driver, emailTextFieldLocator, WAIT_TIME_SECONDS)
                .sendKeys(customerOrderData.getEmail());
        WaitElementMethods.waitForElementLocatedBy(driver, phoneNumberTextFieldLocator, WAIT_TIME_SECONDS)
                .sendKeys(customerOrderData.getPhoneNumber());
        WaitElementMethods.waitForElementLocatedBy(driver, addressTextFieldLocator, WAIT_TIME_SECONDS)
                .sendKeys(customerOrderData.getAddress());
        WaitElementMethods.waitForElementLocatedBy(driver, commentTextFieldLocator, WAIT_TIME_SECONDS)
                .sendKeys(customerOrderData.getComment());

        logger.info("Input customer order data");
        return this;
    }

    public HPShopCartPage deleteProductFromCart(String name) {
        By productLocator = By.xpath(String.format(productDeleteButtonTemplate, name));
        WaitElementMethods.waitForElementLocatedBy(driver, productLocator, WAIT_TIME_SECONDS).click();

        By confirmButtonLocator = By.xpath(confirmButtonPath);
        WaitElementMethods.waitUntilElementToBeClickable(driver, confirmButtonLocator, WAIT_TIME_SECONDS);
        WaitElementMethods.fluentWaitForElementLocatedBy(driver, confirmButtonLocator, WAIT_TIME_SECONDS, 1).click();

        WaitElementMethods.waitUntilInvisibilityOfElementLocatedBy(driver, confirmButtonLocator, WAIT_TIME_SECONDS);

        logger.info("Product '" + name + "' deleted from cart");
        return this;
    }

    public HPShopCartPage purgeCart() {
        By purgeCartLocator = By.xpath("//a[@id='butEmptyCart']");
        By confirmButtonLocator = By.xpath(confirmButtonPath);
        By emptyMessageLocator = By.xpath("//div[@id='shopCart']/p");

        //WaitElementMethods.waitUntilElementToBeClickable(driver, purgeCartLocator, WAIT_TIME_SECONDS);
        WaitElementMethods.fluentWaitForElementLocatedBy(driver, purgeCartLocator, WAIT_TIME_SECONDS, 1).click();

        //WaitElementMethods.waitUntilElementToBeClickable(driver, confirmButtonLocator, WAIT_TIME_SECONDS);
        WaitElementMethods.fluentWaitForElementLocatedBy(driver, confirmButtonLocator, WAIT_TIME_SECONDS, 1).click();
        WaitElementMethods.fluentWaitForElementLocatedBy(driver, emptyMessageLocator, WAIT_TIME_SECONDS, 1);

        logger.info("Cart purged");
        return this;
    }

    public HPShopPage checkoutOrder() {
        By submitButtonLocator = By.xpath(String.format(inputWithTypeAndValueTemplate, "submit", "Оформить заказ"));
        By failureLocator = By.xpath("//div[@class='contact-form']/div[@class='errors']");
        WaitElementMethods.waitForElementLocatedBy(driver, submitButtonLocator, WAIT_TIME_SECONDS).click();


        if (driver.findElements(failureLocator).size() != 0) {
            logger.info("Order not checkout");
            return new HPShopCartPage(driver);
        }
        logger.info("Order checkout");
        return new HPShopSuccessOrderPage(driver);
    }

    public HPShopSuccessOrderPage checkoutSuccessOrder() {
        By submitButtonLocator = By.xpath(String.format(inputWithTypeAndValueTemplate, "submit", "Оформить заказ"));
        WaitElementMethods.waitForElementLocatedBy(driver, submitButtonLocator, WAIT_TIME_SECONDS).click();

        logger.info("Order checkout");
        return new HPShopSuccessOrderPage(driver);
    }
}
