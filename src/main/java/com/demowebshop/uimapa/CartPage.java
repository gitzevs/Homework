package com.demowebshop.uimapa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartPage {
    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    private By cart = By.cssSelector("a[href=\"/cart\"]");

    public void openCart() {
        driver.get("https://demowebshop.tricentis.com/cart");
    }

    public int checkCartListNumber() {
        String cartQtyST = (driver.findElement(By.cssSelector(".cart-qty")).getText());
        int extractedInt = extractIntFromText(cartQtyST);
        return extractedInt;
    }

    private static int extractIntFromText(String text) {

        String regex = "\\((\\d+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String intString = matcher.group(1);
            return Integer.parseInt(intString);
        } else {
            return 11;
        }
    }

    public List<String> getListOfCartItems() {
        List<String> cartlistItems = new ArrayList<>();
        openCart();
        (new WebDriverWait(driver, Duration.ofSeconds(2)))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(".page-title")));
        List<WebElement> cartlistItemElements = driver.findElements(By.cssSelector(".cart-item-row .product-name"));

        for (WebElement cartItemElement : cartlistItemElements) {
            cartlistItems.add(cartItemElement.getText());
        }
        return cartlistItems;
    }

    public void removeItemCart(String item) {
        WebElement productRow = driver.findElement(By.xpath("//td[@class='product']//a[@class='product-name' and contains(text(), '" + item + "')]/ancestor::tr//input[@type='checkbox']"));
        productRow.click();
        WebElement updateCart = driver.findElement(By.cssSelector(".button-2.update-cart-button"));
        updateCart.click();
    }

    public void confirmTherms() {
        driver.findElement(By.id("termsofservice")).click();
    }

    public void pressCheckoutBtn() {
        driver.findElement(By.id("checkout")).click();
    }

    public void checkoutProcess() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement dropdownCountry = driver.findElement(By.cssSelector("select#BillingNewAddress_CountryId"));
        if (dropdownCountry.isDisplayed()) {
            Select select = new Select(dropdownCountry);
            select.selectByVisibleText("United States");

            WebElement billingAddressCity = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#BillingNewAddress_City")));
            billingAddressCity.sendKeys("City");

            WebElement billingAddress1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#BillingNewAddress_Address1")));
            billingAddress1.sendKeys("Address1");

            WebElement zipAddress = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#BillingNewAddress_ZipPostalCode")));
            zipAddress.sendKeys("Zip1");

            WebElement phoneAddress = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#BillingNewAddress_PhoneNumber")));
            phoneAddress.sendKeys("+144232323333");
        }
        driver.findElement(By.xpath("//*[@id=\"billing-buttons-container\"]/input")).click();
        WebElement shippingAddress = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"opc-shipping\"]")));
        shippingAddress.isDisplayed();

        WebElement shipingAddressContinue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"shipping-buttons-container\"]/input")));
        shipingAddressContinue.click();

        WebElement shippingMethodContinue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"shipping-method-buttons-container\"]/input")));
        shippingMethodContinue.click();

        WebElement paymentMethodContinue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"payment-method-buttons-container\"]/input")));
        paymentMethodContinue.click();

        WebElement paymentInfoContinue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"payment-info-buttons-container\"]/input")));
        paymentInfoContinue.click();

        WebElement confirmOrderConfirm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"confirm-order-buttons-container\"]/input")));
        confirmOrderConfirm.click();

        String confirmationText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='title' and contains(., 'Your order has been successfully processed!')]/strong"))).getText();
        assertTrue(confirmationText.equals("Your order has been successfully processed!"));
    }
}
