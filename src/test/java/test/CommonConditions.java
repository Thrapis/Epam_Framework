package test;

import model.CatalogCategory;
import model.CatalogLink;
import model.CatalogSubcategory;
import model.ProductLink;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import driver.DriverSingleton;
import org.testng.annotations.Listeners;
import util.TestListener;

@Listeners({TestListener.class})
public abstract class CommonConditions {

    protected WebDriver driver;

    final static ProductLink firstProductLink = new ProductLink(CatalogCategory.MONITORS, CatalogSubcategory.HP, "Монитор HP EliteDisplay E223 (1FH45AA)");
    final static ProductLink secondProductLink = new ProductLink(CatalogCategory.ACCESSORIES, CatalogSubcategory.BACKPACKS, "Рюкзак HP Odyssey Red/Black Backpack (X0R83AA)");
    final static ProductLink thirdProductLink = new ProductLink(CatalogCategory.ACCESSORIES, CatalogSubcategory.MOUSES, "Мышь HP X1500 (H4K66AA)");
    final static CatalogLink mouseCatalogPageLink = new CatalogLink(CatalogCategory.ACCESSORIES, CatalogSubcategory.MOUSES);

    @BeforeTest(alwaysRun = true)
    public void setup() {
        driver = DriverSingleton.getDriver();
    }

    @BeforeMethod(alwaysRun = true)
    public void clearCookies() {
        DriverSingleton.deleteAllCookies();
    }

    @AfterTest(alwaysRun = true)
    public void shutdown() {
        DriverSingleton.closeDriver();
    }
}
