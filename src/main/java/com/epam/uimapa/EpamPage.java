package com.epam.uimapa;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class EpamPage {
    private WebDriver driver;
//    private By nameLocator = new By.ByCssSelector(".header__logo.header__logo-light");

    public EpamPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://www.epam.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#onetrust-accept-btn-handler"))).click();

    }

    public void goToFooter() {
        WebElement police = driver.findElement(By.cssSelector(".policies"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", police);
    }

    public String getPageTitle() {
        return
                this.driver.getTitle();
    }

    public void turnOnLightMode() {
        driver.findElement(By.cssSelector(".header__content > .theme-switcher-ui .switch")).click();
    }

    public void checkSwitcherStat() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement switcherStat = wait.until(
                visibilityOfElementLocated
                        (By.cssSelector(".light-mode")));
    }

    public void changeLanguage() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.cssSelector(".location-selector__button")).click();


        WebElement dropDown = wait.until(
                visibilityOfElementLocated
                        (By.cssSelector(".location-selector__panel a[href=\"https://careers.epam.ua\"]")));
        dropDown.click();
    }

    public boolean checkPoliciesList() {

        WebElement investors = driver.findElement(By.cssSelector("a[href=\"/investors\"]"));
        WebElement cookie = driver.findElement(By.cssSelector("a[href=\"/cookie-policy\"]"));
        WebElement opensource = driver.findElement(By.cssSelector("a[href=\"/services/engineering/open-source\"]"));
        WebElement notice = driver.findElement(By.cssSelector("a[href=\"/applicant-privacy-notice\"]"));
        WebElement privacy = driver.findElement(By.cssSelector("a[href=\"https://privacy.epam.com/core/interaction/showpolicy?type=CommonPrivacyPolicy\"]"));
        WebElement accessibility = driver.findElement(By.cssSelector("a[href=\"/web-accessibility-statement\"]"));

        boolean isInvestorsCorrect = investors.isDisplayed() && investors.getText().contains("INVESTORS");
        boolean isCookieCorrect = cookie.isDisplayed() && cookie.getText().contains("COOKIE POLICY");
        boolean isOpensourceCorrect = opensource.isDisplayed() && opensource.getText().contains("OPEN SOURCE");
        boolean isNoticeCorrect = notice.isDisplayed() && notice.getText().contains("APPLICANT PRIVACY NOTICE");
        boolean isPrivacyCorrect = privacy.isDisplayed() && privacy.getText().contains("APPLICANT PRIVACY NOTICE");
        boolean isAccessibilityCorrect = accessibility.isDisplayed() && accessibility.getText().contains("WEB ACCESSIBILITY");

        return isInvestorsCorrect && isCookieCorrect && isOpensourceCorrect && isNoticeCorrect && isPrivacyCorrect && isAccessibilityCorrect;
    }

    public void checkLanguage() {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String pageLanguage = (String) js.executeScript("return document.documentElement.lang;");
        assertEquals("uk-UA", pageLanguage);
    }

    public void goToLocations() {
        WebElement locationsSection = driver.findElement(By.xpath("//span[@class='museo-sans-light' and contains(text(), 'Our')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.onbeforeunload = null;");
        js.executeScript("window.onbeforeunload = function(e){};");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private By regionAPAC = By.linkText("APAC");
    private By regionEMEA = By.linkText("EMEA");
    private By regionAmericas = By.linkText("AMERICAS");

    public boolean areLocationsDisplayed() {

        for (By locator : List.of(regionAPAC, regionEMEA, regionAmericas)) {
            WebElement element = driver.findElement(locator);
            if (!element.isDisplayed()) {
                return false;
            }
        }
        return true;
    }

    public void switchToLocation(String region) {
        WebElement locationLink = driver.findElement(By.linkText(region));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.onbeforeunload = null;");
        js.executeScript("window.onbeforeunload = function(e){};");
        locationLink.click();
    }

    public boolean isLocationsListDisplayed(String region) {
        WebElement locationsList = driver.findElement(By.linkText(region));
        return locationsList.isDisplayed();
    }

    public void goToSearchField() {
        driver.findElement(By.cssSelector(".search-icon.dark-iconheader-search__search-icon")).click();
        WebElement searchField = driver.findElement(By.id("new_form_search"));
    }

    public void searchData(String searchData) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement searchField = wait.until(
                visibilityOfElementLocated
                        (By.id("new_form_search")));
        searchField.sendKeys(searchData);

        wait.until(visibilityOfElementLocated(By.xpath("//span[@class='bth-text-layer' and contains(text(), 'Find')]"))).click();
    }

    public boolean searchResult() {
        WebElement search = driver.findElement(By.cssSelector(".search-results__counter"));
        boolean isCounterCorrect = search.isDisplayed() && search.getText().contains("AI");
        return isCounterCorrect;
    }

}