package test;

import driver.DriverSingleton;
import model.info.CatalogProductInfo;
import model.status.FullnessStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.hp_shop.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpecificTests extends CommonConditions {

    @BeforeMethod
    @AfterMethod
    public void deleteAllCookies() {
        DriverSingleton.deleteAllCookies();
    }

    @Test(enabled = true)
    public void verifyEmptySearchResultsTest() {
        String searchTerm = "qwerty";

        HPShopSearchResultsPage searchResultsPage = new HPShopHomePage()
                .openPage()
                .searchForTerms(searchTerm);

        assertThat(searchResultsPage.getSearchStatus(), is(equalTo(FullnessStatus.EMPTY)));
    }

    @Test(enabled = true)
    public void verifyCartAfterPurgeTest() {
        HPShopCartPage cartPage = new HPShopHomePage()
                .jumpToProductPage(firstProductLink)
                .addToCart()
                .jumpToProductPage(secondProductLink)
                .addToCart()
                .jumpToProductPage(thirdProductLink)
                .addToCart()
                .openCart()
                .purgeCart();

        assertThat(cartPage.getCartStatus(), is(equalTo(FullnessStatus.EMPTY)));
    }

    @Test(enabled = true)
    public void verifySelectionOnlyElementsOfTypeMouseTest() {
        String expectedNameContainment = "Мышь";

        List<CatalogProductInfo> products = new HPShopHomePage()
                .jumpToCatalogPage(mouseCatalogPageLink)
                .getProductsFromCatalogPage();

        assertThat(products.get(0).getName(), containsString(expectedNameContainment));
    }
}
