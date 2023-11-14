package com;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class BaseTest {
    public static String browserName = System.getProperty("browser", "chrome");

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected File driverPath;
    protected File browserPath;

    @BeforeAll
    static void setupClass() {
        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();

        } else if (browserName.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();

        } else {
            throw new IllegalArgumentException("Please check 'browser' property setup, We only support chrome or firefox value");
        }

    }

    @BeforeEach
    protected void setupTest() {

        //"chrome by default"
        if (browserName.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();

            options.addArguments("start-maximized");
            options.addArguments("--remote-allow-origins=*");

            options.addArguments("--no-sandbox");

            options.addArguments("--disable-infobars");

            options.addArguments("--disable-dev-shm-usage");

            options.addArguments("--disable-browser-side-navigation");

            options.addArguments("--disable-gpu");


            this.driver = new ChromeDriver(options);
        } else if (browserName.equals("firefox")) {


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
        }
    }


    protected FirefoxDriver startFirefoxDriver() {
        return startFirefoxDriver(new FirefoxOptions());
    }

    protected FirefoxDriver startFirefoxDriver(FirefoxOptions options) {
        options.setImplicitWaitTimeout(Duration.ofSeconds(1));
        driver = new FirefoxDriver(options);
        return (FirefoxDriver) driver;
    }

    protected ChromeDriver startChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setImplicitWaitTimeout(Duration.ofSeconds(1));
        return startChromeDriver(options);
    }

    protected ChromeDriver startChromeDriver(ChromeOptions options) {
        driver = new ChromeDriver(options);
        return (ChromeDriver) driver;
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
