package pages;

import java.time.Duration; // CORRECT
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import groovyjarjarantlr4.v4.runtime.tree.xpath.XPath;
import io.cucumber.java.Scenario;
import manager.FileReaderManager;
import objectRepo.ProductDetailsPageObjRepo;
import utils.Common;

public final class ProductDetailsPage extends ProductDetailsPageObjRepo {
	String currentProductName;

	public ProductDetailsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	private void RandomProduct() {

		  String GREEN  = "\u001B[32m";
		    String RESET  = "\u001B[0m";
		    String CYAN   = "\u001B[36m";
		    String BLUE   = "\u001B[34m";
		    String RED    = "\u001B[31m";

		    System.out.println(CYAN + "────────────────────────────────────────────" + RESET);

		  


		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		    Actions actions = new Actions(driver);

		    System.out.println(BLUE + "🔍 Navigating to category..." + RESET);

		    actions.moveToElement(shopMenu).perform();
		    actions.moveToElement(randomcategory).click().perform();
		    Common.waitForElement(2);
		    // Select random product
		    List<WebElement> products = wait.until(
		            ExpectedConditions.visibilityOfAllElementsLocatedBy(
		                    By.xpath("//div[@class='prod_listing_card']")
		            )
		    );

		    Assert.assertTrue("❌ No products found", products.size() > 0);

		    Collections.shuffle(products);
		    products.get(0).click();
		    Common.waitForElement(2);

	}


	public void clickUsingJavaScript(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	public void productNameAndPercentageAndPrice() {

	    String GREEN  = "\u001B[32m";
	    String RESET  = "\u001B[0m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String PURPLE = "\u001B[35m";
	    String BLUE   = "\u001B[34m";
	    String RED    = "\u001B[31m";

	    String line = "──────────────────────────────────────────────────────────────";
	    System.out.println(CYAN + line + RESET);

	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

	    click(zlaataIndiaShopButton);
	    
	    
	    Common.waitForElement(10);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Actions actions = new Actions(driver);

	    System.out.println(BLUE + "🔍 Navigating to category..." + RESET);

	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(category).click().perform();

	    Common.waitForElement(2);

	    List<WebElement> products = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                    By.xpath("//div[@class='prod_listing_card']")
	            )
	    );

	    Assert.assertTrue("❌ No products found", products.size() > 0);

	    Collections.shuffle(products);
	    WebElement product = products.get(0);

	    String plpName = product.findElement(
	            By.xpath(".//a[@class='product_list_name']")
	    ).getText().trim();

	    String plpPrice = product.findElement(
	            By.xpath(".//span[@class='product_discounted_price']")
	    ).getText().trim();

	    // ---------------- OPTIONAL DISCOUNT (PLP) ----------------
	    String plpDiscount = "";
	    List<WebElement> plpDiscountEle = product.findElements(
	            By.xpath(".//span[@class='product_discounted_percentage']")
	    );

	    if (plpDiscountEle.size() > 0) {
	        plpDiscount = plpDiscountEle.get(0).getText().trim();
	    } else {
	        System.out.println(YELLOW + "⚠ No discount available on PLP" + RESET);
	    }

	    System.out.println(CYAN + "\n🛍 PLP Product Details:" + RESET);
	    System.out.println(GREEN + "✔ Name     : " + plpName + RESET);
	    System.out.println(GREEN + "✔ Price    : " + plpPrice + RESET);
	    System.out.println(GREEN + "✔ Discount : " + (plpDiscount.isEmpty() ? "N/A" : plpDiscount) + RESET);

	    product.findElement(By.xpath(".//a")).click();

	    WebElement pdpName = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//h3[@class='prod_name']")
	            )
	    );

	    WebElement pdpPrice = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//div[@class='prod_current_price']")
	            )
	    );

	    // ---------------- OPTIONAL DISCOUNT (PDP) ----------------
	    List<WebElement> pdpDiscountEle = driver.findElements(
	            By.xpath("//div[@class='prod_discount_percentage']")
	    );

	    String pdpDiscountText = "";

	    if (pdpDiscountEle.size() > 0) {
	        pdpDiscountText = pdpDiscountEle.get(0).getText().trim();
	    } else {
	        System.out.println(YELLOW + "⚠ No discount available on PDP" + RESET);
	    }

	    String pdpNameText = pdpName.getText().trim();
	    String pdpPriceText = pdpPrice.getText().trim();

	    System.out.println(CYAN + "\n📄 PDP Product Details:" + RESET);
	    System.out.println(GREEN + "✔ Name     : " + pdpNameText + RESET);
	    System.out.println(GREEN + "✔ Price    : " + pdpPriceText + RESET);
	    System.out.println(GREEN + "✔ Discount : " + (pdpDiscountText.isEmpty() ? "N/A" : pdpDiscountText) + RESET);

	    // ---------------- ASSERTIONS ----------------

	    Assert.assertEquals(
	            RED + "❌ Price mismatch!" + RESET,
	            plpPrice,
	            pdpPriceText
	    );

	    // ✅ Only validate discount IF present in both
	    if (!plpDiscount.isEmpty() && !pdpDiscountText.isEmpty()) {

	        Assert.assertEquals(
	                RED + "❌ Discount mismatch!" + RESET,
	                plpDiscount,
	                pdpDiscountText
	        );

	        System.out.println(GREEN + "✅ Discount matched successfully" + RESET);

	    } else {
	        System.out.println(YELLOW + "⚠ Discount not available → Skipping validation" + RESET);
	    }

	    System.out.println(GREEN + "\n✅ Product price validation completed successfully!" + RESET);
	}

	public void discountPercentageCalculation() {

		RandomProduct();
		Common.waitForElement(1);

		String PricesText = "";
		if (!promotionalPriceElement.isEmpty()) {
			PricesText = promotionalPriceElement.get(0).getText().replaceAll("[^0-9]", "");
		} else {
			PricesText = normalPricePDP.getText().replaceAll("[^0-9]", "");
		}

		String actualPricesText = actualPrice.getText().replaceAll("[^0-9]", "");
		String discountPercentText = discountPercentage.getText().replaceAll("[^0-9]", "");

		if (PricesText.isEmpty() || actualPricesText.isEmpty() || discountPercentText.isEmpty()) {
			System.out.println("⚠️ One or more price values are missing or unreadable.");
			return;
		}

		int PriceValue = Integer.parseInt(PricesText);
		int atualPriceValue = Integer.parseInt(actualPricesText);
		int displayedDiscountPercent = Integer.parseInt(discountPercentText);

		int calculatedDiscountPercent = Math.round(((atualPriceValue - PriceValue) * 100.0f) / atualPriceValue);

		System.out.println("-------------------------------------");
		System.out.println("Product Discounted Percentage Summary");
		System.out.println("-------------------------------------");
		System.out.println("Product Actual Price     : " + atualPriceValue);
		System.out.println("Product Current Price    : " + PriceValue);
		System.out.println("Expected Discount %     : " + displayedDiscountPercent);
		System.out.println("Actual Discount %    : " + calculatedDiscountPercent);

		if (displayedDiscountPercent == calculatedDiscountPercent) {
			System.out.println("✅ Discount % is correct.");
		} else {
			System.out.println("❌ Mismatch in Discount %. Please verify.");
		}
	}

	public void productImageChange() throws InterruptedException {

	    String GREEN  = "\u001B[32m";
	    String RESET  = "\u001B[0m";
	    String CYAN   = "\u001B[36m";
	    String BLUE   = "\u001B[34m";
	    String RED    = "\u001B[31m";

	    System.out.println(CYAN + "────────────────────────────────────────────" + RESET);

	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

	    click(zlaataIndiaShopButton);
	    
	    Common.waitForElement(10);
	    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Actions actions = new Actions(driver);

	    System.out.println(BLUE + "🔍 Navigating to category..." + RESET);

	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(category).click().perform();
	    Common.waitForElement(2);
	    // Select random product
	    List<WebElement> products = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                    By.xpath("//div[@class='prod_listing_card']")
	            )
	    );

	    Assert.assertTrue("❌ No products found", products.size() > 0);

	    Collections.shuffle(products);
	    products.get(0).click();

	    // Arrow locators
	    By nextArrow = By.xpath("(//div[contains(@class,'swiper-button-next')])[1]");
	    By backArrow = By.xpath("(//div[contains(@class,'swiper-button-prev')])[1]");

	    WebElement nextBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(nextArrow));
	    WebElement backBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(backArrow));

	    // ✅ Initial State
	    System.out.println(CYAN + "📌 Verifying initial image state..." + RESET);

	    Assert.assertEquals(
	            "❌ Back arrow should be disabled initially",
	            "true",
	            backBtn.getAttribute("aria-disabled")
	    );

	    System.out.println(GREEN + "✅ Back arrow disabled initially" + RESET);

	    // ▶️ Click Next Until Disabled
	    int nextCount = 0;
	    while (nextBtn.getAttribute("aria-disabled").equals("false")) {
	        nextBtn.click();
	        Thread.sleep(800);
	        nextCount++;
	    }

	    System.out.println(GREEN + "➡️ Reached last image after " + nextCount + " clicks" + RESET);

	    // ◀️ Click Back Until Disabled
	    int backCount = 0;
	    while (backBtn.getAttribute("aria-disabled").equals("false")) {
	        backBtn.click();
	        Thread.sleep(800);
	        backCount++;
	    }

	    System.out.println(GREEN + "⬅️ Returned to first image after " + backCount + " clicks" + RESET);

	    // ✅ Final validation
	    Assert.assertEquals(
	            "❌ Back arrow should be disabled again",
	            "true",
	            backBtn.getAttribute("aria-disabled")
	    );

	    System.out.println(GREEN + "✅ Image slider navigation verified successfully!" + RESET);
	}


	public int getImageCount() {
		return productImages.size();
	}

	public boolean isBackArrowEnabled() {
		return !driver.findElements(backArrowEnabled).isEmpty();
	}

	public boolean isBackArrowDisabled() {
		return !driver.findElements(backArrowDisabled).isEmpty();
	}

	public boolean isNextArrowEnabled() {
		try {
			return nextArrow.isDisplayed() && nextArrow.isEnabled() && 
					!nextArrow.getAttribute("class").contains("disabled");
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void clickNextArrow() {
		if (isNextArrowEnabled()) {
			nextArrow.click();
		}
	}

	public void clickBackArrow() {
		List<WebElement> backArrowElements = driver.findElements(backArrowEnabled);
		if (!backArrowElements.isEmpty()) {
			backArrowElements.get(0).click();
		}
	}
//	public void wishList() {
//
//	    String GREEN = "\u001B[32m";
//	    String RED   = "\u001B[31m";
//	    String CYAN  = "\u001B[36m";
//	    String BLUE  = "\u001B[34m";
//	    String RESET = "\u001B[0m";
//
//	    System.out.println(CYAN + "────────────────────────────────────────────" + RESET);
//
//	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//	    
//	    
//	    
//	    click(zlaataIndiaShopButton);
//	    
//	    click(wishListButton);
//	    
//	    click(zlaataIndiaShopButton);
//	    click(wishListButton);
//
//	    while (!removeProductFromWishListPage.isEmpty()) {
//	        removeProductFromWishListPage.get(0).click();
//	    }
//
//	 
//	 
//	    
//
//	    
//	    
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//	    Actions actions = new Actions(driver);
//
//	    System.out.println(BLUE + "🔍 Navigating to category..." + RESET);
//
//	    actions.moveToElement(shopMenu).perform();
//	    actions.moveToElement(category).click().perform();
//	    Common.waitForElement(2);
//	    // Pick random product
//	    List<WebElement> products = wait.until(
//	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
//	                    By.xpath("//div[@class='prod_listing_card']")
//	            )
//	    );
//
//	    Assert.assertTrue("❌ No products found", products.size() > 0);
//	    Collections.shuffle(products);
//	    products.get(0).click();
//
//	    // Wishlist button
//	    WebElement wishlistBtn = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("(//div[contains(@class,'prod_wishlist_btn')])[2]")
//	            )
//	    );
//
//	    String classBefore = wishlistBtn.getAttribute("class");
//
//	    System.out.println(CYAN + "💡 Wishlist class before: " + classBefore + RESET);
//
//	    // ✅ If already liked → do nothing
//	    if (classBefore.contains("liked")) {
//	        System.out.println(GREEN + "❤️ Already added to wishlist. No action needed." + RESET);
//	    }
//	    else {
//	        System.out.println(BLUE + "🤍 Not in wishlist → Clicking..." + RESET);
//
//	        ((JavascriptExecutor) driver)
//	                .executeScript("arguments[0].click();", wishlistBtn);
//
//	        // Wait until liked class appears
//	        wait.until(ExpectedConditions.attributeContains(
//	                wishlistBtn, "class", "liked"
//	        ));
//
//	        String classAfter = wishlistBtn.getAttribute("class");
//
//	        Assert.assertTrue(
//	                RED + "❌ Wishlist not added!",
//	                classAfter.contains("liked")
//	        );
//
//	        System.out.println(GREEN + "❤️ Wishlist added successfully!" + RESET);
//	    }
//	}
	
//	public void wishList() {
//
//	    String GREEN = "\u001B[32m";
//	    String RED   = "\u001B[31m";
//	    String CYAN  = "\u001B[36m";
//	    String BLUE  = "\u001B[34m";
//	    String RESET = "\u001B[0m";
//
//	    System.out.println(CYAN + "────────────────────────────────────────────" + RESET);
//
//	    driver.get(FileReaderManager.getInstance()
//	            .getConfigReader()
//	            .getApplicationUrl());
//
//	    // Navigate to Wishlist
//	    click(zlaataIndiaShopButton);
//	    click(wishListButton);
//
//	    Common.waitForElement(2);
//
//	    // Remove all existing wishlist products
//	    System.out.println(BLUE + "🗑️ Removing existing wishlist products..." + RESET);
//
//	    while (!removeProductFromWishListPage.isEmpty()) {
//	        removeProductFromWishListPage.get(0).click();
//	        Common.waitForElement(1);
//	    }
//
//	    System.out.println(
//	            GREEN + "✅ Existing wishlist products removed." + RESET
//	    );
//
//	    WebDriverWait wait = new WebDriverWait(
//	            driver,
//	            Duration.ofSeconds(15)
//	    );
//
//	    Actions actions = new Actions(driver);
//
//	    // Navigate to Category
//	    System.out.println(
//	            BLUE + "🔍 Navigating to category..." + RESET
//	    );
//
//	    actions.moveToElement(shopMenu).perform();
//	    actions.moveToElement(category).click().perform();
//
//	    Common.waitForElement(2);
//
//	    // Pick random product
//	    List<WebElement> products = wait.until(
//	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
//	                    By.xpath("//div[@class='prod_listing_card']")
//	            )
//	    );
//
//	    Assert.assertTrue(
//	            RED + "❌ No products found",
//	            products.size() > 0
//	    );
//
//	    Collections.shuffle(products);
//
//	    products.get(0).click();
//
//	    Common.waitForElement(2);
//
//	    // Capture Product Name
//	    WebElement productNameElement = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//h3[@class='prod_name']")
//	            )
//	    );
//
//	    String productName = productNameElement.getText().trim();
//
//	    System.out.println(
//	            CYAN + "🛍️ Selected Product: "
//	                    + productName
//	                    + RESET
//	    );
//
//	    // Wishlist button
//	    WebElement wishlistBtn = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("(//div[contains(@class,'prod_wishlist_btn')])[2]")
//	            )
//	    );
//
//	    String classBefore = wishlistBtn.getAttribute("class");
//
//	    System.out.println(
//	            CYAN + "💡 Wishlist class before: "
//	                    + classBefore
//	                    + RESET
//	    );
//
//	    // Add product to Wishlist
//	    if (!classBefore.contains("liked")) {
//
//	        System.out.println(
//	                BLUE + "🤍 Adding product to Wishlist..." + RESET
//	        );
//
//	        ((JavascriptExecutor) driver)
//	                .executeScript(
//	                        "arguments[0].click();",
//	                        wishlistBtn
//	                );
//
//	        // Wait until liked class appears
//	        wait.until(
//	                ExpectedConditions.attributeContains(
//	                        wishlistBtn,
//	                        "class",
//	                        "liked"
//	                )
//	        );
//
//	        String classAfter = wishlistBtn.getAttribute("class");
//
//	        Assert.assertTrue(
//	                RED + "❌ Wishlist not added!",
//	                classAfter.contains("liked")
//	        );
//
//	        System.out.println(
//	                GREEN + "❤️ Wishlist added successfully!" + RESET
//	        );
//
//	    } else {
//
//	        System.out.println(
//	                GREEN + "❤️ Product is already in Wishlist." + RESET
//	        );
//	    }
//
//	    // Navigate to Wishlist page
//	    System.out.println(
//	            BLUE + "🔍 Navigating to Wishlist page..." + RESET
//	    );
//
//	    click(wishListButton);
//
//	    Common.waitForElement(2);
//
//	    WebElement wishlistProduct = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//a[@class='product_list_name']")
//	            )
//	    );
//
//	    String wishlistProduct1 = wishlistProduct.getText().trim();
//
//	    Assert.assertEquals(
//	            productName,
//	            wishlistProduct1
//	    );
//
//	    System.out.println(
//	            GREEN + "✅ Product name verified in Wishlist: "
//	            + wishlistProduct1 + RESET
//	    );
//	}
//	
	
	public void wishList() {

	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String CYAN  = "\u001B[36m";
	    String BLUE  = "\u001B[34m";
	    String RESET = "\u001B[0m";

	    System.out.println(
	            CYAN + "────────────────────────────────────────────" + RESET
	    );

	    driver.get(
	            FileReaderManager.getInstance()
	                    .getConfigReader()
	                    .getApplicationUrl()
	    );

	    // Navigate to Wishlist
	    click(zlaataIndiaShopButton);
	    
	    Common.waitForElement(10);

	    click(wishListButton);
	    Common.waitForElement(2);

	    // Remove all existing wishlist products
	    System.out.println(
	            BLUE + "🗑️ Removing existing wishlist products..." + RESET
	    );

	    while (!removeProductFromWishListPage.isEmpty()) {

	        removeProductFromWishListPage.get(0).click();

	        Common.waitForElement(1);
	    }

	    System.out.println(
	            GREEN + "✅ Existing wishlist products removed." + RESET
	    );

	    WebDriverWait wait = new WebDriverWait(
	            driver,
	            Duration.ofSeconds(15)
	    );

	    Actions actions = new Actions(driver);

	    // Navigate to Category
	    System.out.println(
	            BLUE + "🔍 Navigating to category..." + RESET
	    );

	    actions.moveToElement(shopMenu).perform();

	    actions.moveToElement(category).click().perform();

	    Common.waitForElement(2);

	    // Pick random product
	    List<WebElement> products = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                    By.xpath("//div[@class='prod_listing_card']")
	            )
	    );

	    Assert.assertTrue(
	            RED + "❌ No products found",
	            products.size() > 0
	    );

	    Collections.shuffle(products);

	    products.get(0).click();

	    Common.waitForElement(2);

	    // Capture Product Name
	    WebElement productNameElement = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//h3[@class='prod_name']")
	            )
	    );

	    String productName = productNameElement.getText().trim();

	    System.out.println(
	            CYAN + "🛍️ Selected Product: "
	                    + productName
	                    + RESET
	    );

	    // Wishlist button
	    WebElement wishlistBtn = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath(
	                            "(//div[contains(@class,'prod_wishlist_btn')])[2]"
	                    )
	            )
	    );

	    String classBefore = wishlistBtn.getAttribute("class");

	    System.out.println(
	            CYAN + "💡 Wishlist class before: "
	                    + classBefore
	                    + RESET
	    );

	    // Add product to Wishlist
	    if (!classBefore.contains("liked")) {

	        System.out.println(
	                BLUE + "🤍 Adding product to Wishlist..." + RESET
	        );

	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].click();",
	                wishlistBtn
	        );

	        // Wait until liked class appears
	        wait.until(
	                ExpectedConditions.attributeContains(
	                        wishlistBtn,
	                        "class",
	                        "liked"
	                )
	        );

	        String classAfter = wishlistBtn.getAttribute("class");

	        Assert.assertTrue(
	                RED + "❌ Wishlist not added!",
	                classAfter.contains("liked")
	        );

	        System.out.println(
	                GREEN + "❤️ Wishlist added successfully!" + RESET
	        );

	    } else {

	        System.out.println(
	                GREEN + "❤️ Product is already in Wishlist." + RESET
	        );
	    }

	    // Navigate to Wishlist page
	    System.out.println(
	            BLUE + "🔍 Navigating to Wishlist page..." + RESET
	    );

	    click(wishListButton);

	    Common.waitForElement(2);

	    // Get Wishlist Product Name
	    WebElement wishlistProduct = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//a[@class='product_list_name']")
	            )
	    );

	    String wishlistProduct1 = wishlistProduct.getText().trim();

	    System.out.println(
	            CYAN + "🛍️ Wishlist Product: "
	                    + wishlistProduct1
	                    + RESET
	    );

	    // Verify Product Name - Case Insensitive
	    Assert.assertTrue(
	            RED + "❌ Product name mismatch! Expected: "
	                    + productName
	                    + " | Actual: "
	                    + wishlistProduct1,
	            productName.equalsIgnoreCase(wishlistProduct1)
	    );

	    System.out.println(
	            GREEN + "✅ Product name verified in Wishlist: "
	                    + wishlistProduct1
	                    + RESET
	    );

	    System.out.println(
	            GREEN + "❤️ Wishlist functionality verified successfully!"
	                    + RESET
	    );
	}

	public void verifyBestPriceCalculation() {
		LoginPage login = new LoginPage(driver);
		login.userLogin();
		Common.waitForElement(3);
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu);
		actions.moveToElement(shopMenu).moveToElement(category).click().build().perform();
		Common.waitForElement(2);
		// Step 2: Sort by "Price: Low to High"
		click(sortBy);
		Common.waitForElement(1);
		actions.moveToElement(sortByPriceLowtoHigh).click().build().perform();
		Common.waitForElement(2);
		// Step 3: Add lowest priced product
		List<WebElement> allProducts = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
		if (allProducts.isEmpty()) {
			System.out.println(":x: No products found.");
			return;
		}
		WebElement lowestProduct = allProducts.get(0);
		String currentProductName = lowestProduct.findElement(By.xpath(".//h2[@class='product_list_cards_heading']")).getText().trim().toLowerCase();
		System.out.println(":shopping_trolley: Lowest Product Selected: " + currentProductName);
		actions.moveToElement(lowestProduct).click().build().perform();
		Common.waitForElement(2);
		// Step 4: Validate PDP product name
		String pdpProductName = productName.getText().trim().toLowerCase();
		if (!pdpProductName.contains(currentProductName)) {
			System.out.println(":x: Product mismatch between listing and detail page.");
			return;
		}
		// Step 5: Get promotional or normal price
		double price = 0;
		try {
			WebElement promoPrice = driver.findElement(By.xpath("//span[@class='product_list_cards_actual_price_txt']"));
			price = Double.parseDouble(promoPrice.getText().replace("₹", "").replace(",", "").trim());
			System.out.println(":moneybag: Promotional Product Price: ₹" + price);
		} catch (NoSuchElementException e1) {
			WebElement regularPrice = driver.findElement(By.xpath("//span[@class='product_list_cards_actual_price_txt']"));
			price = Double.parseDouble(regularPrice.getText().replace("₹", "").replace(",", "").trim());
			System.out.println(":moneybag: Regular Product Price: ₹" + price);
		}
		// Step 6: Add product to cart
		click(addCartButton);
		Common.waitForElement(2);
		click(bagIcon);
		Common.waitForElement(2);
		click(buyNowButton);
		// Step 7: Expand all coupons if more than 3
		//			List<WebElement> availableCoupons = driver.findElements(By.xpath("//div[@class='available_offer_list']"));
		//			WebElement showMoreOffers = driver.findElement(By.xpath("//button[@class='view_more_coupon_btn Cls_viewmore']"));
		//			if (showMoreOffers.isDisplayed()) {
		//				try {
		//					
		//					click(showMoreOffers);
		//					Common.waitForElement(2);
		//				} catch (Exception e) {
		//					System.out.println(":warning: Unable to click 'Available Offers' – maybe already expanded.");
		//				}
		//			}
		List<WebElement> offerButtons = driver.findElements(By.xpath("//button[@class='view_more_coupon_btn Cls_viewmore']"));

		if (!offerButtons.isEmpty()) {
			WebElement showMoreOffers = offerButtons.get(0);

			if (showMoreOffers.isDisplayed()) {
				try {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", showMoreOffers);
					Common.waitForElement(1);

					((JavascriptExecutor) driver).executeScript("arguments[0].click();", showMoreOffers); // safe click
					System.out.println("✅ Clicked on 'View More Offers' button.");
					Common.waitForElement(2);
				} catch (Exception e) {
					System.out.println("⚠️ Exception while clicking 'View More Offers': " + e.getMessage());
				}
			} else {
				System.out.println("⚠️ Button found but not visible.");
			}
		} else {
			// 👉 Else block when button is not in DOM at all
			System.out.println("ℹ️ 'View More Offers' button not present – skipping click.");

			// You can write fallback logic here if needed:
			// e.g., check coupons, log, or continue with test
		}
		// Step 8: Try applying all coupons and print validation
		List<WebElement> allCouponButtons = driver.findElements(By.xpath("//button[@class='offer_list_item_apply_btn Cls_apply_coupon']"));
		List<WebElement> couponCodes = driver.findElements(By.xpath("//div[@class='available_offer_card']//div[@class='offer_list_item_heading']"));
		System.out.println(":clipboard: Applying all coupons on low price product...");
		for (int i = 0; i < couponCodes.size(); i++) {
			String code = couponCodes.get(i).getText().trim();
			click(allCouponButtons.get(i));
			Common.waitForElement(2);
			String validationMsg = "";
			try {
				validationMsg = driver.findElement(By.xpath("//p[contains(@class,'coupon_apply_msg')]")).getText();
			} catch (Exception ignored) {}
			System.out.println(":bookmark: Coupon: " + code + " → Validation: " + validationMsg);
			// Remove coupon if apply was successful
			try {
				WebElement removeCoupon = driver.findElement(By.xpath("//button[@class='coupon_apply_btn Cls_coupon_apply_rmv_btn']"));
				if (removeCoupon.isDisplayed()) {
					removeCoupon.click();
					Common.waitForElement(1);
				}
			} catch (Exception e) {
				// Ignore if remove not present
			}
		}
		// Step 9: Go back and sort "Price: High to Low"
		driver.navigate().back();
		driver.navigate().back();
		Common.waitForElement(2);
		click(sortBy);
		actions.moveToElement(sortByPriceHightoLow).click().build().perform();
		Common.waitForElement(2);
		// Step 10: Pick product > ₹999
		List<WebElement> highPriceProducts = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
		for (WebElement prod : highPriceProducts) {
			try {
				double highPrice = Double.parseDouble(prod.findElement(By.xpath(".//span[@class='product_list_cards_actual_price_txt']")).getText().replace("₹", "").replace(",", "").trim());
				if (highPrice > 999) {
					actions.moveToElement(prod).click().build().perform();
					Common.waitForElement(2);
					break;
				}
			} catch (Exception ignored) {}
		}
		// Step 11: Get high-price product actual price
		double highPrice = 0;
		try {
			highPrice = Double.parseDouble(driver.findElement(By.xpath("//div[@class='prod_current_price']")).getText().replace("₹", "").replace(",", "").trim());
		} catch (Exception e) {
			highPrice = Double.parseDouble(driver.findElement(By.xpath("//span[@class='product_list_cards_actual_price_txt']")).getText().replace("₹", "").replace(",", "").trim());
		}
		System.out.println(":package: High Price Product: ₹" + highPrice);
		// Step 12: Validate Best Price logic
		System.out.println(":receipt: Validating Best Price Calculation...");
		List<WebElement> couponCodeElements = driver.findElements(By.xpath("//span[@class='prod_bp_coupen']"));
		List<WebElement> bestPriceElements = driver.findElements(By.xpath("//span[@class='prod_bp_value']"));
		for (int i = 0; i < couponCodeElements.size(); i++) {
			String coupon = couponCodeElements.get(i).getText().trim();
			double bestPrice = Double.parseDouble(bestPriceElements.get(i).getText().replace("₹", "").replace(",", "").trim());
			double expectedPrice = highPrice;
			if (coupon.equals("THANKU100")) {
				if (highPrice > 999) expectedPrice -= 100;
			} else if (coupon.equals("FIRSTBUY200")) {
				if (highPrice > 1999) expectedPrice -= 200;
			} else if (coupon.equals("GRAB300")) {
				expectedPrice -= 300;
			} else if (coupon.equals("GRAB500")) {
				expectedPrice -= 500;
			} else if (coupon.contains("%")) {
				try {
					double percent = Double.parseDouble(coupon.replace("%", ""));
					expectedPrice -= (highPrice * percent / 100);
				} catch (Exception ignored) {}
			}
			expectedPrice = Math.round(expectedPrice);
			System.out.println("\nCoupon: " + coupon);
			System.out.println("Expected Best Price: ₹" + expectedPrice + ", Actual: ₹" + bestPrice);
			if ((int) expectedPrice == (int) bestPrice) {
				System.out.println(":white_tick: Best price matched.");
			} else {
				System.out.println(":x: Mismatch → Expected: ₹" + expectedPrice + ", Actual: ₹" + bestPrice);
			}
		}
	}

	public void verifyColoSelectionPDP() throws InterruptedException {
		String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String CYAN  = "\u001B[36m";
	    String BLUE  = "\u001B[34m";
	    String RESET = "\u001B[0m";

	    System.out.println(CYAN + "────────────────────────────────────────────" + RESET);

	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    click(zlaataIndiaShopButton);
	    
	    Common.waitForElement(10);
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Actions actions = new Actions(driver);
	    Random random = new Random();
	    System.out.println(BLUE + "🔍 Navigating to category..." + RESET);

	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(randomcategory).click().perform();

	    System.out.println("🔍 Searching for multi-color product...");
	    Common.waitForElement(2);
	    // 1️⃣ Get all product cards
	    WebElement product = driver.findElement(
	    	    By.xpath("(//div[@class='prod_listing_card'][.//span[contains(@class,'prod_swatch_color')][2]]//a[contains(@class,'prod_listing_img')])[1]")
	    	);

	    	product.click();
//	    List<WebElement> allProducts = driver.findElements(
//	            By.xpath("//div[@class='prod_listing_card']")
//	    );
//
//	    System.out.println("🔍 Searching for multi-color product...");
//
//	    // ✅ Store only multi-color products
//	    List<WebElement> multiColorProducts = new ArrayList<>();
//
//	    for (WebElement product : allProducts) {
//	        List<WebElement> colors =
//	                product.findElements(By.xpath(".//div[contains(@class,'prod_swatch_wrap')]//span[contains(@class,'prod_swatch_color')]"));
//
//	        if (colors.size() > 1) {
//	            multiColorProducts.add(product);
//	        }
//	    }
//
//	    if (multiColorProducts.isEmpty()) {
//	        Assert.fail("❌ No multi-color products found");
//	    }
//	    // ✅ VERY IMPORTANT → SHUFFLE
//	    Collections.shuffle(multiColorProducts);
//	    WebElement selectedProduct = multiColorProducts.get(0);
//	    System.out.println("✅ Random multi-color product selected");
//	 // ✅ Click properly (important)
//	    WebElement productLink = selectedProduct.findElement(
//	            By.xpath(".//a[contains(@class,'prod_listing_img')]")
//	    );
//	    productLink.click();
	   // multiColorProducts.get(0).click();

	 // ---------------- WAIT FOR PDP ----------------
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(@class,'prod_color_options')]")));

	    // ---------------- GET COLORS ----------------
	    List<WebElement> colors = driver.findElements(
	            By.xpath("//div[contains(@class,'prod_color_list')]"));

	    System.out.println("🎨 Total colors: " + colors.size());

	    // ---------------- LOOP COLORS ----------------
	    for (int i = 0; i < colors.size(); i++) {

	        // re-fetch to avoid stale
	        colors = driver.findElements(
	                By.xpath("//div[contains(@class,'prod_color_list')]"));

	        WebElement color = colors.get(i);
	        String colorName = color.getAttribute("title").trim();

	        System.out.println("➡ Selecting: " + colorName);

	        js.executeScript("arguments[0].click();", color);
Thread.sleep(2000);
	        // ----- VERIFY SELECTED COLOR -----
	        String selectedColor = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//span[contains(@class,'Cls_prod_selected_color')]")))
	                .getText().trim();

	        Assert.assertEquals(
	                "Selected color mismatch",
	                colorName.toLowerCase(),
	                selectedColor.toLowerCase()
	        );
	        System.out.println("✅ Verified color: " + colorName);
	        // ----- VERIFY PRODUCT NAME -----
	        String productName = driver.findElement(
	                By.xpath("//h3[@class='prod_name']")).getText().toLowerCase();

//	        Assert.assertTrue(
//	                "Product name mismatch",
//	                productName.contains(colorName.toLowerCase())
//	        );

	        // ----- VERIFY URL -----
	        String url = driver.getCurrentUrl();
	        System.out.println("✅ Verified Product Name: " + productName);
	        Assert.assertTrue(
	                "URL mismatch",
	                url.contains(productName.toLowerCase().replace(" ", "-"))
	        );
	        System.out.println("✅ Verified URL: " + url);
	        
	    }
	    
	    verifyActiveColorAfterNavigate();

	    System.out.println("🎉 ALL COLORS VERIFIED SUCCESSFULLY");
	}
	
	
	
	
	public void verifyActiveColorAfterNavigate() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // Get all color items
	    List<WebElement> colors = driver.findElements(
	            By.xpath("//div[contains(@class,'prod_color_list')]"));

	    int totalColors = colors.size();
	    int loopCount = totalColors - 1;   // 🔥 IMPORTANT

	    System.out.println("🎨 Total colors found: " + totalColors);
	    System.out.println("🔁 Total validations: " + loopCount);

	    for (int i = 0; i < loopCount; i++) {

	        System.out.println("🔁 ROUND : " + (i + 1));

	        // ---------------- ACTIVE COLOR ----------------
	        WebElement activeColor = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//div[contains(@class,'prod_color_list') and contains(@class,'active')]")
	                ));

	        String activeColorName = activeColor.getAttribute("title").trim();
	        System.out.println("🎨 Active Color : " + activeColorName);

	        // ---------------- SELECTED COLOR TEXT ----------------
	        String selectedColor = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//span[contains(@class,'Cls_prod_selected_color')]")))
	                .getText().trim();

	        Assert.assertEquals(
	                "❌ Selected color mismatch",
	                activeColorName.toLowerCase(),
	                selectedColor.toLowerCase()
	        );

	        // ---------------- PRODUCT NAME ----------------
	        String productName = driver.findElement(
	                By.xpath("//h3[@class='prod_name']"))
	                .getText().toLowerCase();
//
//	        Assert.assertTrue(
//	                "❌ Product name mismatch",
//	                productName.contains(activeColorName.toLowerCase())
//	        );

	        // ---------------- URL CHECK ----------------
	        String url = driver.getCurrentUrl();
	        Assert.assertTrue(
	                "❌ URL does not contain product name",
	                url.contains(productName.replace(" ", "-"))
	        );

	        System.out.println("✅ Verified color: " + activeColorName);
Thread.sleep(2000);
	        // ---------------- BACK ----------------
	        driver.navigate().back();

	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//div[contains(@class,'prod_color_list')]")));
	    }

	    System.out.println("🎉 ALL COLOR VERIFICATIONS COMPLETED SUCCESSFULLY");
	}
	public void verifyColorDropdownToggle() throws InterruptedException {
	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String CYAN  = "\u001B[36m";
	    String BLUE  = "\u001B[34m";
	    String RESET = "\u001B[0m";

	    System.out.println(CYAN + "────────────────────────────────────────────" + RESET);

	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    click(zlaataIndiaShopButton);

	    Common.waitForElement(10);

	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Actions actions = new Actions(driver);

	    System.out.println(BLUE + "🔍 Navigating to category..." + RESET);

	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(category).click().perform();

	    
	    Common.waitForElement(5);
	    // Pick random product
	    List<WebElement> products = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                    By.xpath("//div[@class='prod_listing_card']")
	            )
	    );

	    Assert.assertTrue("❌ No products found", products.size() > 0);
	    Collections.shuffle(products);
	    products.get(0).click();

	    System.out.println("✅ Random product opened");

	    // ------------------- STEP 2: Locate Color Section -------------------
	    WebElement colorDropdownArrow = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//div[contains(@class,'prod_color_drop_arrow')]"))
	    );

	    WebElement colorOptions = driver.findElement(
	            By.xpath("//div[contains(@class,'prod_color_options')]"));

	    // ------------------- STEP 3: VERIFY OPTIONS VISIBLE INITIALLY -------------------
	    String displayBefore = colorOptions.getAttribute("style");
	    System.out.println("Before Click Display: " + displayBefore);

	    if (displayBefore.contains("none")) {
	        System.out.println("❌ Color options should be visible but are hidden");
	    } else {
	        System.out.println("✅ Color options are visible");
	    }

	    // ------------------- STEP 4: CLICK DROPDOWN (HIDE COLORS) -------------------
	    js.executeScript("arguments[0].click();", colorDropdownArrow);
Thread.sleep(1500);
	    wait.until(driver ->
	            colorOptions.getAttribute("style").contains("none")
	    );

	    String displayAfter = colorOptions.getAttribute("style");
	    System.out.println("After Click Display: " + displayAfter);

	    if (displayAfter.contains("none")) {
	        System.out.println("✅ Color options hidden successfully");
	    } else {
	        Assert.fail("❌ Color options did NOT hide");
	    }
	    Thread.sleep(1500);
	    // ------------------- STEP 5: CLICK AGAIN (OPTIONAL - SHOW AGAIN) -------------------
	    js.executeScript("arguments[0].click();", colorDropdownArrow);

	    wait.until(driver ->
	            !colorOptions.getAttribute("style").contains("none")
	    );
	    Thread.sleep(2000);
	    System.out.println("✅ Color options displayed again");
	}

	public void verifyMultiColorProductColorMatch() throws InterruptedException {
		
	    String CYAN  = "\u001B[36m";
	    String BLUE  = "\u001B[34m";
	    String RESET = "\u001B[0m";

	    System.out.println(CYAN + "────────────────────────────────────────────" + RESET);

	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    
	    click(zlaataIndiaShopButton);
	    Common.waitForElement(10);

	    
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Actions actions = new Actions(driver);
	    Random random = new Random();
	    System.out.println(BLUE + "🔍 Navigating to category..." + RESET);

	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(randomcategory).click().perform();

	    System.out.println("🔍 Searching for multi-color product...");

//	    // ✅ STEP 1: Directly pick multi-color product (FAST 🚀)
	    WebElement selectedProduct = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.xpath("(//div[@class='prod_listing_card'][.//span[contains(@class,'prod_swatch_color')][2]])[1]")
	    ));

	    System.out.println("✅ Multi-color product selected");

	    // ------------------ PLP COLORS ------------------
	    List<WebElement> plpColorEls = selectedProduct.findElements(
	            By.xpath(".//span[contains(@class,'prod_swatch_color')]")
	    );

	    List<String> plpColors = new ArrayList<>();

	    for (WebElement color : plpColorEls) {
	        String colorName = color.getAttribute("title").trim();   // ✅ correct way
	        plpColors.add(colorName);
	    }

	    System.out.println("🎨 PLP Colors → " + plpColors);

	    // ------------------ CLICK PRODUCT ------------------
	    WebElement productLink = selectedProduct.findElement(
	            By.xpath(".//a[contains(@class,'prod_listing_img')]")
	    );

	    js.executeScript("arguments[0].scrollIntoView({block:'center'});", productLink);
	    js.executeScript("arguments[0].click();", productLink);
	    
	    Common.waitForElement(3);

	    // ------------------ PDP COLORS ------------------
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(@class,'prod_color_list')]")
	    ));

	    List<WebElement> pdpColorEls = driver.findElements(
	            By.xpath("//div[contains(@class,'prod_color_list')]")
	    );

	    List<String> pdpColors = new ArrayList<>();

	    for (WebElement el : pdpColorEls) {
	        String colorName = el.getAttribute("title").trim();
	        pdpColors.add(colorName);
	    }

	    System.out.println("🎨 PDP Colors → " + pdpColors);

	    // ------------------ VALIDATION ------------------
	    Collections.sort(plpColors);
	    Collections.sort(pdpColors);

	    Assert.assertEquals(
	            "❌ Color mismatch\nPLP: " + plpColors + "\nPDP: " + pdpColors,
	            plpColors,
	            pdpColors
	    );

	    System.out.println("✅ COLOR MATCH SUCCESS 🎉");
	}
	
	

	public void sizeChart(Scenario scenario) {
		
		
driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

	    
	    click(zlaataIndiaShopButton);
	    
	    Common.waitForElement(10);

	    RandomProduct();
	    Common.waitForElement(1);
	    Actions actions = new Actions(driver);

	    try {
	        // Wait for color dropdown and size chart to be visible and clickable
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        if (colorDropDown.isDisplayed()) {
	            // Wait until size chart is clickable
	            wait.until(ExpectedConditions.elementToBeClickable(sizeChart));

	            // Perform the click action on the size chart
	            actions.moveToElement(sizeChart).click().build().perform();
	            Common.waitForElement(1);

	            // Capture and attach the initial screenshot
	            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	            scenario.attach(screenshot, "image/png", "Initial Screenshot");

	            Common.waitForElement(1);
	            if (sizeChartBottom.isDisplayed()) {
	                // Wait for the size chart bottom element to be clickable
	                wait.until(ExpectedConditions.elementToBeClickable(sizeChartBottom));

	                Common.waitForElement(2);
	                clickUsingJavaScript(sizeChartBottom);
	                Common.waitForElement(3);

	                // Attach bottom screenshot after interacting with size chart
	                scenario.attach(screenshot, "image/png", "Bottom Screenshot");
	                System.out.println("Screen Shot attached for Size chart");
	            }
	        } else {
	            scenario.log("⚠️ Size chart is null, screenshot not taken.");
	        }
	    } catch (Exception e) {
	        scenario.log("❌ Failed to capture screenshot : " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	
	
//	
//	public void verifySizeOption() {
//		
//driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//
//	    
//	    click(zlaataIndiaShopButton);
//
//		RandomProduct();
//	    // ---------- GET SIZE LISTS ----------
//	    List<WebElement> topSizes = driver.findElements(
//	            By.xpath("//div[contains(@class,'Cls_prod_size_list') and not(contains(@class,'bottom'))]")
//	    );
//
//	    List<WebElement> bottomSizes = driver.findElements(
//	            By.xpath("//div[contains(@class,'Cls_prod_size_list_bottom')]")
//	    );
//
//	    // ---------- BOTH TOP + BOTTOM ----------
//	    if (!topSizes.isEmpty() && !bottomSizes.isEmpty()) {
//
//	        System.out.println("✅ Product has TOP + BOTTOM sizes");
//
//	        verifySizeGroup(
//	                "TOP",
//	                topSizes,
//	                "(//span[contains(@class,'Cls_prod_selected_size')])[1]"
//	        );
//
//	        verifySizeGroup(
//	                "BOTTOM",
//	                bottomSizes,
//	                "//span[contains(@class,'Cls_prod_selected_size_bottom')]"
//	        );
//	    }
//
//	    // ---------- ONLY TOP ----------
//	    else if (!topSizes.isEmpty()) {
//
//	        System.out.println("✅ Product has ONLY TOP size");
//
//	        verifySizeGroup(
//	                "TOP",
//	                topSizes,
//	                "(//span[contains(@class,'Cls_prod_selected_size')])[1]"
//	        );
//	    }
//
//	    // ---------- ONLY BOTTOM ----------
//	    else if (!bottomSizes.isEmpty()) {
//
//	        System.out.println("✅ Product has ONLY BOTTOM size");
//
//	        verifySizeGroup(
//	                "BOTTOM",
//	                bottomSizes,
//	                "//span[contains(@class,'Cls_prod_selected_size_bottom')]"
//	        );
//	    }
//
//	    else {
//	        System.out.println("⚠️ No size options available for this product");
//	    }
//	}
//
//	private void verifySizeGroup(String type,
//            List<WebElement> sizeList,
//            String selectedSizeXpath) {
//
//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//System.out.println("🔹 Checking " + type + " sizes");
//
//for (WebElement size : sizeList) {
//
//if (!size.isDisplayed()) continue;
//
//String sizeName = size.getText().trim();
//
//// Click size
//size.click();
//Common.waitForElement(2);
//// Get selected size text
//WebElement selectedSize = wait.until(
//ExpectedConditions.visibilityOfElementLocated(
//       By.xpath(selectedSizeXpath)));
//
//String selectedText = selectedSize.getText().trim();
//
//// ✅ ASSERTION
//Assert.assertEquals(
//"❌ Selected size mismatch",
//sizeName,
//selectedText
//);
//
//System.out.println("✅ " + type + " size verified → " + sizeName);
//}
//}
//	public void verifySizeOptions() {
//		RandomProduct();
//		List<WebElement> topSizes = driver.findElements(By.xpath("//div[@class='prod_size_list Cls_prod_size_list']"));
//		List<WebElement> bottomSizes = driver.findElements(By.xpath("//div[@class='prod_size_list Cls_prod_size_list_bottom']"));
//
//		if (!topSizes.isEmpty() && !bottomSizes.isEmpty()) {
//			System.out.println("Product contains both top and bottom size");
//
//			String defaultTopSize = topSizes.get(0).getText();
//			System.out.println("Default selected top size: " + defaultTopSize);
//			System.out.println("default top sizes are: ");
//			for (WebElement topSize : topSizes) {
//				if (topSize.isEnabled()) {
//					topSize.click();
//					System.out.print(topSize.getText() + " ");
//				}
//			}
//
//			String defaultBottomSize = bottomSizes.get(0).getText();
//			System.out.println("\nDefault selected bottom size: " + defaultBottomSize);
//			System.out.println("default bottom sizes are: ");
//			for (WebElement bottomSize : bottomSizes) {
//				if (bottomSize.isEnabled()) {
//					bottomSize.click();
//					System.out.print(bottomSize.getText() + " ");
//				}
//			}
//		} else if (!topSizes.isEmpty()) {
//			System.out.println("Product contains only top size");
//
//			String defaultTopSize = topSizes.get(0).getText();
//			System.out.println("Default selected size: " + defaultTopSize);
//			System.out.println("Visible sizes are: ");
//			for (WebElement topSize : topSizes) {
//				if (topSize.isEnabled()) {
//					topSize.click();
//					System.out.print(topSize.getText() + " ");
//				}
//			}
//		} else if (!bottomSizes.isEmpty()) {
//			System.out.println("Product contains only bottom size");
//
//			String defaultBottomSize = bottomSizes.get(0).getText();
//			System.out.println("Default selected size: " + defaultBottomSize);
//			System.out.println("Visible sizes are: ");
//			for (WebElement bottomSize : bottomSizes) {
//				if (bottomSize.isEnabled()) {
//					bottomSize.click();
//					System.out.print(bottomSize.getText() + " ");
//				}
//			}
//		} else {
//			System.out.println("No sizes available");
//		}
//	}
	
	
	
//	public void verifySizeOption() {
//
//	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//	    click(zlaataIndiaShopButton);
//
//	    RandomProduct();
//	    
////	    WebElement pdpName = wait.until(
////	            ExpectedConditions.visibilityOfElementLocated(
////	                    By.xpath("//h4[@class='prod_name']")
////	            )
////	    );
////	    
////	    System.out.println(pdpName);
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//	    // ---------- TOP SIZES ----------
//	    List<WebElement> topSizes = driver.findElements(
//	            By.xpath("//div[contains(@class,'Cls_prod_size_name') and not(contains(@class,'bottom'))]")
//	    );
//
//	    // ---------- BOTTOM SIZES ----------
//	    List<WebElement> bottomSizes = driver.findElements(
//	            By.xpath("//div[contains(@class,'Cls_prod_size_name_bottom')]")
//	    );
//
//	    // ---------- BOTH TOP + BOTTOM ----------
//	    if (!topSizes.isEmpty() && !bottomSizes.isEmpty()) {
//
//	        System.out.println("✅ Product has TOP + BOTTOM sizes");
//
//	        verifySizeGroup(
//	                "TOP",
//	                "//div[contains(@class,'Cls_prod_size_name') and not(contains(@class,'bottom'))]",
//	                "//span[contains(@class,'Cls_prod_selected_size')]"
//	        );
//
//	        verifySizeGroup(
//	                "BOTTOM",
//	                "//div[contains(@class,'Cls_prod_size_name_bottom')]",
//	                "//span[contains(@class,'Cls_prod_selected_size_bottom')]"
//	        );
//	    }
//
//	    // ---------- ONLY TOP ----------
//	    else if (!topSizes.isEmpty()) {
//
//	        System.out.println("✅ Product has ONLY TOP sizes");
//
//	        verifySizeGroup(
//	                "TOP",
//	                "//div[contains(@class,'Cls_prod_size_name') and not(contains(@class,'bottom'))]",
//	                "//span[contains(@class,'Cls_prod_selected_size')]"
//	        );
//	    }
//
//	    // ---------- ONLY BOTTOM ----------
//	    else if (!bottomSizes.isEmpty()) {
//
//	        System.out.println("✅ Product has ONLY BOTTOM sizes");
//
//	        verifySizeGroup(
//	                "BOTTOM",
//	                "//div[contains(@class,'Cls_prod_size_name_bottom')]",
//	                "//span[contains(@class,'Cls_prod_selected_size_bottom')]"
//	        );
//	    }
//
//	    // ---------- NO SIZE ----------
//	    else {
//	        System.out.println("⚠️ No sizes available for this product");
//	    }
//	}
	public void verifySizeOption() {

	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    click(zlaataIndiaShopButton);
	    
	    Common.waitForElement(10);


	    RandomProduct();

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // ✅ GET PRODUCT NAME
	    WebElement pdpName = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//h3[@class='prod_name']")
	            )
	    );

	    String productName = pdpName.getText();
	    System.out.println("🛍️ Product Name: " + productName);

	    // ---------- TOP SIZES ----------
	    List<WebElement> topSizes = driver.findElements(
	            By.xpath("//div[contains(@class,'Cls_prod_size_name') and not(contains(@class,'bottom'))]")
	    );

	    // ---------- BOTTOM SIZES ----------
	    List<WebElement> bottomSizes = driver.findElements(
	            By.xpath("//div[contains(@class,'Cls_prod_size_name_bottom')]")
	    );

	    // ---------- BOTH TOP + BOTTOM ----------
	    if (!topSizes.isEmpty() && !bottomSizes.isEmpty()) {

	        System.out.println("✅ Product has TOP + BOTTOM sizes");

	        verifySizeGroup(
	                "TOP",
	                "//div[contains(@class,'Cls_prod_size_name') and not(contains(@class,'bottom'))]",
	                "//span[contains(@class,'Cls_prod_selected_size')]"
	        );

	        verifySizeGroup(
	                "BOTTOM",
	                "//div[contains(@class,'Cls_prod_size_name_bottom')]",
	                "//span[contains(@class,'Cls_prod_selected_size_bottom')]"
	        );
	    }

	    // ---------- ONLY TOP ----------
	    else if (!topSizes.isEmpty()) {

	        System.out.println("✅ Product has ONLY TOP sizes");

	        verifySizeGroup(
	                "TOP",
	                "//div[contains(@class,'Cls_prod_size_name') and not(contains(@class,'bottom'))]",
	                "//span[contains(@class,'Cls_prod_selected_size')]"
	        );
	    }

	    // ---------- ONLY BOTTOM ----------
	    else if (!bottomSizes.isEmpty()) {

	        System.out.println("✅ Product has ONLY BOTTOM sizes");

	        verifySizeGroup(
	                "BOTTOM",
	                "//div[contains(@class,'Cls_prod_size_name_bottom')]",
	                "//span[contains(@class,'Cls_prod_selected_size_bottom')]"
	        );
	    }

	    // ---------- NO SIZE ----------
	    else {
	        System.out.println("⚠️ No sizes available for this product");
	    }
	}
	private void verifySizeGroup(String type,
            String sizeXpath,
            String selectedSizeXpath) {

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

System.out.println("🔹 Checking " + type + " sizes");

List<WebElement> sizes = driver.findElements(By.xpath(sizeXpath));

for (int i = 0; i < sizes.size(); i++) {

// 🔄 Re-fetch to avoid stale element issue
sizes = driver.findElements(By.xpath(sizeXpath));
WebElement size = sizes.get(i);

// ❌ Skip disabled sizes
if (size.getAttribute("class").contains("disabled")) {
continue;
}

// ✅ Get clean size (NO '2left' issue)
String sizeName = size.getAttribute("data-value").trim();

// Click size
size.click();
Common.waitForElement(1);

// Get selected size
WebElement selectedSize = wait.until(
ExpectedConditions.visibilityOfElementLocated(By.xpath(selectedSizeXpath))
);

String selectedText = selectedSize.getText().trim();

// ✅ Assertion
Assert.assertEquals(
"❌ " + type + " size mismatch",
sizeName,
selectedText
);

System.out.println("✅ " + type + " size verified → " + sizeName);
}
}
	public void verifySizeOptions() {
		RandomProduct();
		List<WebElement> topSizes = driver.findElements(By.xpath("//div[@class='prod_size_list Cls_prod_size_list']"));
		List<WebElement> bottomSizes = driver.findElements(By.xpath("//div[@class='prod_size_list Cls_prod_size_list_bottom']"));

		if (!topSizes.isEmpty() && !bottomSizes.isEmpty()) {
			System.out.println("Product contains both top and bottom size");

			String defaultTopSize = topSizes.get(0).getText();
			System.out.println("Default selected top size: " + defaultTopSize);
			System.out.println("default top sizes are: ");
			for (WebElement topSize : topSizes) {
				if (topSize.isEnabled()) {
					topSize.click();
					System.out.print(topSize.getText() + " ");
				}
			}

			String defaultBottomSize = bottomSizes.get(0).getText();
			System.out.println("\nDefault selected bottom size: " + defaultBottomSize);
			System.out.println("default bottom sizes are: ");
			for (WebElement bottomSize : bottomSizes) {
				if (bottomSize.isEnabled()) {
					bottomSize.click();
					System.out.print(bottomSize.getText() + " ");
				}
			}
		} else if (!topSizes.isEmpty()) {
			System.out.println("Product contains only top size");

			String defaultTopSize = topSizes.get(0).getText();
			System.out.println("Default selected size: " + defaultTopSize);
			System.out.println("Visible sizes are: ");
			for (WebElement topSize : topSizes) {
				if (topSize.isEnabled()) {
					topSize.click();
					System.out.print(topSize.getText() + " ");
				}
			}
		} else if (!bottomSizes.isEmpty()) {
			System.out.println("Product contains only bottom size");

			String defaultBottomSize = bottomSizes.get(0).getText();
			System.out.println("Default selected size: " + defaultBottomSize);
			System.out.println("Visible sizes are: ");
			for (WebElement bottomSize : bottomSizes) {
				if (bottomSize.isEnabled()) {
					bottomSize.click();
					System.out.print(bottomSize.getText() + " ");
				}
			}
		} else {
			System.out.println("No sizes available");
		}
	}
	//	public void askUsAnything(Scenario scenario) {
	//		Actions action = new Actions(driver);
	//		LoginPage login = new LoginPage(driver);
	//		login.userLogin();
	//		Common.waitForElement(2);
	//		RandomProduct();
	//		try {
	//			if (askUsAnythings.isDisplayed()) {
	//				click(askUsAnythings);
	//				click(askUsAnythingsDescription);
	//				Common.waitForElement(5);
	//				type(askUsAnythingsDescription,Common.getValueFromTestDataMap("Description"));
	//				Common.waitForElement(2);
	//				action.moveToElement(askUsSend).build().perform();
	//				byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	//				scenario.attach(screenshot, "image/png", "Initial Screenshot");
	//
	//			}
	//			else {
	//				scenario.log("⚠️ Ask us anything is null, screenshot not taken.");
	//			}
	//		} catch (Exception e) {
	//			scenario.log("❌ Failed to capture screenshot : " + e.getMessage());
	//			e.printStackTrace();
	//		}
	//
	//
	//	}

	public void categoryName() {
		
		
driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

	    
	    click(zlaataIndiaShopButton);
	    
	    Common.waitForElement(10);


		RandomProduct();
		if (detailsPageCategoryName.isDisplayed()) {
			String categoryName = detailsPageCategoryName.getText();
			System.out.println("Category name is: " + categoryName);
		} else {
			System.out.println("Category name is not displayed");
		}

		if (productName.isDisplayed()) { 
			String productNameText = productName.getText(); 
			System.out.println("Product name is: " + productNameText); 
		} else {
			System.out.println("Product name is not displayed");
		}
	}


//
//	public void addToCartAndVerify() {
//		
//driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//
//	    
//	    click(zlaataIndiaShopButton);
//
//		
//RandomProduct();
//		
//		Common.waitForElement(2);
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//
//	    // ---------------- GET PRODUCT DETAILS ----------------
//	    String productName = driver.findElement(
//	            By.xpath("//h4[@class='prod_name']"))
//	            .getText().trim();
//
//	    String selectedColor = driver.findElement(
//	            By.xpath("//div[contains(@class,'prod_color_list') and contains(@class,'active')]"))
//	            .getAttribute("title").trim();
//
//	    String selectedSize = driver.findElement(
//	            By.xpath("//div[contains(@class,'prod_size_name') and contains(@class,'active')]"))
//	            .getText().trim();
//
//	    System.out.println("🛒 Product  : " + productName);
//	    System.out.println("🎨 Color    : " + selectedColor);
//	    System.out.println("📏 Size     : " + selectedSize);
//
//	    // ---------------- GET CART COUNT BEFORE ----------------
//	    WebElement cartCountEle = driver.findElement(
//	            By.xpath("//span[contains(@class,'Cls_cart_count_num')]"));
//
//	    // ---------- SAFE BEFORE COUNT ----------
//	    String beforeText = cartCountEle.getText().trim();
//	    int beforeCount = beforeText.isEmpty() ? 0 : Integer.parseInt(beforeText);
//
//	    System.out.println("🧮 Cart count before: " + beforeCount);
//
//	    // ---------- CLICK ADD TO CART ----------
//	    WebElement addToCartBtn = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//button[contains(@class,'Cls_Cart_Prod')]")));
//	    addToCartBtn.click();
//	    Common.waitForElement(2);
//	    // ---------- WAIT UNTIL COUNT CHANGES ----------
//	    wait.until(driver -> {
//	        String txt = cartCountEle.getText().trim();
//	        return !txt.isEmpty();
//	    });
//
//	    // ---------- AFTER COUNT ----------
//	    String afterText = cartCountEle.getText().trim();
//	    int afterCount = Integer.parseInt(afterText);
//
//	    System.out.println("🧮 Cart count after: " + afterCount);
//
//	    // ---------- ASSERT ----------
//	    Assert.assertEquals(
//	            "❌ Cart count not increased",
//	            beforeCount + 1,
//	            afterCount
//	    );
//
//	    System.out.println("✅ Cart count increased successfully");
//
//	    // ---------------- OPEN CART ----------------
//	    driver.findElement(By.xpath("//a[contains(@class,'Cls_cart_btn')]")).click();
//	    Common.waitForElement(2);
//
//	    // ---------------- VERIFY PRODUCT IN CART ----------------
//	    WebElement cartProduct = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//div[contains(@class,'cart_prod_card_wrpr')]")));
//
//	    String cartProductName = cartProduct.findElement(
//	            By.xpath(".//a[contains(@class,'cp_name')]"))
//	            .getText().trim();
//
//	    String cartColor = cartProduct.findElement(
//	            By.xpath(".//p[contains(@class,'cp_selected_color')]"))
//	            .getText().trim();
//
//	    String cartSize = cartProduct.findElement(
//	            By.xpath(".//div[contains(@class,'cp_selected_size')]//p"))
//	            .getText().trim();
//	    Common.waitForElement(2);
//	    // ---------------- ASSERTIONS ----------------
//	    Assert.assertEquals(
//	            "❌ Product name mismatch",
//	            productName.toLowerCase().trim(),
//	            cartProductName.toLowerCase().trim()
//	    );
//
//	    Assert.assertEquals("❌ Color mismatch",
//	            selectedColor, cartColor);
//
//	    Assert.assertEquals("❌ Size mismatch",
//	            selectedSize, cartSize);
//
//	    System.out.println("✅ PRODUCT VERIFIED SUCCESSFULLY IN CART");
//	}
	
	
	
	
	
	
	
	
	
	public void addToCartAndVerify() {

	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());

	   
	        // Open cart
	        driver.findElement(By.xpath("//button[@class='header_cta_btn Cls_cart_btn ']")).click();
	        Common.waitForElement(1);

	        // ✅ STEP 1: Check if cart is already empty
	        try {
	            if (driver.findElement(By.xpath("//h5[contains(text(),'Your bag is empty')]")).isDisplayed()) {
	                System.out.println("🛍️ Cart already empty. No delete action needed.");
	                return; // Stop method immediately
	            }
	        } catch (NoSuchElementException ignored) {
	            // Cart is NOT empty, proceed to delete
	        }

	        // ✅ STEP 2: Delete products one by one
	        while (true) {
	            try {
	                WebElement deleteBtn = driver.findElement(By.xpath("//div[@title='Delete']"));
	                deleteBtn.click();
	                System.out.println("🗑️ Product deleted");
	                Common.waitForElement(1); 
	            } catch (NoSuchElementException e) {
	                System.out.println("✅ No more products to delete.");
	                break;
	            } catch (Exception e) {
	                System.out.println("⚠️ Error while deleting: " + e.getMessage());
	                break;
	            }
	        }

	        // ✅ STEP 3: Final confirmation
	        try {
	            if (driver.findElement(By.xpath("//h5[contains(text(),'Your bag is empty')]")).isDisplayed()) {
	                System.out.println("🛍️ Cart is empty, Continue Shopping displayed.");
	            }
	        } catch (NoSuchElementException e) {
	            System.out.println("ℹ️ Bag is not empty message not found.");
	        }
	    


	    
	    RandomProduct();

	    Common.waitForElement(2);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // ---------------- GET PRODUCT DETAILS ----------------
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//h3[@class='prod_name']")));

	    String productName = driver.findElement(
	            By.xpath("//h3[@class='prod_name']"))
	            .getText().trim();

	    String selectedColor = driver.findElement(
	            By.xpath("//div[contains(@class,'prod_color_list') and contains(@class,'active')]"))
	            .getAttribute("title").trim();

	    String selectedSize = driver.findElement(
	            By.xpath("//div[contains(@class,'prod_size_name') and contains(@class,'active')]"))
	            .getText().trim();

	    System.out.println("🛒 Product  : " + productName);
	    System.out.println("🎨 Color    : " + selectedColor);
	    System.out.println("📏 Size     : " + selectedSize);

	    // ---------------- CART BUTTON LOCATOR ----------------
	    By cartBtnBy = By.xpath("//button[contains(@class,'Cls_cart_btn')]");

	    // ---------------- GET CART COUNT BEFORE ----------------
	    String beforeText = driver.findElement(cartBtnBy)
	            .getAttribute("data-cart-count");

	    int beforeCount = (beforeText == null || beforeText.isEmpty())
	            ? 0
	            : Integer.parseInt(beforeText);

	    System.out.println("🧮 Cart count before: " + beforeCount);

	    // ---------------- CLICK ADD TO CART ----------------
	    WebElement addToCartBtn = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[contains(@class,'Cls_Cart_Prod')]")));
	    addToCartBtn.click();

	    // ---------------- WAIT UNTIL COUNT UPDATES ----------------
	    wait.until(d -> {
	        String txt = d.findElement(cartBtnBy)
	                .getAttribute("data-cart-count");
	        return txt != null && !txt.isEmpty()
	                && Integer.parseInt(txt) > beforeCount;
	    });

	    // ---------------- GET UPDATED COUNT ----------------
	    int afterCount = Integer.parseInt(
	            driver.findElement(cartBtnBy)
	                    .getAttribute("data-cart-count")
	    );

	    System.out.println("🧮 Cart count after: " + afterCount);

	    // ---------------- ASSERT COUNT ----------------
	    Assert.assertEquals(
	            "❌ Cart count not increased",
	            beforeCount + 1,
	            afterCount
	    );

	    System.out.println("✅ Cart count increased successfully");
	    
	    Common.waitForElement(3);

	    // ---------------- OPEN CART ----------------
	    WebElement cartBtn = wait.until(
	            ExpectedConditions.elementToBeClickable(cartBtnBy));
	    cartBtn.click();

	    Common.waitForElement(2);

	    // ---------------- VERIFY PRODUCT IN CART ----------------
	    WebElement cartProduct = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//div[contains(@class,'cart_prod_card_wrpr')]")));

	    String cartProductName = cartProduct.findElement(
	            By.xpath(".//a[contains(@class,'cp_name')]"))
	            .getText().trim();

	    String cartColor = cartProduct.findElement(
	            By.xpath(".//p[contains(@class,'cp_selected_color')]"))
	            .getText().replace("Color:", "").trim();

	    String cartSize = cartProduct.findElement(
	            By.xpath(".//div[contains(@class,'cp_selected_size')]//p"))
	            .getText().trim();

	    Common.waitForElement(2);

	    // ---------------- ASSERTIONS ----------------
	    Assert.assertEquals(
	            "❌ Product name mismatch",
	            productName.toLowerCase().trim(),
	            cartProductName.toLowerCase().trim()
	    );

	    Assert.assertEquals(
	            "❌ Color mismatch",
	            selectedColor.toLowerCase(),
	            cartColor.toLowerCase()
	    );

	    Assert.assertEquals(
	            "❌ Size mismatch",
	            selectedSize.toLowerCase(),
	            cartSize.toLowerCase()
	    );

	    System.out.println("✅ PRODUCT VERIFIED SUCCESSFULLY IN CART");
	}
	public void buyNow(Scenario scenario) {
		Actions action = new Actions(driver);
		RandomProduct();
		Common.waitForElement(1);
		try {
			if (buyNowbutton.isDisplayed()) {
				action.moveToElement(buyNowbutton).click().build().perform();
				Common.waitForElement(2);
				byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				scenario.attach(screenshot, "image/png", "Initial Screenshot");

			}
			else {
				scenario.log("⚠️ Buy Now is null, screenshot not taken.");
			}
		} catch (Exception e) {
			scenario.log("❌ Failed to capture screenshot : " + e.getMessage());
			e.printStackTrace();
		}
	}
	public void buyNowBtn() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Actions action = new Actions(driver);
	    
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

	    
	   
	    

        // Open cart
        driver.findElement(By.xpath("//button[@class='header_cta_btn Cls_cart_btn ']")).click();
        Common.waitForElement(1);

        // ✅ STEP 1: Check if cart is already empty
        try {
            if (driver.findElement(By.xpath("//h5[contains(text(),'Your bag is empty')]")).isDisplayed()) {
                System.out.println("🛍️ Cart already empty. No delete action needed.");
                return; // Stop method immediately
            }
        } catch (NoSuchElementException ignored) {
            // Cart is NOT empty, proceed to delete
        }

        // ✅ STEP 2: Delete products one by one
        while (true) {
            try {
                WebElement deleteBtn = driver.findElement(By.xpath("//div[@title='Delete']"));
                deleteBtn.click();
                System.out.println("🗑️ Product deleted");
                Common.waitForElement(1); 
            } catch (NoSuchElementException e) {
                System.out.println("✅ No more products to delete.");
                break;
            } catch (Exception e) {
                System.out.println("⚠️ Error while deleting: " + e.getMessage());
                break;
            }
        }

        // ✅ STEP 3: Final confirmation
        try {
            if (driver.findElement(By.xpath("//h5[contains(text(),'Your bag is empty')]")).isDisplayed()) {
                System.out.println("🛍️ Cart is empty, Continue Shopping displayed.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("ℹ️ Bag is not empty message not found.");
        }
    

	    


	    try {
	        
	        RandomProduct();
	        Common.waitForElement(2);
	        // ---------------- GET PRODUCT DETAILS ----------------
		    String productName = driver.findElement(
		            By.xpath("//h3[@class='prod_name']"))
		            .getText().trim();

		    String selectedColor = driver.findElement(
		            By.xpath("//div[contains(@class,'prod_color_list') and contains(@class,'active')]"))
		            .getAttribute("title").trim();

		    String selectedSize = driver.findElement(
		            By.xpath("//div[contains(@class,'prod_size_name') and contains(@class,'active')]"))
		            .getText().trim();

		    System.out.println("🛒 Product  : " + productName);
		    System.out.println("🎨 Color    : " + selectedColor);
		    System.out.println("📏 Size     : " + selectedSize);
		    Common.waitForElement(2);
	        WebElement buyNowBtn = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.xpath("//button[contains(@class,'Cls_Buy_now_To_Cart')]")));

	        action.moveToElement(buyNowBtn).click().perform();
	        Common.waitForElement(3);
	        // ✅ Wait for redirection to cart page
	        wait.until(ExpectedConditions.urlContains("cart"));

	     // ---------------- VERIFY PRODUCT IN CART ----------------
		    WebElement cartProduct = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("//div[contains(@class,'cart_prod_card_wrpr')]")));

		    String cartProductName = cartProduct.findElement(
		            By.xpath(".//a[contains(@class,'cp_name')]"))
		            .getText().trim();

		    String cartColor = cartProduct.findElement(
		            By.xpath(".//p[contains(@class,'cp_selected_color')]"))
		            .getText().trim();

		    String cartSize = cartProduct.findElement(
		            By.xpath(".//div[contains(@class,'cp_selected_size')]//p"))
		            .getText().trim();
		    Common.waitForElement(2);
		    // ---------------- ASSERTIONS ----------------
		    Assert.assertEquals(
		            "❌ Product name mismatch",
		            productName.toLowerCase().trim(),
		            cartProductName.toLowerCase().trim()
		    );

		    Assert.assertEquals("❌ Color mismatch",
		            selectedColor, cartColor);

		    Assert.assertEquals("❌ Size mismatch",
		            selectedSize, cartSize);

		    System.out.println("✅ PRODUCT VERIFIED SUCCESSFULLY IN CART");

	        System.out.println("✅ Buy Now successful, product verified in cart");



	    } catch (Exception e) {
	        Assert.fail("Buy Now flow failed");
	    }
	}
	public void verifyPincode() {
		RandomProduct();
		Common.waitForElement(1);
		Random rand = new Random();
		int randomPincode = rand.nextInt(900000) + 100000; // generates a 6-digit random number
		String pincode = String.valueOf(randomPincode);

		pinCode.sendKeys(pincode);
		checkPincodeButton.click();

		try {
			Thread.sleep(2000); // wait for the response
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		if (deliveryDate.isDisplayed()) {
			System.out.println("Delivery date is displaying for pincode: " + pincode);
			System.out.println("Delivery date: " + deliveryDate.getText());
		} else if (invalidPincodeError.isDisplayed()) {
			System.out.println("Invalid pincode: " + pincode);
			System.out.println("Error message: " + invalidPincodeError.getText());
		} else {
			System.out.println("No response for pincode: " + pincode);
		}
	}

	public void tryAlongSection(Scenario scenario) {
		Actions action = new Actions(driver);
		RandomProduct();
		Common.waitForElement(1);
		action.moveToElement(tryAlongSection).build().perform();
		try {
			if (tryAlongSection.isDisplayed()) {
				scrollToElementUsingJSE(tryAlongSection);
				System.out.println("The try along section is displaying verify screen shot in report");
				byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				scenario.attach(screenshot, "image/png", "Initial Screenshot");

			}
			else {
				scenario.log("⚠️ Try along section not displaying , screenshot not taken.");
			}


		} catch (Exception e) {
			System.out.println("Caught an exception: " + e.getMessage());
		}


	}

	public void tryAlongProducts(Scenario scenario) {
		RandomProduct();
		Common.waitForElement(1);
		scrollUsingJSWindow();
		Common.waitForElement(2);
		if (!selectingOfcheckBox.isEmpty()) {
			for (WebElement checkBox : selectingOfcheckBox ) {
				if (checkBox.isEnabled()) {
					checkBox.click();
					Common.waitForElement(2);
					byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
					scenario.attach(screenshot, "image/png", "Initial Screenshot");
				}
				else {
					scrollUsingJSWindow();
					System.out.println("Try along section check box not clickable ");
					byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
					scenario.attach(screenshot, "image/png", "Initial Screenshot");

				}
			}

		}

	}
	//	public void quickViewIconTryAlong(Scenario scenario) {
	//		RandomProduct();
	//		Common.waitForElement(1);
	//		scrollUsingJSWindow();
	//		Common.waitForElement(2);
	//		Collections.shuffle(clickOnQuickViewButton);
	//		if (!clickOnQuickViewButton.isEmpty()) {
	//			WebElement randomIcon = clickOnQuickViewButton.get(0);
	//			click(randomIcon);
	//			System.out.println("Try along Quick view clicked");
	//			Common.waitForElement(2);
	//			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	//			scenario.attach(screenshot, "image/png", "Initial Screenshot");
	//		}
	//		else {
	//			System.out.println("Try along Quick view is not clickable");
	//		}
	//	}
	public void quickViewIconTryAlong(Scenario scenario) {
		RandomProduct(); // Optional: scroll to a random product
		Common.waitForElement(1);
		scrollUsingJSWindow();
		Common.waitForElement(5);

		try {
			if (productName.isDisplayed()) {
				String productNameText = productName.getText();
				System.out.println("Main Product Name: " + productNameText);
			} else {
				System.out.println("Product name element is not displayed.");
			}
		} catch (Exception e) {
			System.out.println("Error fetching main product name: " + e.getMessage());
		}

		for (int i = 0; i < tryAlongProducts.size(); i++) {
			try {
				System.out.println("Opening Try Along Product #" + (i + 1));
				WebElement tryProduct = tryAlongProducts.get(i);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", tryProduct);

				Common.waitForElement(3); // Wait for popup
				click(closeTheQuickViewPopup);
				Common.waitForElement(2); // Wait before next
			} catch (Exception e) {
				System.out.println("Error on Try Along product #" + (i + 1) + ": " + e.getMessage());
			}
		}
	}

	//	    Collections.shuffle(clickOnQuickViewButton);
	//
	//	    if (!clickOnQuickViewButton.isEmpty()) {
	//	        WebElement randomIcon = clickOnQuickViewButton.get(0);
	//
	//	        try {
	//	            // Wait until the element is clickable
	//	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	//	            wait.until(ExpectedConditions.elementToBeClickable(randomIcon));
	//
	//	            // Click using JavaScript to avoid interception
	//	            JavascriptExecutor js = (JavascriptExecutor) driver;
	//	            js.executeScript("arguments[0].click();", randomIcon);
	//
	//	            System.out.println("✅ Try along Quick View clicked");
	//
	//	            Common.waitForElement(2);
	//	            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	//	            scenario.attach(screenshot, "image/png", "Quick View Screenshot");
	//
	//	        } catch (Exception e) {
	//	            System.out.println("❌ Failed to click Try Along Quick View: " + e.getMessage());
	//	        }
	//
	//	    } else {
	//	        System.out.println("❌ Try along Quick View is not clickable or not found.");
	//	    }
	//	}

//	public void tryAlongQuickViewClose() {
//		RandomProduct();
//		Common.waitForElement(1);
//		scrollUsingJSWindow();
//		Common.waitForElement(2);
//		Collections.shuffle(clickOnQuickViewButton);
//		try {
//			if (!clickOnQuickViewButton.isEmpty()) {
//				WebElement randomIcon = clickOnQuickViewButton.get(0);
//				click(randomIcon);
//				Common.waitForElement(2);
//				click(closeTheQuickViewPopup);
//				System.out.println("Quick view close button clicked");
//			}
//			else {
//				System.out.println("Quick view close button not clickable");
//			}
//		} catch (Exception e) {
//			System.out.println("Caught an exception: " + e.getMessage());
//		}
//	}
	
 	public void viewMoreButton() {
 	// Select a random product
 		RandomProduct();
 		Common.waitForElement(1);

 		// -------------------- MORE FOR YOU --------------------
 		// Scroll to More For You button and click
 		((JavascriptExecutor) driver).executeScript(
 		    "arguments[0].scrollIntoView(true); window.scrollBy(0, -100);", 
 		    moreForYouSectionViewAllButton
 		);
 		((JavascriptExecutor) driver).executeScript("arguments[0].click();", moreForYouSectionViewAllButton);

 		// Get and print heading
 		String headingText = heading.getText();
 		System.out.println("📌 Heading displayed in application (More For You): " + headingText);

 		// -------------------- NAVIGATE BACK --------------------
 		driver.navigate().back(); // Go back to previous page
 		Common.waitForElement(1); // Wait for page to load

 		// -------------------- SUGGESTED FOR YOU --------------------
 		// Scroll to Suggested For You button and click
 		((JavascriptExecutor) driver).executeScript(
 		    "arguments[0].scrollIntoView(true); window.scrollBy(0, -100);", 
 		    suggestedForYouSectionViewAllButton
 		);
 		((JavascriptExecutor) driver).executeScript("arguments[0].click();", suggestedForYouSectionViewAllButton);

 		// Get and print heading
 		String headingText1 = heading.getText();
 		System.out.println("📌 Heading displayed in application (Suggested For You): " + headingText1);
 	}
	
 	public void productDescriptionDropDDown() {

 	    RandomProduct();
 	    Common.waitForElement(1);
 	    scrollUsingJSWindow();
 	    Common.waitForElement(2);

 	    if (!clickAllDropDownArrow.isEmpty()) {

 	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

 	        for (WebElement arrow : clickAllDropDownArrow) {

 	            try {

 	                wait.until(ExpectedConditions.elementToBeClickable(arrow));

 	                ((JavascriptExecutor) driver).executeScript(
 	                        "arguments[0].scrollIntoView(true);", arrow);

 	                Common.waitForElement(1);

 	                arrow.click();

 	            } catch (Exception e) {

 	                System.out.println("Product Description arrow not clickable");

 	            }
 	        }
 	    }
 	}

	public void returnAndExchangeLink() {
		RandomProduct();
		Common.waitForElement(1);
		scrollUsingJSWindow();
		try {
			if (clickOnReturn_ExchangeDropDownArrow.isDisplayed()) {
				click(clickOnReturn_ExchangeDropDownArrow);
				Common.waitForElement(1);
				click(clickOnTheLink);
				Common.waitForElement(2);
			}
			String currentUrl = driver.getCurrentUrl();
			if (!currentUrl.equals(driver.getCurrentUrl())) {
				System.out.println("Redirection of return and exchange link is success");
			} else {
				System.out.println("Redirection of return and exchange link in the same page");
			}
		} catch (Exception e) {
			System.out.println("Caught an exception: " + e.getMessage());
		}
	}
	
	
	
	public void verifyReturnAndExchangeLink() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    
	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    
	    click(zlaataIndiaShopButton);
	    
	    Common.waitForElement(10);


	    try {
	        
	        RandomProduct();
	        Common.waitForElement(1);

	        scrollUsingJSWindow();

	
	        wait.until(ExpectedConditions.elementToBeClickable(clickOnReturn_ExchangeDropDownArrow)).click();
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(clickOnTheLink)).click();
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.urlContains("return-exchange"));

	        // ---------------- VERIFY URL ----------------
	        String actualUrl = driver.getCurrentUrl();
	        String expectedUrl = "https://www.zlaata.com/policy/return-exchange-replacement";

	        Assert.assertEquals(
	                "❌ Return & Exchange URL mismatch",
	                expectedUrl,
	                actualUrl
	        );

	        // ---------------- VERIFY HEADING ----------------
	        WebElement heading = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//h2[contains(@class,'privacy__policy__title')]")));

	        String actualHeading = heading.getText().trim();
	        String expectedHeading = "RETURN, EXCHANGE & REPLACEMENT POLICY";

	        Assert.assertEquals(
	                "❌ Return & Exchange heading mismatch",
	                expectedHeading,
	                actualHeading
	        );

	        System.out.println("✅ Return & Exchange page verified successfully");

	    } catch (Exception e) {
	        System.out.println("❌ Return & Exchange verification failed: " + e.getMessage());
	        Assert.fail("Return & Exchange validation failed");
	    }
	}

	public void reviewViewAll() {
		RandomProduct();
		Common.waitForElement(1);
		scrollUsingJSWindow();
		try {
			if (!viewAllButton.isEmpty()) {
				viewAllButton.get(0).click();
				Common.waitForElement(1);
				System.out.println("View all Review button is Clicked ");
			} else {
				System.out.println("Product Review is not available ");
			}
		} catch (Exception e) {
			System.out.println("Caught an exception: " + e.getMessage());
		}
	}


	public void reviewCalculation() {
		RandomProduct();
		Common.waitForElement(2);  // Wait for product page to load
		scrollUsingJSWindow();

		try {
			if (!viewAllButton.isEmpty()) {
				viewAllButton.get(0).click();
				Common.waitForElement(2);
				System.out.println("✅ View all Review button is Clicked");

				int totalStarCount = 0;
				int totalReviewsParsed = 0;

				List<WebElement> reviewBlocks = driver.findElements(By.xpath("//div[@class='product_review_card_star_rating']"));

				for (WebElement review : reviewBlocks) {
					List<WebElement> stars = review.findElements(By.xpath(".//img[contains(@src, 'star')]"));
					int starsInReview = 0;

					for (WebElement star : stars) {
						String src = star.getAttribute("src");
						if (src.contains("filled_star")) {
							starsInReview++;
						}
					}

					if (starsInReview > 0) {
						totalStarCount += starsInReview;
						totalReviewsParsed++;
					}
				}

				// Handle fallback if all stars are 'empty' and no filled stars were counted
				if (totalReviewsParsed == 0 || totalStarCount == 0) {

					String totalReviewsText = totalReviewCount.getText().replaceAll("[^0-9]", "");
					int totalReviews = Integer.parseInt(totalReviewsText);

					String displayedRatingText = reviewRatingElement.getText().replaceAll("[^0-9.]", "");
					double displayedRating = Double.parseDouble(displayedRatingText);

					double expectedStarCount = totalReviews * displayedRating;
					System.out.println("Expected Total Star Count: " + expectedStarCount);
					System.out.println("Total Reviews : " + totalReviews);
					System.out.println("Displayed Review Rating: " + displayedRating);
					System.out.println("✅ Review calculation fallback assumed correct");
					return;
				}

				int totalReviews = totalReviewsParsed;
				double calculatedRating = totalReviews > 0 ? (double) totalStarCount / totalReviews : 0;
				System.out.println("Total Star Count: " + totalStarCount);
				System.out.println("Total Reviews Parsed: " + totalReviews);
				System.out.println("Calculated Review Rating: " + calculatedRating);

				String reviewRatingText = reviewRatingElement.getText().replaceAll("[^0-9.]", "");
				double displayedReviewRating = Double.parseDouble(reviewRatingText);

				double calculatedRounded = Math.round(calculatedRating * 10.0) / 10.0;
				double displayedRounded = Math.round(displayedReviewRating * 10.0) / 10.0;

				if (calculatedRounded == displayedRounded) {
					System.out.println("✅ Review calculation is correct");
				} else {
					System.out.println("❌ Review calculation is incorrect");
					System.out.println("Displayed Review Rating: " + displayedReviewRating);
				}
			} else {
				System.out.println("ℹ️ Product Review is not available.");
			}
		} catch (Exception e) {
			System.out.println("❗ Exception occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void reviewButtonclickable() {
		{
			try {
				Common.waitForElement(5);
				Actions actions = new Actions(driver);
				actions.moveToElement(shopMenu)
				.moveToElement(category)
				.click()
				.build()
				.perform();
				System.out.println(":white_check_mark: Navigated to category via Shop menu.");
				List<WebElement> addProduct = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
				if (addProduct.isEmpty()) {
					System.out.println(":x: No products found under the selected category.");
					return;
				}
				Collections.shuffle(addProduct);
				WebElement randomProduct = addProduct.get(0);
				actions.moveToElement(randomProduct).click().build().perform();
				System.out.println(":white_check_mark: Random product clicked.");
				Common.waitForElement(2);
				click(clickOnWriteReviewButton);
				System.out.println(":white_check_mark: Clicked on 'Write Review' button.");
			} catch (Exception e) {
				System.out.println(":exclamation: Error in clickRandomProductAndWriteReview(): " + e.getMessage());
			}
		}
	}





	public void moreForYouSection() 
	{
		Common.waitForElement(5);
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();
		List<WebElement> addProduct = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
		Collections.shuffle(addProduct);

		if (!addProduct.isEmpty()) {
			WebElement randomProduct = addProduct.get(0);
			actions.moveToElement(randomProduct).click().build().perform();
			Common.waitForElement(2);

			String moreFor = moreForSectionProduct.getText();
			if (!moreFor.isEmpty()) {

				System.out.println("More for you section is displaying :"+ moreFor);
			}
			else {

				System.out.println("More for you section is not displaying");
			}
		}
	}

	public void recentlyViewed() {
		LoginPage login = new LoginPage(driver);
		login.userLogin();
		Common.waitForElement(5);
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();
		List<WebElement> addProduct = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
		Collections.shuffle(addProduct);

		if (!addProduct.isEmpty()) {
			WebElement randomProduct = addProduct.get(0);
			actions.moveToElement(randomProduct).click().build().perform();
			Common.waitForElement(2);
			String recentlyProduct = recentlyViewSectionProduct.getText();
			System.out.println("Recently viewed is dispalying :"+ recentlyProduct);
		}
	}

	public void suggestedForYou() 
	{
		Common.waitForElement(5);
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();
		List<WebElement> addProduct = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
		Collections.shuffle(addProduct);

		if (!addProduct.isEmpty()) {
			WebElement randomProduct = addProduct.get(0);
			actions.moveToElement(randomProduct).click().build().perform();
			Common.waitForElement(2);
			String suggestedForYou = suggestedForYouSectionProduct.getText();
			if (!suggestedForYou.isEmpty()) {

				System.out.println("Suggest for you section is displaying :"+ suggestedForYou);
			}
			else {
				System.out.println("Suggest for you section is not displaying");
			}

		}
	}
	public void ReviewPopupWithoutEnterAllDataClickOnS() {
		Common.waitForElement(5);
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();

		List<WebElement> addProduct = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
		Collections.shuffle(addProduct);

		if (!addProduct.isEmpty()) {
			WebElement randomProduct = addProduct.get(0);
			actions.moveToElement(randomProduct).click().build().perform();
			Common.waitForElement(2);

			click(clickOnWriteReviewButton);
			Common.waitForElement(2);

			reviewUserName.clear();
			reviewEmailID.clear();
			click(clickOnSubmitButton);
			Common.waitForElement(2);

			// Scroll the popup into view (for all messages)
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", validationMessageForReviewName);
			Common.waitForElement(1);

			// Assert 1: Rating validation
			String actualMessage = validationMessageForRating.getText().trim();
			Assert.assertEquals("The rating is a required field", actualMessage);
			System.out.println("\u001B[32mThe validation message displayed: " + actualMessage + "\u001B[0m");

			
			// Assert 3: Name validation
			String actualMessage2 = validationMessageForReviewName.getText().trim();
			Assert.assertEquals("Name should be between 3 and 50 characters.", actualMessage2);
			System.out.println("\u001B[32mThe validation message displayed: " + actualMessage2 + "\u001B[0m");

			// Assert 4: Email validation
			reviewEmailID.clear();
			String actualMessage3 = validationMessageForReviewEmailID.getText().trim();
			Assert.assertEquals("Please enter a valid email address.", actualMessage3); // adjust this message based on actual UI
			System.out.println("\u001B[32mThe validation message displayed: " + actualMessage3 + "\u001B[0m");

			Common.waitForElement(2);
		}
	}

	public void ReviewPopupEnterAllData() {
		
		Common.waitForElement(5);
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();

		List<WebElement> addProduct = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
		Collections.shuffle(addProduct);

		if (!addProduct.isEmpty()) {
			WebElement randomProduct = addProduct.get(0);
			actions.moveToElement(randomProduct).click().build().perform();
			Common.waitForElement(2);

			click(clickOnWriteReviewButton);
			Common.waitForElement(2);
			
			click(starCount);
			
			
			type(reviewUserName, FileReaderManager.getInstance().getJsonReader().getValueFromJson("UserName"));

			Common.waitForElement(2);
			type(reviewEmailID, FileReaderManager.getInstance().getJsonReader().getValueFromJson("MailID"));

			Common.waitForElement(2);

			click(clickOnSubmitButton);
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // wait max 10 seconds
			wait.until(ExpectedConditions.visibilityOf(reviewSuccessMessage));

			// Get and verify the text
			String successText = reviewSuccessMessage.getText();
			if (successText.contains("Review updated successfully!")) {
			    System.out.println("✅ Verified success message: " + successText);
			} else {
			    System.out.println("❌ Unexpected message: " + successText);
			}
		}


	}
	


	

	public void scrollUsingJSWindow() {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, 800);");

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