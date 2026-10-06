package pages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import manager.FileReaderManager;
import objectRepo.MenuObjRepo;
import utils.Common;

public final class Menus extends MenuObjRepo {

	

	public Menus(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	public void clickUsingJavaScript(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}
	
  public void clickHome() {
	
	click(homeMenu);
	String banner = banners.getTagName();
    Assert.assertTrue("Banner is Visible", banner.length() <= 50);
    

}
  
	

 public void clickNewArrival() {
	 
	 click(newArrivalMenu);
		String heading = newArrivalheading.getText();
		if (heading.contains(heading)) {
			System.out.println("The heading is displayed: "+ heading);
			
		}
		else {
			System.out.println("The Menu is not redirecting");
		}
	    Assert.assertTrue("Navigated to New Arrivals Page", heading.equals(heading));
	    
}
 
 public void newArrivalSuggestion() {
	 
	 Actions actions = new Actions(driver);
	 actions.moveToElement(newArrivalMenu).perform();

	 // Get all dropdown products
	 List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//span[@class='na_dropdown_card_name']"));
	 Collections.shuffle(clickRandomProduct);

	 if (!clickRandomProduct.isEmpty()) {
	     WebElement randomProduct = clickRandomProduct.get(0);
	     actions.moveToElement(randomProduct).click().perform();

	     // Get product heading
	     String heading = newArrivalSuggestionRedirection.getText();

	     // Assert that heading is not empty
	     Assert.assertFalse("❌ Product heading is empty, navigation may have failed!", heading.isEmpty());

	     // Optional: Check that URL contains 'product'
	     String currentUrl = driver.getCurrentUrl();
	     Assert.assertTrue("❌ Did not navigate to product details page!", currentUrl.contains("product"));

	     System.out.println("✅ Navigated to product details page. Heading: " + heading);
	 } else {
	     System.out.println("⚠️ No products found in New Arrivals dropdown.");
	 }

 }
public void saleMenu() {
	click(saleMenu);
	String heading = saleMenuHead.getText();
    Assert.assertTrue("Navigated to sale Page", heading.length() <= 50);

}

	public void bossLady() {
		Actions actions = new Actions(driver);
		actions.moveToElement(bossLadyMenu).build().perform();
	    List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//span[@class='bl_dropdown_card_name']"));
		Collections.shuffle(clickRandomProduct);

		if (!clickRandomProduct.isEmpty()) {
			WebElement randomProduct = clickRandomProduct.get(0);
			actions.moveToElement(randomProduct).click().build().perform();
			String heading = bossLadyPage.getText();
		    Assert.assertTrue("Navigated to boss lady Page", heading.length() <= 50);
		
	
		if (heading.contains(heading)) {
			System.out.println("The heading is displayed: "+ heading);
			
		}
		else {
			System.out.println("The Menu is not redirecting");
		}
		}
	}
	
	public void shopCategory() {
		Common.waitForElement(5);
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();
		String heading = shopPageHead.getText();
	    Assert.assertTrue("Navigated to Category Page", heading.length() <= 50);
	    if (heading.contains(heading)) {
			System.out.println("The heading is displayed: "+ heading);
			
		}
		else {
			System.out.println("The Menu is not redirecting");
	
		}	

	}
	
	public void shopCollection() {
		Common.waitForElement(5);
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();
		String heading = shopPageHead.getText();
	    Assert.assertTrue("Navigated to Collection Page", heading.length() <= 50);
	    if (heading.contains(heading)) {
			System.out.println("The heading is displayed: "+ heading);
			
		}
		else {
			System.out.println("The Menu is not redirecting");
	
		}	

	}
	
	public void shopStyles() {
		Common.waitForElement(5);
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();
		String heading = shopPageHead.getText();
	    Assert.assertTrue("Navigated to Category Page", heading.length() <= 50);
	    if (heading.contains(heading)) {
			System.out.println("The heading is displayed: "+ heading);
			
		}
		else {
			System.out.println("The Menu is not redirecting");
	
		}

	}
	
	public void PopShop() {
		Common.waitForElement(5);
		click(popShop);
	}
	
	public void getUpdates() 
	{
		Common.waitForElement(1);
		click(getUpdateMenu);
		

	}
	
	public void appLaunch() {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
	}
	
	
	
	
	public void verifyHomeMenu() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    String GREEN  = "\u001B[32m";
	    String RESET  = "\u001B[0m";
	    String CYAN   = "\u001B[36m";
	    String line = "──────────────────────────────────────────────────────────────";
	    System.out.println(CYAN + line + RESET);
	    System.out.println(GREEN + "🚀 Starting Home Header Menu..." + RESET);
	    System.out.println(CYAN + line + RESET);
	    WebElement homeMenu1 = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//a[normalize-space()='Home']") 
	    ));
	    homeMenu1.click();
	    Common.waitForElement(2);
	    String beforeClickUrl = driver.getCurrentUrl();
	    System.out.println("🔍 URL before clicking Home: " + beforeClickUrl);

	    WebElement homeMenu = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//a[normalize-space()='Home']") 
	    ));

	    assertTrue("❌ Home menu is not clickable", homeMenu.isEnabled());
	    homeMenu.click();
	    System.out.println(GREEN + "✅ Home menu clicked" + RESET);

	    wait.until(ExpectedConditions.urlToBe(beforeClickUrl));

	    String afterClickUrl = driver.getCurrentUrl();
	    System.out.println("🔍 URL after clicking Home : " + afterClickUrl);

	    assertEquals(
	            "❌ URL changed after clicking Home!",
	            beforeClickUrl,
	            afterClickUrl
	    );

	    System.out.println(
	            GREEN + "✅ URL unchanged | Before: " + beforeClickUrl +
	            " | After: " + afterClickUrl + RESET
	    );

	    WebElement homeBanner = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(@class,'home_banner_container')]")
	    ));

	    assertTrue(
	            "❌ Home page banner is not displayed",
	            homeBanner.isDisplayed()
	    );

	    System.out.println(GREEN + "✅ Home page banner is displayed" + RESET);
	}
	
	public void verifynewArrivalMenu() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    String RESET  = "\u001B[0m";
	    String GREEN  = "\u001B[32m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String PURPLE = "\u001B[35m";
	    String[] expectedUrlParts = {"/new-arrivals", "/new-launch"};

	    System.out.println(CYAN + "🔍 Verifying New Arrival menu..." + RESET);

	    WebElement newArrivalMenu = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[@class='header_nav_item new_in']")
	    ));

	    System.out.println(GREEN + "✅ New Arrival menu clickable" + RESET);

	    newArrivalMenu.click();

	    wait.until(driver -> 
	            driver.getCurrentUrl().contains(expectedUrlParts[0]) ||
	            driver.getCurrentUrl().contains(expectedUrlParts[1])
	    );

	    System.out.println(GREEN + "✅ New Arrival URL verified: " + driver.getCurrentUrl() + RESET);

	    String actualUrl = driver.getCurrentUrl();
	    System.out.println(CYAN + "URL: " + actualUrl + RESET);

	    WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//a[translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='new in']")
	    ));

	    System.out.println(GREEN + "✅ Heading displayed: " + heading.getText() + RESET);

	    // ✅ STALE SAFE PRODUCT FETCH
	    List<WebElement> products = null;

	    for (int i = 0; i < 3; i++) {
	        try {
	            products = driver.findElements(By.xpath("//div[@class='prod_listing_card']"));

	            if (products.size() > 0) {
	                wait.until(ExpectedConditions.visibilityOf(products.get(0)));
	                break;
	            }

	        } catch (StaleElementReferenceException e) {
	            System.out.println("Retrying products...");
	        }
	    }

	    if (products == null || products.size() < 2) {
	        throw new RuntimeException("❌ Less products in New Arrival");
	    }

	    System.out.println(PURPLE + "📦 Products: " + products.size() + RESET);
	}
	
//	public void verifynewArrivalMenu() throws InterruptedException {
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//	    Common.waitForElement(2);
//
//	    String RESET  = "\u001B[0m";
//	    String GREEN  = "\u001B[32m";
//	    String CYAN   = "\u001B[36m";
//	    String YELLOW = "\u001B[33m";
//	    String PURPLE = "\u001B[35m";
//	    String line = "──────────────────────────────────────────────────────────────";
//
//	    System.out.println(CYAN + line + RESET);
//	    System.out.println(GREEN + "🚀 Starting New Arrival Header Menu Verification..." + RESET);
//	    System.out.println(CYAN + line + RESET);
//
//	    String expectedUrlPart = "/new-arrivals";
//
//	    System.out.println(CYAN + "🔍 Verifying New Arrival menu..." + RESET);
//
//	    WebElement newArrivalMenu = wait.until(ExpectedConditions.elementToBeClickable(
//	            By.xpath("//a[contains(@class,'header_nav_link') and contains(@href,'new-arrivals')]")//a[normalize-space()='NEW IN']
//	    ));
//
//	    assertTrue("❌ New Arrival menu is not clickable", newArrivalMenu.isEnabled());
//	    System.out.println(GREEN + "✅ New Arrival menu is clickable" + RESET);
//
//	    System.out.println(YELLOW + "👉 Clicking New Arrival menu" + RESET);
//	    newArrivalMenu.click();
//
//	    wait.until(ExpectedConditions.urlContains(expectedUrlPart));
//	    String actualUrl = driver.getCurrentUrl();
//
//	    assertTrue(
//	            "❌ URL does not contain expected part! Expected: " + expectedUrlPart + " | Actual: " + actualUrl,
//	            actualUrl.contains(expectedUrlPart)
//	    );
//
//	    System.out.println(
//	            GREEN + "✅ URL verified" + RESET +
//	            CYAN  + " | Actual URL: " + actualUrl + RESET
//	    );
//
//	    System.out.println(CYAN + "🔍 Verifying New Arrival heading" + RESET);
//
//	    WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	            By.xpath("//h2[normalize-space()='NEW ARRIVALS']")
//	    ));
//
//	    assertTrue("❌ New Arrival heading not displayed", heading.isDisplayed());
//	    System.out.println(GREEN + "✅ New Arrival heading is displayed" + RESET);
//
//	    System.out.println(CYAN + "🔍 Verifying product list" + RESET);
//
//	    List<WebElement> products = wait.until(ExpectedConditions
//	            .visibilityOfAllElementsLocatedBy(
//	                    By.xpath("//div[@class='prod_listing_card']")
//	            ));
//
//	    assertTrue(
//	            "❌ Less than 2 products displayed! Found: " + products.size(),
//	            products.size() >= 2
//	    );
//
//	    System.out.println(
//	            PURPLE + "📦 Products displayed count: " + products.size() + RESET
//	    );

//	    System.out.println(CYAN + "🔍 Verifying New Arrival hover dropdown" + RESET);
	
//	    Actions actions = new Actions(driver);
//
//	    WebElement newArrivalMenuHover = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//a[normalize-space()='New Arrivals']")
//	            )
//	    );
//
//	    // Hover safely
//	    actions.moveToElement(newArrivalMenuHover)
//	           .pause(Duration.ofMillis(700))
//	           .perform();
//
//	    // Dropdown container
//	    WebElement dropdown = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//div[contains(@class,'new_arrival_dropdown')]")
//	            )
//	    );
//
//	    assertTrue(":x: New Arrival dropdown not visible on hover", dropdown.isDisplayed());
//	    System.out.println(GREEN + ":white_check_mark: New Arrival dropdown displayed on hover" + RESET);
//
//	    // Dropdown products
//	    List<WebElement> dropdownProducts = wait.until(
//	            ExpectedConditions.numberOfElementsToBeMoreThan(
//	                    By.xpath("//div[contains(@class,'new_arrival_dropdown')]//a[contains(@class,'na_dropdown_card')]"),
//	                    2
//	            )
//	    );
//
//	    System.out.println(
//	            PURPLE + ":receipt: Dropdown products count: " + dropdownProducts.size() + RESET
//	    );
//
//	    // Print product names
//	    for (WebElement product : dropdownProducts) {
//	        String productName = product.findElement(
//	                By.xpath(".//span[@class='na_dropdown_card_name']")
//	        ).getText().trim();
//
//	        System.out.println(YELLOW + "➡ " + productName + RESET);
//	    }
	
	
//	public void verifySaleMenu() {
//
//	    Common.waitForElement(2);
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//	    String RESET  = "\u001B[0m";
//	    String GREEN  = "\u001B[32m";
//	    String CYAN   = "\u001B[36m";
//	    String YELLOW = "\u001B[33m";
//	    String PURPLE = "\u001B[35m";
//	    String line = "──────────────────────────────────────────────────────────────";
//
//	    System.out.println(CYAN + line + RESET);
//	    System.out.println(GREEN + "🚀 Starting Sale Header Menu Verification..." + RESET);
//	    System.out.println(CYAN + line + RESET);
//
//	    String expectedUrlPart = "/sale";
//
//	    System.out.println(CYAN + "🔍 Verifying Sale menu..." + RESET);
//
//	    WebElement saleMenu = wait.until(ExpectedConditions.elementToBeClickable(
//	            By.xpath("//a[normalize-space()='SALE']")
//	    ));
//
//	    assertTrue("❌ Sale menu is not clickable", saleMenu.isEnabled());
//	    System.out.println(GREEN + "✅ Sale menu is clickable" + RESET);
//
//	    System.out.println(YELLOW + "👉 Clicking Sale menu" + RESET);
//	    saleMenu.click();
//
//	    wait.until(ExpectedConditions.urlContains(expectedUrlPart));
//	    String actualUrl = driver.getCurrentUrl();
//
//	    assertTrue(
//	            "❌ URL does not contain expected part! Expected: " + expectedUrlPart + " | Actual: " + actualUrl,
//	            actualUrl.contains(expectedUrlPart)
//	    );
//
//	    System.out.println(
//	            GREEN + "✅ URL verified" + RESET +
//	            CYAN + " | Actual URL: " + actualUrl + RESET
//	    );
//
//	    System.out.println(CYAN + "🔍 Verifying Sale products..." + RESET);
//
//	    List<WebElement> products = wait.until(
//	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
//	                    By.xpath("//div[@class='prod_listing_card']")
//	            )
//	    );
//
//	    assertTrue(
//	            "❌ Less than 2 Sale products displayed! Found: " + products.size(),
//	            products.size() >= 2
//	    );
//
//	    System.out.println(
//	            PURPLE + "📦 Sale products displayed count: " + products.size() + RESET
//	    );
//	}
	
	public void verifySaleMenu() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    String RESET  = "\u001B[0m";
	    String GREEN  = "\u001B[32m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String PURPLE = "\u001B[35m";
	    String RED    = "\u001B[31m";

	    String expectedUrlPart = "/sale";

	    System.out.println(CYAN + "🔍 Verifying Sale menu..." + RESET);

	    WebElement saleMenu = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//a[normalize-space()='SALE']")
	    ));

	    saleMenu.click();

	    wait.until(ExpectedConditions.urlContains(expectedUrlPart));

	    String actualUrl = driver.getCurrentUrl();
	    System.out.println(CYAN + "URL: " + actualUrl + RESET);

	    // ✅ STALE SAFE PRODUCT FETCH
	    List<WebElement> products = null;

	    for (int i = 0; i < 3; i++) {
	        try {
	            products = driver.findElements(By.xpath("//div[@class='prod_listing_card']"));

	            if (products.size() > 0) {
	                wait.until(ExpectedConditions.visibilityOf(products.get(0)));
	                break;
	            }

	        } catch (StaleElementReferenceException e) {
	            System.out.println(RED + "Retrying Sale products..." + RESET);
	        }
	    }

	    if (products == null || products.size() < 2) {
	        throw new RuntimeException("❌ Less Sale products");
	    }

	    System.out.println(PURPLE + "📦 Sale Products: " + products.size() + RESET);
	}
	
	public void verifyShopHeaderMenu() throws InterruptedException {

	    Common.waitForElement(2);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);

	    // :art: Console Colors
	    String RESET  = "\u001B[0m";
	    String GREEN  = "\u001B[32m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RED    = "\u001B[31m";

	    String line = "──────────────────────────────────────────────────────────────";
	    System.out.println(CYAN + line + RESET);
	    System.out.println(GREEN + ":rocket: Starting Shop Header Menu Validation" + RESET);
	    System.out.println(CYAN + line + RESET);

//
//	    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	            By.xpath("//div[@class='header_nav_item has_dropdown']")
//	    ));
	    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(@class,'header_nav_item') and contains(@class,'has_dropdown')]")
	    ));
	    System.out.println(CYAN + ":mag: Shop menu located" + RESET);

	    String beforeClickUrl = driver.getCurrentUrl();
	    shopMenu.click();
	    Common.waitForElement(1);
	    String afterClickUrl = driver.getCurrentUrl();

	    if (!beforeClickUrl.equals(afterClickUrl)) {
	        System.out.println(RED + ":x: Shop menu changed URL on click (Should NOT)" + RESET);
	        fail("Shop menu should not be clickable");
	    }

	    System.out.println(GREEN + ":white_check_mark: Shop menu is NOT clickable (URL unchanged)" + RESET);

	    System.out.println(YELLOW + ":point_right: Hovering over Shop menu" + RESET);
	    actions.moveToElement(shopMenu).perform();

	    Thread.sleep(2000);
	    WebElement categories = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//h5[normalize-space()='CATEGORIES']")
	    ));

	    WebElement collection = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//h5[normalize-space()='COLLECTIONS']")
	    ));
//
//	    WebElement styles = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	            By.xpath("//h5[@class='nav_drop_down_category_heading' and normalize-space()='Styles']")
//	    ));

	    assertTrue(":x: Categories not displayed", categories.isDisplayed());
	    assertTrue(":x: Collection not displayed", collection.isDisplayed());
//	    assertTrue(":x: Styles not displayed", styles.isDisplayed());

	    System.out.println(
	            GREEN + " Shop hover menu displayed: Categories | Collection " + RESET
	    );
	}
		
//	public void verifyBossLadyMenu() throws InterruptedException {
//		
//		Common.waitForElement(3);
//		
//		click(bossladyBrandButton);
//		
//		
//		
//        verifynewArrivalMenu();
//		
//		verifySaleMenu();
//			
//		    Common.waitForElement(2);
//		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//		    Actions actions = new Actions(driver);
//
//		    // :art: Console Colors
//		    String RESET  = "\u001B[0m";
//		    String GREEN  = "\u001B[32m";
//		    String CYAN   = "\u001B[36m";
//		    String YELLOW = "\u001B[33m";
//		    String RED    = "\u001B[31m";
//
//		    String line = "──────────────────────────────────────────────────────────────";
//		    System.out.println(CYAN + line + RESET);
//		    System.out.println(GREEN + ":rocket: Starting Shop Header Menu Validation" + RESET);
//		    System.out.println(CYAN + line + RESET);
//
//
//		    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
//		            By.xpath("//div[@class='header_nav_item has_dropdown']")
//		    ));
//
//		    System.out.println(CYAN + ":mag: Shop menu located" + RESET);
//
//		    String beforeClickUrl = driver.getCurrentUrl();
//		    shopMenu.click();
//		    Common.waitForElement(1);
//		    String afterClickUrl = driver.getCurrentUrl();
//
//		    if (!beforeClickUrl.equals(afterClickUrl)) {
//		        System.out.println(RED + ":x: Shop menu changed URL on click (Should NOT)" + RESET);
//		        fail("Shop menu should not be clickable");
//		    }
//
//		    System.out.println(GREEN + ":white_check_mark: Shop menu is NOT clickable (URL unchanged)" + RESET);
//
//		    System.out.println(YELLOW + ":point_right: Hovering over Shop menu" + RESET);
//		    actions.moveToElement(shopMenu).perform();
//
//		    Thread.sleep(2000);
//		    WebElement categories = wait.until(ExpectedConditions.visibilityOfElementLocated(
//		            By.xpath("//h5[normalize-space()='CATEGORIES']")
//		    ));
//
//		    WebElement collection = wait.until(ExpectedConditions.visibilityOfElementLocated(
//		            By.xpath("//h5[normalize-space()='COLLECTIONS']")
//		    ));
//	//
////		    WebElement styles = wait.until(ExpectedConditions.visibilityOfElementLocated(
////		            By.xpath("//h5[@class='nav_drop_down_category_heading' and normalize-space()='Styles']")
////		    ));
//
//		    assertTrue(":x: Categories not displayed", categories.isDisplayed());
//		    assertTrue(":x: Collection not displayed", collection.isDisplayed());
////		    assertTrue(":x: Styles not displayed", styles.isDisplayed());
//
//		    System.out.println(
//		            GREEN + " Shop hover menu displayed: Categories | Collection " + RESET
//		    );
//		    
//		    verifyBlogs();
//		}
		
	public void verifyBossLadyMenu() throws InterruptedException {

	    Common.waitForElement(3);

	    click(bossladyBrandButton);

	    // ✅ Run other flows
	    verifynewArrivalMenu();
	    verifySaleMenu();

	    Common.waitForElement(2);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);

	    String RESET  = "\u001B[0m";
	    String GREEN  = "\u001B[32m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RED    = "\u001B[31m";

	    String line = "──────────────────────────────────────────────────────────────";

	    System.out.println(CYAN + line + RESET);
	    System.out.println(GREEN + "🚀 Starting Shop Header Menu Validation" + RESET);
	    System.out.println(CYAN + line + RESET);

	    // ✅ ALWAYS re-locate element (fix stale)
	    By shopMenuBy = By.xpath("//div[@class='header_nav_item has_dropdown shop']");
	    By categoriesBy = By.xpath("//h5[normalize-space()='CATEGORIES']");
	    By collectionBy = By.xpath("//h5[normalize-space()='COLLECTIONS']");

	    // 🔁 Re-fetch SHOP menu
	    WebElement shopMenu = wait.until(ExpectedConditions.presenceOfElementLocated(shopMenuBy));
	    shopMenu = wait.until(ExpectedConditions.visibilityOf(shopMenu));

	    System.out.println(CYAN + "🔍 Shop menu located" + RESET);

	    String beforeClickUrl = driver.getCurrentUrl();

	    try {
	        shopMenu.click();
	    } catch (Exception e) {
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", shopMenu);
	    }

	    Common.waitForElement(1);

	    String afterClickUrl = driver.getCurrentUrl();

	    if (!beforeClickUrl.equals(afterClickUrl)) {
	        System.out.println(RED + "❌ Shop menu changed URL (Should NOT)" + RESET);
	        fail("Shop menu should not be clickable");
	    }

	    System.out.println(GREEN + "✅ Shop menu is NOT clickable (Correct behavior)" + RESET);

	    // 🔁 Re-fetch again before hover
	    shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));

	    System.out.println(YELLOW + "👉 Hovering on Shop menu" + RESET);
	    actions.moveToElement(shopMenu).pause(Duration.ofMillis(800)).perform();

	    // ✅ Wait for dropdown (avoid stale)
	    WebElement categories = wait.until(ExpectedConditions.visibilityOfElementLocated(categoriesBy));
	    WebElement collection = wait.until(ExpectedConditions.visibilityOfElementLocated(collectionBy));

	    // ✅ Validation
	    if (!categories.isDisplayed()) {
	        System.out.println(RED + "❌ Categories not displayed" + RESET);
	        fail("Categories not visible");
	    }

	    if (!collection.isDisplayed()) {
	        System.out.println(RED + "❌ Collections not displayed" + RESET);
	        fail("Collections not visible");
	    }

	    System.out.println(GREEN + "✅ Shop dropdown visible: Categories | Collections" + RESET);

	    // ✅ Continue flow
	    verifyBlogs();
	}
	
//	public void verifyBossladyMenus() {
//
//	    Common.waitForElement(2);
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//	    // 🎨 Console Colors
//	    String RESET  = "\u001B[0m";
//	    String GREEN  = "\u001B[32m";
//	    String CYAN   = "\u001B[36m";
//	    String YELLOW = "\u001B[33m";
//	    String RED    = "\u001B[31m";
//	    String PURPLE = "\u001B[35m";
//
//	    System.out.println(CYAN + "🔍 Verifying Styled By header menu" + RESET);
//
//	    String expectedUrl = "https://www.zlaata.com/influencers";
//
//	    // 🔁 Locate Styled By menu
//	    WebElement styledByMenu = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//a[normalize-space()='Styled By']")
//	            )
//	    );
//
//	    // ✅ Clickable validation
//	    assertTrue("❌ Styled By menu is not clickable", styledByMenu.isEnabled());
//	    System.out.println(GREEN + "✅ Styled By menu is clickable" + RESET);
//
//	    // 👉 Click Styled By
//	    System.out.println(YELLOW + "👉 Clicking Styled By menu" + RESET);
//	    styledByMenu.click();
//
//	    // ✅ URL validation
//	    wait.until(ExpectedConditions.urlToBe(expectedUrl));
//	    String actualUrl = driver.getCurrentUrl();
//
//	    assertEquals(
//	            "❌ URL mismatch! Expected: " + expectedUrl + " | Actual: " + actualUrl,
//	            expectedUrl,
//	            actualUrl
//	    );
//
//	    System.out.println(
//	            GREEN + "✅ URL verified" + RESET +
//	            CYAN + " | Expected: " + expectedUrl +
//	            " | Actual: " + actualUrl + RESET
//	    );
//
//	    // ✅ Heading validation
//	    System.out.println(CYAN + "🔍 Verifying Style by heading" + RESET);
//
//	    WebElement heading = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//h3[@class='prod_list_topic']//span[normalize-space()='Style by']")
//	            )
//	    );
//
//	    assertTrue("❌ Style by heading not displayed", heading.isDisplayed());
//	    System.out.println(GREEN + "✅ Style by heading is displayed" + RESET);
//
//	    // ✅ Products validation
//	    System.out.println(CYAN + "🔍 Verifying product list" + RESET);
//
//	    List<WebElement> products = wait.until(
//	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
//	                    By.xpath("//div[contains(@class,'product_list_cards_list')]")
//	            )
//	    );
//
//	    assertTrue(
//	            "❌ Less than 2 products displayed! Found: " + products.size(),
//	            products.size() >= 2
//	    );
//
//	    System.out.println(
//	            PURPLE + "📦 Products displayed count: " + products.size() + RESET
//	    );
//	}
//	
	
	public void verifyGiftMenu() {

	    Common.waitForElement(2);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    // 🎨 Console Colors
	    String RESET  = "\u001B[0m";
	    String GREEN  = "\u001B[32m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RED    = "\u001B[31m";
	    String PURPLE = "\u001B[35m";

	    System.out.println(CYAN + "🔍 Verifying Gift header menu" + RESET);

	    String expectedUrl = "https://www.zlaata.com/gift";

	    // 🔁 Locate Gift menu
	    WebElement giftMenu = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//a[normalize-space()='Gift']")
	            )
	    );

	    // ✅ Clickable validation
	    assertTrue("❌ Gift menu is not clickable", giftMenu.isEnabled());
	    System.out.println(GREEN + "✅ Gift menu is clickable" + RESET);

	    // 👉 Click Gift
	    System.out.println(YELLOW + "👉 Clicking Gift menu" + RESET);
	    giftMenu.click();

	    // ✅ URL validation
	    wait.until(ExpectedConditions.urlToBe(expectedUrl));
	    String actualUrl = driver.getCurrentUrl();

	    assertEquals(
	            "❌ URL mismatch! Expected: " + expectedUrl + " | Actual: " + actualUrl,
	            expectedUrl,
	            actualUrl
	    );

	    System.out.println(
	            GREEN + "✅ URL verified" + RESET +
	            CYAN + " | Expected: " + expectedUrl +
	            " | Actual: " + actualUrl + RESET
	    );

	    // ✅ Heading validation
	    System.out.println(CYAN + "🔍 Verifying Gifts Collection heading" + RESET);

	    WebElement heading = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//h3[@class='prod_list_topic']//span[normalize-space()='Gifts Collection']")
	            )
	    );

	    assertTrue("❌ Gifts Collection heading not displayed", heading.isDisplayed());
	    System.out.println(GREEN + "✅ Gifts Collection heading is displayed" + RESET);

	    // ✅ Products validation
	    System.out.println(CYAN + "🔍 Verifying product list" + RESET);

	    List<WebElement> products = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                    By.xpath("//div[contains(@class,'product_list_cards_list')]")
	            )
	    );

	    assertTrue(
	            "❌ Less than 2 products displayed! Found: " + products.size(),
	            products.size() >= 2
	    );

	    System.out.println(
	            PURPLE + "📦 Products displayed count: " + products.size() + RESET
	    );
	}
	
	
	
	public void verifyNewArrivalSuggestion() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);

	    // 🎨 Colors
	    String RESET  = "\u001B[0m";
	    String GREEN  = "\u001B[32m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RED    = "\u001B[31m";
	    String PURPLE = "\u001B[35m";

	    By newArrivalMenuBy = By.xpath("//a[normalize-space()='New Arrivals']");
	    By dropdownProductsBy = By.xpath(
	            "//div[contains(@class,'new_arrival_dropdown')]//a[contains(@class,'na_dropdown_card')]"
	    );

	    System.out.println(CYAN + "🔍 Verifying New Arrival suggestions" + RESET);

	    // 🔁 Initial hover
	    actions.moveToElement(
	            wait.until(ExpectedConditions.visibilityOfElementLocated(newArrivalMenuBy))
	    ).pause(Duration.ofMillis(800)).perform();

	    int productCount = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownProductsBy)
	    ).size();

	    assertTrue("❌ No products found in New Arrival dropdown", productCount > 0);

	    System.out.println(
	            PURPLE + "🧾 Total New Arrival dropdown products: " + productCount + RESET
	    );

	    // 🔁 Click one by one
	    for (int i = 0; i < productCount; i++) {

	        // 🔁 Re-hover EVERY loop
	        WebElement newArrivalMenu = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(newArrivalMenuBy)
	        );

	        actions.moveToElement(newArrivalMenu)
	               .pause(Duration.ofMillis(700))
	               .perform();

	        List<WebElement> products = wait.until(
	                ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownProductsBy)
	        );

	        WebElement productToClick = products.get(i);
	        String dropdownName = productToClick.getText().trim();

	        System.out.println(
	                YELLOW + "👉 Clicking product [" + (i + 1) + "]: " + dropdownName + RESET
	        );

	        productToClick.click();

	        // ✅ PDP heading check (display only)
	        WebElement productHeading = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//h4[@class='prod_name']")
	                )
	        );

	        assertTrue(
	                "❌ Product heading not displayed for: " + dropdownName,
	                productHeading.isDisplayed()
	        );

	        System.out.println(
	                GREEN + "✅ Product page loaded | Heading: "
	                        + productHeading.getText().trim() + RESET
	        );

	        // 🔙 Back
	        driver.navigate().back();

	        // ✅ Wait for homepage header to reload (NOT WebElement)
	        wait.until(ExpectedConditions.visibilityOfElementLocated(newArrivalMenuBy));
	    }

	    System.out.println(
	            GREEN + "🎉 All New Arrival suggestion products validated successfully!" + RESET
	    );
	}
	
	private String generateExpectedCategoryUrl(String categoryName) {

	    String baseUrl = "https://www.zlaata.com/";
	    String name = categoryName.trim().toUpperCase();

	    // 🔴 Special category overrides
	    if (name.equals("URBANLAADO")) {
	        return baseUrl + "urban-laado";
	    }
	    if (name.equals("BOSS LADY")) {
	        return baseUrl + "formal-all";
	    }
	    if (name.equals("AURORA")) {
	        return baseUrl + "aurora-party-wear";
	    }
	    if (name.equals("IKAT REIMAGINED")) {
	        return baseUrl + "ikat";
	    }
	    if (name.equals("FORMAL SHIRTS/TOPS")) {
	        return baseUrl + "formal-shirtstops";
	    }


	    // 🟢 Default dynamic rule
	    String slug = categoryName
	            .toLowerCase()
	            .replace("&", "")
	            .replaceAll("\\s+", "-")
	            .replaceAll("-+", "-")
	            .trim();

	    return baseUrl + slug;
	}
//	public void verifyShopMenuAllCategories_Collections() throws InterruptedException {
//		
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//	    Actions actions = new Actions(driver);
//
//	    // 🎨 Console colors
//	    String RESET  = "\u001B[0m";
//	    String GREEN  = "\u001B[32m";
//	    String RED    = "\u001B[31m";
//	    String CYAN   = "\u001B[36m";
//	    String YELLOW = "\u001B[33m";
//	    String PURPLE = "\u001B[35m";
//
////	    By shopMenuBy = By.xpath("//span[contains(@class,'header_nav_link') and normalize-space()='SHOP']");
////	    By categoriesBy = By.xpath("//div[contains(@class,'nav_drop_down_box_category')]//ul/li/a");
////	    By headingBy = By.xpath("//h3[@class='prod_list_topic']/span");
////	    By productsBy = By.xpath("//div[contains(@class,'product_list_cards_list ')]");
//	    
//	    
//	    By shopMenuBy = By.xpath("//span[contains(@class,'header_nav_link') and normalize-space()='SHOP']");
//
//	 // Updated categories locator
//	 By categoriesBy = By.xpath("//a[contains(@class,'dropdown_category_link')]");
//
//	 By headingBy = By.xpath("//h2[@class='prod_listing_topic']");
//
//	 // Fixed product locator
//	 By productsBy = By.xpath("//div[@class='prod_listing_card']");
//
//	    System.out.println(CYAN + "🔍 Verifying Shop → All Categories (URL Rule Based)" + RESET);
//
//	    // Hover Shop
//	    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
//	    actions.moveToElement(shopMenu).pause(Duration.ofMillis(800)).perform();
//
//	    List<WebElement> categories = wait.until(
//	            ExpectedConditions.visibilityOfAllElementsLocatedBy(categoriesBy)
//	    );
//
//	    int count = categories.size();
//	    assertTrue("❌ No categories found under Shop menu", count > 0);
//
//	    System.out.println(PURPLE + "🧾 Total categories: " + count + RESET);
//
//	    for (int i = 0; i < count; i++) {
//
//	        // Re-hover to avoid stale element
//	        shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
//	        actions.moveToElement(shopMenu).pause(Duration.ofMillis(700)).perform();
//
//	        categories = wait.until(
//	                ExpectedConditions.visibilityOfAllElementsLocatedBy(categoriesBy)
//	        );
//
//	        WebElement category = categories.get(i);
//	        String categoryName = category.getText().trim();
//
//	        String expectedUrl = generateExpectedCategoryUrl(categoryName);
//
//	        System.out.println(
//	                YELLOW + "👉 Clicking [" + (i + 1) + "] " + categoryName + RESET
//	        );
//	        System.out.println(
//	                CYAN + "🌐 Expected URL: " + expectedUrl + RESET
//	        );
//
//	        category.click();
//	        Common.waitForElement(2);
//		wait.until(ExpectedConditions.urlContains(expectedUrl.replace("https://www.zlaata.com/", "")));
//	        String actualUrl = driver.getCurrentUrl();
//
//	        assertEquals(
//	                "❌ URL mismatch for category: " + categoryName +
//	                "\nExpected: " + expectedUrl +
//	                "\nActual: " + actualUrl,
//	                expectedUrl,
//	                actualUrl
//	        );
//
//	        System.out.println(
//	                GREEN + "✅ URL matched successfully" + RESET
//	        );
//
//	        // Heading visible
//	        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(headingBy));
//	        assertTrue("❌ Heading not displayed for " + categoryName, heading.isDisplayed());
//
//	        System.out.println(
//	                GREEN + "📌 Heading displayed: " + heading.getText().trim() + RESET
//	        );
//	        
//	        
//	 //------------------------------------------------        
//		     // ⚠ Skip ALL remaining checks if heading is JUMPSUITS
//	        String headingText = heading.getText().trim().toUpperCase();
//	        if ("JUMPSUITS".equals(headingText)) {
//
//	            System.out.println(
//	                    YELLOW + "⚠ Skipping JUMPSUITS category (No products expected)" + RESET
//	            );
//
//	            driver.navigate().back();
//	            wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
//	            continue;   // 🔥 THIS MUST EXECUTE
//	        }
//		        
//	//---------------------------------------------------------	   
//
//	        // Products count
//	        List<WebElement> products = driver.findElements(productsBy);
//
//     
//	        
//	        
//	        assertTrue(
//	                "❌ Less than 1 products for " + categoryName,
//	                products.size() >= 1
//	        );
//
//	        System.out.println(
//	                GREEN + "🛍️ Products displayed: " + products.size() + RESET
//	        );
//
//	        driver.navigate().back();
//	        wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
//	    }
//
//	    System.out.println(
//	            GREEN + "🎉 All Shop categories validated with URL rules!" + RESET
//	    );
//	}
//	
	
//	public void verifyShopMenuAllCategories_Collections() throws InterruptedException {
//		
//		
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//	    Actions actions = new Actions(driver);
//
//	    String RESET  = "\u001B[0m";
//	    String GREEN  = "\u001B[32m";
//	    String CYAN   = "\u001B[36m";
//	    String YELLOW = "\u001B[33m";
//	    String RED    = "\u001B[31m";
//	    String PURPLE = "\u001B[35m";
//
//	    By shopMenuBy = By.xpath("//span[contains(@class,'header_nav_link') and normalize-space()='SHOP']");
//	    By categoriesBy = By.xpath("//a[contains(@class,'dropdown_category_link')]");
//	    By headingBy = By.xpath("//h2[@class='prod_listing_topic']");
//	    By productsBy = By.xpath("//div[@class='prod_listing_card']");
//
//	    System.out.println(CYAN + "🔍 Verifying Shop → All Categories & Collections" + RESET);
//
//	    // Hover SHOP
//	    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
//	    actions.moveToElement(shopMenu).pause(Duration.ofMillis(800)).perform();
//
//	    List<WebElement> categories = wait.until(
//	            ExpectedConditions.visibilityOfAllElementsLocatedBy(categoriesBy)
//	    );
//
//	    int count = categories.size();
//	    Assert.assertTrue("❌ No categories found under Shop menu", count > 0);
//
//	    System.out.println(PURPLE + "🧾 Total categories found: " + count + RESET);
//
//	    for (int i = 0; i < count; i++) {
//
//	        // Re-hover SHOP to avoid stale element
//	        shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
//	        actions.moveToElement(shopMenu).pause(Duration.ofMillis(700)).perform();
//
//	        categories = wait.until(
//	                ExpectedConditions.visibilityOfAllElementsLocatedBy(categoriesBy)
//	        );
//
//	        WebElement category = categories.get(i);
//	        String categoryName = category.getText().trim();
//
//	        System.out.println(YELLOW + "👉 Clicking Category [" + (i + 1) + "] : " + categoryName + RESET);
//
//	        category.click();
//
//	        Thread.sleep(2000);
//
//	        // ---------------- URL VALIDATION ----------------
//
//	        String actualUrl = driver.getCurrentUrl().toLowerCase();
//
//	        System.out.println(CYAN + "🌐 Current URL : " + actualUrl + RESET);
//
//	        // ---------------- HEADING ----------------
//
//	        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(headingBy));
//	        String headingText = heading.getText().toLowerCase();
//
//	        System.out.println(CYAN + "📌 Page Heading : " + headingText + RESET);
//
//	        // ---------------- NORMALIZE CATEGORY WORDS ----------------
//
//	        String cleanedCategory = categoryName.toLowerCase()
//	                .replace("-", " ")
//	                .replace("_", " ")
//	                .replace("&", " ");
//
//	        String[] words = cleanedCategory.split(" ");
//
//	        boolean urlMatch = true;
//	        boolean headingMatch = true;
//
//	        for (String word : words) {
//
//	            if (word.trim().isEmpty()) continue;
//
//	            if (!actualUrl.contains(word)) {
//	                urlMatch = false;
//	            }
//
//	            if (!headingText.contains(word)) {
//	                headingMatch = false;
//	            }
//	        }
//
//	        // ---------------- PRODUCTS ----------------
//
//	        List<WebElement> products = wait.until(
//	                ExpectedConditions.visibilityOfAllElementsLocatedBy(productsBy)
//	        );
//
//	        int productCount = products.size();
//
//	        System.out.println(CYAN + "🛍 Products Found : " + productCount + RESET);
//
//	        // ---------------- FINAL VALIDATION ----------------
//
//	        if (!urlMatch || !headingMatch || productCount < 1) {
//
//	            Assert.fail(
//	                    RED + "\n❌ VALIDATION FAILED" +
//	                    "\nClicked Category : " + categoryName +
//	                    "\nCurrent URL      : " + actualUrl +
//	                    "\nHeading          : " + headingText +
//	                    "\nProducts Found   : " + productCount +
//	                    RESET
//	            );
//	        }
//
//	        System.out.println(GREEN + "✅ Category validated successfully" + RESET);
//
//	        driver.navigate().back();
//
//	        wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
//	    }
//
//	    System.out.println(GREEN + "🎉 All Categories & Collections validated successfully!" + RESET);
//	    
//		
//
//	}
	
//	public void verifyShopMenuAllCategories_Collections() throws InterruptedException {
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//	    Actions actions = new Actions(driver);
//
//	    String RESET  = "\u001B[0m";
//	    String GREEN  = "\u001B[32m";
//	    String CYAN   = "\u001B[36m";
//	    String YELLOW = "\u001B[33m";
//	    String RED    = "\u001B[31m";
//	    String PURPLE = "\u001B[35m";
//
//	    By shopMenuBy = By.xpath("//span[contains(@class,'header_nav_link') and normalize-space()='SHOP']");
//	    By categoriesBy = By.xpath("//a[contains(@class,'dropdown_category_link')]");
//	    By headingBy = By.xpath("//h2[@class='prod_listing_topic']");
//	    By productsBy = By.xpath("//div[@class='prod_listing_card']");
//
//	    System.out.println(CYAN + "🔍 Verifying Shop → All Categories & Collections" + RESET);
//
//	    // Hover SHOP
//	    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
//	    actions.moveToElement(shopMenu).pause(Duration.ofMillis(800)).perform();
//
//	    List<WebElement> categories = wait.until(
//	            ExpectedConditions.visibilityOfAllElementsLocatedBy(categoriesBy)
//	    );
//
//	    int count = categories.size();
//	    Assert.assertTrue("❌ No categories found under Shop menu", count > 0);
//
//	    System.out.println(PURPLE + "🧾 Total categories found: " + count + RESET);
//
//	    for (int i = 0; i < count; i++) {
//
//	        // Re-hover SHOP (avoid stale)
//	        shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
//	        actions.moveToElement(shopMenu).pause(Duration.ofMillis(700)).perform();
//
//	        categories = wait.until(
//	                ExpectedConditions.visibilityOfAllElementsLocatedBy(categoriesBy)
//	        );
//
//	        WebElement category = categories.get(i);
//	        String categoryName = category.getText().trim();
//
//	        System.out.println(YELLOW + "👉 Clicking Category [" + (i + 1) + "] : " + categoryName + RESET);
//
//	        category.click();
//
//	        Thread.sleep(2000);
//
//	        // ---------------- URL ----------------
//	        String actualUrl = driver.getCurrentUrl().toLowerCase();
//	        System.out.println(CYAN + "🌐 Current URL : " + actualUrl + RESET);
//
//	        // ---------------- HEADING ----------------
//	        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(headingBy));
//	        String headingText = heading.getText().toLowerCase();
//	        System.out.println(CYAN + "📌 Page Heading : " + headingText + RESET);
//
//	        // ---------------- CLEAN CATEGORY ----------------
//	        String cleanedCategory = categoryName.toLowerCase()
//	                .replace("-", " ")
//	                .replace("_", " ")
//	                .replace("&", " ");
//
//	        String[] words = cleanedCategory.split(" ");
//
//	        // ---------------- NEW LOGIC ----------------
//	        int urlMismatchCount = 0;
//	        int headingMismatchCount = 0;
//
//	        for (String word : words) {
//
//	            if (word.trim().isEmpty()) continue;
//
//	            if (!actualUrl.contains(word)) {
//	                urlMismatchCount++;
//	            }
//
//	            if (!headingText.contains(word)) {
//	                headingMismatchCount++;
//	            }
//	        }
//
//	        System.out.println(YELLOW + "🔎 URL Mismatch Count     : " + urlMismatchCount + RESET);
//	        System.out.println(YELLOW + "🔎 Heading Mismatch Count : " + headingMismatchCount + RESET);
//
//	        // ---------------- PRODUCTS ----------------
//	        List<WebElement> products = wait.until(
//	                ExpectedConditions.visibilityOfAllElementsLocatedBy(productsBy)
//	        );
//
//	        int productCount = products.size();
//	        System.out.println(CYAN + "🛍 Products Found : " + productCount + RESET);
//
//	        // ---------------- FINAL VALIDATION ----------------
//	        if (urlMismatchCount > 1 || headingMismatchCount > 1 || productCount < 1) {
//
//	            Assert.fail(
//	                    RED + "\n❌ VALIDATION FAILED" +
//	                    "\nClicked Category : " + categoryName +
//	                    "\nCurrent URL      : " + actualUrl +
//	                    "\nHeading          : " + headingText +
//	                    "\nURL Mismatch     : " + urlMismatchCount +
//	                    "\nHeading Mismatch : " + headingMismatchCount +
//	                    "\nProducts Found   : " + productCount +
//	                    RESET
//	            );
//	        }
//
//	        System.out.println(GREEN + "✅ Category validated successfully" + RESET);
//
//	        driver.navigate().back();
//
//	        wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
//	    }
//
//	    System.out.println(GREEN + "🎉 All Categories & Collections validated successfully!" + RESET);
//	}
//	
	public void verifyShopMenuAllCategories_Collections() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);

	    String RESET  = "\u001B[0m";
	    String GREEN  = "\u001B[32m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RED    = "\u001B[31m";
	    String PURPLE = "\u001B[35m";

	    // ✅ UPDATED XPATH (only change here)
	    By shopMenuBy = By.xpath("//span[contains(@class,'header_nav_link') and normalize-space()='SHOP']");
	    By categoriesBy = By.xpath("//div[contains(@class,'dropdown')]//a[contains(@class,'dropdown_category_link')]");
	    By headingBy = By.xpath("//h2[@class='prod_listing_topic']");
	    By productsBy = By.xpath("//div[@class='prod_listing_card']");

	    System.out.println(CYAN + "🔍 Verifying Shop → All Categories & Collections" + RESET);

	    // Hover SHOP
	    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
	    actions.moveToElement(shopMenu).pause(Duration.ofMillis(800)).perform();

	    // ✅ UPDATED fetching (safe)
	    List<WebElement> categories = driver.findElements(categoriesBy);

	    if (categories.size() == 0) {
	        throw new RuntimeException("❌ Categories not visible under SHOP menu");
	    }

	    int count = categories.size();
	    System.out.println(PURPLE + "🧾 Total categories found: " + count + RESET);

	    for (int i = 0; i < count; i++) {

	        // 🔁 Re-hover SHOP (important)
	        shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
	        actions.moveToElement(shopMenu).pause(Duration.ofMillis(700)).perform();

	        // ✅ Re-fetch using UPDATED XPath
	        categories = driver.findElements(categoriesBy);

	        WebElement category = categories.get(i);
	        String categoryName = category.getText().trim();

	        System.out.println(YELLOW + "👉 Clicking Category [" + (i + 1) + "] : " + categoryName + RESET);

	        category.click();
	        
	        Common.waitForElement(10);

	        Thread.sleep(2000);

	        // ---------------- URL ----------------
	        String actualUrl = driver.getCurrentUrl().toLowerCase();
	        System.out.println(CYAN + "🌐 Current URL : " + actualUrl + RESET);

	        // ---------------- HEADING ----------------
	        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(headingBy));
	        String headingText = heading.getText().toLowerCase();
	        System.out.println(CYAN + "📌 Page Heading : " + headingText + RESET);

	        // ---------------- CLEAN CATEGORY ----------------
	        String cleanedCategory = categoryName.toLowerCase()
	                .replace("-", " ")
	                .replace("_", " ")
	                .replace("&", " ");

	        String[] words = cleanedCategory.split(" ");

	        int urlMismatchCount = 0;
	        int headingMismatchCount = 0;

	        for (String word : words) {

	            if (word.trim().isEmpty()) continue;

	            if (!actualUrl.contains(word)) {
	                urlMismatchCount++;
	            }

	            if (!headingText.contains(word)) {
	                headingMismatchCount++;
	            }
	        }

	        System.out.println(YELLOW + "🔎 URL Mismatch Count     : " + urlMismatchCount + RESET);
	        System.out.println(YELLOW + "🔎 Heading Mismatch Count : " + headingMismatchCount + RESET);

	        // ---------------- PRODUCTS ----------------
	        List<WebElement> products = driver.findElements(productsBy);

	        int productCount = products.size();
	        System.out.println(CYAN + "🛍 Products Found : " + productCount + RESET);

	        // ---------------- FINAL VALIDATION ----------------
	        if (urlMismatchCount > 1 || headingMismatchCount > 1 || productCount < 1) {

	            throw new RuntimeException(
	                    "❌ VALIDATION FAILED\n" +
	                    "Category : " + categoryName +
	                    "\nURL : " + actualUrl +
	                    "\nHeading : " + headingText +
	                    "\nProducts : " + productCount
	            );
	        }

	        System.out.println(GREEN + "✅ Category validated successfully" + RESET);

	        driver.navigate().back();

	        wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
	    }

	    System.out.println(GREEN + "🎉 All Categories validated successfully!" + RESET);
	}
	public void verifyBossLadySuggestions() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);

	    // 🎨 Console colors
	    String RESET  = "\u001B[0m";
	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String PURPLE = "\u001B[35m";

	    By bossLadyMenuBy = By.xpath("//li[contains(@class,'boss-lady')]//span[normalize-space()='Boss Lady']");
	    By dropdownItemsBy = By.xpath("//li[contains(@class,'boss-lady')]//a[contains(@class,'bl_dropdown_card')]");
	    By headingBy = By.xpath("//h3[@class='prod_list_topic']/span");
	    By productsBy = By.xpath("//div[contains(@class,'product_list_cards_list ')]");

	    System.out.println(CYAN + "🔍 Verifying Boss Lady dropdown suggestions" + RESET);

	    // Hover Boss Lady
	    WebElement bossLadyMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(bossLadyMenuBy));
	    actions.moveToElement(bossLadyMenu).pause(Duration.ofMillis(800)).perform();

	    List<WebElement> items = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownItemsBy)
	    );

	    int count = items.size();
	    assertTrue("❌ No Boss Lady dropdown items found", count > 0);

	    System.out.println(PURPLE + "🧾 Boss Lady dropdown items: " + count + RESET);

	    for (int i = 0; i < count; i++) {

	        // Re-hover to avoid stale
	        bossLadyMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(bossLadyMenuBy));
	        actions.moveToElement(bossLadyMenu).pause(Duration.ofMillis(700)).perform();

	        items = wait.until(
	                ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownItemsBy)
	        );

	        WebElement item = items.get(i);

	        String categoryName = item.findElement(
	                By.xpath(".//span[@class='bl_dropdown_card_name']")
	        ).getText().trim();

	        String expectedUrl = generateExpectedCategoryUrl(categoryName);

	        System.out.println(YELLOW + "👉 Clicking [" + (i + 1) + "] " + categoryName + RESET);
	        System.out.println(CYAN + "🌐 Expected URL: " + expectedUrl + RESET);

	        item.click();

	        // URL validation
	        wait.until(ExpectedConditions.urlContains(
	                expectedUrl.replace("https://www.zlaata.com/", "")
	        ));

	        assertEquals(
	                "❌ URL mismatch for " + categoryName,
	                expectedUrl,
	                driver.getCurrentUrl()
	        );

	        System.out.println(GREEN + "✅ URL matched" + RESET);

	        // ✅ Heading MATCH validation
	        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(headingBy));
	        String actualHeading = heading.getText().trim();


	        String expectedNormalized = categoryName
	                .replaceAll("\\s+", "")   // 🔥 remove ALL spaces
	                .toUpperCase();

	        String actualNormalized = actualHeading
	                .replaceAll("\\s+", "")   // 🔥 remove ALL spaces
	                .toUpperCase();

	        System.out.println("🧪 Expected Heading (normalized): " + expectedNormalized);
	        System.out.println("🧪 Actual Heading   (normalized): " + actualNormalized);

	        Assert.assertEquals(
	                "❌ Heading mismatch for category: " + categoryName,
	                expectedNormalized,
	                actualNormalized
	        );

	        System.out.println(
	                GREEN + "📌 Heading matched: " + actualHeading + RESET
	        );

	        // Products validation
	        List<WebElement> products = wait.until(
	                ExpectedConditions.numberOfElementsToBeMoreThan(productsBy, 1)
	        );

	        assertTrue(
	                "❌ Less than 2 products for " + categoryName,
	                products.size() >= 2
	        );

	        System.out.println(
	                GREEN + "🛍️ Products displayed: " + products.size() + RESET
	        );

	        // Navigate back
	        driver.navigate().back();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(bossLadyMenuBy));
	    }

	    System.out.println(
	            GREEN + "🎉 Boss Lady dropdown categories validated successfully!" + RESET
	    );
	}
	
	public void verifyProductHoverImages_Shop_AllCategories_Collections_Styles() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);

	    // 🎨 Console colors
	    String RESET  = "\u001B[0m";
	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String PURPLE = "\u001B[35m";

	    By shopMenuBy = By.xpath("//span[@class='navigation_menu_txt'][normalize-space()='Shop']");
	    By categoriesBy = By.xpath("//div[contains(@class,'nav_drop_down_box_category')]//ul/li/a");
	    By productCardBy = By.xpath("//div[contains(@class,'product_list_cards_list ')]");

	    System.out.println(CYAN + "🔍 Verifying Product Hover Image Functionality (Shop)" + RESET);
	    // Hover Shop
	    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
	    actions.moveToElement(shopMenu).pause(Duration.ofMillis(800)).perform();

	    List<WebElement> categories = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(categoriesBy)
	    );
	    // ❌ Track failed categories
	    List<String> failedCategories = new ArrayList<>();

	    // Get category count (NO storing elements)
	    int categoryCount = wait.until(ExpectedConditions
	            .visibilityOfAllElementsLocatedBy(categoriesBy)).size();

	    for (int c = 0; c < categoryCount; c++) {

	    	Common.waitForElement(1);
	        shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
	        actions.moveToElement(shopMenu).pause(Duration.ofMillis(700)).perform();

	        categories = wait.until(
	                ExpectedConditions.visibilityOfAllElementsLocatedBy(categoriesBy)
	        );

	        WebElement category = categories.get(c);
	        String categoryName = category.getText().trim();

	        System.out.println(PURPLE + "\n📂 CATEGORY: " + categoryName + RESET);
	        Common.waitForElement(1);
	        category.click();
	        Common.waitForElement(2);

	        List<WebElement> products = driver.findElements(productCardBy);
	        int maxProducts = Math.min(4, products.size());

	        boolean categoryFailed = false;

	        for (int i = 0; i < maxProducts; i++) {
	            try {
	                WebElement product = products.get(i);

	                // 🖱️ Hover product
	                actions.moveToElement(product).pause(Duration.ofMillis(600)).perform();

	                // Main image
	                WebElement mainImg = product.findElement(
	                        By.cssSelector("picture.prod_main_img img"));

	                // Hover image
	                WebElement hoverImg = product.findElement(
	                        By.cssSelector("picture.prod_hover_img img"));

	                String mainSrc  = mainImg.getAttribute("src");
	                String hoverSrc = hoverImg.getAttribute("src");

	                boolean mainOk  = mainSrc != null && !mainSrc.contains("placeholder-img");
	                boolean hoverOk = hoverSrc != null && !hoverSrc.contains("placeholder-img");

	                if (mainOk && hoverOk) {
	                    System.out.println(GREEN + "✅ Product " + (i + 1)
	                            + " → Main & Hover images OK" + RESET);
	                } else {
	                    categoryFailed = true;
	                    System.out.println(RED + "❌ Product " + (i + 1)
	                            + " → Image missing / placeholder" + RESET);
	                }

	            } catch (Exception e) {
	                categoryFailed = true;
	                System.out.println(RED + "❌ Product " + (i + 1)
	                        + " → Image elements not found" + RESET);
	            }
	        }

	        if (categoryFailed) {
	            failedCategories.add(categoryName);
	            System.out.println(RED + "❌ Category failed: " + categoryName + RESET);
	        } else {
	            System.out.println(GREEN + "✅ Category passed: " + categoryName + RESET);
	        }

	        // ⬅️ Back to menu safely
	        driver.navigate().back();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(shopMenuBy));
	    }

	    // 🚨 FINAL ASSERT (FAIL AT END)
	    if (!failedCategories.isEmpty()) {
	        Assert.fail(
	                "❌ Image missing in categories: " + String.join(", ", failedCategories)
	        );
	    }

	    System.out.println(
	            GREEN + "\n🎉 All categories passed product hover image validation!" + RESET
	    );
	}

	
	public void verifyBlogs() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    List<String> failedCategories = new ArrayList<>();

	    try {

	        String beforeClickUrl = driver.getCurrentUrl();
	        URI baseUri = URI.create(beforeClickUrl);
	        String baseSiteUrl = baseUri.getScheme() + "://" + baseUri.getHost();

	        WebElement blogsLink = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.xpath("//a[normalize-space()='ZBLOG']"))
	        );

	        blogsLink.click();

	        Common.waitForElement(2);

	        wait.until(ExpectedConditions.urlContains("/blogs"));

	        String expectedBlogsUrl = baseSiteUrl + "/blogs/";
	        String actualBlogsUrl = driver.getCurrentUrl();

	        Assert.assertEquals("❌ Blogs URL mismatch", expectedBlogsUrl, actualBlogsUrl);

	        System.out.println(GREEN + "✅ Blogs page loaded successfully" + RESET);

	        List<WebElement> blogCategories = wait.until(
	                ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                        By.xpath("//ul[@id='ast-hf-menu-1']/li/a"))
	        );

	        Assert.assertTrue("❌ Blog categories not displayed", blogCategories.size() > 0);

	        System.out.println(CYAN + "📂 Total Blog Categories: " + blogCategories.size() + RESET);

	        for (int i = 0; i < blogCategories.size(); i++) {

	            blogCategories = wait.until(
	                    ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                            By.xpath("//ul[@id='ast-hf-menu-1']/li/a"))
	            );

	            WebElement category = blogCategories.get(i);
	            String categoryName = category.getText().trim();

	            System.out.println(YELLOW + "👉 Clicking category: " + categoryName + RESET);

	            category.click();

	            Common.waitForElement(2);

	            // -------- HOME --------
	            if (categoryName.equalsIgnoreCase("Home")) {

	                if (!driver.getCurrentUrl().equals(expectedBlogsUrl)) {

	                    failedCategories.add("Home");

	                    System.out.println(RED + "❌ Home did not stay on Blogs page" + RESET);

	                } else {

	                    System.out.println(GREEN + "✅ Home stayed on Blogs page" + RESET);
	                }
	            }

	            // -------- SHOP --------
	            else if (categoryName.equalsIgnoreCase("Shop")) {

	                wait.until(ExpectedConditions.urlToBe(baseSiteUrl + "/"));

	                String currentUrl = driver.getCurrentUrl();

	                if (!currentUrl.equals(baseSiteUrl + "/")) {

	                    failedCategories.add("Shop");

	                    System.out.println(RED + "❌ Shop did not redirect to home page" + RESET);

	                } else {

	                    System.out.println(GREEN + "✅ Shop redirected to home page" + RESET);
	                }

	                driver.get(expectedBlogsUrl);

	                wait.until(ExpectedConditions.urlToBe(expectedBlogsUrl));
	            }

	            // -------- BLOG CATEGORIES --------
	            else {

	                try {

	                    WebElement heading = wait.until(
	                            ExpectedConditions.visibilityOfElementLocated(
	                                    By.xpath("//h3[contains(text(),'Category:')]"))
	                    );

	                    String headingText = heading.getText().trim().toUpperCase();

	                    String expectedHeading = ("Category: " + categoryName).toUpperCase();

	                    if (!headingText.equals(expectedHeading)) {

	                        failedCategories.add(categoryName);

	                        System.out.println(
	                                RED + "❌ Heading mismatch | Expected: "
	                                        + expectedHeading + " | Actual: " + headingText + RESET);

	                    } else {

	                        System.out.println(GREEN + "✅ Heading verified: " + headingText + RESET);
	                    }

	                }

	                catch (Exception e) {

	                    failedCategories.add(categoryName);

	                    System.out.println(RED + "❌ Heading not found for " + categoryName + RESET);
	                }
	            }
	        }

	        if (!failedCategories.isEmpty()) {

	            Assert.fail(
	                    "❌ Blog category validation failed for: "
	                            + String.join(", ", failedCategories)
	            );
	        }

	        System.out.println(GREEN + "\n🎉 All Blogs categories validated successfully!" + RESET);

	    }

	    catch (Exception e) {

	        System.out.println(RED + "❌ Blogs verification failed: " + e.getMessage() + RESET);

	        Assert.fail("Blogs verification failed");
	    }
	    
	   click(logo);
	}
	
//TC-01	
	public void validateAllHeaderMenus() throws InterruptedException {
		
	//	appLaunch();
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
		
		click(zlaataIndiaShopButton);
		
		Common.waitForElement(10);
		
		//verifyHomeMenu();
		
		verifynewArrivalMenu();
		
		verifySaleMenu();
		
		verifyShopHeaderMenu();
		
		 verifyBlogs();
		
		verifyBossLadyMenu();
		
		
	//	verifyStyledByMenu();
		
	//	verifyGiftMenu();	
	}
	
	
//TC-02
	public void validateNewArrivalSuggestions() {
	//	appLaunch();
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
		
		verifyNewArrivalSuggestion();
	}
	
//TC-03
	public void validateShopAllCategories_CollectionsAndStyle() throws InterruptedException {
	//	appLaunch();
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
		click(zlaataIndiaShopButton);
		
		verifyShopMenuAllCategories_Collections();
		click(brandName);
		verifyShopMenuAllCategories_Collections();

		
		
	}
	
//TC-04
	
	
	public void validateBossLadySuggestions() {
		
	//	appLaunch();
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
		
		verifyBossLadySuggestions();
	}
	
//Tc-04
	
	public void validateMouseHoverAllCategories_CollectionAndStle() {
		
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

		verifyProductHoverImages_Shop_AllCategories_Collections_Styles();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean verifyExactText(WebElement ele, String expectedText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public WebDriver gmail(String browserName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isAt() {
		// TODO Auto-generated method stub
		return false;
	}


}