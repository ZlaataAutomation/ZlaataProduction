package stepDef;

import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.GuestLoginPage;

public class GuestLoginStepDef {




	TestContext testContext;
	GuestLoginPage guest;


	public GuestLoginStepDef(TestContext context) {
		testContext = context;
		guest = testContext.getPageObjectManager().getGuestLoginPage();
	}




	@Given("user is on the Checkout page and clicks on the Place Order button")
	public void user_is_on_the_checkout_page_and_clicks_on_the_place_order_button() {

		guest.guestLoginButton();
	}



	@Given("the user is on the Address page through Guest Login")
	public void the_user_is_on_the_address_page_through_guest_login() {

		guest.addressPagePhoneNumber();
	}


	@When("the user clicks the Save Address button without entering the mandatory details")
	public void the_user_clicks_the_save_address_button_without_entering_the_mandatory_details() {
		guest.guestUseraddressValiadtion();
	}


	@Then("the Place Order button should remain disabled when the mandatory address details are empty")
	public void the_place_order_button_should_remain_disabled_when_the_mandatory_address_details_are_empty() {
		guest.verifyPlaceOrderButtonDisabled();
	}



	@When("the user enters valid details in all mandatory address text boxes")
	public void the_user_enters_valid_details_in_all_mandatory_address_text_boxes() {
		guest.addNewAddressAsGuest();
	}



	@Then("the Estimated Delivery section should be displayed")
	public void the_estimated_delivery_section_should_be_displayed() {
		guest.verifyEstimatedDeliverySection();


	}


		@Then("the user should be able to edit the already entered address")
	public void the_user_should_be_able_to_edit_the_already_entered_address() {
		
			guest.editTheAddrssForGuestLogin();
	}







}
