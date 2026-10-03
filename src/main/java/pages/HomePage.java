package pages;


import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import manager.FileReaderManager;
import objectRepo.HomePageObjRepo;
import utils.Common;

public final class HomePage extends HomePageObjRepo {


	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);

	}
	public void homeLaunch() {
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//				type(accessCode, FileReaderManager.getInstance().getJsonReader().getValueFromJson("Access"));
//				click(submit);
//		handleAccessCodeIfPresentFast();
//			popup();
		click(zlaataIndiaShopButton);
		
		Common.waitForElement(10);

Common.waitForElement(5);
	}
	public void popup() {
	    List<WebElement> popUps = driver.findElements(
	            By.xpath("//div[contains(@class,'chrismas_closebtn')]")
	    );

	    if (!popUps.isEmpty()) {
	    	 ((JavascriptExecutor) driver)
             .executeScript("arguments[0].click();", popUps.get(0));
	    }
	}

	public void handleAccessCodeIfPresentFast() {

        try {
            List<WebElement> accessCodeInput = driver.findElements(
                    By.xpath("//input[@id='security_code']")
            );

            // 🔹 Instant check – if not present, skip
            if (accessCodeInput.isEmpty()) {
                return;
            }

            WebElement input = accessCodeInput.get(0);

            if (input.isDisplayed()) {

                String accessCode = FileReaderManager.getInstance()
                        .getJsonReader()
                        .getValueFromJson("Access");

                // Type access code
                input.clear();
                input.sendKeys(accessCode);

                // Click submit
                WebElement submitBtn = driver.findElement(
                        By.xpath("//form[contains(@action,'accessCheckProcess')]//button")
                );

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", submitBtn);
                
                System.out.println("⚡ Access code entered (fast path)");
            }

        } catch (Exception e) {
            // swallow – fast skip mode
        }
    }
	public void homeLaunchAfterSaved() {

        try {
            
            profile.click();

            WebElement userName = driver.findElement(
                    By.xpath("//div[contains(@class,'account_tabs_user_content')]//h2")
            );

            if (userName.isDisplayed()) {
                System.out.println("\u001B[32m✅ User already logged in: " 
                    + userName.getText() + "\u001B[0m");
                return; 
            }

        } catch (Exception e) {

           
            System.out.println("\u001B[33m⚠ User not logged in. Proceeding with login flow...\u001B[0m");

            driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

            handleAccessCodeIfPresentFast();
            popup();
            LoginPage login=new LoginPage(driver);
            login.userLogin();
        }
    }
	public void scrollToElementUsingJSE(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", ele);
	}
	public void scrollUsingJSWindow() {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, 1800);");

	}

	public void bannerClick() {
//		homeLaunch();
		Common.waitForElement(5);
		click(banners);
		//		WebElement bannerRedirection = driver.findElement(By.xpath("//h3[@class='prod_list_topic']"));
		//		if (bannerRedirection.isDisplayed()) {
		//			String pageHeading = bannerRedirection.getText();
		//			System.out.println("Banner Redirected sucessfull: " +pageHeading);
		//			Assert.assertTrue(verifyDisplayed(bannerRedirection));
		//
		//		}
	}
	public void forAndbackButton() {
		homeLaunch();
		try {
			Actions action = new Actions(driver);
			WebElement bannerNxtBtn = driver.findElement(By.xpath("//*[@class='carousel_banner_prev_btn']"));
			WebElement bannerPrevBtn = driver.findElement(By.xpath("//*[@class='carousel_banner_next_btn']"));

			int maxClicks = 5; // Define the maximum number of clicks

			// Click next button
			for (int i = 0; i < maxClicks; i++) {
				if (bannerNxtBtn.isDisplayed()) {
					action.moveToElement(bannerNxtBtn).click().build().perform();
					Common.waitForElement(1); 
				} else {
					break;
				}
			}

			// Click previous button
			for (int i = 0; i < maxClicks; i++) {
				if (bannerPrevBtn.isDisplayed()) {
					action.moveToElement(bannerPrevBtn).click().build().perform();
					Common.waitForElement(1); 
				} else {
					break;
				}
			}

		} 
		catch (Exception e) {
			System.out.println("Caught an exception: " + e.getMessage());
			NoSuchElementException e1 = new NoSuchElementException("A NoSuchElementException exception occurred");
			e1.initCause(e);
			throw e1;
		}
	}

	public void verifyPauseButton() {
		homeLaunch();
		try {
			WebElement pauseButton = driver.findElement(By.xpath("//div[@class='carousel_cta']"));
			WebElement playButton = driver.findElement(By.xpath("//*[@class='carousel_pause']"));

			// Wait 20 seconds to allow the banner to start moving
			Common.waitForElement(20);
			System.out.println("Banner is moving initially.");

			// Click the pause button and wait to verify if it stops
			pauseButton.click();
			Common.waitForElement(5);
			System.out.println("Banner stopped after pause.");

			// Click the play button and wait to verify if it starts again
			playButton.click();
			Common.waitForElement(15);
			System.out.println("Banner started moving after play.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void topSelling() throws TimeoutException {
		homeLaunch();
		Actions action = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1000);");
			Common.waitForElement(2);
			// Now wait for and click the forward button
			WebElement forwardButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@class='swiper-button-next top_selling_swiper_next']")));
			action.moveToElement(forwardButton).click().perform();
			System.out.println("Top Selling forward button clicked.");

			// Now wait for and click the backward button
			WebElement backwardButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@class='swiper-button-prev top_selling_swiper_prev']")));
			action.moveToElement(backwardButton).click().perform();
			System.out.println("Top Selling backward button clicked.");

		} catch (Exception e) {
			System.out.println("Caught an exception: " + e.getMessage());
			NoSuchElementException e1 = new NoSuchElementException("A NoSuchElementException exception occurred");
			e1.initCause(e);
			throw e1;
		}
	}

//	public void topSellingProduct() {
//		homeLaunch();
//
//		try {
//			((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1000);");
//			Common.waitForElement(2);
//
//			List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//div[@class='product_img']"));
//			if (clickRandomProduct.isEmpty()) {
//				System.out.println("No products found in the Top Selling section.");
//				return;
//			}
//
//			String productName = driver.findElement(By.xpath(".//div[@class='products_contents']")).getText().trim();
//			System.out.println("Top selling product Content: " + productName);
//			WebElement randomProduct = clickRandomProduct.get(0);
//			Collections.shuffle(clickRandomProduct);
//			Actions actions = new Actions(driver);
//			actions.moveToElement(randomProduct).click().build().perform();
//
//			Common.waitForElement(2);
//			WebElement productDetailsPrdContent = driver.findElement(By.xpath("//div[@class='prod_main_details_head']"));
//			String productDetailContent = productDetailsPrdContent.getText().trim();
//			System.out.println("Product details product content: " + productDetailContent);
//
//			// Check if productDetailContent contains productName (partial match)
//			if (productDetailContent.contains(productName)) {
//				System.out.println("Both Contents are matching.");
//			} else {
//				System.out.println("Both Contents are not matching.");
//			}
//
//		} catch (Exception e) {
//			System.out.println("Exception in topSellingProduct: " + e.getMessage());
//			throw e;
//		}
//	}
//

//	public void showMore() {
//		homeLaunch();
//		Actions action = new Actions(driver);
//		Common.waitForElement(2);
//		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 2300);");
//		Common.waitForElement(2);
//		action.moveToElement(showMore).click().build().perform();
//		WebElement showMorePage = driver.findElement(By.xpath("//h3[@class='prod_list_topic']"));
//		if (showMorePage.isDisplayed()) {
//			String pageHeading = showMorePage.getText();
//			System.out.println("Show More button Redirected sucessfull: " +pageHeading);
//			Assert.assertTrue(verifyDisplayed(showMorePage));
//
//
//		}
//
//	}
	
	public void newArrivalArrows() {
	    homeLaunch(); // Open home page
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // Locators
	    By heading = By.xpath("//h2[text()='New Arrivals']");
	    By forwardBtn = By.xpath("//h2[text()='New Arrivals']/following::div[contains(@class,'swiper-button-next')][1]");
	    By backwardBtn = By.xpath("//h2[text()='New Arrivals']/following::div[contains(@class,'swiper-button-prev')][1]");

	    try {
	        // Scroll the New Arrivals heading to the middle of the viewport
	        WebElement headingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", headingElement);
	        Common.waitForElement(2); // Small wait for lazy-loaded content

	        // Click the forward arrow
	        WebElement forwardButton = wait.until(ExpectedConditions.visibilityOfElementLocated(forwardBtn));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", forwardButton);
	        System.out.println("✅ New Arrival forward button clicked.");
	        Common.waitForElement(2);

	        // Click the backward arrow
	        WebElement backwardButton = wait.until(ExpectedConditions.visibilityOfElementLocated(backwardBtn));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backwardButton);
	        System.out.println("✅ New Arrival backward button clicked.");
	        Common.waitForElement(2);

	    } catch (Exception e) {
	        System.out.println("❌ Caught an exception: " + e.getMessage());
	        NoSuchElementException e1 = new NoSuchElementException("A NoSuchElementException occurred in New Arrivals arrows");
	        e1.initCause(e);
	        throw e1;
	    }
	}

	public void newArivalProductImg() {
		homeLaunch();
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 2700);");
		try {

			List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//div[@class='new_arrival_card_list ']")); 
			if (clickRandomProduct.isEmpty()) {
				System.out.println("No products found in New arrivals section.");
				return;
			}

			String productName = driver.findElement(By.xpath(".//div[@class='products_contents']")).getText().trim();
			System.out.println("New arrivals product Content: " + productName);
			WebElement randomProduct = clickRandomProduct.get(0);
			Collections.shuffle(clickRandomProduct);
			Actions actions = new Actions(driver);
			actions.moveToElement(randomProduct).click().build().perform();

			Common.waitForElement(2);
			WebElement productDetailsPrdContent = driver.findElement(By.xpath("//div[@class='prod_main_details_head']"));
			String productDetailContent = productDetailsPrdContent.getText().trim();
			System.out.println("Product details product content: " + productDetailContent);

			// Check if productDetailContent contains productName (partial match)
			if (productDetailContent.contains(productName)) {
				System.out.println("Both Contents are matching.");
			} else {
				System.out.println("Both Contents are not matching.");
			}

		} catch (Exception e) {
			System.out.println("Exception in New arrivals Product: " + e.getMessage());
			throw e;
		}
	}



	public void quickView() {
		homeLaunch();
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 2500);");
		try {

			List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//div[@class='products_quick_view_box Cls_quickview_btn']"));
			if (clickRandomProduct.isEmpty()) {
				System.out.println("Quick view Not found in New arrivals section.");
				return;
			}

			String productName = driver.findElement(By.xpath(".//div[@class='products_contents']")).getText().trim();
			System.out.println("New arrivals product Content: " + productName);
			WebElement randomProduct = clickRandomProduct.get(0);
			Collections.shuffle(clickRandomProduct);
			Actions actions = new Actions(driver);
			actions.moveToElement(randomProduct).click().build().perform();

			Common.waitForElement(3);
			WebElement quickViewPrdContent = driver.findElement(By.xpath("//div[@class='qv_prod_details_cont']"));
			String quickViewContent = quickViewPrdContent.getText().trim();
			System.out.println("Quick view product content: " + quickViewContent);

			// Check if productDetailContent contains productName (partial match)
			if (quickViewContent.contains(productName)) {
				System.out.println("Both Contents are matching.");
			} else {
				System.out.println("Both Contents are not matching.");
			}

		} catch (Exception e) {
			System.out.println("Exception in topSellingProduct: " + e.getMessage());
			throw e;
		}
	}

	public void inspiredBy() {	
		homeLaunch();
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 2600);");
		List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//div[@class='inspired_slide_img']"));
		Collections.shuffle(clickRandomProduct);
		Common.waitForElement(15);


		if (!clickRandomProduct.isEmpty()) {
			WebElement randomProduct = clickRandomProduct.get(0);
			Actions actions = new Actions(driver);
			actions.moveToElement(randomProduct).click().build().perform();
			WebElement inspiredByRedirection = driver.findElement(By.xpath("//div[@class='prod_main_details_head']"));
			if (inspiredByRedirection.isDisplayed()) {
				String details = inspiredByRedirection.getText();
				System.out.println("Inspired by Redirected sucessfull: " +details);
				Assert.assertTrue(verifyDisplayed(inspiredByRedirection));

			}
		}
	}

//	public void happy() {
//
//		homeLaunch();
//		Actions action = new Actions(driver);
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//
//		try {
//			((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 6200);");
//			Common.waitForElement(2);
//			// Now wait for and click the forward button
//			WebElement forwardButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
//					By.xpath("//div[@class='swiper-button-next testimonial_swiper_next']")));
//			action.moveToElement(forwardButton).click().perform();
//			System.out.println("Happy customer forward button clicked.");
//
//			// Now wait for and click the backward button
//			WebElement backwardButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
//					By.xpath("//div[@class='swiper-button-prev testimonial_swiper_prev']")));
//			action.moveToElement(backwardButton).click().perform();
//			System.out.println("Happy customer backward button clicked.");
//
//		} catch (Exception e) {
//			System.out.println("Caught an exception: " + e.getMessage());
//			NoSuchElementException e1 = new NoSuchElementException("A NoSuchElementException exception occurred");
//			e1.initCause(e);
//			throw e1;
//		}
//	}
//
//	public void happyQuickView() {
//		homeLaunch();
//		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 6200);");
//		try {
//
//			List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//a[@class='testimonial_cards_quick_view']"));
//			if (clickRandomProduct.isEmpty()) {
//				System.out.println("No Quick view found in Happy customer section.");
//				return;
//			}
//
//			String customerContent = driver.findElement(By.xpath(".//p[@class='testimonial_cards_disc']")).getText().trim().toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
//			System.out.println("Customer Content: " + customerContent);
//			WebElement randomProduct = clickRandomProduct.get(0);
//			Collections.shuffle(clickRandomProduct);
//			Actions actions = new Actions(driver);
//			actions.moveToElement(randomProduct).click().build().perform();
//
//			Common.waitForElement(2);
//			WebElement reviewContent = driver.findElement(By.xpath("//div[@class='customer_review_card']//p"));
//			String reviewDeatilsContent = reviewContent.getText().trim().toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
//			System.out.println("Review details content: " + reviewDeatilsContent);
//
//			// Check if productDetailContent contains productName (partial match)
//			if (reviewDeatilsContent.contains(customerContent)) {
//				System.out.println("Both Contents are matching.");
//			} else {
//				System.out.println("Both Contents are not matching.");
//			}
//
//		} catch (Exception e) {
//			System.out.println("Exception in Happy customer section: " + e.getMessage());
//			throw e;
//		}
//	}

	public void feedBack() {
		Actions action = new Actions(driver);
		homeLaunch();
		Common.waitForElement(2);
		click(feedBack);
		click(feedletsDoIT);
		Common.waitForElement(1);
		RandomMailId();
		action.moveToElement(continueFeed).click().build().perform();
		clickUsingJavaScript(feedCollectionYES);
		clickUsingJavaScript(feedNextButton);
		clickUsingJavaScript(feedSearchingYES);
		clickUsingJavaScript(feedNextButton);
		clickUsingJavaScript(feedStruggle2);
		clickUsingJavaScript(feedNextButton);
		clickUsingJavaScript(feedStarButton);
		clickUsingJavaScript(feedFinalContinue);
		try {
			WebElement feedBackCopy = driver.findElement(By.id("copyButton"));

			if (feedBackCopy.isDisplayed()) {
				clickUsingJavaScript(feedBackCopy);
				System.out.println("Feed Back copy button is clicked.");

			}

		} 
		catch (Exception e) {
			System.err.println(e);
		}

		String feedBackCoupon = feedBackformText.getText();
		System.out.println("Feed Back form submitted: " + feedBackCoupon);

	}

	public void backtoTop() {
		homeLaunch();
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 6200);");
		Common.waitForElement(1);
		WebElement backTop = driver.findElement(By.xpath("//div[@class='bottom_icons_box']"));
		try {
			if (backTop.isDisplayed()) {
				click(backTop);
				System.out.println("The Back to top button is clicked");
			}
		} catch (Exception e) {
			System.err.println(e);
		}




	}
	public void whatsApp() {

	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String CYAN  = "\u001B[36m";
	    String RESET = "\u001B[0m";

	    try {

	        homeLaunch();

	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        js.executeScript("window.scrollBy(0, 6200);");
	        Common.waitForElement(1);

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	        // Store current window
	        String parentWindow = driver.getWindowHandle();

	        // Click WhatsApp
	        WebElement whatsApp = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.id("whatsappIcon")));

	        whatsApp.click();

	        System.out.println(
	                GREEN + "✅ WhatsApp button clicked successfully."
	                        + RESET);

	        Common.waitForElement(3);

	        // Check for new window
	        Set<String> windows = driver.getWindowHandles();

	        System.out.println(
	                CYAN + "Total Windows : "
	                        + windows.size()
	                        + RESET);

	        // Switch to new window
	        for (String window : windows) {

	            if (!window.equals(parentWindow)) {

	                driver.switchTo().window(window);

	                System.out.println(
	                        CYAN + "🔄 Switched to WhatsApp window."
	                                + RESET);

	                break;
	            }
	        }

	        Common.waitForElement(3);

	        // Verify WhatsApp number/text
	        String pageText = driver.findElement(By.tagName("body"))
	                .getText();

	        if (pageText.contains("7305380625")) {

	            System.out.println(
	                    GREEN
	                            + "✅ WhatsApp number is displayed : 7305380625"
	                            + RESET);

	        } else {

	            System.out.println(
	                    RED
	                            + "❌ WhatsApp number is NOT displayed."
	                            + RESET);
	        }

	        // Close WhatsApp window
	        if (!driver.getWindowHandle().equals(parentWindow)) {
	            driver.close();
	            driver.switchTo().window(parentWindow);
	        }

	    } catch (Exception e) {

	        System.out.println(
	                RED
	                        + "❌ WhatsApp verification failed : "
	                        + e.getMessage()
	                        + RESET);
	    }
	}
	//	public void featureOn() {
	//
	//		homeLaunch();
	//		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 7000);");
	//		
	//		try {
	//
	//			List<WebElement> clickRandomFeatureOn = driver.findElements(By.xpath("//div[@class='featured__slider__main']//div[@class='swiper-slide']"));
	//			if (clickRandomFeatureOn.isEmpty()) {
	//				System.out.println("No Feature on section found. ");
	//				return;
	//			}
	//
	//			WebElement randomFeature = clickRandomFeatureOn.get(0);
	//			Collections.shuffle(clickRandomFeatureOn);
	//			clickUsingJavaScript(randomFeature);
	//			Common.waitForElement(5);
	//			WebElement FeatureOnRedirection = driver.findElement(By.xpath("//h3[@class='prod_list_topic']"));
	//			if (FeatureOnRedirection.isDisplayed()) {
	//				String pageHeading = FeatureOnRedirection.getText();
	//				System.out.println("Feature On Redirected sucessfull: " + pageHeading);
	//				Assert.assertTrue(verifyDisplayed(FeatureOnRedirection));
	// }
	//			else {
	//				Common.waitForElement(5);
	//				WebElement feature = driver.findElement(By.xpath("//h2[contains(text(),'Feature On')]"));
	//				String featureOn =feature.getText();
	//				System.out.println("The feature on redirecting on the same page " + featureOn);
	//				Assert.assertTrue(verifyDisplayed(feature));
	//			}
	//
	//
	//		} catch (Exception e) {
	//			System.err.println("Exception in Feature On Section : " + e.getMessage());
	//			throw e;
	//		}
	//
	//
	//	}
//	public void featureOn() {
//
//		homeLaunch();
//		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 7000);");
//
//		try {
//
//			List<WebElement> clickRandomFeatureOn = driver.findElements(By.xpath("//div[@class='featured__slider__main']//div[@class='swiper-slide']"));
//			if (clickRandomFeatureOn.isEmpty()) {
//				System.out.println("No Feature on section found. ");
//				return;
//			}
//
//			Collections.shuffle(clickRandomFeatureOn);
//			WebElement randomFeature = clickRandomFeatureOn.get(0);
//			clickUsingJavaScript(randomFeature);
//			Common.waitForElement(5);
//
//			List<WebElement> featureOnRedirection = driver.findElements(By.xpath("//h3[@class='prod_list_topic']"));
//			if (!featureOnRedirection.isEmpty() && featureOnRedirection.get(0).isDisplayed()) {
//				String pageHeading = featureOnRedirection.get(0).getText();
//				System.out.println("Feature On Redirected successfully: " + pageHeading);
//				Assert.assertTrue(verifyDisplayed(featureOnRedirection.get(0)));
//			}
//			else {
//				List<WebElement> feature = driver.findElements(By.xpath("//h2[contains(text(),'Feature On')]"));
//				if (!feature.isEmpty() && feature.get(0).isDisplayed()) {
//					String featureOn = feature.get(0).getText();
//					System.out.println("The feature on redirecting on the same page: " + featureOn);
//					Assert.assertTrue(verifyDisplayed(feature.get(0)));
//				} else {
//					System.out.println("Neither of the expected pages were found.");
//				}
//
//				WebElement featureOnNxtBtn = driver.findElement(By.xpath("//*[@class='swiper-button-next featured_next_btn']"));
//				if (featureOnNxtBtn.isDisplayed()) {
//					clickUsingJavaScript(featureOnNxtBtn);
//					System.out.println("Feature on Next Button clicked");	
//				}
//				WebElement featureOnBackBtn = driver.findElement(By.xpath("//*[@class='swiper-button-prev featured_prev_btn']"));
//				if (featureOnBackBtn.isDisplayed()) {
//					clickUsingJavaScript(featureOnBackBtn);
//					System.out.println("Feature on Back Button clicked");
//
//				}
//			}
//		}
//
//		catch (Exception e) {
//			System.out.println("Exception in featureOn: " + e.getMessage());
//			throw e;
//		}
//	}

	public void featureOn() {

	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());

	    click(zlaataIndiaShopButton);
	    featureOnSection();
	    click(bosslady);
	   
	    
	    featureOnSection();
	    
	}

	public void allsectionHomePage() {
		homeLaunch();
		try {

			List<WebElement> elements = driver.findElements(By.xpath("//h2"));
			for (WebElement element : elements) {
				System.out.println(element.getText());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		try {

			List<WebElement> elements = driver.findElements(By.xpath("//h3"));
			for (WebElement element : elements) {
				System.out.println(element.getText());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}



	}



	public  void RandomMailId() {
		// Step 1: Generate a random email
		String randomEmail = generateRandomEmail();

		try {

			WebElement emailInput = driver.findElement(By.id("feedback_email"));
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

	// Utility method to generate random email
	private static String generateRandomEmail() {
		String chars = "abcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder email = new StringBuilder();
		Random rnd = new Random();
		int length = 8;

		for (int i = 0; i < length; i++) {
			email.append(chars.charAt(rnd.nextInt(chars.length())));
		}

		return email.toString() + "@example.com";
	}



	public void clickUsingJavaScript(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}



	public void verifyUrlAndLogo() {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String RESET  = "\u001B[0m";
	    String BLUE = "\u001B[34m";

	    Common.waitForElement(10);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//	    WebElement homeMenu = wait.until(ExpectedConditions.elementToBeClickable(
//	            By.xpath("//a[normalize-space()='Home']") 
//	    ));
//	    homeMenu.click();
//	    String expectedUrl = "https://www.zlaata.com/";
//	    String actualUrl = driver.getCurrentUrl();
//
//	    assertEquals(
//	            "❌ URL mismatch! Expected: " + expectedUrl + " but got: " + actualUrl,
//	            expectedUrl,
//	            actualUrl
//	    );
//	    System.out.println(
//	            GREEN + "✅ URL verified successfully | Expected: "
//	            + expectedUrl + " | Actual: " + actualUrl + RESET
//	    );
	    
	    // Open the URL
        driver.get("https://www.zlaata.com/");

      
Common.waitForElement(10);
        // Get page title
        String title = driver.getTitle();
        System.out.println(BLUE + "Page Title: " + title + RESET);
        if (title.contains("Zlaata")) {
            System.out.println(GREEN + "Application launched successfully" + RESET);
        } else {
            System.out.println(RED + "Application not loaded properly" + RESET);
        }
        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//img[@alt='zlaata Logo']")
        ));

        assertTrue("❌ Logo is not displayed on the page", logo.isDisplayed());

        System.out.println(GREEN + "✅ Logo is displayed" + RESET);

        logo.click();

        // Verify redirect to home page
        String currentUrl = driver.getCurrentUrl();

        if(currentUrl.equals("https://www.zlaata.com/")) {
            System.out.println(GREEN + "✅ Successfully redirected to landing  Page" + RESET);
        } else {
            System.out.println(RED + "❌ Not redirected to landing Page" + RESET);
        }
	}

//TC-01
public void validateUrlAndLogo() {
	
	driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	 click(zlaataIndiaShopButton);
	 Common.waitForElement(10);
	verifyUrlAndLogo();
}



//private void featureOnSection() {
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//
//	    By featuredSection = By.xpath("//section[contains(@class,'featured_section')]");
//	    
//	    By imagesBy = By.xpath("//section[contains(@class,'featured_section')]//img");
//
//	    // ---------------- SCROLL ----------------
//	    WebElement featureEle = wait.until(
//	            ExpectedConditions.presenceOfElementLocated(featuredSection)
//	    );
//
//	    ((JavascriptExecutor) driver).executeScript(
//	            "arguments[0].scrollIntoView({block:'center'});", featureEle);
//
//	    wait.until(ExpectedConditions.visibilityOf(featureEle));
//
//	    System.out.println("✅ Scrolled to Featured Section");
//
//	 // ---------------- SMALL SCROLL ----------------
//	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,300)");
//	    Common.waitForElement(5);
//
//
//	    // ---------------- FORWARD ARROW ----------------
//	    By nextArrowBy = By.xpath("//section[contains(@class,'featured_section')]//*[contains(@class,'swiper-button-next')]");
//
//	    List<WebElement> nextBtns = driver.findElements(nextArrowBy);
//
//	    if (nextBtns.isEmpty()) {
//
//	        System.out.println("⚠️ Forward Arrow NOT AVAILABLE");
//
//	    } else {
//
//	        WebElement nextBtn = nextBtns.get(0);
//
//	        String classAttr = nextBtn.getAttribute("class");
//
//	        if (classAttr != null && classAttr.contains("swiper-button-next swiper-button-lock")) {
//
//	            System.out.println("⚠️ Forward Arrow NOT ACTIVE");
//
//	        } else {
//
//	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", nextBtn);
//
//	            try {
//	                nextBtn.click();
//	            } catch (Exception e) {
//	                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);
//	            }
//
//	            System.out.println("➡️ Forward Arrow ACTIVE → CLICKED");
//	        }
//	    }
//
//
//	    // ---------------- BACKWARD ARROW ----------------
//	    By prevArrowBy = By.xpath("//section[contains(@class,'featured_section')]//*[contains(@class,'swiper-button-prev')]");
//
//	    List<WebElement> prevBtns = driver.findElements(prevArrowBy);
//
//	    if (prevBtns.isEmpty()) {
//
//	        System.out.println("⚠️ Backward Arrow NOT AVAILABLE");
//
//	    } else {
//
//	        WebElement prevBtn = prevBtns.get(0);
//
//	        String classAttr = prevBtn.getAttribute("class");
//
//	        if (classAttr != null && classAttr.contains("swiper-button-prev swiper-button-lock")) {
//
//	            System.out.println("⚠️ Backward Arrow NOT ACTIVE");
//
//	        } else {
//
//	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", prevBtn);
//
//	            try {
//	                prevBtn.click();
//	            } catch (Exception e) {
//	                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", prevBtn);
//	            }
//
//	            System.out.println("⬅️ Backward Arrow ACTIVE → CLICKED");
//	        }
//	    }
//	    // ---------------- CLICK ALL BANNERS ----------------
//	    List<WebElement> images = wait.until(
//	            ExpectedConditions.visibilityOfAllElementsLocatedBy(imagesBy)
//	    );
//
//	    int total = images.size();
//	    System.out.println("🖼 Total Banners: " + total);
//
//	    for (int i = 0; i < total; i++) {
//
//	        // 🔁 Re-fetch to avoid stale element
//	        images = wait.until(
//	                ExpectedConditions.visibilityOfAllElementsLocatedBy(imagesBy)
//	        );
//
//	        WebElement img = images.get(i);
//
//	        try {
//	            ((JavascriptExecutor) driver).executeScript(
//	                    "arguments[0].scrollIntoView({block:'center'});", img);
//
//	            wait.until(ExpectedConditions.elementToBeClickable(img));
//
//	            // ---------------- ALT CHECK ----------------
//	            String bannerName = img.getAttribute("alt");
//
//	            if (bannerName == null || bannerName.trim().isEmpty()) {
//	                System.out.println("⚠️ Banner [" + (i + 1) + "] has NO ALT text → Skipping");
//	                continue;
//	            }
//
//	            bannerName = bannerName.toLowerCase().trim();
//
//	            System.out.println("👉 Clicking Banner [" + (i + 1) + "] : " + bannerName);
//
//	            // ---------------- URL BEFORE CLICK ----------------
//	            String beforeClickUrl = driver.getCurrentUrl();
//
//	            clickUsingJavaScript(img);
//
//	            Thread.sleep(2000);
//
//	            // ---------------- URL AFTER CLICK ----------------
//	            String currentUrl = driver.getCurrentUrl().toLowerCase();
//
//	            // ---------------- NO REDIRECT ----------------
//	            if (currentUrl.equalsIgnoreCase(beforeClickUrl)) {
//
//	                System.out.println("⚠️ Banner [" + (i + 1) + "] has NO REDIRECT / NO LINK");
//	                continue;
//	            }
//
//	            // ---------------- MATCH LOGIC ----------------
//	            String cleanBanner = bannerName.replace(" ", "")
//	                                           .replace(".com", "")
//	                                           .replace("-", "");
//
//	            String cleanUrl = currentUrl.replace("https://", "")
//	                                        .replace("http://", "")
//	                                        .replace("www.", "")
//	                                        .replace(".com", "")
//	                                        .replace("-", "")
//	                                        .replace("/", "");
//
//	            if (cleanUrl.contains(cleanBanner)) {
//
//	                System.out.println("✅ PASS → Banner & URL matched");
//	                System.out.println("🔗 URL: " + currentUrl);
//
//	            } else {
//
//	                Assert.fail(
//	                        "❌ FAIL → Banner & URL NOT matched\n" +
//	                        "Banner : " + bannerName +
//	                        "\nURL    : " + currentUrl
//	                );
//	            }
//
//	            // 🔙 Go back
//	            driver.navigate().back();
//
//	            wait.until(ExpectedConditions.visibilityOfElementLocated(featuredSection));
//
//	        } catch (Exception e) {
//	            Assert.fail("❌ Error on banner index " + i + " : " + e.getMessage());
//	        }
//	    }
//
//	    System.out.println("🎉 All banners validated successfully!");
//	}


//private void featureOnSection() {
//
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//
//    // 🎨 COLORS
//    String GREEN  = "\u001B[32m";
//    String RED    = "\u001B[31m";
//    String YELLOW = "\u001B[33m";
//    String CYAN   = "\u001B[36m";
//    String RESET  = "\u001B[0m";
//
//    By featuredSection = By.xpath("//section[contains(@class,'featured_section')]");
//    By imagesBy = By.xpath("//section[contains(@class,'featured_section')]//img");
//
//    // ---------------- SCROLL ----------------
//    WebElement featureEle = wait.until(
//            ExpectedConditions.presenceOfElementLocated(featuredSection)
//    );
//
//    ((JavascriptExecutor) driver).executeScript(
//            "arguments[0].scrollIntoView({block:'center'});", featureEle);
//
//    wait.until(ExpectedConditions.visibilityOf(featureEle));
//
//    System.out.println(CYAN + "✅ Scrolled to Featured Section" + RESET);
//
//    // ---------------- SMALL SCROLL ----------------
//    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,300)");
//    Common.waitForElement(5);
//
//    // ---------------- FORWARD ARROW ----------------
//    By nextArrowBy = By.xpath("//section[contains(@class,'featured_section')]//*[contains(@class,'swiper-button-next')]");
//
//    List<WebElement> nextBtns = driver.findElements(nextArrowBy);
//
//    if (nextBtns.isEmpty()) {
//        System.out.println(YELLOW + "⚠️ Forward Arrow NOT AVAILABLE" + RESET);
//    } else {
//        WebElement nextBtn = nextBtns.get(0);
//        String classAttr = nextBtn.getAttribute("class");
//
//        if (classAttr != null && classAttr.contains("swiper-button-next swiper-button-lock")) {
//            System.out.println(YELLOW + "⚠️ Forward Arrow NOT ACTIVE" + RESET);
//        } else {
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", nextBtn);
//
//            try {
//                nextBtn.click();
//            } catch (Exception e) {
//                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);
//            }
//
//            System.out.println(GREEN + "➡️ Forward Arrow ACTIVE → CLICKED" + RESET);
//        }
//    }
//
//    // ---------------- BACKWARD ARROW ----------------
//    By prevArrowBy = By.xpath("//section[contains(@class,'featured_section')]//*[contains(@class,'swiper-button-prev')]");
//
//    List<WebElement> prevBtns = driver.findElements(prevArrowBy);
//
//    if (prevBtns.isEmpty()) {
//        System.out.println(YELLOW + "⚠️ Backward Arrow NOT AVAILABLE" + RESET);
//    } else {
//        WebElement prevBtn = prevBtns.get(0);
//        String classAttr = prevBtn.getAttribute("class");
//
//        if (classAttr != null && classAttr.contains("swiper-button-prev swiper-button-lock")) {
//            System.out.println(YELLOW + "⚠️ Backward Arrow NOT ACTIVE" + RESET);
//        } else {
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", prevBtn);
//
//            try {
//                prevBtn.click();
//            } catch (Exception e) {
//                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", prevBtn);
//            }
//
//            System.out.println(GREEN + "⬅️ Backward Arrow ACTIVE → CLICKED" + RESET);
//        }
//    }
//
// // ---------------- CLICK ALL BANNERS ----------------
//    List<WebElement> images = wait.until(
//            ExpectedConditions.visibilityOfAllElementsLocatedBy(imagesBy)
//    );
//    int total = images.size();
//    System.out.println(CYAN + "🖼 Total Banners: " + total + RESET);
//
//    for (int i = 0; i < total; i++) {
//
//        images = wait.until(
//                ExpectedConditions.visibilityOfAllElementsLocatedBy(imagesBy)
//        );
//
//        WebElement img = images.get(i);
//
//        try {
//            ((JavascriptExecutor) driver).executeScript(
//                    "arguments[0].scrollIntoView({block:'center'});", img);
//
//            wait.until(ExpectedConditions.elementToBeClickable(img));
//
//            // ---------------- ALT CHECK ----------------
//            String bannerName = img.getAttribute("alt");
//
//            if (bannerName == null || bannerName.trim().isEmpty()) {
//                System.out.println(YELLOW + "⚠️ Banner [" + (i + 1) + "] NO ALT → Skipping" + RESET);
//                continue;
//            }
//
//            bannerName = bannerName.toLowerCase().trim();
//
//            System.out.println(CYAN + "👉 Clicking Banner [" + (i + 1) + "] : " + bannerName + RESET);
//
//            String beforeClickUrl = driver.getCurrentUrl();
//
//            clickUsingJavaScript(img);
//
//            Thread.sleep(2000);
//
//            String currentUrl = driver.getCurrentUrl().toLowerCase();
//
//            if (currentUrl.equalsIgnoreCase(beforeClickUrl)) {
//                System.out.println(YELLOW + "⚠️ Banner [" + (i + 1) + "] NO REDIRECT" + RESET);
//                continue;
//            }
//
//            String cleanBanner = bannerName.replace(" ", "")
//                                           .replace(".com", "")
//                                           .replace("-", "");
//
//            String cleanUrl = currentUrl.replace("https://", "")
//                                        .replace("http://", "")
//                                        .replace("www.", "")
//                                        .replace(".com", "")
//                                        .replace("-", "")
//                                        .replace("/", "");
//
//            if (cleanUrl.contains(cleanBanner)) {
//
//                System.out.println(GREEN + "✅ PASS → Banner & URL matched" + RESET);
//                System.out.println(CYAN + "🔗 URL: " + currentUrl + RESET);
//
//            } else {
//
//                System.out.println(RED + "❌ FAIL → Banner & URL NOT matched" + RESET);
//
//                Assert.fail(
//                        "Banner : " + bannerName +
//                        "\nURL    : " + currentUrl
//                );
//            }
//
//            driver.navigate().back();
//
//            wait.until(ExpectedConditions.visibilityOfElementLocated(featuredSection));
//
//        } catch (Exception e) {
//            System.out.println(RED + "❌ Error on banner index " + i + RESET);
//            Assert.fail("Error: " + e.getMessage());
//        }
//    }
//
//    System.out.println(GREEN + "🎉 All banners validated successfully!" + RESET);
//}
//
private void featureOnSection() {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(15));

    // 🎨 COLORS
    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";

    By featuredSection =
            By.xpath("//section[contains(@class,'featured_section')]");

    By imagesBy =
            By.xpath("//section[contains(@class,'featured_section')]//img");

    // ---------------- SCROLL ----------------

    WebElement featureEle = wait.until(
            ExpectedConditions.presenceOfElementLocated(featuredSection)
    );

    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            featureEle);

    wait.until(ExpectedConditions.visibilityOf(featureEle));

    System.out.println(CYAN + "✅ Scrolled to Featured Section" + RESET);

    // ---------------- SMALL SCROLL ----------------

    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,300)");
    Common.waitForElement(3);

    // ---------------- FORWARD ARROW ----------------

    By nextArrowBy =
            By.xpath("//section[contains(@class,'featured_section')]//*[contains(@class,'swiper-button-next')]");

    List<WebElement> nextBtns = driver.findElements(nextArrowBy);

    if (nextBtns.isEmpty()) {

        System.out.println(YELLOW +
                "⚠️ Forward Arrow NOT AVAILABLE"
                + RESET);

    } else {

        WebElement nextBtn = nextBtns.get(0);
        String classAttr = nextBtn.getAttribute("class");

        if (classAttr != null &&
                classAttr.contains("swiper-button-lock")) {

            System.out.println(YELLOW +
                    "⚠️ Less banners → Forward Arrow NOT ACTIVE"
                    + RESET);

        } else {

            try {

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        nextBtn);

                Common.waitForElement(1);

                nextBtn.click();

                System.out.println(GREEN +
                        "➡️ Forward Arrow ACTIVE → CLICKED"
                        + RESET);

            } catch (Exception e) {

                System.out.println(YELLOW +
                        "⚠️ Forward Arrow click failed but continuing"
                        + RESET);
            }
        }
    }

    // ---------------- BACKWARD ARROW ----------------

    By prevArrowBy =
            By.xpath("//section[contains(@class,'featured_section')]//*[contains(@class,'swiper-button-prev')]");

    List<WebElement> prevBtns = driver.findElements(prevArrowBy);

    if (prevBtns.isEmpty()) {

        System.out.println(YELLOW +
                "⚠️ Backward Arrow NOT AVAILABLE"
                + RESET);

    } else {

        WebElement prevBtn = prevBtns.get(0);
        String classAttr = prevBtn.getAttribute("class");

        if (classAttr != null &&
                classAttr.contains("swiper-button-lock")) {

            System.out.println(YELLOW +
                    "⚠️ Less banners → Backward Arrow NOT ACTIVE"
                    + RESET);

        } else {

            try {

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        prevBtn);

                Common.waitForElement(1);

                prevBtn.click();

                System.out.println(GREEN +
                        "⬅️ Backward Arrow ACTIVE → CLICKED"
                        + RESET);

            } catch (Exception e) {

                System.out.println(YELLOW +
                        "⚠️ Backward Arrow click failed but continuing"
                        + RESET);
            }
        }
    }

    // ---------------- CLICK ALL BANNERS ----------------

    List<WebElement> images = driver.findElements(imagesBy);

    if (images.isEmpty()) {

        System.out.println(YELLOW +
                "⚠️ No banners available in Featured Section"
                + RESET);

        return;
    }

    int total = images.size();

    System.out.println(CYAN +
            "🖼 Total Banners: " + total
            + RESET);

    for (int i = 0; i < total; i++) {

        try {

            images = driver.findElements(imagesBy);

            if (images.isEmpty()) {

                System.out.println(YELLOW +
                        "⚠️ No banners found while iterating"
                        + RESET);

                break;
            }

            WebElement img = images.get(i);

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    img);

            Common.waitForElement(2);

            String bannerName = img.getAttribute("alt");

            if (bannerName == null ||
                    bannerName.trim().isEmpty()) {

                System.out.println(YELLOW +
                        "⚠️ Banner [" + (i + 1)
                        + "] NO ALT → Skipping"
                        + RESET);

                continue;
            }

            bannerName = bannerName.toLowerCase().trim();

            System.out.println(CYAN +
                    "👉 Clicking Banner [" + (i + 1)
                    + "] : " + bannerName
                    + RESET);

            String beforeClickUrl =
                    driver.getCurrentUrl();

            // ---------------- SAFE CLICK ----------------

            try {

                WebElement clickableBanner =
                        img.findElement(By.xpath("./ancestor::a[1]"));

                ((JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].scrollIntoView({block:'center'});",
                                clickableBanner);

                Common.waitForElement(1);

                clickableBanner.click();

            } catch (Exception e) {

                ((JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].click();",
                                img);
            }

            Common.waitForElement(3);

            String currentUrl =
                    driver.getCurrentUrl().toLowerCase();

            if (currentUrl.equalsIgnoreCase(beforeClickUrl)) {

                System.out.println(YELLOW +
                        "⚠️ Banner [" + (i + 1)
                        + "] NO REDIRECT"
                        + RESET);

                continue;
            }

            String cleanBanner =
                    bannerName.replace(" ", "")
                            .replace(".com", "")
                            .replace("-", "");

            String cleanUrl =
                    currentUrl.replace("https://", "")
                            .replace("http://", "")
                            .replace("www.", "")
                            .replace(".com", "")
                            .replace("-", "")
                            .replace("/", "");

            if (cleanUrl.contains(cleanBanner)) {

                System.out.println(GREEN +
                        "✅ PASS → Banner & URL matched"
                        + RESET);

            } else {

                System.out.println(RED +
                        "❌ FAIL → Banner & URL NOT matched"
                        + RESET);

                Assert.fail(
                        "Banner : " + bannerName +
                        "\nURL    : " + currentUrl
                );
            }

            driver.navigate().back();

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            featuredSection));

            Common.waitForElement(2);

        } catch (Exception e) {

            System.out.println(RED +
                    "❌ Error on banner index " + i
                    + RESET);

            Assert.fail("Error: " + e.getMessage());
        }
    }

    System.out.println(GREEN +
            "🎉 All banners validated successfully!"
            + RESET);
}

public void threadBannerINHomePage() {

    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

    click(zlaataIndiaShopButton);
    Common.waitForElement(10);
    threadbannerClickForZlIndia();
      click(bosslady);
      Common.waitForElement(5);

      threadbannerClickForBl();
      
}
private void threadbannerClickForZlIndia() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Scroll to section
    WebElement section = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//section[@data-section='zi_threads_banner']")
        )
    );

    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView(true);", section);

    System.out.println(CYAN + "🔽 Scrolled to Thread Banner" + RESET);

    // Click banner
    WebElement banner = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//section[@data-section='zi_threads_banner']//img[@alt='CLICK TO EARN']")
        )
    );

    banner.click();

    // Verify
    String url = driver.getCurrentUrl().toLowerCase();

    if (url.contains("threads")) {
        System.out.println(GREEN + "✅ Thread page opened for zlaata India" + RESET);
    } else {
        System.out.println(RED + "❌ Thread page not opened" + RESET);
    }
}
	

private void threadbannerClickForBl() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Scroll to section
    WebElement section = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//section[@data-section='bl_threads_banner']")
        )
    );

    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView(true);", section);

    System.out.println(CYAN + "🔽 Scrolled to BL Thread Banner" + RESET);

    // Click banner
    WebElement banner = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//section[@data-section='bl_threads_banner']//a[@class='bl_banner_link']")
        )
    );

    banner.click();

    // Verify
    String url = driver.getCurrentUrl().toLowerCase();

    if (url.contains("threads")) {
        System.out.println(GREEN + "✅ Thread page opened for Boss lady" + RESET);
    } else {
        System.out.println(RED + "❌ Thread page not opened" + RESET);
    }
}

public void giftCard() {
	
	 driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

	    click(zlaataIndiaShopButton);
	    Common.waitForElement(10);
	    giftcardbannerClickForZL();
	    click(bosslady);
	    Common.waitForElement(5);

	    giftcardbannerClickForBL();
}

private void giftcardbannerClickForZL() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Scroll to section
    WebElement section = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//section[@data-section='zi_gift_card_banner']")
        )
    );

    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView(true);", section);

    System.out.println(CYAN + "🔽 Scrolled to Zlaata India  Gift card banner " + RESET);

    // Click banner
    WebElement banner = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//section[@data-section='zi_gift_card_banner']//a[@class='bl_banner_link boss-lady-gift-card']")
        )
    );

    banner.click();

    // Verify
    String url = driver.getCurrentUrl().toLowerCase();

    if (url.contains("gift-card")) {
        System.out.println(GREEN + "✅ gift-card page opened for zlaata India " + RESET);
    } else {
        System.out.println(RED + "❌ gift-card page not opened" + RESET);
    }
}

private void giftcardbannerClickForBL() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Scroll to section
    WebElement section = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//section[@data-section='bl_gift_card_banner']")
        )
    );

    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView(true);", section);

    System.out.println(CYAN + "🔽 Scrolled to BL Gift card banner " + RESET);

    // Click banner
    WebElement banner = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//section[@data-section='bl_gift_card_banner']//a[@class='bl_banner_link boss-lady-gift-card']")
        )
    );

    banner.click();

    // Verify
    String url = driver.getCurrentUrl().toLowerCase();

    if (url.contains("gift-card")) {
        System.out.println(GREEN + "✅ gift-card page opened for Boss lady" + RESET);
    } else {
        System.out.println(RED + "❌ gift-card page not opened" + RESET);
    }
}


public void MonsoonBanner() {
	
	driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

    click(zlaataIndiaShopButton);
    clickMonsoonBannerForZL();
    click(bosslady);
    clickMonsoonBannerForBL();
  

}
public void clickMonsoonBannerForZL() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";

    System.out.println(CYAN + "────────── Special Banner Test  for Zlaata India──────────" + RESET);


    String expectedUrlPart = "/influencers";

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    // Locate section
    WebElement section = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//section[@class='zi_special_banner module_section']")
        )
    );

    // Scroll
    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({block:'center'});", section);
    System.out.println(CYAN + "Scrolled to banner section" + RESET);

    // Click banner
    WebElement banner = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//section[@class='zi_special_banner module_section']//img[@alt='INFLUENCER BANNER']")
        )
    );

    banner.click();
    System.out.println(CYAN + "Clicked Influencer Banner for Zlaata India" + RESET);

    // Wait for navigation
    wait.until(ExpectedConditions.urlContains(expectedUrlPart));

    if (driver.getCurrentUrl().contains(expectedUrlPart)) {
        System.out.println(GREEN + "PASS: Banner redirected to Influencers page" + RESET);
    } else {
        System.out.println(RED + "FAIL: Banner redirection failed" + RESET);
    }

    // ✅ Product Verification (added)
    List<WebElement> products = wait.until(
        ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.xpath("//div[contains(@class,'prod_listing_card')]")
        )
    );

    if (products.size() > 0) {
        System.out.println(GREEN + "PASS: Products are displayed" + RESET);
    } else {
        System.out.println(YELLOW + "WARNING: No products found" + RESET);
    }

    System.out.println(CYAN + "─────────────────────────────────────────" + RESET);
}



public void clickMonsoonBannerForBL() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";

    System.out.println(CYAN + "────────── Boss Lady Banner Test ──────────" + RESET);

    String expectedUrlPart = "/influencers";

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    // Section
    WebElement section = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//section[contains(@class,'boss-lady-loyalty-section')]")
        )
    );

    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({block:'center'});", section);

    // Banner
    WebElement banner = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//img[@alt='Boss Lady Styled By']")
        )
    );

    try {
        new Actions(driver).moveToElement(banner).pause(Duration.ofSeconds(1)).click().perform();
    } catch (Exception e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", banner);
    }

    // Wait for navigation
    wait.until(ExpectedConditions.urlContains(expectedUrlPart));

    if (driver.getCurrentUrl().contains(expectedUrlPart)) {
        System.out.println(GREEN + "PASS: Banner redirected to Influencers page" + RESET);
    } else {
        System.out.println(RED + "FAIL: Redirection failed" + RESET);
    }

    // ✅ Wait for products (FIXED)
    List<WebElement> products = wait.until(
        ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.xpath("//div[contains(@class,'prod_listing_card')]")
        )
    );

    // ✅ Validation
    if (products.size() > 0) {
        System.out.println(GREEN + "PASS: Products are displayed" + RESET);
    } else {
        System.out.println(YELLOW + "WARNING: No products found" + RESET);
    }

    System.out.println(CYAN + "────────────────────────────────────────────" + RESET);
}


public void aboutus() {
	
    
    
    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

    click(zlaataIndiaShopButton);
    Common.waitForElement(10);
    clickOnAboutUsBannerForZL();
    click(bosslady);
    Common.waitForElement(10);
    clickOnAboutUsBannerForBL();

}
public void clickOnAboutUsBannerForZL() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";

    System.out.println(CYAN + "────────── About Us Banner Test ──────────" + RESET);

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    // ✅ Scroll to section
    WebElement section = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//section[contains(@class,'zi_special_banner')]")
        )
    );

    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({block:'center'});", section);

    System.out.println(CYAN + "Scrolled to Explore Banner section" + RESET);

    // ✅ Click Explore Banner
    WebElement banner = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//section[@class='zi_special_banner section_container module_section']")
        )
    );
Common.waitForElement(3);
    try {
        banner.click();
        System.out.println(CYAN + "Clicked Explore Banner" + RESET);
    } catch (Exception e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", banner);
        System.out.println(CYAN + "Clicked using JS" + RESET);
    }

    // ✅ Expected URL (flexible)
    String expectedUrlPart = "about-us";

    // Wait for navigation
    wait.until(ExpectedConditions.urlContains(expectedUrlPart));

    String currentUrl = driver.getCurrentUrl();

    // ✅ Validation
    if (currentUrl.contains(expectedUrlPart)) {
        System.out.println(GREEN + "PASS: Navigated to About Us page" + RESET);
        System.out.println(CYAN + "URL: " + currentUrl + RESET);
    } else {
        System.out.println(RED + "FAIL: Navigation failed" + RESET);
        System.out.println(CYAN + "Current URL: " + currentUrl + RESET);
    }

    System.out.println(CYAN + "────────────────────────────────────────" + RESET);
    

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




public void clickOnAboutUsBannerForBL() {
	
	

    String GREEN = "\u001B[32m";
    String RED   = "\u001B[31m";
    String CYAN  = "\u001B[36m";
    String RESET = "\u001B[0m";

    System.out.println(CYAN + "────────── Boss Lady Explore Test ──────────" + RESET);

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    // ✅ Scroll to section
    WebElement section = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//section[contains(@class,'boss-lady-loyalty-explore')]")
        )
    );

    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({block:'center'});", section);

    System.out.println(CYAN + "Scrolled to Boss Lady Explore section" + RESET);

    // ✅ Click banner
    WebElement banner = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//img[@alt='Boss Lady Explore']")
        )
    );

    try {
        new Actions(driver).moveToElement(banner).pause(Duration.ofSeconds(1)).click().perform();
        System.out.println(CYAN + "Clicked Boss Lady Explore Banner" + RESET);
    } catch (Exception e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", banner);
        System.out.println(CYAN + "Clicked using JS" + RESET);
    }

    // ✅ Correct URL validation
    String expectedUrlPart = "/about-us";

    wait.until(ExpectedConditions.urlContains(expectedUrlPart));

    String currentUrl = driver.getCurrentUrl();

    if (currentUrl.contains(expectedUrlPart)) {
        System.out.println(GREEN + "PASS: Navigated to About Us page" + RESET);
        System.out.println(CYAN + "URL: " + currentUrl + RESET);
    } else {
        System.out.println(RED + "FAIL: Navigation failed" + RESET);
        System.out.println(CYAN + "Current URL: " + currentUrl + RESET);
    }

    System.out.println(CYAN + "────────────────────────────────────────────" + RESET);
    

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

public void verifyCollectionBanners() throws InterruptedException {

    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

    click(zlaataIndiaShopButton);

    String GREEN  = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String BLUE   = "\u001B[34m";
    String RESET  = "\u001B[0m";

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    // Scroll to collection section
    WebElement section = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//section[contains(@class,'zi_collection')]")
            )
    );

    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", section);

    List<WebElement> banners = driver.findElements(
            By.xpath("//a[contains(@class,'zi_collection_card')]")
    );

    int total = banners.size();
    System.out.println(BLUE + "Total Collections: " + total + RESET);

    for (int i = 0; i < total; i++) {

        // Re-fetch banners every loop
        banners = driver.findElements(
                By.xpath("//a[contains(@class,'zi_collection_card')]")
        );

        WebElement banner = banners.get(i);

        String bannerName = banner.getText().trim();
        System.out.println(BLUE + "Clicking banner: " + bannerName + RESET);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", banner);

        // Wait for heading
        WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[@class='prod_listing_topic']")
                )
        );

        String pageHeading = heading.getText().trim();
        System.out.println(YELLOW + "Page heading: " + pageHeading + RESET);

        // Normalize text
        String normalizedBanner = bannerName.replace("-", "")
                .replace(" ", "")
                .toLowerCase();

        String normalizedHeading = pageHeading.replace("-", "")
                .replace(" ", "")
                .toLowerCase();

        // Validate heading match
        if (!normalizedBanner.equals(normalizedHeading)) {
            throw new AssertionError(
                    "Mismatch! Banner: " + bannerName +
                            " | Heading: " + pageHeading
            );
        }

        // Product validation (RE-FETCH here to avoid stale element)
        List<WebElement> products = driver.findElements(
                By.xpath("//div[contains(@class,'prod_listing_card')]")
        );

        if (products.size() > 0) {

            WebElement firstProduct = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("(//div[contains(@class,'prod_listing_card')])[1]")
                    )
            );

            if (firstProduct.isDisplayed()) {
                System.out.println(
                        GREEN + "Product displayed for: " + bannerName + RESET
                );
            }

        } else {
            System.out.println(
                    YELLOW + "No products found for: " + bannerName + RESET
            );
        }

        // Back to home page
        driver.navigate().back();

        // Wait again for collection section
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//section[contains(@class,'zi_collection')]")
                )
        );
    }
}



//public void verifyAllCategoriesWithProductValidation() throws InterruptedException {
//
//    String GREEN  = "\u001B[32m";
//    String RED    = "\u001B[31m";
//    String YELLOW = "\u001B[33m";
//    String BLUE   = "\u001B[34m";
//    String RESET  = "\u001B[0m";
//
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//
//    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//    click(zlaataIndiaShopButton);
//
//    // ✅ Scroll to Categories section
//    WebElement section = wait.until(
//        ExpectedConditions.presenceOfElementLocated(
//            By.xpath("//section[contains(@class,'zi_categories_banner')]")
//        )
//    );
//
//    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", section);
//    System.out.println(BLUE + "Scrolled to Categories section" + RESET);
//
//    // ✅ Initial count (NO See More click here)
//    List<WebElement> categories = driver.findElements(
//        By.xpath("//a[contains(@class,'zi_categories_card')]")
//    );
//
//    int initialCount = categories.size();
//    System.out.println(BLUE + "Initial Category Count: " + initialCount + RESET);
//
//    int clickedCount = 0;
//
//    for (int i = 0; i < initialCount; i++) {
//
//        // ✅ ONLY after 6th category → click See More
//        if (i == 6) {
//            List<WebElement> seeMoreBtn = driver.findElements(
//                By.xpath("//span[text()='See More']/parent::div")
//            );
//
//            if (seeMoreBtn.size() > 0 && seeMoreBtn.get(0).isDisplayed()) {
//                try {
//                    seeMoreBtn.get(0).click();
//                } catch (Exception e) {
//                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", seeMoreBtn.get(0));
//                }
//                System.out.println(BLUE + "Clicked See More" + RESET);
//                Thread.sleep(1500);
//            }
//        }
//
//        // ✅ Re-fetch categories
//        categories = driver.findElements(
//            By.xpath("//a[contains(@class,'zi_categories_card')]")
//        );
//
//        WebElement category = categories.get(i);
//        String categoryName = category.getText().trim();
//
//        System.out.println(BLUE + "Clicking: " + categoryName + RESET);
//
//        // ✅ Safe click (stale fix)
//        for (int retry = 0; retry < 3; retry++) {
//            try {
//                wait.until(ExpectedConditions.elementToBeClickable(category));
//                category.click();
//                clickedCount++;
//                break;
//            } catch (StaleElementReferenceException e) {
//                categories = driver.findElements(By.xpath("//a[contains(@class,'zi_categories_card')]"));
//                category = categories.get(i);
//            }
//        }
//
//        // ✅ Verify heading
//        WebElement heading = wait.until(
//            ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//h2[@class='prod_listing_topic']")
//            )
//        );
//
//        String pageHeading = heading.getText().trim();
//        System.out.println(YELLOW + "Heading: " + pageHeading + RESET);
//
//        // ✅ Verify URL
//        String currentUrl = driver.getCurrentUrl();
//        System.out.println("URL: " + currentUrl);
//
//        // ✅ Compare category vs heading
//        String cat = categoryName.replace("&", "").replace("-", "").replace(" ", "").toLowerCase();
//        String head = pageHeading.replace("&", "").replace("-", "").replace(" ", "").toLowerCase();
//
//        if (!cat.equals(head)) {
//            System.out.println(RED + "Mismatch: " + categoryName + " vs " + pageHeading + RESET);
//        }
//
//        // ✅ Product validation (ADDED)
//        List<WebElement> products = driver.findElements(
//            By.xpath("//div[contains(@class,'prod_listing_card')]")
//        );
//
//        if (products.size() > 0) {
//
//            WebElement firstProduct = wait.until(
//                ExpectedConditions.visibilityOf(products.get(0))
//            );
//
//            if (firstProduct.isDisplayed()) {
//                System.out.println(GREEN + "Product displayed for: " + categoryName + RESET);
//            }
//
//        } else {
//            System.out.println(YELLOW + "No products found for: " + categoryName + RESET);
//        }
//
//        // ✅ Back
//        driver.navigate().back();
//
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//            By.xpath("//section[contains(@class,'zi_categories_banner')]")
//        ));
//
//        // ✅ Scroll again
//        WebElement sec = driver.findElement(
//            By.xpath("//section[contains(@class,'zi_categories_banner')]")
//        );
//
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sec);
//
//        // ✅ Re-click See More after back (for remaining categories)
//        if (i >= 6) {
//            List<WebElement> seeMoreBtn = driver.findElements(
//                By.xpath("//span[text()='See More']/parent::div")
//            );
//
//            if (seeMoreBtn.size() > 0 && seeMoreBtn.get(0).isDisplayed()) {
//                try {
//                    seeMoreBtn.get(0).click();
//                } catch (Exception e) {
//                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", seeMoreBtn.get(0));
//                }
//                System.out.println(BLUE + "Re-clicked See More" + RESET);
//                Thread.sleep(1000);
//            }
//        }
//    }
//
//    // ✅ Final validation
//    System.out.println(BLUE + "Initial Count: " + initialCount + RESET);
//    System.out.println(BLUE + "Clicked Count: " + clickedCount + RESET);
//
//    if (initialCount == clickedCount) {
//        System.out.println(GREEN + "PASS: All categories clicked successfully" + RESET);
//    } else {
//        System.out.println(RED + "FAIL: Count mismatch" + RESET);
//    }
//
//    // ✅ Close browser
//    driver.quit();
//    System.out.println(BLUE + "Browser closed" + RESET);
//}

public void verifyAllCategoriesWithProductValidation() throws InterruptedException {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String BLUE   = "\u001B[34m";
    String RESET  = "\u001B[0m";

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
    click(zlaataIndiaShopButton);

    // ✅ Scroll to Categories section
    WebElement section = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//section[contains(@class,'zi_categories_banner')]")
        )
    );

    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", section);
    System.out.println(BLUE + "Scrolled to Categories section" + RESET);

    List<WebElement> categories = driver.findElements(
        By.xpath("//a[contains(@class,'zi_categories_card')]")
    );

    int initialCount = categories.size();
    System.out.println(BLUE + "Initial Category Count: " + initialCount + RESET);

    int clickedCount = 0;

    for (int i = 0; i < initialCount; i++) {

        // ✅ See More logic
        if (i == 6) {
            List<WebElement> seeMoreBtn = driver.findElements(
                By.xpath("//span[text()='See More']/parent::div")
            );

            if (!seeMoreBtn.isEmpty() && seeMoreBtn.get(0).isDisplayed()) {
                try {
                    seeMoreBtn.get(0).click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", seeMoreBtn.get(0));
                }
                System.out.println(BLUE + "Clicked See More" + RESET);
                Thread.sleep(1500);
            }
        }

        // ✅ Re-fetch categories (FIX)
        categories = driver.findElements(
            By.xpath("//a[contains(@class,'zi_categories_card')]")
        );

        WebElement category = categories.get(i);
        String categoryName = category.getText().trim();

        System.out.println(BLUE + "Clicking: " + categoryName + RESET);

        // ✅ Safe click with retry
        for (int retry = 0; retry < 3; retry++) {
            try {
                categories = driver.findElements(By.xpath("//a[contains(@class,'zi_categories_card')]"));
                category = categories.get(i);

                wait.until(ExpectedConditions.elementToBeClickable(category));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", category);

                clickedCount++;
                break;

            } catch (StaleElementReferenceException e) {
                System.out.println(RED + "Retry click for index: " + i + RESET);
            }
        }

        // ✅ Verify heading
        WebElement heading = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[@class='prod_listing_topic']")
            )
        );

        String pageHeading = heading.getText().trim();
        System.out.println(YELLOW + "Heading: " + pageHeading + RESET);

        // ✅ Compare names
        String cat = categoryName.replace("&", "").replace("-", "").replace(" ", "").toLowerCase();
        String head = pageHeading.replace("&", "").replace("-", "").replace(" ", "").toLowerCase();

        if (!cat.equals(head)) {
            System.out.println(RED + "Mismatch: " + categoryName + " vs " + pageHeading + RESET);
        }

        // ✅ 🔥 FIXED PRODUCT VALIDATION (NO STALE / NO TIMEOUT)
        List<WebElement> products = driver.findElements(
            By.xpath("//div[contains(@class,'prod_listing_card')]")
        );

        if (!products.isEmpty()) {
            System.out.println(GREEN + "Product displayed for: " + categoryName + RESET);
        } else {
            System.out.println(YELLOW + "No products found for: " + categoryName + RESET);
        }

        // ✅ Back
        driver.navigate().back();

        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//section[contains(@class,'zi_categories_banner')]")
        ));

        // ✅ Scroll again
        WebElement sec = driver.findElement(
            By.xpath("//section[contains(@class,'zi_categories_banner')]")
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sec);

        // ✅ Re-click See More
        if (i >= 6) {
            List<WebElement> seeMoreBtn = driver.findElements(
                By.xpath("//span[text()='See More']/parent::div")
            );

            if (!seeMoreBtn.isEmpty() && seeMoreBtn.get(0).isDisplayed()) {
                try {
                    seeMoreBtn.get(0).click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", seeMoreBtn.get(0));
                }
                System.out.println(BLUE + "Re-clicked See More" + RESET);
                Thread.sleep(1000);
            }
        }
    }

    // ✅ Final validation
    System.out.println(BLUE + "Initial Count: " + initialCount + RESET);
    System.out.println(BLUE + "Clicked Count: " + clickedCount + RESET);

    if (initialCount == clickedCount) {
        System.out.println(GREEN + "PASS: All categories clicked successfully" + RESET);
    } else {
        System.out.println(RED + "FAIL: Count mismatch" + RESET);
    }

    driver.quit();
    System.out.println(BLUE + "Browser closed" + RESET);
}





//public void verifyBossLadyCategories() {
//
//    String GREEN  = "\u001B[32m";
//    String RED    = "\u001B[31m";
//    String YELLOW = "\u001B[33m";
//    String BLUE   = "\u001B[34m";
//    String RESET  = "\u001B[0m";
//
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//    
//    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//    click(clickOnBossladyShopNowButton);
//
//
//    // ✅ Scroll to section
//    WebElement section = wait.until(
//        ExpectedConditions.presenceOfElementLocated(
//            By.xpath("//div[@class='bl_inner_wrap']")
//        )
//    );
//
//    ((JavascriptExecutor) driver).executeScript(
//        "arguments[0].scrollIntoView({block:'center'});", section);
//
//    System.out.println(BLUE + "Scrolled to Boss Lady Categories section" + RESET);
//
//    // ✅ Get total categories
//    List<WebElement> categories = driver.findElements(
//        By.xpath("//div[@class='bl_inner_wrap']//div[@data-category]")
//    );
//
//    int total = categories.size();
//    int clickedCount = 0;
//
//    System.out.println(BLUE + "Total Categories: " + total + RESET);
//
//    // ✅ Loop
//    for (int i = 0; i < total; i++) {
//
//        try {
//            // 🔁 Re-fetch elements (avoid stale)
//            categories = driver.findElements(
//                By.xpath("//div[@class='bl_inner_wrap']//div[@data-category]")
//            );
//
//            WebElement category = categories.get(i);
//
//            String categoryName = category.findElement(
//                By.xpath(".//span[@class='category-name']")
//            ).getText().trim();
//
//            System.out.println(BLUE + "Clicking category: " + categoryName + RESET);
//
//            // Click banner (image or link)
//            WebElement clickElement = category.findElement(
//                By.xpath(".//a[contains(@class,'category-link')]")
//            );
//
//            clickElement.click();
//
//            // ✅ Wait for heading
//            WebElement heading = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//h2[@class='prod_listing_topic']")
//                )
//            );
//
//            String pageHeading = heading.getText().trim();
//            System.out.println(YELLOW + "Heading: " + pageHeading + RESET);
//
//            // ✅ Normalize compare
//            String cat = categoryName.replace("&", "").replace("-", "").replace(" ", "").toLowerCase();
//            String head = pageHeading.replace("&", "").replace("-", "").replace(" ", "").toLowerCase();
//
//            // ✅ Partial match logic
//            if (cat.contains(head) || head.contains(cat)) {
//                System.out.println(GREEN + "PASS: Name matched -> " + categoryName + " vs " + pageHeading + RESET);
//            } else {
//                System.out.println(RED + "FAIL: Name mismatch -> " + categoryName + " vs " + pageHeading + RESET);
//            }
//
//            // ✅ URL
//            String currentUrl = driver.getCurrentUrl();
//            System.out.println("URL: " + currentUrl);
//
//            // ✅ Product check
//            List<WebElement> products = driver.findElements(
//                By.xpath("//div[contains(@class,'prod_listing_card')]")
//            );
//
//            if (products.size() > 0) {
//                wait.until(ExpectedConditions.visibilityOf(products.get(0)));
//                System.out.println(GREEN + "Product displayed for: " + categoryName + RESET);
//            } else {
//                System.out.println(YELLOW + "No products for: " + categoryName + RESET);
//            }
//
//            clickedCount++;
//
//            // ✅ Back
//            driver.navigate().back();
//
//            wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//div[@class='bl_inner_wrap']")
//            ));
//
//        } catch (Exception e) {
//            System.out.println(RED + "Error in category index " + i + RESET);
//        }
//    }
//
//    // ✅ Final count validation
//    System.out.println(BLUE + "Total Categories: " + total + RESET);
//    System.out.println(BLUE + "Clicked Categories: " + clickedCount + RESET);
//
//    if (total == clickedCount) {
//        System.out.println(GREEN + "PASS: All categories clicked successfully" + RESET);
//    } else {
//        System.out.println(RED + "FAIL: Some categories not clicked" + RESET);
//    }
//}




public void verifyBossLadyCategories() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String BLUE   = "\u001B[34m";
    String RESET  = "\u001B[0m";

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    
    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
    click(clickOnBossladyShopNowButton);


    // ✅ Scroll to section
    WebElement section = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[@class='bl_inner_wrap']")
        )
    );

    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({block:'center'});", section);

    System.out.println(BLUE + "Scrolled to Boss Lady Categories section" + RESET);

    // ✅ Get total categories
    List<WebElement> categories = driver.findElements(
        By.xpath("//div[@class='bl_inner_wrap']//div[@data-category]")
    );

    int total = categories.size();
    int clickedCount = 0;

    System.out.println(BLUE + "Total Categories: " + total + RESET);

    // ✅ Loop
    for (int i = 0; i < total; i++) {

        try {
            // 🔁 Re-fetch elements (avoid stale)
            categories = driver.findElements(
                By.xpath("//div[@class='bl_inner_wrap']//div[@data-category]")
            );

            WebElement category = categories.get(i);

            String categoryName = category.findElement(
                By.xpath(".//span[@class='category-name']")
            ).getText().trim();

            System.out.println(BLUE + "Clicking category: " + categoryName + RESET);

            // Click banner (image or link)
            WebElement clickElement = category.findElement(
                By.xpath(".//a[contains(@class,'category-link')]")
            );

            clickElement.click();

            // ✅ Wait for heading
            WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[@class='prod_listing_topic']")
                )
            );

            String pageHeading = heading.getText().trim();
            System.out.println(YELLOW + "Heading: " + pageHeading + RESET);

            // ✅ Normalize compare
            String cat = categoryName.replace("&", "").replace("-", "").replace(" ", "").toLowerCase();
            String head = pageHeading.replace("&", "").replace("-", "").replace(" ", "").toLowerCase();

            // ✅ Partial match logic
            if (cat.contains(head) || head.contains(cat)) {
                System.out.println(GREEN + "PASS: Name matched -> " + categoryName + " vs " + pageHeading + RESET);
            } else {
                System.out.println(RED + "FAIL: Name mismatch -> " + categoryName + " vs " + pageHeading + RESET);
            }

            // ✅ URL
            String currentUrl = driver.getCurrentUrl();
            System.out.println("URL: " + currentUrl);

            // ✅ Product check
            List<WebElement> products = driver.findElements(
                By.xpath("//div[contains(@class,'prod_listing_card')]")
            );

            if (products.size() > 0) {
                wait.until(ExpectedConditions.visibilityOf(products.get(0)));
                System.out.println(GREEN + "Product displayed for: " + categoryName + RESET);
            } else {
                System.out.println(YELLOW + "No products for: " + categoryName + RESET);
            }

            clickedCount++;

            // ✅ Back
            driver.navigate().back();

            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='bl_inner_wrap']")
            ));

        } catch (Exception e) {
            System.out.println(RED + "Error in category index " + i + RESET);
        }
    }

    // ✅ Final count validation
    System.out.println(BLUE + "Total Categories: " + total + RESET);
    System.out.println(BLUE + "Clicked Categories: " + clickedCount + RESET);

    if (total == clickedCount) {
        System.out.println(GREEN + "PASS: All categories clicked successfully" + RESET);
    } else {
        System.out.println(RED + "FAIL: Some categories not clicked" + RESET);
    }
}



//public void verifyNewInProductsCompleteFlow() throws InterruptedException {
//
//    String GREEN  = "\u001B[32m";
//    String RED    = "\u001B[31m";
//    String YELLOW = "\u001B[33m";
//    String BLUE   = "\u001B[34m";
//    String RESET  = "\u001B[0m";
//
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//    Actions actions = new Actions(driver);
//
//    // Open homepage
//    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//
//    // Click Shop button if needed
//    click(zlaataIndiaShopButton);
//
//    By sectionLocator = By.xpath("//section[@data-section='new_arrivals']");
//    By productLocator = By.xpath("//section[@data-section='new_arrivals']//a[contains(@class,'banner_link')]");
//    By viewAllLocator = By.xpath("//a[contains(@class,'new_in_redirect')]");
//
//    // Scroll to NEW IN section
//    WebElement section = wait.until(ExpectedConditions.presenceOfElementLocated(sectionLocator));
//    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", section);
//    Thread.sleep(1000); // allow initial load
//
//    List<WebElement> products = driver.findElements(productLocator);
//    int total = products.size();
//    int hoverLimit = Math.min(4, total); // hover first 4
//    int verifiedCount = 0;
//
//    System.out.println(BLUE + "Total Products: " + total + RESET);
//
//    for (int i = 0; i < hoverLimit; i++) { // Only hover first 4 products
//
//        // Re-fetch elements after each iteration
//        products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productLocator));
//        WebElement product = products.get(i);
//
//        // Scroll & hover to load overlay
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
//        actions.moveToElement(product).perform();
//        Thread.sleep(1500);
//
//        try {
//            // ===== CARD DATA =====
//            String cardName = product.findElement(By.xpath("//h3[@class='new_in_heading']")).getText().trim();
//            String cardCurrent = product.findElement(By.xpath(".//span[contains(@class,'prod_current_price')]")).getText().trim();
//
//            List<WebElement> cardActualList = product.findElements(By.xpath(".//span[contains(@class,'prod_actual_price')]"));
//            String cardActual = cardActualList.size() > 0 ? cardActualList.get(0).getText().trim() : "";
//
//            String cardDiscount = "0%";
//            if (!cardActual.isEmpty()) {
//                int curr = Integer.parseInt(cardCurrent.replaceAll("[^0-9]", ""));
//                int act  = Integer.parseInt(cardActual.replaceAll("[^0-9]", ""));
//                cardDiscount = ((act - curr) * 100) / act + "%";
//            }
//
//            System.out.println(BLUE + "Hover Card: " + cardName + RESET);
//            System.out.println("Card Current: " + cardCurrent + " | Actual: " + cardActual + " | Discount: " + cardDiscount);
//
//            // ===== CLICK PRODUCT =====
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", product);
//
//            // ===== PDP =====
//            WebElement pdpNameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class='prod_name']")));
//            WebElement pdpCurrentEl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='prod_current_price']")));
//            List<WebElement> pdpActualList = driver.findElements(By.xpath("//div[@class='prod_actual_price']"));
//            String pdpActual = pdpActualList.size() > 0 ? pdpActualList.get(0).getText().trim() : "";
//
//            String pdpName = pdpNameEl.getText().trim();
//            String pdpCurrent = pdpCurrentEl.getText().trim();
//            String pdpDiscount = "0%";
//            if (!pdpActual.isEmpty()) {
//                int curr = Integer.parseInt(pdpCurrent.replaceAll("[^0-9]", ""));
//                int act  = Integer.parseInt(pdpActual.replaceAll("[^0-9]", ""));
//                pdpDiscount = ((act - curr) * 100) / act + "%";
//            }
//
//            System.out.println(YELLOW + "PDP Name: " + pdpName + RESET);
//            System.out.println("PDP Current: " + pdpCurrent + " | Actual: " + pdpActual + " | Discount: " + pdpDiscount);
//
//            // ===== VALIDATION =====
//            // Partial name match passes silently, only check strict price
//            if (!cardCurrent.replaceAll("[^0-9]", "").equals(pdpCurrent.replaceAll("[^0-9]", ""))) {
//                System.out.println(RED + "Price Mismatch ❌ -> Card: " + cardCurrent + " | PDP: " + pdpCurrent + RESET);
//            } else {
//                System.out.println(GREEN + "Price Match ✅" + RESET);
//            }
//
//            verifiedCount++;
//
//            // ===== BACK NAVIGATION =====
//            driver.navigate().back();
//            wait.until(ExpectedConditions.presenceOfElementLocated(sectionLocator));
//            WebElement sectionAgain = driver.findElement(sectionLocator);
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sectionAgain);
//            Thread.sleep(1500); // allow swiper reload
//
//        } catch (Exception e) {
//            System.out.println(RED + "Error at product index: " + i + " -> " + e.getMessage() + RESET);
//        }
//    }
//
//    // ✅ CLICK VIEW ALL (if exists) instead of checking remaining product(s)
//    List<WebElement> viewAll = driver.findElements(viewAllLocator);
//    if (!viewAll.isEmpty()) {
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewAll.get(0));
//        Thread.sleep(1500);
//
//        String heading = driver.findElement(By.xpath("//h2[@class='prod_listing_topic']")).getText();
//        String url     = driver.getCurrentUrl();
//
//        System.out.println(BLUE + "View All Page Heading: " + heading + RESET);
//        System.out.println(BLUE + "View All Page URL: " + url + RESET);
//    }
//
//    System.out.println(BLUE + "Verified Products Count: " + verifiedCount + "/" + total + RESET);
//    if (verifiedCount == total) {
//        System.out.println(GREEN + "All products verified successfully ✅" + RESET);
//    } else {
//        System.out.println(BLUE + "Some products are behind View All or skipped ⏭️" + RESET);
//    }
//
//    // Close browser
//    driver.quit();
//}



//public void verifyNewInProductsCompleteFlow() throws InterruptedException {
//
//    String GREEN  = "\u001B[32m";
//    String RED    = "\u001B[31m";
//    String YELLOW = "\u001B[33m";
//    String BLUE   = "\u001B[34m";
//    String RESET  = "\u001B[0m";
//
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//    Actions actions = new Actions(driver);
//
//    // Open homepage
//    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//
//    // Click Shop button
//    click(zlaataIndiaShopButton);
//
//    By sectionLocator = By.xpath("//section[@data-section='new_arrivals']");
//
//    By productLocator = By.xpath(
//            "//section[@data-section='new_arrivals']//a[contains(@class,'banner_link')]"
//    );
//
//    By viewAllLocator = By.xpath("//a[contains(@class,'new_in_redirect')]");
//
//    // Scroll to NEW IN section
//    WebElement section = wait.until(
//            ExpectedConditions.presenceOfElementLocated(sectionLocator)
//    );
//
//    ((JavascriptExecutor) driver)
//            .executeScript("arguments[0].scrollIntoView(true);", section);
//
//    Thread.sleep(1000);
//
//    List<WebElement> products = driver.findElements(productLocator);
//
//    int total = products.size();
//    int hoverLimit = Math.min(4, total);
//    int verifiedCount = 0;
//
//    System.out.println(BLUE + "Total Products: " + total + RESET);
//
//    for (int i = 0; i < hoverLimit; i++) {
//
//        // Re-fetch elements after each iteration
//        products = wait.until(
//                ExpectedConditions.presenceOfAllElementsLocatedBy(productLocator)
//        );
//
//        WebElement product = products.get(i);
//
//        // Scroll & hover
//        ((JavascriptExecutor) driver)
//                .executeScript("arguments[0].scrollIntoView(true);", product);
//
//        actions.moveToElement(product).perform();
//
//        Thread.sleep(1500);
//
//        try {
//
//            // ===== CARD DATA =====
//
//            String cardName = product.findElement(
//                    By.xpath("//h3[@class='new_in_heading']")
//            ).getText().trim();
//
//            String cardCurrent = product.findElement(
//                    By.xpath(".//span[contains(@class,'prod_current_price')]")
//            ).getText().trim();
//
//            // Actual price
//            List<WebElement> cardActualList = product.findElements(
//                    By.xpath(".//span[contains(@class,'prod_actual_price')]")
//            );
//
//            String cardActual;
//
//            // If actual price is not available, use current price
//            if (cardActualList.isEmpty()) {
//                cardActual = cardCurrent;
//            } else {
//                cardActual = cardActualList.get(0).getText().trim();
//            }
//
//            // Discount
//            String cardDiscount = "0%";
//
//            if (!cardActual.equals(cardCurrent)) {
//
//                int curr = Integer.parseInt(
//                        cardCurrent.replaceAll("[^0-9]", "")
//                );
//
//                int act = Integer.parseInt(
//                        cardActual.replaceAll("[^0-9]", "")
//                );
//
//                cardDiscount = ((act - curr) * 100) / act + "%";
//            }
//
//            System.out.println(
//                    BLUE + "Hover Card: " + cardName + RESET
//            );
//
//            System.out.println(
//                    "Card Current: " + cardCurrent
//                    + " | Actual: " + cardActual
//                    + " | Discount: " + cardDiscount
//            );
//
//            // ===== CLICK PRODUCT =====
//
//            ((JavascriptExecutor) driver)
//                    .executeScript("arguments[0].click();", product);
//
//            // ===== PDP =====
//
//            WebElement pdpNameEl = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(
//                            By.xpath("//h3[@class='prod_name']")
//                    )
//            );
//
//            WebElement pdpCurrentEl = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(
//                            By.xpath("//div[@class='prod_current_price']")
//                    )
//            );
//
//            String pdpName = pdpNameEl.getText().trim();
//
//            String pdpCurrent = pdpCurrentEl.getText().trim();
//
//            // PDP Actual price
//            List<WebElement> pdpActualList = driver.findElements(
//                    By.xpath("//div[@class='prod_actual_price']")
//            );
//
//            String pdpActual;
//
//            // If actual price is not available, use current price
//            if (pdpActualList.isEmpty()) {
//                pdpActual = pdpCurrent;
//            } else {
//                pdpActual = pdpActualList.get(0).getText().trim();
//            }
//
//            // PDP Discount
//            String pdpDiscount = "0%";
//
//            if (!pdpActual.equals(pdpCurrent)) {
//
//                int curr = Integer.parseInt(
//                        pdpCurrent.replaceAll("[^0-9]", "")
//                );
//
//                int act = Integer.parseInt(
//                        pdpActual.replaceAll("[^0-9]", "")
//                );
//
//                pdpDiscount = ((act - curr) * 100) / act + "%";
//            }
//
//            System.out.println(
//                    YELLOW + "PDP Name: " + pdpName + RESET
//            );
//
//            System.out.println(
//                    "PDP Current: " + pdpCurrent
//                    + " | Actual: " + pdpActual
//                    + " | Discount: " + pdpDiscount
//            );
//
//            // ===== VALIDATION =====
//
//            // Price validation
//            if (!cardCurrent.replaceAll("[^0-9]", "")
//                    .equals(pdpCurrent.replaceAll("[^0-9]", ""))) {
//
//                System.out.println(
//                        RED + "Price Mismatch ❌ -> Card: "
//                        + cardCurrent
//                        + " | PDP: "
//                        + pdpCurrent
//                        + RESET
//                );
//
//            } else {
//
//                System.out.println(
//                        GREEN + "Price Match ✅" + RESET
//                );
//            }
//
//            verifiedCount++;
//
//            // ===== BACK NAVIGATION =====
//
//            driver.navigate().back();
//
//            wait.until(
//                    ExpectedConditions.presenceOfElementLocated(sectionLocator)
//            );
//
//            WebElement sectionAgain = driver.findElement(sectionLocator);
//
//            ((JavascriptExecutor) driver)
//                    .executeScript(
//                            "arguments[0].scrollIntoView(true);",
//                            sectionAgain
//                    );
//
//            Thread.sleep(1500);
//
//        } catch (Exception e) {
//
//            System.out.println(
//                    RED + "Error at product index: "
//                    + i
//                    + " -> "
//                    + e.getMessage()
//                    + RESET
//            );
//        }
//    }
//
//    // ===== CLICK VIEW ALL =====
//
//    List<WebElement> viewAll = driver.findElements(viewAllLocator);
//
//    if (!viewAll.isEmpty()) {
//
//        ((JavascriptExecutor) driver)
//                .executeScript(
//                        "arguments[0].click();",
//                        viewAll.get(0)
//                );
//
//        Thread.sleep(1500);
//
//        String heading = driver.findElement(
//                By.xpath("//h2[@class='prod_listing_topic']")
//        ).getText();
//
//        String url = driver.getCurrentUrl();
//
//        System.out.println(
//                BLUE + "View All Page Heading: "
//                + heading
//                + RESET
//        );
//
//        System.out.println(
//                BLUE + "View All Page URL: "
//                + url
//                + RESET
//        );
//    }
//
//    System.out.println(
//            BLUE + "Verified Products Count: "
//            + verifiedCount
//            + "/"
//            + total
//            + RESET
//    );
//
//    if (verifiedCount == hoverLimit) {
//
//        System.out.println(
//                GREEN + "All checked products verified successfully ✅"
//                + RESET
//        );
//
//    } else {
//
//        System.out.println(
//                BLUE + "Some products were skipped or had errors ⏭️"
//                + RESET
//        );
//    }
//
//    // Close browser
//    driver.quit();
//}


//public void verifyNewInProductsCompleteFlow() throws InterruptedException {
//
//    String GREEN  = "\u001B[32m";
//    String RED    = "\u001B[31m";
//    String YELLOW = "\u001B[33m";
//    String BLUE   = "\u001B[34m";
//    String RESET  = "\u001B[0m";
//
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//    Actions actions = new Actions(driver);
//
//    // Open homepage
//    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//
//    // Click Shop button
//    click(zlaataIndiaShopButton);
//
//    By sectionLocator = By.xpath(
//            "//section[@data-section='new_arrivals']"
//    );
//
//    By productLocator = By.xpath(
//            "//section[@data-section='new_arrivals']//a[contains(@class,'banner_link')]"
//    );
//
//    By viewAllLocator = By.xpath(
//            "//a[contains(@class,'new_in_redirect')]"
//    );
//
//    // Scroll to NEW IN section
//    WebElement section = wait.until(
//            ExpectedConditions.presenceOfElementLocated(sectionLocator)
//    );
//
//    ((JavascriptExecutor) driver)
//            .executeScript(
//                    "arguments[0].scrollIntoView(true);",
//                    section
//            );
//
//    Thread.sleep(1000);
//
//    List<WebElement> products = wait.until(
//            ExpectedConditions.presenceOfAllElementsLocatedBy(productLocator)
//    );
//
//    int total = products.size();
//    int verifiedCount = 0;
//
//    System.out.println(
//            BLUE + "Total Products: " + total + RESET
//    );
//
//    // Verify ALL products
//    for (int i = 0; i < total; i++) {
//
//        try {
//
//            // Re-fetch products after returning from PDP
//            products = wait.until(
//                    ExpectedConditions.presenceOfAllElementsLocatedBy(
//                            productLocator
//                    )
//            );
//
//            WebElement product = products.get(i);
//
//            // Scroll to product
//            ((JavascriptExecutor) driver)
//                    .executeScript(
//                            "arguments[0].scrollIntoView({block:'center'});",
//                            product
//                    );
//
//            // Hover
//            actions.moveToElement(product).perform();
//
//            Thread.sleep(1500);
//
//            // ===== CARD DATA =====
//
//            String cardName = product.findElement(
//                    By.xpath(".//h3[@class='new_in_heading']")
//            ).getText().trim();
//
//            String cardCurrent = product.findElement(
//                    By.xpath(".//span[contains(@class,'prod_current_price')]")
//            ).getText().trim();
//
//            // Actual price
//            List<WebElement> cardActualList = product.findElements(
//                    By.xpath(".//span[contains(@class,'prod_actual_price')]")
//            );
//
//            String cardActual;
//
//            if (cardActualList.isEmpty()) {
//                cardActual = cardCurrent;
//            } else {
//                cardActual = cardActualList.get(0).getText().trim();
//            }
//
//            // Discount
//            String cardDiscount = "0%";
//
//            if (!cardActual.equals(cardCurrent)) {
//
//                int curr = Integer.parseInt(
//                        cardCurrent.replaceAll("[^0-9]", "")
//                );
//
//                int act = Integer.parseInt(
//                        cardActual.replaceAll("[^0-9]", "")
//                );
//
//                cardDiscount = ((act - curr) * 100) / act + "%";
//            }
//
//            System.out.println(
//                    BLUE + "Hover Card: " + cardName + RESET
//            );
//
//            System.out.println(
//                    "Card Current: " + cardCurrent
//                            + " | Actual: " + cardActual
//                            + " | Discount: " + cardDiscount
//            );
//
//            // ===== CLICK PRODUCT =====
//
//            ((JavascriptExecutor) driver)
//                    .executeScript(
//                            "arguments[0].click();",
//                            product
//                    );
//
//            // ===== PDP =====
//
//            WebElement pdpNameEl = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(
//                            By.xpath("//h3[@class='prod_name']")
//                    )
//            );
//
//            WebElement pdpCurrentEl = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(
//                            By.xpath("//div[@class='prod_current_price']")
//                    )
//            );
//
//            String pdpName = pdpNameEl.getText().trim();
//
//            String pdpCurrent = pdpCurrentEl.getText().trim();
//
//            // PDP Actual price
//            List<WebElement> pdpActualList = driver.findElements(
//                    By.xpath("//div[@class='prod_actual_price']")
//            );
//
//            String pdpActual;
//
//            if (pdpActualList.isEmpty()) {
//                pdpActual = pdpCurrent;
//            } else {
//                pdpActual = pdpActualList.get(0).getText().trim();
//            }
//
//            // PDP Discount
//            String pdpDiscount = "0%";
//
//            if (!pdpActual.equals(pdpCurrent)) {
//
//                int curr = Integer.parseInt(
//                        pdpCurrent.replaceAll("[^0-9]", "")
//                );
//
//                int act = Integer.parseInt(
//                        pdpActual.replaceAll("[^0-9]", "")
//                );
//
//                pdpDiscount = ((act - curr) * 100) / act + "%";
//            }
//
//            System.out.println(
//                    YELLOW + "PDP Name: " + pdpName + RESET
//            );
//
//            System.out.println(
//                    "PDP Current: " + pdpCurrent
//                            + " | Actual: " + pdpActual
//                            + " | Discount: " + pdpDiscount
//            );
//
//            // ===== VALIDATION =====
//
//            // Validate Product Name
//            if (!cardName.equalsIgnoreCase(pdpName)) {
//
//                System.out.println(
//                        RED + "Product Name Mismatch ❌ -> Card: "
//                                + cardName
//                                + " | PDP: "
//                                + pdpName
//                                + RESET
//                );
//
//            } else {
//
//                System.out.println(
//                        GREEN + "Product Name Match ✅" + RESET
//                );
//            }
//
//            // Validate Price
//            if (!cardCurrent.replaceAll("[^0-9]", "")
//                    .equals(pdpCurrent.replaceAll("[^0-9]", ""))) {
//
//                System.out.println(
//                        RED + "Price Mismatch ❌ -> Card: "
//                                + cardCurrent
//                                + " | PDP: "
//                                + pdpCurrent
//                                + RESET
//                );
//
//            } else {
//
//                System.out.println(
//                        GREEN + "Price Match ✅" + RESET
//                );
//            }
//
//            verifiedCount++;
//
//            // ===== BACK NAVIGATION =====
//
//            driver.navigate().back();
//
//            wait.until(
//                    ExpectedConditions.presenceOfElementLocated(
//                            sectionLocator
//                    )
//            );
//
//            WebElement sectionAgain = driver.findElement(
//                    sectionLocator
//            );
//
//            ((JavascriptExecutor) driver)
//                    .executeScript(
//                            "arguments[0].scrollIntoView(true);",
//                            sectionAgain
//                    );
//
//            Thread.sleep(1500);
//
//        } catch (Exception e) {
//
//            System.out.println(
//                    RED + "Error at product index: "
//                            + i
//                            + " -> "
//                            + e.getMessage()
//                            + RESET
//            );
//        }
//    }
//
//    // ===== CLICK VIEW ALL =====
//
//    List<WebElement> viewAll = driver.findElements(
//            viewAllLocator
//    );
//
//    if (!viewAll.isEmpty()) {
//
//        ((JavascriptExecutor) driver)
//                .executeScript(
//                        "arguments[0].click();",
//                        viewAll.get(0)
//                );
//
//        Thread.sleep(1500);
//
//        String heading = driver.findElement(
//                By.xpath("//h2[@class='prod_listing_topic']")
//        ).getText();
//
//        String url = driver.getCurrentUrl();
//
//        System.out.println(
//                BLUE + "View All Page Heading: "
//                        + heading
//                        + RESET
//        );
//
//        System.out.println(
//                BLUE + "View All Page URL: "
//                        + url
//                        + RESET
//        );
//    }
//
//    // ===== FINAL RESULT =====
//
//    System.out.println(
//            BLUE + "Verified Products Count: "
//                    + verifiedCount
//                    + "/"
//                    + total
//                    + RESET
//    );
//
//    if (verifiedCount == total) {
//
//        System.out.println(
//                GREEN
//                        + "All products verified successfully ✅"
//                        + RESET
//        );
//
//    } else {
//
//        System.out.println(
//                RED
//                        + "Some products were not verified ❌"
//                        + RESET
//        );
//    }
//
//    // Close browser
//    driver.quit();
//}


//public void verifyNewInProductsCompleteFlow() throws InterruptedException {
//
//    String GREEN  = "\u001B[32m";
//    String RED    = "\u001B[31m";
//    String YELLOW = "\u001B[33m";
//    String BLUE   = "\u001B[34m";
//    String RESET  = "\u001B[0m";
//
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//    Actions actions = new Actions(driver);
//
//    // =========================================================
//    // OPEN HOMEPAGE
//    // =========================================================
//
//    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//
//    // Click Shop button
//    click(zlaataIndiaShopButton);
//
//    By sectionLocator = By.xpath(
//            "//section[@data-section='new_arrivals']"
//    );
//
//    By productLocator = By.xpath(
//            "//section[@data-section='new_arrivals']"
//                    + "//a[contains(@class,'banner_link')]"
//    );
//
//    By viewAllLocator = By.xpath(
//            "//section[@data-section='new_arrivals']"
//                    + "//a[contains(@class,'new_in_redirect')]"
//    );
//
//    // =========================================================
//    // SCROLL TO NEW IN SECTION
//    // =========================================================
//
//    WebElement section = wait.until(
//            ExpectedConditions.presenceOfElementLocated(sectionLocator)
//    );
//
//    ((JavascriptExecutor) driver)
//            .executeScript(
//                    "arguments[0].scrollIntoView(true);",
//                    section
//            );
//
//    Thread.sleep(1000);
//
//    // =========================================================
//    // GET PRODUCTS
//    // =========================================================
//
//    List<WebElement> products = wait.until(
//            ExpectedConditions.presenceOfAllElementsLocatedBy(productLocator)
//    );
//
//    int total = products.size();
//
//    /*
//     * The last element is View All.
//     * Only the first 7 products should be verified.
//     */
//    int verifyCount = Math.min(total - 1, 7);
//
//    int verifiedCount = 0;
//
//    System.out.println(
//            BLUE + "Total New In Products to Verify: "
//                    + verifyCount
//                    + RESET
//    );
//
//    // =========================================================
//    // VERIFY PRODUCTS
//    // =========================================================
//
//    for (int i = 0; i < verifyCount; i++) {
//
//        try {
//
//            // Re-fetch products after returning from PDP
//            products = wait.until(
//                    ExpectedConditions.presenceOfAllElementsLocatedBy(
//                            productLocator
//                    )
//            );
//
//            WebElement product = products.get(i);
//
//            // =================================================
//            // SCROLL TO PRODUCT
//            // =================================================
//
//            ((JavascriptExecutor) driver)
//                    .executeScript(
//                            "arguments[0].scrollIntoView({block:'center'});",
//                            product
//                    );
//
//            // Hover product
//            actions.moveToElement(product).perform();
//
//            Thread.sleep(1500);
//
//            // =================================================
//            // CARD DATA
//            // =================================================
//
//            String cardName = product.findElement(
//                    By.xpath(".//h3[@class='new_in_heading']")
//            ).getText().trim();
//
//            // -------------------------------------------------
//            // CURRENT PRICE
//            // -------------------------------------------------
//
//            List<WebElement> cardCurrentList = product.findElements(
//                    By.xpath(".//span[contains(@class,'prod_current_price')]")
//            );
//
//            String cardCurrent = "";
//
//            if (!cardCurrentList.isEmpty()) {
//
//                cardCurrent = cardCurrentList.get(0)
//                        .getText()
//                        .trim();
//            }
//
//            // -------------------------------------------------
//            // ACTUAL PRICE
//            // -------------------------------------------------
//
//            List<WebElement> cardActualList = product.findElements(
//                    By.xpath(".//span[contains(@class,'prod_actual_price')]")
//            );
//
//            String cardActual;
//
//            if (cardActualList.isEmpty() || cardCurrent.isEmpty()) {
//
//                cardActual = cardCurrent;
//
//            } else {
//
//                cardActual = cardActualList.get(0)
//                        .getText()
//                        .trim();
//            }
//
//            // -------------------------------------------------
//            // DISCOUNT
//            // -------------------------------------------------
//
//            String cardDiscount = "0%";
//
//            if (!cardCurrent.isEmpty()
//                    && !cardActual.isEmpty()
//                    && !cardActual.equals(cardCurrent)) {
//
//                int curr = Integer.parseInt(
//                        cardCurrent.replaceAll("[^0-9]", "")
//                );
//
//                int act = Integer.parseInt(
//                        cardActual.replaceAll("[^0-9]", "")
//                );
//
//                cardDiscount = ((act - curr) * 100) / act + "%";
//            }
//
//            // =================================================
//            // PRINT CARD DATA
//            // =================================================
//
//            System.out.println(
//                    BLUE + "----------------------------------------"
//                            + RESET
//            );
//
//            System.out.println(
//                    BLUE + "Product "
//                            + (i + 1)
//                            + ": "
//                            + cardName
//                            + RESET
//            );
//
//            System.out.println(
//                    "Card Current: "
//                            + (cardCurrent.isEmpty()
//                                    ? "Not Visible"
//                                    : cardCurrent)
//                            + " | Actual: "
//                            + (cardActual.isEmpty()
//                                    ? "Not Visible"
//                                    : cardActual)
//                            + " | Discount: "
//                            + cardDiscount
//            );
//
//            // =================================================
//            // CLICK PRODUCT
//            // =================================================
//
//            ((JavascriptExecutor) driver)
//                    .executeScript(
//                            "arguments[0].click();",
//                            product
//                    );
//
//            // =================================================
//            // PDP DATA
//            // =================================================
//
//            WebElement pdpNameEl = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(
//                            By.xpath("//h3[@class='prod_name']")
//                    )
//            );
//
//            String pdpName = pdpNameEl
//                    .getText()
//                    .trim();
//
//            // -------------------------------------------------
//            // PDP CURRENT PRICE
//            // -------------------------------------------------
//
//            List<WebElement> pdpCurrentList = driver.findElements(
//                    By.xpath("//div[@class='prod_current_price']")
//            );
//
//            String pdpCurrent = "";
//
//            if (!pdpCurrentList.isEmpty()) {
//
//                pdpCurrent = pdpCurrentList.get(0)
//                        .getText()
//                        .trim();
//            }
//
//            // -------------------------------------------------
//            // PDP ACTUAL PRICE
//            // -------------------------------------------------
//
//            List<WebElement> pdpActualList = driver.findElements(
//                    By.xpath("//div[@class='prod_actual_price']")
//            );
//
//            String pdpActual;
//
//            if (pdpActualList.isEmpty() || pdpCurrent.isEmpty()) {
//
//                pdpActual = pdpCurrent;
//
//            } else {
//
//                pdpActual = pdpActualList.get(0)
//                        .getText()
//                        .trim();
//            }
//
//            // -------------------------------------------------
//            // PDP DISCOUNT
//            // -------------------------------------------------
//
//            String pdpDiscount = "0%";
//
//            if (!pdpCurrent.isEmpty()
//                    && !pdpActual.isEmpty()
//                    && !pdpActual.equals(pdpCurrent)) {
//
//                int curr = Integer.parseInt(
//                        pdpCurrent.replaceAll("[^0-9]", "")
//                );
//
//                int act = Integer.parseInt(
//                        pdpActual.replaceAll("[^0-9]", "")
//                );
//
//                pdpDiscount = ((act - curr) * 100) / act + "%";
//            }
//
//            // =================================================
//            // PRINT PDP DATA
//            // =================================================
//
//            System.out.println(
//                    YELLOW + "PDP Name: "
//                            + pdpName
//                            + RESET
//            );
//
//            System.out.println(
//                    "PDP Current: "
//                            + (pdpCurrent.isEmpty()
//                                    ? "Not Visible"
//                                    : pdpCurrent)
//                            + " | Actual: "
//                            + (pdpActual.isEmpty()
//                                    ? "Not Visible"
//                                    : pdpActual)
//                            + " | Discount: "
//                            + pdpDiscount
//            );
//
//            // =================================================
//            // VALIDATE PRODUCT NAME
//            // =================================================
//
//            if (!cardName.equalsIgnoreCase(pdpName)) {
//
//                System.out.println(
//                        RED + "Product Name Mismatch ❌ -> Card: "
//                                + cardName
//                                + " | PDP: "
//                                + pdpName
//                                + RESET
//                );
//
//            } else {
//
//                System.out.println(
//                        GREEN + "Product Name Match ✅"
//                                + RESET
//                );
//            }
//
//            // =================================================
//            // VALIDATE PRICE
//            // =================================================
//
//            if (cardCurrent.isEmpty() || pdpCurrent.isEmpty()) {
//
//                System.out.println(
//                        YELLOW
//                                + "Price not visible. Price validation skipped."
//                                + RESET
//                );
//
//            } else if (!cardCurrent
//                    .replaceAll("[^0-9]", "")
//                    .equals(
//                            pdpCurrent.replaceAll("[^0-9]", "")
//                    )) {
//
//                System.out.println(
//                        RED + "Price Mismatch ❌ -> Card: "
//                                + cardCurrent
//                                + " | PDP: "
//                                + pdpCurrent
//                                + RESET
//                );
//
//            } else {
//
//                System.out.println(
//                        GREEN + "Price Match ✅"
//                                + RESET
//                );
//            }
//
//            verifiedCount++;
//
//            // =================================================
//            // BACK TO NEW IN SECTION
//            // =================================================
//
//            driver.navigate().back();
//
//            wait.until(
//                    ExpectedConditions.presenceOfElementLocated(
//                            sectionLocator
//                    )
//            );
//
//            WebElement sectionAgain = driver.findElement(
//                    sectionLocator
//            );
//
//            ((JavascriptExecutor) driver)
//                    .executeScript(
//                            "arguments[0].scrollIntoView(true);",
//                            sectionAgain
//                    );
//
//            Thread.sleep(1500);
//
//        } catch (Exception e) {
//
//            System.out.println(
//                    RED + "Error at product index: "
//                            + i
//                            + " -> "
//                            + e.getMessage()
//                            + RESET
//            );
//        }
//    }
//
//    // =========================================================
//    // VIEW ALL - DO NOT VERIFY AS PRODUCT
//    // =========================================================
//
//    List<WebElement> viewAll = driver.findElements(
//            viewAllLocator
//    );
//
//    if (!viewAll.isEmpty()) {
//
//        System.out.println(
//                BLUE + "View All button is available."
//                        + RESET
//        );
//
//        ((JavascriptExecutor) driver)
//                .executeScript(
//                        "arguments[0].scrollIntoView({block:'center'});",
//                        viewAll.get(0)
//                );
//
//        Thread.sleep(500);
//
//        // Click View All
//        ((JavascriptExecutor) driver)
//                .executeScript(
//                        "arguments[0].click();",
//                        viewAll.get(0)
//                );
//
//        Thread.sleep(1500);
//
//        String heading = driver.findElement(
//                By.xpath("//h2[@class='prod_listing_topic']")
//        ).getText();
//
//        String url = driver.getCurrentUrl();
//
//        System.out.println(
//                BLUE + "View All Page Heading: "
//                        + heading
//                        + RESET
//        );
//
//        System.out.println(
//                BLUE + "View All Page URL: "
//                        + url
//                        + RESET
//        );
//
//    } else {
//
//        System.out.println(
//                YELLOW + "View All button is not available."
//                        + RESET
//        );
//    }
//
//    // =========================================================
//    // FINAL RESULT
//    // =========================================================
//
//    System.out.println(
//            BLUE + "========================================"
//                    + RESET
//    );
//
//    System.out.println(
//            BLUE + "Verified New In Products: "
//                    + verifiedCount
//                    + "/"
//                    + verifyCount
//                    + RESET
//    );
//
//    if (verifiedCount == verifyCount) {
//
//        System.out.println(
//                GREEN
//                        + "All 7 New In products verified successfully ✅"
//                        + RESET
//        );
//
//    } else {
//
//        System.out.println(
//                RED
//                        + "Some New In products were not verified ❌"
//                        + RESET
//        );
//    }
//
//    // =========================================================
//    // CLOSE BROWSER
//    // =========================================================
//
//    driver.quit();
//}

//public void verifyNewInProductsCompleteFlow() throws InterruptedException {
//
//    String GREEN  = "\u001B[32m";
//    String RED    = "\u001B[31m";
//    String YELLOW = "\u001B[33m";
//    String BLUE   = "\u001B[34m";
//    String RESET  = "\u001B[0m";
//
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//    Actions actions = new Actions(driver);
//
//    // =========================================================
//    // OPEN HOME PAGE
//    // =========================================================
//
//    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//
//    click(zlaataIndiaShopButton);
//
//    By sectionLocator = By.xpath(
//            "//section[@data-section='new_arrivals']"
//    );
//
//    By productLocator = By.xpath(
//            "//section[@data-section='new_arrivals']"
//                    + "//a[contains(@class,'banner_link')]"
//    );
//
//    By viewAllLocator = By.xpath(
//            "//section[@data-section='new_arrivals']"
//                    + "//a[contains(@class,'new_in_redirect')]"
//    );
//
//    // =========================================================
//    // SCROLL TO NEW ARRIVALS SECTION
//    // =========================================================
//
//    WebElement section = wait.until(
//            ExpectedConditions.presenceOfElementLocated(sectionLocator)
//    );
//
//    ((JavascriptExecutor) driver).executeScript(
//            "arguments[0].scrollIntoView(true);",
//            section
//    );
//
//    Thread.sleep(1000);
//
//    // =========================================================
//    // GET NEW ARRIVALS PRODUCTS
//    // =========================================================
//
//    List<WebElement> products = wait.until(
//            ExpectedConditions.presenceOfAllElementsLocatedBy(productLocator)
//    );
//
//    int total = products.size();
//
//    // Last element is View All.
//    // Verify only first 7 products.
//    int verifyCount = Math.min(total - 1, 7);
//
//    int verifiedCount = 0;
//
//    System.out.println(
//            BLUE + "Total New Arrivals Products to Verify: "
//                    + verifyCount
//                    + RESET
//    );
//
//    // =========================================================
//    // VERIFY FIRST 7 PRODUCTS
//    // =========================================================
//
//    for (int i = 0; i < verifyCount; i++) {
//
//        try {
//
//            // Re-fetch products after returning from PDP
//            products = wait.until(
//                    ExpectedConditions.presenceOfAllElementsLocatedBy(
//                            productLocator
//                    )
//            );
//
//            WebElement product = products.get(i);
//
//            // =================================================
//            // SCROLL TO PRODUCT
//            // =================================================
//
//            ((JavascriptExecutor) driver).executeScript(
//                    "arguments[0].scrollIntoView({block:'center'});",
//                    product
//            );
//
//            actions.moveToElement(product).perform();
//
//            Thread.sleep(1500);
//
//            // =================================================
//            // HOME PAGE / NEW ARRIVALS DATA
//            // =================================================
//
//            String homeProductName = product.findElement(
//                    By.xpath(".//h3[@class='new_in_heading']")
//            ).getText().trim();
//
//            // -------------------------------------------------
//            // HOME PAGE CURRENT PRICE
//            // -------------------------------------------------
//
//            List<WebElement> homeCurrentList = product.findElements(
//                    By.xpath(".//span[contains(@class,'prod_current_price')]")
//            );
//
//            String homeCurrent = "";
//
//            if (!homeCurrentList.isEmpty()) {
//
//                homeCurrent = homeCurrentList.get(0)
//                        .getText()
//                        .trim();
//            }
//
//            // -------------------------------------------------
//            // HOME PAGE ACTUAL PRICE
//            // -------------------------------------------------
//
//            List<WebElement> homeActualList = product.findElements(
//                    By.xpath(".//span[contains(@class,'prod_actual_price')]")
//            );
//
//            String homeActual;
//
//            if (homeActualList.isEmpty() || homeCurrent.isEmpty()) {
//
//                homeActual = homeCurrent;
//
//            } else {
//
//                homeActual = homeActualList.get(0)
//                        .getText()
//                        .trim();
//            }
//
//            // =================================================
//            // PRINT HOME PAGE DATA
//            // NO DISCOUNT PERCENTAGE HERE
//            // =================================================
//
//            System.out.println(
//                    BLUE + "----------------------------------------"
//                            + RESET
//            );
//
//            System.out.println(
//                    BLUE + "New Arrivals Product "
//                            + (i + 1)
//                            + ": "
//                            + homeProductName
//                            + RESET
//            );
//
//            System.out.println(
//                    "Home Page Current Price: "
//                            + (homeCurrent.isEmpty()
//                                    ? "Not Visible"
//                                    : homeCurrent)
//                            + " | Actual Price: "
//                            + (homeActual.isEmpty()
//                                    ? "Not Visible"
//                                    : homeActual)
//            );
//
//            // =================================================
//            // CLICK PRODUCT
//            // =================================================
//
//            ((JavascriptExecutor) driver).executeScript(
//                    "arguments[0].click();",
//                    product
//            );
//
//            // =================================================
//            // PDP PRODUCT NAME
//            // =================================================
//
//            WebElement pdpNameElement = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(
//                            By.xpath("//h3[@class='prod_name']")
//                    )
//            );
//
//            String pdpProductName = pdpNameElement
//                    .getText()
//                    .trim();
//
//            // =================================================
//            // PDP CURRENT PRICE
//            // =================================================
//
//            List<WebElement> pdpCurrentList = driver.findElements(
//                    By.xpath("//div[@class='prod_current_price']")
//            );
//
//            String pdpCurrent = "";
//
//            if (!pdpCurrentList.isEmpty()) {
//
//                pdpCurrent = pdpCurrentList.get(0)
//                        .getText()
//                        .trim();
//            }
//
//            // =================================================
//            // PDP ACTUAL PRICE
//            // =================================================
//
//            List<WebElement> pdpActualList = driver.findElements(
//                    By.xpath("//div[@class='prod_actual_price']")
//            );
//
//            String pdpActual;
//
//            if (pdpActualList.isEmpty() || pdpCurrent.isEmpty()) {
//
//                pdpActual = pdpCurrent;
//
//            } else {
//
//                pdpActual = pdpActualList.get(0)
//                        .getText()
//                        .trim();
//            }
//
//            // =================================================
//            // PDP DISCOUNT PERCENTAGE
//            // =================================================
//
//            String pdpDiscount = "0%";
//
//            if (!pdpCurrent.isEmpty()
//                    && !pdpActual.isEmpty()
//                    && !pdpActual.equals(pdpCurrent)) {
//
//                String currentNumber = pdpCurrent
//                        .replaceAll("[^0-9]", "");
//
//                String actualNumber = pdpActual
//                        .replaceAll("[^0-9]", "");
//
//                if (!currentNumber.isEmpty()
//                        && !actualNumber.isEmpty()) {
//
//                    int currentPrice = Integer.parseInt(currentNumber);
//                    int actualPrice = Integer.parseInt(actualNumber);
//
//                    if (actualPrice > 0) {
//
//                        pdpDiscount =
//                                ((actualPrice - currentPrice) * 100)
//                                        / actualPrice
//                                        + "%";
//                    }
//                }
//            }
//
//            // =================================================
//            // PRINT PDP DATA
//            // PDP DISCOUNT IS DISPLAYED HERE
//            // =================================================
//
//            System.out.println(
//                    YELLOW + "PDP Product Name: "
//                            + pdpProductName
//                            + RESET
//            );
//
//            System.out.println(
//                    "PDP Current Price: "
//                            + (pdpCurrent.isEmpty()
//                                    ? "Not Visible"
//                                    : pdpCurrent)
//                            + " | Actual Price: "
//                            + (pdpActual.isEmpty()
//                                    ? "Not Visible"
//                                    : pdpActual)
//                            + " | Discount: "
//                            + pdpDiscount
//            );
//
//            // =================================================
//            // FAIL IF DISCOUNT IS AVAILABLE ON PDP
//            // =================================================
//
//            if (!pdpDiscount.equals("0%")) {
//
//                System.out.println(
//                        RED + "Discount Percentage is displayed on PDP: "
//                                + pdpDiscount
//                                + " ❌"
//                                + RESET
//                );
//
//                Assert.fail(
//                        "Discount percentage is displayed on PDP: "
//                                + pdpDiscount
//                );
//
//            } else {
//
//                System.out.println(
//                        GREEN
//                                + "No Discount Percentage displayed on PDP ✅"
//                                + RESET
//                );
//            }
//
//            // =================================================
//            // VALIDATE PRODUCT NAME
//            // =================================================
//
//            if (!homeProductName.equalsIgnoreCase(pdpProductName)) {
//
//                System.out.println(
//                        RED + "Product Name Mismatch ❌ -> Home Page: "
//                                + homeProductName
//                                + " | PDP: "
//                                + pdpProductName
//                                + RESET
//                );
//
//                Assert.fail(
//                        "Product Name mismatch. Home Page: "
//                                + homeProductName
//                                + " | PDP: "
//                                + pdpProductName
//                );
//
//            } else {
//
//                System.out.println(
//                        GREEN + "Product Name Match ✅"
//                                + RESET
//                );
//            }
//
//            // =================================================
//            // VALIDATE PRICE
//            // =================================================
//
//            if (homeCurrent.isEmpty() || pdpCurrent.isEmpty()) {
//
//                System.out.println(
//                        YELLOW
//                                + "Price not visible. Price validation skipped."
//                                + RESET
//                );
//
//            } else {
//
//                String homePriceNumber = homeCurrent
//                        .replaceAll("[^0-9]", "");
//
//                String pdpPriceNumber = pdpCurrent
//                        .replaceAll("[^0-9]", "");
//
//                if (!homePriceNumber.equals(pdpPriceNumber)) {
//
//                    System.out.println(
//                            RED + "Price Mismatch ❌ -> Home Page: "
//                                    + homeCurrent
//                                    + " | PDP: "
//                                    + pdpCurrent
//                                    + RESET
//                    );
//
//                    Assert.fail(
//                            "Price mismatch. Home Page: "
//                                    + homeCurrent
//                                    + " | PDP: "
//                                    + pdpCurrent
//                    );
//
//                } else {
//
//                    System.out.println(
//                            GREEN + "Price Match ✅"
//                                    + RESET
//                    );
//                }
//            }
//
//            verifiedCount++;
//
//            // =================================================
//            // BACK TO NEW ARRIVALS
//            // =================================================
//
//            driver.navigate().back();
//
//            wait.until(
//                    ExpectedConditions.presenceOfElementLocated(
//                            sectionLocator
//                    )
//            );
//
//            WebElement sectionAgain = driver.findElement(
//                    sectionLocator
//            );
//
//            ((JavascriptExecutor) driver).executeScript(
//                    "arguments[0].scrollIntoView(true);",
//                    sectionAgain
//            );
//
//            Thread.sleep(1500);
//
//        } catch (AssertionError e) {
//
//            System.out.println(
//                    RED + "Test Case Failed at Product "
//                            + (i + 1)
//                            + " -> "
//                            + e.getMessage()
//                            + RESET
//            );
//
//            throw e;
//
//        } catch (Exception e) {
//
//            System.out.println(
//                    RED + "Error at Product "
//                            + (i + 1)
//                            + " -> "
//                            + e.getMessage()
//                            + RESET
//            );
//
//            Assert.fail(
//                    "Error while verifying Product "
//                            + (i + 1)
//                            + ": "
//                            + e.getMessage()
//            );
//        }
//    }
//
//    // =========================================================
//    // VIEW ALL - DO NOT VERIFY AS PRODUCT
//    // =========================================================
//
//    List<WebElement> viewAll = driver.findElements(
//            viewAllLocator
//    );
//
//    if (!viewAll.isEmpty()) {
//
//        System.out.println(
//                BLUE + "View All button is available."
//                        + RESET
//        );
//
//        ((JavascriptExecutor) driver).executeScript(
//                "arguments[0].scrollIntoView({block:'center'});",
//                viewAll.get(0)
//        );
//
//        Thread.sleep(500);
//
//        // Click View All
//        ((JavascriptExecutor) driver).executeScript(
//                "arguments[0].click();",
//                viewAll.get(0)
//        );
//
//        Thread.sleep(1500);
//
//        String heading = driver.findElement(
//                By.xpath("//h2[@class='prod_listing_topic']")
//        ).getText();
//
//        String url = driver.getCurrentUrl();
//
//        System.out.println(
//                BLUE + "View All Page Heading: "
//                        + heading
//                        + RESET
//        );
//
//        System.out.println(
//                BLUE + "View All Page URL: "
//                        + url
//                        + RESET
//        );
//
//    } else {
//
//        System.out.println(
//                YELLOW + "View All button is not available."
//                        + RESET
//        );
//    }
//
//    // =========================================================
//    // FINAL RESULT
//    // =========================================================
//
//    System.out.println(
//            BLUE + "========================================"
//                    + RESET
//    );
//
//    System.out.println(
//            BLUE + "Verified New Arrivals Products: "
//                    + verifiedCount
//                    + "/"
//                    + verifyCount
//                    + RESET
//    );
//
//    if (verifiedCount == verifyCount) {
//
//        System.out.println(
//                GREEN
//                        + "All 7 New Arrivals products verified successfully ✅"
//                        + RESET
//        );
//
//    } else {
//
//        System.out.println(
//                RED
//                        + "Some New Arrivals products were not verified ❌"
//                        + RESET
//        );
//    }
//
//    // =========================================================
//    // CLOSE BROWSER
//    // =========================================================
//
//    driver.quit();
//}


//public void verifyNewInProductsCompleteFlow() throws InterruptedException {
//
//    String GREEN  = "\u001B[32m";
//    String RED    = "\u001B[31m";
//    String YELLOW = "\u001B[33m";
//    String BLUE   = "\u001B[34m";
//    String RESET  = "\u001B[0m";
//
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//    Actions actions = new Actions(driver);
//
//    // =========================================================
//    // OPEN HOME PAGE
//    // =========================================================
//
//    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//
//    click(zlaataIndiaShopButton);
//
//    By sectionLocator = By.xpath(
//            "//section[@data-section='new_arrivals']"
//    );
//
//    By productLocator = By.xpath(
//            "//section[@data-section='new_arrivals']"
//                    + "//a[contains(@class,'banner_link')]"
//    );
//
//    By viewAllLocator = By.xpath(
//            "//section[@data-section='new_arrivals']"
//                    + "//a[contains(@class,'new_in_redirect')]"
//    );
//
//    // =========================================================
//    // SCROLL TO NEW ARRIVALS SECTION
//    // =========================================================
//
//    WebElement section = wait.until(
//            ExpectedConditions.presenceOfElementLocated(sectionLocator)
//    );
//
//    ((JavascriptExecutor) driver).executeScript(
//            "arguments[0].scrollIntoView(true);",
//            section
//    );
//
//    Thread.sleep(1000);
//
//    // =========================================================
//    // GET NEW ARRIVALS PRODUCTS
//    // =========================================================
//
//    List<WebElement> products = wait.until(
//            ExpectedConditions.presenceOfAllElementsLocatedBy(productLocator)
//    );
//
//    int total = products.size();
//
//    // Last element is View All.
//    // Verify only first 7 products.
//
//    int verifyCount = Math.min(Math.max(total - 1, 0), 7);
//
//    int verifiedCount = 0;
//
//    System.out.println(
//            BLUE + "Total New Arrivals Products to Verify: "
//                    + verifyCount
//                    + RESET
//    );
//
//    // =========================================================
//    // VERIFY FIRST 7 PRODUCTS
//    // =========================================================
//
//    for (int i = 0; i < verifyCount; i++) {
//
//        try {
//
//            // Re-fetch products after returning from PDP
//            products = wait.until(
//                    ExpectedConditions.presenceOfAllElementsLocatedBy(
//                            productLocator
//                    )
//            );
//
//            WebElement product = products.get(i);
//
//            // =================================================
//            // SCROLL TO PRODUCT
//            // =================================================
//
//            ((JavascriptExecutor) driver).executeScript(
//                    "arguments[0].scrollIntoView({block:'center'});",
//                    product
//            );
//
//            actions.moveToElement(product).perform();
//
//            Thread.sleep(1500);
//
//            // =================================================
//            // HOME PAGE / NEW ARRIVALS DATA
//            // =================================================
//
//            String homeProductName = product.findElement(
//                    By.xpath(".//h3[@class='new_in_heading']")
//            ).getText().trim();
//
//            // -------------------------------------------------
//            // HOME PAGE CURRENT PRICE
//            // -------------------------------------------------
//
//            List<WebElement> homeCurrentList = product.findElements(
//                    By.xpath(".//span[contains(@class,'prod_current_price')]")
//            );
//
//            String homeCurrent = "";
//
//            if (!homeCurrentList.isEmpty()) {
//
//                homeCurrent = homeCurrentList.get(0)
//                        .getText()
//                        .trim();
//            }
//
//            // -------------------------------------------------
//            // HOME PAGE ACTUAL PRICE
//            // -------------------------------------------------
//
//            List<WebElement> homeActualList = product.findElements(
//                    By.xpath(".//span[contains(@class,'prod_actual_price')]")
//            );
//
//            String homeActual;
//
//            if (homeActualList.isEmpty() || homeCurrent.isEmpty()) {
//
//                homeActual = homeCurrent;
//
//            } else {
//
//                homeActual = homeActualList.get(0)
//                        .getText()
//                        .trim();
//            }
//
//            // =================================================
//            // PRINT HOME PAGE DATA
//            // DISCOUNT PERCENTAGE SHOULD NOT BE DISPLAYED HERE
//            // =================================================
//
//            System.out.println(
//                    BLUE + "----------------------------------------"
//                            + RESET
//            );
//
//            System.out.println(
//                    BLUE + "New Arrivals Product "
//                            + (i + 1)
//                            + ": "
//                            + homeProductName
//                            + RESET
//            );
//
//            System.out.println(
//                    "Home Page Current Price: "
//                            + (homeCurrent.isEmpty()
//                                    ? "Not Visible"
//                                    : homeCurrent)
//                            + " | Actual Price: "
//                            + (homeActual.isEmpty()
//                                    ? "Not Visible"
//                                    : homeActual)
//            );
//
//            // =================================================
//            // CLICK PRODUCT
//            // =================================================
//
//            ((JavascriptExecutor) driver).executeScript(
//                    "arguments[0].click();",
//                    product
//            );
//
//            // =================================================
//            // PDP PRODUCT NAME
//            // =================================================
//
//            WebElement pdpNameElement = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(
//                            By.xpath("//h3[@class='prod_name']")
//                    )
//            );
//
//            String pdpProductName = pdpNameElement
//                    .getText()
//                    .trim();
//
//            // =================================================
//            // PDP CURRENT PRICE
//            // =================================================
//
//            List<WebElement> pdpCurrentList = driver.findElements(
//                    By.xpath("//div[@class='prod_current_price']")
//            );
//
//            String pdpCurrent = "";
//
//            if (!pdpCurrentList.isEmpty()) {
//
//                pdpCurrent = pdpCurrentList.get(0)
//                        .getText()
//                        .trim();
//            }
//
//            // =================================================
//            // PDP ACTUAL PRICE
//            // =================================================
//
//            List<WebElement> pdpActualList = driver.findElements(
//                    By.xpath("//div[@class='prod_actual_price']")
//            );
//
//            String pdpActual;
//
//            if (pdpActualList.isEmpty() || pdpCurrent.isEmpty()) {
//
//                pdpActual = pdpCurrent;
//
//            } else {
//
//                pdpActual = pdpActualList.get(0)
//                        .getText()
//                        .trim();
//            }
//
//            // =================================================
//            // PDP DISCOUNT PERCENTAGE
//            // DISCOUNT % MUST BE DISPLAYED ON PDP
//            // =================================================
//
//            List<WebElement> pdpDiscountList = driver.findElements(
//                    By.xpath("//div[contains(@class,'prod_discounted_percentage')]")
//            );
//
//            String pdpDiscount = "";
//
//            if (!pdpDiscountList.isEmpty()) {
//
//                pdpDiscount = pdpDiscountList.get(0)
//                        .getText()
//                        .trim();
//            }
//
//            // =================================================
//            // CALCULATE EXPECTED DISCOUNT
//            // =================================================
//
//            String expectedDiscount = "";
//
//            if (!pdpCurrent.isEmpty()
//                    && !pdpActual.isEmpty()
//                    && !pdpActual.equals(pdpCurrent)) {
//
//                String currentNumber = pdpCurrent
//                        .replaceAll("[^0-9]", "");
//
//                String actualNumber = pdpActual
//                        .replaceAll("[^0-9]", "");
//
//                if (!currentNumber.isEmpty()
//                        && !actualNumber.isEmpty()) {
//
//                    int currentPrice = Integer.parseInt(currentNumber);
//                    int actualPrice = Integer.parseInt(actualNumber);
//
//                    if (actualPrice > 0) {
//
//                        expectedDiscount =
//                                ((actualPrice - currentPrice) * 100)
//                                        / actualPrice
//                                        + "%";
//                    }
//                }
//            }
//
//            // =================================================
//            // PRINT PDP DATA
//            // =================================================
//
//            System.out.println(
//                    YELLOW + "PDP Product Name: "
//                            + pdpProductName
//                            + RESET
//            );
//
//            System.out.println(
//                    "PDP Current Price: "
//                            + (pdpCurrent.isEmpty()
//                                    ? "Not Visible"
//                                    : pdpCurrent)
//                            + " | Actual Price: "
//                            + (pdpActual.isEmpty()
//                                    ? "Not Visible"
//                                    : pdpActual)
//                            + " | Discount: "
//                            + (pdpDiscount.isEmpty()
//                                    ? "Not Visible"
//                                    : pdpDiscount)
//            );
//
//            // =================================================
//            // VALIDATE PDP DISCOUNT PERCENTAGE
//            // DISCOUNT % MUST BE DISPLAYED
//            // =================================================
//
//            if (pdpDiscount.isEmpty()) {
//
//                System.out.println(
//                        RED
//                                + "Discount Percentage is NOT displayed on PDP ❌"
//                                + RESET
//                );
//
//                Assert.fail(
//                        "Discount percentage is not displayed on PDP"
//                );
//
//            } else {
//
//                System.out.println(
//                        GREEN
//                                + "Discount Percentage is displayed on PDP: "
//                                + pdpDiscount
//                                + " ✅"
//                                + RESET
//                );
//
//                // -------------------------------------------------
//                // VALIDATE DISCOUNT PERCENTAGE
//                // -------------------------------------------------
//
//                if (!expectedDiscount.isEmpty()
//                        && !pdpDiscount.equals(expectedDiscount)) {
//
//                    System.out.println(
//                            RED
//                                    + "Discount Percentage Mismatch ❌ -> Expected: "
//                                    + expectedDiscount
//                                    + " | Actual: "
//                                    + pdpDiscount
//                                    + RESET
//                    );
//
//                    Assert.fail(
//                            "Discount percentage mismatch. Expected: "
//                                    + expectedDiscount
//                                    + " | Actual: "
//                                    + pdpDiscount
//                    );
//
//                } else if (!expectedDiscount.isEmpty()) {
//
//                    System.out.println(
//                            GREEN
//                                    + "Discount Percentage Match ✅"
//                                    + RESET
//                    );
//                }
//            }
//
//            // =================================================
//            // VALIDATE PRODUCT NAME
//            // =================================================
//
//            if (!homeProductName.equalsIgnoreCase(pdpProductName)) {
//
//                System.out.println(
//                        RED + "Product Name Mismatch ❌ -> Home Page: "
//                                + homeProductName
//                                + " | PDP: "
//                                + pdpProductName
//                                + RESET
//                );
//
//                Assert.fail(
//                        "Product Name mismatch. Home Page: "
//                                + homeProductName
//                                + " | PDP: "
//                                + pdpProductName
//                );
//
//            } else {
//
//                System.out.println(
//                        GREEN + "Product Name Match ✅"
//                                + RESET
//                );
//            }
//
//            // =================================================
//            // VALIDATE PRICE
//            // =================================================
//
//            if (homeCurrent.isEmpty() || pdpCurrent.isEmpty()) {
//
//                System.out.println(
//                        YELLOW
//                                + "Price not visible. Price validation skipped."
//                                + RESET
//                );
//
//            } else {
//
//                String homePriceNumber = homeCurrent
//                        .replaceAll("[^0-9]", "");
//
//                String pdpPriceNumber = pdpCurrent
//                        .replaceAll("[^0-9]", "");
//
//                if (!homePriceNumber.equals(pdpPriceNumber)) {
//
//                    System.out.println(
//                            RED + "Price Mismatch ❌ -> Home Page: "
//                                    + homeCurrent
//                                    + " | PDP: "
//                                    + pdpCurrent
//                                    + RESET
//                    );
//
//                    Assert.fail(
//                            "Price mismatch. Home Page: "
//                                    + homeCurrent
//                                    + " | PDP: "
//                                    + pdpCurrent
//                    );
//
//                } else {
//
//                    System.out.println(
//                            GREEN + "Price Match ✅"
//                                    + RESET
//                    );
//                }
//            }
//
//            verifiedCount++;
//
//            // =================================================
//            // BACK TO NEW ARRIVALS
//            // =================================================
//
//            driver.navigate().back();
//
//            wait.until(
//                    ExpectedConditions.presenceOfElementLocated(
//                            sectionLocator
//                    )
//            );
//
//            WebElement sectionAgain = driver.findElement(
//                    sectionLocator
//            );
//
//            ((JavascriptExecutor) driver).executeScript(
//                    "arguments[0].scrollIntoView(true);",
//                    sectionAgain
//            );
//
//            Thread.sleep(1500);
//
//        } catch (AssertionError e) {
//
//            System.out.println(
//                    RED + "Test Case Failed at Product "
//                            + (i + 1)
//                            + " -> "
//                            + e.getMessage()
//                            + RESET
//            );
//
//            throw e;
//
//        } catch (Exception e) {
//
//            System.out.println(
//                    RED + "Error at Product "
//                            + (i + 1)
//                            + " -> "
//                            + e.getMessage()
//                            + RESET
//            );
//
//            Assert.fail(
//                    "Error while verifying Product "
//                            + (i + 1)
//                            + ": "
//                            + e.getMessage()
//            );
//        }
//    }
//
//    // =========================================================
//    // VIEW ALL - DO NOT VERIFY AS PRODUCT
//    // =========================================================
//
//    List<WebElement> viewAll = driver.findElements(
//            viewAllLocator
//    );
//
//    if (!viewAll.isEmpty()) {
//
//        System.out.println(
//                BLUE + "View All button is available."
//                        + RESET
//        );
//
//        ((JavascriptExecutor) driver).executeScript(
//                "arguments[0].scrollIntoView({block:'center'});",
//                viewAll.get(0)
//        );
//
//        Thread.sleep(500);
//
//        // Click View All
//
//        ((JavascriptExecutor) driver).executeScript(
//                "arguments[0].click();",
//                viewAll.get(0)
//        );
//
//        Thread.sleep(1500);
//
//        String heading = driver.findElement(
//                By.xpath("//h2[@class='prod_listing_topic']")
//        ).getText();
//
//        String url = driver.getCurrentUrl();
//
//        System.out.println(
//                BLUE + "View All Page Heading: "
//                        + heading
//                        + RESET
//        );
//
//        System.out.println(
//                BLUE + "View All Page URL: "
//                        + url
//                        + RESET
//        );
//
//    } else {
//
//        System.out.println(
//                YELLOW + "View All button is not available."
//                        + RESET
//        );
//    }
//
//    // =========================================================
//    // FINAL RESULT
//    // =========================================================
//
//    System.out.println(
//            BLUE + "========================================"
//                    + RESET
//    );
//
//    System.out.println(
//            BLUE + "Verified New Arrivals Products: "
//                    + verifiedCount
//                    + "/"
//                    + verifyCount
//                    + RESET
//    );
//
//    if (verifiedCount == verifyCount) {
//
//        System.out.println(
//                GREEN
//                        + "All 7 New Arrivals products verified successfully ✅"
//                        + RESET
//        );
//
//    } else {
//
//        System.out.println(
//                RED
//                        + "Some New Arrivals products were not verified ❌"
//                        + RESET
//        );
//    }
//
//    // =========================================================
//    // CLOSE BROWSER
//    // =========================================================
//
//    driver.quit();
//}


public void verifyNewInProductsCompleteFlow() throws InterruptedException {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String BLUE   = "\u001B[34m";
    String RESET  = "\u001B[0m";

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    Actions actions = new Actions(driver);

    // =========================================================
    // OPEN HOME PAGE
    // =========================================================

    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

    click(zlaataIndiaShopButton);
    
    Common.waitForElement(10);

    By sectionLocator = By.xpath(
            "//section[@data-section='new_arrivals']"
    );

    By productLocator = By.xpath(
            "//section[@data-section='new_arrivals']"
                    + "//a[contains(@class,'banner_link')]"
    );

    By viewAllLocator = By.xpath(
            "//section[@data-section='new_arrivals']"
                    + "//a[contains(@class,'new_in_redirect')]"
    );

    // =========================================================
    // SCROLL TO NEW ARRIVALS SECTION
    // =========================================================

    WebElement section = wait.until(
            ExpectedConditions.presenceOfElementLocated(sectionLocator)
    );

    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView(true);",
            section
    );

    Thread.sleep(1000);

    // =========================================================
    // GET NEW ARRIVALS PRODUCTS
    // =========================================================

    List<WebElement> products = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(productLocator)
    );

    int total = products.size();

    // Last element is View All.
    // Verify only first 7 products.

    int verifyCount = Math.min(
            Math.max(total - 1, 0),
            7
    );

    int verifiedCount = 0;

    System.out.println(
            BLUE + "Total New Arrivals Products to Verify: "
                    + verifyCount
                    + RESET
    );

    // =========================================================
    // VERIFY FIRST 7 PRODUCTS
    // =========================================================

    for (int i = 0; i < verifyCount; i++) {

        try {

            // Re-fetch products after returning from PDP

            products = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            productLocator
                    )
            );

            WebElement product = products.get(i);

            // =================================================
            // SCROLL TO PRODUCT
            // =================================================

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    product
            );

            actions.moveToElement(product).perform();

            Thread.sleep(1500);

            // =================================================
            // HOME PAGE / NEW ARRIVALS DATA
            // =================================================

            String homeProductName = product.findElement(
                    By.xpath(".//h3[@class='new_in_heading']")
            ).getText().trim();

            // =================================================
            // HOME PAGE CURRENT PRICE
            // =================================================

            List<WebElement> homeCurrentList = product.findElements(
                    By.xpath(
                            ".//span[contains(@class,'prod_current_price')]"
                    )
            );

            String homeCurrent = "";

            if (!homeCurrentList.isEmpty()) {

                homeCurrent = homeCurrentList.get(0)
                        .getText()
                        .trim();
            }

            // =================================================
            // HOME PAGE ACTUAL / STRIKETHROUGH PRICE
            // =================================================

            List<WebElement> homeActualList = product.findElements(
                    By.xpath(
                            ".//span[contains(@class,'prod_actual_price')]"
                    )
            );

            String homeActual = "";

            if (!homeActualList.isEmpty()) {

                homeActual = homeActualList.get(0)
                        .getText()
                        .trim();
            }

            // =================================================
            // CHECK DISCOUNT FROM HOME PAGE
            // =================================================

            /*
             * If Actual Price / Strikethrough Price is available
             * on Home Page:
             *
             * -> Product is discounted
             * -> PDP discount percentage should be displayed
             *
             * If Actual Price / Strikethrough Price is NOT available:
             *
             * -> Product is not discounted
             * -> PDP discount percentage is not required
             */

            boolean homeDiscountAvailable =
                    !homeActual.isEmpty();

            // =================================================
            // PRINT HOME PAGE DATA
            // =================================================

            System.out.println(
                    BLUE + "----------------------------------------"
                            + RESET
            );

            System.out.println(
                    BLUE + "New Arrivals Product "
                            + (i + 1)
                            + ": "
                            + homeProductName
                            + RESET
            );

            System.out.println(
                    "Home Page Current Price: "
                            + (homeCurrent.isEmpty()
                                    ? "Not Visible"
                                    : homeCurrent)
                            + " | Actual Price: "
                            + (homeActual.isEmpty()
                                    ? "Not Visible"
                                    : homeActual)
            );

            if (homeDiscountAvailable) {

                System.out.println(
                        GREEN
                                + "Home Page: Strikethrough price is available. "
                                + "Product is discounted."
                                + RESET
                );

            } else {

                System.out.println(
                        GREEN
                                + "Home Page: No strikethrough price available. "
                                + "Product is not discounted."
                                + RESET
                );
            }

            // =================================================
            // CLICK PRODUCT
            // =================================================

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    product
            );

            // =================================================
            // PDP PRODUCT NAME
            // =================================================

            WebElement pdpNameElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//h3[@class='prod_name']")
                    )
            );

            String pdpProductName = pdpNameElement
                    .getText()
                    .trim();

            // =================================================
            // PDP CURRENT PRICE
            // =================================================

            List<WebElement> pdpCurrentList = driver.findElements(
                    By.xpath("//div[@class='prod_current_price']")
            );

            String pdpCurrent = "";

            if (!pdpCurrentList.isEmpty()) {

                pdpCurrent = pdpCurrentList.get(0)
                        .getText()
                        .trim();
            }

            // =================================================
            // PDP ACTUAL PRICE
            // =================================================

            List<WebElement> pdpActualList = driver.findElements(
                    By.xpath("//div[@class='prod_actual_price']")
            );

            String pdpActual = "";

            if (!pdpActualList.isEmpty()) {

                pdpActual = pdpActualList.get(0)
                        .getText()
                        .trim();
            }

            // =================================================
            // PDP DISCOUNT PERCENTAGE
            // =================================================

            List<WebElement> pdpDiscountList = driver.findElements(
                    By.xpath(
                            "//div[@class='prod_discount_percentage']"
                    )
            );

            String pdpDiscount = "";

            if (!pdpDiscountList.isEmpty()) {

                pdpDiscount = pdpDiscountList.get(0)
                        .getText()
                        .trim();
            }

            // =================================================
            // PRINT PDP DATA
            // =================================================

            System.out.println(
                    YELLOW + "PDP Product Name: "
                            + pdpProductName
                            + RESET
            );

            System.out.println(
                    "PDP Current Price: "
                            + (pdpCurrent.isEmpty()
                                    ? "Not Visible"
                                    : pdpCurrent)
                            + " | Actual Price: "
                            + (pdpActual.isEmpty()
                                    ? "Not Visible"
                                    : pdpActual)
                            + " | Discount: "
                            + (pdpDiscount.isEmpty()
                                    ? "Not Visible"
                                    : pdpDiscount)
            );

            // =================================================
            // PRINT PDP DISCOUNT ONLY
            // NO DISCOUNT VALIDATION
            // =================================================

            if (homeDiscountAvailable) {

                if (!pdpDiscount.isEmpty()) {

                    System.out.println(
                            GREEN
                                    + "PDP Discount Percentage: "
                                    + pdpDiscount
                                    + " ✅"
                                    + RESET
                    );

                } else {

                    System.out.println(
                            RED
                                    + "PDP Discount Percentage is NOT displayed ❌"
                                    + RESET
                    );
                }

            } else {

                System.out.println(
                        GREEN
                                + "No strikethrough price on Home Page. "
                                + "PDP discount percentage is not required."
                                + RESET
                );
            }

            // =================================================
            // VALIDATE PRODUCT NAME
            // =================================================

            if (!homeProductName.equalsIgnoreCase(pdpProductName)) {

                System.out.println(
                        RED + "Product Name Mismatch ❌ -> Home Page: "
                                + homeProductName
                                + " | PDP: "
                                + pdpProductName
                                + RESET
                );

                Assert.fail(
                        "Product Name mismatch. Home Page: "
                                + homeProductName
                                + " | PDP: "
                                + pdpProductName
                );

            } else {

                System.out.println(
                        GREEN + "Product Name Match ✅"
                                + RESET
                );
            }

            // =================================================
            // VALIDATE PRICE
            // =================================================

            if (homeCurrent.isEmpty() || pdpCurrent.isEmpty()) {

                System.out.println(
                        YELLOW
                                + "Price not visible. Price validation skipped."
                                + RESET
                );

            } else {

                String homePriceNumber = homeCurrent
                        .replaceAll("[^0-9]", "");

                String pdpPriceNumber = pdpCurrent
                        .replaceAll("[^0-9]", "");

                if (!homePriceNumber.equals(pdpPriceNumber)) {

                    System.out.println(
                            RED + "Price Mismatch ❌ -> Home Page: "
                                    + homeCurrent
                                    + " | PDP: "
                                    + pdpCurrent
                                    + RESET
                    );

                    Assert.fail(
                            "Price mismatch. Home Page: "
                                    + homeCurrent
                                    + " | PDP: "
                                    + pdpCurrent
                    );

                } else {

                    System.out.println(
                            GREEN + "Price Match ✅"
                                    + RESET
                    );
                }
            }

            verifiedCount++;

            // =================================================
            // BACK TO NEW ARRIVALS
            // =================================================

            driver.navigate().back();

            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            sectionLocator
                    )
            );

            WebElement sectionAgain = driver.findElement(
                    sectionLocator
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);",
                    sectionAgain
            );

            Thread.sleep(1500);

        } catch (AssertionError e) {

            System.out.println(
                    RED + "Test Case Failed at Product "
                            + (i + 1)
                            + " -> "
                            + e.getMessage()
                            + RESET
            );

            throw e;

        } catch (Exception e) {

            System.out.println(
                    RED + "Error at Product "
                            + (i + 1)
                            + " -> "
                            + e.getMessage()
                            + RESET
            );

            Assert.fail(
                    "Error while verifying Product "
                            + (i + 1)
                            + ": "
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // VIEW ALL - DO NOT VERIFY AS PRODUCT
    // =========================================================

    List<WebElement> viewAll = driver.findElements(
            viewAllLocator
    );

    if (!viewAll.isEmpty()) {

        System.out.println(
                BLUE + "View All button is available."
                        + RESET
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                viewAll.get(0)
        );

        Thread.sleep(500);

        // Click View All

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                viewAll.get(0)
        );

        Thread.sleep(1500);

        String heading = driver.findElement(
                By.xpath("//h2[@class='prod_listing_topic']")
        ).getText();

        String url = driver.getCurrentUrl();

        System.out.println(
                BLUE + "View All Page Heading: "
                        + heading
                        + RESET
        );

        System.out.println(
                BLUE + "View All Page URL: "
                        + url
                        + RESET
        );

    } else {

        System.out.println(
                YELLOW + "View All button is not available."
                        + RESET
        );
    }

    // =========================================================
    // FINAL RESULT
    // =========================================================

    System.out.println(
            BLUE + "========================================"
                    + RESET
    );

    System.out.println(
            BLUE + "Verified New Arrivals Products: "
                    + verifiedCount
                    + "/"
                    + verifyCount
                    + RESET
    );

    if (verifiedCount == verifyCount) {

        System.out.println(
                GREEN
                        + "All New Arrivals products verified successfully ✅"
                        + RESET
        );

    } else {

        System.out.println(
                RED
                        + "Some New Arrivals products were not verified ❌"
                        + RESET
        );
    }

    // =========================================================
    // CLOSE BROWSER
    // =========================================================

    driver.quit();
}
public void flashNotification() {
	
	
	 driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

	    // Click Shop button if needed
	    click(zlaataIndiaShopButton);
	    Common.waitForElement(10);
	    flashNotificationInZL();
	    click(bosslady);
	    Common.waitForElement(10);

	    flashNotificationInZL();
	    
	   }

public void flashNotificationInZL() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String RESET  = "\u001B[0m";

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    try {

        By flashSaleLocator = By.xpath("//a[@aria-label='Flash Sale']");

        // 1. Verify Flash Sale notification availability
        List<WebElement> flashList = driver.findElements(flashSaleLocator);

        if (flashList.size() == 0) {
            System.out.println(YELLOW +
                    "Flash notification NOT available ⚠️" + RESET);
            return;
        }

        System.out.println(GREEN +
                "Flash notification is available ✅" + RESET);

        // 2. Store current URL before click
        String beforeClickUrl = driver.getCurrentUrl();

        System.out.println("URL Before Click: " + beforeClickUrl);

        // 3. Click Flash Sale
        WebElement flashSale = wait.until(
                ExpectedConditions.elementToBeClickable(flashSaleLocator)
        );

        flashSale.click();

        // 4. Wait until URL changes
        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlToBe(beforeClickUrl)
        ));

        // 5. Store current URL after click
        String afterClickUrl = driver.getCurrentUrl();

        System.out.println("URL After Click: " + afterClickUrl);

        // 6. Verify both URLs
        if (!beforeClickUrl.equals(afterClickUrl)) {

            System.out.println(GREEN +
                    "Redirection SUCCESS - URLs are different ✅" + RESET);

        } else {

            System.out.println(RED +
                    "Redirection FAILED - URLs are the same ❌" + RESET);
        }

    } catch (Exception e) {

        System.out.println(RED +
                "Error in Flash Sale validation: " +
                e.getMessage() + RESET);
    }
}

//public void verifyDotsAndProducts() {
//
//    String GREEN  = "\u001B[32m";
//    String RED    = "\u001B[31m";
//    String CYAN   = "\u001B[36m";
//    String RESET  = "\u001B[0m";
//
//    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//
//    click(zlaataIndiaShopButton);
//
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//    WebElement section = wait.until(
//        ExpectedConditions.presenceOfElementLocated(
//            By.xpath("//section[@class='zi_special_banner module_section']")
//        )
//    );
//
//    ((JavascriptExecutor) driver).executeScript(
//        "arguments[0].scrollIntoView({block:'center'});", section);
//    System.out.println(CYAN + "Scrolled to banner section" + RESET);
//
//    // 🔹 Get all products (UPDATED XPath)
//    List<WebElement> products = driver.findElements(By.xpath("//div[@class='influencer_prod_details']"));
//
//    System.out.println(CYAN + "Total Products: " + products.size() + RESET);
//
//    for (int i = 0; i < products.size(); i++) {
//
//        // 🔁 Re-fetch
//        products = driver.findElements(By.xpath("//div[@class='influencer_prod_details']"));
//        WebElement product = products.get(i);
//
//        // 🔹 BEFORE click
//        String nameBefore = product.findElement(By.xpath("//h6[@class='influencer_prod_name']")).getText();
//        String priceBefore = product.findElement(By.xpath("//div[@class='prod_price_row ']")).getText();
//
//        System.out.println(CYAN + "Before Click -> " + nameBefore + " | " + priceBefore + RESET);
//
//        // 🔹 Click using parent <a>
//        WebElement clickable = product.findElement(By.xpath("./ancestor::a"));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickable);
//
//        // 🔹 AFTER click
//        String nameAfter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class='prod_name']"))).getText();
//        String priceAfter = driver.findElement(By.xpath("//div[@class='prod_current_price']")).getText();
//
//        System.out.println(CYAN + "After Click -> " + nameAfter + " | " + priceAfter + RESET);
//
//        // 🔹 Validate
//        if (!(nameAfter.contains(nameBefore) || nameBefore.contains(nameAfter))) {
//            System.out.println(RED + "Name NOT matched" + RESET);
//            throw new RuntimeException("Product name mismatch");
//        }
//
//        if (!priceAfter.contains(priceBefore)) {
//            System.out.println(RED + "Price NOT matched" + RESET);
//            throw new RuntimeException("Product price mismatch");
//        }
//
//        System.out.println(GREEN + "Product matched successfully" + RESET);
//
//        // 🔹 Go back
//        driver.navigate().back();
//
//        // 🔹 Wait for section again
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//            By.xpath("//section[@class='zi_special_banner module_section']")
//        ));
//    }
//}

public void verifyDotsAndProducts() throws InterruptedException {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";

    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

    click(zlaataIndiaShopButton);

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    Actions actions = new Actions(driver);

    WebElement section = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            By.xpath("//section[@class='zi_special_banner module_section']")
        )
    );

    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({block:'center'});", section);

    System.out.println(CYAN + "Scrolled to banner section" + RESET);

    List<WebElement> products = driver.findElements(By.xpath("//div[@class='influencer_prod_details']"));

    System.out.println(CYAN + "Total Products: " + products.size() + RESET);

    for (int i = 0; i < products.size(); i++) {

        products = driver.findElements(By.xpath("//div[@class='influencer_prod_details']"));
        WebElement product = products.get(i);

        // 🔥 IMPORTANT: Hover to make text visible
        actions.moveToElement(product).perform();
        Thread.sleep(800); // small wait for UI

        // 🔹 Get name & price AFTER hover
        String nameBefore = product.findElement(By.xpath(".//h3")).getText().trim();
        String priceBefore = product.findElement(By.xpath(".//span")).getText().trim();

        if (nameBefore.isEmpty() || priceBefore.isEmpty()) {
            System.out.println(RED + "Before Click still empty (UI issue)" + RESET);
            throw new RuntimeException("Product details not visible before click");
        }

        System.out.println(CYAN + "Before Click -> " + nameBefore + " | " + priceBefore + RESET);

//        // 🔹 Click product
//        WebElement clickable = product.findElement(By.xpath("./ancestor::a"));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickable);
        
        List<WebElement> links = driver.findElements(By.xpath("//a[@class='influencer_prod_details_wrap']"));
        WebElement clickable = links.get(i);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickable);

        // 🔹 AFTER click
        String nameAfter = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class='prod_name']"))
        ).getText();

        String priceAfter = driver.findElement(By.xpath("//div[@class='prod_current_price']")).getText();

        System.out.println(CYAN + "After Click -> " + nameAfter + " | " + priceAfter + RESET);

     // 🔹 Normalize strings
        String before = nameBefore.toLowerCase().replaceAll("\\s+", "").replace(",", "");
        String after  = nameAfter.toLowerCase().replaceAll("\\s+", "").replace(",", "");

        // 🔹 Validate Name
        if (!(before.contains(after) || after.contains(before))) {
            System.out.println(RED + "Name NOT matched" + RESET);
            throw new RuntimeException("Product name mismatch");
        }

        // 🔹 Normalize & Validate Price
        String cleanBefore = priceBefore.replaceAll("[^0-9]", "");
        String cleanAfter  = priceAfter.replaceAll("[^0-9]", "");

        if (!cleanAfter.equals(cleanBefore)) {
            System.out.println(RED + "Price NOT matched" + RESET);
            throw new RuntimeException("Product price mismatch");
        }

        System.out.println(GREEN + "Product matched successfully" + RESET);

        driver.navigate().back();

        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//section[@class='zi_special_banner module_section']")
        ));
    }
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


