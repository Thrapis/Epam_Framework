package page.hp_shop;

import driver.DriverSingleton;
import model.link.CatalogLink;
import model.link.ProductLink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import model.navigation.SearchAttribute;
import wait.WaitElementMethods;

public abstract class HPShopPage {

    protected WebDriver driver;

    protected static final Logger LOGGER = LogManager.getRootLogger();
    protected static final String HOMEPAGE_URL = "https://hp-shop.by/";
    protected static final long WAIT_TIME_SECONDS = 10;

    private static final String ATTRIBUTE_SELECTION_TEMPLATE = "//label[text()='%s']/..";

    private static final By ATTRIBUTE_COMBO_BOX_LOCATOR = By.xpath("//div[@id='product-categori']");
    private static final By SEARCH_INPUT_LOCATOR = By.xpath("//input[@name='search']");
    private static final By CART_BUTTON_LOCATOR = By.xpath("//div[@class='header-cart-area']");

    public HPShopPage() {
        this.driver = DriverSingleton.getDriver();
    }

    public HPShopPage setSearchAttribute(SearchAttribute attribute) {
        By searchAttributeLocator = By.xpath(String.format(ATTRIBUTE_SELECTION_TEMPLATE, attribute.getAttribute()));

        WaitElementMethods.waitForElementLocatedBy(driver, ATTRIBUTE_COMBO_BOX_LOCATOR, WAIT_TIME_SECONDS).click();
        WaitElementMethods.waitForElementLocatedBy(driver, searchAttributeLocator, WAIT_TIME_SECONDS).click();

        LOGGER.info("The attribute '" + attribute.getAttribute() + "' has been set");
        return this;
    }

    public HPShopSearchResultsPage searchForTerms(String term) {
        WebElement searchInputField = WaitElementMethods.waitForElementLocatedBy(driver, SEARCH_INPUT_LOCATOR, WAIT_TIME_SECONDS);
        searchInputField.sendKeys(term);
        searchInputField.submit();

        LOGGER.info("Searched for term '" + term + "'");
        return new HPShopSearchResultsPage();
    }

    public HPShopCartPage openCart() {
        WaitElementMethods.waitForElementLocatedBy(driver, CART_BUTTON_LOCATOR, WAIT_TIME_SECONDS).click();
        WaitElementMethods.waitUntilAjaxCompleted(driver, WAIT_TIME_SECONDS);

        LOGGER.info("Cart opened");
        return new HPShopCartPage();
    }

    public HPShopCatalogPage jumpToCatalogPage(CatalogLink catalogLink) {
        driver.get(catalogLink.getLink());

        LOGGER.info("There was a jump to the catalog page '" + catalogLink.getLink() + "'");
        return new HPShopCatalogPage();
    }

    public HPShopProductPage jumpToProductPage(ProductLink productLink) {
        driver.get(productLink.getLink());

        LOGGER.info("There was a jump to the product page '" + productLink.getLink() + "'");
        return new HPShopProductPage();
    }
}
