package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
//cart pageeeeeee
public class CartPage {

    WebDriver driver;
    WebDriverWait wait;

    public By cartButton = By.cssSelector(".shopping_cart_link");
    public By removeButton = By.id("remove-sauce-labs-backpack");
    public By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
    }

    public void removeProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(removeButton));
    }

    public boolean isProductPresent() {
        return driver.findElements(removeButton).size() > 0;
    }

    public void continueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton)).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    public boolean isOnProductPage() {
        return driver.getCurrentUrl().contains("inventory.html");
    }
}
