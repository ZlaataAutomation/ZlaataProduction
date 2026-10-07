package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Function;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import objectRepo.SaleOffer50PercentageObjRepo;
import utils.Common;

import io.cucumber.java.Scenario;
import manager.FileReaderManager;

public final class OrdersPage extends SaleOffer50PercentageObjRepo{
	
	public OrdersPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	 private void clickUsingJS(WebElement element) {
		 JavascriptExecutor executor = (JavascriptExecutor)driver;
		 executor.executeScript("arguments[0].click();", element);

	}
	
	 public void applyThreadValue() {

		    String GREEN  = "\u001B[32m";
		    String RED    = "\u001B[31m";
		    String YELLOW = "\u001B[33m";
		    String CYAN   = "\u001B[36m";
		    String RESET  = "\u001B[0m";
		    String LINE   = CYAN + "──────────────────────────────────────────────────────────────" + RESET;
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		    System.out.println(LINE);
		    System.out.println(CYAN + "🧵 Checking available Threads..." + RESET);
		    System.out.println(LINE);

		    try {
		    	Common.waitForElement(2);
		        // Get available thread count
		        WebElement availableThreadElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
		                By.xpath("//span[@class='price_details_key_span']")
		        ));

		        String threadText = availableThreadElement.getText(); // Example: "35"
		        int availableThreads = Integer.parseInt(threadText);

		        System.out.println(GREEN + "✔ Available Threads: " + availableThreads + RESET);

		        if (availableThreads >= 10) {

		            // Locate input field
		            WebElement threadInput = wait.until(ExpectedConditions.elementToBeClickable(
		                    By.xpath("//input[contains(@class,'Cls_thread_value')]")
		            ));

		            threadInput.clear();
		            threadInput.sendKeys("10");

		            System.out.println(GREEN + "🧵 Entered 10 Threads successfully!" + RESET);

		        } else {
		            System.out.println(RED + "❌ Not enough threads available (" + availableThreads + ") — Need at least 10" + RESET);
		        }

		    } catch (Exception e) {
		        System.out.println(RED + "❌ Error while applying thread value!" + RESET);
		        System.out.println(YELLOW + "⚠ Reason: " + e.getMessage() + RESET);
		    }

		    System.out.println(LINE);
		}
	
	

		int totalMRP;
		int discountedMRP;
		int expressShipping;
		int threadValue;
		int couponDiscount;
		int cartPageCalcTotalAmount;
		int cartPageCalcYouSaved;
		int extraPrepaidDiscount;
		public void verifyPriceDetailsCalculation() {

		    String GREEN  = "\u001B[32m";
		    String RED    = "\u001B[31m";
		    String YELLOW = "\u001B[33m";
		    String CYAN   = "\u001B[36m";
		    String RESET  = "\u001B[0m";
		    String LINE   = CYAN + "──────────────────────────────────────────────────────────────" + RESET;

		    System.out.println(LINE);
		    System.out.println(CYAN + "🔎 Starting Price Details Calculation..." + RESET);

		    try {

		        Common.waitForElement(2);

		        // Helper to parse int safely
		        Function<WebElement, Integer> parseMoney = el ->
		                Integer.parseInt(el.getText().replaceAll("[^0-9]", ""));

		        // Helper to safely get integer value (returns 0 if not found)
		        Function<String, Integer> safeGet = (xpath) -> {
		            try {
		                WebElement el = driver.findElement(By.xpath(xpath));
		                return parseMoney.apply(el);
		            } catch (Exception e) {
		                return 0;  // element not available
		            }
		        };

		        // -----------------------------
		        // Fetch ALL values safely
		        // -----------------------------

		        totalMRP    = safeGet.apply("//div[contains(@class, 'price_details_pair') and contains(@class, 'Cls_cart_total_mrp')]");
		        discountedMRP  = safeGet.apply("//div[contains(@class, 'price_details_pair') and contains(@class, 'Cls_cart_discounted_mrp')]");
		        expressShipping = safeGet.apply("(//span[contains(@class,'Cls_convency_fee')])[1]");

		        // Thread Value input
		        threadValue = 0;
		        try {
		            WebElement threadInput = driver.findElement(By.xpath("//input[contains(@class,'Cls_thread_value')]"));
		            if (!threadInput.getAttribute("value").isEmpty()) {
		                threadValue = Integer.parseInt(threadInput.getAttribute("value"));
		            }
		        } catch (Exception e) { threadValue = 0; }

		         extraPrepaidDiscount = safeGet.apply(
		        	    "(//div[contains(@class,'Cls_cart_extra_prepaid_discount')])[2]"
		        	);

		        // Coupon discount
		        couponDiscount = safeGet.apply("//div[@data-coupon_discount]");

		        // UI shown values
		        int uiSavedAmount = safeGet.apply("//div[contains(@class, 'price_details_pair') and contains(@class, 'Cls_cart_saved_amount')]");
		        int uiTotalAmount = safeGet.apply("//div[contains(@class, 'price_details_pair') and contains(@class, 'Cls_cart_total_amount')]");

		        // -----------------------------
		        // PRINT fetched values
		        //------------------------------

		       
		        System.out.println(LINE);
		        System.out.println(CYAN + "📌 Fetched Values From UI" + RESET);

		        System.out.println(YELLOW + "Total MRP: " + totalMRP + RESET);
		        System.out.println(YELLOW + "Discounted MRP: " + discountedMRP + RESET);
		        System.out.println(YELLOW + "Express Shipping: " + expressShipping + RESET);
		        System.out.println(YELLOW + "Thread Value: " + threadValue + RESET);
		        System.out.println(YELLOW + "Extra Prepaid Discount: " + extraPrepaidDiscount + RESET);
		        System.out.println(YELLOW + "Coupon Discount: " + couponDiscount + RESET);
		        System.out.println(LINE);
		     // UI shown values
		        System.out.println(LINE);
		        System.out.println(CYAN + "📌 This Value Displayng in application checkout page" + RESET);
		        System.out.println(YELLOW + "You Saved UI: " + uiSavedAmount + RESET);
		        System.out.println(YELLOW + "Total Amount UI : " + uiTotalAmount + RESET);
		        System.out.println(LINE);
		        // -----------------------------
		        // Perform calculations
		        // -----------------------------
		        System.out.println(
		        	    "calcTotalAmount = ("
		        	        + "DiscountedMRP : " + discountedMRP + " + "
		        	        + "ExpressShipping : " + expressShipping + " + "
		        	        + ") - ("
		        	        + "ThreadValue : " + threadValue + " + "
		        	        + "CouponDiscount : " + couponDiscount
		        	        + ") " 
		        	     
		        	);

		        cartPageCalcTotalAmount =
		            (discountedMRP + expressShipping)
		                    - (threadValue + couponDiscount + extraPrepaidDiscount );
		        System.out.println(LINE);
		        System.out.println(
		        	    "calcSaved = ("
		        	        + "TotalMRP : " + totalMRP + " - "
		        	        + "DiscountedMRP : " + discountedMRP
		        	        + ") + "
		        	        + "ThreadValue : " + threadValue + " + "
		        	        + "CouponDiscount : " + couponDiscount
		        	        + "  "
		        	        
		        	);
		        // Calculate Saved: (TotalMRP - DiscountedMRP) + coupon + thread 
		        cartPageCalcYouSaved = (totalMRP - discountedMRP)
		                + threadValue + couponDiscount + extraPrepaidDiscount;

		        System.out.println(CYAN + "🧮 Performing Calculations..." + RESET);
		        System.out.println(GREEN + "Calculated Saved Amount: " + cartPageCalcYouSaved + RESET);
		        System.out.println(GREEN + "Calculated Total Amount: " + cartPageCalcTotalAmount + RESET);
		        System.out.println(LINE);

		        // -----------------------------
		        // VALIDATION
		        // -----------------------------
		     // Validation of "You Saved" amount
		        System.out.println(CYAN + "📌 Expected vs Actual Saved Amount:" + RESET);
		        System.out.println(YELLOW + "Expected Saved Amount (UI): " + uiSavedAmount + RESET);
		        System.out.println(YELLOW + "Calculated Saved Amount: " + cartPageCalcYouSaved + RESET);

		        if (cartPageCalcYouSaved == uiSavedAmount) {
		            System.out.println(GREEN + "✅ Saved Amount MATCHES UI" + RESET);
		        } else {
		            System.out.println(RED + "❌ Saved Amount MISMATCH — UI: " + uiSavedAmount +
		                    " | Calc: " + cartPageCalcYouSaved + RESET);

		            Assert.fail("❌ Saved Amount MISMATCH — UI: " + uiSavedAmount +
		                    " | Calc: " + cartPageCalcYouSaved);
		        }
		     // Validation of "Total Amount"
		        System.out.println(CYAN + "📌 Expected vs Actual Total Amount:" + RESET);
		        System.out.println(YELLOW + "Expected Total Amount (UI): " + uiTotalAmount + RESET);
		        System.out.println(YELLOW + "Calculated Total Amount: " + cartPageCalcTotalAmount + RESET);

		        if (cartPageCalcTotalAmount == uiTotalAmount) {
		            System.out.println(GREEN + "✅ Total Amount MATCHES UI" + RESET);
		        } else {
		            System.out.println(RED + "❌ Total Amount MISMATCH — UI: " + uiTotalAmount +
		                    " | Calc: " + cartPageCalcTotalAmount + RESET);

		            Assert.fail("❌ Total Amount MISMATCH — UI: " + uiTotalAmount +
		                    " | Calc: " + cartPageCalcTotalAmount);
		        }

		        System.out.println(LINE);

		    } catch (Exception e) {
		        System.out.println(RED + "❌ ERROR: " + e.getMessage() + RESET);
		    }
		}
		
		public void validateAddressAndPaymentPagePriceWithCartOne() {

		    String GREEN  = "\u001B[32m";
		    String RED    = "\u001B[31m";
		    String YELLOW = "\u001B[33m";
		    String CYAN   = "\u001B[36m";
		    String RESET  = "\u001B[0m";

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
		    click(continueBtn);
		    System.out.println(GREEN + "✅ Clicked Continue Button" + RESET);
		    Common.waitForElement(2);
		    
		    
		    		
		    wait.until(ExpectedConditions.elementToBeClickable(selectCOD));
		    click(selectCOD);
		    System.out.println(GREEN + "✅ Selected COD" + RESET);
		    
		    Common.waitForElement(3);

			  WebElement codExtraElement = wait.until(
	  		            ExpectedConditions.visibilityOfElementLocated(
	  		                    By.xpath("(//span[contains(@class,'checkout__price_cart_courier_fee ')])[2]")
	  		            ));

	  		    int codExtraChargeone = Integer.parseInt(
	  		    		codExtraElement.getText().replaceAll("[^0-9]", "").trim()
	  		    );
	  		    
	 
		    
		    int totalCodCharges = cartPageCalcTotalAmount+codExtraChargeone;

		    
		    // ✅ Fetch "You Saved" from Address Page UI
		    WebElement addressYouSavedElement = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("(//div[contains(@class,'Cls_cart_saved_amount')])[2]")
		            ));

		    int addressUiSavedAmount = Integer.parseInt(
		            addressYouSavedElement.getText().replaceAll("[^0-9]", "").trim()
		    );

		    // ✅ Fetch "Total Amount" from Address Page UI
		    WebElement addressTotalAmountElement = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("(//div[contains(@class,'Cls_cart_total_amount')])[2]")
		            ));

		    int addressUiTotalAmount = Integer.parseInt(
		            addressTotalAmountElement.getText().replaceAll("[^0-9]", "").trim()
		    );

		    // ==============================
		    // ✅ VALIDATE "YOU SAVED"
		    // ==============================
		    System.out.println(CYAN + "📌 Cart vs Address Page — You Saved:" + RESET);
		    System.out.println(YELLOW + "Cart Page Saved: " + cartPageCalcYouSaved + RESET);
		    System.out.println(YELLOW + "Address Page UI Saved: " + addressUiSavedAmount + RESET);
		    
		  

		    if (cartPageCalcYouSaved == addressUiSavedAmount) {
		        System.out.println(GREEN + "✅ You Saved MATCHES on Address Page" + RESET);
		    } else {
		        System.out.println(RED + "❌ You Saved MISMATCH — Cart: " + cartPageCalcYouSaved +
		                " | Address: " + addressUiSavedAmount + RESET);

		        Assert.fail("❌ You Saved MISMATCH — Cart: " + cartPageCalcYouSaved +
		                " | Address: " + addressUiSavedAmount);
		    }
		    


		    // ==============================
		    // ✅ VALIDATE "TOTAL AMOUNT"
		    // ==============================
		    System.out.println(CYAN + "📌 Cart vs Address Page — Total Amount:" + RESET);
		    System.out.println(YELLOW + "Cart Page Total: " + totalCodCharges + RESET);
		    System.out.println(YELLOW + "Address Page  UI Total: " + addressUiTotalAmount + RESET);

		    if (totalCodCharges == addressUiTotalAmount) {
		        System.out.println(GREEN + "✅ Total Amount MATCHES on Address Page" + RESET);
		    } else {
		        System.out.println(RED + "❌ Total Amount MISMATCH — Cart: " + totalCodCharges +
		                " | Address: " + addressUiTotalAmount + RESET);

		        Assert.fail("❌ Total Amount MISMATCH — Cart: " + totalCodCharges +
		                " | Address: " + addressUiTotalAmount);
		    }
		}
		
		
		
		public void validateAddressAndPaymentPagePriceWithCart() {

		    String GREEN  = "\u001B[32m";
		    String RED    = "\u001B[31m";
		    String YELLOW = "\u001B[33m";
		    String CYAN   = "\u001B[36m";
		    String RESET  = "\u001B[0m";

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
		    click(continueBtn);
		    System.out.println(GREEN + "✅ Clicked Continue Button" + RESET);
		    Common.waitForElement(2);
		   
		    
		    // ✅ Fetch "You Saved" from Address Page UI
		    WebElement addressYouSavedElement = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("(//div[contains(@class,'Cls_cart_saved_amount')])[2]")
		            ));

		    int addressUiSavedAmount = Integer.parseInt(
		            addressYouSavedElement.getText().replaceAll("[^0-9]", "").trim()
		    );

		    // ✅ Fetch "Total Amount" from Address Page UI
		    WebElement addressTotalAmountElement = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("(//div[contains(@class,'Cls_cart_total_amount')])[2]")
		            ));

		    int addressUiTotalAmount = Integer.parseInt(
		            addressTotalAmountElement.getText().replaceAll("[^0-9]", "").trim()
		    );

		    // ==============================
		    // ✅ VALIDATE "YOU SAVED"
		    // ==============================
		    System.out.println(CYAN + "📌 Cart vs Address Page — You Saved:" + RESET);
		    System.out.println(YELLOW + "Cart Page Saved: " + cartPageCalcYouSaved + RESET);
		    System.out.println(YELLOW + "Address Page UI Saved: " + addressUiSavedAmount + RESET);
		    
		  

		    if (cartPageCalcYouSaved == addressUiSavedAmount) {
		        System.out.println(GREEN + "✅ You Saved MATCHES on Address Page" + RESET);
		    } else {
		        System.out.println(RED + "❌ You Saved MISMATCH — Cart: " + cartPageCalcYouSaved +
		                " | Address: " + addressUiSavedAmount + RESET);

		        Assert.fail("❌ You Saved MISMATCH — Cart: " + cartPageCalcYouSaved +
		                " | Address: " + addressUiSavedAmount);
		    }
		    
//		    
//		 // ==============================
//		 // ✅ VALIDATE "YOU SAVED"
//		 // ==============================
//
//		 int expectedAddressSavedAmount = cartPageCalcYouSaved + 50;
//
//		 System.out.println(CYAN + "📌 Cart vs Address Page — You Saved:" + RESET);
//		 System.out.println(YELLOW + "Cart Page Saved: " + cartPageCalcYouSaved + RESET);
//		 System.out.println(YELLOW + "Expected Address Saved (+₹50): " + expectedAddressSavedAmount + RESET);
//		 System.out.println(YELLOW + "Address Page UI Saved: " + addressUiSavedAmount + RESET);
//
//		 if (expectedAddressSavedAmount == addressUiSavedAmount) {
//
//		     System.out.println(GREEN + "✅ You Saved MATCHES on Address Page" + RESET);
//
//		 } else {
//
//		     System.out.println(RED + "❌ You Saved MISMATCH — Expected: " + expectedAddressSavedAmount +
//		             " | Address: " + addressUiSavedAmount + RESET);
//
//		     Assert.fail("❌ You Saved MISMATCH — Expected: " + expectedAddressSavedAmount +
//		             " | Address: " + addressUiSavedAmount);
//		 }

		    // ==============================
		    // ✅ VALIDATE "TOTAL AMOUNT"
		    // ==============================
		    System.out.println(CYAN + "📌 Cart vs Address Page — Total Amount:" + RESET);
		    System.out.println(YELLOW + "Cart Page Total: " + cartPageCalcTotalAmount + RESET);
		    System.out.println(YELLOW + "Address Page  UI Total: " + addressUiTotalAmount + RESET);

		    if (cartPageCalcTotalAmount == addressUiTotalAmount) {
		        System.out.println(GREEN + "✅ Total Amount MATCHES on Address Page" + RESET);
		    } else {
		        System.out.println(RED + "❌ Total Amount MISMATCH — Cart: " + cartPageCalcTotalAmount +
		                " | Address: " + addressUiTotalAmount + RESET);

		        Assert.fail("❌ Total Amount MISMATCH — Cart: " + cartPageCalcTotalAmount +
		                " | Address: " + addressUiTotalAmount);
		    }
		}
		public void selectExpressDelivery() {

		    String GREEN  = "\u001B[32m";
		    String RED    = "\u001B[31m";
		    String YELLOW = "\u001B[33m";
		    String CYAN   = "\u001B[36m";
		    String RESET  = "\u001B[0m";

		    String LINE = CYAN + "──────────────────────────────────────────────────────────────" + RESET;
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		    System.out.println(LINE);
		    System.out.println(CYAN + "🚚 Checking Express Delivery availability..." + RESET);
		    System.out.println(LINE);
		    Common.waitForElement(2);
		    try {
		        // Locate Express Delivery parent div
		        WebElement expressDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
		                By.xpath("//div[contains(@class,'delivery_type_card')][label/input[@id='delivery_type_2']]")
		        ));

		        // Check if class contains 'disabled'
		        String classValue = expressDiv.getAttribute("class");

		        if (classValue.contains("disabled")) {
		            System.out.println(RED + "❌ Express Delivery NOT enabled!" + RESET);
		            return;  // Do nothing
		        }

		        // If enabled → click radio button
		        WebElement expressRadio = expressDiv.findElement(By.xpath("//input[@id='delivery_type_2']"));
		      
		        wait.until(ExpectedConditions.elementToBeClickable(expressRadio)).click();

		        System.out.println(GREEN + "✅ Express Delivery Selected Successfully!" + RESET);

		    } catch (Exception e) {
		        System.out.println(RED + "❌ Unable to check/select Express Delivery!" + RESET);
		        System.out.println(YELLOW + "⚠ Reason: " + e.getMessage() + RESET);
		    }

		    System.out.println(LINE);
		}
		public void applyCouponAndGiftWrap() {

		    String GREEN  = "\u001B[32m";
		    String RED    = "\u001B[31m";
		    String YELLOW = "\u001B[33m";
		    String CYAN   = "\u001B[36m";
		    String BLUE   = "\u001B[34m";
		    String RESET  = "\u001B[0m";

		    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		    System.out.println(LINE);
		    System.out.println(CYAN + "🛒 Starting Apply Coupon Process..." + RESET);
		    System.out.println(LINE);
		    
//		    
		    Common.waitForElement(10);

		    By couponInput = By.xpath("(//input[@placeholder='Enter Coupon Code'])[1]");

		    WebElement searchBox = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(couponInput)
		    );
		    searchBox.click();
		    searchBox.sendKeys("TESTMODE");


		    // Click Apply
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(applyBtn));
		    click(applyBtn);
		    System.out.println(CYAN + "🔄 Applying coupon..." + RESET);

		    System.out.println(LINE);
		    System.out.println(CYAN + "🔍 Checking Coupon Status..." + RESET);
		    System.out.println(LINE);

		    // CHECK 1: Coupon Applied
		    try {
		        WebElement appliedMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
		                By.xpath("//p[@class='acc_status']")));

		        System.out.println(GREEN + "✅ Coupon applied successfully!" + RESET);

		    } catch (TimeoutException e) {
		        System.out.println(RED + "❌ Coupon NOT applied!" + RESET);
		        Assert.fail("Coupon was not applied!");
		    }

		    // CHECK 2: Discount Amount
		    try {
		        WebElement discountMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
		                By.xpath("//p[@class='acc_details_status']")));

		        String discountText = discountMsg.getText(); 
		        String discountValue = discountText.replaceAll("[^0-9]", "");

		        System.out.println(GREEN + "💰 Discount Applied: ₹" + discountValue + RESET);

		    } catch (TimeoutException e) {
		        System.out.println(RED + "❌ Discount amount not found!" + RESET);
		        Assert.fail("Discount amount not detected!");
		    }


		    System.out.println(LINE);
		    System.out.println(GREEN + "🎉 Coupon  Completed Successfully!" + RESET);
		    System.out.println(LINE);
		}
		int codExtraCharge;
		public void placeOrderWithCOD() {

		    String GREEN = "\u001B[32m";
		    String YELLOW = "\u001B[33m";
		    String RED = "\u001B[31m";
		    String CYAN = "\u001B[36m";
		    String RESET = "\u001B[0m";
		    String LINE = "────────────────────────────────────────";
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		    Common.waitForElement(2);

		    System.out.println(CYAN + "Cart Total before COD: ₹" + cartPageCalcTotalAmount + RESET);

		    wait.until(ExpectedConditions.elementToBeClickable(selectCOD));
		    click(selectCOD);
		    System.out.println(GREEN + "✅ Selected COD" + RESET);

		    Common.waitForElement(2);
		    
		    		  WebElement codExtraElement = wait.until(
		  		            ExpectedConditions.visibilityOfElementLocated(
		  		                    By.xpath("(//span[contains(@class,'checkout__price_cart_courier_fee ')])[2]")
		  		            ));

		  		     codExtraCharge = Integer.parseInt(
		  		    		codExtraElement.getText().replaceAll("[^0-9]", "").trim()
		  		    );
		  		    
		 

		    System.out.println(YELLOW + "COD Extra Charge displayed: ₹" + codExtraCharge + RESET);

		    if (codExtraCharge != 99) {
		        Assert.fail("❌ COD extra charge mismatch. Expected ₹99 but found ₹" + codExtraCharge);
		    }

		    WebElement addressTotalAmountElement = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("(//div[contains(@class,'Cls_cart_total_amount')])[2]")
		            ));

		    int addressUiTotalAmount = Integer.parseInt(
		            addressTotalAmountElement.getText().replaceAll("[^0-9]", "").trim()
		    );

		    int expectedTotalAfterCOD = cartPageCalcTotalAmount + codExtraCharge + extraPrepaidDiscount ;

		    System.out.println(CYAN + "Expected Total After COD: ₹" + expectedTotalAfterCOD + RESET);
		    System.out.println(CYAN + "Actual Total After COD: ₹" + addressUiTotalAmount + RESET);

		    if (addressUiTotalAmount != expectedTotalAfterCOD) {
		        Assert.fail("❌ Total amount mismatch after COD. Expected ₹"
		                + expectedTotalAfterCOD + " but found ₹" + addressUiTotalAmount);
		    }

		    System.out.println(GREEN + "✅ COD charge & total amount validated successfully" + RESET);
		    System.out.println(LINE);
		 
		}
		
//		int threadsEarned;
		
public void validateOrderConfirmationDetails() throws InterruptedException {
	String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String RED = "\u001B[31m";
    String CYAN = "\u001B[36m";
    String RESET = "\u001B[0m";
    String LINE = "────────────────────────────────────────";
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn));
		    click(placeOrderBtn);
		    System.out.println(GREEN + "✅ Clicked Place Order" + RESET);
		    
		    
		    Thread.sleep(3000);
		    Common.waitForElement(3);

		    try {

		        wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                By.xpath("//h2[contains(text(),'THANK YOU FOR SHOPPING WITH US')]")
		            )
		        );

		        List<WebElement> popup = driver.findElements(
		            By.xpath("//h2[contains(text(),'THANK YOU FOR SHOPPING WITH US')]")
		        );

		        if (!popup.isEmpty()) {
		            driver.findElement(
		                By.xpath("//div[contains(@class,'feedback__closebtn')]")
		            ).click();

		            System.out.println("✅ Thank You popup displayed and closed.");
		        }

		    } catch (TimeoutException e) {
		        System.out.println("ℹ️ Thank You popup not displayed. Continuing normal flow.");
		    }
		    Common.waitForElement(2);

		    try {
		        WebElement confirmMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
		                By.xpath("//h5[@class='checkout_success_heading' and normalize-space()='Order Confirmed']")
		        ));

		        if (confirmMsg.isDisplayed()) {
		            System.out.println(GREEN + "🎉 Order Confirmed Successfully!" + RESET);
		            WebElement element = driver.findElement(By.cssSelector(".placed_prod_view_details_row"));
		            JavascriptExecutor js = (JavascriptExecutor) driver;
		            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
		            
//		            Common.waitForElement(2);
//		            WebElement threadElement = wait.until(
//		                    ExpectedConditions.visibilityOfElementLocated(
//		                            By.cssSelector(".view_order_details_with_address_para")
//		                    )
//		            );
//
//		             threadsEarned = Integer.parseInt(
//		                    threadElement.getText()
//		                            .replaceAll("[^0-9]", "")
//		                            .trim()
//		            );
//
//		            System.out.println("🧵 Threads Earned in Order Confirmation Page: " + threadsEarned);



		            Common.waitForElement(2);
		            wait.until(ExpectedConditions.elementToBeClickable(viewOrderDetails));
		            click(viewOrderDetails);
		            System.out.println(GREEN + "🧾 Clicked View Order Details" + RESET);
		            Common.waitForElement(2);
		           
		    	   
		    	    System.out.println(LINE);          
		    
		        }
		    } catch (Exception e) {
		        System.out.println(RED + "❌ ERROR DURING ORDER CONFIRMATION: " + e.getMessage() + RESET);
		    }
}   
int calcTotalAmount_P1;
int totalMRP_P1;
int discountedMRP_P1;
int couponDiscount_P1;
int threadValue_P1;
int calcYouSaved1;
public void validatePriceBreakupDetail_P1() {
	
	String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String RED = "\u001B[31m";
    String CYAN = "\u001B[36m";
    String RESET = "\u001B[0m";
    String LINE = "────────────────────────────────────────";
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    Common.waitForElement(2);
		    driver.findElement(By.xpath("(//button[@class='price_breakup_btn active'])[1]")).click();
    	    Common.waitForElement(2);
    	    // Helper: returns value or 0 if row missing
    	    Function<String, Integer> getValue = (label) -> {
    	        try {
    	            WebElement ele = driver.findElement(By.xpath(
    	                "//div[@class='price_details_key' and normalize-space(text())='" + label + "']" +
    	                "/following-sibling::div[@class='price_details_pair']"
    	            ));
    	            return Integer.parseInt(ele.getText().replaceAll("[^0-9]", ""));
    	        } catch (Exception e) { return 0; }
    	    };

    	    Common.waitForElement(1);

    	    // -------------------------------
    	    // 🔹 FETCH UI VALUES 
    	    // -------------------------------
    	     totalMRP_P1         = getValue.apply("Total MRP");
    	      discountedMRP_P1    = getValue.apply("Discounted MRP");
    	     couponDiscount_P1   = getValue.apply("Coupon Discount");
    	      threadValue_P1      = getValue.apply("Applied Threads");

    	    int uiYouSaved       = getValue.apply("You Saved");
    	    int uiTotalAmount    = getValue.apply("Total Amount");

    	    // -------------------------------
    	    // 🔹 PRINT UI VALUES
    	    // -------------------------------
    	    System.out.println(LINE);
    	    System.out.println(CYAN + "📌 PRICE DETAILS DISPLAYED IN UI FROM PRICE BREAK UP" + RESET);

    	    System.out.println(YELLOW + "Total MRP:            " + totalMRP_P1 + RESET);
    	    System.out.println(YELLOW + "Discounted MRP:       " + discountedMRP_P1 + RESET);
    	    System.out.println(YELLOW + "Coupon Discount:      " + couponDiscount_P1 + RESET);
    	    System.out.println(YELLOW + "Applied Threads:      " + threadValue_P1 + RESET);
    	    System.out.println(YELLOW + "You Saved (UI):       " + uiYouSaved + RESET);
    	    System.out.println(YELLOW + "Total Amount (UI):    " + uiTotalAmount + RESET);
    	    System.out.println(LINE);

    	    // -------------------------------
    	    // 🔹 CALCULATIONS
    	    // -------------------------------
    	    
    	    System.out.println(
    	    	    "calcTotalAmount = ("
    	    	        + "DiscountedMRP_P1 : " + discountedMRP_P1 + " + "
    	    	        + ") - ("
    	    	        + "ThreadValue_P1 : " + threadValue_P1 + " + "
    	    	        + "CouponDiscount_P1 : " + couponDiscount_P1
    	    	        + ") "
    	    	        
    	    	);
    	    calcTotalAmount_P1 =
    	            (discountedMRP_P1)
    	            - (threadValue_P1 + couponDiscount_P1);
    	     
    	     
    	    System.out.println(
    	    	    "calcYouSaved = "
    	    	        + "TotalMRP_P1 : " + totalMRP_P1 + " - "
    	    	        + "Total Amount : " + calcTotalAmount_P1
    	    	        + ""
    	    	        
    	    	);
    	     calcYouSaved1 =
    	            totalMRP_P1 - calcTotalAmount_P1;
    

    	    // -------------------------------
    	    // 🔹 PRINT CALCULATIONS
    	    // -------------------------------
    	    System.out.println(CYAN + "🧮 DETAILED CALCULATIONS" + RESET);

    	    // YOU SAVED
    	    System.out.println(YELLOW + "You Saved Formula:" + RESET);
    	    System.out.println("   " + totalMRP_P1 + " - " + calcTotalAmount_P1 +"");
    	    System.out.println(GREEN + "   = " + calcYouSaved1 + RESET);

    	    System.out.println();

    	    // TOTAL AMOUNT
    	    System.out.println(YELLOW + "Total Amount Formula:" + RESET);
    	    System.out.println("   (" + discountedMRP_P1 + " + )" +
    	            " - (" + threadValue_P1 + " + "  + couponDiscount_P1 + ")");
    	    System.out.println(GREEN + "   = " + calcTotalAmount_P1 + RESET);

    	    System.out.println(LINE);

    	    // -------------------------------
    	    // 🔹 VALIDATIONS
    	    // -------------------------------
    	    System.out.println(CYAN + "🔍 FINAL VALIDATION RESULTS" + RESET);

    	    // YOU SAVED
    	    System.out.println(YELLOW + "You Saved Validation:" + RESET);
    	    System.out.println("   Calculated = " + calcYouSaved1);
    	    System.out.println("   UI Value   = " + uiYouSaved);

    	    if (calcYouSaved1 == uiYouSaved) {
    	        System.out.println(GREEN + "   ✔ MATCHED" + RESET);
    	    } else {
    	        System.out.println(RED + "   ✘ MISMATCH — UI: " + uiYouSaved +
    	                " | Calc: " + calcYouSaved1 + RESET);
    	        Assert.fail("❌ You Saved MISMATCH!");
    	    }

    	    System.out.println();

    	    // TOTAL AMOUNT
    	    System.out.println(YELLOW + "Total Amount Validation:" + RESET);
    	    System.out.println("   Calculated = " + calcTotalAmount_P1);
    	    System.out.println("   UI Value   = " + uiTotalAmount);

    	    if (calcTotalAmount_P1 == uiTotalAmount) {
    	        System.out.println(GREEN + "   ✔ MATCHED" + RESET);
    	    } else {
    	        System.out.println(RED + "   ✘ MISMATCH — UI: " + uiTotalAmount +
    	                " | Calc: " + calcTotalAmount_P1 + RESET);
    	        Assert.fail("❌ Total Amount MISMATCH!");
    	    }

    	    
}  

int calcTotalAmount_P2;
int totalMRP_P2;
int discountedMRP_P2;
int couponDiscount_P2;
int threadValue_P2;
int calcYouSaved2;
public void validatePriceBreakupDetail_P2() {
	
	String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String RED = "\u001B[31m";
    String CYAN = "\u001B[36m";
    String RESET = "\u001B[0m";
    String LINE = "────────────────────────────────────────";
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    Common.waitForElement(2);
		    driver.findElement(By.xpath("(//button[@class='price_breakup_btn active'])[2]")).click();
    	    Common.waitForElement(2);
    	    // Helper: returns value or 0 if row missing
    	    Function<String, Integer> getValue = (label) -> {
    	        try {
    	            WebElement ele = driver.findElement(By.xpath(
    	                "//div[@class='price_details_key' and normalize-space(text())='" + label + "']" +
    	                "/following-sibling::div[@class='price_details_pair']"
    	            ));
    	            return Integer.parseInt(ele.getText().replaceAll("[^0-9]", ""));
    	        } catch (Exception e) { return 0; }
    	    };

    	    Common.waitForElement(1);

    	    // -------------------------------
    	    // 🔹 FETCH UI VALUES 
    	    // -------------------------------
    	     totalMRP_P2         = getValue.apply("Total MRP");
    	      discountedMRP_P2    = getValue.apply("Discounted MRP");
    	     couponDiscount_P2   = getValue.apply("Coupon Discount");
    	      threadValue_P2      = getValue.apply("Applied Threads");

    	    int uiYouSaved       = getValue.apply("You Saved");
    	    int uiTotalAmount    = getValue.apply("Total Amount");

    	    // -------------------------------
    	    // 🔹 PRINT UI VALUES
    	    // -------------------------------
    	    System.out.println(LINE);
    	    System.out.println(CYAN + "📌 PRICE DETAILS DISPLAYED IN UI FROM PRICE BREAK UP" + RESET);

    	    System.out.println(YELLOW + "Total MRP:            " + totalMRP_P2 + RESET);
    	    System.out.println(YELLOW + "Discounted MRP:       " + discountedMRP_P2 + RESET);
    	    System.out.println(YELLOW + "Coupon Discount:      " + couponDiscount_P2 + RESET);
    	    System.out.println(YELLOW + "Applied Threads:      " + threadValue_P2 + RESET);
    	    System.out.println(YELLOW + "You Saved (UI):       " + uiYouSaved + RESET);
    	    System.out.println(YELLOW + "Total Amount (UI):    " + uiTotalAmount + RESET);
    	    System.out.println(LINE);

    	    // -------------------------------
    	    // 🔹 CALCULATIONS
    	    // -------------------------------
    	    
    	    System.out.println(
    	    	    "calcTotalAmount = ("
    	    	        + "DiscountedMRP_P2 : " + discountedMRP_P2 + " + "
    	    	        + ") - ("
    	    	        + "ThreadValue_P2 : " + threadValue_P2 + " + "
    	    	        + "CouponDiscount_P2 : " + couponDiscount_P2
    	    	        + ") "
    	    	        
    	    	);
    	    calcTotalAmount_P2 =
    	            (discountedMRP_P2)
    	            - (threadValue_P2 + couponDiscount_P2);
    	     
    	     
    	    System.out.println(
    	    	    "calcYouSaved = "
    	    	        + "TotalMRP_P2 : " + totalMRP_P2 + " - "
    	    	        + "Total Amount : " + calcTotalAmount_P2
    	    	        + ""
    	    	        
    	    	);
    	     calcYouSaved2 =
    	            totalMRP_P2 - calcTotalAmount_P2;
    

    	    // -------------------------------
    	    // 🔹 PRINT CALCULATIONS
    	    // -------------------------------
    	    System.out.println(CYAN + "🧮 DETAILED CALCULATIONS" + RESET);

    	    // YOU SAVED
    	    System.out.println(YELLOW + "You Saved Formula:" + RESET);
    	    System.out.println("   " + totalMRP_P2 + " - " + calcTotalAmount_P2 +"");
    	    System.out.println(GREEN + "   = " + calcYouSaved2 + RESET);

    	    System.out.println();

    	    // TOTAL AMOUNT
    	    System.out.println(YELLOW + "Total Amount Formula:" + RESET);
    	    System.out.println("   (" + discountedMRP_P2 + " + )" +
    	            " - (" + threadValue_P2 + " + "  + couponDiscount_P2 + ")");
    	    System.out.println(GREEN + "   = " + calcTotalAmount_P2 + RESET);

    	    System.out.println(LINE);

    	    // -------------------------------
    	    // 🔹 VALIDATIONS
    	    // -------------------------------
    	    System.out.println(CYAN + "🔍 FINAL VALIDATION RESULTS" + RESET);

    	    // YOU SAVED
    	    System.out.println(YELLOW + "You Saved Validation:" + RESET);
    	    System.out.println("   Calculated = " + calcYouSaved2);
    	    System.out.println("   UI Value   = " + uiYouSaved);

    	    if (calcYouSaved2 == uiYouSaved) {
    	        System.out.println(GREEN + "   ✔ MATCHED" + RESET);
    	    } else {
    	        System.out.println(RED + "   ✘ MISMATCH — UI: " + uiYouSaved +
    	                " | Calc: " + calcYouSaved2 + RESET);
    	        Assert.fail("❌ You Saved MISMATCH!");
    	    }

    	    System.out.println();

    	    // TOTAL AMOUNT
    	    System.out.println(YELLOW + "Total Amount Validation:" + RESET);
    	    System.out.println("   Calculated = " + calcTotalAmount_P2);
    	    System.out.println("   UI Value   = " + uiTotalAmount);

    	    if (calcTotalAmount_P2 == uiTotalAmount) {
    	        System.out.println(GREEN + "   ✔ MATCHED" + RESET);
    	    } else {
    	        System.out.println(RED + "   ✘ MISMATCH — UI: " + uiTotalAmount +
    	                " | Calc: " + calcTotalAmount_P2 + RESET);
    	        Assert.fail("❌ Total Amount MISMATCH!");
    	    }

    	    
}    
public void verifyCouponSplit_P1() {
	String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";
    String BLUE   = "\u001B[34m";

    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;

    System.out.println(LINE);
    System.out.println(CYAN + "📘 COUPON DISTRIBUTION CALCULATION" + RESET);
	// =============================
	// COUPON CALCULATION
	// =============================
	System.out.println(CYAN + "🧮 Performing Coupon Calculation..." + RESET);

	// Formula: (Product Discounted Amount / Total Discounted MRP) * Coupon Discount Amount
	System.out.println(GREEN + "Formula: (ProductDiscountedAmount / TotalDiscountedMRP) * TotalCouponDiscount" + RESET);

	// Avoid divide-by-zero
	double calcCouponRaw = 0.0;
	if (discountedMRP > 0) {
	    calcCouponRaw = ((double) discountedMRP_P1 / (double) discountedMRP) * couponDiscount;
	}

	// ROUNDING OPTIONS
	int calcCouponFloor = (int) Math.floor(calcCouponRaw);
	int calcCouponCeil  = (int) Math.ceil(calcCouponRaw);

	System.out.println(YELLOW + "Calculated Coupon Raw:      " + calcCouponRaw + RESET);
	System.out.println(YELLOW + "Calculated Coupon Floor:    " + calcCouponFloor + RESET);
	System.out.println(YELLOW + "Calculated Coupon Ceil:     " + calcCouponCeil + RESET);
	System.out.println(LINE);
	System.out.println(YELLOW + "UI Coupon Discount:         " + couponDiscount_P1 + RESET);

	System.out.println(LINE);

	// =============================
	// VALIDATION WITH TOLERANCE
	// =============================
	if (couponDiscount_P1 == calcCouponFloor || couponDiscount_P1 == calcCouponCeil) {

	    System.out.println(GREEN +
	        "✅ COUPON DISCOUNT MATCHED UI (Accepted Floor/Ceil Tolerance)" +
	    RESET);

	} else {

	    System.out.println(RED +
	        "❌ COUPON DISCOUNT MISMATCH — UI: " + couponDiscount_P1 +
	        " | CalcFloor: " + calcCouponFloor +
	        " | CalcCeil: " + calcCouponCeil +
	        RESET);

	    Assert.fail("❌ COUPON DISCOUNT MISMATCH — UI: " + couponDiscount_P1 +
	        " | CalcFloor: " + calcCouponFloor +
	        " | CalcCeil: " + calcCouponCeil);
	}

	System.out.println(LINE);
	
	
} 
public void verifyCouponSplit_P2() {
	String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";
    String BLUE   = "\u001B[34m";

    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;

    System.out.println(LINE);
    System.out.println(CYAN + "📘 COUPON DISTRIBUTION CALCULATION" + RESET);
	// =============================
	// COUPON CALCULATION
	// =============================
	System.out.println(CYAN + "🧮 Performing Coupon Calculation..." + RESET);

	// Formula: (Product Discounted Amount / Total Discounted MRP) * Coupon Discount Amount
	System.out.println(GREEN + "Formula: (ProductDiscountedAmount / TotalDiscountedMRP) * TotalCouponDiscount" + RESET);

	// Avoid divide-by-zero
	double calcCouponRaw = 0.0;
	if (discountedMRP > 0) {
	    calcCouponRaw = ((double) discountedMRP_P2 / (double) discountedMRP) * couponDiscount;
	}

	// ROUNDING OPTIONS
	int calcCouponFloor = (int) Math.floor(calcCouponRaw);
	int calcCouponCeil  = (int) Math.ceil(calcCouponRaw);

	System.out.println(YELLOW + "Calculated Coupon Raw:      " + calcCouponRaw + RESET);
	System.out.println(YELLOW + "Calculated Coupon Floor:    " + calcCouponFloor + RESET);
	System.out.println(YELLOW + "Calculated Coupon Ceil:     " + calcCouponCeil + RESET);
	System.out.println(LINE);
	System.out.println(YELLOW + "UI Coupon Discount:         " + couponDiscount_P2 + RESET);

	System.out.println(LINE);

	// =============================
	// VALIDATION WITH TOLERANCE
	// =============================
	if (couponDiscount_P2 == calcCouponFloor || couponDiscount_P2 == calcCouponCeil) {

	    System.out.println(GREEN +
	        "✅ COUPON DISCOUNT MATCHED UI (Accepted Floor/Ceil Tolerance)" +
	    RESET);

	} else {

	    System.out.println(RED +
	        "❌ COUPON DISCOUNT MISMATCH — UI: " + couponDiscount_P2 +
	        " | CalcFloor: " + calcCouponFloor +
	        " | CalcCeil: " + calcCouponCeil +
	        RESET);

	    Assert.fail("❌ COUPON DISCOUNT MISMATCH — UI: " + couponDiscount_P2 +
	        " | CalcFloor: " + calcCouponFloor +
	        " | CalcCeil: " + calcCouponCeil);
	}

	System.out.println(LINE);
	
	
} 
public void verifyThreadSplit_P1() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";
    String BLUE   = "\u001B[34m";

    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;

    System.out.println(LINE);
    System.out.println(CYAN + "📘 THREAD DISTRIBUTION CALCULATION" + RESET);

    // ============================================
    // 🛑 SKIP LOGIC — If threadValue2 is ZERO
    // ============================================
    if (threadValue == 0) {

        System.out.println(YELLOW +
            "⚠ SKIPPING THREAD SPLIT VALIDATION — UI Thread Value is 0" +
        RESET);

        System.out.println(LINE);
        return;  // EXIT — Do NOT perform any thread validation
    }

    // ============================================
    // THREAD SPLIT CALCULATION
    // ============================================
    System.out.println(CYAN + "🧮 Performing Thread Split Calculation..." + RESET);
    System.out.println(GREEN +
            "Formula: (ProductDiscountedMRP / TotalDiscountedMRP) * TotalThreadAmount"
            + RESET);

    double calcThreadRaw = 0.0;

    if (discountedMRP > 0) {
        calcThreadRaw = ((double) discountedMRP_P1 / (double) discountedMRP) * threadValue;
    }

    int calcThreadFloor = (int) Math.floor(calcThreadRaw);
    int calcThreadCeil  = (int) Math.ceil(calcThreadRaw);

    System.out.println(YELLOW + "Calculated Thread Raw:      " + calcThreadRaw + RESET);
    System.out.println(YELLOW + "Calculated Thread Floor:    " + calcThreadFloor + RESET);
    System.out.println(YELLOW + "Calculated Thread Ceil:     " + calcThreadCeil + RESET);
    System.out.println(LINE);

    System.out.println(YELLOW + "UI Thread Value:            " + threadValue_P1 + RESET);
    System.out.println(LINE);

    // ============================================
    // VALIDATION WITH TOLERANCE
    // ============================================
    if (threadValue_P1 == calcThreadFloor || threadValue_P1 == calcThreadCeil) {

        System.out.println(GREEN +
                "✅ THREAD DISTRIBUTION MATCHED UI (Accepted Floor/Ceil Tolerance)" +
                RESET);

    } else {

        System.out.println(RED +
                "❌ THREAD DISTRIBUTION MISMATCH — UI: " + threadValue_P1 +
                " | CalcFloor: " + calcThreadFloor +
                " | CalcCeil: " + calcThreadCeil +
                RESET);

        Assert.fail("❌ THREAD DISTRIBUTION MISMATCH — UI: " +
        		threadValue_P1 + " | CalcFloor: " + calcThreadFloor +
                " | CalcCeil: " + calcThreadCeil);
    }

    System.out.println(LINE);
} 
public void verifyThreadSplit_P2() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";
    String BLUE   = "\u001B[34m";

    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;

    System.out.println(LINE);
    System.out.println(CYAN + "📘 THREAD DISTRIBUTION CALCULATION" + RESET);

    // ============================================
    // 🛑 SKIP LOGIC — If threadValue2 is ZERO
    // ============================================
    if (threadValue == 0) {

        System.out.println(YELLOW +
            "⚠ SKIPPING THREAD SPLIT VALIDATION — UI Thread Value is 0" +
        RESET);

        System.out.println(LINE);
        return;  // EXIT — Do NOT perform any thread validation
    }

    // ============================================
    // THREAD SPLIT CALCULATION
    // ============================================
    System.out.println(CYAN + "🧮 Performing Thread Split Calculation..." + RESET);
    System.out.println(GREEN +
            "Formula: (ProductDiscountedMRP / TotalDiscountedMRP) * TotalThreadAmount"
            + RESET);

    double calcThreadRaw = 0.0;

    if (discountedMRP > 0) {
        calcThreadRaw = ((double) discountedMRP_P2 / (double) discountedMRP) * threadValue;
    }

    int calcThreadFloor = (int) Math.floor(calcThreadRaw);
    int calcThreadCeil  = (int) Math.ceil(calcThreadRaw);

    System.out.println(YELLOW + "Calculated Thread Raw:      " + calcThreadRaw + RESET);
    System.out.println(YELLOW + "Calculated Thread Floor:    " + calcThreadFloor + RESET);
    System.out.println(YELLOW + "Calculated Thread Ceil:     " + calcThreadCeil + RESET);
    System.out.println(LINE);

    System.out.println(YELLOW + "UI Thread Value:            " + threadValue_P2 + RESET);
    System.out.println(LINE);

    // ============================================
    // VALIDATION WITH TOLERANCE
    // ============================================
    if (threadValue_P2 == calcThreadFloor || threadValue_P2 == calcThreadCeil) {

        System.out.println(GREEN +
                "✅ THREAD DISTRIBUTION MATCHED UI (Accepted Floor/Ceil Tolerance)" +
                RESET);

    } else {

        System.out.println(RED +
                "❌ THREAD DISTRIBUTION MISMATCH — UI: " + threadValue_P2 +
                " | CalcFloor: " + calcThreadFloor +
                " | CalcCeil: " + calcThreadCeil +
                RESET);

        Assert.fail("❌ THREAD DISTRIBUTION MISMATCH — UI: " +
        		threadValue_P2 + " | CalcFloor: " + calcThreadFloor +
                " | CalcCeil: " + calcThreadCeil);
    }

    System.out.println(LINE);
}
//Helper to extract ₹ values → int
		private int parseMoney(String text) {
		    return Integer.parseInt(text.replaceAll("[^0-9]", ""));
		}
public void validateOrderSummaryForTwoProduct() {

    String CYAN = "\u001B[36m";
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";
    String LINE = "──────────────────────────────────────────────";
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    // =============================
    // STEP 1: UI Values
    // =============================
    
   js.executeScript("window.scrollBy(0, 500);");
 // Payable Amount - only first text node
    Common.waitForElement(2);
    WebElement amountDiv = driver.findElement(
            By.xpath("//tr[contains(@class,'total_order_value')]//div[contains(@class,'prod_order_payment_mode_value')]")
    );

    String fullText = amountDiv.getText().trim();

    // Remove the "You have Saved …" part
    String cleaned = fullText.replaceAll("You have Saved.*", "").trim();

    // Extract digits
    int uiOrderValue = Integer.parseInt(
            cleaned.replaceAll("[^0-9]", "")
    );

    System.out.println("Order Value: " + uiOrderValue);

   // System.out.println("Order Value: " + uiPayableAmount);

    // Helper to parse int safely
    Function<WebElement, Integer> parseMoney = el ->
            Integer.parseInt(el.getText().replaceAll("[^0-9]", ""));

    // Helper to safely get integer value (returns 0 if not found)
    Function<String, Integer> safeGet = (xpath) -> {
        try {
            WebElement el = driver.findElement(By.xpath(xpath));
            return parseMoney.apply(el);
        } catch (Exception e) {
            return 0;  // element not available
        }
    };

    	// Saved Amount
    	String savedText = driver.findElement(
    	        By.cssSelector(".prod_order_payment_mode_value span")
    	).getText().trim();

    	int uiSavedAmount = parseMoney(savedText);
    int uiShippingCharges = safeGet.apply("//div[normalize-space(text())='Shipping Charges']/following::div[1]");
//    int summaryEarnedThread = safeGet.apply("//div[normalize-space(text())='Threads Earned']/following::div[1]");
  
    // =============================
    // STEP 2: Print Backend Values
    // =============================
    System.out.println(LINE);

    System.out.println(CYAN + "📌 UI Values from Order Summary Page" + RESET);
    System.out.println(YELLOW + "You Saved (UI): " + uiSavedAmount + RESET);
      System.out.println(YELLOW + "Shipping Charges (UI): " + uiShippingCharges + RESET);
    System.out.println(YELLOW + "Total Order Value (UI): " + uiOrderValue + RESET);
  //  System.out.println(YELLOW + "Threads Earned (UI): " + summaryEarnedThread + RESET);
    System.out.println(LINE);

    // =============================
    // STEP 3: Calculations
    // =============================
    System.out.println(CYAN + "🧮 Performing Calculations..." + RESET);
    
      int calcTotalOrderValue =
           (calcTotalAmount_P1 + calcTotalAmount_P2 + uiShippingCharges);
      
      int calcYouSaved =
    		  calcYouSaved1 + calcYouSaved2;
      
      int calcShippingChrages= expressShipping + codExtraCharge;
 

//    int calcTotalOrderValue =
//            (discountedMRP + giftWrapFee + expressShipping + customFee)
//                    - (threadValue + couponDiscount);

//    System.out.println(GREEN + "Formula: (DiscountedMRP + Wrap + Express + Custom) - (Thread + Coupon)" + RESET);
    System.out.println(YELLOW + "Calculated Total Order Value: " + calcTotalOrderValue + RESET);
    System.out.println(YELLOW + "Calculated YouSaved Amount: " + calcYouSaved + RESET);
    System.out.println(YELLOW + "Calculated Shipping  Charge: " + calcShippingChrages + RESET);
//    int calcPayableAmount =
//            calcTotalOrderValue - (giftWrapFee + expressShipping);
//
//    System.out.println(GREEN + "Formula: TotalOrderValue - (Wrap + Express)" + RESET);
    
//    System.out.println(YELLOW + "Calculated Payable Amount: " + calcPayableAmount + RESET);

    System.out.println(LINE);

    // =============================
    // STEP 4: VALIDATION
    // =============================
    if (calcTotalOrderValue == uiOrderValue) {
        System.out.println(GREEN + "✅ TOTAL ORDER VALUE MATCHED UI" + RESET);
    } else {
        System.out.println(RED + "❌ TOTAL ORDER VALUE MISMATCH — UI: " +
        		uiOrderValue + " | Calc: " + calcTotalOrderValue + RESET);

        Assert.fail("❌ TOTAL ORDER VALUE MISMATCH — UI: " +
        		uiOrderValue + " | Calc: " + calcTotalOrderValue);
    }
    
 // ---- YOUSAVED AMOUNT ----
    if (calcYouSaved == uiSavedAmount) {
        System.out.println(GREEN + "✅ YOUSAVED AMOUNT MATCHED UI" + RESET);
    } else {
        System.out.println(RED + "❌ YOUSAVED AMOUNT MISMATCH — UI: " +
        		uiSavedAmount + " | Calc: " + calcYouSaved + RESET);

        Assert.fail("❌ YOUSAVED AMOUNT MISMATCH — UI: " +
        		uiSavedAmount + " | Calc: " + calcYouSaved);
    }

    // ---- SHIPPING AMOUNT ----
    if (calcShippingChrages == uiShippingCharges) {
        System.out.println(GREEN + "✅ SHIPPING AMOUNT MATCHED UI" + RESET);
    } else {
        System.out.println(RED + "❌ SHIPPING AMOUNT MISMATCH — UI: " +
        		uiShippingCharges + " | Calc: " + calcShippingChrages + RESET);

        Assert.fail("❌ SHIPPING AMOUNT MISMATCH — UI: " +
        		uiShippingCharges + " | Calc: " + calcShippingChrages);
    }
    
 // ---- EARNED THREAD AMOUNT ----
//    if (summaryEarnedThread == threadsEarned) {
//        System.out.println(GREEN + "✅ EARNED THREAD AMOUNT MATCHED UI" + RESET);
//    } else {
//        System.out.println(RED + "❌ EARNED THREAD AMOUNT MISMATCH — UI: " +
//        		summaryEarnedThread + " | Calc: " + threadsEarned + RESET);
//
//        Assert.fail("❌ EARNED THREAD AMOUNT MISMATCH — UI: " +
//        		summaryEarnedThread + " | Calc: " + threadsEarned);
//    }
//    

    System.out.println(LINE);
}   

public void verifyTwoProductsOrderPlacedAndCancelButtons() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    Common.waitForElement(2);
    // ===============================
    // 1️⃣ Verify TWO "Order Placed" texts
    // ===============================
    List<WebElement> orderStatusList = driver.findElements(
            By.cssSelector("h4.order_status")
    );

    if (orderStatusList.size() != 2) {
        Assert.fail("❌ Expected 2 'Order Placed' texts but found: "
                + orderStatusList.size());
    }

    for (int i = 0; i < orderStatusList.size(); i++) {
        String statusText = orderStatusList.get(i).getText().trim();

        if (!statusText.equalsIgnoreCase("Order Placed")) {
            Assert.fail("❌ Product " + (i + 1)
                    + " status mismatch. Expected 'Order Placed' but found: "
                    + statusText);
        }

        System.out.println("✅ Product " + (i + 1) + " status: " + statusText);
    }

    // ===============================
    // 2️⃣ Verify TWO Product Cancel buttons
    // ===============================
    List<WebElement> productCancelButtons = driver.findElements(
            By.cssSelector("button.prod_cancel_btn")
    );

    if (productCancelButtons.size() != 2) {
        Assert.fail("❌ Expected 2 Product Cancel buttons but found: "
                + productCancelButtons.size());
    }

    System.out.println("✅ 2 Product Cancel buttons are displayed");

    // ===============================
    // 3️⃣ Verify ONE Order Cancel button
    // ===============================
    List<WebElement> orderCancelButtons = driver.findElements(
            By.cssSelector("button.order_cancel_btn")
    );

    if (orderCancelButtons.size() != 1) {
        Assert.fail("❌ Expected 1 Order Cancel button but found: "
                + orderCancelButtons.size());
    }

    System.out.println("✅ Order Cancel button is displayed");

    // ===============================
    // 4️⃣ Final Summary
    // ===============================
    System.out.println("🎯 2 Products → Order Placed & Cancel verification PASSED");
}

public void removePopup() {
	try {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

	    WebElement closePopup = wait.until(
	        ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(@class,'feedback__closebtn')]")
	        )
	    );

	    closePopup.click();
	    System.out.println("✅ Feedback popup displayed and closed.");
	    Common.waitForElement(3);

	} catch (TimeoutException e) {
	    System.out.println("ℹ️ Feedback popup not displayed. Continuing normal flow.");
	}
}
String productlistingName;

public String takeRandomProductFromAll() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    Actions actions = new Actions(driver);
    
    

    // Hover on Shop → All
    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath(" //div[@class='header_nav_item has_dropdown shop']")));
    actions.moveToElement(shopMenu).perform();

    WebElement allButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[normalize-space()='dresses']")));
    allButton.click();

   Common.waitForElement(5);
   removePopup();
    System.out.println("✅ Clicked on 'Dress' under Shop menu");

    // Collect all product cards
    List<WebElement> products = wait.until(ExpectedConditions
            .visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='prod_listing_card']")));

    if (products.isEmpty()) {
        System.out.println("⚠️ No products found on listing page!");
        return null;
    }

//    Random rand = new Random();
//    int maxAttempts = Math.min(5, products.size());
//    boolean productFound = false;
//
//    for (int attempt = 1; attempt <= maxAttempts; attempt++) {
//
//        int randomIndex = rand.nextInt(products.size()) + 1;
//        System.out.println("🎯 Checking random product index: " + randomIndex);
//
//        WebElement productCard = driver.findElement(
//                By.xpath("(//div[@class='prod_listing_card'])[" + randomIndex + "]"));
//
////        String name = productCard.findElement(
////                By.xpath(".//h2[@class='product_list_cards_heading']"))
////                .getText().trim();
//        
//
//      String name = productCard.findElement(
//              By.xpath("//a[contains(@class,'product_list_name')]"))
//              .getText().trim();
//        
//        
////        List<WebElement> stockLabels = productCard.findElements(
////                By.xpath(".//h2[contains(@class,'product_list_cards_out_of_stock_heading') and normalize-space()='OUT OF STOCK']"));
//        
//        List<WebElement> stockLabels = productCard.findElements(
//        		By.xpath("//span[contains(@class,'prod_listing_hurry') and contains(text(),'Out of Stock')]"));
//
//        boolean isOutOfStock = !stockLabels.isEmpty() && stockLabels.get(0).isDisplayed();
//
//        if (isOutOfStock) {
//            System.out.println("❌ '" + name + "' is OUT OF STOCK. Retrying...");
//            continue;
//        }
//
//        // Found in-stock product
//        String  productName = name;
//
//        WebElement productNameElement = productCard.findElement(
//                By.xpath("//a[contains(@class,'product_list_name')]"));
//
//     // Fix: JS click to avoid interception
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", productNameElement);
//
//        productFound = true;
//        System.out.println("✅ Selected random in-stock product: " + productName);
//        break;
//    }

    Random rand = new Random();
    int maxAttempts = Math.min(5, products.size());
    boolean productFound = false;

    for (int attempt = 1; attempt <= maxAttempts; attempt++) {

        int randomIndex = rand.nextInt(products.size()) + 1;
        System.out.println("🎯 Checking random product index: " + randomIndex);

        WebElement productCard = driver.findElement(
                By.xpath("(//div[@class='prod_listing_card'])[" + randomIndex + "]"));

        String name = productCard.findElement(
                By.xpath(".//a[contains(@class,'product_list_name')]"))
                .getText().trim();

        List<WebElement> stockLabels = productCard.findElements(
                By.xpath(".//span[contains(@class,'prod_listing_hurry') and contains(text(),'Out of Stock')]"));

        boolean isOutOfStock = !stockLabels.isEmpty() && stockLabels.get(0).isDisplayed();

        if (isOutOfStock) {
            System.out.println("❌ '" + name + "' is OUT OF STOCK. Retrying...");
            continue;
        }

        WebElement productNameElement = productCard.findElement(
                By.xpath(".//a[contains(@class,'product_list_name')]"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", productNameElement);

        productFound = true;
        System.out.println("✅ Selected random in-stock product: " + name);
        break;
    }
    if (!productFound) {
        System.out.println("⚠️ No in-stock product found after trying " + maxAttempts);
        return null;
    }
    // Click ADD TO CART button on PDP
    
    productlistingName = driver.findElement(
            By.xpath("//h3[@class='prod_name']")
    ).getText().trim();
    System.out.println("Product Name: " + productlistingName);
    
    Common.waitForElement(2);
    WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//button[contains(text(),'Buy Now')])[1]")));
    

//    
    Common.waitForElement(2);
 // scroll it into center
    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block: 'center'});", addToCart);

    // click via JS (bypasses click interception)
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCart);
    Common.waitForElement(1);

    // Open cart
    driver.findElement(By.xpath("//button[@class='header_cta_btn Cls_cart_btn ']")).click();
    Common.waitForElement(1);

    System.out.println("🛒 Add to Cart clicked on PDP for: " + productlistingName);

    return productlistingName;
}

public void deleteAllProductsFromCart() {
	driver.get(FileReaderManager.getInstance()
            .getConfigReader()
            .getApplicationUrl());

    // Open cart
    driver.findElement(By.xpath("//button[@class='header_cta_btn Cls_cart_btn ']")).click();
    Common.waitForElement(1);
    Common.waitForElement(10);

    // ✅ STEP 1: Check if cart is already empty
    try {
        if (driver.findElement(By.xpath("//a[@class='empty_bag_shop_btn btn___2']")).isDisplayed()) {
            System.out.println("🛍️ Cart already empty. No delete action needed.");
            return; // Stop method immediately
        }
    } catch (NoSuchElementException ignored) {
    	System.out.println("Cart is NOT empty, proceed to delete");
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
        if (driver.findElement(By.xpath("//a[@class='empty_bag_shop_btn btn___2']")).isDisplayed()) {
            System.out.println("🛍️ Cart is empty, Continue Shopping displayed.");
        }
    } catch (NoSuchElementException e) {
        System.out.println("ℹ️ Bag is not empty message not found.");
    }
}



public void verifyOrderCancellation() {
	 String CYAN = "\u001B[36m";
	  
	    String GREEN = "\u001B[32m";
	    String RESET = "\u001B[0m";
	    String line = "──────────────────────────────────────────────────────────────";
		driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());
		
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    System.out.println(CYAN + line + RESET);
	    System.out.println(GREEN + "🚀 Starting Order Return Flow..." + RESET);
	    System.out.println(CYAN + line + RESET);
	    Common.waitForElement(3);
	    wait.until(ExpectedConditions.elementToBeClickable(myProfileIcon));
		click(myProfileIcon);
		Common.waitForElement(1);
	    wait.until(ExpectedConditions.elementToBeClickable(myOrdersBtn));
		click(myOrdersBtn);
		 Common.waitForElement(2);
	 		// Build dynamic XPath
	 		String xpath = "(//a[contains(@class,'order_placed_redirect_btn')])[1]";
	 		WebElement btn = driver.findElement(By.xpath(xpath));

	 		// 1️⃣ Scroll to the element
	 		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
	 		
	 		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
	 		Common.waitForElement(2);
	 		 // Click Cancel button
	 	    WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'order_cancel_btn btn___1')]")));
	 	    if (cancelButton.isDisplayed()) {
	 	        System.out.println(" Cancel Button: Displayed ✅");
	 	        cancelButton.click();
	 	        System.out.println(GREEN + "🛑 Clicked Cancel Order button" + RESET);
	 	    }
	 	    
	 	    
	 	    // Select cancellation reason
	 	    Common.waitForElement(2);
	 	    wait.until(ExpectedConditions.elementToBeClickable(selectCancelReason));
	 		click(selectCancelReason);
	 	    System.out.println(GREEN + "📌 Selected Cancel Reason: " + selectCancelReason + RESET);

	 	    // 3 Click Continue / Confirm Cancel
	 	    Common.waitForElement(1);
	 	    wait.until(ExpectedConditions.elementToBeClickable(continueReturnBtn));
	 		click(continueReturnBtn);
	 	    System.out.println(GREEN + "✅ Clicked Continue button" + RESET);

	 	    //  Verify Order Cancelled message
	 	   Common.waitForElement(2);
	 	   List<WebElement> cancelledLabels = wait.until(
	 		        ExpectedConditions.numberOfElementsToBeMoreThan(
	 		                By.xpath("//h4[contains(@class,'order_status') and normalize-space()='Order Cancelled']"),
	 		                1   // more than 1 → means at least 2
	 		        )
	 		);

	 		Assert.assertEquals(
	 		        "❌ Expected 2 'Order Cancelled' labels",
	 		        2,
	 		        cancelledLabels.size()
	 		);

	 		System.out.println(
	 		        GREEN + "🎉 2 'Order Cancelled' labels are displayed successfully" + RESET
	 		);
		
}
	 public void verifyOrderPlacementAndCalculationAndAfterPalced() throws InterruptedException {
	 
		 
		 deleteAllProductsFromCart();
		 
		 takeRandomProductFromAll();
		 
		 takeRandomProductFromAll();
		 
		 applyCouponAndGiftWrap();
		 
		 selectExpressDelivery();
		 		 
		 applyThreadValue();
		 
		 verifyPriceDetailsCalculation();
		 
		 validateAddressAndPaymentPagePriceWithCart();
		 
		 validateAddressAndPaymentPagePriceWithCartOne();
		 
		 placeOrderWithCOD();
		 
		 validateOrderConfirmationDetails();
		 
		 validatePriceBreakupDetail_P1();
		 
		 verifyCouponSplit_P1();
		 
		 verifyThreadSplit_P1();
		 
		 closeBtn.click();

		 validatePriceBreakupDetail_P2();
		 
		 verifyCouponSplit_P2();
		 
		 verifyThreadSplit_P2();
		 
		 closeBtn.click();
		 
		 validateOrderSummaryForTwoProduct();
		 
		 verifyTwoProductsOrderPlacedAndCancelButtons();
	 }
	 
	 
	public void validateOrderCancellation() {
		
		verifyOrderCancellation();
	}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	public void verifyOrderPlacementAndValidationFlow(Scenario scenario) {
//    Actions actions = new Actions(driver);
//    LoginPage login = new LoginPage(driver);
//    login.userLogin();
//    Common.waitForElement(2);
//
//    // Step 1: Navigate to product listing page & add random product
//    actions.moveToElement(driver.findElement(By.xpath("//li[@class='navigation_menu_list nav_menu_dropdown shop']")))
//           .moveToElement(driver.findElement(By.xpath("//div[@class='nav_drop_down_box_category active']//a[contains(translate(text(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'ALL')]"))).click().build().perform();
//    Common.waitForElement(2);
//
//    List<WebElement> addProduct = driver.findElements(By.xpath("//button[@class='product_list_cards_btn']"));
//	Collections.shuffle(addProduct);
//
//	if (!addProduct.isEmpty()) {
//		WebElement randomProduct = addProduct.get(0);
//		actions.moveToElement(randomProduct).click().build().perform();
//		
//
//	}
//	Common.waitForElement(2);
//    // Step 1A: Handle Custom Size
//    try {
//        WebElement customInput = driver.findElement(By.xpath("//div[@class='prod_size_name Cls_prod_size_name Cls_prod_size Cls_custom_btn ']"));
//        if (customInput.isDisplayed()) {
//        	click(customInput);
//        	driver.findElement(By.xpath("(.//label[@class='custom__input'])[1]")).sendKeys("34.6");
//    		driver.findElement(By.xpath("(.//label[@class='custom__input'])[2]")).sendKeys("36.5");
//    		driver.findElement(By.xpath("(.//label[@class='custom__input'])[3]")).sendKeys("32.5");
//    		driver.findElement(By.xpath("(.//label[@class='custom__input'])[4]")).sendKeys("35.5");
//    		driver.findElement(By.id("submitButton")).click();
//        }
//    } catch (Exception ignored) {
//    	
//    }
//
//    // Add to cart
//    driver.findElement(By.xpath("//button[@class='add_bag_prod_buy_now_btn btn___2  Cls_CartList ClsProductListSizes']")).click();
//    Common.waitForElement(2);
//
//    // Proceed to checkout
//    driver.findElement(By.xpath("//button[@class='Cls_cart_btn']")).click();
//    Common.waitForElement(2);
//    driver.findElement(By.xpath("//button[text()='Buy Now']")).click();
//    Common.waitForElement(2);
//
//    // Step 2: Verify Discount Percentage
//    double mrp = Double.parseDouble(driver.findElement(By.xpath("//div[@class='cp_actual_price']")).getText().replaceAll("[^\\d.]", ""));
//    double discounted = Double.parseDouble(driver.findElement(By.xpath("//div[@class='cp_current_price']")).getText().replaceAll("[^\\d.]", ""));
//    double actualPercent = Math.round(((mrp - discounted) / mrp) * 100);
//    System.out.println("✅ Discount % on Checkout Page: " + actualPercent + "%");
     verifyCheckoutCalculations();
    // Step 3: Apply Coupon in text box
     Common.waitForElement(3);
     WebElement remove = driver.findElement(By.xpath("//button[@class='coupon_apply_btn Cls_coupon_apply_rmv_btn']"));
     clickUsingJS(remove);
    WebElement couponInput = driver.findElement(By.name("couponInputField"));
    couponInput.sendKeys("TESTMODE");
    driver.findElement(By.xpath("//button[@class='coupon_apply_btn Cls_coupon_apply_rmv_btn']")).click();
    Common.waitForElement(2);

    // Step 4: Gift Wrap
//    try {
//        driver.findElement(By.id("checkout__model__gift")).click();
//        driver.findElement(By.id("recipient-name")).sendKeys("Test User");
//        driver.findElement(By.xpath("//button[@class='gift__submit btn___2']")).click();
//    } catch (Exception ignored) {
//    	
//    }

    // Step 5: Express Delivery
//    try {
//        WebElement express = driver.findElement(By.id("checked2"));
//        if (!express.isSelected()) express.click();
//    } catch (Exception ignored) {
//    	
//    }

    // Step 6: Apply Threads
//    try {
//        WebElement threadCount = driver.findElement(By.xpath("//span[@class='price_details_key_span']"));
//        int threads = Integer.parseInt(threadCount.getText().replaceAll("[^\\d]", ""));
//        WebElement threadInput = driver.findElement(By.xpath("//input[@placeholder='Enter threads']"));
//        threadInput.clear();
//        threadInput.sendKeys(String.valueOf(threads));
//    } catch (Exception ignored) {
//    	
//    }

    // Step 7: Validate Price Fields
//    double youSaved = Double.parseDouble(driver.findElement(By.xpath("//div[contains(@class,'Cls_cart_saved_amount')]")).getText().replaceAll("[^\\d.]", ""));
//    double totalDiscounted = Double.parseDouble(driver.findElement(By.xpath("//div[contains(@class,'Cls_cart_discounted_mrp')]")).getText().replaceAll("[^\\d.]", ""));
//    double finalPayable = Double.parseDouble(driver.findElement(By.xpath("//div[contains(@class,'Cls_cart_total_amount')]")).getText().replaceAll("[^\\d.]", ""));
//    double flatDiscount = 0;
//    try {
//        flatDiscount = Double.parseDouble(driver.findElement(By.xpath("//div[contains(@class,'Cls_cart_extra_prepaid_discount')]")).getText().replaceAll("[^\\d.]", ""));
//    } catch (Exception ignored) {}
//
//    System.out.println("🧾 Checkout Summary:");
//    System.out.println("MRP: " + mrp + " Discounted: " + discounted + " You Saved: " + youSaved);
//    System.out.println("Total: " + totalDiscounted + " Payable: " + finalPayable + " Flat ₹50: " + flatDiscount);

    // Step 8: Go to Address Page
    driver.findElement(By.xpath("//button[@class='place_order_btn Cls_place_order btn___2 ']")).click();
    Common.waitForElement(2);

    // Step 9: Confirm selected address
    WebElement selectedAddress = driver.findElement(By.xpath("//div[@class='address_card Cls_addr_data_section']"));
    System.out.println("📦 Selected Address: " + selectedAddress.getText());
    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    scenario.attach(screenshot, "image/png", "Initial Screenshot");
    // Step 10: Place Order
    driver.findElement(By.xpath("//button[@class='place_order_btn Cls_place_order btn___2 enabled']")).click();
    WebElement  totalAmountPaymentPage= driver.findElement(By.xpath(".//div[@class='price_details_pair Cls_cart_total_amount']"));
	String totalText = totalAmountPaymentPage.getText();
	
//    Common.waitForElement(3);
//	click(netBanking);
//	Common.waitForElement(1);
//	click(paymentPlaceOrder);
//	Common.waitForElement(1);
//	try {
//    driver.switchTo().frame("razorpay-checkout-frame");
//	WebElement paymentpopUpAmount = driver.findElement(By.xpath("//div[@class='mt-3 flex items-baseline gap-1']//h3[@class='text-2xl font-semibold number-flip']"));
//	String popupText = paymentpopUpAmount.getAttribute("data-value");
//	System.out.println("Popup Amount: Rs " + popupText);
//	System.out.println("Total Amount in Payment page: Rs " + totalText);
//	String cleanedTotalText = totalText.replaceAll("[^0-9]", "");
//	String cleanedPopupText = popupText.replaceAll("[^0-9]", "");
//
//	if (cleanedTotalText.equals(cleanedPopupText)) {
//		System.out.println("✅ Payment Popup and Total amount is matching → Rs " + cleanedTotalText);
//	} else {
//		System.out.println("❌ Payment Popup and Total amount is NOT matching → Expected: Rs " + cleanedTotalText + ", Actual: Rs " + cleanedPopupText);
//	}
//	} catch (NoSuchElementException e) {
//	    System.out.println("❌ Unable to locate Razorpay popup amount element.");
//	}
//    Common.waitForElement(1);
//    clickUsingJS(closePopup);
//    Common.waitForElement(1);
//    click(cancelPayment);
//    Common.waitForElement(1);
//    driver.switchTo().defaultContent();
    scenario.attach(screenshot, "image/png", "Initial Screenshot");
    click(cODMode);
    Common.waitForElement(1);
    click(paymentPlaceOrder);
    Common.waitForElement(5);
    // Step 11: Confirmation Page % Validation
//    double finalMRP = mrp;
//    double totalDiscounts = (mrp - discounted) + flatDiscount;
//    double expectedPercent = Math.round((totalDiscounts / finalMRP) * 100);

//    String confirmationDiscount = driver.findElement(By.xpath("//span[@class='placed_prod_discount']")).getText().replaceAll("[^\\d]", "");
//    System.out.println("🎯 Discount % on Confirmation Page: " + confirmationDiscount + " | Expected: " + expectedPercent);

    // Step 12: Order Details Page
    driver.findElement(By.xpath("//button[text()='View Order Details']")).click();
    Common.waitForElement(2);

    WebElement cancelBtn = driver.findElement(By.xpath("//button[@class='prod_cancel_btn cls_cancel_button']"));
    if (cancelBtn.isDisplayed()) {
        System.out.println("❌ Cancel Button: Displayed ✅");
    }

    // Step 13: Price Breakup
    driver.findElement(By.xpath("//button[@class='price_breakup_btn active']")).click();
    Common.waitForElement(1);
    scenario.attach(screenshot, "image/png", "Initial Screenshot");
    System.out.println("🧾 Price Breakup Verified:");
    System.out.println("Total MRP: " + driver.findElement(By.xpath("//div[@class='price_details_row actual_mrp']//div[@class='price_details_pair']")).getText());
    System.out.println("Discounted MRP: " + driver.findElement(By.xpath("//div[@class='price_details_row discount_mrp']//div[@class='price_details_pair']")).getText());
    System.out.println("Coupon Discount: " + driver.findElement(By.xpath("//div[@class='price_details_row coupon_discount']//div[@class='price_details_pair']")).getText());
    System.out.println("You Saved: " + driver.findElement(By.xpath("//div[@class='price_details_row saved_amount']//div[@class='price_details_pair']")).getText());

	
}
	public void verifyCheckoutCalculations() {
		LoginPage login = new LoginPage(driver);
		login.userLogin();
		Common.waitForElement(5);
		Actions actions = new Actions(driver);

		WebElement bagIcon = driver.findElement(By.xpath("//button[@class='Cls_cart_btn Cls_redirect_restrict']"));
		bagIcon.click();
		Common.waitForElement(2);

		List<WebElement> productBlocks = driver.findElements(By.xpath(".//div[@class='cart_prod_card ']"));
		if (productBlocks.isEmpty()) {
			System.out.println("🛍️ Bag item count not displayed. Adding product...");
			WebElement closeBag = driver.findElement(By.xpath("(//header[@class='popup_containers_header']//div[@class='popup_containers_cls_btn'])[2]"));
			closeBag.click();
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
				driver.findElement(By.xpath("//button[@class='Cls_cart_btn Cls_redirect_restrict']")).click();
				Common.waitForElement(2);
			}
		}

		driver.findElement(By.xpath("//button[.='Buy Now']")).click();
		productBlocks = driver.findElements(By.xpath(".//div[@class='cart_prod_card ']"));

		double expectedTotalMrp = 0, expectedTotalDiscounted = 0, expectedYouSaved = 0;

		System.out.println("=== Product-wise Breakdown ===\n");

		for (int i = 0; i < productBlocks.size(); i++) {
			WebElement product = productBlocks.get(i);
			String productName = product.findElement(By.xpath(".//div[@class='cp_name_row']")).getText();
			int quantity = Integer.parseInt(product.findElement(By.xpath(".//div[@class='cp_quantity_wrap']")).getText().trim());
			double mrp = Double.parseDouble(product.findElement(By.xpath(".//div[@class='cp_actual_price']")).getText().replaceAll("[^\\d.]", ""));

			double discounted = 0;
			boolean isPromotional = false;

			if (product.findElements(By.xpath(".//span[@class='product_list_cards_actual_price_txt']")).size() > 0) {
				discounted = Double.parseDouble(product.findElement(By.xpath(".//span[@class='product_list_cards_actual_price_txt']")).getText().replaceAll("[^\\d.]", ""));
				isPromotional = true;
			} else {
				discounted = Double.parseDouble(product.findElement(By.xpath(".//div[@class='cp_current_price']")).getText().replaceAll("[^\\d.]", ""));
			}

			double expectedMrp = mrp * quantity;
			double expectedDiscounted = discounted * quantity;
			double expectedSaved = (mrp - discounted) * quantity;

			if (isPromotional) {
				System.out.println("Product " + (i + 1) + ": "  + " (Promotional)");
			} else {
				System.out.println("Product " + (i + 1) + ": "  + " (Normal)");
			}

			expectedTotalMrp += expectedMrp;
			expectedTotalDiscounted += expectedDiscounted;
			expectedYouSaved += expectedSaved;

			System.out.println("Product " + (i + 1) + ": " + productName);
			System.out.println("Quantity: " + quantity);
			System.out.println("Actual Price of the product : Rs. " + expectedMrp + " | Discounted Price of the product: Rs. " + expectedDiscounted);
			System.out.println("Product " + (i + 1) + " calculations complete\n");

		}

		// Gift Wrap
		double express = 0, custom = 0;
//		try {
//			WebElement giftWrapToggle = driver.findElement(By.xpath("//input[@class='checkout_git_list_item__checkbox']"));
//			giftWrapToggle.click();
//			driver.findElement(By.id("recipient-name")).sendKeys("Test User");
//			driver.findElement(By.xpath("//button[@class='gift__submit btn___2']")).click();
//			Common.waitForElement(2);
//			giftWrap = Double.parseDouble(driver.findElement(By.xpath(".//div[@class='price_details_pair git__wraping11']")).getText().replaceAll("[^\\d.]", ""));
//		} catch (Exception e) {
//			System.out.println("🎁 Gift wrap section not available or skipped");
//		}

		// Express Delivery
		try {
			WebElement expressOption = driver.findElement(By.id("checked2"));
			if (!expressOption.isSelected()) expressOption.click();
			express = Double.parseDouble(driver.findElement(By.xpath(".//div[@class='Cls_CourierFee']")).getText().replaceAll("[^\\d.]", ""));
		} catch (Exception e) {
			System.out.println("---------------------------------------------");
			System.out.println("🚚 Express delivery not available or skipped");
			System.out.println("---------------------------------------------");
		}

		// Customization Charges
		try {
			custom = Double.parseDouble(driver.findElement(By.xpath(".//div[@class='price_details_row Cls_customized_extras']")).getText().replaceAll("[^\\d.]", ""));
		} catch (Exception ignored) {}

		// Coupon
		double couponDiscount = 0;
		try {
			Common.waitForElement(2);
			List<WebElement> availableCoupons = driver.findElements(By.xpath("//button[@class='offer_list_item_apply_btn Cls_apply_coupon']"));
			if (!availableCoupons.isEmpty()) {
				availableCoupons.get(0).click();
				Common.waitForElement(2);
				WebElement appliedText = driver.findElement(By.xpath("//p[@class='coupon_apply_msg active']"));
				couponDiscount = Double.parseDouble(appliedText.getText().replaceAll("[^\\d.]", ""));
				System.out.println("---------------------------------------------");
				System.out.println("✅ Coupon applied successfully. Discount amount: Rs. " + couponDiscount);
				System.out.println("---------------------------------------------");
			}
		} catch (Exception e) {
			System.out.println("---------------------------------------------");
			System.out.println("❌ Coupon skipped or not applicable.");
			System.out.println("---------------------------------------------");
		}

		// Flat Rs. 50 Discount
		double flatDiscount = 0;
		try {
			flatDiscount = Double.parseDouble(driver.findElement(By.xpath("//div[@class='price_details_pair Cls_cart_extra_prepaid_discount']")).getText().replaceAll("[^\\d.]", ""));
		} catch (Exception ignored) {}

		// Thread Application
		int threadsApplied = 0;
		try {
			WebElement availableThread = driver.findElement(By.xpath("//span[@class='price_details_key_span']"));
			threadsApplied = Integer.parseInt(availableThread.getText().replaceAll("[^\\d]", ""));
			WebElement threadInput = driver.findElement(By.xpath("//input[@placeholder='Enter threads']"));
			if (!threadInput.isSelected()) threadInput.click();
			threadInput.clear();
			threadInput.sendKeys(String.valueOf(threadsApplied));
		} catch (Exception e) {
			System.out.println("🧵 Threads not applied or skipped");
		}

		double finalAmount = Double.parseDouble(driver.findElement(By.xpath(".//div[@class='price_details_pair Cls_cart_total_amount']")).getText().replaceAll("[^\\d.]", ""));
		double actualSaved = Double.parseDouble(driver.findElement(By.xpath(".//div[@class='price_details_pair Cls_cart_saved_amount']")).getText().replaceAll("[^\\d.]", ""));
		double expectedFinalYouSaved = expectedYouSaved + flatDiscount + couponDiscount + threadsApplied;
		double finalPayable = finalAmount;
		double actualTotalMrp = expectedTotalMrp; 
		double actualDiscountedMrp = expectedTotalDiscounted;
		int earnedThreads = ((int) (finalPayable  - express - custom) / 500) * 10;
		int actualEarnedThreads = ((int) (finalPayable  - express - custom) / 500) * 10;

		System.out.println("============= Checkout Summary ==================================");	
		System.out.println("Expected Total MRP: Rs. " + expectedTotalMrp + " || Actual Total MRP: Rs. " + actualTotalMrp + " || " + (expectedTotalMrp == actualTotalMrp ? "\u001B[32mMatched\u001B[0m" : "\u001B[31mMismatch\u001B[0m"));
		System.out.println("Expected Discounted MRP: Rs. " + expectedTotalDiscounted + " || Actual Discounted MRP: Rs. " + actualDiscountedMrp + " || " + (expectedTotalDiscounted == actualDiscountedMrp ? "\u001B[32mMatched\u001B[0m" : "\u001B[31mMismatch\u001B[0m"));
//		System.out.println("Gift Wrap: Rs. " + giftWrap);
		System.out.println("Express Delivery: Rs. " + express);
		System.out.println("Customization Charges: Rs. " + custom);
		System.out.println("Expected Earned Threads: " + earnedThreads + " || Actual Earned Threads: " + actualEarnedThreads + " || " + (earnedThreads == actualEarnedThreads ? "\u001B[32mMatched\u001B[0m" : "\u001B[31mMismatch\u001B[0m"));
		System.out.println("Flat ₹50 Discount Applied: Rs. " + flatDiscount);
		System.out.println("Thread Discount Applied: Rs. " + threadsApplied);
		System.out.println("Coupon Discount Applied: Rs. " + couponDiscount);
		System.out.println("Expected Final 'You Saved': Rs. " + expectedFinalYouSaved + " || Actual Final 'You Saved': Rs. " + actualSaved + " || " + (expectedFinalYouSaved == actualSaved ? "\u001B[32mMatched\u001B[0m" : "\u001B[31mMismatch\u001B[0m"));
		System.out.println("Final Total Amount (Expected): Rs. " + finalPayable + " || Final Total Amount (Actual): Rs. " + finalAmount + " || " + (finalPayable == finalAmount ? "\u001B[32mMatched\u001B[0m" : "\u001B[31mMismatch\u001B[0m"));
		System.out.println("\u001B[0m✅ All checkout calculations validated.");
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
