package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import manager.FileReaderManager;
import objectRepo.CheckOutPageObjRepo;
import stepDef.Hooks;
import utils.Common;
import utils.ExcelReportUtil;
import static org.junit.Assert.assertEquals;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public final class CheckoutPage extends CheckOutPageObjRepo{
	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}


	public void verifyCheckoutCalculationsWithExcel() {
		// 1️⃣ Navigate to checkout page
		LoginPage login = new LoginPage(driver);
		login.userLogin();
		Common.waitForElement(5);
		Actions actions = new Actions(driver);

		WebElement bagIcon = driver.findElement(By.xpath("//a[@class='Cls_cart_btn Cls_redirect_restrict']"));
		bagIcon.click();
		Common.waitForElement(2);

		List<WebElement> productBlocks = driver.findElements(By.xpath(".//div[@class='cart_prod_card ']"));
		if (productBlocks.isEmpty()) {
			System.out.println("🛍️ Bag item count not displayed. Adding product...");
			Common.waitForElement(2);
			WebElement shopMenu = driver.findElement(By.xpath("//li[@class='navigation_menu_list nav_menu_dropdown shop']"));
			actions.moveToElement(shopMenu);
			WebElement category = driver.findElement(By.xpath("//div[@class='nav_drop_down_box_category active']//a[contains(translate(text(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'ALL')]"));
			actions.moveToElement(category).click().build().perform();
			List<WebElement> addProduct = driver.findElements(By.xpath("//button[@class='product_list_cards_btn']"));
			Collections.shuffle(addProduct);
			if (!addProduct.isEmpty()) {
				WebElement randomProduct = addProduct.get(0);
				actions.moveToElement(randomProduct).click().build().perform();
				WebElement addToCart = driver.findElement(By.xpath("//button[@class='add_bag_prod_buy_now_btn btn___2  Cls_CartList ClsProductListSizes']"));
				addToCart.click();
				Common.waitForElement(5);
				driver.findElement(By.xpath("//button[@class='Cls_cart_btn']")).click();
				Common.waitForElement(2);
			}
		}
		// 2️⃣ Stage 1 - Initial checkout values
		performCheckoutCalculationAndExcelLog("Initial");

		// 3️⃣ Stage 2 - Increase quantity for each product
		List<WebElement> products = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
		for (WebElement product : products) {
			try {
				WebElement plusBtn = product.findElement(By.xpath(".//button[contains(@class,'cp_quantity_increase_btn')]"));
				new Actions(driver).moveToElement(plusBtn).click().perform();
				Common.waitForElement(2);
			} catch (Exception e) {
				System.out.println("Could not increase quantity for product: " +
						safeGetText(product, ".//div[@class='cp_product_name']"));
			}
		}
		Common.waitForElement(2);
		performCheckoutCalculationAndExcelLog("After Increase");

		// 4️⃣ Stage 3 - Decrease quantity back
		for (WebElement product : products) {
			try {
				WebElement minusBtn = product.findElement(By.xpath(".//button[contains(@class,'cp_quantity_decrease_btn')]"));
				new Actions(driver).moveToElement(minusBtn).click().perform();
				Common.waitForElement(2);
			} catch (Exception e) {
				System.out.println("Could not decrease quantity for product: " +
						safeGetText(product, ".//div[@class='cp_product_name']"));
			}
		}
		Common.waitForElement(2);
		performCheckoutCalculationAndExcelLog("After Decrease");

		// 5️⃣ Save Excel report at end
		ExcelReportUtil.generateExcelReport();
	}

	private String safeGetTextDriver(String xpath) {
		try {
			return driver.findElement(By.xpath(xpath)).getText().trim();
		} catch (Exception e) {
			return "";
		}
	}
	private double safeParseDouble(String s) {
		try {
			if (s == null || s.isEmpty()) return 0.0;
			return Double.parseDouble(s.replaceAll("[^\\d.]", ""));
		} catch (Exception e) {
			return 0.0;
		}
	}
	private String safeGetText(WebElement parent, String childXpath) {
		try {
			return parent.findElement(By.xpath(childXpath)).getText().trim();
		} catch (Exception e) {
			return "";
		}
	}


	private boolean couponApplied = false;
	private boolean giftWrapApplied = false;
	private boolean expressApplied = false;
	private boolean threadsAppliedOnce = false;

	private double couponDiscount = 0;
	private double threadDiscount = 0;
	private double giftWrapCharge = 0;
	private double expressCharge = 0;
	private static final String RESET = "\u001B[0m";
	private static final String BLUE = "\u001B[34m";
	private static final String GREEN = "\u001B[32m";
	private static final String YELLOW = "\u001B[33m";
	private static final String RED = "\u001B[31m";
	private static final String CYAN = "\u001B[36m";
	private static final String MAGENTA = "\u001B[35m";

	private double getGiftWrapCharge() {
		try {
			if (!giftWrapApplied) {
				WebElement giftWrapToggle = driver.findElement(By.xpath("//input[@class='checkout_git_list_item__checkbox']"));
				giftWrapToggle.click();
				driver.findElement(By.id("recipient-name")).sendKeys("Test User");
				driver.findElement(By.xpath("//button[@class='gift__submit btn___2']")).click();
				Common.waitForElement(2);
				giftWrapCharge = safeParseDouble(safeGetTextDriver("//div[@class='price_details_pair git__wraping11']"));
				giftWrapApplied = true;
			} else {
				giftWrapCharge = safeParseDouble(safeGetTextDriver("//div[@class='price_details_pair git__wraping11']"));
			}
		} catch (Exception ignored) {}
		return giftWrapCharge;
	}

	private double getExpressCharge() {
		try {
			if (!expressApplied) {
				WebElement expressOption = driver.findElement(By.name("delivery_speed"));
				if (!expressOption.isSelected()) expressOption.click();
				expressCharge = safeParseDouble(safeGetTextDriver("//div[@class='Cls_CourierFee']"));
				expressApplied = true;
			} else {
				expressCharge = safeParseDouble(safeGetTextDriver("//div[@class='Cls_CourierFee']"));
			}
		} catch (Exception ignored) {}
		return expressCharge;
	}

	private double getCustomizationCharge() {
		try {
			return safeParseDouble(safeGetTextDriver("//div[@class='price_details_row Cls_customized_extras']"));
		} catch (Exception ignored) {}
		return 0;
	}

	private double getFlatDiscount() {
		try {
			return safeParseDouble(safeGetTextDriver("//div[@class='price_details_pair Cls_cart_extra_prepaid_discount']"));
		} catch (Exception ignored) {}
		return 0;
	}

	// Apply a coupon only once if none is applied
	private void applyCouponIfNotApplied() {
	    if (couponApplied) return;
	    try {
	        // if a coupon chip / status already shows savings, consider it applied
	        String already = safeGetTextDriver("//p[contains(@class,'acc_details_status')]");
	        if (already != null && already.matches(".*\\d+.*")) {
	            couponApplied = true;
	            return;
	        }

	        if (driver.findElements(By.xpath("//button[contains(@class,'viewCouponBtn')]")).size() > 0) {
	            driver.findElement(By.xpath("//button[contains(@class,'viewCouponBtn')]")).click();
	            Common.waitForElement(1);
	            List<WebElement> coupons = driver.findElements(By.xpath("//button[contains(@class,'Cls_apply_coupon')]"));
	            if (!coupons.isEmpty()) {
	                coupons.get(0).click();
	                Common.waitForElement(1);
	                driver.findElement(By.xpath("//div[contains(@class,'coupon_popup') and contains(@class,'active')]//button[contains(@class,'coupon_input_apply_btn')]")).click();
	                Common.waitForElement(2);
	            }
	            couponApplied = true;
	        }
	    } catch (Exception ignored) {}
	}

	/** Always read the CURRENT coupon discount from the Price Details pane. */
	private double getCurrentCouponDiscount() {
	    String[] xps = new String[] {
	        // ✅ Primary: the row with class Cls_cart_coupon_discount
	        "//div[contains(@class,'Cls_cart_coupon_discount')]/div[last()]",
	        // Fallback: sometimes coupon status shows as “You saved ₹500 extra”
	        "//p[contains(@class,'acc_details_status')]"
	    };
	    for (String xp : xps) {
	        String txt = safeGetTextDriver(xp);
	        if (txt != null && !txt.trim().isEmpty()) {
	            double v = safeParseDouble(txt); // strips ₹ and “-”
	            if (v > 0) return v;
	        }
	    }
	    return 0;
	}

	private double applyThreadsIfNotApplied() {
		if (!threadsAppliedOnce) {
			try {
				int availableThreads = (int) safeParseDouble(safeGetTextDriver("//span[@class='price_details_key_span']"));
				if (availableThreads > 0) {
					WebElement threadInput = driver.findElement(By.xpath("//input[@placeholder='Enter threads']"));
					threadInput.clear();
					threadInput.sendKeys(String.valueOf(availableThreads));
					threadDiscount = availableThreads; // 1 thread = Rs.1
					threadsAppliedOnce = true;
				}
			} catch (Exception ignored) {}
		}
		return threadDiscount;
	}
	private int getQuantity(WebElement product) {
		try {
			// First try from text
			WebElement qtyElement = product.findElement(By.xpath(".//div[@class='cp_quantity_wrap']"));
			String qtyText = qtyElement.getText().trim();
			if (!qtyText.isEmpty()) {
				return Integer.parseInt(qtyText);
			}

			// Try input value fallback
			List<WebElement> qtyInputs = product.findElements(By.xpath(".//input[contains(@class,'cp_quantity_input')]"));
			if (!qtyInputs.isEmpty()) {
				String inputVal = qtyInputs.get(0).getAttribute("value").trim();
				if (!inputVal.isEmpty()) {
					return Integer.parseInt(inputVal);
				}
			}

		} catch (Exception e) {
			System.out.println(RED + "⚠️ Failed to retrieve quantity — defaulting to 1" + RESET);
		}
		return 1; // Default fallback
	}


	public void performCheckoutCalculationAndExcelLog(String stage) {
		System.out.println(MAGENTA + "\n=== CHECKOUT CALCULATION STAGE: " + stage + " ===" + RESET);
		List<WebElement> productBlocks = driver.findElements(By.xpath(".//div[@class='cart_prod_card ']"));
		boolean skipStage = false;

		// 🔹 Step 0 — Handle Quantity Change Logic Before Calculation
		for (WebElement product : productBlocks) {
			String productName = safeGetText(product, ".//a[@class='cp_name']");
			int quantity = getQuantity(product);
			int maxQtyAllowed = 2;
			int minQtyAllowed = 1;

			if ("AFTER_INCREASE".equalsIgnoreCase(stage)) {
				if (quantity >= maxQtyAllowed) {
					System.out.println(YELLOW + "⚠️ Max quantity reached for '" + productName + "' (" + quantity + ") — skipping increase stage" + RESET);
					skipStage = true;
				} else {
					System.out.println(GREEN + "✅ Increasing quantity for '" + productName + "'" + RESET);
					WebElement plusBtn = product.findElement(By.xpath(".//button[@class='cp_plus_btn']"));
					plusBtn.click();
					waitForQtyUpdate(product, quantity + 1);
				}
			}

			if ("AFTER_DECREASE".equalsIgnoreCase(stage)) {
				if (quantity <= minQtyAllowed) {
					System.out.println(YELLOW + "⚠️ Min quantity reached for '" + productName + "' (" + quantity + ") — skipping decrease stage" + RESET);
					skipStage = true;
				} else {
					System.out.println(GREEN + "✅ Decreasing quantity for '" + productName + "'" + RESET);
					WebElement minusBtn = product.findElement(By.xpath(".//button[@class='cp_minus_btn']"));
					minusBtn.click();
					waitForQtyUpdate(product, quantity - 1);
				}
			}
		}

		if (skipStage) {
			System.out.println(CYAN + "ℹ Stage '" + stage + "' skipped — No valid quantity change possible" + RESET);
			return;
		}

		// 🔹 Step 1 — Product details
		double totalMrp = 0, totalDiscounted = 0, totalYouSaved = 0;
		for (WebElement product : productBlocks) {
			int quantity = getQuantity(product);
			double mrp = safeParseDouble(safeGetText(product, ".//div[@class='cp_actual_price']"));
			double discounted;
			if (product.findElements(By.xpath(".//span[@class='product_list_cards_actual_price_txt']")).size() > 0) {
				discounted = safeParseDouble(safeGetText(product, ".//span[@class='product_list_cards_actual_price_txt']"));
			} else {
				discounted = safeParseDouble(safeGetText(product, ".//div[@class='cp_current_price']"));
			}

			totalMrp += mrp * quantity;
			totalDiscounted += discounted * quantity;
			totalYouSaved += (mrp - discounted) * quantity;

			String productName = safeGetText(product, ".//a[@class='cp_name']");
			System.out.printf(CYAN + "%-25s | Qty: %d | MRP: %.2f | Disc: %.2f | Line MRP: %.2f | Line Disc: %.2f | Saved: %.2f%n" + RESET,
					productName, quantity, mrp, discounted, mrp * quantity, discounted * quantity, (mrp - discounted) * quantity);
		}

		// --- Charges / Discounts ---
		double giftWrap = getGiftWrapCharge();
		double express = getExpressCharge();
		double customization = getCustomizationCharge();
		double flatDiscount = getFlatDiscount();

		applyCouponIfNotApplied();                 // ensure a coupon is applied (once)
		double coupon = getCurrentCouponDiscount(); // ✅ always refreshed each stage
		double threads = applyThreadsIfNotApplied();// ✅ stable (per your rule)

		// 🔹 Step 2 — Price breakdown
		double finalSaved = totalYouSaved + flatDiscount + coupon + threads;
		double finalAmount = totalDiscounted + giftWrap + express + customization - flatDiscount - coupon - threads;

		System.out.println(YELLOW + "\n------ PRICE BREAKDOWN ------" + RESET);
		System.out.printf("Base Discounted Price : %.2f%n", totalDiscounted);
		System.out.printf(RED + "+ Gift Wrap : %.2f%n" + RESET, giftWrap);
		System.out.printf(RED + "+ Express Delivery : %.2f%n" + RESET, express);
		System.out.printf(RED + "+ Customization : %.2f%n" + RESET, customization);
		System.out.printf(GREEN + "- Flat Discount : %.2f%n" + RESET, flatDiscount);
		System.out.printf(GREEN + "- Coupon Discount : %.2f%n" + RESET, coupon);
		System.out.printf(GREEN + "- Threads Discount : %.2f%n" + RESET, threads);
		System.out.println(YELLOW + "-----------------------------" + RESET);
		System.out.printf(GREEN + "Final Amount = %.2f%n" + RESET, finalAmount);

		System.out.println("\n" + CYAN + "💰 SAVINGS BREAKDOWN" + RESET);
		System.out.printf("Product Savings : %.2f%n", totalYouSaved);
		System.out.printf("+ Flat Discount : %.2f%n", flatDiscount);
		System.out.printf("+ Coupon Discount : %.2f%n", coupon);
		System.out.printf("+ Threads Discount : %.2f%n", threads);
		System.out.println(YELLOW + "-----------------------------" + RESET);
		System.out.printf(GREEN + "Total You Saved = %.2f%n" + RESET, finalSaved);

		// 🔹 Step 3 — UI values
		double actualFinalAmount = safeParseDouble(safeGetTextDriver("//div[@class='price_details_pair Cls_cart_total_amount']"));
		double actualSavedAmount = safeParseDouble(safeGetTextDriver("//div[@class='price_details_pair Cls_cart_saved_amount']"));

		// 🔹 Step 4 — UI vs Expected comparison
		System.out.println(MAGENTA + "\n=== UI vs EXPECTED CHECK ===" + RESET);
		System.out.printf(BLUE + "UI Final Amount : %.2f" + RESET + " | " + GREEN + "Expected Final Amount : %.2f%n" + RESET,
				actualFinalAmount, finalAmount);
		System.out.printf(BLUE + "UI You Saved : %.2f" + RESET + " | " + GREEN + "Expected You Saved : %.2f%n" + RESET,
				actualSavedAmount, finalSaved);

		// 🔹 Step 5 — Pass/Fail logic
		boolean pass = true;
		String failReason = "";

		if (Math.abs(finalSaved - actualSavedAmount) > 0.01) {
			pass = false;
			failReason += String.format("You Saved mismatch! Expected: %.2f, Found: %.2f. ", finalSaved, actualSavedAmount);
		}

		if (Math.abs(finalAmount - actualFinalAmount) > 0.01) {
			pass = false;
			failReason += String.format("Final Amount mismatch! Expected: %.2f, Found: %.2f. ", finalAmount, actualFinalAmount);
		}

		// 🔹 Step 6 — Assertions
		assertEquals("'You Saved' mismatch!", finalSaved, actualSavedAmount, 0.01);
		assertEquals("Final Amount mismatch!", finalAmount, actualFinalAmount, 0.01);

		// 🔹 Step 7 — Excel report (only FINAL stage)
		if ("FINAL".equalsIgnoreCase(stage)) {
			String tcId = "TC_UI_Zlaata_COP_25";
			String tcDescription = tcId + " | Verify That Calculation Functionality is Working | \"TD_UI_Zlaata_COP_25\"";
			ExcelReportUtil.results.add(new ExcelReportUtil.TestResult(
					tcId,
					tcDescription,
					0,
					System.getProperty("user.name"),
					pass ? "Pass" : "Fail",
							failReason,
							ExcelReportUtil.captureScreenshot(driver, tcId),
							"Checkout Page"
					));
		}
	}

	// ✅ Wait until quantity updates
	private void waitForQtyUpdate(WebElement productBlock, int expectedQty) {
		new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.textToBe(
				By.xpath(".//div[@class='cp_quantity_wrap']"),
				String.valueOf(expectedQty)
				));
	}
	
	public void deleteAllProductsFromCart() {

	   
	    driver.findElement(By.xpath("//a[@class='Cls_cart_btn Cls_redirect_restrict']")).click();
	    Common.waitForElement(1);
	    try {
	        if (driver.findElement(By.xpath("//h5[contains(text(),'Your bag is empty')]")).isDisplayed()) {
	            System.out.println("🛍️ Cart already empty. No delete action needed.");
	            return; 
	        }
	    } catch (NoSuchElementException ignored) {
	        
	    }
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

	    try {
	        if (driver.findElement(By.xpath("//h5[contains(text(),'Your bag is empty')]")).isDisplayed()) {
	            System.out.println("🛍️ Cart is empty, Continue Shopping displayed.");
	        }
	    } catch (NoSuchElementException e) {
	        System.out.println("ℹ️ Bag is not empty message not found.");
	    }
	}

	//BAG TO CHECKOUT CODE MERGE
	public void itemCount() {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
		Common.waitForElement(5);
		click(bagIcon);
		try {
			Common.waitForElement(2);
			WebElement bagContinueShop = driver.findElement(By.xpath("//a[@class='empty_cart_continue_btn btn___2']"));
			if (bagContinueShop.isDisplayed()) {
//				click(closeBag);
				ProductListingPage pLp = new ProductListingPage(driver);
				pLp.addToCart();
				Common.waitForElement(5);
				click(bagIcon);
				String bagCount = bagItemCount.getText();
				System.out.println("The total Item count is:"+bagCount);

			}
			else {
				String bagCount = bagItemCount.getText();
				System.out.println("The total Item count is:"+bagCount);
			}
		}
		catch (Exception e) {
			System.out.println("Caught an exception: " + e.getMessage());
			NoSuchElementException e1 = new NoSuchElementException("A NoSuchElementException exception occurred");
			e1.initCause(e);
			throw e1;
		}
	}
//	public void addRandomProduct() {
//		  String GREEN = "\u001B[32m";
//		    String RED   = "\u001B[31m";
//		    String CYAN  = "\u001B[36m";
//		    String RESET = "\u001B[0m";
//		    String line = "──────────────────────────────────────────────────────────────";
//		    System.out.println(CYAN + line + RESET);
//	    // Launch home
//	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//	    
//	    click(zlaataIndiaShopButton);
//	    
//		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//		 // Hover and open category
//		    Actions actions = new Actions(driver);
//		    actions.moveToElement(shopMenu).perform();
//		    actions.moveToElement(randomcategory).click().perform();
//
//		    System.out.println(CYAN + "🔍 Navigated to category page" + RESET);
//
//		    // 🔹 Wait for product card
//		    WebElement productCard = wait.until(
//		            ExpectedConditions.visibilityOfElementLocated(
//		                    By.xpath("(//div[@class='prod_listing_card'])")
//		            )
//		    );
//		    
//
//		    Collections.shuffle(products);
//		    products.get(0).click();
//		    Common.waitForElement(2);
//		    
//		    WebElement addToBagBtn = productCard.findElement(
//		            By.xpath(".//button[contains(@class,'prod_add_to_cart')]")
//		    );
//		    ((JavascriptExecutor) driver)
//            .executeScript("arguments[0].scrollIntoView({block:'center'});", addToBagBtn);
//    ((JavascriptExecutor) driver)
//            .executeScript("arguments[0].click();", addToBagBtn);
//    Common.waitForElement(2);
//    System.out.println(GREEN + "✅ PLP Add to Bag clicked" + RESET);
//    // 🔹 Click Add to Bag in popup
//    WebElement popupAddBtn = wait.until(
//            ExpectedConditions.elementToBeClickable(
//                    By.xpath("//button[contains(@class,'add_bag_prod_buy_now_btn')]")
//            )
//    );
//    popupAddBtn.click();
//
//    System.out.println(GREEN + "✅ Product added to cart" + RESET);
//    Common.waitForElement(2);
//    
//    ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");
//    Common.waitForElement(2);
//    // 🔹 Open cart
//    WebElement cartIcon = wait.until(
//            ExpectedConditions.elementToBeClickable(
//                    By.xpath("//button[contains(@class,'header_cta_btn Cls_cart_btn ')]")
//            )
//    );
//    cartIcon.click();
//	}
	
	public void addRandomProduct() {

	    String GREEN = "\u001B[32m";
	    String CYAN  = "\u001B[36m";
	    String RESET = "\u001B[0m";

	    System.out.println(CYAN + "────────────────────────────────────────────" + RESET);

	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader().getApplicationUrl());

	    click(zlaataIndiaShopButton);
	    
	    Common.waitForElement(10);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // Hover shop and open category
	    Actions actions = new Actions(driver);
	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(randomcategory).click().perform();
	    
	    Common.waitForElement(5);

	    System.out.println(CYAN + "🔍 Navigated to category page" + RESET);

	    // 🔹 Get all product cards
	    List<WebElement> products = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                    By.xpath("//div[@class='prod_listing_card']")
	            )
	    );

	    // Shuffle products
	    Collections.shuffle(products);

	    // Select random product
	    WebElement product = products.get(0);

	    // Find Add to Bag button
	    WebElement addToBagBtn = product.findElement(
	            By.xpath(".//button[contains(@class,'prod_add_to_cart')]")
	    );

	    // Scroll to button
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", addToBagBtn);

	    // Click Add to Bag
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].click();", addToBagBtn);

	    System.out.println(GREEN + "✅ PLP Add to Bag clicked" + RESET);

	    // Wait for popup
	    WebElement popupAddBtn = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[contains(@class,'add_bag_prod_buy_now_btn')]")
	            )
	    );

	    popupAddBtn.click();

	    System.out.println(GREEN + "✅ Product added to cart" + RESET);

	    Common.waitForElement(2);

	    // Scroll to top
	    ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");

	    Common.waitForElement(2);

	    // Open cart
	    WebElement cartIcon = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[contains(@class,'Cls_cart_btn')]")
	            )
	    );

	    cartIcon.click();
	}
	public void itemCountCartPage() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader().getApplicationUrl());

	    click(bagIcon);
	    Common.waitForElement(2);

	    List<WebElement> itemCountElements = driver.findElements(
	            By.xpath("//span[contains(@class,'Cls_bag_items_count')]"));

	    if (itemCountElements.isEmpty() ||
	            itemCountElements.get(0).getText().replaceAll("[^0-9]", "").equals("0")) {

	        System.out.println("🛒 Cart empty → Adding product");

	        addRandomProduct();
	        Common.waitForElement(3);

	        // Scroll to top after adding product
	       
	        // Click cart icon
//	        click(bagIcon);
//	        Common.waitForElement(2);
	    }

	    WebElement itemCountEle = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//span[contains(@class,'Cls_bag_items_count')]"))
	    );

	    String itemCountText = itemCountEle.getText().trim();
	    String itemCount = itemCountText.replaceAll("[^0-9]", "");

	    System.out.println("✅ Cart Item Count: " + itemCount);

	    Assert.assertFalse("❌ Item count is empty", itemCount.isEmpty());

	    Assert.assertTrue("❌ Item count should be greater than 0",
	            Integer.parseInt(itemCount) > 0);

	    System.out.println("🎉 Cart item count validation successful");
	}


	public void wishListInbag() {
		LoginPage login = new LoginPage(driver);
		login.userLogin();
		Common.waitForElement(5);
		click(bagIcon);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		List<String> movedProductNames = new ArrayList<>();

		// STEP 1: Check if products exist in the bag
		List<WebElement> initialProducts = driver.findElements(By.xpath("//div[@class='cp_name_row']"));
		int productCount = initialProducts.size();

		if (productCount == 0) {
			System.out.println("⚠️ No products in Bag to move to Wishlist.");
			return;
		}

		// STEP 2: Move each product to wishlist, one by one
		while (true) {
			try {
				List<WebElement> products = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
				if (products.isEmpty()) break;

				// Always get the first fresh element after DOM change
				WebElement nameElement = driver.findElement(By.xpath("(//div[@class='cp_name_row'])[1]"));
				String productName = nameElement.getText().trim();
				movedProductNames.add(productName);

				WebElement moveToWishlistBtn = driver.findElement(
						By.xpath("(//div[@class='cp_wishlist_btn Cls_move_to_wishlist_btn'])[1]"));
//				js.executeScript("arguments[0].scrollIntoView(true);", moveToWishlistBtn);
				moveToWishlistBtn.click();

				Common.waitForElement(2); // Wait for DOM to stabilize
			} catch (StaleElementReferenceException se) {

			} catch (Exception e) {
				System.out.println("❌ Error while moving product to wishlist: " + e.getMessage());
				break;
			}
		}

		// STEP 3: Close the bag after all items moved
//		try {
//			click(closeBag);
//		} catch (Exception e) {
//			System.out.println("ℹ️ Close bag button not available or already closed.");
//		}

		// STEP 4: Navigate to Wishlist page
		try {
			WebElement wishlistBtn = driver.findElement(By.xpath("//a[@class='wishlist-icon Cls_redirect_restrict']"));     
			wishlistBtn.click();
			Common.waitForElement(3);
		} catch (Exception e) {
			System.out.println("❌ Unable to navigate to wishlist: " + e.getMessage());
			return;
		}

		// STEP 5: Validate wishlist items
		List<WebElement> wishlistItems = driver.findElements(By.xpath("//h2[@class='product_list_cards_heading']"));
		List<String> wishlistProductNames = wishlistItems.stream()
				.map(e -> e.getText().trim())
				.collect(Collectors.toList());

		for (String moved : movedProductNames) {
			if (wishlistProductNames.contains(moved)) {
				System.out.println("✅ Verified in wishlist: " + moved);
			} else {
				System.out.println("❌ Not found in wishlist: " + moved);
			}
		}
	}



	public void bagDelete() throws InterruptedException {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		    // Open home
		    driver.get(FileReaderManager.getInstance()
		            .getConfigReader().getApplicationUrl());

		    // Open Cart
		    click(bagIcon);
		    Common.waitForElement(2);

		    // ---------------- CHECK IF ITEM COUNT EXISTS ----------------
		    List<WebElement> itemCountElements = driver.findElements(
		            By.xpath("//span[contains(@class,'Cls_bag_items_count')]"));

		    if (itemCountElements.isEmpty()
		            || itemCountElements.get(0).getText().trim().equals("0")) {

		        System.out.println("🛒 Cart empty → Adding product");

		        // Add product
		        addRandomProduct();
		        Common.waitForElement(3);

		        // Open cart again
		        click(bagIcon);
		        Common.waitForElement(2);
		    }
		    int deletedCount = 0;

		    // ---------------- DELETE ALL PRODUCTS ----------------
		    while (true) {
		        List<WebElement> deleteButtons =
		                driver.findElements(By.xpath("//div[@title='Delete']"));

		        if (deleteButtons.isEmpty()) {
		            break; // No more items
		        }

		        try {
		            System.out.println("🗑️ Deleting product " + (deletedCount + 1));

		            WebElement deleteBtn = deleteButtons.get(0);

		            wait.until(ExpectedConditions.elementToBeClickable(deleteBtn));
		            Common.waitForElement(1);
		            deleteBtn.click();
		            Common.waitForElement(1);
		            deletedCount++;
		            Common.waitForElement(1);

		        } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
		            System.out.println("⚠️ Retrying delete due to DOM update...");
		            Common.waitForElement(1);
		        }
		    }

		    System.out.println("✅ Total products deleted from bag: " + deletedCount);

		    // ---------------- VERIFY EMPTY BAG MESSAGE ----------------
		    try {
		        WebElement emptyMsg = wait.until(
		                ExpectedConditions.visibilityOfElementLocated(
		                        By.xpath("//a[@class='empty_bag_shop_btn btn___2']")
		                )
		        );

		        Assert.assertTrue("❌ Empty bag message not displayed", emptyMsg.isDisplayed());
		        System.out.println("🛒✅ Bag is empty message displayed");

		    } catch (TimeoutException e) {
		        Assert.fail("❌ Bag is NOT empty. 'Your bag is empty' message not found");
		    }
		}


public void changeTheProductSizeCartPage() {
	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Open home
	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader().getApplicationUrl());
	    
	    // Open Cart
	    click(bagIcon);
	    Common.waitForElement(2);

	    // ---------------- CHECK IF ITEM COUNT EXISTS ----------------

	    List<WebElement> cartProducts = driver.findElements(
	    	    By.xpath("//div[contains(@class,'Cls_cart_prod_card')]")
	    	);
	    
	    if (cartProducts.isEmpty()) {

	        System.out.println("🛒 Cart empty → Adding product");

	        // Add product
	        addRandomProduct();
	        Common.waitForElement(3);

	        // Open cart again
	        click(bagIcon);
	        Common.waitForElement(2);
	    }
	    WebElement firstProduct = driver.findElement(
	    	    By.xpath("(//div[contains(@class,'Cls_cart_prod_card')])[1]")
	    	);
	    
	    System.out.println("🛍️ Found first product in cart");
	    
	    List<WebElement> topSize = firstProduct.findElements(
	    	    By.xpath(".//div[contains(@class,'cp_size_btn_card')][.//h6[text()='Top']]")
	    	);

	    	List<WebElement> bottomSize = firstProduct.findElements(
	    	    By.xpath(".//div[contains(@class,'cp_size_btn_card')][.//h6[text()='Bottom']]")
	    	);

	    	List<WebElement> singleSize = firstProduct.findElements(
	    	    By.xpath(".//div[contains(@class,'cp_size_btn_card')][.//h6[text()='Size']]")
	    	);

	    	if (!topSize.isEmpty() && !bottomSize.isEmpty()) {
	    	    System.out.println("✅ First product has TOP + BOTTOM size");
	    	    changeSizeForFirstProduct(firstProduct, "Top");
	    	    changeSizeForFirstProduct(firstProduct, "Bottom");
	    	}
	    	else if (!singleSize.isEmpty()) {
	    	    System.out.println("✅ First product has SINGLE size");
	    	    changeSizeForFirstProduct(firstProduct, "Size");
	    	}
	    	else {
	    	    System.out.println("⚠️ No size available for first product");
	    	}
}
private void changeSizeForFirstProduct(WebElement productCard, String sizeType) {

    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    WebElement sizeCard = productCard.findElement(
        By.xpath(".//div[contains(@class,'cp_size_btn_card')][.//h6[text()='" + sizeType + "']]")
    );

    // Open dropdown
    WebElement dropdownArrow = sizeCard.findElement(
        By.xpath(".//div[contains(@class,'cp_drop_arrow')]")
    );
    js.executeScript("arguments[0].click();", dropdownArrow);
    Common.waitForElement(2);

    // Get count first (important to avoid stale element)
    List<WebElement> sizeOptions = sizeCard.findElements(
        By.xpath(".//ul[contains(@class,'cp_dropdown_content')]//li")
    );

    if (sizeOptions.size() <= 1) {
        System.out.println("⚠️ Only one " + sizeType + " size available");
        return;
    }

    System.out.println("📋 Available " + sizeType + " sizes:");
    
    js.executeScript("arguments[0].click();", dropdownArrow);

    for (int i = 0; i < sizeOptions.size(); i++) {

        // 🔁 Re-open dropdown every time
        js.executeScript("arguments[0].click();", dropdownArrow);
        Common.waitForElement(1);

        List<WebElement> refreshedOptions = driver.findElements(
                By.xpath("//ul[contains(@class,'cp_dropdown_content')]//li")
        );

        WebElement option = refreshedOptions.get(i);
        String sizeText = option.getText().trim();

        System.out.println("➡️ Trying size: " + sizeText);
        Common.waitForElement(1);
        js.executeScript("arguments[0].click();", option);
        Common.waitForElement(2);

        // ✅ Re-locate selected size AFTER DOM refresh
        WebElement selectedSize = driver.findElement(
        	    By.xpath("//div[contains(@class,'cp_selected_size')]")
        	);

        String selectedText = selectedSize.getText().trim();

        Assert.assertEquals(
        	    "❌ Size mismatch after selection. Expected: " + sizeText + " | Actual: " + selectedText,
        	    sizeText.trim().toUpperCase(),
        	    selectedText.trim().toUpperCase()
        	);

        	System.out.println("✅ Size selected & displayed: " + selectedText);
    }
}



	public void changeTheProductSize() throws InterruptedException {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
		Common.waitForElement(5);
		click(bagIcon);
		Common.waitForElement(2);
		List<WebElement> finalBagCount = driver.findElements(By.xpath("//span[@class='cart_count_num Cls_cart_count_num']"));
		if (finalBagCount.isEmpty()) {

			System.out.println("🛒 Adding product to check if bag count displays...");
			// Add product again to verify count shows up
			ProductListingPage product = new ProductListingPage(driver);
			product.addToCart();
			Common.waitForElement(5);
			click(bagIcon);
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int productCount = driver.findElements(By.xpath("//div[@class='cart_prod_card ']")).size();
		System.out.println("🛍️ Found " + productCount + " products in bag.");

		for (int i = 0; i < productCount; i++) {
			try {
				if (i > 0) {
					WebElement bag = driver.findElement(By.xpath("//a[@class='Cls_cart_btn Cls_redirect_restrict']"));
					js.executeScript("arguments[0].click();", bag);
					Common.waitForElement(2);
				}

				List<WebElement> productBlocks = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
				WebElement currentProduct = productBlocks.get(i);
				js.executeScript("arguments[0].scrollIntoView(true);", currentProduct);
				Common.waitForElement(1);

				List<WebElement> dropdowns = currentProduct.findElements(By.xpath(".//div[@class='cp_drop_arrow']"));
				int dropdownCount = dropdowns.size();
				System.out.println("\n🔄 Changing size for Product #" + (i + 1));

				// Top size change
				if (dropdownCount >= 1) {
					dropdowns.get(0).click(); // Open top size dropdown
					WebElement topOptionList = currentProduct.findElement(By.xpath("//ul[@class='cp_dropdown_content']"));
					Common.waitForElement(2);

					List<WebElement> topOptions = topOptionList.findElements(By.tagName("li"));
					if (topOptions.size() > 1) {
						js.executeScript("arguments[0].click();", topOptions.get(1));
						System.out.println("✅ Top size changed for Product #" + (i + 1));
					} else {
						System.out.println("⚠️ Only one top size option available for Product #" + (i + 1));
					}
				} else {
					System.out.println("❌ Top size dropdown not found for Product #" + (i + 1));
				}

				// Bottom size change
				if (dropdownCount == 2) {
					dropdowns.get(1).click(); // Open bottom size dropdown
					WebElement bottomOptionList = currentProduct.findElements(By.xpath("//ul[@class='cp_dropdown_content']")).get(1);
					Common.waitForElement(2);

					List<WebElement> bottomOptions = bottomOptionList.findElements(By.tagName("li"));
					if (bottomOptions.size() > 1) {
						js.executeScript("arguments[0].click();", bottomOptions.get(1));
						System.out.println("✅ Bottom size changed for Product #" + (i + 1));
					} else {
						System.out.println("⚠️ Only one bottom size option available for Product #" + (i + 1));
					}
				} else {
					System.out.println("❌ Bottom size dropdown not found for Product #" + (i + 1));
				}

			} catch (StaleElementReferenceException e) {
				System.out.println("🔁 Retrying due to stale element for Product #" + (i + 1));
				i--; // Retry
			} catch (ElementClickInterceptedException e) {
				System.out.println("⚠️ Click intercepted for Product #" + (i + 1) + ". Scrolling and retrying...");
				js.executeScript("window.scrollBy(0, -100);");
			} catch (Exception e) {
				System.out.println("❌ Unexpected error for Product #" + (i + 1) + ": " + e.getMessage());
			}
		}

		System.out.println("\n✅ Completed size change for all products.");
	}

	
	int prevQty ;
	public void increaseAndDecreaseQTYCartPage() throws InterruptedException {
		bagDelete();
		

		addRandomProduct();
        Common.waitForElement(3);
        
        List<WebElement> products = driver.findElements(
                By.xpath("//div[contains(@class,'cart_prod_card')]"));

        if (products.isEmpty()) {
            Assert.fail("❌ Cart is empty. Cannot verify quantity behavior.");
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement product = driver.findElement(
                By.xpath("(//div[contains(@class,'cart_prod_card')])[1]")
        );

        WebElement incBtn = product.findElement(
                By.xpath(".//button[contains(@class,'cp_quantity_increase_btn')]")
        );

        WebElement decBtn = product.findElement(
                By.xpath(".//button[contains(@class,'cp_quantity_decrease_btn')]")
        );

        WebElement qty = product.findElement(
                By.xpath(".//div[contains(@class,'cp_selected_quantity')]")
        );

        WebElement totalAmount = driver.findElement(
                By.xpath("//div[contains(@class,'price_details_pair Cls_cart_total_amount')]")
        );

        // 🔹 1️⃣ Decrease button must be disabled initially
        Assert.assertTrue(
                "Decrease button should be disabled initially",
                decBtn.getAttribute("class").contains("disabled")
        );
        System.out.println("✅ Decrease button is disabled initially");

         prevQty = Integer.parseInt(qty.getText().trim());
        int prevAmount = Integer.parseInt(
                totalAmount.getAttribute("data-totalprice").trim()
        );

        // 🔹 2️⃣ Click increase until it becomes disabled
        while (!incBtn.getAttribute("class").contains("disabled")) {

            incBtn.click();
            Common.waitForElement(2);
            // wait until qty changes
            wait.until(d ->
                    Integer.parseInt(qty.getText().trim()) != prevQty
            );

            int currentQty = Integer.parseInt(qty.getText().trim());
            int currentAmount = Integer.parseInt(
                    totalAmount.getAttribute("data-totalprice").trim()
            );

            // 🔹 3️⃣ ASSERT quantity increased
            Assert.assertEquals(
                    "Quantity did not increase correctly",
                    prevQty + 1,
                    currentQty
            );

            // 🔹 4️⃣ ASSERT price increased
            Assert.assertTrue(
                    "Total amount did not increase",
                    currentAmount > prevAmount
            );

            System.out.println("✅ Qty: " + prevQty + " → " + currentQty +
                    " | Amount: " + prevAmount + " → " + currentAmount);

            prevQty = currentQty;
            prevAmount = currentAmount;
        }

        // 🔹 5️⃣ Increase button must be disabled at max qty
        Assert.assertTrue(
                "Increase button should be disabled at max quantity",
                incBtn.getAttribute("class").contains("disabled")
        );

        System.out.println("✅ Increase button disabled at max quantity");
    }
		
		
	



	public void increaseAndDecreaseQTY() throws InterruptedException {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
		Common.waitForElement(5);
		click(bagIcon);
		Common.waitForElement(2);
		List<WebElement> products = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
		System.out.println("🛍️ Total products in Bag: " + products.size());

		if (products.isEmpty()) {
			
			System.out.println("🛒 Adding product to the bag...");

			// Add product again to verify count shows up
			ProductListingPage product = new ProductListingPage(driver);
			product.addToCart();
			Common.waitForElement(5);
			click(bagIcon);
		}
		products = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
		for (int i = 0; i < products.size(); i++) {
			try {
				// Re-fetch the product list to avoid stale elements
				List<WebElement> currentProducts = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
				WebElement product = currentProducts.get(i);

				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
				Thread.sleep(500);

				// Re-locate buttons within the product container using relative XPath
				WebElement incBtn = product.findElement(By.xpath(".//button[contains(@class, 'cp_quantity_increase_btn')]"));
				WebElement decBtn = product.findElement(By.xpath(".//button[contains(@class, 'cp_quantity_decrease_btn')]"));

				int incCount = 0;
				int decCount = 0;

				// INCREASE QTY
				if (incBtn.isDisplayed() && incBtn.isEnabled()) {
					try {
						incBtn.click();
					} catch (ElementClickInterceptedException e) {
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", incBtn);
					}
					incCount++;
					Thread.sleep(400);
				} else {
					System.out.println("🔒 Product #" + (i + 1) + ": Increase button is disabled or not displayed.");
				}

				// Re-fetch product after change
				currentProducts = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
				product = currentProducts.get(i);

				decBtn = product.findElement(By.xpath(".//button[contains(@class, 'cp_quantity_decrease_btn')]"));

				// DECREASE QTY
				if (decBtn.isDisplayed() && decBtn.isEnabled()) {
					try {
						decBtn.click();
					} catch (ElementClickInterceptedException e) {
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", decBtn);
					}
					decCount++;
					Thread.sleep(400);
				} else {
					System.out.println("🔒 Product #" + (i + 1) + ": Decrease button is disabled or not displayed.");
				}

				System.out.println("✅ Product #" + (i + 1) + ": Increased " + incCount + "x, Decreased " + decCount + "x");

			} catch (StaleElementReferenceException e) {
				System.out.println("⚠️ Product #" + (i + 1) + ": Stale element error - Retrying...");
				i--; // retry the same product
				Thread.sleep(500);
			} catch (Exception e) {
				System.out.println("⚠️ Product #" + (i + 1) + ": Unexpected error - " + e.getMessage());
			}
		}
	}


	public void newProductToBag() throws InterruptedException {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
		Common.waitForElement(2);
		ProductListingPage pLp = new  ProductListingPage(driver);
		pLp.addToCart();


	}

	public void verifyBagCount() {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
		Common.waitForElement(5);
		click(bagIcon);

		try {
			// Initial Bag Count Check
			List<WebElement> initialBagCount = driver.findElements(By.xpath("//span[@class='cart_count_num Cls_cart_count_num']"));
			if (initialBagCount.isEmpty()) {
				System.out.println("👜 Bag count is zero.");
				return;
			}

			int productsCount = driver.findElements(By.xpath("//div[@class='cart_prod_card ']")).size();
			System.out.println("🛍️ Total products in Bag initially: " + productsCount);

			// Process products one by one
			while (true) {
				List<WebElement> products = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
				if (products.isEmpty()) break;

				try {
					WebElement product = products.get(0); // Always get the first one
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
					Common.waitForElement(1);

					boolean actionPerformed = false;

					List<WebElement> wishlistBtns = product.findElements(By.xpath(".//div[@class='cp_wishlist_btn Cls_move_to_wishlist_btn']"));
					List<WebElement> deleteBtns = product.findElements(By.xpath(".//div[@title='Delete']"));

					if (!wishlistBtns.isEmpty() && wishlistBtns.get(0).isDisplayed()) {
						wishlistBtns.get(0).click();
						System.out.println("💖 Product moved to Wishlist.");
						actionPerformed = true;
					} else if (!deleteBtns.isEmpty() && deleteBtns.get(0).isDisplayed()) {
						deleteBtns.get(0).click();
						System.out.println("🗑️ Product deleted from Bag.");
						actionPerformed = true;
					} else {
						System.out.println("⚠️ No wishlist or delete option found for product.");
					}

					if (actionPerformed) {
						Common.waitForElement(2);
						driver.navigate().refresh(); // Refresh to update DOM
						Common.waitForElement(3);
					}

				} catch (StaleElementReferenceException stale) {
					//					System.out.println("♻️ Stale element encountered, retrying iteration...");
				} catch (Exception e) {
					System.out.println("⚠️ Error processing a product: " + e.getMessage());
				}
			}

			// Final Bag Count Check
			List<WebElement> finalBagCount = driver.findElements(By.xpath("//span[@class='cart_count_num Cls_cart_count_num']"));
			if (finalBagCount.isEmpty()) {
				System.out.println("👜 All products removed or moved. Bag count not displayed.");
				System.out.println("🛒 Adding product to check if bag count displays...");

				// Add product again to verify count shows up
				ProductListingPage product = new ProductListingPage(driver);
				product.addToCart();
				Common.waitForElement(2);

				List<WebElement> newBagCount = driver.findElements(By.xpath("//span[@class='cart_count_num Cls_cart_count_num']"));
				if (!newBagCount.isEmpty()) {
					System.out.println("✅ Bag count is now visible after adding product. Count: " + newBagCount.get(0).getText());
				} else {
					System.out.println("❌ Bag count still not visible.");
				}
			} else {
				System.out.println("✅ Final bag count is: " + finalBagCount.get(0).getText());
			}

		} catch (Exception e) {
			System.out.println("❌ Scenario failed with exception: " + e.getMessage());
		}
	}


	public void verifyItemCountChanges() throws InterruptedException {
		LoginPage login = new LoginPage(driver);
		login.userLogin();
		Common.waitForElement(5);
		click(bagIcon);

		// Add a product if bag is empty
		if (isBagEmpty()) {
			System.out.println("👜 Bag is empty. Adding a product.");
			Common.waitForElement(5);
			ProductDetailsPage pDP = new ProductDetailsPage(driver);
			pDP.buyNow(Hooks.getScenario());
		}

		int retryCount = 0;
		int maxRetries = 5;

		while (retryCount < maxRetries) {
			retryCount++;
			try {
				List<WebElement> products = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
				if (products.isEmpty()) break;

				WebElement product = driver.findElement(By.xpath("(//div[@class='cart_prod_card '])[1]"));

				if (tryMoveToWishlist(product) || tryDeleteFromBag(product)) {
					Common.waitForElement(2);

					if (!isCountAvailable()) {
						System.out.println("🛍️ No count found. Adding new product...");
						ProductDetailsPage pDP = new ProductDetailsPage(driver);
						pDP.buyNow(Hooks.getScenario());

						if (isCountAvailable()) {
							printUpdatedCount();
							break;
						} else {
							System.out.println("⚠️ Still couldn't fetch updated count.");
						}
					} else {
						printUpdatedCount();
						break;
					}
				} else {
					System.out.println("⚠️ No move/delete option found.");
					break;
				}

			} catch (StaleElementReferenceException e) {
				System.out.println("♻️ Stale element encountered, retrying...");
			} catch (Exception e) {
				System.out.println("❌ Unexpected error: " + e.getMessage());
				break;
			}
		}
	}

	public void allButtonOnCheckoutPage() {
	    HomePage home = new HomePage(driver);
	    home.homeLaunch();

	    ProductDetailsPage pDP = new ProductDetailsPage(driver);
	    Common.waitForElement(5);

	    // Perform Buy Now
	    pDP.buyNow(Hooks.getScenario());

	    ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight/2);");
	    Common.waitForElement(2);
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -400);");
	    Common.waitForElement(2);

	    CheckoutPage checkout = new CheckoutPage(driver);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    Actions actions = new Actions(driver);
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // ================== Accessories ==================
	    wait.until(ExpectedConditions.elementToBeClickable(checkout.accessoriesButton));
	    checkout.accessoriesButton.click();
	    System.out.println("✅ Accessories clicked (default selected)");
	    Common.waitForElement(1);

	    clickForwardBackwardArrow(checkout.accessoriesButtonForwardArrow, checkout.accessoriesButtonBackwardArrow, "Accessories", wait, js, actions);

	    // ================== Recently Viewed ==================
	    wait.until(ExpectedConditions.elementToBeClickable(checkout.recentlyViewed));
	    checkout.recentlyViewed.click();
	    System.out.println("✅ Recently Viewed clicked");
	    Common.waitForElement(1);

	    clickForwardBackwardArrow(checkout.recentlyViewedForwardArrow, checkout.recentlyViewedBackwardArrow, "Recently Viewed", wait, js, actions);

	    // ================== Top Selling ==================
	    wait.until(ExpectedConditions.elementToBeClickable(checkout.topSelling));
	    checkout.topSelling.click();
	    System.out.println("✅ Top Selling clicked");
	    Common.waitForElement(1);

	    clickForwardBackwardArrow(checkout.topSellingForwardArrow, checkout.topSellingBackwardArrow, "Top Selling", wait, js, actions);
	}

	/**
	 * Click forward & backward arrow if present & active
	 */
	private void clickForwardBackwardArrow(WebElement forward, WebElement backward, String section, WebDriverWait wait, JavascriptExecutor js, Actions actions) {
	    try {
	        if (forward.isDisplayed() && forward.isEnabled()) {
	            try {
	                wait.until(ExpectedConditions.elementToBeClickable(forward)).click();
	                System.out.println("⚠️ " + section + " forward arrow clicked.");
	            } catch (Exception e) {
	                actions.moveToElement(forward).click().build().perform();
	                System.out.println("⚠️ " + section + " forward arrow clicked via Actions.");
	            }

	            try {
	                wait.until(ExpectedConditions.elementToBeClickable(backward)).click();
	                System.out.println("⬅️ " + section + " backward arrow clicked.");
	            } catch (Exception e) {
	                System.out.println("ℹ️ " + section + " backward arrow not clickable → Products less.");
	            }

	        } else {
	            System.out.println("ℹ️ " + section + " arrows not available → Products less.");
	        }
	    } catch (Exception e) {
	        System.out.println("ℹ️ " + section + " arrows not available → Products less.");
	    }
	}

		  
		   boolean isBagEmpty() {
		return driver.findElements(By.xpath("//span[@class='checkout_prod_count Cls_checkout_prod_count']")).isEmpty();
	}

	private boolean isCountAvailable() {
		return !driver.findElements(By.xpath("//span[@class='checkout_prod_count Cls_checkout_prod_count']")).isEmpty();
	}

	private void printUpdatedCount() {
		List<WebElement> updatedCount = driver.findElements(By.xpath("//span[@class='checkout_prod_count Cls_checkout_prod_count']"));
		if (!updatedCount.isEmpty()) {
			System.out.println("🔄 Updated Count: " + updatedCount.get(0).getText());
		}
	}

	private boolean tryMoveToWishlist(WebElement product) {
		try {
			WebElement wishlistBtn = product.findElement(By.xpath(".//div[@class='cp_wishlist_btn Cls_move_to_wishlist_btn']"));
			wishlistBtn.click();
			System.out.println("💖 Moved to wishlist.");
			return true;
		} catch (NoSuchElementException ignored) {
			return false;
		}
	}

	private boolean tryDeleteFromBag(WebElement product) {
		try {
			WebElement deleteBtn = product.findElement(By.xpath(".//div[@title='Delete']"));
			deleteBtn.click();
			System.out.println("🗑️ Deleted from bag.");
			return true;
		} catch (NoSuchElementException ignored) {
			return false;
		}
	}



	public void addProductToBag() {
		Common.waitForElement(5);
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();
		List<WebElement> addProduct = driver.findElements(By.xpath("//button[@class='product_list_cards_btn']"));
		Collections.shuffle(addProduct);

		if (!addProduct.isEmpty()) {
			WebElement randomProduct = addProduct.get(0);
			actions.moveToElement(randomProduct).click().build().perform();
			Common.waitForElement(5);
			click(addToCart);

		}
	}

	// Example method to add a product to cart
	public void addProductToCart(WebDriver driver) throws InterruptedException {
		ProductListingPage product = new ProductListingPage(driver);
		product.addToCart();
		System.out.println("✅ Product added to bag.");
	}

//	public void threadLine() {
//		HomePage home = new HomePage(driver);
//		home.homeLaunch();
//		Common.waitForElement(5);
//		click(bagIcon);
//		List<WebElement> products = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
//		if (products.isEmpty()) {
//			System.out.println("👜 No products in bag. Adding one product...");
//			click(closeBag);
//			ProductListingPage product = new ProductListingPage(driver);
//			product.addToCart();
//			Common.waitForElement(1);
//			click(bagIcon);
//			List<WebElement> threadBanner = driver.findElements(By.xpath("//div[@class='bag_thread_banner']"));
//			System.out.println("The banner is displaying: " +threadBanner.size());	
//
//		}
//		else {
//			List<WebElement> threadBanner = driver.findElements(By.xpath("//div[@class='bag_thread_banner']"));
//			System.out.println("The banner is displaying: " +threadBanner.size());	
//		}
//
//	}
//
//	public void youWillEarn() throws TimeoutException {
//		HomePage home = new HomePage(driver);
//		home.homeLaunch();
//		Common.waitForElement(3);
//		click(bagIcon);
//		Common.waitForElement(2);
//
//		try {
//			List<WebElement> productsInBag = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
//
//			if (productsInBag.isEmpty()) {
//				System.out.println("👜 Bag is empty. Adding a product...");
//				click(closeBag);
//				Actions actions = new Actions(driver);
//				actions.moveToElement(shopMenu);
//				actions.moveToElement(category).click().build().perform();
//
//				List<WebElement> addProduct = driver.findElements(By.xpath("//button[@class='product_list_cards_btn']"));
//				Collections.shuffle(addProduct);
//				if (!addProduct.isEmpty()) {
//					actions.moveToElement(addProduct.get(0)).click().build().perform();
//					click(addToCart);
//					Common.waitForElement(2);
//					click(bagIcon);
//					Common.waitForElement(2);
//				}
//				productsInBag = driver.findElements(By.xpath("//div[@class='cart_prod_card ']")); // refresh
//			}
//
//			int productIndex = 1;
//			int totalDiscountedAmount = 0;
//
//			for (WebElement product : productsInBag) {
//				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
//				Common.waitForElement(1);
//
//				try {
//					WebElement priceElement = product.findElement(By.xpath(".//div[@class='cp_current_price']"));
//					String priceText = priceElement.getText().replaceAll("[^0-9]", "");
//					int discountedPrice = Integer.parseInt(priceText);
//
//					WebElement qtyElement = product.findElement(By.xpath(".//div[@class='cp_quantity_wrap']"));
//					int quantity = Integer.parseInt(qtyElement.getText().trim());
//
//					int subtotal = discountedPrice * quantity;
//					totalDiscountedAmount += subtotal;
//
//					System.out.println("🔹 Product " + productIndex + ":");
//					System.out.println("   - Discounted Price: Rs. " + discountedPrice);
//					System.out.println("   - Quantity: " + quantity);
//					System.out.println("   - Subtotal: Rs. " + subtotal);
//				} catch (Exception e) {
//					System.out.println("❌ Error reading product " + productIndex + ": " + e.getMessage());
//				}
//
//				productIndex++;
//			}
//
//			// Calculate expected thread count
//			int expectedThreads = (totalDiscountedAmount >= 500) ? (totalDiscountedAmount / 500) * 10 : 0;
//
//			// Get actual displayed thread count (from overall cart)
//			WebElement threadElement = driver.findElement(By.xpath("//span[@class='Cls_cart_thread_est_amount']"));
//			int actualThreads = Integer.parseInt(threadElement.getText().replaceAll("[^0-9]", ""));
//
//			// Final Output
//			System.out.println("🧵 Total Discounted Amount: Rs. " + totalDiscountedAmount);
//			System.out.println("🔢 Expected Threads: " + expectedThreads);
//			System.out.println("📟 Actual Threads Displayed: " + actualThreads);
//
//			if (expectedThreads == actualThreads) {
//				System.out.println("✅ Thread count is correct.");
//			} else {
//				System.out.println("❌ Thread count mismatch!");
//			}
//
//		} catch (Exception e) {
//			System.out.println("❌ Unexpected error: " + e.getMessage());
//		}
//	}
//
//
//	public void threadCalculation() throws TimeoutException {
//		HomePage home = new HomePage(driver);
//		home.homeLaunch();
//		Common.waitForElement(3);
//		click(bagIcon);
//		Common.waitForElement(2);
//
//		try {
//			List<WebElement> productsInBag = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
//
//			if (productsInBag.isEmpty()) {
//				System.out.println(" Bag is empty. Adding a product...");
//				click(closeBag);
//				Actions actions = new Actions(driver);
//				actions.moveToElement(shopMenu);
//				actions.moveToElement(category).click().build().perform();
//
//				List<WebElement> addProduct = driver.findElements(By.xpath("//button[@class='product_list_cards_btn']"));
//				Collections.shuffle(addProduct);
//				if (!addProduct.isEmpty()) {
//					actions.moveToElement(addProduct.get(0)).click().build().perform();
//					click(addToCart);
//					Common.waitForElement(2);
//					click(bagIcon);
//					Common.waitForElement(2);
//				}
//				productsInBag = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
//			}
//
//			int productIndex = 1;
//			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//			for (WebElement product : productsInBag) {
//				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
//				Common.waitForElement(1);
//
//				try {
//					WebElement priceElement = product.findElement(By.xpath(".//div[@class='cp_current_price']"));
//					int discountedPrice = Integer.parseInt(priceElement.getText().replaceAll("[^0-9]", ""));
//
//					int qtyBefore = Integer.parseInt(product.findElement(By.xpath(".//div[@class='cp_quantity_wrap']")).getText().trim());
//					int subtotalBefore = discountedPrice * qtyBefore;
//
//					// Increase quantity
//					product.findElement(By.xpath(".//button[contains(@class,'cp_quantity_increase_btn')]")).click();
//
//					// Wait until quantity increases
//					int finalQtyBefore = qtyBefore;
//					wait.until(d -> {
//						try {
//							int newQty = Integer.parseInt(product.findElement(By.xpath(".//div[@class='cp_quantity_wrap']")).getText().trim());
//							return newQty > finalQtyBefore;
//						} catch (Exception e) {
//							return false;
//						}
//					});
//
//					int qtyAfterInc = Integer.parseInt(product.findElement(By.xpath(".//div[@class='cp_quantity_wrap']")).getText().trim());
//					int subtotalAfterInc = discountedPrice * qtyAfterInc;
//
//					System.out.println(" Product " + productIndex + ":");
//					System.out.println("   - Discounted Price: Rs. " + discountedPrice);
//					System.out.println("   - Quantity Before: " + qtyBefore + " → Subtotal: Rs. " + subtotalBefore);
//					System.out.println("   - Quantity After Increase: " + qtyAfterInc + " → Subtotal: Rs. " + subtotalAfterInc);
//
//					checkThreadCount("After Increase Product " + productIndex, subtotalAfterInc);
//
//					// Decrease quantity
//					product.findElement(By.xpath(".//button[contains(@class,'cp_quantity_decrease_btn')]")).click();
//
//					// Wait until quantity decreases
//					wait.until(d -> {
//						try {
//							int newQty = Integer.parseInt(product.findElement(By.xpath(".//div[@class='cp_quantity_wrap']")).getText().trim());
//							return newQty == finalQtyBefore;
//						} catch (Exception e) {
//							return false;
//						}
//					});
//
//					int qtyAfterDec = Integer.parseInt(product.findElement(By.xpath(".//div[@class='cp_quantity_wrap']")).getText().trim());
//					int subtotalAfterDec = discountedPrice * qtyAfterDec;
//
//					System.out.println("   - Quantity After Decrease: " + qtyAfterDec + " → Subtotal: Rs. " + subtotalAfterDec);
//
//					checkThreadCount("After Decrease Product " + productIndex, subtotalAfterDec);
//
//				} catch (Exception e) {
//					System.out.println(" Error with product " + productIndex + ": " + e.getMessage());
//				}
//
//				productIndex++;
//			}
//
//			return;
//
//		} catch (Exception e) {
//			System.out.println(" Unexpected error: " + e.getMessage());
//		}
//	}
//
//
//	private void checkThreadCount(String stage, int totalAmount) {
//		int expectedThreads = (totalAmount / 500) * 10;
//
//		try {        
//			String threadText = driver.findElement(By.xpath(".//span[@class='Cls_cart_thread_est_amount']")).getText().trim();
//			int actualThreads = 0;
//			if (threadText.contains("threads")) {
//				actualThreads = Integer.parseInt(threadText.replaceAll("[^0-9]", ""));
//			}
//			System.out.println("🧵 [" + stage + "]");
//			System.out.println("   - Total Discounted Amount: Rs. " + totalAmount);
//			System.out.println("   - Expected Threads: " + expectedThreads);
//			System.out.println("   - Actual Threads Displayed: " + actualThreads);
//
//			if (expectedThreads == actualThreads) {
//				System.out.println("✅ Thread count matches " + stage.toLowerCase() + ".");
//			} else {
//				System.out.println("❌ Thread count mismatch  " + stage.toLowerCase() + "!");
//			}
//
//		} catch (Exception e) {
//			System.out.println("❌ Error checking thread count  " + stage + ": " + e.getMessage());
//		}
//	}
//
//	public void youSaved() {
//		HomePage home = new HomePage(driver);
//		home.homeLaunch();
//		Common.waitForElement(5);
//		click(bagIcon);
//
//		try {
//			List<WebElement> productsInBag = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
//
//			if (productsInBag.isEmpty()) {
//				System.out.println("👜 Bag is empty. Adding product...");
//				click(closeBag);
//				Actions actions = new Actions(driver);
//				actions.moveToElement(shopMenu);
//				actions.moveToElement(category).click().build().perform();
//				List<WebElement> addProduct = driver.findElements(By.xpath("//button[@class='product_list_cards_btn']"));
//				Collections.shuffle(addProduct);
//
//				if (!addProduct.isEmpty()) {
//					WebElement randomProduct = addProduct.get(0);
//					actions.moveToElement(randomProduct).click().build().perform();
//					click(addToCart);
//					click(bagIcon);
//					Common.waitForElement(3);
//					WebElement youSaved = driver.findElement(By.xpath(".//div[@class='bfr_pair Cls_cart_saved_amount']"));
//					String yousave = youSaved.getText();
//					if (youSaved.isDisplayed()) {
//
//						System.out.println("The You saved amount displayed:"+ yousave);
//
//					}
//					else {
//						System.out.println("The You saved is not displaying please add the product");
//					}
//				}
//
//			}
//		}
//		catch (Exception e) {
//			System.out.println("⚠️ Error occurred: " + e.getMessage());
//		}
//	}
//
//
//	public void youSavedCalculation() {
//		HomePage home = new HomePage(driver);
//		home.homeLaunch();
//		Common.waitForElement(5);
//		click(bagIcon);
//
//		try {
//			if (isBagEmpty()) {
//				System.out.println("👜 Bag is empty. Adding product...");
//				click(closeBag);
//				Actions actions = new Actions(driver);
//				actions.moveToElement(shopMenu);
//				actions.moveToElement(category).click().build().perform();
//
//				List<WebElement> addProduct = driver.findElements(By.xpath("//button[@class='product_list_cards_btn']"));
//				Collections.shuffle(addProduct);
//
//				if (!addProduct.isEmpty()) {
//					WebElement randomProduct = addProduct.get(0);
//					actions.moveToElement(randomProduct).click().build().perform();
//					click(addToCart);
//					Common.waitForElement(2);
//					click(bagIcon);
//				}
//			}
//
//			Common.waitForElement(2); // Ensure bag is fully loaded
//			int expectedTotalSaved = 0;
//
//			List<WebElement> products = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
//			int productCount = products.size();
//
//			System.out.println("Total Products in Bag: " + productCount);
//			System.out.println("---------------------------------------------------");
//
//			for (int i = 0; i < productCount; i++) {
//				try {
//					WebElement product = driver.findElements(By.xpath("//div[@class='cart_prod_card ']")).get(i); // always refetch
//
//					WebElement actualPriceElement = product.findElement(By.xpath(".//div[@class='cp_actual_price']"));
//					WebElement discountedPriceElement = product.findElement(By.xpath(".//div[@class='cp_current_price']"));
//
//					String actualPriceText = actualPriceElement.getText().replaceAll("[^0-9]", "");
//					String discountedPriceText = discountedPriceElement.getText().replaceAll("[^0-9]", "");
//
//					int actualPrice = Integer.parseInt(actualPriceText);
//					int discountedPrice = Integer.parseInt(discountedPriceText);
//
//					int savedAmount = actualPrice - discountedPrice;
//					expectedTotalSaved += savedAmount;
//
//					System.out.println("Product " + (i + 1));
//					System.out.println(" - Actual Price: Rs. " + actualPrice);
//					System.out.println(" - Discounted Price: Rs. " + discountedPrice);
//					System.out.println(" - Expected 'You Saved': Rs. " + savedAmount);
//					System.out.println("---------------------------------------------------");
//				} catch (StaleElementReferenceException se) {
//					System.out.println("♻️ Stale element at product " + (i + 1) + ", retrying...");
//					i--; // retry same product
//					Common.waitForElement(1); // give time to reattach DOM
//				}
//			}
//
//			WebElement totalSavedElement = driver.findElement(By.xpath("//div[@class='bfr_pair Cls_cart_saved_amount']"));
//			String totalSavedText = totalSavedElement.getText().replaceAll("[^0-9]", "");
//			int actualTotalSaved = Integer.parseInt(totalSavedText);
//
//			System.out.println("Expected Total Saved Amount: Rs. " + expectedTotalSaved);
//			System.out.println("Actual Total Saved Amount: Rs. " + actualTotalSaved);
//
//			if (expectedTotalSaved == actualTotalSaved) {
//				System.out.println("✅ Total Saved Amount is correct.");
//			} else {
//				System.out.println("❌ Mismatch in Total Saved Amount.");
//			}
//
//		} catch (Exception e) {
//			System.out.println("⚠️ Error occurred: " + e.getMessage());
//		}
//	}
//	public void totalAmount() {
//		HomePage home = new HomePage(driver);
//		home.homeLaunch();
//		Common.waitForElement(5);
//		click(bagIcon);
//
//		try {
//			List<WebElement> products = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
//
//			if (products.isEmpty()) {
//				System.out.println("🛒 Bag is empty. Adding product...");
//				click(closeBag);
//
//				Actions actions = new Actions(driver);
//				actions.moveToElement(shopMenu).moveToElement(category).click().build().perform();
//
//				List<WebElement> addProduct = driver.findElements(By.xpath("//button[@class='product_list_cards_btn']"));
//				Collections.shuffle(addProduct);
//
//				if (!addProduct.isEmpty()) {
//					WebElement randomProduct = addProduct.get(0);
//					actions.moveToElement(randomProduct).click().build().perform();
//					click(addToCart);
//					click(bagIcon);
//				}
//
//				products = driver.findElements(By.xpath("//div[@class='cart_prod_card ']")); // Refetch
//			}
//
//			int expectedTotalAmount = 0;
//			System.out.println("Total Products in Bag: " + products.size());
//			System.out.println("---------------------------------------------------");
//
//			for (int i = 0; i < products.size(); i++) {
//				try {
//					// Re-locate the product each time to avoid stale elements
//					WebElement product = driver.findElements(By.xpath("//div[@class='cart_prod_card ']")).get(i);
//
//					WebElement discountedPriceElement = product.findElement(By.xpath(".//div[@class='cp_current_price']"));
//					String discountedPriceText = discountedPriceElement.getText().replaceAll("[^0-9]", "");
//					int discountedPrice = Integer.parseInt(discountedPriceText);
//
//					expectedTotalAmount += discountedPrice;
//
//					System.out.println(" - Discounted Price: Rs. " + discountedPrice);
//					System.out.println("---------------------------------------------------");
//
//				} catch (StaleElementReferenceException sere) {
//					System.out.println("♻️ Stale element detected. Retrying...");
//					i--; // retry current index
//					Common.waitForElement(1);
//				}
//			}
//
//			// Retry fetching actual total
//			String actualTotalText = "";
//			int actualTotalAmount = 0;
//			boolean totalFetched = false;
//			int retries = 3;
//
//			while (!totalFetched && retries > 0) {
//				try {
//					WebElement actualTotalElement = driver.findElement(By.xpath("//div[@class='bfr_pair Cls_cart_total_amount']"));
//					actualTotalText = actualTotalElement.getText().replaceAll("[^0-9]", "");
//					actualTotalAmount = Integer.parseInt(actualTotalText);
//					totalFetched = true;
//				} catch (StaleElementReferenceException e) {
//					System.out.println("⚠️ Retrying fetching total amount due to stale element...");
//					Common.waitForElement(1);
//					retries--;
//				}
//			}
//
//			System.out.println("✅ Expected Total Amount: Rs. " + expectedTotalAmount);
//			System.out.println("✅ Actual Total Amount displayed: Rs. " + actualTotalAmount);
//
//			if (expectedTotalAmount == actualTotalAmount) {
//				System.out.println("🎯 Total amount is correct.");
//			} else {
//				System.out.println("❌ Total amount mismatch!");
//			}
//
//		} catch (Exception e) {
//			System.out.println("💥 Error occurred: " + e.getMessage());
//		}
//	}
//
//	public void youSavedandTotalAmountCalculationBasedOnQTY() throws InterruptedException {
//	    HomePage home = new HomePage(driver);
//	    home.homeLaunch();
//	    Common.waitForElement(3);
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
////	    // If bag empty, add a product
////	    List<WebElement> products = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
////	    if (products.isEmpty()) {
////	        System.out.println(" Bag is empty. Adding product...");
////	        click(closeBag);
//	        Actions actions = new Actions(driver);
//	        actions.moveToElement(shopMenu).moveToElement(category).click().build().perform();
//	        List<WebElement> addProduct = driver.findElements(By.xpath("//button[@class='product_list_cards_btn']"));
//	        Collections.shuffle(addProduct);
//	        if (!addProduct.isEmpty()) {
//	            WebElement randomProduct = addProduct.get(0);
//	            actions.moveToElement(randomProduct).click().build().perform();
//	            click(addToCart);
//	            click(bagIcon);
//	    	    Common.waitForElement(3);
//
//	            
//	        }
//	    
//	    // Lambda or function wrappers to get product data freshly from DOM each time:
//	    java.util.function.Function<Integer, Integer> getQuantity = index -> {
//	        String text = getElementTextWithRetry(By.xpath("//div[@class='cart_prod_card ']"),
//	                By.xpath(".//div[@class='cp_quantity_wrap']"), index);
//	        return Integer.parseInt(text);
//	    };
//	    java.util.function.Function<Integer, Integer> getActualPrice = index -> {
//	        String text = getElementTextWithRetry(By.xpath("//div[@class='cart_prod_card ']"),
//	                By.xpath(".//div[@class='cp_actual_price']"), index);
//	        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
//	    };
//	    java.util.function.Function<Integer, Integer> getDiscountedPrice = index -> {
//	        String text = getElementTextWithRetry(By.xpath("//div[@class='cart_prod_card ']"),
//	                By.xpath(".//span[contains(@class,'cp_current_price') or contains(@class,'product_list_cards_actual_price_txt')]"), index);
//	        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
//	    };
//	    // Function to print summary of products
//	    java.util.function.Consumer<String> printSummary = label -> {
//	        System.out.println("\n" + label);
//	        List<WebElement> prods = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
//	        int totalYouSaved = 0;
//	        int totalDiscounted = 0;
//	        for (int i = 0; i < prods.size(); i++) {
//	            final int index = i;
//	            int quantity = getQuantity.apply(index);
//	            int actualPrice = getActualPrice.apply(index);
//	            int discountedPrice = getDiscountedPrice.apply(index);
//	            int youSaved = (actualPrice - discountedPrice) * quantity;
//	            int discountedTotal = discountedPrice * quantity;
//	            totalYouSaved += youSaved;
//	            totalDiscounted += discountedTotal;
//	            System.out.println("---------------------------------------------------");
//	            System.out.println("Product " + (i + 1));
//	            System.out.println(" - Quantity: " + quantity);
//	            System.out.println(" - Actual Price: Rs. " + actualPrice);
//	            System.out.println(" - Discounted Price: Rs. " + discountedPrice);
//	            System.out.println(" - Expected 'You Saved': Rs. " + youSaved + " Actual you saved : Rs. " + youSaved + " /-");
//	            System.out.println(" - Expected Discounted Total: Rs. " + discountedTotal + " Actual Total Amount : Rs. " + discountedTotal + " /-");
//	        }
//	        System.out.println("\n " + label + " - Grand Totals:");
//	        System.out.println("Expected Total 'You Saved': Rs. " + totalYouSaved + ", Actual you saved: Rs. " + totalYouSaved);
//	        System.out.println("Expected Total Amount: Rs. " + totalDiscounted + ", Actual Total Amount: Rs. " + totalDiscounted);
//	        System.out.println("---------------------------------------------------");
//	    };
//	    // Print initial summary
//	    printSummary.accept(" Initial Product Summary");
//	    // Increase quantity for each product
//	    List<WebElement> productsBeforeIncrease = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
//	    for (int i = 0; i < productsBeforeIncrease.size(); i++) {
//	        final int index = i;
//	        WebElement product = driver.findElements(By.xpath("//div[@class='cart_prod_card ']")).get(index);
//	        WebElement incBtn = product.findElement(By.xpath(".//button[contains(@class,'cp_quantity_increase_btn')]"));
//	        int oldQty = getQuantity.apply(index);
//	        incBtn.click();
//	        int expectedQty = oldQty + 1;
//	        wait.until(driver -> {
//	            try {
//	                int currentQty = getQuantity.apply(index);
//	                return currentQty == expectedQty;
//	            } catch (Exception e) {
//	                return false;
//	            }
//	        });
//	    }
//	    // After increase, print summary
//	    printSummary.accept(" After Increasing Quantity");
//	    // Decrease quantity back to original
//	    List<WebElement> productsBeforeDecrease = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
//	    for (int i = 0; i < productsBeforeDecrease.size(); i++) {
//	        final int index = i;
//	        WebElement product = productsBeforeDecrease.get(index);
//	        WebElement decBtn = product.findElement(By.xpath(".//button[contains(@class,'cp_quantity_decrease_btn')]"));
//	        int oldQty = getQuantity.apply(index);
//	        decBtn.click();
//	        int expectedQty = oldQty - 1;
//	        wait.until(driver -> {
//	            try {
//	                int currentQty = getQuantity.apply(index);
//	                return currentQty == expectedQty;
//	            } catch (Exception e) {
//	                return false;
//	            }
//	        });
//	    }
//	    // After decrease, print final summary
//	    printSummary.accept(" After Decreasing Back to Original");
//	}
//	// Helper method to get text with retries for stale elements
//	private String getElementTextWithRetry(By parentBy, By childBy, int index) {
//	    int attempts = 0;
//	    while (attempts < 5) {
//	        try {
//	            List<WebElement> parents = driver.findElements(parentBy);
//	            if (parents.size() <= index) {
//	                throw new NoSuchElementException("No element at index " + index);
//	            }
//	            WebElement parent = parents.get(index);
//	            WebElement child = parent.findElement(childBy);
//	            return child.getText().trim();
//	        } catch (StaleElementReferenceException | NoSuchElementException e) {
//	            attempts++;
//	            try {
//	                Thread.sleep(200);  // small wait before retry
//	            } catch (InterruptedException ie) {
//	                Thread.currentThread().interrupt();
//	            }
//	        }
//	    }
//	    throw new RuntimeException("Failed to get element text after 5 retries");
//	}
//
//
//
//
//
//
//
//
//
//
//
//
//
//	public void buyNowBagPage() {
//		HomePage home = new HomePage(driver);
//		home.homeLaunch();
//		Common.waitForElement(5);
//		click(bagIcon);
//
//		List<WebElement> products = driver.findElements(By.xpath("//div[@class='cart_prod_card ']"));
//		try {
//
//
//			if (products.isEmpty()) {
//				System.out.println("👜 Bag is empty. Adding product...");
//				Actions actions = new Actions(driver);
//				actions.moveToElement(shopMenu);
//				actions.moveToElement(category).click().build().perform();
//				List<WebElement> addProduct = driver.findElements(By.xpath("//button[@class='product_list_cards_btn']"));
//				Collections.shuffle(addProduct);
//				if (!addProduct.isEmpty()) {
//					WebElement randomProduct = addProduct.get(0);
//					actions.moveToElement(randomProduct).click().build().perform();
//					click(addToCart);
//					click(bagIcon);
//					Common.waitForElement(1);
//					click(buyNowButton);
//				}
//				else {
//					click(buyNowButton);
//				}
//			}
//		}
//		catch (Exception e) {
//			System.out.println("⚠️ Error occurred: " + e.getMessage());
//		}
//
//	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


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

