package objectRepo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import basePage.BasePage;

public abstract class  GuestLoginObjRepo extends BasePage {



	@FindBy(xpath = "//button[@class='place_order_btn Cls_place_order btn___2 ']")
	protected WebElement placeOrderButton;


	@FindBy(xpath = "//button[@class='checkout__guest Cls_checkout_guest']")
	protected WebElement checkoutASGuestButton;

	@FindBy(xpath = "//h3[@class='popup_containers_heading Cls_popup_containers_heading']")
	protected WebElement addressPageHeading;

	@FindBy(id = "addressPhoneNumber")
	protected WebElement phoneNumber;

	@FindBy(xpath = "//span[@class='error__msg Cls_form_name_error name_error_msg active']")
	protected WebElement validationName;

	@FindBy(xpath = "//span[@class='error__msg Cls_form_phone_number_error phone_number_error_msg active']")
	protected WebElement validationPhoneNumber;

	@FindBy(xpath = "//span[@class='error__msg Cls_form_email_error email_error_msg active']")
	protected WebElement validationMailID;

	@FindBy(xpath = "//span[@class='error__msg Cls_form_address_error address_error_msg active']")
	protected WebElement validationAddressTxtBox;

	@FindBy(xpath = "//span[@class='error__msg Cls_form_street_error street_error_msg active']")
	protected WebElement validationStreet;

	@ FindBy(xpath = "//span[@class='error__msg pincode_error_msg active']")
	protected WebElement validationPincode;

	@FindBy(xpath = "//span[@class='error__msg Cls_form_locality_town_error locality_error_msg active']")
	protected WebElement valiadtionLocality;

	@FindBy(xpath = "//span[@class='error__msg Cls_form_district_field_error active']")
	protected WebElement validationDistrict;

	@FindBy(xpath = "//span[@class='error__msg Cls_form_address_state_error active']")
	protected WebElement validationState;

	@FindBy(xpath = "//span[@class='address_type_error_msg error__msg Cls_form_comments_error comment_error_msg active']")
	protected WebElement validationCommentTxtBox;

	@FindBy(xpath = "//button[@class='address_form_submit_btn btn___2 Cls_address_form_submit_btn']")
	protected WebElement saveButtonInAddressPage;


	@FindBy(xpath = "(//button[contains(@class,'Cls_place_order') and contains(@class,'disabled')])[1]")
	protected WebElement guestUserPalceOrderButton;



	@FindBy(id = "addressName")
	protected WebElement nameTextBox;

	@FindBy(id = "addressPhoneNumber")
	protected WebElement phoneNumberTextBox;

	@FindBy(id = "addressEmail")
	protected WebElement emailIDTextBox;

	@FindBy(id = "addressDetails")
	protected WebElement addressDetailsTextBox;

	@FindBy(id = "addressStreet")
	protected WebElement streetTextBox;

	@FindBy(id = "addressPincode")
	protected WebElement pinCodeTextBox;

	@FindBy(id ="addressTown")
	protected WebElement townTextBox;

	@FindBy(xpath = "//div[@class='address_dropdown_box cls_district_status']")
	protected WebElement district;

	@FindBy(xpath = "//div[@class='address_dropdown_box cls_state_data']")
	protected WebElement state;

	@FindBy(id = "address_type_home")
	protected WebElement homeTypeRadioButton;

	@FindBy(id ="address_type_work")
	protected WebElement workTypeRadioButton;

	@FindBy(id = "address_type_others")
	protected WebElement otherTypeRadioButton;

	@FindBy(xpath = "//textarea[@class='address_form_textarea input-field zl_comments']")
	protected WebElement enterDataInCommentsTextBox;

	@FindBy(xpath = "//input[@name='address_default_checkbox']")
	protected WebElement saveasDefaultCheckBox;

	@FindBy(xpath = "//span[@class='address__mode']")
	protected WebElement defaultAddress;
	
	@FindBy(id ="addressPhoneNumber")
	protected WebElement addressPageNumber;


	@FindBy(xpath = "//button[@class='address_form_submit_btn btn___2 Cls_address_form_submit_btn']")
	protected WebElement saveAddressButton;
	
	@FindBy(xpath = "//div[@class='estimated_delivery_card Cls_estimated_delivery_card']")
	protected WebElement estimateDeliverySection;
	
	@FindBy(xpath = "address_card_edit_btn Cls_address_card_edit_btn")
	protected WebElement editButtonInGuestLoginAddressPage;


}
