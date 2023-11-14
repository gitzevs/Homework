package com.epam.uimapa;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

import static java.time.Duration.ofSeconds;

public class AboutPage {

    private WebDriver driver;

    public AboutPage(WebDriver driver) {
        this.driver = driver;
    }

    private void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void open() {
        driver.get("https://www.epam.com/about");
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(5));
        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#onetrust-accept-btn-handler")));
        if (submitBtn.isEnabled()) {
            submitBtn.click();
        }
    }

    public void clickCompanyLogo() {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(5));
        WebElement logoElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.header__logo-container.desktop-logo")));
        logoElement.click();
    }

    public void clickDownloadLink() {
        WebElement downloadLink = driver.findElement(By.linkText("DOWNLOAD"));
        scrollToElement(driver, downloadLink);
        downloadLink.click();
    }

    public boolean isFileDownloaded(String downloadPath, String expectedFileName) {
        String name = new String();
        name.startsWith(expectedFileName);
        File downloadedFile = new File(downloadPath + File.separator + name);
        return downloadedFile.exists();
    }

    public void deleteDownloadedFile(String downloadPath, String expectedFileName) {
        String name = new String();
        name.startsWith(expectedFileName);
        File downloadedFile = new File(downloadPath + File.separator + name);
        if (downloadedFile.exists()) {
            downloadedFile.delete();
        } else System.out.println("File not Deleted!");
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }


}
