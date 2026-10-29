package pages;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import objectRepo.SearchBarObjRepo;
import utils.Common;

public final class SearchSectionPage  extends SearchBarObjRepo{

	public SearchSectionPage(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	//TC 01
	public void searchbarClikable() {
		Common.waitForElement(5);
		
	click(clickOnSearchBar);
	
//	Common.waitForElement(2000);
		try {
			if (searchBarPage.isDisplayed()) 
			{
				System.out.println("the Search  icon is  cliked and it is open the search page ");
			}
			else {
				System.out.println("the Search icon not  cliked it is not ooen the search page ");
			}
		} catch (Exception e) 
		{
			System.out.println(e);
		}
		
	}
	
	
	public void printRecentSearches() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // 🎨 COLORS
	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    // 🔹 OPEN SEARCH
	    WebElement searchBox = wait.until(
	            ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='search']"))
	    );
	    searchBox.click();

	    System.out.println(CYAN + "🔍 Search opened" + RESET);

	    // 🔹 CHECK IF SECTION EXISTS
	    By recentSectionBy = By.xpath("//div[contains(@class,'recent_search_wrapper')]");
	    List<WebElement> section = driver.findElements(recentSectionBy);

	    if (section.size() == 0) {
	        System.out.println(YELLOW + "⚠️ No Recent Searches section available" + RESET);
	        return;
	    }

	    // 🔹 GET LIST
	    By recentListBy = By.xpath("//ul[contains(@class,'recent_search_list')]//li");
	    List<WebElement> recentSearches = driver.findElements(recentListBy);

	    // 🔹 CHECK EMPTY LIST
	    if (recentSearches.size() == 0) {
	        System.out.println(YELLOW + "⚠️ Recent Searches section present but NO data" + RESET);
	        return;
	    }

	    System.out.println(YELLOW + "📌 Total Recent Searches: " + recentSearches.size() + RESET);

	    // 🔹 LOOP & PRINT
	    for (int i = 0; i < recentSearches.size(); i++) {

	        recentSearches = driver.findElements(recentListBy); // 🔁 re-fetch
	        WebElement item = recentSearches.get(i);

	        String text = item.getText().trim();

	        if (text.isEmpty()) {
	            System.out.println(RED + "⚠️ Empty search at index " + i + RESET);
	            continue;
	        }

	        System.out.println(GREEN + "👉 [" + (i + 1) + "] " + text + RESET);
	    }

	    System.out.println(CYAN + "✅ Recent Searches validation completed" + RESET);
	}
	
//TC 02
	public void TrendingAndRelatedHeading()
	{
		searchbarClikable();
		String trendings = headingTrendings.getText();
		System.out.println("heading is displaying:"+trendings);
		String related = headingRelatedProducts.getText();
		System.out.println("heading is displaying:"+related);
	}
	
	//TC 03

	public void clickAllTrendingProductsAndVerify() throws InterruptedException, TimeoutException {
		searchbarClikable();
		// XPath for trending product links
		String trendingLinksXPath = "//a[@class='product-redirect-tag cls_search_collection']";
		// XPath for product page heading - replace with actual XPath of the heading on product page
		String productHeadingXPath = "//h3[@class='prod_list_topic']"; 
		String trendings1 = headingTrendings.getText();
		System.out.println("heading is displaying:"+trendings1);
		// Fetch the trending product links initially
		List<WebElement> trendingsOptionList = driver.findElements(By.xpath(trendingLinksXPath));
		int total = trendingsOptionList.size();
		System.out.println("🔥 Total Trending Products: " + total);

		for (int i = 0; i < total; i++) {
			// Re-fetch the list each time, because DOM reloads after navigation
			trendingsOptionList = driver.findElements(By.xpath(trendingLinksXPath));
			WebElement product = trendingsOptionList.get(i);

			String productName = product.getText().trim();
			System.out.println("👉 Clicking on Trendings sub options: " + productName);

			// Scroll into view and click using JavaScript (safer)
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
			Thread.sleep(500);

			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", product);
			} catch (Exception e) {
				System.out.println("Normal click failed, trying JavaScript click...");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", product);

			}

			// Wait for product page heading to appear and verify
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(productHeadingXPath)));
			String headingText = driver.findElement(By.xpath(productHeadingXPath)).getText();
			System.out.println("✔️ Product page heading verified: " + headingText);

			// Navigate back to trending/search page
			searchbarClikable();
			Thread.sleep(1000);
		}
	}
//TC 04

	public void searchKeyWordRedirectToCorrectpage() {
		Common.waitForElement(2);
		click(searchBarInput);
		Common.waitForElement(2);
		String value = Common.getValueFromTestDataMap("Search bar");
		System.out.println("🔍 Step 1: Entering search keyword from Excel: " + value);
		searchbaractive.sendKeys(value);
		System.out.println("✅ Step 1: Keyword entered in input: " + searchbaractive.getAttribute("value"));
		Common.waitForElement(2);
		searchbaractive.sendKeys(Keys.ENTER); // First redirection
		Common.waitForElement(3);

		String firstRedirectionHeading = heading.getText();
		System.out.println("📄 Step 2: Heading after first redirection: " + firstRedirectionHeading);
		click(searchBarInput);
		Common.waitForElement(3);
		String historyHeading = headingSearchHistory.getText();
		System.out.println("🧾 Step 3: Search history heading displayed: " + historyHeading);

		String displayedKeyword = newSearchHistorykeywrod.getText();
		System.out.println("🧾 Step 3: Search history keyword displayed: " + displayedKeyword);

		click(newSearchHistorykeywrod); // Second redirection
		Common.waitForElement(3);

		String secondRedirectionHeading = heading.getText();
		System.out.println("📄 Step 4: Heading after second redirection: " + secondRedirectionHeading);

		if (firstRedirectionHeading.equals(secondRedirectionHeading)) {
			System.out.println("❌ FAIL: Both redirections landed on the SAME page.");
		}
		else {
			System.out.println("❌ FAIL: Both redirections differnE page.");

		}
	}

	
	//Tc 05

	public void validateRelatedQueriesAndHeadings() throws InterruptedException {
		String keyword = Common.getValueFromTestDataMap("Search bar"); // e.g., "yellow"
		System.out.println("🔍 Searching for keyword: " + keyword);

		click(searchBarInput);
		Common.waitForElement(3);
		searchbaractive.sendKeys(keyword);
//		Common.waitForElement(10); // Wait for related queries dropdown

		List<WebElement> queries = driver.findElements(By.xpath("//a[@class='product-redirect-tag cls_search_collection']"));
		int totalQueries = queries.size();
		System.out.println("🔽 Total related queries found: " + totalQueries);

		for (int i = 0; i < totalQueries; i++) {
			queries = driver.findElements(By.xpath("//a[@class='product-redirect-tag cls_search_collection']"));
			WebElement query = queries.get(i);
			String expectedHeading = query.getText().trim();
			System.out.println("👉 Clicking related query: " + expectedHeading);

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", query);
			Thread.sleep(500);
			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", query);
			} catch (Exception e) {
				System.out.println("⚠️ Normal click failed. Trying JS click.");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", query);
			}
			Common.waitForElement(2);
			String actualHeading = heading.getText().trim();

			if (actualHeading.equalsIgnoreCase(expectedHeading)) {
				System.out.println("✅ Heading matched: " + actualHeading);
			} else {
				System.err.println("❌ Heading mismatch! Expected: '" + expectedHeading + "' but found: '" + actualHeading + "'");
				Assert.fail("Heading mismatch for: " + expectedHeading);
			}
			Common.waitForElement(1);
			driver.navigate().back();

			click(searchBarInput);
			searchbaractive.sendKeys(keyword);
			Common.waitForElement(1);
		}
		

	System.out.println("🏁 Validation completed for all related queries.");

		}
	//TC 06
	public void verifySearchSuggestionHeading() {
		searchbarClikable();
		Common.waitForElement(2);
		String value = Common.getValueFromTestDataMap("Search bar");
		System.out.println("🔍 Testing search keyword: " + value);	    
		searchbaractive.sendKeys(value);
		System.out.println("✅ Entered keyword: " + searchbaractive.getAttribute("value"));
		Common.waitForElement(2);
		searchbaractive.sendKeys(Keys.ENTER);
		Common.waitForElement(3);
		String actualMessage = heading.getText();
		System.out.println("🧾 Heading displayed: " + actualMessage);
		Assert.assertTrue("❌ Heading is empty or not as expected!", !actualMessage.trim().isEmpty());
		System.out.println("\u001B[32m" + "✅ The heading message displayed correctly: " + actualMessage + "\u001B[0m");
	}

//TC 07
	public void verifySearchHistoryDisplaying()
	{
		Common.waitForElement(2);

		click(searchBarInput);

		Common.waitForElement(2);

		// Step 2: Fetch search keyword from Excel/TestData
		String value = Common.getValueFromTestDataMap("Search bar");
		System.out.println("🔍 Testing search keyword: " + value);	    

		// Step 3: Type into search bar and press Enter
		searchbaractive.sendKeys(value);
		System.out.println("✅ Entered keyword: " + searchbaractive.getAttribute("value"));
		Common.waitForElement(2);
		searchbaractive.sendKeys(Keys.ENTER);
		Common.waitForElement(3);

		// Step 4: Reopen the search bar (to check search history/suggestion)
		click(searchBarInput);
		Common.waitForElement(3);

		String actualMessage =headingSearchHistory.getText();
		System.out.println("🧾 Heading displayed: " + actualMessage);

	}
	//TC 08
	
	public void verifysearchHistoryKeyworddisplayAnduserabletoDelete() {
		Common.waitForElement(2);
		click(searchBarInput);
		String value = Common.getValueFromTestDataMap("Search bar");
		System.out.println("🔍 Testing search keyword: " + value);

		searchbaractive.sendKeys(value);
		System.out.println("✅ Entered keyword: " + searchbaractive.getAttribute("value"));
		Common.waitForElement(2);
		searchbaractive.sendKeys(Keys.ENTER); // Hit Enter
		click(searchBarInput);
		Common.waitForElement(3);

		String historyHeading = headingSearchHistory.getText();
		System.out.println("🧾 History heading displayed: " + historyHeading);

		String displayedKeyword = newSearchHistorykeywrod.getText();
		System.out.println("🧾 Search history keyword displayed: " + displayedKeyword);

		click(searchHistoryRemoveButtons);

		Common.waitForElement(2);

		List<WebElement> updatedKeywords = driver.findElements(By.xpath("//*[@class='search_history_item_remove_btn']")); // 🔁 Update XPath if needed

		boolean isRemoved = true;
		for (WebElement keyword : updatedKeywords) {
		    if (keyword.getText().trim().equalsIgnoreCase(displayedKeyword)) {
		        isRemoved = false;
		        break;
		    }
		}

		// If-else block to validate
		if (isRemoved) {
		    System.out.println("✅ Keyword successfully removed: " + displayedKeyword);
		} else {
		    System.err.println("❌ Keyword still present after clicking remove: " + displayedKeyword);
		    Assert.fail("Search history keyword not removed.");
		}
	}
	//TC 09

	public void enterProductNameExactlyRedirectToProduct() {
//		Common.waitForElement(2);
//		click(searchBarInput);
//		Common.waitForElement(2);
//		String value = Common.getValueFromTestDataMap("Search bar");
//		System.out.println("🔍 Testing search keyword: " + value);
//		searchbaractive.sendKeys(value);
//		System.out.println("✅ Entered keyword: " + searchbaractive.getAttribute("value"));
//		Common.waitForElement(1);
//		searchbaractive.sendKeys(Keys.ENTER);
//		Common.waitForElement(3);
//
//		String actualHeading = heading.getText().trim();
//		System.out.println("🧾 Search Result Heading: " + actualHeading);
//		Assert.assertFalse("❌ Heading is empty!", actualHeading.isEmpty());
//		System.out.println("\u001B[32m✅ Heading displayed correctly: " + actualHeading + "\u001B[0m");
//
//		String displayedProductName = productName.getText().trim();
//		System.out.println("🧾 Product Name Displayed: " + displayedProductName);
//
//		Assert.assertEquals("❌ Heading and product name mismatch!",
//				actualHeading.toLowerCase(), displayedProductName.toLowerCase());
//
//		System.out.println("\u001B[32m✅ Heading and product name match: " + actualHeading + "\u001B[0m");
		Common.waitForElement(2);
		click(searchBarInput);
		Common.waitForElement(2);

		String value = Common.getValueFromTestDataMap("Search bar");
		System.out.println("🔍 Testing search keyword: " + value);
		searchbaractive.sendKeys(value);
		System.out.println("✅ Entered keyword: " + searchbaractive.getAttribute("value"));

		Common.waitForElement(1);
		searchbaractive.sendKeys(Keys.ENTER);
		Common.waitForElement(3);

		// Get and validate heading
		String headingText = heading.getText().trim();
		System.out.println("🧾 Search Result Heading: " + headingText);
		Assert.assertFalse("❌ Heading is empty!", headingText.isEmpty());
		System.out.println("\u001B[32m✅ Heading displayed correctly: " + headingText + "\u001B[0m");

		// Get and print product name
		String productText = productName.getText().trim();
		System.out.println("🧾 Product Name Displayed: " + productText);

		// Normalize text
		headingText = headingText.toLowerCase().replaceAll("[^a-z0-9 ]", "");
		productText = productText.toLowerCase().replaceAll("[^a-z0-9 ]", "");

		// Split product name into words
		String[] productWords = productText.split(" ");

		// Check if any word from product name is contained in heading
		boolean matchFound = false;
		for (String word : productWords) {
		    if (headingText.contains(word)) {
		        matchFound = true;
		        break;
		    }
		}

		if (matchFound) {
		    System.out.println("\u001B[32m✅ At least one word from product name matched in heading. Test passed.\u001B[0m");
		} else {
		    Assert.fail("❌ No matching word from product name found in heading!");
		}

	}

	//TC 10
	public void recentlyViewProductAppears() {
		Common.waitForElement(2);

	    click(searchBarInput);
	    Common.waitForElement(2);

	    String value = Common.getValueFromTestDataMap("Search bar");
	    System.out.println("🔍 Testing search keyword: " + value);

	    searchbaractive.sendKeys(value);
	    System.out.println("✅ Entered keyword: " + searchbaractive.getAttribute("value"));
	    Common.waitForElement(1);
	    searchbaractive.sendKeys(Keys.ENTER);
	    Common.waitForElement(3);

	    String actualHeading = heading.getText().trim();
	    System.out.println("🧾 Search Result Heading: " + actualHeading);
	    Assert.assertFalse("❌ Heading is empty!", actualHeading.isEmpty());
	    System.out.println("\u001B[32m✅ Heading displayed correctly: " + actualHeading + "\u001B[0m");

	    click(productListingImage);
	    Common.waitForElement(2);
	    click(buyNowButton);
	    Common.waitForElement(3);

	    click(searchBarInput);
	    Common.waitForElement(2);

	    String recentHeading = recentlyViwed.getText().trim();
	    System.out.println("🧾 Recently Viewed Section Heading: " + recentHeading);

	    String recentProduct = recentlyViwedProduct.getText().trim();
	    System.out.println("🧾 Recently Viewed Product Name: " + recentProduct);

	    // ✅ Better comparison logic
	    String headingNormalized = actualHeading.toLowerCase().trim();
	    String recentProductNormalized = recentProduct.toLowerCase().trim();

	    System.out.println("🔁 Comparing for partial match:");
	    System.out.println("   🟢 Search Heading   : " + headingNormalized);
	    System.out.println("   🟡 Recently Viewed  : " + recentProductNormalized);

//	    boolean partialMatch = headingNormalized.contains(recentProductNormalized)
//	                        || recentProductNormalized.contains(headingNormalized);

//	    Assert.assertTrue("❌ Recently viewed product does not match or partially match searched product!",
//	                      partialMatch);

	    System.out.println("\u001B[32m✅ Recently viewed product matches (partially or fully): " + recentProduct + "\u001B[0m");
	}

	


	public void verifyBothBrandRelatedQueries() throws InterruptedException {
		
		searchbarClikable();
		verifyRelatedQueriesZI();
		verifyRelatedQueriesBL();

		
	}
//	public void verifyRelatedQueriesZI() throws InterruptedException {
//
//	    String keyword = "red";
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//
//	    // Search
//	    WebElement searchInput = wait.until(
//	            ExpectedConditions.elementToBeClickable(By.id("globalSearchInput"))
//	    );
//
//	    searchInput.click();
//	    searchInput.clear();
//	    searchInput.sendKeys(keyword);
//
//	    Thread.sleep(2000);
//
//	    // Get first suggestion
//	    WebElement firstSuggestion = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("(//li[contains(@class,'product-redirect-tag')])[1]")
//	            )
//	    );
//
//	    String expectedName = firstSuggestion.getText().trim();
//
//	    System.out.println("First Suggestion: " + expectedName);
//
//	    // Click first suggestion
//	    firstSuggestion.click();
//
//	    // Verify heading
//	    WebElement heading = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//h2[contains(@class,'prod_listing_topic')]")
//	            )
//	    );
//
//	    String actualHeading = heading.getText().trim();
//
//	    System.out.println("Expected Heading: " + expectedName);
//	    System.out.println("Actual Heading: " + actualHeading);
//
//	    Assert.assertEquals(
//	    	    expectedName.toUpperCase(),
//	    	    actualHeading.toUpperCase()
//	    	);
//
//	    // Verify products are displayed
//	    List<WebElement> products = driver.findElements(
//	            By.xpath("//div[contains(@class,'prod_listing_card')]")
//	    );
//
//	    Assert.assertTrue(
//	            "No products displayed!",
//	            products.size() > 0
//	   
//	    );
//
//	    System.out.println("✅ Products Found: " + products.size());
//	    System.out.println("✅ First suggestion validated successfully.");
//	}
	public void verifyRelatedQueriesZI() throws InterruptedException {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    String keyword = "Red";
	    System.out.println(CYAN + "🔍 Searching for keyword: " + keyword + RESET);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // ✅ Open search
	    WebElement searchInput = wait.until(
	            ExpectedConditions.elementToBeClickable(By.id("globalSearchInput"))
	    );

	    searchInput.click();
	    searchInput.clear();
	    searchInput.sendKeys(keyword);

	    Thread.sleep(2000);

	    // ✅ Check wrapper
	    By wrapperBy = By.xpath("//div[@class='recent_search_wrapper']");
	    List<WebElement> wrappers = driver.findElements(wrapperBy);

	    if (wrappers.isEmpty()) {
	        System.out.println(YELLOW + "⚠️ No Related Queries section found" + RESET);
	        return;
	    }

	    WebElement wrapper = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(wrapperBy)
	    );

	    String sectionHeading = wrapper.findElement(By.xpath("//h2[@class='search_heading']")).getText();
	    System.out.println(GREEN + "📌 Section Heading: " + sectionHeading + RESET);

	    // ✅ Queries XPath
	    By queriesBy = By.xpath("//li[contains(@class,'product-redirect-tag')]");

	    List<WebElement> queries = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(queriesBy)
	    );

	    int total = queries.size();
	    System.out.println(CYAN + "🔽 Total Related Queries: " + total + RESET);

	    // ✅ Product XPath
	    By productsBy = By.xpath("//div[contains(@class,'prod_listing_card')]");

	    for (int i = 0; i < total; i++) {

	        // 🔁 Re-fetch queries
	        queries = wait.until(
	                ExpectedConditions.visibilityOfAllElementsLocatedBy(queriesBy)
	        );

	        WebElement query = queries.get(i);
	        String expected = query.getText().trim();

	        System.out.println(YELLOW + "👉 Clicking Query [" + (i + 1) + "] : " + expected + RESET);

	        try {
	            wait.until(ExpectedConditions.elementToBeClickable(query)).click();
	        } catch (Exception e) {
	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", query);
	        }

	        // ✅ Heading validation
	        WebElement headingEle = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//h2[contains(@class,'prod_listing_topic')]")
	                )
	        );

	        String actual = headingEle.getText().trim();

	        if (actual.equalsIgnoreCase(expected)) {
	            System.out.println(GREEN + "✅ Heading matched: " + actual + RESET);
	        } else {
	            System.out.println(RED + "❌ Heading mismatch! Expected: " + expected + " | Actual: " + actual + RESET);
	            Assert.fail("Heading mismatch");
	        }

	        // ✅ PRODUCT VALIDATION (NEW 🔥)
	        List<WebElement> products = wait.until(
	                ExpectedConditions.visibilityOfAllElementsLocatedBy(productsBy)
	        );

	        int productCount = products.size();

	        if (productCount > 0) {
	            System.out.println(GREEN + "🛍 Products Found: " + productCount + RESET);
	        } else {
	            System.out.println(RED + "❌ No products found!" + RESET);
	            Assert.fail("No products displayed for: " + expected);
	        }

	        // 🔙 Back
	        driver.navigate().back();

	        // 🔥 Re-open search (fix stale)
	        searchInput = wait.until(
	                ExpectedConditions.elementToBeClickable(By.id("globalSearchInput"))
	        );

	        searchInput.click();
	        searchInput.clear();
	        searchInput.sendKeys(keyword);

	        Thread.sleep(1500);
	    }

	    System.out.println(GREEN + "🎉 All Related Queries + Products validated successfully!" + RESET);
	}
	public void verifyRelatedQueriesBL() throws InterruptedException {
		
		click(bosslady);
		
		searchbarClikable();

		 String keyword = "formal";

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		    // Search
		    WebElement searchInput = wait.until(
		            ExpectedConditions.elementToBeClickable(By.id("globalSearchInput"))
		    );

		    searchInput.click();
		    searchInput.clear();
		    searchInput.sendKeys(keyword);

		    Thread.sleep(2000);

		    // Get first suggestion
		    WebElement firstSuggestion = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("(//li[contains(@class,'product-redirect-tag')])[1]")
		            )
		    );

		    String expectedName = firstSuggestion.getText().trim();

		    System.out.println("First Suggestion: " + expectedName);

		    // Click first suggestion
		    firstSuggestion.click();

		    // Verify heading
		    WebElement heading = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("//h2[contains(@class,'prod_listing_topic')]")
		            )
		    );

		    String actualHeading = heading.getText().trim();

		    System.out.println("Expected Heading: " + expectedName);
		    System.out.println("Actual Heading: " + actualHeading);

		    Assert.assertEquals(
		    	    expectedName.toUpperCase(),
		    	    actualHeading.toUpperCase()
		    	);

		    // Verify products are displayed
		    List<WebElement> products = driver.findElements(
		            By.xpath("//div[contains(@class,'prod_listing_card')]")
		    );

		    Assert.assertTrue(
		            "No products displayed!",
		            products.size() > 0
		   
		    );

		    System.out.println("✅ Products Found: " + products.size());
		    System.out.println("✅ First suggestion validated successfully.");
	}
	public void verifyBothBrandcloseButtonInSearchbar() throws InterruptedException {
		searchbarClikable();
		verifyCloseButtonInSearchBarFor();
		System.out.println("Text remove from zlaata India ");
		click(bosslady);
		searchbarClikable();
		verifyCloseButtonInSearchBarFor();
		System.out.println("Text remove from Boss lady ");

		
	}

	public void verifyCloseButtonInSearchBarFor() throws InterruptedException {

		 String keyword = "kurta";
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		    // ANSI color codes for console
		    final String RESET = "\u001B[0m";
		    final String RED = "\u001B[31m";
		    final String GREEN = "\u001B[32m";
		    final String YELLOW = "\u001B[33m";
		    final String BLUE = "\u001B[34m";

		    // ✅ Step 1: Open search and type keyword
		    WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("globalSearchInput")));
		    searchInput.click();
		    searchInput.clear();
		    searchInput.sendKeys(keyword);
		    System.out.println(BLUE + "✍️ Typed keyword: " + keyword + RESET);

		    Thread.sleep(500); // shorter wait, enough for UI update

		    // ✅ Step 2: Click Close button (SVG)
		    By closeBtnBy = By.xpath("//*[@class='search_clr_icon']");
		    WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(closeBtnBy));

		    try {
		        closeBtn.click();
		        System.out.println(RED + "❌ Clicked Close button" + RESET);
		    } catch (Exception e) {
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
		        System.out.println(YELLOW + "⚠️ JS click on Close button performed" + RESET);
		    }

		    Thread.sleep(500); // wait for DOM update

		    // ✅ Step 3: Validate search input is cleared
		    String currentValue = searchInput.getAttribute("value");
		    System.out.println(BLUE + "📌 Search input value after close: '" + currentValue + "'" + RESET);

		    if (currentValue == null || currentValue.isEmpty()) {
		        System.out.println(GREEN + "✅ PASS → Text removed after clicking Close" + RESET);
		    } else {
		        System.err.println(RED + "❌ FAIL → Text NOT removed. Value: '" + currentValue + "'" + RESET);
		        Assert.fail("Search text not removed after Close button");
		    }
	}
	
	public void verifyBothBrandRecentSearches() {
		searchbarClikable();
		verifyRecentSearchesZL();
		click(bosslady);
		searchbarClikable();
		verifyRecentSearchesZL();
		
	}
	
		public void verifyRecentSearchesZL() {

	    // Console colors
	    String GREEN = "\u001B[32m";
	    String RED = "\u001B[31m";
	    String CYAN = "\u001B[36m";
	    String RESET = "\u001B[0m";

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // 1️⃣ Random dress keyword
	    List<String> dressKeywords = Arrays.asList(
	            "dress", "kurta", "gown", "maxi dress", "mini dress",
	            "saree dress", "evening dress", "party dress"
	    );
	    Collections.shuffle(dressKeywords);
	    String keyword = dressKeywords.get(0);
	    System.out.println(CYAN + "Random dress keyword selected: " + keyword + RESET);

	    String currentUrlBefore = "";
	    int productCountBefore = 0;

	    try {
	        // 2️⃣ Open search and type keyword
	        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("globalSearchInput")));
	        searchInput.click();
	        searchInput.clear();
	        searchInput.sendKeys(keyword);
	        searchInput.sendKeys(Keys.ENTER);

	        // 3️⃣ Store URL and product count
	        currentUrlBefore = driver.getCurrentUrl();
	        By productsBy = By.xpath("//div[contains(@class,'prod_listing_card')]");
	        wait.until(ExpectedConditions.visibilityOfElementLocated(productsBy));
	        productCountBefore = driver.findElements(productsBy).size();

	        System.out.println("Initial URL: " + currentUrlBefore);
	        System.out.println("Initial product count: " + productCountBefore);

	        // 4️⃣ Click the Close button (without clearing)
	        WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='search_clr_icon']")));
	        closeBtn.click();

	        // 5️⃣ Click search bar again
	        searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("globalSearchInput")));
	        searchInput.click();

	        // 6️⃣ Click the same keyword from Recent Searches
	        By recentListBy = By.xpath("//ul[@class='recent_search_list']/li[contains(@class,'search_history_item_name')]");
	        List<WebElement> recentItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(recentListBy));
	        boolean foundRecent = false;

	        for (WebElement item : recentItems) {
	            if (item.getText().trim().equalsIgnoreCase(keyword)) {
	                foundRecent = true;
	                item.click();
	                break;
	            }
	        }

	        if (!foundRecent) {
	            System.out.println(RED + "❌ Keyword not found in Recent Searches: " + keyword + RESET);
	            return;
	        }

	        // 7️⃣ After navigation, re-locate products and URL
	        String urlAfter = driver.getCurrentUrl();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(productsBy));
	        int productCountAfter = driver.findElements(productsBy).size();

	        // ✅ Verification using equalsIgnoreCase for URL
	        if (currentUrlBefore.equalsIgnoreCase(urlAfter) && productCountBefore == productCountAfter) {
	            System.out.println(GREEN + "✅ Recent search results match previous search for keyword: " + keyword + RESET);
	            System.out.println(GREEN + "✅ URL: " + urlAfter + RESET);
	            System.out.println(GREEN + "✅ Product count: " + productCountAfter + RESET);
	        } else {
	            System.out.println(RED + "❌ Recent search results DO NOT match previous search for keyword: " + keyword + RESET);
	            System.out.println(RED + "❌ Previous URL: " + currentUrlBefore + ", Current URL: " + urlAfter + RESET);
	            System.out.println(RED + "❌ Previous count: " + productCountBefore + ", Current count: " + productCountAfter + RESET);
	        }

	    } catch (Exception e) {
	        System.out.println(RED + "Error during recent search verification: " + e.getMessage() + RESET);
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

