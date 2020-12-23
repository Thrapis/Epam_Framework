package page;

import model.CatalogLink;
import model.ProductLink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import model.SearchAttribute;
import org.openqa.selenium.support.ui.WebDriverWait;
import wait.WaitElementMethods;

public abstract class HPShopPage {

    protected WebDriver driver;

    protected static final String HOMEPAGE_URL = "https://hp-shop.by/";
    protected static final long WAIT_TIME_SECONDS = 10;
    protected static final long WAIT_TIME_FEW_SECONDS = 5;
    protected static final Logger logger = LogManager.getRootLogger();

    private static final String attributeSelectionTemplate = "//label[text()='$']/..";

    public HPShopPage(WebDriver driver) {
        this.driver = driver;
    }

    public HPShopPage setSearchAttribute(SearchAttribute attribute) {
        WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//div[@id='product-categori']"), WAIT_TIME_SECONDS).click();
        WaitElementMethods.waitForElementLocatedBy(driver, By.xpath(attributeSelectionTemplate
                .replace("$", attribute.getAttribute())), WAIT_TIME_SECONDS).click();
        return this;
    }

    public HPShopSearchResultsPage searchForTerms(String term) {
        WebElement searchInputField = WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//input[@name='search']"), WAIT_TIME_SECONDS);
        searchInputField.sendKeys(term);
        searchInputField.submit();
        return new HPShopSearchResultsPage(driver);
    }

    public HPShopCartPage openCart() {
        WaitElementMethods.waitForElementLocatedBy(driver,
                By.xpath("//div[@class='header-cart-area']"), WAIT_TIME_SECONDS).click();

        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME_SECONDS);
        wait.until(driver -> (boolean)((JavascriptExecutor)driver).
                executeScript("return jQuery.active == 0"));

        return new HPShopCartPage(driver);
    }

    public HPShopCatalogPage jumpToCatalogPage(CatalogLink catalogLink) {
        driver.get(catalogLink.getLink());
        return new HPShopCatalogPage(driver);
    }

    public HPShopProductPage jumpToProductPage(ProductLink productLink) {
        driver.get(productLink.getLink());
        return new HPShopProductPage(driver);
    }
}
