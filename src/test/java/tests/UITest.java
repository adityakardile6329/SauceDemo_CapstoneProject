package tests;

import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.UiPage;
import utils.DriverFactory;
import utils.ScreenShotUtils;
import utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class UITest {

    WebDriver driver;
    LoginPage loginPage;
    UiPage uiPage;
    ExtentReports extent;
    ExtentTest test;

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver("chrome");
        extent = ExtentManager.getExtentReports();

        loginPage = new LoginPage(driver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        uiPage = new UiPage(driver);
    }

    @Test(priority = 1)
    public void testVerifyBurgerMenuPresence() {
        test = extent.createTest("Verify presence of Burger Menu");
        Assert.assertTrue(uiPage.isBurgerMenuDisplayed(), "Burger menu should be visible");
        ScreenShotUtils.takeScreenshot(driver, "BurgerMenuPresence");
        test.pass("Burger menu is visible on the product page");
    }

    @Test(priority = 2)
    public void testLogoutFromBurgerMenu() {
        test = extent.createTest("Logout from Burger Menu");
        uiPage.openBurgerMenu();  
        uiPage.clickLogout();     
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("saucedemo.com"), "User should be redirected to login page");
        ScreenShotUtils.takeScreenshot(driver, "LogoutFromBurgerMenu");
        test.pass("User logged out successfully and redirected to login page");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
        extent.flush();
    }
}