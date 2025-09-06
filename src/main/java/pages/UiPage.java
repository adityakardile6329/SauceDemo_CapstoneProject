package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class UiPage {
    WebDriver driver;
    WebDriverWait wait;

    public By burgerMenuButton = By.id("react-burger-menu-btn");
    public By logoutLink = By.id("logout_sidebar_link");

    public UiPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isBurgerMenuDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(burgerMenuButton)).isDisplayed();
    }

    public void openBurgerMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(burgerMenuButton)).click();
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }
}
