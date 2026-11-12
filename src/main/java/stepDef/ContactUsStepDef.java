package stepDef;

import context.TestContext;
import io.cucumber.java.en.Given;
import pages.ContactUsPage;

public class ContactUsStepDef {


	TestContext testContext;
	ContactUsPage contact;

	public ContactUsStepDef (TestContext context)
	{
		testContext = context;
		contact= testContext.getPageObjectManager().getContactUsPage();


	}



	@Given("that the user is on the Contact Us page and clicks the Send Message button.")
	public void that_the_user_is_on_the_contact_us_page_and_clicks_the_send_message_button()

	{

		contact.contactUsPageIfUserClicksOnSendMessageButtonWithoutEnteringData();
	}



	@Given("that the user is on the Contact Us page and enters fewer than the required number of characters.")
	public void that_the_user_is_on_the_contact_us_page_and_enters_fewer_than_the_required_number_of_characters() {

		contact.contactUsPageIfUserenterfewercharacterClicksOnSendMessage();

	}


		@Given("that the user is on the Contact Us page and enters characters in the Queries field to verify whether the character count decreases.")
	public void that_the_user_is_on_the_contact_us_page_and_enters_characters_in_the_queries_field_to_verify_whether_the_character_count_decreases() {
		
	}






}
