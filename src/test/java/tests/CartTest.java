package tests;

import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.ProductPage;
import pages.CartPage;
import utils.DriverFactory;
import utils.ScreenShotUtils;
import utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class CartTest {

    WebDriver driver;
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;
    ExtentReports extent;
    ExtentTest test;

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver("chrome");
        extent = ExtentManager.getExtentReports();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        productPage = new ProductPage(driver);
        productPage.addProductToCart();

        cartPage = new CartPage(driver);
        cartPage.openCart();
    }

    @Test
    public void testRemoveProductFromCart() {
        test = extent.createTest("Remove Product from Cart");
        cartPage.removeProduct();
        Assert.assertFalse(cartPage.isProductPresent());
        ScreenShotUtils.takeScreenshot(driver, "RemoveProductFromCart");
        test.pass("Product removed successfully");
    }

    @Test
    public void testContinueShopping() {
        test = extent.createTest("Continue Shopping from Cart");
        cartPage.continueShopping();
        Assert.assertTrue(cartPage.isOnProductPage());
        ScreenShotUtils.takeScreenshot(driver, "ContinueShopping");
        test.pass("Navigated to products page successfully");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
        extent.flush();
    }
}
