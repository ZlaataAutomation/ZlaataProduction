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



		@Given("User on the home page of Boss Lady")
	public void user_on_the_home_page_of_boss_lady() {
			logo.bossLadyfooterLogoRedirection();
	}





	@Given("User on the landing page")
	public void user_on_the_landing_page() {


		logo.landingPage();
	}





}
