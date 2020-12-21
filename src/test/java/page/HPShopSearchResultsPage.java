package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import wait.WaitElementMethods;

public class HPShopSearchResultsPage extends HPShopPage{

    private static final String productLinkTemplate = "//a[contains(text(), '$')]";

    public HPShopSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public HPShopProductPage selectProductLink(String name) {
        WebElement productInfoLink = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath(productLinkTemplate.replace("$", name)), WAIT_TIME_SECONDS);
        productInfoLink.click();
        return new HPShopProductPage(driver);
    }

    public HPShopSearchResultsPage addToCart(String name) {
        WebElement addToCartButton = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//a[contains(text(), '" + name + "')]/../..//button[@title='В корзину']"), WAIT_TIME_SECONDS);
        addToCartButton.click();
        return this;
    }
}
