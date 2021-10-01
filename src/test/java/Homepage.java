import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends PageObject{

    @FindBy(css = ".CwnOs4oL8oWmYWqSY_56._mmZzV2aOFvnIrTf86Ff._oLop0y04dgY7BZRvIg3.w8swYPJjmSz5vqWq1nw2")
    private WebElement loginButton;
    @FindBy(css = "div.xalhSun51Qqw6pW5d7vv:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > input:nth-child(1)")
    private WebElement loginInputEmail;
    @FindBy(css = "div.xalhSun51Qqw6pW5d7vv:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > input:nth-child(1)")
    private WebElement loginInputPassword;
    @FindBy(css = "#authentication-form button[class^='CwnOs4oL8oWmYWqSY_56']")
    private WebElement btnSubmitCredentials;
    @FindBy(css = ".P5D11awNBX__0OFQm_Rd")
    private WebElement profileIcon;
    @FindBy(css = ".AYLLt4hBkv5F_Sng5YzW")
    private WebElement availableProductCounter;
    @FindBy(css = "._xq8z8zXvhJlYpbW_Z7p")
    public WebElement productFilterSection;
    @FindBy(css = "div._5u9c1fu8EANChv_Y_UL:nth-child(2) > div:nth-child(1) > div:nth-child(1) > label:nth-child(1)")
    private WebElement firstFilterInCategorySection;
    @FindBy(css = ".WoEwkpk6Jm_suqrOwBhK .CwnOs4oL8oWmYWqSY_56")
    private WebElement btnRemoveAllFilters;
    @FindBy(css = ".zZV9AKJPLU6SBTKQL9md > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > a:nth-child(1)")
    private WebElement firstProductOnPage;

    @FindBy(css = "._X19n206Kt95vuMdcTz8")
    private WebElement productSortBtn;

    @FindBy(css = ".MuiList-root")
    private WebElement productSortDropdownMenu;
    @FindBy(css = ".MuiList-root li")
    private List<WebElement> productSortMenuOptions;

    @FindBy(css = "li.MuiButtonBase-root:nth-child(1)")
    private WebElement sortByRelevancy;
    @FindBy(css = "li.MuiButtonBase-root:nth-child(2)")
    private WebElement sortByMostPopular;
    @FindBy(css = "li.MuiButtonBase-root:nth-child(3)")
    private WebElement sortByProductRelevancy;
    @FindBy(css = "li.MuiButtonBase-root:nth-child(4)")
    private WebElement sortByLowPrice;
    @FindBy(css = "li.MuiButtonBase-root:nth-child(5)")
    private WebElement sortByHighPrice;

    public WebDriverWait webDriverWait;

    public Homepage(WebDriver driver) {
        super(driver);
    }

    /**
     *
     * Logs in the application and validates that the profile icon is displayed
     * @param email email credentials
     * @param password password
     */

    public void login(String email, String password) throws InterruptedException {
        loginButton.click();
        Thread.sleep(500);
        loginInputEmail.sendKeys(email);
        loginInputPassword.sendKeys(password);
        btnSubmitCredentials.click();
        Thread.sleep(2000);
        Assert.assertTrue("Profile Icon not displayed, user not logged in or icon missing", profileIcon.isDisplayed());
    }

    /**
     *
     * @return Returns the number of products available on the product listing page
     */
    public String getAvailableProducts() {
        String[] words = availableProductCounter.getText().split(" ");
        return words[0];
    }

    /**
     * Method applies the first filter on the "category" section
     */
    public void applyProductFilter() throws InterruptedException {
        firstFilterInCategorySection.click();
        Thread.sleep(2000);
    }

    /**
     * Method removes all applied filters on product page
     */
    public void removeAllFilters() throws InterruptedException {
        btnRemoveAllFilters.click();
        Thread.sleep(2000);
    }

    /**
     *
     * @return Return the href value of the first product on the page
     */
    public String getProductHrefValue() {
        return firstProductOnPage.getAttribute("href");
    }

    /**
     * Method clicks on the first product from the product list page and also returns the href of the product it opens
     */
    public String clickOnProduct() throws InterruptedException {
        String productHrefValue = firstProductOnPage.getAttribute("href");
        firstProductOnPage.click();
        Thread.sleep(2000);
        return productHrefValue;
    }

    /**
     * Method clicks on the product sorting button and opens the product sorting dropdown menu
     */

    public void openSortingMenu() throws InterruptedException {
        productSortBtn.click();
        Thread.sleep(2000);
        Assert.assertTrue(productSortDropdownMenu.isDisplayed());
    }

    /**
     * Method creates a list of string containing the textContent of the elements in the product sorting dropdown menu
     * @return Returns a list of the names of the sorting options
     */
    public List<String> getSortingOptionsNames() throws InterruptedException {
        if(!productSortDropdownMenu.isDisplayed()) {
            openSortingMenu();
        }
        ArrayList<String> sortOptionName = new ArrayList<>();
        for (WebElement element: productSortMenuOptions) {
            sortOptionName.add(element.getAttribute("textContent"));
        }
        return sortOptionName;
    }

    /**
     * Method opens the product sort menu and sorts the products by the highest price
     */
    public void sortProductByHighestPrice() throws InterruptedException {
        if(!checkElementExists(driver, productSortDropdownMenu)) {
            openSortingMenu();
        }
        sortByHighPrice.click();
        Thread.sleep(2000);
    }

    /**
     * Method opens the product sort menu and sorts the products by the lowest price
     */
    public void sortProductByLowestPrice() throws InterruptedException {
        if(!checkElementExists(driver, productSortDropdownMenu)) {
            openSortingMenu();
        }
        sortByLowPrice.click();
        Thread.sleep(2000);
    }

    /**
     * Method opens the product sort menu and sorts the products by product relevancy
     */
    public void sortProductByRelevancy() throws InterruptedException {
        if(!checkElementExists(driver, productSortDropdownMenu)) {
            openSortingMenu();
        }
        sortByRelevancy.click();
        Thread.sleep(2000);
    }

    /**
     * @param element The name of the webElement that is to be trimmed
     * @return Returns the string selector of the webElement
     */
    public String extractLocator(WebElement element) {
        String textElement = element.toString();
        return textElement.substring(textElement.lastIndexOf(": ") + 2, textElement.length() - 1);
    }

    /**
     * @param element The name of the webElement that is checked
     * @return Returns true or false in accordance with existence of element on page
     */
    public boolean checkElementExists(WebDriver driver, WebElement element) {

        this.webDriverWait = new WebDriverWait(driver, 2);
        try {
            webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(extractLocator(element))));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }
}
