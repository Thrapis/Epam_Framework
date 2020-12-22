package page;

import model.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wait.WaitElementMethods;

import java.util.ArrayList;
import java.util.List;

public class HPShopCartPage extends HPShopPage{

    private final static String productCountPathTemplate = "//a[contains(text(), '$')]/../../..//input[@class='shk-count']";
    private final static String inputWithTypeAndValueTemplate = "//input[@type='%s' and @value='%s']";
    private final static String inputWithTypeAndNameTemplate = "//input[@type='%s' and @name='%s']";
    private final static String textareaWithIdTemplate = "//textarea[@id='%s']";

    public HPShopCartPage(WebDriver driver) { super(driver); }

    public List<ProductInfo> getProductsFromCart() {
        By productNamesLocator = By.xpath("//td[@class='th-details']/child::h2/child::a");
        By productPricesLocator = By.xpath("//td[@class='th-price']");
        By productCountsLocator = By.xpath("//td[@class='th-qty']//input[@name='count']");

        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME_SECONDS);
        wait.until(ExpectedConditions.elementToBeClickable(productNamesLocator));
        List<WebElement> productNames = WaitElementMethods.fluentWaitForElementsLocatedBy(
                driver, productNamesLocator, WAIT_TIME_SECONDS, 2);
        wait.until(ExpectedConditions.elementToBeClickable(productPricesLocator));
        List<WebElement> productPrices = WaitElementMethods.fluentWaitForElementsLocatedBy(
                driver, productPricesLocator, WAIT_TIME_SECONDS, 2);
        wait.until(ExpectedConditions.elementToBeClickable(productCountsLocator));
        List<WebElement> productCounts = WaitElementMethods.fluentWaitForElementsLocatedBy(
                driver, productCountsLocator, WAIT_TIME_SECONDS, 2);

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
        WebElement productCountElement = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath(productCountPathTemplate.replace("$", name)), WAIT_TIME_SECONDS);
        return Integer.parseInt(productCountElement.getAttribute("value"));
    }

    public double getCartTotalCost() {
        WebElement cartTotal = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//td[@id='cart_total']"), WAIT_TIME_SECONDS);
        String cartTotalText = cartTotal.getText();
        double totalCost = Double.parseDouble(cartTotalText
                .substring(0, cartTotalText.length() - 3)
                .replace(" ", ""));
        return totalCost;
    }

    public CartStatus getCartStatus() {
        try {
            WebElement emptyCartMessage = WaitElementMethods.fluentWaitForElementLocatedBy(driver,
                    By.xpath("//p[text()='Ваша корзина пуста']"), WAIT_TIME_FEW_SECONDS, 1);
            if (emptyCartMessage.isDisplayed()) {
                return CartStatus.EMPTY;
            }
        } catch (Exception exception) { }

        return CartStatus.NOT_EMPTY;
    }

    public HPShopCartPage setDeliveryMethod(DeliveryMethod deliveryMethod) {
        By radioButtonLocator = By.xpath(String.format(inputWithTypeAndValueTemplate, "radio", deliveryMethod.getDeliveryMethod()));
        WaitElementMethods.waitForElementLocatedBy(driver, radioButtonLocator, WAIT_TIME_SECONDS).click();
        return this;
    }

    public HPShopCartPage setPaymentMethod(PaymentMethod paymentMethod) {
        By radioButtonLocator = By.xpath(String.format(inputWithTypeAndValueTemplate, "radio", paymentMethod.getPaymentMethod()));
        WaitElementMethods.waitForElementLocatedBy(driver, radioButtonLocator, WAIT_TIME_SECONDS).click();
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
        return this;
    }

    public HPShopCartPage purgeCart() {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME_SECONDS);
        By purgeCartLocator = By.xpath("//a[@id='butEmptyCart']");
        By confirmButtonLocator = By.xpath("//div[@id='stuffHelper']//button[@id='confirmButton']");

        wait.until(ExpectedConditions.elementToBeClickable(purgeCartLocator));
        WebElement clearCartButton = WaitElementMethods.fluentWaitForElementLocatedBy(
                driver, purgeCartLocator, WAIT_TIME_SECONDS, 1);
        clearCartButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmButtonLocator));
        WebElement confirmButton = WaitElementMethods.fluentWaitForElementLocatedBy(
                driver, confirmButtonLocator, WAIT_TIME_SECONDS, 1);
        confirmButton.click();
        return this;
    }

    public HPShopSuccessOrderPage checkoutOrder() {
        By submitButtonLocator = By.xpath(String.format(inputWithTypeAndValueTemplate, "submit", "Оформить заказ"));
        WaitElementMethods.waitForElementLocatedBy(driver, submitButtonLocator, WAIT_TIME_SECONDS).click();
        return new HPShopSuccessOrderPage(driver);
    }
}
