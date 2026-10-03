package pages;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import manager.FileReaderManager;
import objectRepo.ProductListObjRepo;
import utils.Common;

public final class ProductListingPage extends ProductListObjRepo {



	public ProductListingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
//TC-01
	public void homeCrumbLink() {
		   String GREEN  = "\u001B[32m";
		    String RESET  = "\u001B[0m";
		    String CYAN   = "\u001B[36m";
		    String YELLOW = "\u001B[33m";
		    String PURPLE = "\u001B[35m";
		    String line = "──────────────────────────────────────────────────────────────";
		    System.out.println(CYAN + line + RESET);
	    // Launch home
	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    
	    click(zlaataIndiaShopButton);
	    
	    Common.waitForElement(10);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Hover and open category
	    Common.waitForElement(2);
	    Actions actions = new Actions(driver);
	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(category).click().perform();

	    System.out.println(CYAN + "🔍 Navigated to category page" + RESET);

	    // Click Home breadcrumb
	    wait.until(ExpectedConditions.elementToBeClickable(homeCrumbLink)).click();
	    System.out.println(YELLOW + "👉 Clicked Home breadcrumb" + RESET);

	    // ✅ Verify URL
	    String expectedUrl = "https://www.zlaata.com/zlaata-india";
	    wait.until(ExpectedConditions.urlToBe(expectedUrl));

	    String actualUrl = driver.getCurrentUrl();
	    Assert.assertEquals(
	            "❌ URL mismatch after clicking Home breadcrumb",
	            expectedUrl,
	            actualUrl
	    );

	    System.out.println(GREEN + "✅ URL verified: " + actualUrl + RESET);

//	    // ✅ Verify Home Banner
//	    WebElement homeBanner = wait.until(
//	            ExpectedConditions.visibilityOf(banners)
//	    );
//
//	    Assert.assertTrue(
//	            "❌ Home banner is NOT visible",
//	            homeBanner.isDisplayed()
//	    );
//
//	    System.out.println(GREEN + "✅ Home banner is displayed" + RESET);
	    
	    
	    click(brandswitch);
	    
	    
	    Common.waitForElement(2);
	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(category).click().perform();

	    System.out.println(CYAN + "🔍 Navigated to category page" + RESET);

	    // Click Home breadcrumb
	    wait.until(ExpectedConditions.elementToBeClickable(homeCrumbLink)).click();
	    System.out.println(YELLOW + "👉 Clicked Home breadcrumb" + RESET);

	    // ✅ Verify URL
	    String expectedUrl1 = "https://www.zlaata.com/boss-lady";
	    wait.until(ExpectedConditions.urlToBe(expectedUrl1));

	    String actualUrl1 = driver.getCurrentUrl();
	    Assert.assertEquals(
	            "❌ URL mismatch after clicking Home breadcrumb",
	            expectedUrl1,
	            actualUrl1
	    );

	    System.out.println(GREEN + "✅ URL verified: " + actualUrl1 + RESET);

	    
	    
	}
	
	//TC-02
	
	public void pLpHeading() throws InterruptedException {

	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String CYAN  = "\u001B[36m";
	    String RESET = "\u001B[0m";
	    String line = "──────────────────────────────────────────────────────────────";
	    System.out.println(CYAN + line + RESET);
    // Launch home
    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
    
    click(zlaataIndiaShopButton);
    
    Common.waitForElement(10);
    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    System.out.println(CYAN + "🔍 Navigating to Product Listing Page..." + RESET);

	    Actions actions = new Actions(driver);
	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(category).click().perform();

	    WebElement headingElement = wait.until(
	            ExpectedConditions.visibilityOf(shopPageHead)
	    );

	    String headingText = headingElement.getText().trim();
Thread.sleep(2000);
	    // ✅ Validation
	    if (headingElement.isDisplayed() && !headingText.isEmpty()) {
	        System.out.println(GREEN + "✅ PLP Heading Displayed Successfully" + RESET);
	        System.out.println(CYAN + "📌 Heading Text: " + headingText + RESET);
	    } else {
	        System.out.println(RED + "❌ PLP Heading NOT displayed or empty" + RESET);
	        Assert.fail("PLP Heading validation failed");
	    }
	}
//TC-03
	public void pagination() throws InterruptedException {

	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String CYAN  = "\u001B[36m";
	    String RESET = "\u001B[0m";
	    String line = "──────────────────────────────────────────────────────────────";
	    System.out.println(CYAN + line + RESET);
    // Launch home
    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
    
    click(zlaataIndiaShopButton);
    
    Common.waitForElement(10);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    System.out.println(CYAN + "🔍 Navigating to Product Listing Page..." + RESET);

	    Actions actions = new Actions(driver);
	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(category).click().perform();

	    try {
	        // Wait for pagination
	        WebElement paginationElement = wait.until(
	                ExpectedConditions.visibilityOf(pagination)
	        );

	        // Scroll to pagination
	        ((JavascriptExecutor) driver)
	                .executeScript("arguments[0].scrollIntoView(true);", paginationElement);

	        if (paginationElement.isDisplayed()) {
	            System.out.println(GREEN + "✅ Pagination is visible on PLP page" + RESET);
	            Assert.assertTrue(true);
	        }

	    } catch (TimeoutException e) {
	        System.out.println(RED + "❌ Pagination not found on PLP page" + RESET);
	        Assert.fail("Pagination not displayed on Product Listing Page");

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Unexpected error while verifying pagination: " 
	                           + e.getMessage() + RESET);
	        throw e;
	    }
	    Thread.sleep(2000);
	}
	
//TC-04	
	public void pagiNationArrows() {

	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String CYAN  = "\u001B[36m";
	    String RESET = "\u001B[0m";
	    String line = "──────────────────────────────────────────────────────────────";
	    System.out.println(CYAN + line + RESET);
	 // Launch home
//	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		    System.out.println(CYAN + "🔍 Navigating to Product Listing Page..." + RESET);

		    Actions actions = new Actions(driver);
		    actions.moveToElement(shopMenu).perform();
		    actions.moveToElement(category).click().perform();
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    try {
	        // Wait for pagination
	        WebElement paginationBlock = wait.until(
	                ExpectedConditions.visibilityOf(pagination)
	        );

	        System.out.println(GREEN + "✅ Pagination visible" + RESET);

	        // Scroll pagination to CENTER of screen (important)
	        js.executeScript(
	                "arguments[0].scrollIntoView({block:'center'});",
	                paginationBlock
	        );

	        // Wait a moment for sticky header to settle
	        Thread.sleep(2000);

	        WebElement nextBtn = wait.until(
	                ExpectedConditions.elementToBeClickable(paginationNext)
	        );

	        System.out.println(CYAN + "➡ Clicking Next page" + RESET);

	        // ✅ JS click avoids interception
	        js.executeScript("arguments[0].click();", nextBtn);
	        Thread.sleep(2000);
	        // Validate navigation
	        wait.until(ExpectedConditions.urlContains("page=2"));
	        String currentUrl = driver.getCurrentUrl();

	        Assert.assertTrue(
	                "❌ Pagination failed. URL: " + currentUrl,
	                currentUrl.contains("page=2")
	        );

	        System.out.println(GREEN + "✅ Pagination success → Page 2 loaded" + RESET);

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Pagination failed: " + e.getMessage() + RESET);
	        Assert.fail("Pagination arrow not clickable");
	    }
	}
//TC-05
	
	public void paginationNumber() {

	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String CYAN  = "\u001B[36m";
	    String RESET = "\u001B[0m";

	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    
	    click(zlaataIndiaShopButton);
	    
	    Common.waitForElement(10);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    Actions actions = new Actions(driver);

	    System.out.println(CYAN + "🔍 Navigating to PLP..." + RESET);

	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(category).click().perform();

	    try {
	        // Get all pagination numbers
	        List<WebElement> pages = wait.until(
	                ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                        By.xpath("//div[@class='pagi_count_wrap']//a[normalize-space()]")
	                )
	        );

	        Assert.assertTrue("❌ Pagination not found", pages.size() > 0);

	        Collections.shuffle(pages);
	        WebElement page = pages.get(0);

	        String pageNo = page.getText().trim();
	        System.out.println(CYAN + "➡ Clicking page: " + pageNo + RESET);

	        // Scroll safely
	        js.executeScript("arguments[0].scrollIntoView({block:'center'});", page);
	        Thread.sleep(800);

	        // Click via JS (avoids header issue)
	        js.executeScript("arguments[0].click();", page);
	        Thread.sleep(2000);
	        // Validate URL
	        wait.until(ExpectedConditions.urlContains("/all"));
	        String currentUrl = driver.getCurrentUrl();

	        if (pageNo.equals("1")) {

	            Assert.assertTrue(
	                    "❌ Page 1 URL incorrect: " + currentUrl,
	                    currentUrl.equals("https://www.zlaata.com/zlaata-india/all")
	                    || currentUrl.equals("https://www.zlaata.com/zlaata-india/all#")
	            );

	            System.out.println(GREEN + "✅ Page 1 loaded correctly → " + currentUrl + RESET);

	        } else {
	            Assert.assertTrue(
	                    "❌ Page URL mismatch: " + currentUrl,
	                    currentUrl.contains("page=" + pageNo)
	            );
	            System.out.println(GREEN + "✅ Page " + pageNo + " loaded correctly" + RESET);
	        }

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Pagination validation failed: " + e.getMessage() + RESET);
	        Assert.fail("Pagination failed");
	    }
	}

//TC-06
	public void showFilter() {
		Common.waitForElement(5);
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();
		Common.waitForElement(5);
		actions.moveToElement(showFilter).click().build().perform();
		try {

			if (showFilterMenu.isDisplayed()) {
				Assert.assertTrue((verifyDisplayed(showFilterMenu)));
			}

		} catch (Exception e) {
			System.out.println("Caught an exception: " + e.getMessage());
			NoSuchElementException e1 = new NoSuchElementException("A NoSuchElementException exception occurred");
			e1.initCause(e);
			throw e1;
		}
	}
	public void verifyFilter() {

	    String CYAN  = "\u001B[36m";
	    String BLUE  = "\u001B[34m";
	    String RED   = "\u001B[31m";
	    String GREEN = "\u001B[32m";
	    String RESET = "\u001B[0m";

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    Actions actions = new Actions(driver);

	    System.out.println(CYAN + "🔍 Navigating to PLP..." + RESET);

	    // ✅ Navigate to PLP
	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(categoryDresses).click().perform();
	    
	    Common.waitForElement(2);

	    // ✅ Click Filter Button (SVG)
	    WebElement filterBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//*[name()='svg' and contains(@class,'prod_list_filter_btn')]")
	    ));
	    filterBtn.click();
	    System.out.println("✅ Clicked Filter Button");

	    // ✅ Click Categories section
	    WebElement categories = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//li[@data-filter='categories']")
	    ));
	    categories.click();
	    System.out.println("✅ Opened Categories");

	    // ✅ Select categories_co_ords checkbox
	    WebElement coOrdscheckbox = wait.until(ExpectedConditions.elementToBeClickable(
	            By.id("categories_co_ords")
	    ));

	    // Scroll + click (safe)
	    js.executeScript("arguments[0].scrollIntoView({block:'center'});", coOrdscheckbox);
	    js.executeScript("arguments[0].click();", coOrdscheckbox);

	    System.out.println("✅ Selected coords");

	    // ✅ Click Apply button
	    WebElement applyBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[contains(@class,'Cls_apply_filter')]")
	    ));
	    applyBtn.click();

	    System.out.println("✅ Clicked Apply");

	    // ✅ Validate Heading = All
	    WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//h2[contains(@class,'prod_listing_topic')]")
	    ));

	    String headingText = heading.getText().trim();

	    if (headingText.equalsIgnoreCase("All")) {
	        System.out.println(GREEN + "✅ Heading Verified: " + headingText + RESET);
	    } else {
	        System.out.println(RED + "❌ Heading Mismatch: " + headingText + RESET);
	        Assert.fail("Heading is not 'All'");
	    }

	    System.out.println(GREEN + "🎉 FILTER TEST PASSED" + RESET);
	}

	public void sortByFilter() {
		Common.waitForElement(5);
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();
		Common.waitForElement(5);
		actions.moveToElement(sortBy).click().build().perform();

	}
	
	public void selectSortOption(String option) {
	    By sortOption = By.xpath("//li[contains(@class,'filter_sort_list_items') and normalize-space()='" + option + "']");
	    driver.findElement(sortOption).click();
	}
	public void waitForProductsToLoad() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(@class,'prod_listing_card')]")));
	}
	public List<Integer> getAllPrices() {
	    List<WebElement> elements = driver.findElements(
	            By.xpath("//span[@class='product_discounted_price']")
	    );

	    List<Integer> prices = new ArrayList<>();

	    for (WebElement ele : elements) {
	        String text = ele.getText().trim();

	        // 🔥 skip empty values
	        if (text.isEmpty()) {
	            continue;
	        }

	        text = text.replaceAll("[^0-9]", "");

	        // 🔥 double safety
	        if (!text.isEmpty()) {
	            prices.add(Integer.parseInt(text));
	        }
	    }

	    return prices;
	}
	public List<Integer> getAllDiscounts() {
	    List<WebElement> elements = driver.findElements(
	            By.xpath("//span[contains(@class,'product_discounted_percentage')]")
	    );

	    List<Integer> discounts = new ArrayList<>();

	    for (WebElement ele : elements) {
	        String text = ele.getText().trim();

	        if (text.isEmpty()) {
	            continue;
	        }

	        text = text.replaceAll("[^0-9]", "");

	        if (!text.isEmpty()) {
	            discounts.add(Integer.parseInt(text));
	        }
	    }

	    return discounts;
	}
	public boolean isSortedAscending(List<Integer> list) {
	    for (int i = 0; i < list.size() - 1; i++) {
	        if (list.get(i) > list.get(i + 1)) {
	            return false;
	        }
	    }
	    return true;
	}
	public boolean isSortedDescending(List<Integer> list) {
	    for (int i = 0; i < list.size() - 1; i++) {
	        if (list.get(i) < list.get(i + 1)) {
	            return false;
	        }
	    }
	    return true;
	}
//	public void verifySortBy() {
//
//	    String CYAN  = "\u001B[36m";
//	    String BLUE  = "\u001B[34m";
//	    String RED   = "\u001B[31m";
//	    String GREEN = "\u001B[32m";
//	    String RESET = "\u001B[0m";
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//	    JavascriptExecutor js = (JavascriptExecutor) driver;
//	    Actions actions = new Actions(driver);
//
//	    System.out.println(CYAN + "🔍 Navigating to PLP..." + RESET);
//
//	    // ✅ Navigate to PLP
//	    actions.moveToElement(shopMenu).perform();
//	    actions.moveToElement(categoryDresses).click().perform();
//Common.waitForElement(2);
//	    
//	    wait.until(ExpectedConditions.visibilityOfElementLocated(
//	            By.xpath("//div[contains(@class,'prod_listing_card')]")));
//
//	    // ================================
//	    // 🔹 SORT BUTTON
//	    // ================================
//	    By sortBtn = By.xpath("//*[name()='svg' and contains(@class,'prod_list_sortby_btn')]");
//
//	    // ================================
//	    // 🔹 1. PRICE HIGH TO LOW
//	    // ================================
//	    System.out.println(BLUE + "🔽 Verifying Price High to Low..." + RESET);
//
//	    driver.findElement(sortBtn).click();
//	    Common.waitForElement(2);
//	    selectSortOption("Price High to Low");
//
//	    waitForProductsToLoad();
//
//	    if (isSortedDescending(getAllPrices())) {
//	        System.out.println(GREEN + "✅ Price High to Low working" + RESET);
//	    } else {
//	        System.out.println(RED + "❌ Price High to Low failed" + RESET);
//	    }
//	    Common.waitForElement(2);
//	    // ================================
//	    // 🔹 2. PRICE LOW TO HIGH
//	    // ================================
//	    System.out.println(BLUE + "🔼 Verifying Price Low to High..." + RESET);
//
//	    driver.findElement(sortBtn).click();
//	    Common.waitForElement(2);
//	    selectSortOption("Price Low to High");
//
//	    waitForProductsToLoad();
//
//	    if (isSortedAscending(getAllPrices())) {
//	        System.out.println(GREEN + "✅ Price Low to High working" + RESET);
//	    } else {
//	        System.out.println(RED + "❌ Price Low to High failed" + RESET);
//	    }
//	    Common.waitForElement(2);
//	    // ================================
//	    // 🔹 3. DISCOUNT HIGH TO LOW
//	    // ================================
//	    System.out.println(BLUE + "🔽 Verifying Discount High to Low..." + RESET);
//
//	    driver.findElement(sortBtn).click();
//	    Common.waitForElement(2);
//	    selectSortOption("Discount High to Low");
//
//	    waitForProductsToLoad();
//
//	    if (isSortedDescending(getAllDiscounts())) {
//	        System.out.println(GREEN + "✅ Discount High to Low working" + RESET);
//	    } else {
//	        System.out.println(RED + "❌ Discount High to Low failed" + RESET);
//	    }
//	    Common.waitForElement(2);
//	    // ================================
//	    // 🔹 4. DISCOUNT LOW TO HIGH
//	    // ================================
//	    System.out.println(BLUE + "🔼 Verifying Discount Low to High..." + RESET);
//
//	    driver.findElement(sortBtn).click();
//	    Common.waitForElement(2);
//	    selectSortOption("Discount Low to High");
//
//	    waitForProductsToLoad();
//
//	    if (isSortedAscending(getAllDiscounts())) {
//	        System.out.println(GREEN + "✅ Discount Low to High working" + RESET);
//	    } else {
//	        System.out.println(RED + "❌ Discount Low to High failed" + RESET);
//	    }
//	    Common.waitForElement(2);
//	}
	
	
	public void verifySortBy() {

	    String CYAN  = "\u001B[36m";
	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    
	    
	    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    Actions actions = new Actions(driver);

	    System.out.println(CYAN + "🔍 Navigating to PLP..." + RESET);

	    // Navigate to PLP
	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(categoryDresses).click().perform();

	    Common.waitForElement(2);



	    By sortButton = By.xpath("//*[@aria-label='Sort products']");

	    By sortOptions = By.xpath(
	            "//li[contains(@class,'filter_sort_list_items')]");

	    // Open Sort By first time
	    wait.until(ExpectedConditions.elementToBeClickable(sortButton)).click();

	    wait.until(ExpectedConditions.visibilityOfElementLocated(sortOptions));

	    // Get all options dynamically
	    List<WebElement> options = driver.findElements(sortOptions);

	    List<String> sortNames = new ArrayList<>();

	    for (WebElement option : options) {

	        String value = option.getAttribute("data-value");

	        if (value != null && !value.trim().isEmpty()) {
	            sortNames.add(value.trim());
	        }
	    }

	    System.out.println(
	            CYAN + "Total Sort Options : "
	                    + sortNames.size()
	                    + RESET);

	    // Click every option
	    for (String sortName : sortNames) {

	        // Find option again
	        WebElement option = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.xpath("//li[@data-value=\"" + sortName + "\"]")));

	        option.click();

	        System.out.println(
	                GREEN + "✅ Clicked : "
	                        + sortName
	                        + RESET);

	        Common.waitForElement(2);

	        // Open Sort By again ONLY if popup is closed
	        if (!driver.findElements(sortOptions).stream()
	                .anyMatch(WebElement::isDisplayed)) {

	            wait.until(ExpectedConditions.elementToBeClickable(sortButton))
	                    .click();

	            wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    sortOptions));
	        }
	    }

	    System.out.println(
	            GREEN + "🎉 All Sort By options clicked successfully."
	                    + RESET);
	
	}
	
	//	public void basicFilterFunction() {
//		Common.waitForElement(5);
//		Actions actions = new Actions(driver);
//		actions.moveToElement(shopMenu);
//		actions.moveToElement(category).click().build().perform();
//		Common.waitForElement(2);
//		actions.moveToElement(showFilter).click().build().perform();
//		List<WebElement> clickShowFilterMenu = driver.findElements(By.xpath("//span[@class='prod_filter_heading']"));
//		Collections.shuffle(clickShowFilterMenu);
//
//		if (!clickShowFilterMenu.isEmpty()) {
//			WebElement randomFilter = clickShowFilterMenu.get(0);
//			actions.moveToElement(randomFilter).click().build().perform();
//		}
//		List<WebElement> clickShowFilterSubMenu = driver.findElements(By.xpath("//div[@class='prod_filter_value']"));
//		Collections.shuffle(clickShowFilterSubMenu);
//
//		if (priceRangeFilter.isDisplayed()) {
//			click(maxPriceFilter);
//			maxPriceFilter.clear();
//			Common.waitForElement(2);
//			type(maxPriceFilter,Common.getValueFromTestDataMap("Mobile Number"));
//			Common.waitForElement(2);
//			click(filterApply);
//			click(showFilter);
//			click(filterClearAll);
//			Common.waitForElement(2);
//			click(closeShowFilter);
//		}
//
//
//		click(filterApply);
//		Common.waitForElement(2);
//		click(showFilter);
//		click(filterClearAll);
//	}

	public void basicFilterFunction() {
	    Common.waitForElement(5);
	    Actions actions = new Actions(driver);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    // Hover over shopMenu and click category
	    actions.moveToElement(shopMenu).perform();
	    wait.until(ExpectedConditions.elementToBeClickable(category));
	    actions.moveToElement(category).click().build().perform();
	    Common.waitForElement(2);
	    // Click showFilter
	    wait.until(ExpectedConditions.elementToBeClickable(showFilter));
	    actions.moveToElement(showFilter).click().build().perform();
	    // Click a random main filter
	    List<WebElement> mainFilters = driver.findElements(By.xpath("//span[@class='prod_filter_heading']"));
	    List<WebElement> visibleMainFilters = mainFilters.stream()
	            .filter(WebElement::isDisplayed)
	            .filter(WebElement::isEnabled)
	            .collect(Collectors.toList());
	    Collections.shuffle(visibleMainFilters);
	    String selectedMainFilterName = "";
	    if (!visibleMainFilters.isEmpty()) {
	        WebElement randomMainFilter = visibleMainFilters.get(0);
	        selectedMainFilterName = randomMainFilter.getText().trim();
	        js.executeScript("arguments[0].scrollIntoView(true);", randomMainFilter);
	        wait.until(ExpectedConditions.elementToBeClickable(randomMainFilter));
	        try {
	            actions.moveToElement(randomMainFilter).click().build().perform();
	        } catch (ElementNotInteractableException e) {
	            js.executeScript("arguments[0].click();", randomMainFilter);
	        }
	    }
	    // Wait for sub-filters to appear and get only the visible sub-filters
	    List<WebElement> filterOptions = driver.findElements(By.xpath("//p[@class='prod_filter_value_name']"));
	    List<WebElement> visibleOptions = filterOptions.stream()
	            .filter(WebElement::isDisplayed)
	            .filter(WebElement::isEnabled)
	            .collect(Collectors.toList());
	    Collections.shuffle(visibleOptions);
	    int expectedProductCount = -1;
	    String selectedSubFilterName = "";
	    if (!visibleOptions.isEmpty()) {
	        WebElement selectedFilter = visibleOptions.get(0);
	        String filterText = selectedFilter.getText().trim();  // Example: Green (25)
	        selectedSubFilterName = filterText.replaceAll("\\s*\\(\\d+\\)$", ""); // Get "Green"
	        System.out.println("Sub filter selected: " + filterText);
	        try {
	            expectedProductCount = Integer.parseInt(filterText.replaceAll(".*\\((\\d+)\\)", "$1"));
	        } catch (Exception e) {
	            System.out.println(":x: Could not extract count from: " + filterText);
	        }
	        js.executeScript("arguments[0].scrollIntoView(true);", selectedFilter);
	        wait.until(ExpectedConditions.elementToBeClickable(selectedFilter));
	        try {
	            selectedFilter.click();
	        } catch (ElementNotInteractableException e) {
	            js.executeScript("arguments[0].click();", selectedFilter);
	        }
	    }
	    // Apply filter
	    click(filterApply);
	    Common.waitForElement(3);
	    // Count products across all pages
	    int totalProductsFound = 0;
	    boolean hasNextPage = true;
	    while (hasNextPage) {
	        List<WebElement> productItems = driver.findElements(By.xpath("//div[@class='product_list_card_img']"));
	        totalProductsFound += productItems.size();
	        List<WebElement> nextButtons = driver.findElements(By.xpath("//a[contains(@class, 'next')]"));
	        if (!nextButtons.isEmpty() && nextButtons.get(0).isDisplayed() && nextButtons.get(0).isEnabled()) {
	            WebElement nextBtn = nextButtons.get(0);
	            js.executeScript("arguments[0].scrollIntoView(true);", nextBtn);
	            try {
	                nextBtn.click();
	            } catch (Exception e) {
	                js.executeScript("arguments[0].click();", nextBtn);
	            }
	            Common.waitForElement(3);
	        } else {
	            hasNextPage = false;
	        }
	    }
	    // Final verification logs
	    System.out.println("Main filter selected: " + selectedMainFilterName);
	    System.out.println("Sub filter selected: " + selectedSubFilterName + " with count of " + expectedProductCount);
	    System.out.println("Product listing page matched count: " + totalProductsFound);
	    if (expectedProductCount != -1 && expectedProductCount == totalProductsFound) {
	        System.out.println(":white_tick: Product count matches across pages.");
	    } else {
	        System.out.println(":x: Product count mismatch.");
	    }
	    // Cleanup filters
	    click(showFilter);
	    click(filterClearAll);
	    Common.waitForElement(2);
	    click(closeShowFilter);
	}
	public void allsortBy() {
		Common.waitForElement(5);
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();
		Common.waitForElement(2);
		click(sortBy);
		actions.moveToElement(sortByWhatsNew).click().build().perform();;


		String sortOptionXpath ="//li[contains(@class,'filter_sort_list_item ') or contains(@class,'filter_sort_')]";

		 List<WebElement> options = driver.findElements(By.xpath(sortOptionXpath));
		    int totalOptions = options.size();

		    for (int i = 0; i < totalOptions; i++) {
		        // Re-click the Sort By dropdown before each selection
		        sortBy.click();
		        Common.waitForElement(1);

		        // Re-fetch the sort options to avoid stale elements
		        List<WebElement> currentOptions = driver.findElements(By.xpath(sortOptionXpath));

		        if (i < currentOptions.size()) {
		            WebElement option = currentOptions.get(i);
		            String optionText = option.getText().trim();
		            System.out.println("🟢 Clicking Sort Option [" + (i + 1) + "]: " + optionText);

		            option.click();
			        Common.waitForElement(1);
		        } else {
		            System.out.println("❌ Index " + i + " is out of range!");
		        }
		    }
		}
//		try {

			


//			List<Double> allDiscounts = new ArrayList<>();
//
//			// Loop through pagination if exists
//			while (true) {
//				List<WebElement> discountElements = driver.findElements(By.xpath("//span[@class='product_list_cards_discount_percent']"));
//
//				for (WebElement element : discountElements) {
//					String discountText = element.getText(); // Example: "25% Off"
//					double discount = extractDiscount(discountText);
//					allDiscounts.add(discount);
//				}
//
//				// Try to click next if exists, else break
//				List<WebElement> nextButtons = driver.findElements(By.xpath("//div[@class='pagi_next_btn']"));
//				if (nextButtons.size() > 0 && nextButtons.get(0).isDisplayed()) {
//					nextButtons.get(0).click();
//					Common.waitForElement(5);// wait for next page load
//				} else {
//					break;
//				}
//			}
//
//			// Check if the list is sorted in ascending order
//			List<Double> sortedDiscounts = new ArrayList<>(allDiscounts);
//			Collections.sort(sortedDiscounts);
//
//			if (allDiscounts.equals(sortedDiscounts)) {
//				System.out.println("✅ Discount values are correctly sorted from Low to High.");
//			} else {
//				System.out.println("❌ Discount sorting is incorrect!");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
		

	



	private double extractPrice(String priceText) {
		// TODO Auto-generated method stub
		return 0;

	}

	// Helper function to extract numeric discount
	public static double extractDiscount(String text) {
		return Double.parseDouble(text.replaceAll("[^0-9]", ""));
	}















//
//	public void wishListIcon() throws InterruptedException {
//
//	    
//	    String GREEN = "\u001B[32m";
//	    String RED   = "\u001B[31m";
//	    String BLUE   = "\u001B[34m";
//	    String CYAN  = "\u001B[36m";
//	    String RESET = "\u001B[0m";
//	    String line = "──────────────────────────────────────────────────────────────";
//	    System.out.println(CYAN + line + RESET);
//    // Launch home
//    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//    
//    click(filterClearAll);
//	   
//    // Hover and open category
//    Actions actions = new Actions(driver);
//    actions.moveToElement(shopMenu).perform();
//    actions.moveToElement(category).click().perform();
//
//    System.out.println(CYAN + "🔍 Navigated to category page" + RESET);
//    Common.waitForElement(3);
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//	    // ✅ Pick first product
//	    WebElement productCard = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//div[@class='product_list_cards_list ']")
//	            )
//	    );
//
//	    // ✅ Capture product details
//	    String productName = productCard.findElement(
//	            By.xpath(".//h2[@class='product_list_cards_heading']")
//	    ).getText();
//
//	    String productPrice = productCard.findElement(
//	            By.xpath(".//span[@class='prod_current_price']")
//	    ).getText();
//
//	    String productDiscount = productCard.findElement(
//	            By.xpath(".//span[@class='prod_discount_percentage']")
//	    ).getText();
//
//	    System.out.println(CYAN + "📌 Selected Product:" + RESET);
//	    System.out.println("   Name: " + productName);
//	    System.out.println("   Price: " + productPrice);
//	    System.out.println("   Discount: " + productDiscount);
//	    Common.waitForElement(2);
//	    // ✅ Click wishlist icon
//	    WebElement wishBtn = productCard.findElement(
//	            By.xpath(".//div[contains(@class,'product_list_wishlist_icon')]")
//	    );
//	 // Check current wishlist state
//	    String classBefore = wishBtn.getAttribute("class");
//
//	    System.out.println(CYAN + "💡 Wishlist class before: " + classBefore + RESET);
//
//	    // ❤️ If already wish-listed → do nothing
//	    if (classBefore.contains("liked")) {
//
//	        System.out.println(
//	                GREEN + "❤️ Product already in wishlist. Skipping click." + RESET
//	        );
//
//	    } else {
//
//	        System.out.println(
//	                BLUE + "🤍 Product not in wishlist → Clicking..." + RESET
//	        );
//
//	        ((JavascriptExecutor) driver)
//	                .executeScript("arguments[0].click();", wishBtn);
//
//	        // Wait until liked class appears
//	      
//	        wait.until(driver ->
//	                wishBtn.getAttribute("class").contains("liked")
//	        );
//
//	        System.out.println(
//	                GREEN + "❤️ Product successfully added to wishlist" + RESET
//	        );
//	    }
////
////	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", wishBtn);
////	    System.out.println(GREEN + "❤️ Product added to wishlist" + RESET);
//
//	    // ✅ Open Wishlist page
//	    WebElement wishlistIcon = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//a[contains(@class,'wishlist-icon')]")
//	            )
//	    );
//	    
//	    wishlistIcon.click();
//Thread.sleep(2000);
//	    // ✅ Verify product in wishlist
//	    WebElement wishProduct = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//h2[text()='" + productName + "']")
//	            )
//	    );
//
//	    String wishPrice = driver.findElement(
//	            By.xpath("//h2[text()='" + productName + "']/following::span[@class='prod_current_price'][1]")
//	    ).getText();
//
//	    String wishDiscount = driver.findElement(
//	            By.xpath("//h2[text()='" + productName + "']/following::span[@class='prod_discount_percentage'][1]")
//	    ).getText();
//
//	    // ✅ Assertions
//	    Assert.assertEquals(productPrice, wishPrice);
//	    Assert.assertEquals(productDiscount, wishDiscount);
//
//	    System.out.println(GREEN + "✅ Wishlist validation successful!" + RESET);
//	    
//	}


//
//	public void wishListIcon() throws InterruptedException {
//
//	    String GREEN = "\u001B[32m";
//	    String RED   = "\u001B[31m";
//	    String BLUE  = "\u001B[34m";
//	    String CYAN  = "\u001B[36m";
//	    String RESET = "\u001B[0m";
//	    String line = "────────────────────────────────────────────";
//
//	    System.out.println(CYAN + line + RESET);
//
//	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//	    click(zlaataIndiaShopButton);
//
//	    // Hover shop menu
//	    Actions actions = new Actions(driver);
//	    actions.moveToElement(shopMenu).perform();
//	    actions.moveToElement(category).click().perform();
//
//	    System.out.println(CYAN + "🔍 Navigated to category page" + RESET);
//
//	    // Wait for products
//	    wait.until(ExpectedConditions.visibilityOfElementLocated(
//	            By.xpath("//div[contains(@class,'prod_listing_card')]")
//	    ));
//
//	    // Get all products
//	    List<WebElement> products = driver.findElements(
//	            By.xpath("//div[contains(@class,'prod_listing_card')]")
//	    );
//
//	    System.out.println(CYAN + "Total Products on page: " + products.size() + RESET);
//
//	    List<String> alreadyInWishlist = new ArrayList<>();
//	    List<WebElement> notInWishlist = new ArrayList<>();
//
//	    // Separate products based on wishlist status
//	    for (WebElement product : products) {
//	        WebElement wishBtn = product.findElement(
//	                By.xpath(".//button[contains(@class,'product_wishlist_icon')]")
//	        );
//	        String classAttr = wishBtn.getAttribute("class");
//	        String productName = product.findElement(
//	                By.xpath(".//a[contains(@class,'product_list_name')]")
//	        ).getText().trim();
//
//	        if (classAttr.contains("is-liked") || classAttr.contains("liked")) {
//	            alreadyInWishlist.add(productName);
//	        } else {
//	            notInWishlist.add(product);
//	        }
//	    }
//
//	    System.out.println(GREEN + "✅ Products already in wishlist (" + alreadyInWishlist.size() + "):" + RESET);
//	    alreadyInWishlist.forEach(System.out::println);
//
//	    System.out.println(BLUE + "🟡 Products not in wishlist (" + notInWishlist.size() + "):" + RESET);
//	    notInWishlist.forEach(p -> {
//	        try {
//	            System.out.println(p.findElement(By.xpath(".//a[contains(@class,'product_list_name')]")).getText().trim());
//	        } catch (NoSuchElementException ignored) {}
//	    });
//
//	    if (notInWishlist.isEmpty()) {
//	        System.out.println(GREEN + "All products already in wishlist. No product needs to be added." + RESET);
//	        return; // Stop execution
//	    }
//
//	    // Add first available product to wishlist
//	    WebElement productCard = notInWishlist.get(0);
//
//	    String productName = productCard.findElement(
//	            By.xpath(".//a[contains(@class,'product_list_name')]")
//	    ).getText().trim();
//
//	    String productActualPrice;
//	    try{
//	        productActualPrice = productCard.findElement(
//	                By.xpath(".//s[contains(@class,'product_actual_price')]")
//	        ).getText().trim();
//	    } catch(Exception e){
//	        productActualPrice = "No Discount";
//	    }
//
//	    String productOfferPrice;
//	    try{
//	        productOfferPrice = productCard.findElement(
//	                By.xpath(".//span[contains(@class,'product_discounted_price')]")
//	        ).getText().trim();
//	    } catch(Exception e){
//	        productOfferPrice = "No Price";
//	    }
//
//	    String productURL = productCard.findElement(
//	            By.xpath(".//a[contains(@class,'product_list_name')]")
//	    ).getAttribute("href");
//
//	    // Click wishlist icon
//	    WebElement wishBtn = productCard.findElement(
//	            By.xpath(".//button[@aria-label='Add to wishlist']")
//	    );
//
//	    String classBefore = wishBtn.getAttribute("class");
//
//	    if (classBefore.contains("liked")) {
//	        System.out.println(GREEN + "❤️ Product already in wishlist" + RESET);
//	    } else {
//	        System.out.println(BLUE + "🤍 Adding product to wishlist..." + RESET);
//	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", wishBtn);
//	        wait.until(driver -> wishBtn.getAttribute("class").contains("liked"));
//	        System.out.println(GREEN + "❤️ Product added to wishlist" + RESET);
//	    }
//
//	    // Print newly added product details
//	    System.out.println(CYAN + "📌 Newly Added Product Details:" + RESET);
//	    System.out.println("   Name        : " + productName);
//	    System.out.println("   Actual Price: " + productActualPrice);
//	    System.out.println("   Offer Price : " + productOfferPrice);
//	    System.out.println("   Product URL : " + productURL);
//
//	    // Open wishlist page
//	    WebElement wishlistIcon = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//button[contains(@class,'wishlist-icon')]")
//	            )
//	    );
//	    wishlistIcon.click();
//
//	    // Wait for wishlist page
//	    wait.until(ExpectedConditions.visibilityOfElementLocated(
//	            By.xpath("//div[contains(@class,'prod_listing_card')]")
//	    ));
//
//	    List<WebElement> wishlistProducts = driver.findElements(
//	            By.xpath("//div[contains(@class,'prod_listing_card')]")
//	    );
//
//	    boolean productFound = false;
//
//	    System.out.println(CYAN + "🛒 Verifying Newly Added Product in Wishlist:" + RESET);
//
//	    for(WebElement product : wishlistProducts){
//	        String wishName = product.findElement(
//	                By.xpath(".//a[contains(@class,'product_list_name')]")
//	        ).getText().trim();
//
//	        if(wishName.equals(productName)){
//	            productFound = true;
//
//	            String wishActualPrice;
//	            try{
//	                wishActualPrice = product.findElement(
//	                        By.xpath(".//s[contains(@class,'product_actual_price')]")
//	                ).getText().trim();
//	            }catch(Exception e){
//	                wishActualPrice = "No Discount";
//	            }
//
//	            String wishPrice;
//	            try{
//	                wishPrice = product.findElement(
//	                        By.xpath(".//span[contains(@class,'product_discounted_price')]")
//	                ).getText().trim();
//	            }catch(Exception e){
//	                wishPrice = "No Price";
//	            }
//
//	            String wishURL = product.findElement(
//	                    By.xpath(".//a[contains(@class,'product_list_name')]")
//	            ).getAttribute("href");
//
//	            System.out.println("-----------------------------------");
//	            System.out.println("Product Name  : " + wishName);
//	            System.out.println("Actual Price  : " + wishActualPrice);
//	            System.out.println("Offer Price   : " + wishPrice);
//	            System.out.println("Product URL   : " + wishURL);
//
//	            break;
//	        }
//	    }
//
//	    Assert.assertTrue("Wishlist validation failed. Product not found.", productFound);
//	    System.out.println(GREEN + "✅ Added product verified successfully in Wishlist!" + RESET);
//	}
	
	
	
	public void wishListIcon() throws InterruptedException {

	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String BLUE  = "\u001B[34m";
	    String CYAN  = "\u001B[36m";
	    String RESET = "\u001B[0m";
	    String line = "────────────────────────────────────────────";

	    System.out.println(CYAN + line + RESET);

	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    
	    
	    click(zlaataIndiaShopButton);
	    
	    Common.waitForElement(10);
	    
	    click(wishListButton);

	    Common.waitForElement(2);

	    // Remove all existing wishlist products
	    System.out.println(BLUE + "🗑️ Removing existing wishlist products..." + RESET);

	    while (!removeProductFromWishListPage.isEmpty()) {
	        removeProductFromWishListPage.get(0).click();
	        Common.waitForElement(1);
	    }

	    System.out.println(
	            GREEN + "✅ Existing wishlist products removed." + RESET
	    );

	    WebDriverWait wait = new WebDriverWait(
	            driver,
	            Duration.ofSeconds(15)
	    );

	    Actions actions = new Actions(driver);



	    // Hover shop menu
	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(category).click().perform();

	    System.out.println(CYAN + "🔍 Navigated to category page" + RESET);
	    Common.waitForElement(4);
	 // Wait for products
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(@class,'prod_listing_card')]")
	    ));
	    Common.waitForElement(3);
	    // Get all products
	    List<WebElement> products = driver.findElements(
	            By.xpath("//div[contains(@class,'prod_listing_card')]")
	    );

	    // Filter NOT liked products
	    List<WebElement> notLikedProducts = new ArrayList<>();

	    for (WebElement product : products) {
	        try {
	            WebElement wishBtn = product.findElement(
	                    By.xpath(".//button[contains(@class,'product_wishlist_icon')]")
	            );

	            String classAttr = wishBtn.getAttribute("class");

	            if (!classAttr.contains("liked")) {
	                notLikedProducts.add(product);
	            }

	        } catch (Exception ignored) {}
	    }

	    // If no product available
	    if (notLikedProducts.isEmpty()) {
	        System.out.println("All products already in wishlist");
	        return;
	    }

	    // 👉 Pick RANDOM product
	    Random rand = new Random();
	    WebElement productCard = notLikedProducts.get(rand.nextInt(notLikedProducts.size()));

	    // Get details
	    String productName = productCard.findElement(
	            By.xpath(".//a[contains(@class,'product_list_name')]")
	    ).getText().trim();

	    String productURL = productCard.findElement(
	            By.xpath(".//a[contains(@class,'product_list_name')]")
	    ).getAttribute("href");

	    // Click wishlist
	    WebElement wishBtn = productCard.findElement(
	            By.xpath(".//button[contains(@class,'product_wishlist_icon')]")
	    );

	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", wishBtn);

	    // Wait until liked
	    wait.until(d -> wishBtn.getAttribute("class").contains("liked"));

	    System.out.println(GREEN+ "Added Product to Wish List: " + productName + RESET);

	    // Go to Wishlist page
	    driver.findElement(
	            By.xpath("//button[contains(@class,'wishlist-icon')]")
	    ).click();
	    Common.waitForElement(2);
	    // Wait for wishlist products
	    List<WebElement> wishlistProducts = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                    By.xpath("//div[contains(@class,'prod_listing_card')]")
	            )
	    );

	    // Verify product
	    boolean found = false;

	    for (WebElement product : wishlistProducts) {
	        String name = product.findElement(
	                By.xpath(".//a[contains(@class,'product_list_name')]")
	        ).getText().trim();

	        if (name.equals(productName)) {
	            found = true;
	            break;
	        }
	    }

	    // Assertion
	    Assert.assertTrue("Product not found in wishlist", found);

	    System.out.println("✅ Product verified in Wishlist");
	}
//	public void addToCart() throws InterruptedException {
//
//	    
//	    String GREEN = "\u001B[32m";
//	    String RED   = "\u001B[31m";
//	    String CYAN  = "\u001B[36m";
//	    String RESET = "\u001B[0m";
//	    String line = "──────────────────────────────────────────────────────────────";
//	    System.out.println(CYAN + line + RESET);
//    // Launch home
//    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//	 // Hover and open category
//	    Actions actions = new Actions(driver);
//	    actions.moveToElement(shopMenu).perform();
//	    actions.moveToElement(category).click().perform();
//
//	    System.out.println(CYAN + "🔍 Navigated to category page" + RESET);
//
//	    // 🔹 Wait for product card
//	    WebElement productCard = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("(//div[contains(@class,'product_list_cards_list')])[1]")
//	            )
//	    );
//
//	    // 🔹 Capture product details
//	    String productName = productCard.findElement(
//	            By.xpath(".//h2[@class='product_list_cards_heading']")
//	    ).getText();
//
//	    String productPrice = productCard.findElement(
//	            By.xpath(".//span[@class='prod_current_price']")
//	    ).getText();
//
//	    String productDiscount = productCard.findElement(
//	            By.xpath(".//span[@class='prod_discount_percentage']")
//	    ).getText();
//
//	    System.out.println(CYAN + "🛍 Selected Product" + RESET);
//	    System.out.println("   Name     : " + productName);
//	    System.out.println("   Price    : " + productPrice);
//	    System.out.println("   Discount : " + productDiscount);
//
//	    // 🔹 Click Add to Bag (PLP)
//	    WebElement addToBagBtn = productCard.findElement(
//	            By.xpath(".//div[contains(@class,'product_list_add_to_cart')]")
//	    );
//
//	    ((JavascriptExecutor) driver)
//	            .executeScript("arguments[0].scrollIntoView({block:'center'});", addToBagBtn);
//	    ((JavascriptExecutor) driver)
//	            .executeScript("arguments[0].click();", addToBagBtn);
//	    Common.waitForElement(2);
//	    System.out.println(GREEN + "✅ PLP Add to Bag clicked" + RESET);
//
//	    // 🔹 Capture popup product name
//	    WebElement popupName = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//h4[@class='prod_name']")
//	            )
//	    );
//
//	    String popupProductName = popupName.getText().trim();
//	    System.out.println(GREEN + "🧾 Popup Product: " + popupProductName + RESET);
//	    Common.waitForElement(2);
//	    // 🔹 Click Add to Bag in popup
//	    WebElement popupAddBtn = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//button[contains(@class,'add_bag_prod_buy_now_btn')]")
//	            )
//	    );
//	    popupAddBtn.click();
//
//	    System.out.println(GREEN + "✅ Product added to cart" + RESET);
//	    Common.waitForElement(2);
//	    // 🔹 Open cart
//	    WebElement cartIcon = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//a[contains(@class,'Cls_cart_btn')]")
//	            )
//	    );
//	    cartIcon.click();
//
//	    // 🔹 Verify product in cart
//	    WebElement cartProduct = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//a[contains(@class,'cp_name') and contains(text(),'" + popupProductName + "')]")
//	            )
//	    );
//	    Common.waitForElement(2);
//	    String cartPrice = cartProduct.findElement(
//	            By.xpath("./ancestor::div[contains(@class,'cart_prod_card')]//div[@class='cp_current_price']")
//	    ).getText();
//
//	    String cartDiscount = cartProduct.findElement(
//	            By.xpath("./ancestor::div[contains(@class,'cart_prod_card')]//div[@class='cp_discount_percentage']")
//	    ).getText();
//
//	    // ✅ Assertions
//	    Assert.assertEquals(cartPrice, productPrice);
//	    Assert.assertEquals(cartDiscount, productDiscount);
//
//	    System.out.println(GREEN + "✅ Cart validation successful!" + RESET);
//	}
	
	
//	public void addToCart() throws InterruptedException {
//	    
//	    String GREEN = "\u001B[32m";
//	    String RED   = "\u001B[31m";
//	    String CYAN  = "\u001B[36m";
//	    String RESET = "\u001B[0m";
//	    String line = "──────────────────────────────────────────────────────────────";
//	    System.out.println(CYAN + line + RESET);
//
//	    // Launch home
//	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//
//	    click(zlaataIndiaShopButton);
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//
//	    // Hover and open category
//	    Actions actions = new Actions(driver);
//	    actions.moveToElement(shopMenu).perform();
//	    actions.moveToElement(category).click().perform();
//
//	    System.out.println(CYAN + "🔍 Navigated to category page" + RESET);
//
//	    // 🔹 Wait for product card
//	    WebElement productCard = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//div[@class='prod_listing_card']")
//	            )
//	    );
//
//	    // 🔹 Capture product details
//	    String productName = productCard.findElement(
//	            By.xpath(".//a[@class='product_list_name']")
//	    ).getText();
//
//	    String productPrice = productCard.findElement(
//	            By.xpath(".//span[@class='product_discounted_price']")
//	    ).getText();
//
//	    String productDiscount = productCard.findElement(
//	            By.xpath(".//span[@class='product_discounted_percentage']")
//	    ).getText();
//
//	    System.out.println(CYAN + "🛍 Selected Product" + RESET);
//	    System.out.println("   Name     : " + productName);
//	    System.out.println("   Price    : " + productPrice);
//	    System.out.println("   Discount : " + productDiscount);
//
//	    // 🔹 Click Add to Bag (PLP)
//	    WebElement addToBagBtn = productCard.findElement(
//	            By.xpath("//button[@class='prod_add_to_cart ClsSingleCart']")
//	    );
//
//	    ((JavascriptExecutor) driver)
//	            .executeScript("arguments[0].scrollIntoView({block:'center'});", addToBagBtn);
//	    ((JavascriptExecutor) driver)
//	            .executeScript("arguments[0].click();", addToBagBtn);
//	    Common.waitForElement(2);
//	    System.out.println(GREEN + "✅ PLP Add to Bag clicked" + RESET);
//
//	    // 🔹 Capture popup product name
//	    WebElement popupName = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//h4[@class='prod_name']")
//	            )
//	    );
//
//	    String popupProductName = popupName.getText().trim();
//	    System.out.println(GREEN + "🧾 Popup Product: " + popupProductName + RESET);
//	    Common.waitForElement(2);
//	    
//	    
//	  
//	    // 🔹 Click Add to Bag in popup
//	    WebElement popupAddBtn = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//button[contains(@class,'add_bag_prod_buy_now_btn')]")
//	            )
//	    );
//	    popupAddBtn.click();
//
//	    System.out.println(GREEN + "✅ Product added to cart" + RESET);
//	    Common.waitForElement(2);
//
//	    // 🔹 Scroll to top and click cart
//	    WebElement cartIcon = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//button[@title='Cart']")
//	            )
//	    );
//	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'start'});", cartIcon);
//	    Common.waitForElement(1);
//	    cartIcon.click();
//
//	    // 🔹 Verify product in cart
//	    WebElement cartProduct = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//a[contains(@class,'cp_name') and contains(text(),'" + popupProductName + "')]")
//	            )
//	    );
//	    Common.waitForElement(2);
//
//	    String cartPrice = cartProduct.findElement(
//	            By.xpath("./ancestor::div[contains(@class,'cart_prod_card')]//div[@class='cp_current_price']")
//	    ).getText();
//
//	    String cartDiscount = cartProduct.findElement(
//	            By.xpath("./ancestor::div[contains(@class,'cart_prod_card')]//div[@class='cp_discount_percentage']")
//	    ).getText();
//
//	    // ✅ Assertions
//	    Assert.assertEquals(cartPrice, productPrice);
//	    Assert.assertEquals(cartDiscount, productDiscount);
//
//	    System.out.println(GREEN + "✅ Cart validation successful!" + RESET);
//	}
//	
	
	public void addToCart() throws InterruptedException {

	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String CYAN  = "\u001B[36m";
	    String RESET = "\u001B[0m";

	    String line = "──────────────────────────────────────────────────────────────";

	    System.out.println(CYAN + line + RESET);

	    // Launch home
	    deleteAllProductsFromCart();

	    WebDriverWait wait = new WebDriverWait(
	            driver,
	            Duration.ofSeconds(15)
	    );

	    // Hover and open category
	    Actions actions = new Actions(driver);

	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(categoryDresses).click().perform();

	    System.out.println(
	            CYAN + "🔍 Navigated to category page" + RESET
	    );

	    Common.waitForElement(5);

	    // Wait for product card
	    WebElement productCard = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//div[@class='prod_listing_card']")
	            )
	    );

	    Common.waitForElement(3);

	    // Check if product is out of stock
	    List<WebElement> stockLabels = productCard.findElements(
	            By.xpath(
	                    ".//span[contains(@class,'prod_listing_hurry') and contains(text(),'Out of Stock')]"
	            )
	    );

	    if (!stockLabels.isEmpty()) {

	        System.out.println(
	                RED + "❌ Product is Out of Stock. Skipping Add to Cart." + RESET
	        );

	        return;
	    }

	    // Capture product details
	    String productName = productCard.findElement(
	            By.xpath(".//a[@class='product_list_name']")
	    ).getText().trim();

	    String productPrice = productCard.findElement(
	            By.xpath(".//span[@class='product_discounted_price']")
	    ).getText().trim();

	    // Capture discount only if available
	    List<WebElement> discountElements = productCard.findElements(
	            By.xpath(".//span[@class='product_discounted_percentage']")
	    );

	    String productDiscount = "";

	    if (!discountElements.isEmpty()) {

	        productDiscount = discountElements.get(0).getText().trim();

	    }

	    System.out.println(
	            CYAN + "🛍 Selected Product" + RESET
	    );

	    System.out.println(
	            "   Name     : " + productName
	    );

	    System.out.println(
	            "   Price    : " + productPrice
	    );

	    if (!productDiscount.isEmpty()) {

	        System.out.println(
	                "   Discount : " + productDiscount
	        );

	    } else {

	        System.out.println(
	                "   Discount : No discount"
	        );
	    }

	    // Click Add to Bag (PLP)
	    WebElement addToBagBtn = productCard.findElement(
	            By.xpath("//button[@class='prod_add_to_cart ClsSingleCart']")
	    );

	    ((JavascriptExecutor) driver)
	            .executeScript(
	                    "arguments[0].scrollIntoView({block:'center'});",
	                    addToBagBtn
	            );

	    ((JavascriptExecutor) driver)
	            .executeScript(
	                    "arguments[0].click();",
	                    addToBagBtn
	            );

	    Common.waitForElement(2);

	    System.out.println(
	            GREEN + "✅ PLP Add to Bag clicked" + RESET
	    );

	    // Capture popup product name
	    WebElement popupName = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//h4[@class='prod_name']")
	            )
	    );

	    String popupProductName = popupName.getText().trim();

	    System.out.println(
	            GREEN + "🧾 Popup Product: "
	                    + popupProductName
	                    + RESET
	    );

	    Common.waitForElement(2);

	    // Click Add to Bag in popup
	    WebElement popupAddBtn = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath(
	                            "//button[contains(@class,'add_bag_prod_buy_now_btn')]"
	                    )
	            )
	    );

	    popupAddBtn.click();

	    System.out.println(
	            GREEN + "✅ Product added to cart" + RESET
	    );

	    Common.waitForElement(2);

	    // Scroll to top before clicking cart
	    ((JavascriptExecutor) driver)
	            .executeScript("window.scrollTo(0, 0);");

	    Common.waitForElement(1);

	    WebElement cartIcon = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[@title='Cart']")
	            )
	    );

	    cartIcon.click();

	    // Verify product in cart
	    WebElement cartProduct = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath(
	                            "//a[contains(@class,'cp_name') and contains(text(),'"
	                                    + popupProductName
	                                    + "')]"
	                    )
	            )
	    );

	    Common.waitForElement(2);

	    // Capture cart price
	    String cartPrice = cartProduct.findElement(
	            By.xpath(
	                    "./ancestor::div[contains(@class,'cart_prod_card')]"
	                            + "//div[@class='cp_current_price']"
	            )
	    ).getText().trim();

	    // Capture cart discount only if available
	    List<WebElement> cartDiscountElements = cartProduct.findElements(
	            By.xpath(
	                    "./ancestor::div[contains(@class,'cart_prod_card')]"
	                            + "//div[@class='cp_discount_percentage']"
	            )
	    );

	    String cartDiscount = "";

	    if (!cartDiscountElements.isEmpty()) {

	        cartDiscount = cartDiscountElements.get(0).getText().trim();
	    }

	    // Verify price
	    Assert.assertEquals(
	            cartPrice,
	            productPrice
	    );

	    // Verify discount only when product has discount
	    if (!productDiscount.isEmpty()) {

	        Assert.assertEquals(
	                cartDiscount,
	                productDiscount
	        );

	        System.out.println(
	                GREEN + "✅ Discount validation successful: "
	                        + cartDiscount
	                        + RESET
	        );
	    }

	    System.out.println(
	            GREEN + "✅ Cart validation successful!" + RESET
	    );
	}	
	
//	
//	public void addToCart() throws InterruptedException {
//
//	    String GREEN = "\u001B[32m";
//	    String RED   = "\u001B[31m";
//	    String CYAN  = "\u001B[36m";
//	    String RESET = "\u001B[0m";
//	    String line = "──────────────────────────────────────────────────────────────";
//	    System.out.println(CYAN + line + RESET);
//
//	    // Launch home
//	    
//	    deleteAllProductsFromCart();	  
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//
//	    // Hover and open category
//	    Actions actions = new Actions(driver);
//	    actions.moveToElement(shopMenu).perform();
//	    actions.moveToElement(categoryDresses).click().perform();
//
//	    System.out.println(CYAN + "🔍 Navigated to category page" + RESET);
//	    
//	    Common.waitForElement(5);
//
//	    // Wait for product card
//	    WebElement productCard = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//div[@class='prod_listing_card']")
//	            )
//	    );
//Common.waitForElement(3);
//	    // Check if product is out of stock
//	    List<WebElement> stockLabels = productCard.findElements(
//	            By.xpath(".//span[contains(@class,'prod_listing_hurry') and contains(text(),'Out of Stock')]")
//	    );
//
//	    if (!stockLabels.isEmpty()) {
//	        System.out.println(RED + "❌ Product is Out of Stock. Skipping Add to Cart." + RESET);
//	        return; // Stop this method or continue with another product
//	    }
//
//	    // 🔹 Capture product details
//	    String productName = productCard.findElement(
//	            By.xpath(".//a[@class='product_list_name']")
//	    ).getText();
//
//	    String productPrice = productCard.findElement(
//	            By.xpath(".//span[@class='product_discounted_price']")
//	    ).getText();
//
//	    String productDiscount = productCard.findElement(
//	            By.xpath(".//span[@class='product_discounted_percentage']")
//	    ).getText();
//
//	    System.out.println(CYAN + "🛍 Selected Product" + RESET);
//	    System.out.println("   Name     : " + productName);
//	    System.out.println("   Price    : " + productPrice);
//	    System.out.println("   Discount : " + productDiscount);
//
//	    // 🔹 Click Add to Bag (PLP)
//	    WebElement addToBagBtn = productCard.findElement(
//	            By.xpath("//button[@class='prod_add_to_cart ClsSingleCart']")
//	    );
//
//	    ((JavascriptExecutor) driver)
//	            .executeScript("arguments[0].scrollIntoView({block:'center'});", addToBagBtn);
//	    ((JavascriptExecutor) driver)
//	            .executeScript("arguments[0].click();", addToBagBtn);
//	    Common.waitForElement(2);
//	    System.out.println(GREEN + "✅ PLP Add to Bag clicked" + RESET);
//
//	    // 🔹 Capture popup product name
//	    WebElement popupName = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//h4[@class='prod_name']")
//	            )
//	    );
//
//	    String popupProductName = popupName.getText().trim();
//	    System.out.println(GREEN + "🧾 Popup Product: " + popupProductName + RESET);
//	    Common.waitForElement(2);
//
//	    // 🔹 Click Add to Bag in popup
//	    WebElement popupAddBtn = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//button[contains(@class,'add_bag_prod_buy_now_btn')]")
//	            )
//	    );
//	    popupAddBtn.click();
//
//	    System.out.println(GREEN + "✅ Product added to cart" + RESET);
//	    Common.waitForElement(2);
//
//	 // 🔹 Scroll to top before clicking cart
//	    ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);"); // Scroll to top
//	    Common.waitForElement(1); // small wait
//
//	    WebElement cartIcon = wait.until(
//	        ExpectedConditions.elementToBeClickable(
//	            By.xpath("//button[@title='Cart']")
//	        )
//	    );
//	    cartIcon.click();
//
//	    // 🔹 Verify product in cart
//	    WebElement cartProduct = wait.until(
//	            ExpectedConditions.visibilityOfElementLocated(
//	                    By.xpath("//a[contains(@class,'cp_name') and contains(text(),'" + popupProductName + "')]")
//	            )
//	    );
//	    Common.waitForElement(2);
//
//	    String cartPrice = cartProduct.findElement(
//	            By.xpath("./ancestor::div[contains(@class,'cart_prod_card')]//div[@class='cp_current_price']")
//	    ).getText();
//
//	    String cartDiscount = cartProduct.findElement(
//	            By.xpath("./ancestor::div[contains(@class,'cart_prod_card')]//div[@class='cp_discount_percentage']")
//	    ).getText();
//
//	    // ✅ Assertions
//	    Assert.assertEquals(cartPrice, productPrice);
//	    Assert.assertEquals(cartDiscount, productDiscount);
//
//	    System.out.println(GREEN + "✅ Cart validation successful!" + RESET);
//	}
	
	
	
	
	
	public void deleteAllProductsFromCart() {
	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationUrl());

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Open cart
	    WebElement cartBtn = wait.until(
	            ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='header_cta_btn Cls_cart_btn ']"))
	    );
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cartBtn);
	    cartBtn.click();
	    Common.waitForElement(10);

	    // Check if cart is already empty
	    try {
	        if (driver.findElement(By.xpath("//a[@class='empty_bag_shop_btn btn___2']")).isDisplayed()) {
	            System.out.println("🛍️ Cart already empty. No delete action needed.");
	            return;
	        }
	    } catch (NoSuchElementException ignored) {}

	    // Loop to delete all products
	    while (true) {
	        List<WebElement> deleteBtns = driver.findElements(By.xpath("//div[@title='Delete']"));

	        if (deleteBtns.isEmpty()) {
	            System.out.println("✅ No more products to delete.");
	            break;
	        }

	        for (WebElement deleteBtn : deleteBtns) {
	            try {
	                // Scroll the element into view slightly above the button to avoid sticky header
	                ((JavascriptExecutor) driver).executeScript(
	                        "arguments[0].scrollIntoView({block:'center', inline:'center'});", deleteBtn);

	                // Use JavaScript click to bypass interception
	                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);

	                System.out.println("🗑️ Product deleted");
	                Common.waitForElement(1);

	            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
	                System.out.println("⚠️ Delete button not clickable, retrying...");
	                break; // Exit for-loop to refresh list
	            }
	        }
	    }

	    // Final confirmation
	    try {
	        if (driver.findElement(By.xpath("//a[@class='empty_bag_shop_btn btn___2']")).isDisplayed()) {
	            System.out.println("🛍️ Cart is empty, Continue Shopping displayed.");
	        }
	    } catch (NoSuchElementException e) {
	        System.out.println("ℹ️ Bag is not empty message not found.");
	    }
	}
	
	
//	public void gridToggle() {
//
//	    // 🎨 Console Colors
//	    String GREEN  = "\u001B[32m";
//	    String RED    = "\u001B[31m";
//	    String CYAN   = "\u001B[36m";
//	    String YELLOW = "\u001B[33m";
//	    String RESET  = "\u001B[0m";
//
//	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//	    click(zlaataIndiaShopButton);
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//	    Actions actions = new Actions(driver);
//
//	    System.out.println(CYAN + "🔍 Navigating to category..." + RESET);
//
//	    // ---------- NAVIGATION ----------
//	    wait.until(ExpectedConditions.visibilityOf(shopMenu));
//	    actions.moveToElement(shopMenu).perform();
//
//	    wait.until(ExpectedConditions.elementToBeClickable(categoryDresses));
//	    actions.moveToElement(categoryDresses).click().perform();
//
//	    wait.until(ExpectedConditions.visibilityOfElementLocated(
//	            By.xpath("//div[contains(@class,'prod_listing')]")
//	    ));
//
//	    System.out.println(YELLOW + "📦 Product listing page loaded" + RESET);
//
//	    // ---------- GRID LOCATORS ----------
//	    By twoGrid = By.xpath("//span[@data-layout-toggle='2']");
//	    By fourGrid = By.xpath("//span[@data-layout-toggle='4']");
//
//	    // ---------- CLICK 2 GRID ----------
//	    try {
//	        wait.until(ExpectedConditions.elementToBeClickable(twoGrid)).click();
//
//	        wait.until(ExpectedConditions.attributeContains(twoGrid, "class", "active"));
//
//	        System.out.println(GREEN + "✅ 2-Grid layout applied successfully" + RESET);
//
//	    } catch (Exception e) {
//	        System.out.println(RED + "❌ Failed to apply 2-Grid layout" + RESET);
//	    }
//
//	    // ---------- CLICK 4 GRID ----------
//	    try {
//	        wait.until(ExpectedConditions.elementToBeClickable(fourGrid)).click();
//
//	        wait.until(ExpectedConditions.attributeContains(fourGrid, "class", "active"));
//
//	        System.out.println(GREEN + "✅ 4-Grid layout applied successfully" + RESET);
//
//	    } catch (Exception e) {
//	        System.out.println(RED + "❌ Failed to apply 4-Grid layout" + RESET);
//	    }
//	}
//

	
	public void gridToggle() {

	    // 🎨 Console Colors
	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    click(zlaataIndiaShopButton);

	    Common.waitForElement(5);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Actions actions = new Actions(driver);

	    System.out.println(CYAN + "🔍 Navigating to category..." + RESET);

	    // ---------- NAVIGATION ----------
	    wait.until(ExpectedConditions.visibilityOf(shopMenu));
	    actions.moveToElement(shopMenu).perform();

	    wait.until(ExpectedConditions.elementToBeClickable(categoryDresses));
	    actions.moveToElement(categoryDresses).click().perform();

	    // Wait for PLP
	    By gridContainer = By.xpath("//div[contains(@class,'product_listing_grid')]");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(gridContainer));

	    System.out.println(YELLOW + "📦 Product listing page loaded" + RESET);

	    // ---------- GRID LOCATORS ----------
	    By twoGrid = By.xpath("//span[@data-layout-toggle='2']");
	    By fourGrid = By.xpath("//span[@data-layout-toggle='4']");

	    // ---------- CLICK 2 GRID ----------
	    try {
	        wait.until(ExpectedConditions.elementToBeClickable(twoGrid)).click();

	        // ✅ Verify using DOM (BEST)
	        wait.until(ExpectedConditions.attributeContains(
	                gridContainer, "class", "product_columns_2"));

	        System.out.println(GREEN + "✅ 2-Grid layout applied (DOM Verified)" + RESET);

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Failed to apply 2-Grid layout" + RESET);
	    }

	    // ---------- CLICK 4 GRID ----------
	    try {
	        wait.until(ExpectedConditions.elementToBeClickable(fourGrid)).click();

	        // ✅ Verify using DOM (BEST)
	        wait.until(ExpectedConditions.attributeContains(
	                gridContainer, "class", "product_columns_4"));

	        System.out.println(GREEN + "✅ 4-Grid layout applied (DOM Verified)" + RESET);

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Failed to apply 4-Grid layout" + RESET);
	    }
	}
	
	
	public void verifyUserAbleToClickOnColorButton() {

		 String GREEN  = "\u001B[32m";
		    String RED    = "\u001B[31m";
		    String CYAN   = "\u001B[36m";
		    String RESET  = "\u001B[0m";

		    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
		    click(zlaataIndiaShopButton);

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		    Actions actions = new Actions(driver);
		    
		    Random random = new Random();

		    System.out.println(CYAN + "🔍 Navigating to category..." + RESET);

		    // ---------- NAVIGATION ----------
		    wait.until(ExpectedConditions.visibilityOf(shopMenu));
		    actions.moveToElement(shopMenu).perform();

		    wait.until(ExpectedConditions.elementToBeClickable(category));
		    actions.moveToElement(category).click().perform();
		    
		    Common.waitForElement(5);
		
			  By productCard = By.xpath("//div[contains(@class,'prod_listing_card')]");
			    List<WebElement> products = wait.until(
			            ExpectedConditions.visibilityOfAllElementsLocatedBy(productCard)
			    );

			    List<Integer> validProductIndexes = new ArrayList<>();

			    // ✅ STEP 1: Collect products having NON-ACTIVE colors
			    for (int i = 0; i < products.size(); i++) {

			        List<WebElement> colors = products.get(i).findElements(
			                By.xpath(".//span[contains(@class,'prod_swatch_color')]")
			        );

			        for (WebElement c : colors) {
			            String cls = c.getAttribute("class");

			            if (cls != null && !cls.contains("active")) {
			                validProductIndexes.add(i);
			                break;
			            }
			        }
			    }

			    if (validProductIndexes.isEmpty()) {
			        System.out.println(RED + "❌ No valid products found" + RESET);
			        return;
			    }

			    // ✅ STEP 2: Pick RANDOM product
			    int productIndex = validProductIndexes.get(
			            random.nextInt(validProductIndexes.size())
			    );

			    WebElement product = products.get(productIndex);

			    // ✅ STEP 3: Get NON-ACTIVE colors
			    List<WebElement> colors = product.findElements(
			            By.xpath(".//span[contains(@class,'prod_swatch_color')]")
			    );

			    List<WebElement> nonActiveColors = new ArrayList<>();

			    for (WebElement c : colors) {
			        String cls = c.getAttribute("class");

			        if (cls != null && !cls.contains("active")) {
			            nonActiveColors.add(c);
			        }
			    }

			    // ✅ STEP 4: Pick RANDOM color
			    WebElement color = nonActiveColors.get(
			            random.nextInt(nonActiveColors.size())
			    );

			    String colorName = color.getAttribute("title");

			    System.out.println(CYAN + "🎨 Clicking Random Product Index: " + productIndex + RESET);
			    System.out.println(CYAN + "🎨 Clicking Random Color: " + colorName + RESET);

			    // CLICK
			    color.click();

			    // Wait refresh
			    wait.until(ExpectedConditions.stalenessOf(product));

			    // Re-fetch
			    products = driver.findElements(productCard);
			    product = products.get(productIndex);

			    String productName = product.findElement(
			            By.xpath(".//a[contains(@class,'product_list_name')]")
			    ).getText().trim();

			    System.out.println(GREEN + "✅ Product Name: " + productName + RESET);
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
