package page.hp_shop;

import model.status.FullnessStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import wait.WaitElementMethods;

import java.util.List;

public class HPShopSearchResultsPage extends HPShopPage{

    private static final String PRODUCT_LINK_TEMPLATE = "//a[contains(text(), '%s')]";
    private static final String ADD_TO_CART_BUTTON_TEMPLATE = "//a[contains(text(), '%s')]/../..//button[@class='button btn-cart']";

    private final static By PRODUCT_BLOCKS_LOCATOR = By.xpath("//div[@class='single-shop single-product shk-item']");

    public HPShopSearchResultsPage() {
        super();
    }

    public HPShopProductPage selectProductLink(String name) {
        By productLinkLocator = By.xpath(String.format(PRODUCT_LINK_TEMPLATE, name));
        WaitElementMethods.waitForElementLocatedBy(driver, productLinkLocator, WAIT_TIME_SECONDS).click();

        LOGGER.info("Product link of '" + name + "' is selected");
        return new HPShopProductPage();
    }

    public HPShopSearchResultsPage addToCart(String name) {
        By addToCartButtonLocator = By.xpath(String.format(ADD_TO_CART_BUTTON_TEMPLATE, name));
        WaitElementMethods.waitForElementLocatedBy(driver, addToCartButtonLocator, WAIT_TIME_SECONDS).click();

        LOGGER.info("Product of'" + name + "' is added to cart");
        return this;
    }

    public FullnessStatus getSearchStatus() {
        List<WebElement> products = driver.findElements(PRODUCT_BLOCKS_LOCATOR);

        if (products.size() == 0) {
            return FullnessStatus.EMPTY;
        }
        return FullnessStatus.NOT_EMPTY;
    }
}
