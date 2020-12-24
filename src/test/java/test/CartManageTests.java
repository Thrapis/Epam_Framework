package test;

import driver.DriverSingleton;
import model.order.CustomerOrderData;
import model.order.DeliveryMethod;
import model.order.PaymentMethod;
import model.info.ProductInfo;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.hp_shop.HPShopCartPage;
import page.hp_shop.HPShopHomePage;
import page.hp_shop.HPShopProductPage;
import page.hp_shop.HPShopSuccessOrderPage;
import page.web_pay.WebPayPaymentPage;
import service.CustomerOrderDataCreator;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CartManageTests extends CommonConditions  {

    @BeforeMethod
    public void preparationForTest() {
        DriverSingleton.deleteAllCookies();
        new HPShopHomePage()
                .jumpToProductPage(firstProductLink)
                .addToCart();
    }

    @AfterMethod()
    public void deleteAllCookies() {
        DriverSingleton.deleteAllCookies();
    }

    @Test(enabled = true)
    public void verifyCartAfterAdditionOfProductTest() {
        double expectedCartTotalCost = 513.0;
        int expectedProductCount = 1;
        String expectedProductName = "Монитор HP EliteDisplay E223 (1FH45AA)";

        HPShopCartPage cartPage = new HPShopProductPage().openCart();

        List<ProductInfo> products = cartPage.getProductsFromCart();

        assertThat(cartPage.getCartTotalCost(), is(equalTo(expectedCartTotalCost)));
        assertThat(products.get(0).getName(), containsString(expectedProductName));
        assertThat(products.get(0).getCount(), is(equalTo(expectedProductCount)));
    }

    @Test(enabled = true)
    public void verifyChangeOfDeliveryMethodTest() {
        double expectedMarkup = 15.0;

        HPShopCartPage cartPage = new HPShopProductPage().openCart();

        double totalCostBeforeChangingOfDeliveryMethod = cartPage.getCartTotalCost();
        double expectedCartTotalCostAfterChangingOfDeliveryMethod = totalCostBeforeChangingOfDeliveryMethod + expectedMarkup;

        cartPage.setDeliveryMethod(DeliveryMethod.BY_COURIER_IN_BELARUS);

        assertThat(cartPage.getCartTotalCost(), is(equalTo(expectedCartTotalCostAfterChangingOfDeliveryMethod)));
    }

    @Test(enabled = true)
    public void verifyCartAfterAdditionAndDeletingOfProductTest() {
        int expectedCountOfProductsInCart = 1;
        String deletingProductName = "Монитор HP EliteDisplay E223 (1FH45AA)";

        HPShopCartPage cartPage = new HPShopProductPage()
                .jumpToProductPage(secondProductLink)
                .addToCart()
                .openCart()
                .deleteProductFromCart(deletingProductName);

        int actualCountOfProductsInCart = cartPage.getProductsFromCart().size();

        assertThat(actualCountOfProductsInCart, is(equalTo(expectedCountOfProductsInCart)));
    }

    @Test(enabled = true)
    public void verifyFailedOrderTest() {
        CustomerOrderData customerOrderData = CustomerOrderDataCreator.withEmptyCredentials();
        String expectedUrl = "https://hp-shop.by/cart.html#zakaz";

        String actualUrl = new HPShopProductPage()
                .openCart()
                .setCustomerOrderData(customerOrderData)
                .submitOrder();

        assertThat(actualUrl, is(equalTo(expectedUrl)));

        HPShopCartPage cartPage = new HPShopCartPage();

        assertThat(cartPage.hasASubmitFailureMessage(), is(true));
    }

    @Test(enabled = true)
    public void verifySuccessOrderWithCardPayTest() {
        CustomerOrderData customerOrderData = CustomerOrderDataCreator.withCredentialsFromProperty();
        String expectedUrl = "https://hp-shop.by/spasibo-za-zakaz.html#zakaz";

        String actualUrl = new HPShopProductPage()
                .openCart()
                .setPaymentMethod(PaymentMethod.ONLINE_PAYMENT_BY_CREDIT_CARD_ON_THE_WEBSITE)
                .setCustomerOrderData(customerOrderData)
                .submitOrder();

        assertThat(actualUrl, is(equalTo(expectedUrl)));

        WebPayPaymentPage paymentPage = new HPShopSuccessOrderPage().payNow();

        assertThat(paymentPage.getEmail(), is(equalTo(customerOrderData.getEmail())));
    }
}
