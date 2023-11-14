package com;

import com.demowebshop.uimapa.CartPage;
import com.demowebshop.uimapa.MainDemoShopPage;
import com.demowebshop.uimapa.WishlistPage;
import com.epam.uimapa.AboutPage;
import com.epam.uimapa.ContactUs;
import com.epam.uimapa.EpamPage;
import com.epam.uimapa.UaPageObject;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseFireFoxTest extends BaseTest {

    public EpamPage epamPage;
    public ContactUs contactUs;
    public UaPageObject uaPageObject;
    public AboutPage aboutPage;
    public MainDemoShopPage mainDemoShopPage;
    public WishlistPage wishlistPage;
    public CartPage cartPage;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.firefoxdriver().setup();

    }


    @BeforeEach
    public void setup() {

        FirefoxOptions options = new FirefoxOptions();

        options.addArguments("--start-maximized");
//        options.addArguments("--remote-allow-origins=*");

        options.addArguments("--no-sandbox");

        options.addArguments("--disable-infobars");

        options.addArguments("--disable-dev-shm-usage");

        options.addArguments("--disable-browser-side-navigation");

        options.addArguments("--disable-gpu");

        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();

//        driver = new ChromeDriver(options);
        epamPage = new EpamPage(this.driver);
        contactUs = new ContactUs(this.driver);
        uaPageObject = new UaPageObject(this.driver);
        aboutPage = new AboutPage(this.driver);
        mainDemoShopPage = new MainDemoShopPage(this.driver);
        wishlistPage = new WishlistPage(this.driver);
        cartPage = new CartPage(this.driver);
        cartPage = new CartPage(this.driver);
    }

}


