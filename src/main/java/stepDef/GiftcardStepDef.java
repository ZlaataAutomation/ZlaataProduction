package stepDef;

import context.TestContext;
import io.cucumber.java.en.Given;
import pages.GiftCardPage;

public class GiftcardStepDef {


	TestContext testContext;
	GiftCardPage giftCard;


	public  GiftcardStepDef(TestContext context) {
		testContext = context;
		giftCard = testContext.getPageObjectManager().getGiftCardPage();
	}





	@Given("user is able to add the Gift Card to the cart")
	public void user_is_able_to_add_the_gift_card_to_the_cart() {



		giftCard.giftcardorder();
		giftCard.verifyGiftCardImageNavigation();
		giftCard.verifyGiftCardLearnMore();
		giftCard.giftcardorder();
		giftCard.verifyGiftCardImageNavigation();
		giftCard.verifyGiftCardLearnMore();
		giftCard.verifyCheckBalanceValidation();
	}

}






