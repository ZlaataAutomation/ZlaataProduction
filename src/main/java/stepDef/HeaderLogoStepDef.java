package stepDef;

import context.TestContext;
import io.cucumber.java.en.Given;
import pages.HeaderLogoPage;

public class HeaderLogoStepDef {



	TestContext testContext;
	HeaderLogoPage headlogo;

	public HeaderLogoStepDef(TestContext context)
	{
		testContext = context;
		headlogo = testContext.getPageObjectManager().getHeaderLogoPage();
	}


	@Given("User on the hoame page of zlaata India and clik header logo")
	public void user_on_the_hoame_page_of_zlaata_india_and_clik_header_logo() {

		headlogo.zlaataIndiaHeaderLogoRedirection();

	}




	@Given("User on the hoame page of Boss lady and clik header logo")
	public void user_on_the_hoame_page_of_boss_lady_and_clik_header_logo() {
		
		headlogo.bossLadyHeaderLogoRedirection();

	}




		@Given("User on the landing page  and clik header logo")
	public void user_on_the_landing_page_and_clik_header_logo() {
		
			headlogo.landingPageofHeaderLogo();
	}






}
