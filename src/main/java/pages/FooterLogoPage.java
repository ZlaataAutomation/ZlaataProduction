package pages;



import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;


import manager.FileReaderManager;
import objectRepo.FooterLogoPageObjRepo;
import utils.Common;

public  final class FooterLogoPage extends FooterLogoPageObjRepo {



	String GREEN = "\u001B[32m";
	String RED = "\u001B[31m";
	String RESET = "\u001B[0m";


	public FooterLogoPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(this.driver, this);

	}

	public void zlaataIndiafooterLogoRedirection() {



		String expectedUrl = "https://www.zlaata.com/zlaata-india";

		driver.get(FileReaderManager.getInstance()
				.getConfigReader().getApplicationUrl());

		// Zlaata India page
		click(zlaataIndiaShopButton);
		Common.waitForElement(3);

		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);

		footerZlaataLogo.click();
		Common.waitForElement(3);

		String currentUrl = driver.getCurrentUrl();

		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN
					+ "Zlaata India - Logo redirects to Home page: "
					+ currentUrl + RESET);
		} else {
			System.out.println(RED
					+ "Zlaata India - Logo redirection failed: "
					+ currentUrl + RESET);
		}

		// New In page
		driver.findElement(By.xpath(
				"//div[@class='header_nav_item new_in']")).click();
		Common.waitForElement(3);

		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);

		footerZlaataLogo.click();
		Common.waitForElement(3);

		currentUrl = driver.getCurrentUrl();

		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN
					+ "New In - Logo redirects to Home page: "
					+ currentUrl + RESET);
		} else {
			System.out.println(RED
					+ "New In - Logo redirection failed: "
					+ currentUrl + RESET);
		}

		// Sale page
		driver.findElement(By.xpath(
				"//div[@class='header_nav_item sale']")).click();
		Common.waitForElement(3);

		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);

		footerZlaataLogo.click();
		Common.waitForElement(3);

		currentUrl = driver.getCurrentUrl();

		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN
					+ "Sale - Logo redirects to Home page: "
					+ currentUrl + RESET);
		} else {
			System.out.println(RED
					+ "Sale - Logo redirection failed: "
					+ currentUrl + RESET);
		}

		// Dropdown page
		driver.findElement(By.xpath(
				"//div[contains(@class,'header_nav_item') "
						+ "and contains(@class,'has_dropdown')]")).click();
		Common.waitForElement(3);

		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);

		footerZlaataLogo.click();
		Common.waitForElement(3);


		currentUrl = driver.getCurrentUrl();

		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN
					+ "Dropdown - Logo redirects to Home page: "
					+ currentUrl + RESET);
		} else {
			System.out.println(RED
					+ "Dropdown - Logo redirection failed: "
					+ currentUrl + RESET);
		}

		// About Us page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/about-us']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "About Us - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "About Us - Logo redirection failed: " + currentUrl + RESET);
		}

		// Loyalty Points page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/threads']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "Loyalty Points - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "Loyalty Points - Logo redirection failed: " + currentUrl + RESET);
		}

		// Gift Card page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/gift-card']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "Gift Card - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "Gift Card - Logo redirection failed: " + currentUrl + RESET);
		}

		// Pop Shop page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/pop-shop']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "Pop Shop - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "Pop Shop - Logo redirection failed: " + currentUrl + RESET);
		}

		// Privacy Policy page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/policy/privacy-policy']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "Privacy Policy - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "Privacy Policy - Logo redirection failed: " + currentUrl + RESET);
		}

		// Terms & Conditions page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/policy/terms-and-condition']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "Terms & Conditions - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "Terms & Conditions - Logo redirection failed: " + currentUrl + RESET);
		}

		// FAQ page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/faq']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "FAQ - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "FAQ - Logo redirection failed: " + currentUrl + RESET);
		}

		// Contact Us page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/contact-us']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "Contact Us - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "Contact Us - Logo redirection failed: " + currentUrl + RESET);
		}
	}


	public void bossLadyfooterLogoRedirection() {


		String expectedUrl = "https://www.zlaata.com/boss-lady";

		driver.get(FileReaderManager.getInstance()
				.getConfigReader().getApplicationUrl());

		// Zlaata India page
		click(clickOnBossladyShopNowButton);
		Common.waitForElement(3);

		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);

		footerZlaataLogo.click();
		Common.waitForElement(3);

		String currentUrl = driver.getCurrentUrl();

		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN
					+ "Boss Lady - Logo redirects to Home page: "
					+ currentUrl + RESET);
		} else {
			System.out.println(RED
					+ "Boss Lady - Logo redirection failed: "
					+ currentUrl + RESET);
		}

		// New In page
		driver.findElement(By.xpath(
				"//div[@class='header_nav_item new_in']")).click();
		Common.waitForElement(3);

		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);

		footerZlaataLogo.click();
		Common.waitForElement(3);

		currentUrl = driver.getCurrentUrl();

		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN
					+ "New In - Logo redirects to Home page: "
					+ currentUrl + RESET);
		} else {
			System.out.println(RED
					+ "New In - Logo redirection failed: "
					+ currentUrl + RESET);
		}

		// Sale page
		driver.findElement(By.xpath(
				"//div[@class='header_nav_item sale']")).click();
		Common.waitForElement(3);

		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);

		footerZlaataLogo.click();
		Common.waitForElement(3);

		currentUrl = driver.getCurrentUrl();

		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN
					+ "Sale - Logo redirects to Home page: "
					+ currentUrl + RESET);
		} else {
			System.out.println(RED
					+ "Sale - Logo redirection failed: "
					+ currentUrl + RESET);
		}

		// Dropdown page
		driver.findElement(By.xpath(
				"//div[contains(@class,'header_nav_item') "
						+ "and contains(@class,'has_dropdown')]")).click();
		Common.waitForElement(3);

		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);

		footerZlaataLogo.click();
		Common.waitForElement(3);


		currentUrl = driver.getCurrentUrl();

		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN
					+ "Dropdown - Logo redirects to Home page: "
					+ currentUrl + RESET);
		} else {
			System.out.println(RED
					+ "Dropdown - Logo redirection failed: "
					+ currentUrl + RESET);
		}

		// About Us page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/about-us']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "About Us - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "About Us - Logo redirection failed: " + currentUrl + RESET);
		}



		// Loyalty Points page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/threads']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "Loyalty Points - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "Loyalty Points - Logo redirection failed: " + currentUrl + RESET);
		}

		// Gift Card page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/gift-card']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "Gift Card - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "Gift Card - Logo redirection failed: " + currentUrl + RESET);
		}

		// Pop Shop page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/pop-shop']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "Pop Shop - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "Pop Shop - Logo redirection failed: " + currentUrl + RESET);
		}

		// Privacy Policy page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/policy/privacy-policy']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "Privacy Policy - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "Privacy Policy - Logo redirection failed: " + currentUrl + RESET);
		}

		// Terms & Conditions page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/policy/terms-and-condition']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "Terms & Conditions - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "Terms & Conditions - Logo redirection failed: " + currentUrl + RESET);
		}

		// FAQ page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/faq']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "FAQ - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "FAQ - Logo redirection failed: " + currentUrl + RESET);
		}

		// Contact Us page
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		driver.findElement(By.xpath("//div[@class='footer_details']//a[@href='/contact-us']")).click();
		Common.waitForElement(3);
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
		Common.waitForElement(2);
		footerZlaataLogo.click();
		Common.waitForElement(3);
		currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN + "Contact Us - Logo redirects to Home page: " + currentUrl + RESET);
		} else {
			System.out.println(RED + "Contact Us - Logo redirection failed: " + currentUrl + RESET);
		}
	}



	public void landingPage() {

		driver.get(FileReaderManager.getInstance()
				.getConfigReader().getApplicationUrl());

		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");


		String expectedUrl = "https://www.zlaata.com/";


		Common.waitForElement(2);

		footerZlaataLogo.click();
		Common.waitForElement(3);

		String currentUrl = driver.getCurrentUrl();

		if (currentUrl.equals(expectedUrl)) {
			System.out.println(GREEN
					+ " landing page  - Logo redirects to Same page : "
					+ currentUrl + RESET);
		} else {
			System.out.println(RED
					+ "landing page - Logo redirection failed: "
					+ currentUrl + RESET);
		}



	}




	public void scrollUsingJSWindow() {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, 7200);");

	}

	@Override
	public boolean verifyExactText(WebElement ele, String expectedText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public WebDriver gmail(String browserName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isAt() {
		// TODO Auto-generated method stub
		return false;
	}


}
