package tests;

import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import pages.ProductPage;
import pages.LoginPage;
import pages.CheckoutPage;
import utils.ScreenShotUtils;
import utils.DriverFactory;
import utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class CheckoutTest {

    WebDriver driver;
    LoginPage loginPage;
    ProductPage productPage;
    CheckoutPage checkoutPage;
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
        checkoutPage = new CheckoutPage(driver);
    }
    
    @Test(priority = 1)
    public void testCheckoutWithValidInformation() {
        test = extent.createTest("Checkout with Valid Information");
        productPage.addProductToCart();
        int count = productPage.getCartCount();
        Assert.assertEquals(count, 1);
        test.pass("Product added to cart successfully");
        checkoutPage.openCart();
        checkoutPage.clickCheckout();
        test.pass("Navigated to checkout page");
        checkoutPage.enterCustomerInfo("Aditya", "Kardile", "411001");
        checkoutPage.clickContinue();
        test.pass("Entered valid customer information");
        checkoutPage.clickFinish();
        String confirmation = checkoutPage.getConfirmationMessage();
        Assert.assertEquals(confirmation, "Thank you for your order!");
        ScreenShotUtils.takeScreenshot(driver, "CheckoutValidInfo");
        test.pass("Order completed and confirmation verified");
    }


    @Test(priority = 2)
    public void testCompleteOrder() {
        test = extent.createTest("Complete Order and Verify Confirmation");
        productPage.addProductToCart();
        int count = productPage.getCartCount();
        Assert.assertEquals(count, 1);
        test.pass("Product added to cart successfully");
        checkoutPage.openCart();
        checkoutPage.clickCheckout();
        test.pass("Navigated to checkout page");
        checkoutPage.enterCustomerInfo("John", "Carry", "12345");
        checkoutPage.clickContinue();
        test.pass("Entered customer info and continued");
        checkoutPage.clickFinish();
        String confirmation = checkoutPage.getConfirmationMessage();
        Assert.assertEquals(confirmation, "Thank you for your order!");
        ScreenShotUtils.takeScreenshot(driver, "OrderConfirmation");
        test.pass("Order completed and confirmation verified");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
        extent.flush();
    }
}
