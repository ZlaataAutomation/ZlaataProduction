package objectRepo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import basePage.BasePage;

public abstract class FooterLogoPageObjRepo  extends  BasePage {
	
	@FindBy(xpath = "//a[@href='zlaata-india']//div[@class='landing_page_content']//span[@class='landing_page_link_btn'][normalize-space()='SHOP NOW']")
	   protected WebElement zlaataIndiaShopButton;
	
	@FindBy(xpath = "//a[@class='footer_zlaata_logo']")
	protected WebElement footerZlaataLogo;
	

}
