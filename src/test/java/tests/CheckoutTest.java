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

        loginPage = new LoginPage(driver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        productPage = new ProductPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }
    
    @Test(priority = 1)
    public void testCheckoutWithValidInformation() throws InterruptedException {
        test = extent.createTest("Checkout with Valid Information");
        productPage.addProductToCart();
        Thread.sleep(3000);
        int count = productPage.getCartCount();
        Assert.assertEquals(count, 1);
        checkoutPage.openCart();
        Thread.sleep(2000);
        checkoutPage.clickCheckout();
        Thread.sleep(2000);
        checkoutPage.enterCustomerInfo("Aditya", "Kardile", "411001");
        Thread.sleep(2000);
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();
        Thread.sleep(2000);
        String confirmation = checkoutPage.getConfirmationMessage();
        Assert.assertEquals(confirmation, "Thank you for your order!");
        ScreenShotUtils.takeScreenshot(driver, "CheckoutValidInfo");
        test.pass("Order completed and confirmation verified");
    }


    @Test(priority = 2)
    public void testCompleteOrder() throws InterruptedException {
        test = extent.createTest("Complete Order and Verify Confirmation");
        productPage.addProductToCart();
        Thread.sleep(2000);
        int count = productPage.getCartCount();
        Assert.assertEquals(count, 1);
        checkoutPage.openCart();
        Thread.sleep(2000);
        checkoutPage.clickCheckout();
        Thread.sleep(2000);
        checkoutPage.enterCustomerInfo("John", "Carry", "12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();
        Thread.sleep(2000);
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