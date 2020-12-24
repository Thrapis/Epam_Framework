package test;

import model.link.CatalogCategory;
import model.link.CatalogLink;
import model.link.CatalogSubcategory;
import model.link.ProductLink;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import driver.DriverSingleton;
import util.TestListener;

@Listeners({TestListener.class})
public abstract class CommonConditions {

    protected WebDriver driver;

    protected final static ProductLink firstProductLink = new ProductLink(CatalogCategory.MONITORS, CatalogSubcategory.HP, "Монитор HP EliteDisplay E223 (1FH45AA)");
    protected final static ProductLink secondProductLink = new ProductLink(CatalogCategory.ACCESSORIES, CatalogSubcategory.BACKPACKS, "Рюкзак HP Odyssey Red/Black Backpack (X0R83AA)");
    protected final static ProductLink thirdProductLink = new ProductLink(CatalogCategory.ACCESSORIES, CatalogSubcategory.MOUSES, "Мышь HP X1500 (H4K66AA)");

    protected final static CatalogLink mouseCatalogPageLink = new CatalogLink(CatalogCategory.ACCESSORIES, CatalogSubcategory.MOUSES);

    @BeforeTest(alwaysRun = true)
    public void setup() {
        driver = DriverSingleton.getDriver();
    }

    @AfterTest(alwaysRun = true)
    public void shutdown() {
        DriverSingleton.closeDriver();
    }
}
