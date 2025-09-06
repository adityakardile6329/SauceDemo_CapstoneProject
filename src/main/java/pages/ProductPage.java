package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {

	WebDriver driver;

	public By cartBadge = By.cssSelector(".shopping_cart_badge");

	public ProductPage(WebDriver driver) {
		this.driver = driver;
	}

	public void addProductToCart() {
		By addButton = By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']");
		driver.findElement(addButton).click();
	}

	public void clickProduct() throws InterruptedException {
		By productLink = By.xpath("//a[@id='item_4_title_link']");
		driver.findElement(productLink).click();
		Thread.sleep(2000);
	}

	public int getCartCount() {
		try {
			String count = driver.findElement(cartBadge).getText();
			return Integer.parseInt(count);
		} catch (Exception e) {
			return 0;
		}
	}
}