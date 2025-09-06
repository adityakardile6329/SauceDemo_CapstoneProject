package tests;

import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import pages.ProductPage;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import utils.ScreenShotUtils;
import utils.DriverFactory;
import utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class NegativeTest {

    WebDriver driver;
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;
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
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    public void testAddThenRemoveProduct() {
        test = extent.createTest("Add Product, Remove It, Verify Cart Empty");
        productPage.addProductToCart();
        int count = productPage.getCartCount();
        Assert.assertEquals(count, 1);
        test.pass("Product added to cart successfully");
        cartPage.openCart();
        cartPage.removeProduct();
        test.pass("Product removed from cart");
        Assert.assertFalse(cartPage.isProductPresent());
        ScreenShotUtils.takeScreenshot(driver, "CartEmptyAfterRemove");
        test.pass("Cart is empty after removing product");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
        extent.flush();
    }
}
