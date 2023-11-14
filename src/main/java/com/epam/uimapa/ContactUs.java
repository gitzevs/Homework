package com.epam.uimapa;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static java.time.Duration.ofSeconds;

public class ContactUs {

    private WebDriver driver;

    public ContactUs(WebDriver driver) {
        this.driver = driver;
    }

    private void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void open() {
        driver.get("https://www.epam.com/about/who-we-are/contact");
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(5));
        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#onetrust-accept-btn-handler")));
        if (submitBtn.isEnabled()) {
            submitBtn.click();
        }
//        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#onetrust-accept-btn-handler"))).click();
    }

    public void fillName(String name) {
        WebElement nameField = driver.findElement(By.name("Name"));
        nameField.sendKeys(name);
    }

    public void fillEmail(String email) {
        WebElement emailField = driver.findElement(By.name("Email"));
        emailField.sendKeys(email);
    }

    public void fillMessage(String message) {
        WebElement messageField = driver.findElement(By.name("Message"));
        messageField.sendKeys(message);
    }

    public void submitForm() {

        WebElement submitBtnLoc = driver.findElement(By.cssSelector(".checkbox__label.checkbox-custom-label.checkbox__label-text"));
//            ".validation_hint"
        scrollToElement(driver, submitBtnLoc);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebElement submitBtn = driver.findElement(By.cssSelector("#_content_epam_en_about_who-we-are_contact_jcr_content_content-container_section_section-par_form_constructor > div.end > div > div.button-ui-wrapper.button-submit > button"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", submitBtn);

    }


    public boolean isNameFieldValidated() {
        WebElement nameField = driver.findElement(By.id("_content_epam_en_about_who-we-are_contact_jcr_content_content-container_section_section-par_form_constructor_user_first_name"));
        WebElement nameValidationMessage = nameField.findElement(By.xpath("//*[@id='_content_epam_en_about_who-we-are_contact_jcr_content_content-container_section_section-par_form_constructor_user_first_name-error']/span"));
        return nameValidationMessage.isDisplayed();
    }

    public boolean isEmailFieldValidated() {
        WebElement emailField = driver.findElement(By.xpath("//*[@id='_content_epam_en_about_who-we-are_contact_jcr_content_content-container_section_section-par_form_constructor_user_email']"));
        WebElement emailValidationMessage = emailField.findElement(By.xpath("//*[@id='_content_epam_en_about_who-we-are_contact_jcr_content_content-container_section_section-par_form_constructor_user_email-error']/span"));
        return emailValidationMessage.isDisplayed();
    }

    public boolean isPhoneFieldValidated() {

        WebElement phoneFieldLoc = driver.findElement(By.xpath("//*[@id='select2-_content_epam_en_about_who-we-are_contact_jcr_content_content-container_section_section-par_form_constructor_user_city-container']"));
        scrollToElement(driver, phoneFieldLoc);

        WebElement phoneField = driver.findElement(By.xpath("//*[@id='_content_epam_en_about_who-we-are_contact_jcr_content_content-container_section_section-par_form_constructor_user_phone']"));
        Actions action = new Actions(driver);
//        action.moveToElement(phoneField).perform();

        WebElement phoneValidationMessage = phoneField.findElement(By.xpath("//*[@id='_content_epam_en_about_who-we-are_contact_jcr_content_content-container_section_section-par_form_constructor_user_phone-error']/span"));
        return phoneValidationMessage.isDisplayed();
    }
}

