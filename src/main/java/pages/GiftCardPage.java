package pages;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import manager.FileReaderManager;
import objectRepo.GiftCardObjRepo;
import utils.Common;

public final class GiftCardPage  extends GiftCardObjRepo{

	public GiftCardPage(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	
	
	public void deleteAllProductsFromCart() {

	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());

	    // Open cart
	    driver.findElement(
	            By.xpath("//button[@class='header_cta_btn Cls_cart_btn ']")
	    ).click();

	    Common.waitForElement(1);

	    // STEP 1: Check if cart is already empty
	    try {
	        if (driver.findElement(
	                By.xpath("//a[@class='empty_bag_shop_btn btn___2']")
	        ).isDisplayed()) {

	            System.out.println("🛍️ Cart already empty. No delete action needed.");
	            return;
	        }

	    } catch (NoSuchElementException e) {
	        System.out.println("🛒 Cart is NOT empty, proceed to delete.");
	    }


	    // STEP 2: Remove both normal products and Gift Cards
	    while (true) {

	        boolean itemDeleted = false;

	        try {

	            // =========================
	            // Remove Normal Product
	            // =========================
	            List<WebElement> productDeleteButtons = driver.findElements(
	                    By.xpath("//div[@title='Delete']")
	            );

	            if (!productDeleteButtons.isEmpty()) {

	                WebElement deleteBtn = productDeleteButtons.get(0);

	                deleteBtn.click();

	                System.out.println("🗑️ Normal product deleted.");

	                Common.waitForElement(1);

	                itemDeleted = true;
	            }


	            // =========================
	            // Remove Gift Card
	            // =========================
	            List<WebElement> giftCardRemoveButtons = driver.findElements(
	                    By.xpath("//button[contains(@class,'Cls_gc_remove_btn') and normalize-space()='remove']")
	            );

	            if (!giftCardRemoveButtons.isEmpty()) {

	                WebElement giftCardRemoveBtn = giftCardRemoveButtons.get(0);

	                giftCardRemoveBtn.click();

	                System.out.println("🎁 Gift Card removed.");

	                Common.waitForElement(1);

	                itemDeleted = true;
	            }


	            // =========================
	            // No Items Remaining
	            // =========================
	            if (!itemDeleted) {

	                System.out.println("✅ No more products or Gift Cards to delete.");

	                break;
	            }

	        } catch (Exception e) {

	            System.out.println(
	                    "⚠️ Error while deleting cart items: "
	                    + e.getMessage()
	            );

	            break;
	        }
	    }


	    // STEP 3: Final confirmation
	    try {

	        if (driver.findElement(
	                By.xpath("//a[@class='empty_bag_shop_btn btn___2']")
	        ).isDisplayed()) {

	            System.out.println(
	                    "🛍️ Cart is empty. Continue Shopping displayed."
	            );

	        }

	    } catch (NoSuchElementException e) {

	        System.out.println(
	                "ℹ️ 'Your bag is empty' message not found."
	        );
	    }
	}

	
	public void giftcardorder() {

	    deleteAllProductsFromCart();

	    scrollUsingJSWindow();
	    Common.waitForElement(1);

	    click(giftCard);

	    Common.waitForElement(2);

	    // =========================
	    // Gift Card Page URL
	    // =========================

	    String currentUrl = driver.getCurrentUrl();

	    Assert.assertTrue(
	            currentUrl.contains("/gift-card")
	    );

	    System.out.println("\u001B[32m✅ Navigated to Gift Card page: "
	            + currentUrl + "\u001B[0m");


	    // =========================
	    // Select Gift Card Amount
	    // =========================

	    choosegiftCardAmount.click();

	    nextButton.click();


	    // =========================
	    // Cancel Gift Card
	    // =========================

	    cancelButton.click();

	    yesDiscardButton.click();

	    Common.waitForElement(2);


	    // =========================
	    // Verify Gift Card Page
	    // =========================

	    String afterDiscardUrl = driver.getCurrentUrl();

	    WebElement giftCardHeading = driver.findElement(
	            By.xpath("//h1[contains(@class,'gift-card-container-text') and normalize-space()='GIFT CARD']")
	    );

	    Assert.assertTrue(
	            giftCardHeading.isDisplayed()
	    );

	    System.out.println(
	            "\u001B[32m✅ Returned to Gift Card page after discard\u001B[0m"
	    );

	    System.out.println(
	            "\u001B[36mCurrent URL : "
	            + afterDiscardUrl
	            + "\u001B[0m"
	    );

	    System.out.println(
	            "\u001B[32m✅ GIFT CARD heading displayed successfully\u001B[0m"
	    );
	
	    // =========================
	    // Select Gift Card Amount
	    // =========================

	    choosegiftCardAmount.click();

	    nextButton.click();

	    // Generate tomorrow's date
	    String date = LocalDate.now().plusDays(1).toString();

	    // Recipient Email
	    String email = "test@gmail.com";
	    recipientEmail.sendKeys(email);

	    suggestionBox.click();

	    // Recipient Name
	    String recipient = "Test Recipient";
	    recipientName.sendKeys(recipient);

	    // Set Delivery Date
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    js.executeScript(
	            "arguments[0].value = arguments[1];" +
	            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
	            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
	            deliveryDate, date
	    );

	    // Phone Number
	    String phone = "9876543210";
	    phoneNumber.sendKeys(phone);

	    // Gift Message
	    String message = "Happy Birthday!";
	    giftMessage.sendKeys(message);

	    // Sender Name
	    String sender = "Test Sender";
	    senderName.sendKeys(sender);


	    // =========================
	    // Print Entered Details
	    // =========================

	    String RESET = "\u001B[0m";
	    String GREEN = "\u001B[32m";
	    String CYAN = "\u001B[36m";
	    String YELLOW = "\u001B[33m";

	    System.out.println(CYAN + "════════════════════════════════════" + RESET);
	    System.out.println(CYAN + "       🎁 GIFT CARD DETAILS" + RESET);
	    System.out.println(CYAN + "════════════════════════════════════" + RESET);

	    // Print stored email because suggestion click clears the input value
	    System.out.println(GREEN + "Recipient Email : "
	            + email + RESET);

	    System.out.println(GREEN + "Recipient Name  : "
	            + recipientName.getAttribute("value") + RESET);

	    System.out.println(YELLOW + "Delivery Date   : "
	            + deliveryDate.getAttribute("value") + RESET);

	    System.out.println(GREEN + "Phone Number    : "
	            + phoneNumber.getAttribute("value") + RESET);

	    System.out.println(GREEN + "Gift Message    : "
	            + giftMessage.getAttribute("value") + RESET);

	    System.out.println(GREEN + "Sender Name     : "
	            + senderName.getAttribute("value") + RESET);

	    System.out.println(CYAN + "════════════════════════════════════" + RESET);
	    
	    previewButton.click();
	    
	    
	    giftCardAddToCart.click();
	    
	    
	    Common.waitForElement(2);

	 // =========================
	 // Get Gift Card Amount
	 // =========================

	 String addedGiftCardAmount = currentPrice.getText().trim();

	 System.out.println("\u001B[36m════════════════════════════════════\u001B[0m");
	 System.out.println("\u001B[36m       🎁 GIFT CARD AMOUNT CHECK\u001B[0m");
	 System.out.println("\u001B[36m════════════════════════════════════\u001B[0m");

	 System.out.println("\u001B[32mAdded Gift Card Amount : "
	         + addedGiftCardAmount + "\u001B[0m");


	 // =========================
	 // Get Gift Card MRP From Cart
	 // =========================

	 String cartGiftCardMRP = giftCardMRP.getText().trim();

	 System.out.println("\u001B[33mGift Card MRP          : "
	         + cartGiftCardMRP + "\u001B[0m");


	 // =========================
	 // Compare Amounts
	 // =========================

	 if (addedGiftCardAmount.equals(cartGiftCardMRP)) {

	     System.out.println("\u001B[32m✅ PASS: Gift Card amount matched.\u001B[0m");
	     System.out.println("\u001B[32mAdded Amount : "
	             + addedGiftCardAmount + " | Cart MRP : "
	             + cartGiftCardMRP + "\u001B[0m");

	 } else {

	     System.out.println("\u001B[31m❌ FAIL: Gift Card amount mismatch.\u001B[0m");
	     System.out.println("\u001B[31mAdded Amount : "
	             + addedGiftCardAmount + " | Cart MRP : "
	             + cartGiftCardMRP + "\u001B[0m");

	     Assert.fail(
	             "Gift Card amount mismatch. Added Amount: "
	             + addedGiftCardAmount
	             + " | Cart MRP: "
	             + cartGiftCardMRP
	     );
	 }

	 System.out.println("\u001B[36m════════════════════════════════════\u001B[0m");	    
	    
	    
	}
	
	
	public void verifyGiftCardImageNavigation() {

	    String GREEN = "\u001B[32m";
	    String RED = "\u001B[31m";
	    String CYAN = "\u001B[36m";
	    String RESET = "\u001B[0m";

	    System.out.println(CYAN + "════════════════════════════════════" + RESET);
	    System.out.println(CYAN + "   🎁 GIFT CARD IMAGE NAVIGATION" + RESET);
	    System.out.println(CYAN + "════════════════════════════════════" + RESET);

	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());

	    
	    scrollUsingJSWindow();
	    Common.waitForElement(1);

	    click(giftCard);

	    Common.waitForElement(2);
	    
	    // =========================
	    // Get Initial Image
	    // =========================

	    String initialImage = selectedGiftCardImage.getAttribute("src");

	    System.out.println(CYAN + "Initial Image : "
	            + initialImage + RESET);


	    // =========================
	    // Click Next Arrow
	    // =========================

	    nextArrow.click();

	    Common.waitForElement(1);

	    String nextImage = selectedGiftCardImage.getAttribute("src");

	    System.out.println(CYAN + "After Next    : "
	            + nextImage + RESET);


	    // Verify Next Image Changed
	    if (!initialImage.equals(nextImage)) {

	        System.out.println(
	                GREEN + "✅ PASS: Next arrow changed the Gift Card image."
	                        + RESET
	        );

	    } else {

	        System.out.println(
	                RED + "❌ FAIL: Next arrow did NOT change the Gift Card image."
	                        + RESET
	        );

	        Assert.fail("Next arrow did not change the Gift Card image.");
	    }


	    // =========================
	    // Click Previous Arrow
	    // =========================

	    previousArrow.click();

	    Common.waitForElement(1);

	    String previousImage = selectedGiftCardImage.getAttribute("src");

	    System.out.println(CYAN + "After Previous: "
	            + previousImage + RESET);


	    // Verify Previous Image
	    if (previousImage.equals(initialImage)) {

	        System.out.println(
	                GREEN + "✅ PASS: Previous arrow returned to the previous Gift Card image."
	                        + RESET
	        );

	    } else {

	        System.out.println(
	                RED + "❌ FAIL: Previous arrow did NOT return to the previous image."
	                        + RESET
	        );

	        Assert.fail("Previous arrow did not return to the previous Gift Card image.");
	    }

	    System.out.println(CYAN + "════════════════════════════════════" + RESET);
	}
	
	
	
	
	
	public void verifyGiftCardLearnMore() {

	    String GREEN = "\u001B[32m";
	    String RED = "\u001B[31m";
	    String CYAN = "\u001B[36m";
	    String RESET = "\u001B[0m";

	    System.out.println(CYAN + "════════════════════════════════════" + RESET);
	    System.out.println(CYAN + "       🎁 GIFT CARD LEARN MORE" + RESET);
	    System.out.println(CYAN + "════════════════════════════════════" + RESET);
	    
	    
	    openforTheGiftcardPage();

	    // Click Learn More
	    learnMore.click();

	    Common.waitForElement(2);

	    // Verify URL
	    String currentUrl = driver.getCurrentUrl();

	    Assert.assertTrue(
	            currentUrl.contains("/gift-card-learn-more")
	    );

	    System.out.println(
	            GREEN + "✅ Navigated to Gift Card Learn More page: "
	                    + currentUrl
	                    + RESET
	    );

	    System.out.println(CYAN + "════════════════════════════════════" + RESET);
	}
	
	
	
	public void verifyCheckBalanceValidation() {

	    String GREEN = "\u001B[32m";
	    String RED = "\u001B[31m";
	    String CYAN = "\u001B[36m";
	    String RESET = "\u001B[0m";

	    openforTheGiftcardPage();

	    // =========================
	    // Case 1: Empty Gift Card Number
	    // =========================

	    checkBalanceButton.click();

	    Common.waitForElement(1);

	    giftcardBalancePopup.click();

	    String expectedMessage = "Please enter a gift card number.";
	    String actualMessage = giftCardErrorMessage.getText().trim();

	    System.out.println(CYAN + "Expected Message : "
	            + expectedMessage + RESET);

	    System.out.println(CYAN + "Actual Message   : "
	            + actualMessage + RESET);

	    if (actualMessage.equals(expectedMessage)) {

	        System.out.println(
	                GREEN + "✅ PASS: Correct validation message displayed."
	                        + RESET
	        );

	    } else {

	        System.out.println(
	                RED + "❌ FAIL: Validation message mismatch."
	                        + RESET
	        );

	        Assert.fail(
	                "Expected: " + expectedMessage
	                        + " | Actual: " + actualMessage
	        );
	    }


	    // =========================
	    // Case 2: Invalid Gift Card Number
	    // =========================

	    giftCardNumberInput.sendKeys("1234567890");

	    giftcardBalancePopup.click();

	    Common.waitForElement(1);

	    String expectedMessage1 = "Invalid card number! Try again.";
	    String actualMessage1 = invalidCardNumberError.getText().trim();

	    System.out.println(CYAN + "Expected Message : "
	            + expectedMessage1 + RESET);

	    System.out.println(CYAN + "Actual Message   : "
	            + actualMessage1 + RESET);

	    if (actualMessage1.equals(expectedMessage1)) {

	        System.out.println(
	                GREEN + "✅ PASS: Invalid card number validation displayed correctly."
	                        + RESET
	        );

	    } else {

	        System.out.println(
	                RED + "❌ FAIL: Invalid card number validation mismatch."
	                        + RESET
	        );

	        Assert.fail(
	                "Expected: " + expectedMessage1
	                        + " | Actual: " + actualMessage1
	        );
	    }

	    // Close popup
	    popupCloseButton.click();

	    Common.waitForElement(1);

	    System.out.println(
	            GREEN + "✅ Gift Card Balance popup closed successfully."
	                    + RESET
	    );
	}
	public void openforTheGiftcardPage() {
		  driver.get(FileReaderManager.getInstance()
		            .getConfigReader()
		            .getApplicationUrl());

		    
		    scrollUsingJSWindow();
		    Common.waitForElement(1);

		    click(giftCard);

		    Common.waitForElement(2);

	}
	
	 public void scrollUsingJSWindow() {

			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("window.scrollTo(0, 7200);");

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
