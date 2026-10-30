package pages;


import java.time.Duration;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import objectRepo.GuestLoginObjRepo;
import utils.Common;

public final  class GuestLoginPage extends  GuestLoginObjRepo {


public GuestLoginPage(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}


String GREEN  = "\u001B[32m";
String RED    = "\u001B[31m";
String YELLOW = "\u001B[33m";
String CYAN   = "\u001B[36m";
String BLUE   = "\u001B[34m";
String RESET  = "\u001B[0m";
	
public void guestLoginButton() {

   

    HomePage page = new HomePage(driver);
    page.homeLaunch();

    OrdersPage order = new OrdersPage(driver);
    order.takeRandomProductFromAll();

    placeOrderButton.click();

    if (checkoutASGuestButton.isDisplayed()) {

        System.out.println(GREEN + "SUCCESS: Guest Login button is displayed" + RESET);

        checkoutASGuestButton.click();

        System.out.println(GREEN + "SUCCESS: Guest Login button clicked successfully" + RESET);

        String currentURL = driver.getCurrentUrl();

        System.out.println(CYAN + "Current URL after clicking Guest Login: " + currentURL + RESET);

        if (currentURL.contains("checkout/address")) {

            System.out.println(GREEN + "SUCCESS: Redirected to Checkout/Address page" + RESET);

        } else {

            System.out.println(RED + "FAILED: Not redirected to Checkout/Address page" + RESET);

        }

       

    } else {

        System.out.println(RED + "FAILED: Guest Login button is not displayed" + RESET);

    }

}
  public void addressPageHeading() {
	
	  String heading = addressPageHeading.getText();

      if (heading != null && !heading.trim().isEmpty()) {
          System.out.println(GREEN + "PASS: Address Page Heading: " + heading + RESET);
      } else {
          System.out.println(RED + "FAIL: Address page heading is not displayed" + RESET);
          Assert.fail("Address page heading is not displayed");
      }

}


public void addressPagePhoneNumber() {

	guestLoginButton();
	
	String phone = phoneNumber.getAttribute("value");

	if (phone != null && !phone.trim().isEmpty()) {
	    System.out.println(RED + "FAIL: Phone number is auto-populated: " + phone + RESET);
	    Assert.fail("Phone number should not be auto-populated");
	} else {
	    System.out.println(GREEN + "PASS: Phone number is not auto-populated" + RESET);
	}	
}


public void validateAllFields() {

 

    if (validationName.getText().equals("Name should be between 3 and 50 characters.")) {
        System.out.println(GREEN + "✅ PASS: Name validation" + RESET);
    } else {
        System.out.println(RED + "❌ FAIL: Name validation" + RESET);
    }

    if (validationPhoneNumber.getText().equals("Please enter a valid 10-digit Indian phone number (may start with +91).")) {
        System.out.println(GREEN + "✅ PASS: Phone validation" + RESET);
    } else {
        System.out.println(RED + "❌ FAIL: Phone validation" + RESET);
    }

    if (validationMailID.getText().equals("Please enter a valid email address.")) {
        System.out.println(GREEN + "✅ PASS: Email validation" + RESET);
    } else {
        System.out.println(RED + "❌ FAIL: Email validation" + RESET);
    }

    if (validationAddressTxtBox.getText().equals("Address must be between 3 and 50 characters.")) {
        System.out.println(GREEN + "✅ PASS: Address validation" + RESET);
    } else {
        System.out.println(RED + "❌ FAIL: Address validation" + RESET);
    }

    if (validationStreet.getText().equals("Street must be between 3 and 50 characters.")) {
        System.out.println(GREEN + "✅ PASS: Street validation" + RESET);
    } else {
        System.out.println(RED + "❌ FAIL: Street validation" + RESET);
    }

    if (validationPincode.getText().equals("Please enter a valid 6-digit pincode.")) {
        System.out.println(GREEN + "✅ PASS: Pincode validation" + RESET);
    } else {
        System.out.println(RED + "❌ FAIL: Pincode validation" + RESET);
    }

    if (valiadtionLocality.getText().equals("Locality/Town must be between 3 and 50 characters.")) {
        System.out.println(GREEN + "✅ PASS: Locality validation" + RESET);
    } else {
        System.out.println(RED + "❌ FAIL: Locality validation" + RESET);
    }

    if (validationDistrict.getText().equals("District is required.")) {
        System.out.println(GREEN + "✅ PASS: District validation" + RESET);
    } else {
        System.out.println(RED + "❌ FAIL: District validation" + RESET);
    }

    if (validationState.getText().equals("State is required.")) {
        System.out.println(GREEN + "✅ PASS: State validation" + RESET);
    } else {
        System.out.println(RED + "❌ FAIL: State validation" + RESET);
    }
}

public void guestUseraddressValiadtion() {
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	WebElement saveButton = wait.until(
	    ExpectedConditions.visibilityOf(saveButtonInAddressPage)
	);

	((JavascriptExecutor) driver).executeScript(
	    "arguments[0].scrollIntoView({block: 'center'});",
	    saveButton
	);

	saveButton.click();

    System.out.println(GREEN + "Clicked Save Address button without entering details" + RESET);

    validateAllFields();
}




public void verifyPlaceOrderButtonDisabled() {

    if (!guestUserPalceOrderButton.isEnabled()
            || guestUserPalceOrderButton.getAttribute("class").contains("disabled")) {

        System.out.println("\u001B[32mPASS: Place Order button is disabled\u001B[0m");

    } else {

        System.out.println("\u001B[31mFAIL: Place Order button is enabled\u001B[0m");
    }
}

public void addNewAddressAsGuest() {
	
	enterRandomAddressDetails();
}
	


public void newAddressData() {

	Common.waitForElement(2);
	String value = Common.getValueFromTestDataMap("Name Address");
	System.out.println("Testing name: " + value);
	type(nameTextBox, value);
	String enteredname = nameTextBox.getAttribute("value");  // Fixed here
	System.out.println("Entered name in application : " + enteredname);
	
	
	String number = Common.getValueFromTestDataMap("Mobile Number ");
	System.out.println("Testing  Number: " + number);
	type(addressPageNumber, number);
	String enteredNumber = addressPageNumber.getAttribute("value");  // Fixed here
	System.out.println("Entered name in application : " + enteredNumber);

	String value1 = Common.getValueFromTestDataMap("Email Id Address");
	System.out.println("Testing Email ID: " + value1);
	type(emailIDTextBox, value1);
	String enteredname1 = emailIDTextBox.getAttribute("value");  // Fixed here
	System.out.println("Entered email id in application : " + enteredname1);

	String value2 = Common.getValueFromTestDataMap("Mobile Number");
	System.out.println("Testing Mobile number: " + value2);
	type(phoneNumberTextBox, value2);
	String enteredname2 = phoneNumberTextBox.getAttribute("value");  // Fixed here
	System.out.println("Entered Phonumber in application : " + enteredname2);

	String value3 = Common.getValueFromTestDataMap("Address");
	System.out.println("Testing Address : " + value3);
	type(addressDetailsTextBox, value3);
	String enteredname3 = addressDetailsTextBox.getAttribute("value");  // Fixed here
	System.out.println("Entered Address in application : " + enteredname3);

	String value4 = Common.getValueFromTestDataMap("Street");
	System.out.println("Testing Street : " + value4);
	type(streetTextBox, value4);
	String enteredname4 = streetTextBox.getAttribute("value");  // Fixed here
	System.out.println("Entered Street in application : " + enteredname4);


	String value5 = Common.getValueFromTestDataMap("Pincode");
	System.out.println("Testing Pincode : " + value5);
	type(pinCodeTextBox, value5);
	String enteredname5 = pinCodeTextBox.getAttribute("value");  // Fixed here
	System.out.println("Entered pincode in application : " + enteredname5);
	Common.waitForElement(2);

	String value6 = Common.getValueFromTestDataMap("Locality");
	System.out.println("Testing town : " + value6);
	type(townTextBox, value6);
	String enteredname6 = townTextBox.getAttribute("value");  // Fixed here
	System.out.println("Entered town in application : " + enteredname6);

	Common.waitForElement(2);

	String enteredname7 = district.getText();  // Fixed here
	System.out.println("Entered district in application : " + enteredname7);


	String enteredname8 = state.getText();  // Fixed here
	System.out.println("Entered state in application : " + enteredname8);

	click(saveAddressButton);
	Common.waitForElement(5);
}


public void verifyEstimatedDeliverySection() {
	
	
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    try {
        wait.until(ExpectedConditions.visibilityOf(estimateDeliverySection));

        if (estimateDeliverySection.isDisplayed()) {
            System.out.println("\u001B[32mEstimated Delivery section is displayed: "
                    + estimateDeliverySection.getText() + "\u001B[0m");
        }
    } catch (Exception e) {
        System.out.println("\u001B[31mEstimated Delivery section is not displayed.\u001B[0m");
        Assert.fail("Estimated Delivery section is not displayed.");
    }
}


public void enterRandomAddressDetails() {

    Random random = new Random();

    String[] indianPinCodes = {
        "110001", "400001", "600001", "700001", "500001",
        "560001", "411001", "380001", "302001", "682001",
        "522001", "751001", "160001", "226001", "440001"
    };

    String name = "Test User" + random.nextInt(10000);
    String phone = "9" + String.format("%09d", random.nextInt(1000000000));
    String email = "testuser" + random.nextInt(1000000) + "@gmail.com";
    String address = "No " + random.nextInt(999) + ", Test Street";
    String street = "Test Street " + random.nextInt(1000);
    String pincode = indianPinCodes[random.nextInt(indianPinCodes.length)];
    String town = "Town " + random.nextInt(1000);
    String locality = "Locality " + random.nextInt(1000);

    nameTextBox.clear();
    nameTextBox.sendKeys(name);

    phoneNumberTextBox.clear();
    phoneNumberTextBox.sendKeys(phone);

    emailIDTextBox.clear();
    emailIDTextBox.sendKeys(email);

    addressDetailsTextBox.clear();
    addressDetailsTextBox.sendKeys(address);

    streetTextBox.clear();
    streetTextBox.sendKeys(street);

    pinCodeTextBox.clear();
    pinCodeTextBox.sendKeys(pincode);

    townTextBox.clear();
    townTextBox.sendKeys(town);

    System.out.println("\u001B[32m========== Random Address Details ==========");
    System.out.println("Name: " + name);
    System.out.println("Phone: " + phone);
    System.out.println("Email: " + email);
    System.out.println("Address: " + address);
    System.out.println("Street: " + street);
    System.out.println("Pincode: " + pincode);
    System.out.println("Town: " + town);
    System.out.println("Locality: " + locality);
    System.out.println("============================================\u001B[0m");
    
    click(saveAddressButton);
	Common.waitForElement(5);
}


 public void editTheAddrssForGuestLogin() {
	
//	 editButtonInGuestLoginAddressPage.click();
	 
	 printExistingAddressDetails();
	 
	 
	 
	 enterRandomAddressDetails();
	 
	 

}
 
 public void printExistingAddressDetails() {

	    System.out.println("\u001B[34m========== Existing Address Details ==========\u001B[0m");

	    System.out.println("Name: " + nameTextBox.getAttribute("value"));
	    System.out.println("Phone: " + phoneNumberTextBox.getAttribute("value"));
	    System.out.println("Email: " + emailIDTextBox.getAttribute("value"));
	    System.out.println("Address: " + addressDetailsTextBox.getAttribute("value"));
	    System.out.println("Street: " + streetTextBox.getAttribute("value"));
	    System.out.println("Pincode: " + pinCodeTextBox.getAttribute("value"));
	    System.out.println("Town: " + townTextBox.getAttribute("value"));

	    System.out.println("\u001B[34m==============================================\u001B[0m");
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
