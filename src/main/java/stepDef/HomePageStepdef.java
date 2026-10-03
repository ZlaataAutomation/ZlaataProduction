package stepDef;



import java.util.concurrent.TimeoutException;

import context.TestContext;
import pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;


public class HomePageStepdef {


	TestContext testContext;
	HomePage home;

	public HomePageStepdef(TestContext context) {
		testContext = context;
		home = testContext.getPageObjectManager().getHomePage();
	}

	//TC-01
	@Given("User able to Launch Url and Logo.")
	public void user_able_to_launch_url_and_logo() {
		home.validateUrlAndLogo();
	}

	@When("User going to click available banners in home page")
	public void user_going_to_click_available_banners_in_home_page() {

		home.bannerClick();
	}


	@Given("User clicks on forward and backward button at home page banner section")
	public void user_clicks_on_forward_and_backward_button_at_home_page_banner_section() {
		home.forAndbackButton();


	}

	@Given("User clicks on pause button")
	public void user_clicks_on_pause_button() {
		home.verifyPauseButton();
	}

	//
	//	@Given("User verifying top selling section")
	//	public void user_verifying_top_selling_section() throws TimeoutException {
	//		home.topSelling();
	//
	//	}



	@Given("User clicks on product image in the New In section of Zlaata India")
	public void user_clicks_on_product_image_in_the_new_in_section_of_zlaata_india() throws InterruptedException {
		home.verifyNewInProductsCompleteFlow();

	}


	//
	//	@Given("User clicks on product image at tope selling section")
	//	public void user_clicks_on_product_image_at_tope_selling_section() throws TimeoutException, InterruptedException {
	//		home.verifyNewInProductsCompleteFlow();
	//
	//	}




	@Given("User clicks on forward and backward arrows on  new arrivals")
	public void user_clicks_on_forward_and_backward_arrows_on_new_arrivals() throws TimeoutException {

		home.newArrivalArrows();

	}


	@Given("User clicks on product image at new arrival section")
	public void user_clicks_on_product_image_at_new_arrival_section() {
		home.newArivalProductImg();

	}

	@Given("User clicks on home page quick view icon")
	public void user_clicks_on_home_page_quick_view_icon() {
		home.quickView();
	}


	@Given("User clicks on inspired by images")
	public void user_clicks_on_inspired_by_images() {
		home.inspiredBy();

	}



	@Given("User submitting feedback")
	public void user_submitting_feedback() {
		home.feedBack();
	}



	@Given("User clicks on whats app icon")
	public void user_clicks_on_whats_app_icon() throws TimeoutException {
		home.whatsApp();
	}

	@Given("User veridying feature on section")
	public void user_veridying_feature_on_section() {
		home.featureOn();

	}


	@Given("User verifying all the available headings in home page")
	public void user_verifying_all_the_available_headings_in_home_page() {
		home.allsectionHomePage();
	}



	@Given("the user verifies that the Thread banner is available on the Home Page for zlaata India  and Boss lady")
	public void the_user_verifies_that_the_thread_banner_is_available_on_the_home_page_for_zlaata_india_and_boss_lady() {
		home.threadBannerINHomePage();
	}




	@Given("the user verifies that the Gift card  banner is available on the Home Page for zlaata India  and Boss lady")
	public void the_user_verifies_that_the_gift_card_banner_is_available_on_the_home_page_for_zlaata_india_and_boss_lady() {
		home.giftCard();

	}


		@Given("the user verifies that the Influencer Banner section is available on the Home Page for zlaata India  and Boss lady")
	public void the_user_verifies_that_the_influencer_banner_section_is_available_on_the_home_page_for_zlaata_india_and_boss_lady() throws InterruptedException {
			
			home.verifyDotsAndProducts();
			home.MonsoonBanner();
			

	}




	@Given("the user verifies that the about us  Banner section is available on the Home Page for zlaata India  and Boss lady")
	public void the_user_verifies_that_the_about_us_banner_section_is_available_on_the_home_page_for_zlaata_india_and_boss_lady() {
		home.aboutus();
	}




	@Given("the user verifies that the collection  banner  is available on the Home Page for zlaata India")
	public void the_user_verifies_that_the_collection_banner_is_available_on_the_home_page_for_zlaata_india() throws InterruptedException {
		home.verifyCollectionBanners();
	}



	@Given("the user verifies that the category  banner  is available on the Home Page for zlaata India")
	public void the_user_verifies_that_the_category_banner_is_available_on_the_home_page_for_zlaata_india() throws InterruptedException {
		home.verifyAllCategoriesWithProductValidation();
	}




	@Given("the user verifies that the category  banner  is available on the Home Page for Boss lady")
	public void the_user_verifies_that_the_category_banner_is_available_on_the_home_page_for_boss_lady() {
		home.verifyBossLadyCategories();
	}


	@Given("the user verifies that the flash notification  is available on the Home Page for Boss lady  and zlaata India")
	public void the_user_verifies_that_the_flash_notification_is_available_on_the_home_page_for_boss_lady_and_zlaata_india() {
		home.flashNotification();
	}











}
