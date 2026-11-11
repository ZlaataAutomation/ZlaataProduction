package objectRepo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import basePage.BasePage;

public   abstract class HeaderLogoObjRepo extends BasePage {
	
	
	@FindBy(xpath = "//a[@href='zlaata-india']//div[@class='landing_page_content']//span[@class='landing_page_link_btn'][normalize-space()='SHOP NOW']")
	   protected WebElement zlaataIndiaShopButton;
	
	@FindBy(xpath = "//img[@alt='zlaata Logo']")
	protected WebElement footerZlaataLogo;
	
	@FindBy(xpath = "//h2[@class='landing_page_title' and text()='BOSS LADY']")
	protected WebElement clickOnBossladyShopNowButton;
	

}
