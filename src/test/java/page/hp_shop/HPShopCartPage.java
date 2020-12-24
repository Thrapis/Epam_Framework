package page.hp_shop;

import model.info.ProductInfo;
import model.order.CustomerOrderData;
import model.order.DeliveryMethod;
import model.order.PaymentMethod;
import model.status.FullnessStatus;
import org.openqa.selenium.*;
import wait.WaitElementMethods;

import java.util.ArrayList;
import java.util.List;

public class HPShopCartPage extends HPShopPage{

    private final static String PRODUCT_COUNT_PATH_TEMPLATE = "//a[contains(text(), '%s')]/../../..//input[@class='shk-count']";
    private final static String PRODUCT_DELETE_BUTTON_TEMPLATE = "//a[contains(text(), '%s')]/../../..//td[@class='th-delate']/a";
    private final static String INPUT_WITH_TYPE_AND_VALUE_TEMPLATE = "//input[@type='%s' and @value='%s']";
    private final static String INPUT_WITH_TYPE_AND_NAME_TEMPLATE = "//input[@type='%s' and @name='%s']";
    private final static String TEXTAREA_WITH_ID_TEMPLATE = "//textarea[@id='%s']";

    private final static By PRODUCT_NAMES_LOCATOR = By.xpath("//td[@class='th-details']/child::h2/child::a");
    private final static By PRODUCT_PRICES_LOCATOR = By.xpath("//td[@class='th-price']");
    private final static By PRODUCT_COUNTS_LOCATOR = By.xpath("//td[@class='th-qty']//input[@name='count']");
    private final static By CART_TOTAL_LOCATOR = By.xpath("//td[@id='cart_total']");
    private final static By PRODUCT_ROWS_LOCATOR = By.xpath("//form[@id='bigCart']//tbody/tr");
    private final static By INPUT_COUNT_LOCATOR = By.xpath("//div[@id='stuffCount']/label/input");
    private final static By CONFIRM_BUTTON_LOCATOR = By.xpath("//div[@id='stuffHelper']//button[@id='confirmButton']");
    private final static By PURGE_CART_LOCATOR = By.xpath("//a[@id='butEmptyCart']");
    private final static By ORDER_FAILURE_LOCATOR = By.xpath("//div[@class='contact-form']/div[@class='errors']");
    private final static By SUBMIT_BUTTON_LOCATOR = By.xpath("//input[@type='submit' and @value='Оформить заказ']");

    public HPShopCartPage() { super(); }

    public List<ProductInfo> getProductsFromCart() {
        List<WebElement> productNames = driver.findElements(PRODUCT_NAMES_LOCATOR);
        List<WebElement> productPrices = driver.findElements(PRODUCT_PRICES_LOCATOR);
        List<WebElement> productCounts = driver.findElements(PRODUCT_COUNTS_LOCATOR);
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
        By productLocator = By.xpath(String.format(PRODUCT_COUNT_PATH_TEMPLATE, name));
        WebElement productCountElement = WaitElementMethods.waitForElementLocatedBy(driver,
                productLocator, WAIT_TIME_SECONDS);

        return Integer.parseInt(productCountElement.getAttribute("value"));
    }

    public double getCartTotalCost() {
        String cartTotalText = WaitElementMethods.waitForElementLocatedBy(driver,
                CART_TOTAL_LOCATOR, WAIT_TIME_SECONDS).getText();
        double totalCost = Double.parseDouble(cartTotalText
                .substring(0, cartTotalText.length() - 3)
                .replace(" ", ""));

        return totalCost;
    }

    public FullnessStatus getCartStatus() {
        List<WebElement> products = driver.findElements(PRODUCT_ROWS_LOCATOR);

        if (products.size() == 0) {
            return FullnessStatus.EMPTY;
        }
        return FullnessStatus.NOT_EMPTY;
    }

    public HPShopCartPage setProductCountFromCart(String name, Integer count) {
        By productLocator = By.xpath(String.format(PRODUCT_COUNT_PATH_TEMPLATE, name));

        WaitElementMethods.waitForElementLocatedBy(driver, productLocator, WAIT_TIME_SECONDS).click();
        WebElement inputCount = WaitElementMethods.waitForElementLocatedBy(driver, INPUT_COUNT_LOCATOR, WAIT_TIME_SECONDS);
        inputCount.clear();
        inputCount.sendKeys(count.toString());

        WaitElementMethods.waitForElementLocatedBy(driver, CONFIRM_BUTTON_LOCATOR, WAIT_TIME_SECONDS).click();
        WaitElementMethods.waitUntilInvisibilityOfElementLocatedBy(driver, CONFIRM_BUTTON_LOCATOR, WAIT_TIME_SECONDS);

        LOGGER.info("Count of product '" + name + "' was set at '" + count.toString() + "'");
        return this;
    }

    public HPShopCartPage setDeliveryMethod(DeliveryMethod deliveryMethod) {
        By deliveryRadioButtonLocator = By.xpath(String.format(INPUT_WITH_TYPE_AND_VALUE_TEMPLATE, "radio", deliveryMethod.getDeliveryMethod()));

        WaitElementMethods.waitForElementLocatedBy(driver, deliveryRadioButtonLocator, WAIT_TIME_SECONDS).click();
        WaitElementMethods.waitUntilAjaxCompleted(driver, WAIT_TIME_SECONDS);

        LOGGER.info("Delivery method was set at '" + deliveryMethod.getDeliveryMethod() + "'");
        return this;
    }

    public HPShopCartPage setPaymentMethod(PaymentMethod paymentMethod) {
        By paymentRadioButtonLocator = By.xpath(String.format(INPUT_WITH_TYPE_AND_VALUE_TEMPLATE, "radio", paymentMethod.getPaymentMethod()));
        WaitElementMethods.waitForElementLocatedBy(driver, paymentRadioButtonLocator, WAIT_TIME_SECONDS).click();

        LOGGER.info("Payment method was set at '" + paymentMethod.getPaymentMethod() + "'");
        return this;
    }

    public HPShopCartPage setCustomerOrderData(CustomerOrderData customerOrderData) {
        By nameTextFieldLocator = By.xpath(String.format(INPUT_WITH_TYPE_AND_NAME_TEMPLATE, "text", "name"));
        By emailTextFieldLocator = By.xpath(String.format(INPUT_WITH_TYPE_AND_NAME_TEMPLATE, "email", "email"));
        By phoneNumberTextFieldLocator = By.xpath(String.format(INPUT_WITH_TYPE_AND_NAME_TEMPLATE, "text", "phone"));
        By addressTextFieldLocator = By.xpath(String.format(INPUT_WITH_TYPE_AND_NAME_TEMPLATE, "text", "address"));
        By commentTextFieldLocator = By.xpath(String.format(TEXTAREA_WITH_ID_TEMPLATE, "message"));

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

        LOGGER.info("Input customer order data");
        return this;
    }

    public HPShopCartPage deleteProductFromCart(String name) {
        By productLocator = By.xpath(String.format(PRODUCT_DELETE_BUTTON_TEMPLATE, name));

        WaitElementMethods.waitForElementLocatedBy(driver, productLocator, WAIT_TIME_SECONDS).click();
        WaitElementMethods.waitUntilElementToBeClickable(driver, CONFIRM_BUTTON_LOCATOR, WAIT_TIME_SECONDS).click();
        WaitElementMethods.waitUntilAjaxCompleted(driver, WAIT_TIME_SECONDS);

        LOGGER.info("Product '" + name + "' deleted from cart");
        return this;
    }

    public HPShopCartPage purgeCart() {
        WaitElementMethods.waitForElementLocatedBy(driver, PURGE_CART_LOCATOR, WAIT_TIME_SECONDS).click();
        WaitElementMethods.waitForElementLocatedBy(driver, CONFIRM_BUTTON_LOCATOR, WAIT_TIME_SECONDS).click();
        WaitElementMethods.waitUntilAjaxCompleted(driver, WAIT_TIME_SECONDS);

        LOGGER.info("Cart purged");
        return this;
    }

    public String submitOrder() {
        WaitElementMethods.waitForElementLocatedBy(driver, SUBMIT_BUTTON_LOCATOR, WAIT_TIME_SECONDS).click();

        return driver.getCurrentUrl();
    }

    public boolean hasASubmitFailureMessage() {
        if (driver.findElements(ORDER_FAILURE_LOCATOR).size() != 0) {
            return true;
        }
        return false;
    }
}
