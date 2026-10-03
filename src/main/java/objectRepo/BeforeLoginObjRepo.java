package objectRepo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import basePage.BasePage;

public abstract class BeforeLoginObjRepo  extends BasePage{
	
	@FindBy(name = "access_code")
	protected WebElement accessCode;
	
	@FindBy(xpath = "//button[text()='Submit']")
	protected WebElement submit;
	
	@FindBy(xpath = "(//span[@class='landing_page_link_btn'][normalize-space()='SHOP NOW'])[1]")
	   protected WebElement zlaataIndiaShopButton;
	
	@FindBy(xpath = "(//button[@title='Account'])[1]")
	protected WebElement accountIconbutton;
	
	@FindBy(xpath = "//div[@class='login_popup_wrap']")
	protected WebElement loginPopup;
	
	@FindBy(xpath = "(//button[@title='Wishlist'])[1]")
	protected WebElement WishListIcon;
	
	@FindBy(xpath = "//button[@class='header_cta_btn account_icon_btn ']")
	protected WebElement profile;

	@FindBy(xpath = "//a[contains(text(), 'Loyalty Points')]")
	protected WebElement clickOnLoyalitypoints;

	@FindBy(xpath = "//p[contains(text(), 'Sign Up Now')]")
	protected WebElement threadsSignupButton;

	@FindBy(xpath = "//span[@class='login_process_heading' and normalize-space()='Login']")
	protected WebElement threadsloginpopup;

	@FindBy(xpath = "//a[@class='thread_step1_block2_content_img1_box thread_dob']")
	protected WebElement threadPageDOBButton;

	@FindBy(xpath = "//p[@class='thread_step1_block3_content_img1_box threads_button']")
	protected WebElement  threadPagereferNowButton;

	@FindBy(xpath = "//a[@class='thread_step1_block4_content_img1_box thread_rate_us']")
	protected WebElement threadPageRateusButton;

	@FindBy(xpath = "//a[@class='thread_step1_block5_content_img1_box']")
	protected WebElement threadPageViewGallery;

	@FindBy(xpath = "//button[@class='threads_donations_bannerButton']")
	protected WebElement donateWithLoveBannerButton;

	

	@FindBy(xpath = "//button[@class='place_order_btn Cls_place_order btn___2 ']")
	protected WebElement placeOrderButtonInCheckoutPage;
	
	@FindBy(xpath = "//div[@title='Move to wishlist']//img[@alt='Image']")
	protected WebElement checkouPageWishlistButton;
	
	@FindBy(xpath = "//button[normalize-space()='apply']")
	protected WebElement applyButtonOnCheckoutPage;
	
	@FindBy(xpath = "//button[@class='checkout_details_sub_heading viewCouponBtn']")
	protected WebElement viewCouponButton;
	
	@FindBy(xpath = "//div[@class='coupon_popup_input_wrap']//button[@type='submit']")
	protected WebElement applyButtonInViewCouponPopup;
	
	@FindBy(xpath = "//input[@placeholder='Enter threads']")
	protected WebElement threadTextBox;
	

	@FindBy(xpath = "//h2[@class='landing_page_title' and text()='BOSS LADY']")
	protected WebElement clickOnBossladyShopNowButton;

	


}
