import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;

public class SmokeTest {

    private WebDriver driver;
    public Homepage homepage;

    @BeforeTest()
    public void setUp() {
        System.setProperty(Utils.CHROME_DRIVER_KEY, Utils.CHROME_DRIVER_LOCATION);
        driver = new ChromeDriver();
        homepage = new Homepage(driver);
        driver.get(Utils.BASE_URL);
    }

    @Test(priority = 1)
    public void login() throws InterruptedException {
        System.out.println("User logs in the application");

        homepage.login("test@tundra.com","test@tundra.com");
    }

    @Test(priority = 2, dependsOnMethods = "login")
    public void validateAvailableProductsCounter() throws InterruptedException {
        System.out.println("The available product counter functinality is validated");

        String availableProductsNoFilter = homepage.getAvailableProducts();
        System.out.println("Found " + availableProductsNoFilter + " products available before filter is applied");
        homepage.applyProductFilter();
        String availableProductsFiltered = homepage.getAvailableProducts();
        System.out.println("Found " + availableProductsFiltered + " products available after filter was applied");
        Assert.assertNotSame(availableProductsNoFilter, availableProductsFiltered);
        homepage.removeAllFilters();
        String availableProductsFilterRemoved = homepage.getAvailableProducts();
        System.out.println("Found " + availableProductsFilterRemoved + " products available after filter was removed");
        Assert.assertEquals(availableProductsNoFilter, availableProductsFilterRemoved);
    }

    @Test(priority = 3, dependsOnMethods = "login")
    public void validateExistenceOfFiltersSection() {
        System.out.println("Validates that the filter section is displayed on the product search results page");

        Assert.assertTrue(homepage.productFilterSection.isDisplayed());
    }

    @Test(priority = 4, dependsOnMethods = "login")
    public void validateProductSortButton() throws InterruptedException {
        System.out.println("Validates on the product search results page, the product sorting button can be clicked and the sorting dropdown contains the sorting criteria options");

        List<String> expectedSortOptions = Arrays.asList("Relevancy", "Most Popular", "Product Rating", "Lowest Price", "Highest Price");
        homepage.openSortingMenu();
        Assert.assertEquals(expectedSortOptions, homepage.getSortingOptionsNames());
    }

    @Test(priority = 5, dependsOnMethods = "login")
    public void validateProductSort() throws InterruptedException {
        System.out.println("Validates that the product sorting functionality works on the product search results page");

        homepage.sortProductByLowestPrice();
        String lowPriceSortFirstProduct = homepage.getProductHrefValue();
        homepage.sortProductByHighestPrice();
        String highPriceSortFirstProduct = homepage.getProductHrefValue();
        Assert.assertNotSame(lowPriceSortFirstProduct, highPriceSortFirstProduct);
    }

    @Test(priority = 6, dependsOnMethods = "login")
    public void accessProductPage() throws InterruptedException{
        System.out.println("Validates that the user can access a product from the product search results page");

        String productHref = homepage.clickOnProduct();
        Assert.assertEquals(productHref, driver.getCurrentUrl());
    }

    @AfterTest()
    public void cleanup(){
        driver.close();
    }

}
