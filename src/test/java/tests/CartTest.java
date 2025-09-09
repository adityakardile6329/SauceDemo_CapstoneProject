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

//cart testtt for saucedemo
public class CartTest {

    WebDriver driver;
    LoginPage loginPage;
    LoginPage loginPage1;
    ProductPage productPage;
    CartPage cartPage;
    ExtentReports extent;
    ExtentTest test;

    @BeforeMethod
    public void setup() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        extent = ExtentManager.getExtentReports();

        loginPage = new LoginPage(driver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        productPage = new ProductPage(driver);
        productPage.addProductToCart();
        Thread.sleep(3000);

        cartPage = new CartPage(driver);
        cartPage.openCart();
        Thread.sleep(3000);
    }

    @Test
    public void testRemoveProductFromCart() throws InterruptedException {
        test = extent.createTest("Remove Product from Cart");
        cartPage.removeProduct();
        Thread.sleep(3000);
        Assert.assertFalse(cartPage.isProductPresent());
        ScreenShotUtils.takeScreenshot(driver, "RemoveProductFromCart");
        test.pass("Product removed successfully");
    }

    @Test
    public void testContinueShopping() throws InterruptedException {
        test = extent.createTest("Continue Shopping from Cart");
        cartPage.continueShopping();
        Thread.sleep(3000);
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