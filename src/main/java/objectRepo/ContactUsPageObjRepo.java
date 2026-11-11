package objectRepo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import basePage.BasePage;

public abstract class ContactUsPageObjRepo extends BasePage {
	
	

	
	@FindBy(xpath = "//button[@class='contact__send__btn btn___2 cls_contact_us']")
	protected WebElement sendMessage;
	
	
	@FindBy(xpath = "//span[@class='error__msg name_error_msg active']")
	protected WebElement nameValidation;
	
	
	@FindBy(xpath = "//span[@class='error__msg phone_error_msg active']")
	protected WebElement phoneNumberValidation;
	
	
	@FindBy(xpath = "//span[@class='error__msg email_error_msg active']")
	protected WebElement emailID;
	
	@FindBy(xpath = "//span[@class='error__msg query_error_msg active']")
	protected WebElement queries;
	
	
	@FindBy(xpath = "//input[@class='cls_name name_writetous']")
	protected WebElement name;
	
	@FindBy(xpath = "//input[@class='cls_ph_no']")
	protected WebElement  phoneNUmber;
	
	@FindBy(id = "email_id")
	protected WebElement emailId;
	
	@FindBy(id = "txtarea")
	protected WebElement queriesTextBox;
	
	@FindBy(id= "raiseQueryNum")
	protected WebElement queriesTextBoxCount;
	
	
	
	
	
}
