package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import manager.FileReaderManager;
import objectRepo.landingPageObjRepo;
import utils.Common;

public final class landingPage  extends landingPageObjRepo{


	public landingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);

	}
	
	
	public void openTheApplication() {
	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());


	}
	public void verifyhamburgerdropdownAllMenu() throws InterruptedException {

	    String GREEN = "\u001B[32m";
	    String YELLOW = "\u001B[33m";
	    String BLUE = "\u001B[34m";
	    String CYAN = "\u001B[36m";
	    String RESET = "\u001B[0m";

	    openTheApplication();
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    click(hambager);
	     Common.waitForElement(5);

	    List<WebElement> categories = driver.findElements(
	            By.xpath("//div[contains(@class,'brand_categories_card')]//a"));

	    int total = categories.size();
	    System.out.println(CYAN + "Total categories found: " + total + RESET);

	    click(hambager);
	     Common.waitForElement(5);

	    
	    for (int i = 0; i < total; i++) {

	        wait.until(ExpectedConditions.elementToBeClickable(hambager)).click();
	        Thread.sleep(2000);

	        categories = driver.findElements(
	                By.xpath("//div[contains(@class,'brand_categories_card')]//a"));

	        WebElement category = categories.get(i);

	        String categoryName = category.getText().trim();
	        System.out.println(BLUE + "Clicking category: " + categoryName + RESET);

	        category.click();
		     Common.waitForElement(2);


	        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//h2[@class='prod_listing_topic']")));

	        String pageHeading = heading.getText().trim();
	        System.out.println(YELLOW + "Page heading: " + pageHeading + RESET);

	        String normalizedCategory = categoryName.replace("-", "").replace(" ", "").toLowerCase();
	        String normalizedHeading = pageHeading.replace("-", "").replace(" ", "").toLowerCase();

	        if (!normalizedCategory.equals(normalizedHeading)) {
	            throw new AssertionError(
	                    "Category mismatch! Clicked: " + categoryName + " but Page shows: " + pageHeading);
	        }

	        List<WebElement> products = driver.findElements(
	                By.xpath("//div[@class='prod_listing_card']")
	        );

	        if (products.size() > 0) {

	            WebElement firstProduct = wait.until(
	                    ExpectedConditions.visibilityOf(products.get(0))
	            );

	            if (firstProduct.isDisplayed()) {
	                System.out.println(GREEN + "Product is displayed in category: " + categoryName + RESET);
	            }

	        } else {

	            System.out.println(YELLOW + "No products found in category: " + categoryName + RESET);

	        }

	        driver.navigate().back();
	    }
	}
	
	public void verifyUserCanabletoClikShopButtonforZlaata_IndiaandBoss_lady() {

	    String GREEN = "\u001B[32m";
	    String YELLOW = "\u001B[33m";
	    String BLUE = "\u001B[34m";
	    String CYAN = "\u001B[36m";
	    String RESET = "\u001B[0m";

	    openTheApplication();
	    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    System.out.println(CYAN + "Opening Application..." + RESET);

	    // Click Zlaata India Shop
	    System.out.println(BLUE + "Clicking Zlaata India Shop button" + RESET);
	    click(zlaataIndiaShopButton);

	    Common.waitForElement(10);
	    wait.until(ExpectedConditions.urlContains("zlaata-india"));

	    String actualUrl = driver.getCurrentUrl();
	    String expectedUrl = "https://www.zlaata.com/zlaata-india";

	    System.out.println(YELLOW + "Zlaata India URL: " + actualUrl + RESET);

	    if (!actualUrl.equals(expectedUrl)) {
	        throw new AssertionError("Zlaata India URL mismatch!");
	    }

	    System.out.println(GREEN + "Zlaata India URL verification successful" + RESET);

	    // Click Zlaata Logo
	    System.out.println(BLUE + "Clicking Zlaata Logo to return home" + RESET);

	    WebElement  blogs = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//a[normalize-space()='ZBLOG']")));

	    blogs.click();
	    

	    WebElement  logo  = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//img[@alt='Zlaata Blog']")));

	    logo.click();

	    // Click Boss Lady Shop
	    System.out.println(BLUE + "Clicking Boss Lady Shop button" + RESET);

	    wait.until(ExpectedConditions.elementToBeClickable(bossladyShopButton)).click();

	    wait.until(ExpectedConditions.urlContains("boss-lady"));

	    String bossActualUrl = driver.getCurrentUrl();
	    String bossExpectedUrl = "https://www.zlaata.com/boss-lady";

	    System.out.println(YELLOW + "Boss Lady URL: " + bossActualUrl + RESET);

	    if (!bossActualUrl.equals(bossExpectedUrl)) {
	        throw new AssertionError("Boss Lady URL mismatch!");
	    }

	    System.out.println(GREEN + "Boss Lady URL verification successful" + RESET);

	    System.out.println(CYAN + "Both Shop buttons working correctly" + RESET);
	}




	public void verifythatLogoRedirectionInLandingPage() {

	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String RESET = "\u001B[0m";

	    openTheApplication();
	    String before = driver.getCurrentUrl();

	    driver.findElement(By.xpath("//a[@class='brand_logo']//img[@alt='zlaata Logo']")).click();

	    String after = driver.getCurrentUrl();

	    if (!before.equals(after)) {
	        System.out.println(GREEN + "Redirection happened" + RESET);
	    } else {
	        System.out.println(RED + "No redirection" + RESET);
	    }
	}
	
	
	public void verifycartButtonRedirection() {

	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    
	    openTheApplication();

	    String expected = "checkout/cart";


	    // Click cart button
	    driver.findElement(By.xpath("//button[@title='Cart']")).click();

	    String after = driver.getCurrentUrl();

	    if (after.contains(expected)) {
	        System.out.println(GREEN + "Cart redirection successful" + RESET);
	    } else {
	        System.out.println(RED + "Cart redirection failed" + RESET);
	    }
	}
	
	public void verifywishlistButtonRedirecdtion() {
		
		
		
		 String GREEN = "\u001B[32m";
		    String RED   = "\u001B[31m";
		    String RESET = "\u001B[0m";
		    openTheApplication();
		    
		    String expected = "wishlist";
		    
		    // Click cart button
		    driver.findElement(By.xpath("//button[@title='Cart']")).click();

		    String after = driver.getCurrentUrl();

		    if (after.contains(expected)) {
		        System.out.println(GREEN + "Cart redirection successful" + RESET);
		    } else {
		        System.out.println(RED + "Cart redirection failed" + RESET);
		    }
		
	
	}
	
	public void verifyProfileiconRedirection() {

	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String CYAN  = "\u001B[36m";
	    String RESET = "\u001B[0m";

	    openTheApplication();
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    System.out.println(CYAN + "Clicking Profile Icon..." + RESET);

	    // Click Account / Profile icon
	    WebElement profileIcon = wait.until(
	        ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[@title='Account']")
	        )
	    );
	    profileIcon.click();

	    // Verify either of the elements is displayed
	    boolean isProfileVisible = false;

	    try {
	        WebElement accountContent = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//div[@class='account_tabs_user_content']")
	            )
	        );
	        isProfileVisible = accountContent.isDisplayed();

	        System.out.println(GREEN + "Account content is visible" + RESET);

	    } catch (Exception e) {
	        try {
	            WebElement couponDetails = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//p[@class='login_coupon_details']")
	                )
	            );
	            isProfileVisible = couponDetails.isDisplayed();

	            System.out.println(GREEN + "Login popup are visible" + RESET);

	        } catch (Exception ex) {
	            isProfileVisible = false;
	        }
	    }

	    // Final validation
	    if (isProfileVisible) {
	        System.out.println(GREEN + "✅ Profile section is displayed" + RESET);
	    } else {
	        System.out.println(RED + "❌ Profile section is NOT displayed" + RESET);
	    }
	}
	
	
	public void verifyFlashSaleSection() {

	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    
	    
	    openTheApplication();

	    boolean isPresent = driver.findElements(
	        By.xpath("//a[@aria-label='Flash Sale']")
	    ).size() > 0;

	    if (isPresent) {
	        System.out.println(GREEN + "✅ Flash Sale section is DISPLAYED" + RESET);
	    } else {
	        System.out.println(RED + "❌ Flash Sale section is NOT AVAILABLE" + RESET);
	    }
	}
	
	
	public void verifyhamburgeiconCloseButtonandPrintThedropdowncategoryname() {

	    openTheApplication();
	    click(hambager);

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    // 🔹 OPEN dropdown
	    driver.findElement(By.xpath("//div[@class='brand_categories_header']")).click();
	    System.out.println(CYAN + "Dropdown opened" + RESET);

	    // 🔹 Print ZLAATA INDIA Heading
	    String heading1 = driver.findElement(By.xpath("(//div[@class='brand_categories_header']//span)[1]")).getText();
	    System.out.println(CYAN + "Heading: " + heading1 + RESET);

	    // 🔹 Print ZLAATA INDIA Categories
	    List<WebElement> cat1 = driver.findElements(By.xpath("(//div[@class='brand_categories_card_wrap'])[1]//span"));

	    if (cat1.size() == 0) {
	        System.out.println(RED + "ZLAATA INDIA categories not found" + RESET);
	        throw new RuntimeException("ZLAATA INDIA categories not displayed");
	    }

	    System.out.println(YELLOW + "ZLAATA INDIA Categories:" + RESET);
	    for (WebElement c : cat1) {

	        if (c.getText().trim().isEmpty()) {
	            System.out.println(RED + "Empty category name found" + RESET);
	            throw new RuntimeException("Category name is empty");
	        }

	        // 🔹 UPDATED: Check image is actually loaded
	        WebElement image = c.findElement(By.xpath("./preceding-sibling::picture//img"));

	        Boolean isImageLoaded = (Boolean) ((JavascriptExecutor) driver)
	                .executeScript("return arguments[0].complete && arguments[0].naturalWidth > 0", image);

	        if (!isImageLoaded) {
	            System.out.println(RED + "Image NOT loaded for: " + c.getText() + RESET);
	            throw new RuntimeException("Image not loaded for category: " + c.getText());
	        }

	        System.out.println(GREEN + c.getText() + " - Image loaded" + RESET);
	    }

	    // 🔹 Print BOSS LADY Heading
	    String heading2 = driver.findElement(By.xpath("(//div[@class='brand_categories_header']//span)[2]")).getText();
	    System.out.println(CYAN + "Heading: " + heading2 + RESET);

	    // 🔹 Print BOSS LADY Categories
	    List<WebElement> cat2 = driver.findElements(By.xpath("(//div[@class='brand_categories_card_wrap'])[2]//span"));

	    if (cat2.size() == 0) {
	        System.out.println(RED + "BOSS LADY categories not found" + RESET);
	        throw new RuntimeException("BOSS LADY categories not displayed");
	    }

	    System.out.println(YELLOW + "BOSS LADY Categories:" + RESET);
	    for (WebElement c : cat2) {

	        if (c.getText().trim().isEmpty()) {
	            System.out.println(RED + "Empty category name found" + RESET);
	            throw new RuntimeException("Category name is empty");
	        }

	        // 🔹 UPDATED: Check image is actually loaded
	        WebElement image = c.findElement(By.xpath("./preceding-sibling::picture//img"));

	        Boolean isImageLoaded = (Boolean) ((JavascriptExecutor) driver)
	                .executeScript("return arguments[0].complete && arguments[0].naturalWidth > 0", image);

	        if (!isImageLoaded) {
	            System.out.println(RED + "Image NOT loaded for: " + c.getText() + RESET);
	            throw new RuntimeException("Image not loaded for category: " + c.getText());
	        }

	        System.out.println(GREEN + c.getText() + " - Image loaded" + RESET);
	    }

	    // 🔹 CLOSE dropdown
	    click(hambager);
	    System.out.println(CYAN + "Dropdown closed" + RESET);
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
