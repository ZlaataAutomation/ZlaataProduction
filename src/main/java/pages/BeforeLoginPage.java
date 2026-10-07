package pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import manager.FileReaderManager;
import objectRepo.BeforeLoginObjRepo;
import utils.Common;

public  final class BeforeLoginPage  extends BeforeLoginObjRepo{

	public BeforeLoginPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}


	public void homeLaunch() {
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
		//type(accessCode, FileReaderManager.getInstance().getJsonReader().getValueFromJson("Access"));
		System.out.println("Clicked Zlaata india to the Shop Now Button");
		click(zlaataIndiaShopButton);
		Common.waitForElement(10);

	}

	public void verifyLogOut() {

	    try {

	        driver.get(FileReaderManager.getInstance()
	                .getConfigReader().getApplicationUrl());

	        Common.waitForElement(2);

	        
	        profile.click();
	        
	        Common.waitForElement(2);

	        // Already logged out
	        if (!driver.findElements(By.xpath("//div[@class='login_popup_wrap']")).isEmpty()) {

	            System.out.println("ℹ️ Application is already logged out.");
	            return;
	        }

	        // User is logged in → Logout
	        profile.click();

	        Common.waitForElement(2);

	        WebElement logoutBtn = driver.findElement(
	                By.xpath("//*[contains(@class,'logout-btn')]")
	        );

	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].click();", logoutBtn
	        );

	        System.out.println("✅ Logout clicked");

	    } catch (Exception e) {

	        System.out.println("❌ Logout failed: " + e.getMessage());
	    }
	}

	// TC-01
	public void verifyClickingAccountIcon() throws Exception {


		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {

			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);
			System.out.println(CYAN + " TC-01 : Verify Login Popup From Account Icon " + RESET);
			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);

			// Click Account Icon
			wait.until(ExpectedConditions.elementToBeClickable(accountIconbutton)).click();
			System.out.println(YELLOW + "➡ Clicked Account Icon" + RESET);

			// Verify Login Popup
			wait.until(ExpectedConditions.visibilityOf(loginPopup));

			Assert.assertTrue(loginPopup.isDisplayed());

			System.out.println(GREEN + "✅ Login Popup displayed successfully" + RESET);

			System.out.println(CYAN + "══════════════ TEST PASSED ══════════════" + RESET);

		} catch (Exception e) {

			System.out.println(RED + "❌ TEST FAILED : Login Popup is not displayed." + RESET);
			throw e;
		}
	}


	// TC-02
	public void verifyClickingOnWishList() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {

			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);
			System.out.println(CYAN + " TC-02 : Verify Login Popup From Wishlist Icon " + RESET);
			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);

			// Click Wishlist Icon
			wait.until(ExpectedConditions.elementToBeClickable(WishListIcon)).click();
			System.out.println(YELLOW + "➡ Clicked Wishlist Icon" + RESET);

			// Verify Login Popup
			wait.until(ExpectedConditions.visibilityOf(loginPopup));

			Assert.assertTrue(loginPopup.isDisplayed());

			System.out.println(GREEN + "✅ Login Popup displayed successfully" + RESET);

			System.out.println(CYAN + "══════════════ TEST PASSED ══════════════" + RESET);

		} catch (Exception e) {

			System.out.println(RED + "❌ TEST FAILED : Login Popup is not displayed." + RESET);
			throw e;
		}
	}





	// TC-03
	public void clickWishlistIconOnProductListingPage() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {

			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);
			System.out.println(CYAN + " TC-03 : Verify Login Popup From Best Sellers Wishlist " + RESET);
			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);

			// Wait for Best Sellers section
			WebElement bestSellerSection = wait.until(
					ExpectedConditions.visibilityOfElementLocated(
							By.xpath("(//section[@aria-label='Best Sellers Section'])[1]")));

			// Scroll to Best Sellers
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
					bestSellerSection);

			System.out.println(YELLOW + "⬇ Scrolled to Best Sellers section" + RESET);

			// Find Wishlist Icons
			List<WebElement> wishlistIcons = wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(
							By.xpath("//div[contains(@aria-label,'1 / 8')]//button[@aria-label='Add to wishlist']//*[name()='svg']")));

			System.out.println(CYAN + "📦 Wishlist Icons Found : " + wishlistIcons.size() + RESET);

			// Click first Wishlist icon
			for (WebElement wishlistIcon : wishlistIcons) {

				if (wishlistIcon.isDisplayed() && wishlistIcon.isEnabled()) {

					wait.until(ExpectedConditions.elementToBeClickable(wishlistIcon));
					wishlistIcon.click();

					System.out.println(GREEN + "✅ Wishlist icon clicked successfully" + RESET);
					break;
				}
			}

			// Verify Login Popup
			WebElement loginPopup = wait.until(
					ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//div[@class='login_popup_wrap']")));

			Assert.assertTrue(loginPopup.isDisplayed());

			System.out.println(GREEN + "✅ Login popup displayed successfully" + RESET);

			// Close Popup
			WebElement closePopupButton = wait.until(
					ExpectedConditions.elementToBeClickable(
							By.xpath("//div[@class='login_popup_cls_btn']//*[name()='svg']")));

			closePopupButton.click();

			System.out.println(GREEN + "✅ Login popup closed successfully" + RESET);

			System.out.println(CYAN + "══════════════ TEST PASSED ══════════════" + RESET);

		} catch (Exception e) {

			System.out.println(RED + "❌ TEST FAILED : " + e.getMessage() + RESET);
			throw e;
		}
	}



	public void verifyLoginPopupFromNewInWishlist() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {

			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);
			System.out.println(CYAN + " TC-06 : Verify Login Popup From NEW IN Wishlist " + RESET);
			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);

			// Click NEW IN
			WebElement newInSection = wait.until(
					ExpectedConditions.elementToBeClickable(
							By.xpath("//a[normalize-space()='NEW IN']")));

			newInSection.click();

			System.out.println(GREEN + "✅ Clicked NEW IN section" + RESET);

			// Wait for Product Listing
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@class='prod_listing_container collections']")));

			System.out.println(YELLOW + "📦 Product listing page loaded" + RESET);

			// Capture Wishlist Icons
			List<WebElement> wishlistIcons = wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(
							By.xpath("(//div[@class='prod_listing_container collections']//div//button//*[name()='svg'])[position() <= 12]")));

			System.out.println(CYAN + "📦 Wishlist Icons Found : " + wishlistIcons.size() + RESET);

			// Click first Wishlist icon
			if (!wishlistIcons.isEmpty()) {

				WebElement firstProductWishlist = wishlistIcons.get(0);

				wait.until(ExpectedConditions.elementToBeClickable(firstProductWishlist));

				firstProductWishlist.click();

				System.out.println(GREEN + "✅ First product wishlist icon clicked" + RESET);
			}

			// Verify Login Popup
			WebElement loginPopup = wait.until(
					ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//div[@class='login_popup_wrap']")));

			Assert.assertTrue(loginPopup.isDisplayed());

			System.out.println(GREEN + "✅ Login popup displayed successfully" + RESET);

			// Close Popup
			WebElement closePopupButton = wait.until(
					ExpectedConditions.elementToBeClickable(
							By.xpath("//div[@class='login_popup_cls_btn']//*[name()='svg']")));

			closePopupButton.click();

			System.out.println(GREEN + "✅ Login popup closed successfully" + RESET);

			System.out.println(CYAN + "══════════════ TEST PASSED ══════════════" + RESET);

		} catch (Exception e) {

			System.out.println(RED + "❌ TEST FAILED : " + e.getMessage() + RESET);
			throw e;
		}
	}

	public void verifyLoginPopupFromProductDetails() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {

			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);
			System.out.println(CYAN + " TC-04 : Verify Login Popup From Product Details Page " + RESET);
			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);

			Actions actions = new Actions(driver);

			// Hover on Shop Menu
			WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@class='header_nav_item has_dropdown shop']")));
			actions.moveToElement(shopMenu).perform();
			System.out.println(YELLOW + "➡ Hovered on Shop menu" + RESET);

			// Click All
			WebElement allMenu = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("(//a[@class='dropdown_category_link '][normalize-space()='All'])[1]")));
			allMenu.click();
			System.out.println(GREEN + "✅ Clicked 'All' category" + RESET);

			// Wait for products
			List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					By.xpath("//div[@class='prod_listing_content']//a[@class='product_list_name']")));

			System.out.println(CYAN + "📦 Total Products : " + products.size() + RESET);

			// Select random product
			Random random = new Random();
			WebElement randomProduct = products.get(random.nextInt(products.size()));

			String productName = randomProduct.getText().trim();

			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView({block:'center'});", randomProduct);

			wait.until(ExpectedConditions.elementToBeClickable(randomProduct)).click();

			System.out.println(GREEN + "✅ Random Product Selected : " + productName + RESET);

			// Wait for PDP
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@class='prod_name_wrap']")));

			System.out.println(CYAN + "📄 Product Details Page Opened" + RESET);

			// Click Wishlist
			WebElement wishlistIcon = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//div[@class='prod_name_wrap']//div[contains(@class,'prod_wishlist_btn prod_wishlist_icon')]//*[name()='svg']")));

			wishlistIcon.click();
			System.out.println(YELLOW + "❤ Clicked Wishlist Icon" + RESET);

			// Verify Login Popup
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@class='login_popup_wrap']")));

			System.out.println(GREEN + "✅ Login Popup Displayed Successfully" + RESET);

			// Close Popup
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//div[@class='login_popup_cls_btn']//*[name()='svg']"))).click();

			System.out.println(GREEN + "✅ Login Popup Closed Successfully" + RESET);

			System.out.println(CYAN + "══════════════ TEST PASSED ══════════════" + RESET);

		} catch (Exception e) {
			System.out.println(RED + "❌ TEST FAILED : " + e.getMessage() + RESET);
			throw e;
		}
	}	

	public void verifyLoginPopupFromTryAlongQuickView() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {

			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);
			System.out.println(CYAN + " TC-05 : Verify Login Popup From Try Along Quick View " + RESET);
			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);

			// Scroll to Try Along Section
			WebElement tryAlongSection = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@class='try_along_list_wrap']")));

			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView({block:'center'});", tryAlongSection);

			System.out.println(YELLOW + "⬇ Scrolled to Try Along Section" + RESET);

			Common.waitForElement(2);

			// Open Quick View
			WebElement quickView = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("(//div[@class='try_along_quickview_btn Cls_quickview_btn'])[1]")));

			quickView.click();

			System.out.println(GREEN + "✅ Quick View Opened Successfully" + RESET);

			Common.waitForElement(2);

			// Click Wishlist
			WebElement wishlistIcon = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//div[@class='qv_prod_details']//div[contains(@class,'prod_wishlist_btn prod_wishlist_icon')]//*[name()='svg']")));

			wishlistIcon.click();

			System.out.println(YELLOW + "❤ Clicked Wishlist Icon" + RESET);

			Common.waitForElement(2);

			// Verify Login Popup
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@class='login_popup_wrap']")));

			System.out.println(GREEN + "✅ Login Popup Displayed Successfully" + RESET);

			// Close Popup
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//div[@class='login_popup_cls_btn']//*[name()='svg']"))).click();

			System.out.println(GREEN + "✅ Login Popup Closed Successfully" + RESET);

			System.out.println(CYAN + "══════════════ TEST PASSED ══════════════" + RESET);

		} catch (Exception e) {
			System.out.println(RED + "❌ TEST FAILED : " + e.getMessage() + RESET);
			throw e;
		}
	}



	private void scrollUsingJSWindow() {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, 7200);");

	}


	//======================================
	// ANSI Console Colors
	//======================================


	private static final String YELLOW = "\u001B[33m";
	private static final String CYAN = "\u001B[36m";
	private static final String BLUE = "\u001B[34m";
	private static final String GREEN = "\u001B[32m";
	private static final String RED = "\u001B[31m";
	private static final String RESET = "\u001B[0m";

	//======================================
	// Common Method - Navigate to Threads Page
	//======================================
	public void navigateToThreadsPage() {

		homeLaunch();
		scrollUsingJSWindow();
		clickOnLoyalitypoints.click();
		Common.waitForElement(5);
	}

	//======================================
	// Common Method - Verify Login Popup
	//======================================
	public void verifyLoginPopup(String buttonName) {

		if (threadsloginpopup.isDisplayed()) {

			System.out.println(GREEN + "====================================================");
			System.out.println("✅ PASS : Login Popup displayed after clicking '" + buttonName + "' Button.");
			System.out.println("====================================================" + RESET);

		} else {

			System.out.println(RED + "====================================================");
			System.out.println("❌ FAIL : Login Popup NOT displayed after clicking '" + buttonName + "' Button.");
			System.out.println("====================================================" + RESET);

			Assert.fail("Login Popup is not displayed.");
		}
	}

	public void threadPageSignUpButton() {

		navigateToThreadsPage();

		threadsSignupButton.click();

		Assert.assertTrue("Login Popup is not displayed.", threadsloginpopup.isDisplayed());

		verifyLoginPopup("Sign Up");
	}

	public void threadPageEnterDOBButton() {

		navigateToThreadsPage();

		threadPageDOBButton.click();

		Assert.assertTrue("Login Popup is not displayed.", threadsloginpopup.isDisplayed());

		verifyLoginPopup("Enter DOB");
	}

	public void threadPageReferNowButtonOnThreadButton() {

		navigateToThreadsPage();

		threadPagereferNowButton.click();

		Assert.assertTrue("Login Popup is not displayed.", threadsloginpopup.isDisplayed());

		verifyLoginPopup("Refer Now");
	}

	public void threadPageRateUsButton() {

		navigateToThreadsPage();

		threadPageRateusButton.click();

		Assert.assertTrue("Login Popup is not displayed.", threadsloginpopup.isDisplayed());

		verifyLoginPopup("Rate Us");
	}

	public void threadPageViewGalleryButton() {

		navigateToThreadsPage();

		threadPageViewGallery.click();

		Common.waitForElement(2);

		donateWithLoveBannerButton.click();

		Assert.assertTrue("Login Popup is not displayed.", threadsloginpopup.isDisplayed());

		verifyLoginPopup("View Gallery");
	}



	public void deleteAllProductsFromCart() {

		driver.get(FileReaderManager.getInstance()
				.getConfigReader()
				.getApplicationUrl());

		// Open cart
		driver.findElement(By.xpath("//button[@class='header_cta_btn Cls_cart_btn ']")).click();
		Common.waitForElement(10);


		// ✅ STEP 1: Check if cart is already empty
		try {

			if (driver.findElement(By.xpath("//a[@class='empty_bag_shop_btn btn___2']")).isDisplayed()) {

				System.out.println(GREEN + 
						"🛍️ Cart already empty. No delete action needed."
						+ RESET);

				return;
			}

		} catch (NoSuchElementException ignored) {

			System.out.println(YELLOW +
					"🛒 Cart is NOT empty, proceed to delete."
					+ RESET);
		}



		// ✅ STEP 2: Delete products one by one
		while (true) {

			// Check cart empty before finding delete button
			try {

				if (driver.findElement(By.xpath("//a[@class='empty_bag_shop_btn btn___2']")).isDisplayed()) {

					System.out.println(GREEN +
							"🛍️ Cart is empty. All products deleted."
							+ RESET);

					break;
				}

			} catch (NoSuchElementException ignored) {

			}



			try {

				WebElement deleteBtn = driver.findElement(
						By.xpath("//div[@title='Delete']"));

				deleteBtn.click();

				System.out.println(YELLOW +
						"🗑️ Product deleted successfully."
						+ RESET);

				Common.waitForElement(1);


			} catch (NoSuchElementException e) {

				System.out.println(GREEN +
						"✅ No more products to delete."
						+ RESET);

				break;


			} catch (Exception e) {

				System.out.println(RED +
						"❌ Error while deleting: "
						+ e.getMessage()
						+ RESET);

				break;
			}
		}



		// ✅ STEP 3: Final confirmation
		try {

			if (driver.findElement(By.xpath("//a[@class='empty_bag_shop_btn btn___2']")).isDisplayed()) {

				System.out.println(GREEN +
						"🛍️ Cart is empty, Continue Shopping displayed."
						+ RESET);
			}

		} catch (NoSuchElementException e) {

			System.out.println(RED +
					"ℹ️ Bag is not empty message not found."
					+ RESET);
		}
	}
	public String randomMethod() {

		String productListingName;

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		Actions actions = new Actions(driver);
		Random random = new Random();

		// Hover on Shop Menu
		WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='header_nav_item has_dropdown shop']")));
		actions.moveToElement(shopMenu).perform();

		// Click Dresses
		WebElement dressesMenu = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//a[normalize-space()='dresses']")));
		dressesMenu.click();

		Common.waitForElement(5);

		System.out.println(CYAN + "==================================================" + RESET);
		System.out.println(CYAN + "🛍 Navigated to Dresses Listing Page." + RESET);
		System.out.println(CYAN + "==================================================" + RESET);

		// Get Product List
		List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
				By.xpath("//div[@class='prod_listing_card']")));

		if (products.isEmpty()) {

			System.out.println(RED + "==================================================" + RESET);
			System.out.println(RED + "❌ No Products Found on Product Listing Page." + RESET);
			System.out.println(RED + "==================================================" + RESET);

			Assert.fail("No products found on Product Listing Page.");
		}

		int maxAttempts = Math.min(5, products.size());
		boolean productFound = false;

		for (int attempt = 1; attempt <= maxAttempts; attempt++) {

			int randomIndex = random.nextInt(products.size()) + 1;

			System.out.println(YELLOW + "🎯 Attempt " + attempt +
					" : Checking Product Index -> " + randomIndex + RESET);

			WebElement productCard = driver.findElement(
					By.xpath("(//div[@class='prod_listing_card'])[" + randomIndex + "]"));

			String productName = productCard.findElement(
					By.xpath(".//a[contains(@class,'product_list_name')]"))
					.getText().trim();

			List<WebElement> stockLabel = productCard.findElements(
					By.xpath(".//span[contains(@class,'prod_listing_hurry') and contains(text(),'Out of Stock')]"));

			if (!stockLabel.isEmpty() && stockLabel.get(0).isDisplayed()) {

				System.out.println(RED + "❌ Product '" + productName + "' is Out of Stock." + RESET);
				continue;
			}

			WebElement product = productCard.findElement(
					By.xpath(".//a[contains(@class,'product_list_name')]"));

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", product);

			productFound = true;

			System.out.println(GREEN + "✅ Selected Product : " + productName + RESET);

			break;
		}

		if (!productFound) {

			System.out.println(RED + "==================================================" + RESET);
			System.out.println(RED + "❌ No In-Stock Product Found after " + maxAttempts + " attempts." + RESET);
			System.out.println(RED + "==================================================" + RESET);

			Assert.fail("No In-Stock Product Found.");
		}

		// Product Name
		productListingName = driver.findElement(
				By.xpath("//h3[@class='prod_name']"))
				.getText().trim();

		System.out.println(GREEN + "==================================================" + RESET);
		System.out.println(GREEN + "🛍 Selected Product : " + productListingName + RESET);
		System.out.println(GREEN + "==================================================" + RESET);

		Common.waitForElement(2);

		// Click Buy Now
		WebElement buyNowButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("(//button[contains(text(),'Buy Now')])[1]")));

		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView({block:'center'});", buyNowButton);

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", buyNowButton);

		Common.waitForElement(2);

		System.out.println(GREEN + "✅ Buy Now button clicked successfully." + RESET);

		// Open Cart
		WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//button[@class='header_cta_btn Cls_cart_btn ']")));

		cartButton.click();

		Common.waitForElement(10);

		System.out.println(GREEN + "==================================================" + RESET);
		System.out.println(GREEN + "🛒 Product added to Cart successfully." + RESET);
		System.out.println(GREEN + "🛍 Product Name : " + productListingName + RESET);
		System.out.println(GREEN + "==================================================" + RESET);

		return productListingName;
	}

	public void checkoutPagePalceOrderButton() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		try {

			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);
			System.out.println(CYAN + " TC : Verify Login Popup From Checkout Place Order Button " + RESET);
			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);

			// Launch Application
			driver.get(FileReaderManager.getInstance()
					.getConfigReader()
					.getApplicationUrl());



			System.out.println(YELLOW + "➡ Application launched successfully." + RESET);


			// Open Cart
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//button[@class='header_cta_btn Cls_cart_btn ']")))
			.click();
			
			Common.waitForElement(10);			
			System.out.println(YELLOW + "➡ Cart page opened successfully." + RESET);


			// Click Place Order Button
			wait.until(ExpectedConditions.elementToBeClickable(placeOrderButtonInCheckoutPage))
			.click();

			System.out.println(YELLOW + "➡ Clicked 'Place Order' button." + RESET);

			// Verify Login Popup
			verifyLoginPopup("Checkout Place Order Button");

			System.out.println(GREEN + "✅ Login popup displayed successfully." + RESET);

			System.out.println(CYAN + "══════════════ TEST PASSED ══════════════" + RESET);

		} catch (Exception e) {

			System.out.println(RED + "❌ TEST FAILED : " + e.getMessage() + RESET);
			throw e;
		}
	}
	public void checkoutPageWishListButton() {
		
		
		
		deleteAllProductsFromCart();
		
				Common.waitForElement(3);
		
				randomMethod();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		try {

			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);
			System.out.println(CYAN + " TC : Verify Login Popup From Checkout Wishlist Button " + RESET);
			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);

			// Launch Application
			driver.get(FileReaderManager.getInstance()
					.getConfigReader()
					.getApplicationUrl());

			System.out.println(YELLOW + "➡ Application launched successfully." + RESET);

			// Open Cart
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//button[@class='header_cta_btn Cls_cart_btn ']")))
			.click();

			System.out.println(YELLOW + "➡ Cart page opened successfully." + RESET);

			Common.waitForElement(10);

			// Click Wishlist Button
			wait.until(ExpectedConditions.elementToBeClickable(checkouPageWishlistButton))
			.click();

			System.out.println(YELLOW + "➡ Clicked 'Wishlist' button." + RESET);

			// Verify Login Popup
			verifyLoginPopup("Checkout Wishlist Button");

			System.out.println(GREEN + "✅ Login popup displayed successfully." + RESET);

			System.out.println(CYAN + "══════════════ TEST PASSED ══════════════" + RESET);

		} catch (Exception e) {

			System.out.println(RED + "❌ TEST FAILED : " + e.getMessage() + RESET);
			throw e;
		}
	}



	public void checkoutPageApplyButton() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		try {

			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);
			System.out.println(CYAN + " TC : Verify Login Popup From Apply Button " + RESET);
			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);

			// Launch Application
			driver.get(FileReaderManager.getInstance()
					.getConfigReader()
					.getApplicationUrl());

			System.out.println(YELLOW + "➡ Application launched successfully." + RESET);

			// Open Cart
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//button[@class='header_cta_btn Cls_cart_btn ']")))
			.click();

			System.out.println(YELLOW + "➡ Cart page opened successfully." + RESET);

			Common.waitForElement(10);

			// Click Apply Button
			wait.until(ExpectedConditions.elementToBeClickable(applyButtonOnCheckoutPage))
			.click();

			System.out.println(YELLOW + "➡ Clicked 'Apply' button." + RESET);

			// Verify Login Popup
			verifyLoginPopup("Apply Button in Checkout Page");

			System.out.println(GREEN + "✅ Login popup displayed successfully." + RESET);

			System.out.println(CYAN + "══════════════ TEST PASSED ══════════════" + RESET);

		} catch (Exception e) {

			System.out.println(RED + "❌ TEST FAILED : " + e.getMessage() + RESET);
			throw e;
		}
	}

	public void viewCouponApplyButton() {


		//		deleteAllProductsFromCart();
		//
		//		Common.waitForElement(3);
		//
		//		randomMethod()

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		try {

			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);
			System.out.println(CYAN + " TC : Verify Login Popup From View Coupon Button " + RESET);
			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);

			// Launch Application
			driver.get(FileReaderManager.getInstance()
					.getConfigReader()
					.getApplicationUrl());

			System.out.println(YELLOW + "➡ Application launched successfully." + RESET);

			// Open Cart
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//button[@class='header_cta_btn Cls_cart_btn ']")))
			.click();
			
			Common.waitForElement(10);

			System.out.println(YELLOW + "➡ Cart page opened successfully." + RESET);

			Common.waitForElement(2);

			// Click View Coupon Button
			wait.until(ExpectedConditions.elementToBeClickable(viewCouponButton)).click();

			System.out.println(YELLOW + "➡ Clicked 'View Coupon' button." + RESET);

			Common.waitForElement(2);

			// Verify Login Popup
			verifyLoginPopup("View Coupon Button in Checkout Page");

			System.out.println(GREEN + "✅ Login popup displayed successfully." + RESET);

			System.out.println(CYAN + "══════════════ TEST PASSED ══════════════" + RESET);

		} catch (Exception e) {

			System.out.println(RED + "❌ TEST FAILED : " + e.getMessage() + RESET);
			throw e;
		}
	}

	public void CheckoutPageTrytoApplyThread() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		try {

			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);
			System.out.println(CYAN + " TC : Verify Login Snackbar While Applying Threads " + RESET);
			System.out.println(CYAN + "══════════════════════════════════════════════════════" + RESET);

			// Launch Application
			driver.get(FileReaderManager.getInstance()
					.getConfigReader()
					.getApplicationUrl());

			System.out.println(YELLOW + "➡ Application launched successfully" + RESET);

			// Open Cart
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//button[@class='header_cta_btn Cls_cart_btn ']")))
			.click();

			System.out.println(YELLOW + "➡ Cart page opened" + RESET);

			Common.waitForElement(10);

			// Enter Thread Value
			wait.until(ExpectedConditions.visibilityOf(threadTextBox));
			threadTextBox.clear();
			threadTextBox.sendKeys("2");

			System.out.println(YELLOW + "➡ Entered Threads value : 2" + RESET);

			// Verify Snackbar Message
			WebElement snackbarMessage = wait.until(
					ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//div[contains(@class,'snackbar-container')]//p[normalize-space()='Please login to apply Threads.']")));

			Assert.assertTrue(snackbarMessage.isDisplayed());

			System.out.println(GREEN + "✅ Snackbar displayed successfully" + RESET);
			System.out.println(GREEN + "✅ Message : " + snackbarMessage.getText() + RESET);

			System.out.println(CYAN + "══════════════ TEST PASSED ══════════════" + RESET);

		} catch (Exception e) {

			System.out.println(RED + "❌ TEST FAILED : " + e.getMessage() + RESET);
			throw e;
		}
	}


	//		WebElement snackbarMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
	//		        By.xpath("//div[contains(@class,'snackbar-container')]//p[normalize-space()='Please login to apply Threads.']")
	//		));
	//
	//
	//		if (snackbarMessage.isDisplayed()) {
	//
	//		    System.out.println(GREEN + 
	//		            "✅ PASS : Snackbar message displayed - " 
	//		            + snackbarMessage.getText()
	//		            + RESET);
	//
	//		} else {
	//
	//		    System.out.println(RED +
	//		            "❌ FAIL : Snackbar message not displayed."
	//		            + RESET);
	//
	//		    Assert.fail("Snackbar message is not displayed.");
	//		}




	public void verifyOutOfStockProductAvailable() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());

	    System.out.println(CYAN + "══════════════ Verify Out Of Stock Product ══════════════" + RESET);

	    clickOnBossladyShopNowButton.click();
	    Common.waitForElement(5);

	    // Hover on Shop
	    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[@class='header_nav_item has_dropdown shop']")));

	    actions.moveToElement(shopMenu).perform();

	    // Click All
	    WebElement allButton = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//a[normalize-space()='All']")));

	    allButton.click();

	    Common.waitForElement(5);

	    boolean productClicked = false;

	    while (true) {

	        System.out.println(BLUE + "🔍 Checking current page..." + RESET);

	        // Check Out of Stock product
	        List<WebElement> outOfStockProducts = driver.findElements(
	                By.xpath("//span[contains(normalize-space(),'Out of Stock')]")
	        );

	        if (!outOfStockProducts.isEmpty()) {

	            System.out.println(GREEN + "✅ Out of Stock product available on current page." + RESET);

	            // Get all product cards
	            List<WebElement> productCards = driver.findElements(
	                    By.xpath("//div[@class='prod_listing_card']")
	            );

	            for (WebElement productCard : productCards) {

	                // Check Out of Stock inside same product card
	                List<WebElement> stockLabel = productCard.findElements(
	                        By.xpath(".//span[contains(normalize-space(),'Out of Stock')]")
	                );

	                if (!stockLabel.isEmpty()) {

	                    // Get product name
	                    WebElement productNameElement = productCard.findElement(
	                            By.xpath(".//a[contains(@class,'product_list_name')]")
	                    );

	                    String productName = productNameElement.getText().trim();

	                    System.out.println(YELLOW + "📦 Out of Stock Product Name : " + productName + RESET);

	                    // Scroll to product
	                    js.executeScript(
	                            "arguments[0].scrollIntoView({block:'center'});",
	                            productNameElement
	                    );

	                    Common.waitForElement(2);

	                    // Click Out of Stock Product
	                    js.executeScript(
	                            "arguments[0].click();",
	                            productNameElement
	                    );

	                    Common.waitForElement(5);

	                    System.out.println(GREEN + "✅ Clicked Out of Stock Product : " + productName + RESET);

	                    // Click Notify Me button
	                    WebElement notifyButton = wait.until(ExpectedConditions.elementToBeClickable(
	                            By.xpath("//button[@class='prod_no_stock_btn btn___2 cls_add_notify']")
	                    ));

	                    System.out.println(BLUE + "🔔 Clicking Notify Me button..." + RESET);

	                    js.executeScript(
	                            "arguments[0].click();",
	                            notifyButton
	                    );

	                    Common.waitForElement(3);

	                    // Verify Login Popup
	                    verifyLoginPopup("Notify button");
	                    
	                    Common.waitForElement(5);

	                    System.out.println(GREEN + "✅ Login popup displayed successfully." + RESET);
	                    System.out.println(CYAN + "══════════════ TEST PASSED ══════════════" + RESET);

	                    productClicked = true;
	                    break;
	                }
	            }
	        }

	        // Stop after clicking product
	        if (productClicked) {
	            break;
	        }

	        System.out.println(YELLOW + "⚠️ No Out of Stock product on this page." + RESET);

	        // Scroll to pagination
	        WebElement pagination = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//div[@class='pagination_wrap']")
	                ));

	        js.executeScript(
	                "arguments[0].scrollIntoView({block:'center'});",
	                pagination
	        );

	        Common.waitForElement(2);

	        // Click Next page
	        List<WebElement> nextButton = driver.findElements(
	                By.xpath("//a[contains(@class,'next')]")
	        );

	        if (nextButton.isEmpty()) {

	            System.out.println(RED + "❌ Last page reached. No Out of Stock product found." + RESET);
	            break;
	        }

	        System.out.println(BLUE + "➡ Navigating to next page..." + RESET);

	        js.executeScript(
	                "arguments[0].click();",
	                nextButton.get(0)
	        );

	        Common.waitForElement(5);
	    }

	    System.out.println(CYAN + "══════════════ Verification Completed ══════════════" + RESET);
	}



	//TC-01
	public void navigateToHomePageAsGuest() throws Exception {
		
		
		

		homeLaunch();



		verifyClickingAccountIcon();
	}	

	// TC-02
	public void LoginPopupOnClickingWishlist() throws Exception {
		homeLaunch();
		verifyClickingOnWishList();
	}


	//TC-03
	public void listingPageWishlistPopup() throws Exception {
		homeLaunch();
		clickWishlistIconOnProductListingPage();
		verifyLoginPopupFromNewInWishlist();
	}

	//TC-04
	public void productDetailsWishlistPopupFlow() throws Exception {
		homeLaunch();
		verifyLoginPopupFromProductDetails();
		verifyLoginPopupFromTryAlongQuickView();
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
