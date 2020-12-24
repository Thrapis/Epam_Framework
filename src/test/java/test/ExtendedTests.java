package test;

import model.*;
import org.testng.annotations.Test;
import page.*;
import service.CustomerOrderDataCreator;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExtendedTests extends CommonConditions {

    @Test(enabled = true)
    public void verifyCartAfterAdditionOfProductTest() {
        double expectedCartTotalCost = 513.0;
        int expectedProductCount = 1;
        String expectedProductName = "Монитор HP EliteDisplay E223 (1FH45AA)";
        HPShopCartPage cartPage = new HPShopHomePage(driver)
                .jumpToProductPage(firstProductLink)
                .addToCart()
                .openCart();
        List<ProductInfo> products = cartPage.getProductsFromCart();

        assertThat(cartPage.getCartTotalCost(), is(equalTo(expectedCartTotalCost)));
        assertThat(products.get(0).getName(), containsString(expectedProductName));
        assertThat(products.get(0).getCount(), is(equalTo(expectedProductCount)));
    }

    @Test(enabled = true)
    public void verifyAddingALargeNumberOfProductsTest() {
        int countOfProduct = 2_000_000;
        double expectedCartTotalCost = 1_026_000_000.0;
        HPShopCartPage cartPage = new HPShopHomePage(driver)
                .jumpToProductPage(firstProductLink)
                .setCountOfProduct(countOfProduct)
                .addToCart()
                .openCart();

        assertThat(cartPage.getCartTotalCost(), is(equalTo(expectedCartTotalCost)));
    }

    @Test(enabled = true)
    public void verifyEmptySearchResultsTest() {
        String searchTerm = "qwerty";
        HPShopSearchResultsPage searchResultsPage = new HPShopHomePage(driver)
                .searchForTerms(searchTerm);

        assertThat(searchResultsPage.getSearchStatus(), is(equalTo(FullnessStatus.EMPTY)));
    }

    @Test(enabled = true)
    public void verifyFailedOrderTest() {
        CustomerOrderData customerOrderData = CustomerOrderDataCreator.withEmptyCredentials();

        HPShopPage openedPage = new HPShopHomePage(driver)
                .jumpToProductPage(firstProductLink)
                .addToCart()
                .openCart()
                .setCustomerOrderData(customerOrderData)
                .checkoutOrder();

        assertThat(openedPage, is(instanceOf(HPShopCartPage.class)));
    }

    @Test(enabled = true)
    public void verifyCartAfterAdditionAndDeletingOfProductTest() {
        int expectedCountOfProductsInCart = 1;
        String deletingProductName = "Монитор HP EliteDisplay E223 (1FH45AA)";
        HPShopCartPage cartPage = new HPShopHomePage(driver)
                .jumpToProductPage(firstProductLink)
                .addToCart()
                .jumpToProductPage(secondProductLink)
                .addToCart()
                .openCart()
                .deleteProductFromCart(deletingProductName);
        int actualCountOfProductsInCart = cartPage.getProductsFromCart().size();

        assertThat(actualCountOfProductsInCart, is(equalTo(expectedCountOfProductsInCart)));
    }

    @Test(enabled = true)
    public void verifyCartAfterPurgeTest() {
        HPShopCartPage cartPage = new HPShopHomePage(driver)
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
    public void verifySuccessOrderWithCardPayTest() {
        CustomerOrderData customerOrderData = CustomerOrderDataCreator.withCredentialsFromProperty();

        WebPayPaymentPage paymentPage = new HPShopHomePage(driver)
                .jumpToProductPage(firstProductLink)
                .addToCart()
                .openCart()
                .setPaymentMethod(PaymentMethod.ONLINE_PAYMENT_BY_CREDIT_CARD_ON_THE_WEBSITE)
                .setCustomerOrderData(customerOrderData)
                .checkoutSuccessOrder()
                .payNow();

        assertThat(paymentPage.getEmail(), is(equalTo(customerOrderData.getEmail())));
    }

    @Test(enabled = true)
    public void verifyChangeTheQuantityOfProductsTest() {
        int countOfProduct = 2_000;
        int newCountOfProduct = 500;
        double expectedCartTotalCost = 256_500.0;
        String productName = "Монитор HP EliteDisplay E223 (1FH45AA)";
        HPShopCartPage cartPage = new HPShopHomePage(driver)
                .jumpToProductPage(firstProductLink)
                .setCountOfProduct(countOfProduct)
                .addToCart()
                .openCart()
                .setProductCountFromCart(productName, newCountOfProduct);

        assertThat(cartPage.getCartTotalCost(), is(equalTo(expectedCartTotalCost)));
    }

    @Test(enabled = true)
    public void verifySelectionOnlyElementsOfTypeMouseTest() {
        String expectedNameContainment = "Мышь";
        List<CatalogProductInfo> products = new HPShopHomePage(driver)
                .jumpToCatalogPage(mouseCatalogPageLink)
                .getProductsFromCatalogPage();

        assertThat(products.get(0).getName(), containsString(expectedNameContainment));
    }

    @Test(enabled = true)
    public void verifyChangeOfDeliveryMethodTest() {
        double expectedCartTotalCost = 528.0;
        HPShopCartPage cartPage = new HPShopHomePage(driver)
                .jumpToProductPage(firstProductLink)
                .addToCart()
                .openCart()
                .setDeliveryMethod(DeliveryMethod.BY_COURIER_IN_BELARUS);

        assertThat(cartPage.getCartTotalCost(), is(equalTo(expectedCartTotalCost)));
    }
}
