package com.demowebshop;

import com.BaseTest;
import com.demowebshop.uimapa.CartPage;
import com.demowebshop.uimapa.MainDemoShopPage;
import com.demowebshop.uimapa.WishlistPage;
import com.epam.uimapa.AboutPage;
import com.epam.uimapa.ContactUs;
import com.epam.uimapa.EpamPage;
import com.epam.uimapa.UaPageObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DemoWebShopTest extends BaseTest {
    public EpamPage epamPage;
    public ContactUs contactUs;
    public UaPageObject uaPageObject;
    public AboutPage aboutPage;
    public MainDemoShopPage mainDemoShopPage;
    public WishlistPage wishlistPage;
    public CartPage cartPage;

    @BeforeEach
    protected void setupTest() {
        super.setupTest();
        epamPage = new EpamPage(this.driver);
        contactUs = new ContactUs(this.driver);
        uaPageObject = new UaPageObject(this.driver);
        aboutPage = new AboutPage(this.driver);
        mainDemoShopPage = new MainDemoShopPage(this.driver);
        wishlistPage = new WishlistPage(this.driver);
        cartPage = new CartPage(this.driver);
    }

    @Test
    public void testRegisterUser() {

        mainDemoShopPage.open();
        mainDemoShopPage.registerUser();
        mainDemoShopPage.radioBtn();
        mainDemoShopPage.enterFirstName("Johni");
        mainDemoShopPage.enterLastName("Doer");
        mainDemoShopPage.enterEmail();
        mainDemoShopPage.enterPassword("password123");
        mainDemoShopPage.confirmPassword("password123");
        mainDemoShopPage.clickRegisterButton();

        String successMessage = mainDemoShopPage.getRegistrationSuccessMessage();

        assertEquals("Your registration completed", successMessage);
    }

    @Test
    public void loginCheck() {
        mainDemoShopPage.open();
        mainDemoShopPage.loginCheck("qwe@yahoo.com", "qwe123");
    }

    @Test
    public void testVerifyComputersSubGroups() {

        mainDemoShopPage.open();

        mainDemoShopPage.navigateToComputers();
        assertTrue(mainDemoShopPage.isSubGroupPresent("Desktops"));
        assertTrue(mainDemoShopPage.isSubGroupPresent("Notebooks"));
        assertTrue(mainDemoShopPage.isSubGroupPresent("Accessories"));
    }

    @Test
    public void checkSortingItems() {
        mainDemoShopPage.open();
        mainDemoShopPage.navigateToDesktops();


        mainDemoShopPage.selectSortByOption("Name: A to Z");
        List<String> sortedProductNames = mainDemoShopPage.getListOfProductNames();
        mainDemoShopPage.selectSortByOption("Name: Z to A");
        List<String> reverseSortedProductNames = mainDemoShopPage.getListOfProductNames();
        assertNotEquals(sortedProductNames, reverseSortedProductNames);
        Collections.sort(sortedProductNames, Collections.reverseOrder());
        assertEquals(sortedProductNames, reverseSortedProductNames);
    }

    @Test
    public void testChangeItemsPerPage() {
        mainDemoShopPage.open();
        mainDemoShopPage.navigateToDesktops();
        String itemsSelector = (".product-grid");
        int initialItemsPerPage = mainDemoShopPage.getNumberOfItemsInContainer(itemsSelector);
        mainDemoShopPage.selectItemsPerPage("4");
        int currentItemsPerPage = mainDemoShopPage.getNumberOfItemsInContainer(itemsSelector);

        assertNotEquals(initialItemsPerPage, currentItemsPerPage);
    }

    @Test
    public void checkWishList() {


        mainDemoShopPage.open();

        Integer qtyInitial = wishlistPage.checkWishListNumber();
        mainDemoShopPage.goToWishItem();
        mainDemoShopPage.addToWishlist();
        Integer qtyAfter = wishlistPage.checkWishListNumber();
        wishlistPage.openWishList();
        List listOfItems = wishlistPage.getListOfWishlistItems();
        assertNotEquals(qtyInitial, qtyAfter);
        assertTrue(listOfItems.contains("Blue and green Sneaker"));
    }
    @Test
    public void testAddToCart(){
        mainDemoShopPage.open();
    //Check the number of items in cart UI
    Integer qtyInitialCart = cartPage.checkCartListNumber();
        mainDemoShopPage.navigateToDesktops();
        mainDemoShopPage.addToCartItem("Build your own expensive computer");
    Integer qtyAfterCart = cartPage.checkCartListNumber();
        cartPage.openCart();
    List listOfCartItems = cartPage.getListOfCartItems();
    assertNotEquals(qtyInitialCart, qtyAfterCart);
    assertTrue(listOfCartItems.contains("Build your own expensive computer"));
}
   @Test
    public void removeItemCart(){
        mainDemoShopPage.open();
        mainDemoShopPage.navigateToDesktops();
        mainDemoShopPage.addToCartItem("Build your own expensive computer");
        cartPage.openCart();
        List listOfCartItems = cartPage.getListOfCartItems();
        assertTrue(listOfCartItems.contains("Build your own expensive computer"));
        cartPage.removeItemCart("Build your own expensive computer");
        List listOfCartItemsAfter = cartPage.getListOfCartItems();
        assertFalse(listOfCartItemsAfter.contains("Build your own expensive computer"));
    }
    @Test
    public void checkout(){
        mainDemoShopPage.open();
        mainDemoShopPage.loginCheck("qwe@yahoo.com", "qwe123");
        mainDemoShopPage.navigateToDesktops();
        mainDemoShopPage.addToCartItem("Build your own expensive computer");
        List listOfCartItems = cartPage.getListOfCartItems();
        assertTrue(listOfCartItems.contains("Build your own expensive computer"));
        cartPage.confirmTherms();
        cartPage.pressCheckoutBtn();
        cartPage.checkoutProcess();
        List listOfCartItemsAfter = cartPage.getListOfCartItems();
        assertFalse(listOfCartItemsAfter.contains("Build your own expensive computer"));
    }
}
