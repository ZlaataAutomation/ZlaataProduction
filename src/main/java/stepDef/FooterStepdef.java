package stepDef;
import context.TestContext;
import io.cucumber.java.en.Given;
import pages.FooterPage;
import pages.HomePage;

public class FooterStepdef  {


	TestContext testContext;
	FooterPage footer;
	HomePage home;


	public FooterStepdef(TestContext context) {
		testContext = context;
		footer = testContext.getPageObjectManager().getFooterPage();
		home = testContext.getPageObjectManager().getHomePage();
	}


	@Given("User verifies About Us details are visible in footer")
	public void user_verifies_about_us_details_are_visible_in_footer() {
		footer.verifyAboutUsLink();
	}


	@Given("User verifies Blogs details are visible in footer")
	public void user_verifies_blogs_details_are_visible_in_footer() {
		footer.verifyBlogs();
	}


	@Given("User verifies Pop Shop details are visible in footer")
	public void user_verifies_pop_shop_details_are_visible_in_footer() {
		footer.verifyPopShopLink();
	}


	@Given("User verifies Terms & Conditions details are visible in footer")
	public void user_verifies_terms_conditions_details_are_visible_in_footer() {
		footer.verifyTermsAndConditionsLink();
	}


	@Given("User verifies Privacy Policy details are visible in footer")
	public void user_verifies_privacy_policy_details_are_visible_in_footer() {
		footer.verifyPrivacyPolicyLink();	   
	}


	@Given("User verifies Raise a query  details are visible in footer")
	public void user_verifies_raise_a_query_details_are_visible_in_footer() {
		footer.verifyRaiseQueryContactUsLink();
	}


	@Given("User verifies Faq  details are visible in footer")
	public void user_verifies_faq_details_are_visible_in_footer() {
		footer.verifyFaqLink();
	}

	@Given("User verifies Shipping & Cancellation Policy  details are visible in footer")
	public void user_verifies_shipping_cancellation_policy_details_are_visible_in_footer() {
		footer.verifyShippingAndCancellationPolicyLink();
	}


	@Given("User verifies Return, Exchange & Replacement Policy  details are visible in footer")
	public void user_verifies_return_exchange_replacement_policy_details_are_visible_in_footer() {
		footer.verifyReturnExchangeReplacementPolicyLink();
	}

	@Given("User verifies Contact Us  details are visible in footer")
	public void user_verifies_contact_us_details_are_visible_in_footer() {
		footer.verifyFooterContactUsDetails();
	}

	@Given("User verifies Track Order   are visible in footer")
	public void user_verifies_track_order_are_visible_in_footer() {
		footer.verifyFooterTrackOrderValidation();
	}

	@Given("User verifies social media icons are visible in footer")
	public void user_verifies_social_media_icons_are_visible_in_footer() {
		footer.socialMediaFooter();
	}
	@Given("User verifies the payment logo is visible in footer")
	public void user_verifies_the_payment_logo_is_visible_in_footer() {
		footer.verifyPaymentMethods();


	}

	@Given("User verifies Copy Rights is visible in footer")
	public void user_verifies_copy_rights_is_visible_in_footer() {
		footer.verifyCopyRights();

	}


	@Given("User the newsletter subscription field")
	public void user_the_newsletter_subscription_field() {
		footer.verifyFooterSubscribeValidations();
	}




















	@Given("User going to click Shop All footer links")
	public void user_going_to_click_shop_all_footer_links() {
		home.homeLaunch();	
		footer.footerShopAllLinks();
	}




	@Given("User verifies that the Loyalty Points link in the footer navigates to the correct page")
	public void user_verifies_that_the_loyalty_points_link_in_the_footer_navigates_to_the_correct_page() {

		footer.threadBanner();

	}


//		@Given("User verifies that the Gift Card link in the footer redirects to the correct page")
//	public void user_verifies_that_the_gift_card_link_in_the_footer_redirects_to_the_correct_page() {
//		footer.giftcardBanner();
//	}







	//
	//	@Given("User verifies Contact Us details are visible in footer")
	//	public void user_verifies_contact_us_details_are_visible_in_footer() {
	//		home.homeLaunch();
	//		footer.contactUS();
	//	}
	//
	//

	//	@Given("User verifies the Zlaata logo is visible in footer")
	//	public void user_verifies_the_zlaata_logo_is_visible_in_footer() {
	//		home.homeLaunch();
	//		footer.zlaataLogo();
	//	}




	@Given("User verifies Zlaata email ID is visible in footer")
	public void user_verifies_zlaata_email_id_is_visible_in_footer() {
		home.homeLaunch();
		footer.copyRights();
	}


	@Given("User enters a new valid email ID in the newsletter subscription field")
	public void user_enters_a_new_valid_email_id_in_the_newsletter_subscription_field() {
		home.homeLaunch();
		footer.footerSubscribe();
	}


	@Given("User enters an already subscribed email ID in the newsletter subscription field")
	public void user_enters_an_already_subscribed_email_id_in_the_newsletter_subscription_field() {
		home.homeLaunch();
		footer.footerSubscribeAlready();
	}


	@Given("User enters an invalid email ID in the newsletter subscription field")
	public void user_enters_an_invalid_email_id_in_the_newsletter_subscription_field() {
		home.homeLaunch();
		footer.invalidMail();
	}










}
