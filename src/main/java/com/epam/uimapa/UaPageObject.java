package com.epam.uimapa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UaPageObject {
    private WebDriver driver;

    public UaPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void checkPageTitle() {

        String pageTitle = driver.getTitle();
        this.driver.getTitle();
        String actualTitle = pageTitle;
        String expectedTitle = new String("EPAM Ukraine - найбільша ІТ-компанія в Україні | Вакансії");
        assertEquals(expectedTitle, actualTitle);
    }
    public boolean verifyElements() {
        WebElement navMenuVacancies = driver.findElement(By.cssSelector("a[href=\"/vacancies\"]"));
        WebElement mainSubsript = driver.findElement(By.cssSelector("[href=\"https://careers.epam.ua/company/contact/news-subscription\"]"));
        WebElement mainLogo = driver.findElement(By.cssSelector(".responsive-image__img.imager-ready"));

        boolean isVacanciesCorrect = navMenuVacancies.isDisplayed() && navMenuVacancies.getText().contains("Вакансії");
        boolean isMainAdvIconCorrect = mainSubsript.isDisplayed() && mainSubsript.getText().equals("ПІДПИСКА НА НОВИНИ");
        boolean isLogoCorrect = mainLogo.isDisplayed() && mainLogo.getAttribute("src").equals("/content/dam/epam/ukraine-assistance-fund/We_Stand_Fund_850.jpg.transform/resize_w_1920/image.jpg");

        return isVacanciesCorrect && isMainAdvIconCorrect && isLogoCorrect;
    }
//        public void pageLoaded() {
//        this.driver.findElements{
//
//        }
//        {
//            String currentUrl = driver.getCurrentUrl();
//            assertTrue(currentUrl.contains(".ua/"), "The site's context has changed to UA.");

//            public void checkExampleElement(String expectedLinkText) {
//            WebElement exampleElement = driver.findElement(By.xpath("//a[@href='/']"));
//            String actualLinkText = exampleElement.getText();
//            assertTrue(actualLinkText.equals(expectedLinkText));
//        }

        }
