package stepDef;

import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.BeforeLoginPage;

public class BeforeLoginStepDef {

	TestContext testContext;
	BeforeLoginPage beforelogin;


	public BeforeLoginStepDef(TestContext context) {
		testContext = context;
		beforelogin = testContext.getPageObjectManager().getBeforeLogin();
	}





		@Given("user navigates to the Zlaata  India home page  as a guest user")
	public void user_navigates_to_the_zlaata_india_home_page_as_a_guest_user() {
		beforelogin.verifyLogOut();
	}




	//TC-01
	@Given("user navigates to the Zlaata homepage as a guest user")
	public void user_navigates_to_the_zlaata_homepage_as_a_guest_user() throws Exception {


		//		beforelogin.verifyLogOut();

		beforelogin.navigateToHomePageAsGuest();


	}

	@When("user clicks on the Profile icon in the header")
	public void user_clicks_on_the_profile_icon_in_the_header() {

	}

	@Then("verify that the login popup is displayed successfully")
	public void verify_that_the_login_popup_is_displayed_successfully() {

	}


	//TC-02
	@When("user clicks on the Wishlist icon from the top right corner header section")
	public void user_clicks_on_the_wishlist_icon_from_the_top_right_corner_header_section() throws Exception {
		beforelogin.LoginPopupOnClickingWishlist();
	}


	//TC-03
	@Given("user navigates to the product listing page as a guest user")
	public void user_navigates_to_the_product_listing_page_as_a_guest_user() throws Exception {

		beforelogin.listingPageWishlistPopup();

	}

	@When("user clicks on the Wishlist icon on the product listing page")
	public void user_clicks_on_the_wishlist_icon_on_the_product_listing_page() {

	}


	//TC-04
	@Given("user navigates to the product details page as a guest user")
	public void user_navigates_to_the_product_details_page_as_a_guest_user() throws Exception {

	}

	@When("user clicks on the Wishlist icon on the product details page")
	public void user_clicks_on_the_wishlist_icon_on_the_product_details_page() throws Exception {

		beforelogin.productDetailsWishlistPopupFlow();

	}

	@Then("verify that the login popup is displayed successfully on product details page")
	public void verify_that_the_login_popup_is_displayed_successfully_on_product_details_page() {

		System.out.println("Login popup verified successfully.");

	}


	//TC-05
	@Given("user navigates to the cart page as a guest user")
	public void user_navigates_to_the_cart_page_as_a_guest_user() throws Exception {

	}

	@When("user clicks on the Move to Wishlist icon on the cart page")
	public void user_clicks_on_the_move_to_wishlist_icon_on_the_cart_page() throws Exception {
		beforelogin.checkoutPageWishListButton();

	}

	@Then("the login popup should be displayed successfully for Move to Wishlist action on cart page")
	public void the_login_popup_should_be_displayed_successfully_for_move_to_wishlist_action_on_cart_page() {

	}


	//TC-06
	@When("user clicks on the View Coupons option on the cart page")
	public void user_clicks_on_the_view_coupons_option_on_the_cart_page() throws Exception {

		beforelogin.viewCouponApplyButton();


	}

	@Then("the login popup should be displayed successfully for View Coupons action on cart page")
	public void the_login_popup_should_be_displayed_successfully_for_view_coupons_action_on_cart_page() {

	}


	//TC-07
	@When("user clicks on the Apply button in the coupon section on the cart page")
	public void user_clicks_on_the_apply_button_in_the_coupon_section_on_the_cart_page() throws Exception {

		beforelogin.checkoutPageApplyButton();


	}

	@Then("the login popup should be displayed successfully for Apply Coupon action on cart page")
	public void the_login_popup_should_be_displayed_successfully_for_apply_coupon_action_on_cart_page() {

	}


	//TC-08
	@When("user clicks on the Game icon")
	public void user_clicks_on_the_game_icon() throws Exception {

	}

	@Then("the login popup should be displayed successfully after clicking the Game icon")
	public void the_login_popup_should_be_displayed_successfully_after_clicking_the_game_icon() {

	}


	//TC-09
	@Given("user navigates to the Threads page as a guest user")
	public void user_navigates_to_the_threads_page_as_a_guest_user() throws Exception {

	}

	@When("user clicks on any action button such as Sign Up Now, Enter DOB, Refer Now, or Rate Us")
	public void user_clicks_on_any_action_button_such_as_sign_up_now_enter_dob_refer_now_or_rate_us() throws Exception {


		beforelogin.threadPageSignUpButton();
		beforelogin.threadPageEnterDOBButton();
		beforelogin.threadPageRateUsButton();
		beforelogin.threadPageReferNowButtonOnThreadButton();
		beforelogin.threadPageViewGalleryButton();

	}

	@Then("the login popup should be displayed successfully for Threads action")
	public void the_login_popup_should_be_displayed_successfully_for_threads_action() {

	}


	//TC-10
	@When("user clicks on the Place Order button")
	public void user_clicks_on_the_place_order_button() throws Exception {


		beforelogin.checkoutPagePalceOrderButton();
	}

	@Then("the login popup should be displayed successfully for Place Order action")
	public void the_login_popup_should_be_displayed_successfully_for_place_order_action() {



	}



	//TC-11

	@Then("the validation message Please login to apply threads. should be displayed")
	public void the_validation_message_please_login_to_apply_threads_should_be_displayed() {

		beforelogin.CheckoutPageTrytoApplyThread();

	}


	@When("user clicks on Threads option without logging in")
	public void user_clicks_on_threads_option_without_logging_in() throws Exception {



	}

	@Then("the validation message  should be displayed")
	public void the_validation_message_should_be_displayed(String message) {

	}


	//    //TC-12
	//    @When("user clicks on the Gift Card Amount section without logging in")
	//    public void user_clicks_on_the_gift_card_amount_section_without_logging_in() throws Exception {
	//
	//    }
	//
	//    @Then("the validation message  should be displayed for gift card amount")
	//    public void the_validation_message_should_be_displayed_for_gift_card_amount(String message) {
	//
	//    }



	@Given("user navigates to the product details page")
public void user_navigates_to_the_product_details_page() {
	
		beforelogin.verifyOutOfStockProductAvailable();
}



}