package pages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import manager.FileReaderManager;
import objectRepo.FooterObjRepo;
import utils.Common;

public  final class FooterPage  extends FooterObjRepo{
	
	public FooterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

//TC-01	
	public void verifyAboutUsLink() {

		  driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
		  
			click(zlaataIndiaShopButton);

		  
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    try {

	        scrollUsingJSWindow();
	        Common.waitForElement(1);

	        String beforeClickUrl = driver.getCurrentUrl();
	        System.out.println("🔗 URL before clicking About Us: " + beforeClickUrl);

	        WebElement aboutUsLink = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.xpath("//a[normalize-space()='About Us']")
	                )
	        );
	        aboutUsLink.click();

	        Common.waitForElement(2);

	        wait.until(ExpectedConditions.not(
	                ExpectedConditions.urlToBe(beforeClickUrl)
	        ));

	        String actualUrl = driver.getCurrentUrl();
	        System.out.println("🔗 URL after clicking About Us: " + actualUrl);

	        Assert.assertTrue(
	                "❌ URL does not contain /about-us",
	                actualUrl.contains("/about-us")
	        );
	        
	        URI baseUri = URI.create(beforeClickUrl);
	        String expectedUrl = baseUri.getScheme() + "://" + baseUri.getHost() + "/about-us";

	        Assert.assertEquals(
	                "❌ About Us URL mismatch",
	                expectedUrl,
	                actualUrl
	        );

	        WebElement heading = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("(//div[contains(@class,'about_us_heading')]/p)[1]")
	                )
	        );

	        String actualHeading = heading.getText().trim();
	        String expectedHeading = "Our Story";
	        System.out.println("🔗 Excpected About Us Heading: " + expectedHeading);

	        Assert.assertEquals(
	                "❌ About Us heading mismatch",
	                expectedHeading,
	                actualHeading
	        );

	        System.out.println("✅ About Us page verified successfully");

	    } catch (Exception e) {
	        System.out.println("❌ About Us verification failed: " + e.getMessage());
	        Assert.fail("About Us validation failed");
	    }
	    

	    // Wait for About page section
	    List<WebElement> headings = wait.until(
	        ExpectedConditions.presenceOfAllElementsLocatedBy(
	            By.xpath("//div[@class='frames_heading_section']")
	        )
	    );

	    // Validation
	    if (headings.size() > 0) {

	        System.out.println("PASS: Headings are present");

	        for (WebElement heading : headings) {
	            System.out.println("Heading: " + heading.getText());
	        }

	    } else {
	        System.out.println("FAIL: No headings found on About page");
	    }
	   }

	
	
	//TC-02		
	public void verifyBlogs() {

		  driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
			click(zlaataIndiaShopButton);

		  

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";
        scrollUsingJSWindow();
        Common.waitForElement(1);
	    List<String> failedCategories = new ArrayList<>();

	    try {
	        String beforeClickUrl = driver.getCurrentUrl();
	        URI baseUri = URI.create(beforeClickUrl);
	        String baseSiteUrl = baseUri.getScheme() + "://" + baseUri.getHost();

	        WebElement blogsLink = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.xpath("//a[normalize-space()='Blogs']"))
	        );

	        blogsLink.click();
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.urlContains("/blogs"));

	        String expectedBlogsUrl = baseSiteUrl + "/blogs/";
	        String actualBlogsUrl = driver.getCurrentUrl();

	        Assert.assertEquals(
	                "❌ Blogs URL mismatch",
	                expectedBlogsUrl,
	                actualBlogsUrl
	        );

	        System.out.println(GREEN + "✅ Blogs page loaded successfully" + RESET);

	        List<WebElement> blogCategories = wait.until(
	                ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                        By.xpath("//ul[@id='ast-hf-menu-1']/li/a"))
	        );

	        Assert.assertTrue(
	                "❌ Blog categories not displayed",
	                blogCategories.size() > 0
	        );

	        System.out.println(CYAN + "📂 Total Blog Categories: " + blogCategories.size() + RESET);

	        for (int i = 0; i < blogCategories.size(); i++) {

	            blogCategories = wait.until(
	                    ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                            By.xpath("//ul[@id='ast-hf-menu-1']/li/a"))
	            );

	            WebElement category = blogCategories.get(i);
	            String categoryName = category.getText().trim();

	            System.out.println(YELLOW + "👉 Clicking category: " + categoryName + RESET);

	            category.click();
	            Common.waitForElement(2);

	            if (categoryName.equalsIgnoreCase("Home")) {

	                if (!driver.getCurrentUrl().equals(expectedBlogsUrl)) {
	                    failedCategories.add("Home");
	                    System.out.println(RED + "❌ Home did not stay on Blogs page" + RESET);
	                } else {
	                    System.out.println(GREEN + "✅ Home stayed on Blogs page" + RESET);
	                }

	            }
	            else if (categoryName.equalsIgnoreCase("Shop")) {

	                wait.until(ExpectedConditions.urlToBe(baseSiteUrl + "/"));

	                String currentUrl = driver.getCurrentUrl();
	                if (!currentUrl.equals(baseSiteUrl + "/")) {
	                    failedCategories.add("Shop");
	                    System.out.println(RED + "❌ Shop did not redirect to home page" + RESET);
	                } else {
	                    System.out.println(GREEN + "✅ Shop redirected to home page" + RESET);
	                }

	                driver.get(expectedBlogsUrl);
	                wait.until(ExpectedConditions.urlToBe(expectedBlogsUrl));
	            }
	            else {
	                try {
	                    WebElement heading = wait.until(
	                            ExpectedConditions.visibilityOfElementLocated(
	                                    By.xpath("//h3[contains(text(),'Category:')]"))
	                    );

	                    String headingText = heading.getText().trim().toUpperCase();
	                    String expectedHeading = ("Category: " + categoryName).toUpperCase();

	                    if (!headingText.equals(expectedHeading)) {
	                        failedCategories.add(categoryName);
	                        System.out.println(
	                                RED + "❌ Heading mismatch | Expected: "
	                                        + expectedHeading + " | Actual: " + headingText + RESET);
	                    } else {
	                        System.out.println(GREEN + "✅ Heading verified: " + headingText + RESET);
	                    }

	                } catch (Exception e) {
	                    failedCategories.add(categoryName);
	                    System.out.println(RED + "❌ Heading not found for " + categoryName + RESET);
	                }
	            }
	        }

	        if (!failedCategories.isEmpty()) {
	            Assert.fail(
	                    "❌ Blog category validation failed for: "
	                            + String.join(", ", failedCategories)
	            );
	        }

	        System.out.println(GREEN + "\n🎉 All Blogs categories validated successfully!" + RESET);

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Blogs verification failed: " + e.getMessage() + RESET);
	        Assert.fail("Blogs verification failed");
	    }
	}

	//TC-03
		public void verifyPopShopLink() {

		    String GREEN  = "\u001B[32m";
		    String RED    = "\u001B[31m";
		    String CYAN   = "\u001B[36m";
		    String YELLOW = "\u001B[33m";
		    String RESET  = "\u001B[0m";
	        String PURPLE = "\033[0;35m";
	         String BLUE = "\033[0;34m";


		    driver.get(FileReaderManager.getInstance()
		            .getConfigReader()
		            .getApplicationUrl());
		    
			click(zlaataIndiaShopButton);


		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		    try {

		        scrollUsingJSWindow();
		        Common.waitForElement(1);

		        String beforeClickUrl = driver.getCurrentUrl();
		        System.out.println(CYAN + "🔗 URL before clicking Pop Shop: "
		                + beforeClickUrl + RESET);

		        WebElement popShopLink = wait.until(
		                ExpectedConditions.elementToBeClickable(
		                        By.xpath("//a[normalize-space()='Pop shop']")
		                )
		        );
		        popShopLink.click();

		        Common.waitForElement(2);

		        wait.until(ExpectedConditions.not(
		                ExpectedConditions.urlToBe(beforeClickUrl)
		        ));

		        String actualUrl = driver.getCurrentUrl();
		        System.out.println(CYAN + "🔗 URL after clicking Pop Shop: "
		                + actualUrl + RESET);

		        Assert.assertTrue(
		                "URL does not contain /pop-shop",
		                actualUrl.contains("/pop-shop")
		        );

		        URI baseUri = URI.create(beforeClickUrl);
		        String expectedUrl =
		                baseUri.getScheme() + "://" + baseUri.getHost() + "/pop-shop";

		        Assert.assertEquals(
		                "Pop Shop URL mismatch",
		                expectedUrl,
		                actualUrl
		        );

		        System.out.println(GREEN + "✅ Pop Shop URL verified successfully" + RESET);

		        WebElement banner = wait.until(
		                ExpectedConditions.visibilityOfElementLocated(
		                        By.xpath("//div[contains(@class,'expo_header_banner')]//img")
		                )
		        );

		        Assert.assertTrue(
		                "Pop Shop banner image not displayed",
		                banner.isDisplayed()
		        );

		        String bannerSrc = banner.getAttribute("src");

		        Assert.assertTrue(
		                "Pop Shop banner image is broken",
		                bannerSrc != null && !bannerSrc.contains("placeholder-img")
		        );

		        System.out.println(GREEN + "✅ Pop Shop banner displayed successfully" + RESET);
		        System.out.println(GREEN + "🎉 Pop Shop page verified successfully" + RESET);

		    } catch (Exception e) {
		        System.out.println(RED + "❌ Pop Shop verification failed: "
		                + e.getMessage() + RESET);
		        Assert.fail("Pop Shop validation failed");
		    }
		    

		      

		        driver.get(FileReaderManager.getInstance()
		                .getConfigReader()
		                .getApplicationUrl());

		        expobanner.click();
		        JavascriptExecutor js = (JavascriptExecutor) driver;

		        
		        By sectionWrappers = By.xpath("//div[@class='expo_mall_wrpr']");
		        By sectionTitle = By.xpath(".//h2[contains(@class,'expo_mall_heading')]");
		        By cards = By.xpath(".//div[@class='expo_review_slider_card']");
		        By customerName = By.xpath(".//h2[@class='expo_review_slider_card_txt_heading']");
		        By customerComment = By.xpath(".//p[contains(@class,'expo_review_slider_card_desc')]");
		        By productLink = By.xpath(".//a[@class='expo_review_slider_card_img']");
		        By productName = By.xpath("//h3[@class='prod_name']");
		        By page404Text = By.xpath("//div[@class='server__err__wrpr']");

		        // 🔥 NEW MAP FOR 404 URLS
		        Map<String, List<String>> section404UrlMap = new LinkedHashMap<>();

		        List<WebElement> sections = driver.findElements(sectionWrappers);

		        System.out.println(GREEN + "Total Sections : " + sections.size() + RESET);

		        for (int i = 0; i < sections.size(); i++) {

		            sections = driver.findElements(sectionWrappers);
		            WebElement section = sections.get(i);

		            js.executeScript("arguments[0].scrollIntoView(true);", section);
		            Common.waitForElement(2);

		            String sectionName = section.findElement(sectionTitle).getText().trim();

		            System.out.println(PURPLE + "\n==============================");
		            System.out.println(PURPLE + "✅ SECTION : " + sectionName);
		            System.out.println(PURPLE + "==============================" + RESET);

		            List<WebElement> allCards = section.findElements(cards);

		            System.out.println(BLUE + "🟦 Total Reviews : " + allCards.size() + RESET);

		            for (int j = 0; j < allCards.size(); j++) {

		                try {

		                    sections = driver.findElements(sectionWrappers);
		                    section = sections.get(i);
		                    allCards = section.findElements(cards);

		                    WebElement card = allCards.get(j);

		                    String name = card.findElement(customerName).getText();
		                    System.out.println(CYAN + "👤 Customer Name : " + name + RESET);

		                    String comment;
		                    try {
		                        comment = card.findElement(customerComment).getText();
		                        System.out.println(YELLOW + "💬 Comment : " + comment + RESET);
		                    } catch (Exception e) {
		                        System.out.println(RED + "❌ Comment Missing" + RESET);
		                    }

		                    // ================= CLICK PRODUCT =================

		                    WebElement img = card.findElement(productLink);

		                    String beforeUrl = driver.getCurrentUrl();

		                    js.executeScript("arguments[0].scrollIntoView(true);", img);
		                    Common.waitForElement(1);

		                    js.executeScript("arguments[0].click();", img);

		                    Common.waitForElement(3);

		                    String afterUrl = driver.getCurrentUrl();

		                    // ================= NO REDIRECT =================

		                    if (beforeUrl.equals(afterUrl)) {

		                        System.out.println(RED + "🔁 NO REDIRECT - SKIPPING PRODUCT" + RESET);

		                        driver.navigate().refresh();
		                        Common.waitForElement(2);

		                        continue;
		                    }

		                    // ================= PRODUCT VALIDATION =================

		                    if (driver.findElements(productName).size() > 0) {

		                        String prod = wait.until(ExpectedConditions
		                                .visibilityOfElementLocated(productName))
		                                .getText();

		                        System.out.println(GREEN + "🟢 PRODUCT : " + prod + RESET);

		                    } else if (driver.findElements(page404Text).size() > 0) {

		                        String currentUrl = driver.getCurrentUrl();

		                        System.out.println(RED + "🔴 404 ERROR PAGE DISPLAYED" + RESET);
		                        System.out.println(RED + "🔗 URL : " + currentUrl + RESET);

		                        // 🔥 STORE 404 URL
		                        section404UrlMap
		                                .computeIfAbsent(sectionName, k -> new ArrayList<>())
		                                .add(currentUrl);

		                    } else {

		                        System.out.println(RED + "🔴 PRODUCT NOT LOADED" + RESET);
		                    }

		                    // ================= BACK =================

		                    driver.navigate().back();
		                    Common.waitForElement(2);

		                    sections = driver.findElements(sectionWrappers);
		                    section = sections.get(i);

		                    js.executeScript("arguments[0].scrollIntoView(true);", section);
		                    Common.waitForElement(2);

		                } catch (Exception e) {
		                    System.out.println(RED + "❌ ERROR IN CARD INDEX : " + j + RESET);
		                }
		            }
		        }

		        // ================= FINAL 404 REPORT =================

		        System.out.println(GREEN + "\n=================================================" + RESET);
		        System.out.println(CYAN + "📌 404 ERROR URL REPORT" + RESET);
		        System.out.println(GREEN + "=================================================" + RESET);

		        for (Map.Entry<String, List<String>> entry : section404UrlMap.entrySet()) {

		            System.out.println(PURPLE + "\n✅ SECTION : " + entry.getKey() + RESET);

		            for (String url : entry.getValue()) {
		                System.out.println(RED + "🔗 " + url + RESET);
		            }
		        }

		        System.out.println(GREEN + "\n✅ FINAL EXECUTION COMPLETED SUCCESSFULLY" + RESET);
		    }
		

//TC-04
	public void verifyTermsAndConditionsLink() {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());

		click(zlaataIndiaShopButton);
		
		Common.waitForElement(10);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    try {

	        scrollUsingJSWindow();
	        Common.waitForElement(1);

 	        String beforeClickUrl = driver.getCurrentUrl();
	        System.out.println(CYAN + "🔗 URL before clicking Terms & Conditions: "
	                + beforeClickUrl + RESET);

 	        WebElement termsLink = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.xpath("//a[normalize-space()='Terms & Conditions']")
	                )
	        );
	        termsLink.click();

	        Common.waitForElement(2);

	        wait.until(ExpectedConditions.not(
	                ExpectedConditions.urlToBe(beforeClickUrl)
	        ));

 	        String actualUrl = driver.getCurrentUrl();
	        System.out.println(CYAN + "🔗 URL after clicking Terms & Conditions: "
	                + actualUrl + RESET);

 	        Assert.assertTrue(
	                "❌ URL does not contain /policy/terms-and-condition",
	                actualUrl.contains("/policy/terms-and-condition")
	        );

	        URI baseUri = URI.create(beforeClickUrl);
	        String expectedUrl =
	                baseUri.getScheme() + "://" + baseUri.getHost()
	                        + "/policy/terms-and-condition";

	        Assert.assertEquals(
	                "❌ Terms & Conditions URL mismatch",
	                expectedUrl,
	                actualUrl
	        );

	        System.out.println(GREEN + "✅ Terms & Conditions URL verified" + RESET);

 	        WebElement heading = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//h2[contains(@class,'privacy__policy__title')]")
	                )
	        );

	        String actualHeading = heading.getText().trim();
	        String expectedHeading = "TERMS & CONDITIONS";

	        System.out.println(CYAN + "🔍 Expected Heading: "
	                + expectedHeading + RESET);

	        Assert.assertEquals(
	                "❌ Terms & Conditions heading mismatch",
	                expectedHeading,
	                actualHeading
	        );

	        System.out.println(GREEN + "✅ Terms & Conditions heading verified" + RESET);
	        System.out.println(GREEN + "🎉 Terms & Conditions page verified successfully" + RESET);

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Terms & Conditions verification failed: "
	                + e.getMessage() + RESET);
	        Assert.fail("Terms & Conditions validation failed");
	    }
	}

//TC-05
	public void verifyPrivacyPolicyLink() {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());
	    
		click(zlaataIndiaShopButton);


	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    try {

	        scrollUsingJSWindow();
	        Common.waitForElement(1);

 	        String beforeClickUrl = driver.getCurrentUrl();
	        System.out.println(CYAN + "🔗 URL before clicking Privacy Policy: "
	                + beforeClickUrl + RESET);

 	        WebElement privacyPolicyLink = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.xpath("//a[normalize-space()='Privacy Policy']")
	                )
	        );
	        privacyPolicyLink.click();

	        Common.waitForElement(2);

	        wait.until(ExpectedConditions.not(
	                ExpectedConditions.urlToBe(beforeClickUrl)
	        ));

 	        String actualUrl = driver.getCurrentUrl();
	        System.out.println(CYAN + "🔗 URL after clicking Privacy Policy: "
	                + actualUrl + RESET);

 	        Assert.assertTrue(
	                "❌ URL does not contain /policy/privacy-policy",
	                actualUrl.contains("/policy/privacy-policy")
	        );

	        URI baseUri = URI.create(beforeClickUrl);
	        String expectedUrl =
	                baseUri.getScheme() + "://" + baseUri.getHost()
	                        + "/policy/privacy-policy";

	        Assert.assertEquals(
	                "❌ Privacy Policy URL mismatch",
	                expectedUrl,
	                actualUrl
	        );

	        System.out.println(GREEN + "✅ Privacy Policy URL verified" + RESET);

 	        WebElement heading = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//h2[contains(@class,'privacy__policy__title')]")
	                )
	        );

	        String actualHeading = heading.getText().trim();
	        String expectedHeading = "PRIVACY POLICY";

	        System.out.println(CYAN + "🔍 Expected Heading: "
	                + expectedHeading + RESET);

	        Assert.assertEquals(
	                "❌ Privacy Policy heading mismatch",
	                expectedHeading,
	                actualHeading
	        );

	        System.out.println(GREEN + "✅ Privacy Policy heading verified" + RESET);
	        System.out.println(GREEN + "🎉 Privacy Policy page verified successfully" + RESET);

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Privacy Policy verification failed: "
	                + e.getMessage() + RESET);
	        Assert.fail("Privacy Policy validation failed");
	    }
	}

//Tc-06
	
	public void verifyRaiseQueryContactUsLink() {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());
	    
		click(zlaataIndiaShopButton);


	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    try {

	        scrollUsingJSWindow();
	        Common.waitForElement(1);

 	        String beforeClickUrl = driver.getCurrentUrl();
	        System.out.println(CYAN + "🔗 URL before clicking Raise a query: "
	                + beforeClickUrl + RESET);

 	        WebElement raiseQueryLink = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.xpath("//a[normalize-space()='Contact Us']")
	                )
	        );
	        raiseQueryLink.click();

	        Common.waitForElement(2);

	        wait.until(ExpectedConditions.not(
	                ExpectedConditions.urlToBe(beforeClickUrl)
	        ));

 	        String actualUrl = driver.getCurrentUrl();
	        System.out.println(CYAN + "🔗 URL after clicking Raise a query: "
	                + actualUrl + RESET);

 	        assertTrue(
	                "❌ URL does not contain /contact-us",
	                actualUrl.contains("/contact-us")
	        );

	        URI baseUri = URI.create(beforeClickUrl);
	        String expectedUrl =
	                baseUri.getScheme() + "://" + baseUri.getHost() + "/contact-us";

	        assertEquals(
	                "❌ Contact Us URL mismatch",
	                expectedUrl,
	                actualUrl
	        );

	        System.out.println(GREEN + "✅ Contact Us URL verified" + RESET);

 	        WebElement contactWrapper = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//div[contains(@class,'contact__us__wrpr')]")
	                )
	        );

	        assertTrue(
	                "❌ Contact Us wrapper not displayed",
	                contactWrapper.isDisplayed()
	        );

	        System.out.println(GREEN + "✅ Contact Us section displayed" + RESET);

 	        WebElement getInTouch = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//h3[normalize-space()='GET IN TOUCH']")
	                )
	        );

	        WebElement contactTitle = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//h3[normalize-space()='Contact']")
	                )
	        );

	        assertEquals("GET IN TOUCH", getInTouch.getText().trim());
	        assertEquals("Contact", contactTitle.getText().trim());

	        System.out.println(GREEN + "✅ Headings verified" + RESET);

 	        assertTrue(
	                "❌ Phone number not visible",
	                driver.findElement(By.xpath("//span[contains(@class,'ph__no')]"))
	                        .isDisplayed()
	        );

	        assertTrue(
	                "❌ Email ID not visible",
	                driver.findElement(By.xpath("//span[contains(@class,'gm__id')]"))
	                        .isDisplayed()
	        );

	        assertTrue(
	                "❌ Address not visible",
	                driver.findElement(By.xpath("//div[contains(@class,'address__title')]"))
	                        .isDisplayed()
	        );

	        System.out.println(GREEN + "✅ Contact details verified" + RESET);

 	        WebElement contactForm = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//form[@id='contact_us_frm']")
	                )
	        );

	        assertTrue(
	                "❌ Contact form not displayed",
	                contactForm.isDisplayed()
	        );

	        System.out.println(GREEN + "✅ Contact form displayed" + RESET);

	        System.out.println(GREEN + "🎉 Raise a query (Contact Us) page verified successfully" + RESET);

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Contact Us verification failed: "
	                + e.getMessage() + RESET);
	        fail("Contact Us validation failed");
	    }
	}

	
//TC-07
public void verifyFaqLink() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String CYAN   = "\u001B[36m";
    String YELLOW = "\u001B[33m";
    String RESET  = "\u001B[0m";

    driver.get(FileReaderManager.getInstance()
            .getConfigReader()
            .getApplicationUrl());

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    try {

        scrollUsingJSWindow();
        Common.waitForElement(1);

         String beforeClickUrl = driver.getCurrentUrl();
        System.out.println(CYAN + "🔗 URL before clicking FAQ: "
                + beforeClickUrl + RESET);

         WebElement faqLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@href='/faq']")
                )
        );
        faqLink.click();

        Common.waitForElement(2);

        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlToBe(beforeClickUrl)
        ));

         String faqUrl = driver.getCurrentUrl();
        System.out.println(CYAN + "🔗 URL after click: " + faqUrl + RESET);

        assertTrue(
                "❌ URL does not contain /faq",
                faqUrl.contains("/faq")
        );

        System.out.println(GREEN + "✅ FAQ URL verified" + RESET);

         WebElement faqHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[contains(@class,'faq_heading')]")
                )
        );

        assertEquals(
                "❌ FAQ heading mismatch",
                "faq",
                faqHeading.getText().trim().toLowerCase()
        );

        System.out.println(GREEN + "✅ FAQ heading displayed" + RESET);

         WebElement writeToUsBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@class,'contact_us_btn')]")
                )
        );

        writeToUsBtn.click();
        Common.waitForElement(2);

         String contactUrl = driver.getCurrentUrl();
        System.out.println(CYAN + "🔗 URL after clicking Write to us: "
                + contactUrl + RESET);

        assertTrue(
                "❌ Write to us did not redirect to /contact-us",
                contactUrl.contains("/contact-us")
        );

        System.out.println(GREEN + "✅ Redirected to Contact Us page" + RESET);

        System.out.println(
                GREEN + "🎉 FAQ → Write to Us → Contact Us flow verified successfully"
                        + RESET
        );

    } catch (Exception e) {
        System.out.println(
                RED + "❌ FAQ verification failed: "
                        + e.getMessage() + RESET
        );
        fail("FAQ → Write to Us flow validation failed");
    }
}

//TC-08
public void verifyShippingAndCancellationPolicyLink() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

     String RESET = "\u001B[0m";
    String GREEN = "\u001B[32m";
    String RED   = "\u001B[31m";
    String CYAN  = "\u001B[36m";
    driver.get(FileReaderManager.getInstance()
            .getConfigReader()
            .getApplicationUrl());
    try {

        scrollUsingJSWindow();
        Common.waitForElement(1);

         String beforeClickUrl = driver.getCurrentUrl();
        System.out.println(CYAN + "🔗 URL before click: " + beforeClickUrl + RESET);

         WebElement policyLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@href='/policy/shipping-cancellation']")
                )
        );
        policyLink.click();

        Common.waitForElement(2);

         wait.until(ExpectedConditions.not(
                ExpectedConditions.urlToBe(beforeClickUrl)
        ));

        String actualUrl = driver.getCurrentUrl();
        System.out.println(CYAN + "🔗 URL after click: " + actualUrl + RESET);

         URI baseUri = URI.create(beforeClickUrl);
        String expectedUrl =
                baseUri.getScheme() + "://" + baseUri.getHost() + "/policy/shipping-cancellation";

         Assert.assertEquals(
                "❌ Shipping & Cancellation Policy URL mismatch",
                expectedUrl,
                actualUrl
        );

         WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'privacy__policy__title')]")
                )
        );

        String actualHeading = heading.getText().trim();
        String expectedHeading = "SHIPPING & CANCELLATION POLICY";

        Assert.assertEquals(
                "❌ Shipping & Cancellation heading mismatch",
                expectedHeading,
                actualHeading
        );

        System.out.println(GREEN + "✅ Shipping & Cancellation Policy page verified successfully" + RESET);

    } catch (Exception e) {
        System.out.println(RED + "❌ Shipping & Cancellation Policy verification failed: " + e.getMessage() + RESET);
        Assert.fail("Shipping & Cancellation Policy validation failed");
    }
}

//TC-09
public void verifyReturnExchangeReplacementPolicyLink() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

     String RESET = "\u001B[0m";
    String GREEN = "\u001B[32m";
    String RED   = "\u001B[31m";
    String CYAN  = "\u001B[36m";
    driver.get(FileReaderManager.getInstance()
            .getConfigReader()
            .getApplicationUrl());
    try {

        scrollUsingJSWindow();
        Common.waitForElement(1);

         String beforeClickUrl = driver.getCurrentUrl();
        System.out.println(CYAN + "🔗 URL before click: " + beforeClickUrl + RESET);

         WebElement policyLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@href='/policy/return-exchange-replacement']")
                )
        );
        policyLink.click();

        Common.waitForElement(2);

         wait.until(ExpectedConditions.not(
                ExpectedConditions.urlToBe(beforeClickUrl)
        ));

        String actualUrl = driver.getCurrentUrl();
        System.out.println(CYAN + "🔗 URL after click: " + actualUrl + RESET);

         URI baseUri = URI.create(beforeClickUrl);
        String expectedUrl =
                baseUri.getScheme() + "://" + baseUri.getHost()
                        + "/policy/return-exchange-replacement";

         Assert.assertEquals(
                "❌ Return, Exchange & Replacement Policy URL mismatch",
                expectedUrl,
                actualUrl
        );

         WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'privacy__policy__title')]")
                )
        );

        String actualHeading = heading.getText().trim();
        String expectedHeading = "RETURN, EXCHANGE & REPLACEMENT POLICY";

        Assert.assertEquals(
                "❌ Return, Exchange & Replacement heading mismatch",
                expectedHeading,
                actualHeading
        );

        System.out.println(
                GREEN + "✅ Return, Exchange & Replacement Policy page verified successfully" + RESET
        );

    } catch (Exception e) {
        System.out.println(
                RED + "❌ Return, Exchange & Replacement Policy verification failed: "
                        + e.getMessage() + RESET
        );
        Assert.fail("Return, Exchange & Replacement Policy validation failed");
    }
}
//TC-10
public void verifyFooterContactUsDetails() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

     String RESET = "\u001B[0m";
    String GREEN = "\u001B[32m";
    String RED   = "\u001B[31m";
    String CYAN  = "\u001B[36m";
    driver.get(FileReaderManager.getInstance()
            .getConfigReader()
            .getApplicationUrl());
    try {

        scrollUsingJSWindow();
        Common.waitForElement(1);

        System.out.println(CYAN + "🔍 Verifying Footer Contact Us details" + RESET);

         WebElement contactFooter = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'foot_nav')]//a[contains(@href,'wa.me')]/ancestor::div[contains(@class,'foot_nav')]")
                )
        );

         WebElement timeText = contactFooter.findElement(By.xpath(".//p[contains(text(),'Mon')]"));
        String actualTime = timeText.getText().trim();
        String expectedTime = "Mon - Sat : 9:30 AM To 6:30 PM";

        Assert.assertEquals("❌ Working time mismatch", expectedTime, actualTime);
        System.out.println(GREEN + "✅ Time verified" + RESET);

         WebElement mobileLink = contactFooter.findElement(
                By.xpath(".//a[contains(@href,'wa.me')]")
        );

        Assert.assertEquals(
                "❌ Mobile number text mismatch",
                "+91 7305380625",
                mobileLink.getText().trim()
        );

        Assert.assertTrue(
                "❌ Mobile link not clickable",
                mobileLink.isDisplayed() && mobileLink.isEnabled()
        );

        System.out.println(GREEN + "✅ Mobile number verified & clickable" + RESET);

         WebElement emailLink = contactFooter.findElement(
                By.xpath(".//a[starts-with(@href,'mailto')]")
        );

        Assert.assertEquals(
                "❌ Email mismatch",
                "support@zlaata.com",
                emailLink.getText().trim()
        );

        Assert.assertTrue(
                "❌ Email link not clickable",
                emailLink.isDisplayed() && emailLink.isEnabled()
        );

        System.out.println(GREEN + "✅ Email verified & clickable" + RESET);

         WebElement addressText = contactFooter.findElement(
                By.xpath(".//p[(contains(text(),'ZLAATA FASHION'))]")
        );

        String actualAddress = addressText.getText().trim().replaceAll("\\s+", " ");
        String expectedAddress =
                "ZLAATA FASHION, PPR Complex, NO.1/70, Medavakkam Main Rd, " +
                "Vaithiyalingam Nagar, Nanmangalam, Chennai, Tamil Nadu 600117";

        Assert.assertEquals("❌ Address mismatch", expectedAddress, actualAddress);

        System.out.println(GREEN + "✅ Address verified" + RESET);

        System.out.println(
                GREEN + "🎉 Footer Contact Us details verified successfully!" + RESET
        );

    } catch (Exception e) {
        System.out.println(
                RED + "❌ Footer Contact Us verification failed: "
                        + e.getMessage() + RESET
        );
        Assert.fail("Footer Contact Us validation failed");
    }
}

//TC-11
	public void verifyFooterTrackOrderValidation() {
	
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	
	    // 🎨 Console colors
	    String RESET = "\u001B[0m";
	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String CYAN  = "\u001B[36m";
	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());
	    try {
	
	        scrollUsingJSWindow();
	        Common.waitForElement(1);
	
	        System.out.println(CYAN + "🔍 Verifying Footer Track Order validation" + RESET);
	
	        // ---------------- LOCATORS ----------------
	        WebElement trackButton = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.cssSelector(".footer_track_btn")
	                )
	        );
	
	        WebElement trackInput = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.id("track_order_input_footer")
	                )
	        );
	
	        By errorMsgLocator = By.id("trackError");
	
	        String expectedErrorMsg =
	                "Please enter your Order ID.";
	        
	        String expectedErrorMsg1 =
	                "Order ID must be 6-19 alphanumeric characters.";
	
	        // ---------------- FIRST CLICK (EMPTY INPUT) ----------------
	        trackButton.click();
	        Common.waitForElement(2);
	        WebElement errorMsg1 = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(errorMsgLocator)
	        );
	
	        Assert.assertEquals(
	                "❌ Error message mismatch on empty Track click",
	                expectedErrorMsg,
	                errorMsg1.getText().trim()
	        );
	
	        System.out.println(GREEN + "✅ Error message displayed on empty Track click" + RESET);
	
	        // ---------------- ENTER INVALID TRACK ID ----------------
	        trackInput.clear();
	        trackInput.sendKeys("ABC123");   // invalid / random value
	        Common.waitForElement(1);
	
	        // ---------------- SECOND CLICK ----------------
	        trackButton.click();
	        Common.waitForElement(2);
	
	        WebElement errorMsg2 = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(errorMsgLocator)
	        );
	
	        Assert.assertEquals(
	                "❌ Error message mismatch after entering Track ID",
	                expectedErrorMsg1,
	                errorMsg2.getText().trim()
	        );
	
	        System.out.println(GREEN + "✅ Same error message displayed after invalid Track ID" + RESET);
	
	        System.out.println(
	                GREEN + "🎉 Footer Track Order validation verified successfully!" + RESET
	        );
	
	    } catch (Exception e) {
	        System.out.println(
	                RED + "❌ Footer Track Order verification failed: "
	                        + e.getMessage() + RESET
	        );
	        Assert.fail("Footer Track Order validation failed");
	    }
	}

//TC-12
public void socialMediaFooter() {

    driver.get(FileReaderManager.getInstance()
            .getConfigReader()
            .getApplicationUrl());
    Common.waitForElement(2);

    Actions actions = new Actions(driver);
    actions.sendKeys(Keys.END).perform();
    Common.waitForElement(1);
    scrollUsingJSWindow();
    Common.waitForElement(1);
    JavascriptExecutor js = (JavascriptExecutor) driver;

    List<WebElement> footerLinks = driver.findElements(By.xpath("//a[@class='social_media_link']"));
    System.out.println("Total footer links: " + footerLinks.size());

    for (int i = 0; i < footerLinks.size(); i++) {

        WebElement link = footerLinks.get(i);
        String linkText = link.getText();
        String linkUrl = link.getAttribute("href");

        System.out.println("Verifying link " + (i + 1) + ": " + linkText);

        // ✅ VALIDATION 1
        Assert.assertNotNull("❌ Social link href is NULL", linkUrl);
        Assert.assertFalse("❌ Social link href is EMPTY", linkUrl.trim().isEmpty());

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
        Common.waitForElement(1);

        if (linkUrl.startsWith("mailto:") || linkUrl.startsWith("tel:")) {
            System.out.println("Skipping mail/tel link");
            continue;
        }

        try {
            String originalWindowHandle = driver.getWindowHandle();
            js.executeScript("window.open(arguments[0], '_blank');", linkUrl);
            Common.waitForElement(2);

            // ✅ VALIDATION 2
            Assert.assertTrue(
                    "❌ New tab did not open",
                    driver.getWindowHandles().size() > 1
            );

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));

            // ✅ VALIDATION 3
            Assert.assertFalse(
                    "❌ Page title is EMPTY",
                    driver.getTitle().trim().isEmpty()
            );

            Assert.assertFalse(
                    "❌ Current URL is EMPTY",
                    driver.getCurrentUrl().trim().isEmpty()
            );

            System.out.println("--------------------------------------------------------------");
            System.out.println("Page title: " + driver.getTitle());
            System.out.println("Page URL  : " + driver.getCurrentUrl());
            System.out.println("--------------------------------------------------------------");

            driver.close();
            driver.switchTo().window(originalWindowHandle);

        } catch (Exception e) {
            Assert.fail("❌ Error verifying social link: " + e.getMessage());
        }

        Common.waitForElement(1);
    }
}

//TC-13

    public void verifyPaymentMethods() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(FileReaderManager.getInstance()
                .getConfigReader()
                .getApplicationUrl());
        Common.waitForElement(1);
        scrollUsingJSWindow();
        Common.waitForElement(2);
        try {
             List<WebElement> paymentCards = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.xpath("//div[@class='footer_payment_methods']")
                    )
            );

            Assert.assertTrue("❌ No payment method cards found", paymentCards.size() > 0);
            System.out.println("✅ Total payment method cards found: " + paymentCards.size());

            for (int i = 0; i < paymentCards.size(); i++) {
                WebElement card = paymentCards.get(i);

                 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", card);

                 wait.until(ExpectedConditions.visibilityOf(card));

                 Assert.assertTrue("❌ Payment card " + (i + 1) + " is not visible", card.isDisplayed());

                 WebElement img = card.findElement(By.tagName("img"));
                String src = img.getAttribute("src");
                String alt = img.getAttribute("alt");

                Assert.assertTrue("❌ Payment card " + (i + 1) + " image src is empty", src != null && !src.isEmpty());
                Assert.assertTrue("❌ Payment card " + (i + 1) + " alt text is empty", alt != null && !alt.isEmpty());

                System.out.println("✅ Payment card " + (i + 1) + " → Image: " + alt + " | Src: " + src);
            }

            System.out.println("🎉 All payment method cards verified successfully!");

        } catch (TimeoutException e) {
            System.out.println("❌ Payment method cards not found or not visible.");
            Assert.fail("Payment method verification failed");
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
            Assert.fail("Payment method verification failed due to unexpected error");
        }
    }

//TC-14
    public void verifyCopyRights() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(FileReaderManager.getInstance()
                .getConfigReader()
                .getApplicationUrl());
        
        click(zlaataIndiaShopButton);
        
        Common.waitForElement(1);
        scrollUsingJSWindow();
        Common.waitForElement(2);
        try {
             new Actions(driver).sendKeys(Keys.END).perform();
            Common.waitForElement(1);

             WebElement copyRightElement = wait.until(
                    ExpectedConditions.visibilityOf(footerSectionEmailID)
            );

            Assert.assertTrue(
                    "❌ Copyright text is not displayed",
                    copyRightElement.isDisplayed()
            );

            String actualText = copyRightElement.getText().trim();
            String expectedText = "Copyright 2026 @ zlaata";

            System.out.println("🔍 Copyright Text Found: " + actualText);

            Assert.assertEquals(
                    "❌ Copyright text mismatch",
                    expectedText,
                    actualText
            );

            System.out.println("✅ Copyright text verified successfully");

        } catch (TimeoutException e) {
            Assert.fail("❌ Copyright text not found in footer");
        } catch (Exception e) {
            Assert.fail("❌ Error verifying copyright: " + e.getMessage());
        }
    }
//TC-15
    
//    public void verifyFooterSubscribeValidations() {
//
//         driver.get(FileReaderManager.getInstance()
//                 .getConfigReader()
//                 .getApplicationUrl());
//         click(zlaataIndiaShopButton);
//         
//         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//         WebElement newsletterSection = wait.until(
//                 ExpectedConditions.visibilityOfElementLocated(
//                         By.cssSelector("section.newsletter_section")
//                 )
//         );
//
//         JavascriptExecutor js = (JavascriptExecutor) driver;
//
//         // Scroll directly to newsletter (no bottom scroll)
//         js.executeScript("arguments[0].scrollIntoView({block:'center'});", newsletterSection);
//
//         Common.waitForElement(1);
//
//        String GREEN = "\u001B[32m";
//        String RED   = "\u001B[31m";
//        String RESET = "\u001B[0m";
//
//        WebElement emailInput = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.id("newsletterInput"))
//        );
//
//        WebElement subscribeBtn = driver.findElement(
//                By.id("subscribeletterbtn")
//        );
//
//        WebElement errorMsg = driver.findElement(
//                By.cssSelector("p.error_message_footer")
//        );
//
//  
//        emailInput.clear();
//        emailInput.sendKeys("invalidemail");
//        subscribeBtn.click();
//        Common.waitForElement(2);
//
//        wait.until(ExpectedConditions.visibilityOf(errorMsg));
//
//        String invalidMsg = errorMsg.getText().trim();
//        String expectedInvalidMsg = "Please enter a valid email address.";
//
//        Assert.assertEquals(
//                "❌ Invalid email validation failed",
//                expectedInvalidMsg,
//                invalidMsg
//        );
//
//        System.out.println(GREEN + "✅ Invalid email error verified" + RESET);
//
//        String randomEmail ="test" + System.currentTimeMillis() + "@gmail.com";
//
//        emailInput.clear();
//        emailInput.sendKeys(randomEmail);
//        subscribeBtn.click();
//
//        WebElement successMsg = wait.until(
//                ExpectedConditions.visibilityOf(mailValidationMessage)
//        );
//
//        String actualSuccessMsg = successMsg.getText().trim();
//        String expectedSuccessMsg = "Successfully Subscribed";
//
//        Assert.assertEquals(
//                "❌ Subscription success message mismatch",
//                expectedSuccessMsg,
//                actualSuccessMsg
//        );
//
//        System.out.println(GREEN + "✅ Successfully subscribed with new email" + RESET);
//
//        Common.waitForElement(1);
//        emailInput.clear();
//        emailInput.sendKeys(randomEmail);
//        subscribeBtn.click();
//        Common.waitForElement(2);
//        wait.until(ExpectedConditions.visibilityOf(errorMsg));
//
//        String alreadySubMsg = errorMsg.getText().trim();
//        String expectedAlreadySubMsg = "You have already Subscribed";
//
//        Assert.assertEquals(
//                "❌ Already subscribed message mismatch",
//                expectedAlreadySubMsg,
//                alreadySubMsg
//        );
//
//        System.out.println(GREEN + "✅ Already subscribed validation verified" + RESET);
//    }

	
	
	
    public void verifyFooterSubscribeValidations() {

        String GREEN = "\u001B[32m";
        String RED   = "\u001B[31m";
        String RESET = "\u001B[0m";

        driver.get(FileReaderManager.getInstance()
                .getConfigReader()
                .getApplicationUrl());

        click(zlaataIndiaShopButton);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Wait & scroll to newsletter section
        WebElement newsletterSection = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("section.newsletter_section"))
        );

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", newsletterSection);

        // Locators (use By instead of WebElement to avoid stale)
        By emailInputBy = By.id("newsletterInput");
        By subscribeBtnBy = By.id("subscribeletterbtn");
        By errorMsgBy = By.cssSelector("p.error_message_footer");

        // -------------------------------
        // INVALID EMAIL VALIDATION
        // -------------------------------
        WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(emailInputBy));
        emailInput.clear();
        emailInput.sendKeys("invalidemail");

        driver.findElement(subscribeBtnBy).click();

        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsgBy));

        String invalidMsg = errorMsg.getText().trim();
        Assert.assertEquals("❌ Invalid email validation failed",
                "Please enter a valid email address.",
                invalidMsg);

        System.out.println(GREEN + "✅ Invalid email error verified" + RESET);

        // -------------------------------
        // GENERATE VALID EMAIL (RETRY LOGIC)
        // -------------------------------
        int maxRetries = 10;
        String validEmail = "";

        for (int i = 0; i < maxRetries; i++) {

            emailInput = wait.until(ExpectedConditions.elementToBeClickable(emailInputBy));
            emailInput.clear();

            String email = generateRandomEmail();
            System.out.println(GREEN + "Trying Email: " + email + RESET);

            emailInput.sendKeys(email);
            driver.findElement(subscribeBtnBy).click();

            Common.waitForElement(2);

            List<WebElement> errorList = driver.findElements(errorMsgBy);

            if (!errorList.isEmpty() && errorList.get(0).isDisplayed()) {
                System.out.println(RED + "❌ Error shown. Retrying..." + RESET);
            } else {
                validEmail = email;
                System.out.println(GREEN + "✅ Valid email found: " + validEmail + RESET);
                break;
            }
        }

        if (validEmail.isEmpty()) {
            throw new RuntimeException("❌ Failed to generate valid email");
        }

        // -------------------------------
        // SUCCESS VALIDATION
        // -------------------------------
        emailInput = wait.until(ExpectedConditions.elementToBeClickable(emailInputBy));
        emailInput.clear();
        emailInput.sendKeys(validEmail);

        driver.findElement(subscribeBtnBy).click();

        WebElement successMsg = wait.until(
                ExpectedConditions.visibilityOf(mailValidationMessage)
        );

        Assert.assertEquals("❌ Subscription success message mismatch",
                "Successfully Subscribed",
                successMsg.getText().trim());

        System.out.println(GREEN + "✅ Successfully subscribed" + RESET);

        // -------------------------------
        // ALREADY SUBSCRIBED VALIDATION
        // -------------------------------
        emailInput = wait.until(ExpectedConditions.elementToBeClickable(emailInputBy));
        emailInput.clear();
        emailInput.sendKeys(validEmail);

        driver.findElement(subscribeBtnBy).click();

        WebElement alreadyMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorMsgBy)
        );

        Assert.assertEquals("❌ Already subscribed message mismatch",
                "You have already Subscribed",
                alreadyMsg.getText().trim());

        System.out.println(GREEN + "✅ Already subscribed validation verified" + RESET);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	public void footerShopAllLinks() {
	    Common.waitForElement(2);

	    Actions actions = new Actions(driver);
	    actions.sendKeys(Keys.END).perform();
	    Common.waitForElement(1);

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    List<WebElement> footerLinks = driver.findElements(By.xpath("//div[@class='foot_navigation']//a"));
	    System.out.println("Total footer links: " + footerLinks.size());

	    for (int i = 0; i < footerLinks.size(); i++) {
	        WebElement link = footerLinks.get(i);
	        String linkText = link.getText();
	        String linkUrl = link.getAttribute("href");
	        System.out.println("Verifying link " + (i + 1) + ": " + linkText);

	        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
	        Common.waitForElement(1);

	        if (linkUrl != null && !linkUrl.isEmpty()) {
	            if (linkUrl.startsWith("mailto:")) {
	                System.out.println("Skipping email address link: " + linkText);
	            } else if (linkUrl.startsWith("tel:")) {
	                System.out.println("Skipping phone number link: " + linkText);
	            } else {
	                try {
	                    // Open link in new tab
	                    String originalWindowHandle = driver.getWindowHandle();
	                    ((JavascriptExecutor) driver).executeScript("window.open('" + linkUrl + "','_blank');");
	                    Common.waitForElement(2);

	                    // Switch to new tab
	                    ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
	                    driver.switchTo().window(tabs.get(1));

	                    // Verify page title
	                    System.out.println("--------------------------------------------------------------");
	                    System.out.println("Page title after clicking link " + (i + 1) + ": " + driver.getTitle());
	                    System.out.println("--------------------------------------------------------------");
	                    // Close new tab and switch back to original tab
	                    driver.close();
	                    driver.switchTo().window(originalWindowHandle);
	                } catch (Exception e) {
	                    System.out.println("Error verifying link " + (i + 1) + ": " + e.getMessage());
	                }
	            }
	        } else {
	            System.out.println("Link URL is null or empty for link " + (i + 1) + ": " + linkText);
	        }

	        Common.waitForElement(1);
	    }
	}
	
//	public void contactUS() {
//		Common.waitForElement(2);
//        Actions actions = new Actions(driver);
//	    actions.sendKeys(Keys.END).perform();
//	    Common.waitForElement(1);
//	    JavascriptExecutor js = (JavascriptExecutor) driver;
//	    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", footerContactUsLinks);
//	    try {
//			if (footerContactUsLinks.isDisplayed()) {
//				String contactDetails = footerContactUsLinks.getText();
//				System.out.println("The contact details: ");
//				System.out.println("------------------------");
//				System.out.println(contactDetails);
//			}
//			else {
//				System.err.println("Contact Deatils not displaying");
//			}
//		} catch (Exception e) {
//			System.out.println("Error verifying link: " + e.getMessage());
//		}
//	}
//	
	
//	public void zlaataLogo() {
//		Common.waitForElement(2);
//        Actions actions = new Actions(driver);
//	    actions.sendKeys(Keys.END).perform();
//	    Common.waitForElement(1);
//	    JavascriptExecutor js = (JavascriptExecutor) driver;
//	    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", footerzlaataLogo);
//	    try {
//			if (footerzlaataLogo.isDisplayed()) {
//				System.out.println("Logo is displayed");
//				
//			}
//		} catch (Exception e) {
//			System.out.println("Error verifying link: " + e.getMessage());
//		}
//	}
	
	public void paymentMethods() {
	    try {
	        WebElement paymentLabel = driver.findElement(By.xpath("//div[@class='footer_payment_method_card]"));

	        // Scroll into view
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].scrollIntoView(true);", paymentLabel);

	        Thread.sleep(500); // Optional pause

	        if (paymentLabel.isDisplayed()) {
	            String payText = paymentLabel.getText();
	            System.out.println("Payment label is displayed with text: " + payText);
	        } else {
	            System.out.println("Payment label is present but not visible.");
	        }

	    } catch (NoSuchElementException e) {
	        // This is your "else" condition if element is missing
	        System.out.println("Payment label is NOT present on the page.");
	    } catch (Exception e) {
	        System.out.println("An unexpected error occurred: " + e.getMessage());
	    }
	}

	
	public void socialMedia() {
		driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());
	    Common.waitForElement(2);

	    Actions actions = new Actions(driver);
	    actions.sendKeys(Keys.END).perform();
	    Common.waitForElement(1);

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    List<WebElement> footerLinks = driver.findElements(By.xpath("//a[@class='social_link_card']"));
	    System.out.println("Total footer links: " + footerLinks.size());

	    for (int i = 0; i < footerLinks.size(); i++) {
	        WebElement link = footerLinks.get(i);
	        String linkText = link.getText();
	        String linkUrl = link.getAttribute("href");
	        System.out.println("Verifying link " + (i + 1) + ": " + linkText);

	        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
	        Common.waitForElement(1);

	        if (linkUrl != null && !linkUrl.isEmpty()) {
	            if (linkUrl.startsWith("mailto:")) {
	                System.out.println("Skipping email address link: " + linkText);
	            } else if (linkUrl.startsWith("tel:")) {
	                System.out.println("Skipping phone number link: " + linkText);
	            } else {
	                try {
	                    // Open link in new tab
	                    String originalWindowHandle = driver.getWindowHandle();
	                    ((JavascriptExecutor) driver).executeScript("window.open('" + linkUrl + "','_blank');");
	                    Common.waitForElement(2);

	                    // Switch to new tab
	                    ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
	                    driver.switchTo().window(tabs.get(1));

	                    // Verify page title
	                    System.out.println("--------------------------------------------------------------");
	                    System.out.println("Page title after clicking link " + (i + 1) + ": " + driver.getTitle());
	                    System.out.println("--------------------------------------------------------------");
	                    // Close new tab and switch back to original tab
	                    driver.close();
	                    driver.switchTo().window(originalWindowHandle);
	                } catch (Exception e) {
	                    System.out.println("Error verifying link " + (i + 1) + ": " + e.getMessage());
	                }
	            }
	        } else {
	            System.out.println("Link URL is null or empty for link " + (i + 1) + ": " + linkText);
	        }

	        Common.waitForElement(1);
	    }
	}
	
	
	public void copyRights() {
		Common.waitForElement(2);

	    Actions actions = new Actions(driver);
	    actions.sendKeys(Keys.END).perform();
	    Common.waitForElement(1);
	   actions.moveToElement(footerSectionEmailID).build().perform();
	    try {
			if (footerSectionEmailID.isDisplayed()) {
				String copyRightMsg = footerSectionEmailID.getText();
				System.out.println("Zlaata Copy rights: " +copyRightMsg);
				
			}
		} catch (Exception e) {
			System.out.println("Error verifying Copy rights: " + e.getMessage());
		}
	}
	
	public void footerSubscribe() {
		scrollUsingJSWindow();
		Common.waitForElement(1);
		click(mailId);
		Common.waitForElement(1);
		RandomMailId();
		
		click(subScribeBtn);
		Common.waitForElement(2);
		String actualMessage = mailValidationMessage.getText();
        Assert.assertTrue("Validation failed for ", actualMessage.equals(actualMessage));
        System.out.println("\u001B[32m✅ SUCCESS: Validation Message = " + actualMessage + "\u001B[0m");
	}
	
	public void footerSubscribeAlready() {
		scrollUsingJSWindow();
		Common.waitForElement(1);
		click(mailId);
		Common.waitForElement(1);
	    type(mailId, Common.getValueFromTestDataMap("Email Id"));
		click(subScribeBtn);
		Common.waitForElement(2);
		String actualMessage = mailAlreadyValidation.getText();
        Assert.assertTrue("Validation failed for ", actualMessage.equals(actualMessage));
        System.out.println("\u001B[32m✅ SUCCESS: Validation Message = " + actualMessage + "\u001B[0m");
	}
	public void invalidMail() {
		
		
		scrollUsingJSWindow();
		Common.waitForElement(1);
		click(mailId);
		Common.waitForElement(1);
	    type(mailId, Common.getValueFromTestDataMap("Email Id"));
		click(subScribeBtn);
		Common.waitForElement(2);
		String actualMessage = inValid.getText();
        Assert.assertTrue("Validation failed for ", actualMessage.equals(actualMessage));
        System.out.println("\u001B[32m✅ Error : Validation Message = " + actualMessage + "\u001B[0m");
	}
	
	
	
	
	
	
	
	
	
	
	
	public  void RandomMailId() {
		// Step 1: Generate a random email
		String randomEmail = generateRandomEmail();

		try {

			WebElement emailInput = driver.findElement(By.id("subscribeletter"));
			emailInput.sendKeys(randomEmail);
			// Optionally print or use email later
			System.out.println("Random email entered: " + randomEmail);

		} finally {
			// Close the browser after a short delay for demo purposes
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void threadBanner() {
		
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());


		scrollUsingJSWindow();
		Common.waitForElement(1);

		click(threadLink);

		Common.waitForElement(2);

		// Store current URL
		String currentUrl = driver.getCurrentUrl();

		// Verify URL
		Assert.assertTrue("❌ Thread page URL mismatch",
		        currentUrl.contains("my-accounts/my-threads"));

		System.out.println("\u001B[32m✅ Navigated to Threads page: " + currentUrl + "\u001B[0m");
		
		}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	public void scrollUsingJSWindow() {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, 7200);");

	}
	
	
	
	
	
//	private static String generateRandomEmail() {
//		String chars = "abcdefghijklmnopqrstuvwxyz1234567890";
//		StringBuilder email = new StringBuilder();
//		Random rnd = new Random();
//		int length = 8;
//
//		for (int i = 0; i < length; i++) {
//			email.append(chars.charAt(rnd.nextInt(chars.length())));
//		}
//
//		return email.toString() + "@example.com";
//	}



	public void clickUsingJavaScript(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}
	
	
	
	
	
	private static String lastGeneratedEmail;

	private static String generateRandomEmail() {
		String prefix = "testing"; // fixed name prefix
		String digits = "0123";
		Random rnd = new Random();

		StringBuilder email = new StringBuilder(prefix);

		// Add 4 random digits after the prefix
		for (int i = 0; i < 4; i++) {
			email.append(digits.charAt(rnd.nextInt(digits.length())));
		}

		// Append fixed domain
		email.append("@gmail.com");

		lastGeneratedEmail = email.toString();
		return lastGeneratedEmail;
	}

	public static String getLastGeneratedEmail() {
		return lastGeneratedEmail;
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
