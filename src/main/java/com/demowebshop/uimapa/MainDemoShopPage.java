package com.demowebshop.uimapa;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainDemoShopPage {
    private WebDriver driver;

    public MainDemoShopPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://demowebshop.tricentis.com/");
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div[1]/div[1]/div[2]/div[1]/ul/li[1]/a")));
    }

    public void registerUser() {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(3));
        WebElement registerBtn = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div[2]/div[1]/ul/li[1]/a"));
        registerBtn.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/form/div")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#gender-male")));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    public void radioBtn() {
        WebElement radioBtn = driver.findElement(By.xpath("//*[@id=\"gender-male\"]"));
        radioBtn.click();
    }

    public void enterFirstName(String firstName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement firstNameField = driver.findElement(By.xpath("//*[@id=\"FirstName\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(firstNameField));
        firstNameField.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        WebElement lastNameField = driver.findElement(By.xpath("//*[@id=\"LastName\"]"));
        lastNameField.sendKeys(lastName);
    }

    public static String generateRandomEmail() {
        String baseEmail = "John";
        String domain = "@yahoo.com";
        Random random = new Random();
        int randomInt = random.nextInt(1000); // Generates a random number between 0 and 999
        return baseEmail + randomInt + domain;
    }

    public void enterEmail() {
        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"Email\"]"));
        emailField.sendKeys(generateRandomEmail());
    }

    public void enterPassword(String password) {
        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"Password\"]"));
        passwordField.sendKeys(password);
    }

    public void confirmPassword(String password) {
        WebElement confirmPasswordField = driver.findElement(By.xpath("//*[@id=\"ConfirmPassword\"]"));
        confirmPasswordField.sendKeys(password);
    }

    public void clickRegisterButton() {
        WebElement registerButton = driver.findElement(By.id("register-button"));
        registerButton.click();
    }

    public String getRegistrationSuccessMessage() {

        try {
            WebElement successMessage = driver.findElement(By.cssSelector(".result"));
            return successMessage.getText();
        } catch (Exception e) {
            return ("No Element!");
        }
    }

    public boolean loginCheck(String email, String password) {

        driver.findElement(By.cssSelector(".ico-login")).click();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.cssSelector("input.button-1.login-button")).click();
        WebElement customerInfo = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div[2]/div[1]/ul/li[1]/a"));
        String customerData = customerInfo.getText();
        assertEquals(customerData, email);
        return false;
    }

    public void navigateToComputers() {
        WebElement computersLink = driver.findElement(By.cssSelector("a[href=\"/computers\"]"));
        computersLink.click();
    }

    public boolean isSubGroupPresent(String subGroupName) {
        String xpath = new String();
        switch (subGroupName) {
            case "Desktops":
                xpath = ("Desktops");
                break;
            case "Notebooks":
                xpath = ("Notebooks");
                break;
            case "Accessories":
                xpath = ("Accessories");
                break;
        }
        WebElement subGroup = driver.findElement(By.linkText(xpath));
        return subGroup.isDisplayed();
    }

    public void navigateToDesktops() {
        WebElement computersLink = driver.findElement(By.cssSelector("a[href=\"/computers\"]"));
        computersLink.click();
        WebElement desktops = driver.findElement(By.linkText("Desktops"));
        desktops.click();
    }


    public void selectSortByOption(String optionText) {
        WebElement sortByDropdown = driver.findElement(By.id("products-orderby"));
        Select sortBySelect = new Select(sortByDropdown);
        sortBySelect.selectByVisibleText(optionText);
    }

    public String getFirstProductTitle() {
        WebElement firstProductTitle = driver.findElement(By.cssSelector(".product-grid > div.item-box"));
        return firstProductTitle.getText();
    }
    public List<String> getListOfProductNames() {
        List<String> productNames = new ArrayList<>();
        List<WebElement> productElements = driver.findElements(By.cssSelector(".product-item .product-title a"));

        for (WebElement productElement : productElements) {
            productNames.add(productElement.getText());
        }

        return productNames;
    }
    public int getNumberOfItemsInContainer(String containerSelector) {
        WebElement container = driver.findElement(By.cssSelector(containerSelector));
        return container.findElements(By.cssSelector("*")).size();
    }
    public void selectItemsPerPage(String itemsPerPage) {
        WebElement itemsPerPageDropdown = driver.findElement(By.id("products-pagesize"));
        Select itemsPerPageSelect = new Select(itemsPerPageDropdown);
        itemsPerPageSelect.selectByVisibleText(itemsPerPage);
    }

    private static int extractIntFromText(String text) {

        String regex = "\\((\\d+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);


        if (matcher.find()) {
            String intString = matcher.group(1);
            return Integer.parseInt(intString);
        } else {
            return 1;
        }
    }
    public void goToWishItem(){
        driver.get("https://demowebshop.tricentis.com/blue-and-green-sneaker");
    }

    public void addToWishlist() {
        goToWishItem();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button-2.add-to-wishlist-button")));
        WebElement addToWishList = driver.findElement(By.cssSelector(".button-2.add-to-wishlist-button"));
        addToWishList.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"bar-notification\"]/p/a")));
    }

    public void selectDesktop(String name){
        driver.findElement(By.linkText(name)).click();
    }
    public void addToCartItem(String name){
        selectDesktop(name);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.cssSelector(".page.product-details-page")));
        driver.findElement(By.cssSelector(".button-1.add-to-cart-button")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}