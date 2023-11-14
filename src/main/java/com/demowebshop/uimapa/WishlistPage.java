package com.demowebshop.uimapa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.Duration.ofSeconds;

public class WishlistPage {

    private WebDriver driver;

    public WishlistPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://demowebshop.tricentis.com/wishlist");
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Wishlist")));
    }

    public void openWishList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement wishlist = driver.findElement(By.linkText("Wishlist"));
        wait.until(ExpectedConditions.elementToBeClickable(wishlist));
        wishlist.click();
    }

    private static int extractIntFromText(String text) {

        String regex = "\\((\\d+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String intString = matcher.group(1);
            return Integer.parseInt(intString);
        } else {
            return 13;
        }
    }

    public int checkWishListNumber() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement wishlist = driver.findElement(By.cssSelector("a[href=\"/wishlist\"]"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href=\"/wishlist\"]")));
        String wishlistQtyST = (driver.findElement(By.cssSelector(".wishlist-qty")).getText());
        return extractIntFromText(wishlistQtyST);
    }

        public List<String> getListOfWishlistItems () {
            List<String> wishlistItems = new ArrayList<>();
            openWishList();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".wishlist-content .product a")));
            List<WebElement> wishlistItemElements = driver.findElements(By.cssSelector(".wishlist-content .product a"));

            for (WebElement wishlistItemElement : wishlistItemElements) {
                wishlistItems.add(wishlistItemElement.getText());
            }
            return wishlistItems;
        }
}

