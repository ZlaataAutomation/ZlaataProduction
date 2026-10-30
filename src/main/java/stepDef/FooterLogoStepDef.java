package stepDef;

import context.TestContext;
import io.cucumber.java.en.Given;
import pages.FooterLogoPage;

public class FooterLogoStepDef {

	TestContext testContext;
	FooterLogoPage logo;

	public FooterLogoStepDef(TestContext context) {
		testContext = context;
		logo = testContext.getPageObjectManager().getFooterLogoPage();
	}


		@Given("User on the hoame page of zlaata India")
	public void user_on_the_hoame_page_of_zlaata_india() {
		
			logo.zlaataIndiafooterLogoRedirection();
	}




}
