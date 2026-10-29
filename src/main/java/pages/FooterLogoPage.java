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




//	public void zlaataIndiafooterLogoRedirection() {
//
//	    String GREEN = "\u001B[32m";
//	    String RESET = "\u001B[0m";
//
//	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//
//	    // Zlaata India page
//	    click(zlaataIndiaShopButton);
//	    Common.waitForElement(5);
//	    footerZlaataLogo.click();
//	    Common.waitForElement(3);
//
//	    String currentUrl = driver.getCurrentUrl();
//
//	    if (currentUrl.equals(FileReaderManager.getInstance().getConfigReader().getApplicationUrl())) {
//	        System.out.println(GREEN + "Zlaata India - Logo redirects to Home page: " + currentUrl + RESET);
//	    } else {
//	        System.out.println(GREEN + "Zlaata India - Logo redirection failed: " + currentUrl + RESET);
//	    }
//
//	    // New In page
//	    driver.findElement(By.xpath("//div[@class='header_nav_item new_in']")).click();
//	    Common.waitForElement(2);
//	    footerZlaataLogo.click();
//	    Common.waitForElement(3);
//
//	    currentUrl = driver.getCurrentUrl();
//
//	    if (currentUrl.equals(FileReaderManager.getInstance().getConfigReader().getApplicationUrl())) {
//	        System.out.println(GREEN + "New In - Logo redirects to Home page: " + currentUrl + RESET);
//	    } else {
//	        System.out.println(GREEN + "New In - Logo redirection failed: " + currentUrl + RESET);
//	    }
//
//	    // Sale page
//	    driver.findElement(By.xpath("//div[@class='header_nav_item sale']")).click();
//	    Common.waitForElement(2);
//	    footerZlaataLogo.click();
//	    Common.waitForElement(3);
//
//	    currentUrl = driver.getCurrentUrl();
//
//	    if (currentUrl.equals(FileReaderManager.getInstance().getConfigReader().getApplicationUrl())) {
//	        System.out.println(GREEN + "Sale - Logo redirects to Home page: " + currentUrl + RESET);
//	    } else {
//	        System.out.println(GREEN + "Sale - Logo redirection failed: " + currentUrl + RESET);
//	    }
//
//	    // Dropdown page
//	    driver.findElement(By.xpath("//div[contains(@class,'header_nav_item') and contains(@class,'has_dropdown')]")).click();
//	    Common.waitForElement(2);
//	    footerZlaataLogo.click();
//	    Common.waitForElement(3);
//
//	    currentUrl = driver.getCurrentUrl();
//
//	    if (currentUrl.equals(FileReaderManager.getInstance().getConfigReader().getApplicationUrl())) {
//	        System.out.println(GREEN + "Dropdown - Logo redirects to Home page: " + currentUrl + RESET);
//	    } else {
//	        System.out.println(GREEN + "Dropdown - Logo redirection failed: " + currentUrl + RESET);
//	    }
//	}

	


public void zlaataIndiafooterLogoRedirection() {

    String GREEN = "\u001B[32m";
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";

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
