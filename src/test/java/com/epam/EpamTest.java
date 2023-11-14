package com.epam;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;




public class EpamTest extends BaseTest {

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
    public void testEpamPage() {
        epamPage.open();
        String value = epamPage.getPageTitle();
        assertEquals("EPAM | Software Engineering & Product Development Services", value);
    }

    @Test
    public void checkStatus() {
        epamPage.open();
        epamPage.turnOnLightMode();
        epamPage.checkSwitcherStat();
    }

    @Test
    public void changeLanguageUA() {
        epamPage.open();
        epamPage.changeLanguage();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        uaPageObject.checkPageTitle();
        uaPageObject.verifyElements();
        epamPage.checkLanguage();
    }

    @Test

    public void checkPoliciesList() {
        epamPage.open();
        epamPage.goToFooter();
        epamPage.checkPoliciesList();
    }

    @Test
    public void checkLocationsList() {
        epamPage.open();
        epamPage.goToLocations();

        assertTrue(epamPage.areLocationsDisplayed(), "All elements are displayed");
        epamPage.switchToLocation("EMEA");
        assertTrue(epamPage.isLocationsListDisplayed("EMEA"), "EMEA locations list is displayed");

        epamPage.switchToLocation("APAC");
        assertTrue(epamPage.isLocationsListDisplayed("APAC"), "APAC locations list is displayed");

        epamPage.switchToLocation("AMERICAS");
        assertTrue(epamPage.isLocationsListDisplayed("AMERICAS"), "Americas locations list is displayed");
    }

    @Test
    public void searchCheck() {
        epamPage.open();
        epamPage.goToSearchField();
        epamPage.searchData("AI");
        epamPage.searchResult();
    }

    @Test
    public void testFormValidation() {

        contactUs.open();
        contactUs.submitForm();

        assertTrue(contactUs.isNameFieldValidated());
        assertTrue(contactUs.isEmailFieldValidated());
        assertTrue(contactUs.isPhoneFieldValidated());
    }

    @Test
    public void testLogoNavigation() {
        aboutPage.open();
        aboutPage.clickCompanyLogo();
        String currentUrl = aboutPage.getCurrentUrl();
        assertEquals("https://www.epam.com/", currentUrl);
    }

    @Test
    public void testDownloadReport() {
        aboutPage.open();
        aboutPage.clickDownloadLink();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String downloadPath = "C:\\Users\\Oleh_Badlo\\Downloads";
        String expectedFileName = "EPAM_Corporate_Overview";
        assertTrue(aboutPage.isFileDownloaded(downloadPath, expectedFileName));
    }

}
