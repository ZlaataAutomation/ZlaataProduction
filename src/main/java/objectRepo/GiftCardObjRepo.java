package objectRepo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import basePage.BasePage;

public abstract class GiftCardObjRepo  extends BasePage{
	
	
	@FindBy(xpath = "//a[normalize-space()='Gift Card']")
	protected WebElement giftCard;

	
	@FindBy(xpath = "//button[@class='gift-card-button gift-card-next']")
	protected WebElement nextButton;
	
	@FindBy(xpath = "//div[@class='gift-amount-section__box gift-amount-section__box--value-added gift_card_amount']")
	protected WebElement choosegiftCardAmount;
	
	@FindBy(xpath = "//input[@id='gift_recipient_email']")
	protected  WebElement recipientEmail;

	@FindBy(xpath = "//input[@id='gift_name']")
	protected WebElement recipientName;

	@FindBy(xpath = "//input[@id='gift__dob']")
	protected WebElement deliveryDate;

	@FindBy(xpath = "//input[@id='gitPhonenumber']")
	protected WebElement phoneNumber;

	@FindBy(xpath = "//textarea[@id='gift_message']")
	protected WebElement giftMessage;

	@FindBy(xpath = "//input[@id='gift_sendName']")
	protected WebElement senderName;
	
	
	@FindBy(xpath = "//div[@id='suggestions-box']//div")
	protected WebElement suggestionBox;
	
	
	@FindBy(xpath = "//button[normalize-space()='Preview']")
	protected WebElement previewButton;
	
	@FindBy(xpath = "//button[contains(@class,'giftcard_addToCart') and normalize-space()='Add to Cart']")
	protected WebElement giftCardAddToCart;
	
	@FindBy(xpath = "//button[contains(@class,'Cls_gc_remove_btn') and normalize-space()='remove']")
	protected WebElement giftCardRemove;

	@FindBy(xpath = "//span[contains(@class,'cp_current_price')]")
	protected WebElement  currentPrice;
	
	@FindBy(xpath = "//div[@class='price_details_pair Cls_cart_gift_card_mrp']")
	protected WebElement giftCardMRP;
	
	@FindBy(xpath = "//button[normalize-space()='Cancel']")
	protected WebElement cancelButton;
	
	@FindBy(xpath = "//button[@id='gift-popup-yes']")
	protected WebElement yesDiscardButton;
	
	@FindBy(xpath = "//div[contains(@class,'gc-arrow-next')]")
	protected WebElement nextArrow;

	@FindBy(xpath = "//div[contains(@class,'gc-arrow-prev')]")
	protected WebElement previousArrow;

	@FindBy(xpath = "//div[contains(@class,'selected_gift_image')]//img")
	protected WebElement selectedGiftCardImage;
	
	
	@FindBy(xpath = "//p[normalize-space()='Learn More']")
	protected WebElement learnMore;
	
	
	@FindBy(xpath = "//button[contains(@class,'gift_card_check_balance') and normalize-space()='Check Balance']")
	protected WebElement checkBalanceButton;
	
	@FindBy(xpath = "//button[@class='btn___2 check_balance_btn']")
	protected WebElement giftcardBalancePopup;
	
	@FindBy(xpath = "//p[contains(@class,'gift_card_error__msg') and normalize-space()='Please enter a gift card number.']")
	protected WebElement giftCardErrorMessage;
	
	@FindBy(xpath = "//input[@id='GiftCardInput']")
	protected WebElement giftCardNumberInput;
	
	
	@FindBy(xpath = "//p[contains(@class,'gift_card_error__msg') and normalize-space()='Invalid card number! Try again.']")
	protected WebElement invalidCardNumberError;
	
	@FindBy(xpath = "//div[contains(@class,'popup_containers_cls_btn')]")
	protected WebElement popupCloseButton;
}
