package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import manager.FileReaderManager;
import objectRepo.ContactUsPageObjRepo;
import utils.Common;

public final class ContactUsPage  extends ContactUsPageObjRepo{

	public ContactUsPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(this.driver, this);

	}



	
	String CYAN = "\u001B[36m";
	String RESET = "\u001B[0m";
	String RED = "\u001B[31m";
	
	String GREEN = "\u001B[32m";
	
	
	public void contactUsPageIfUserClicksOnSendMessageButtonWithoutEnteringData() {

	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());

	    // Contact Us page
	    ((JavascriptExecutor) driver).executeScript(
	            "window.scrollTo(0, document.body.scrollHeight);");

	    Common.waitForElement(2);

	    driver.findElement(By.xpath(
	            "//div[@class='footer_details']//a[@href='/contact-us']")).click();

	    Common.waitForElement(2);

	    sendMessage.click();

	    System.out.println(CYAN + "Name Validation: " + nameValidation.getText() + RESET);
	    System.out.println(CYAN + "Phone Number Validation: " + phoneNumberValidation.getText() + RESET);
	    System.out.println(CYAN + "Email ID Validation: " + emailID.getText() + RESET);
	    System.out.println(CYAN + "Queries Validation: " + queries.getText() + RESET);
	}




	public void contactUsPageIfUserenterfewercharacterClicksOnSendMessage() {
		
		
	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());

	    // Contact Us page
	    ((JavascriptExecutor) driver).executeScript(
	            "window.scrollTo(0, document.body.scrollHeight);");

	    Common.waitForElement(2);

	    driver.findElement(By.xpath(
	            "//div[@class='footer_details']//a[@href='/contact-us']")).click();

	    Common.waitForElement(2);
	    
	    
	  
//	    
//	 // Invalid data
//	    name.sendKeys("Hi");
//	    phoneNUmber.sendKeys("96");
//	   
//
//	    sendMessage.click();
//
//	    System.out.println(CYAN + "Name Validation: " + nameValidation.getText() + RESET);
//	    System.out.println(CYAN + "Phone Number Validation: " + phoneNumberValidation.getText() + RESET);
//
//
//	    // Valid Name and Phone
//	    name.clear();
//	    name.sendKeys("Hiii");
//
//	    phoneNUmber.clear();
//	    phoneNUmber.sendKeys("9629674856");
//
//	    sendMessage.click();
//
//	    System.out.println(CYAN + "Name Validation Disappeared: "
//	            + driver.findElements(By.xpath("YOUR_NAME_VALIDATION_XPATH")).isEmpty() + RESET);
//
//	    System.out.println(CYAN + "Phone Validation Disappeared: "
//	            + driver.findElements(By.xpath("YOUR_PHONE_VALIDATION_XPATH")).isEmpty() + RESET);
//
//	    // Valid Email
//	    emailId.clear();
//	    emailId.sendKeys("charil30072000@gmail.com");
//
//	    sendMessage.click();
//
//	    System.out.println(CYAN + "Email Validation Disappeared: "
//	            + driver.findElements(By.xpath("YOUR_EMAIL_VALIDATION_XPATH")).isEmpty() + RESET);

	    
	  
}
	
	
	public void queriesTextBoxCharacterCountReducing() {
		
	
	
		int initialCount = Integer.parseInt(queriesTextBox.getAttribute("maxlength"));

		System.out.println(CYAN + "Initial Character Count: " + initialCount + RESET);

		queriesTextBox.sendKeys("Testing");

		int remainingCount = initialCount - queriesTextBox.getAttribute("value").length();

		System.out.println(CYAN + "Remaining Character Count: " + remainingCount + RESET);

		if (remainingCount < initialCount) {
		    System.out.println(GREEN + "Query Textbox Count Is Reducing" + RESET);
		} else {
		    System.out.println(RED + "Query Textbox Count Is Not Reducing" + RESET);
		}



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
