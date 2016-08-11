package test.selenium;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestPardot {

	private WebDriver driver;

	private String originalListName;

	private String listId;

	private WebDriverWait webDriverWait;

	public TestPardot() {

	}

	public TestPardot(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return this.driver;
	}

	/**
	 * @param driver the driver to set
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	private void openPage(String URL) {

		this.driver.get(URL);

	}

	public void step1Login() {
		openPage(TestConstants.BASE_URL);

		WebElement username = this.driver.findElement(By.id("email_address"));
		if (username != null) {
			username.clear();
			username.sendKeys(TestConstants.USER_NAME);
		}

		WebElement password = this.driver.findElement(By.id("password"));
		if (password != null) {
			password.clear();
			password.sendKeys(TestConstants.PASSWORD);
		}

		WebElement signInButton = this.driver.findElement(By.name("commit"));
		if (signInButton != null) {
			signInButton.click();
		}

	}

	public boolean isLoginSuccess() {
		try {
			getWebDriverWait().until(ExpectedConditions.titleContains("Dashboard"));
			System.out.println("Step 1: Logged in Successfully");
			return true;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Current page title does not contain word Dashboard.");
			return false;
		}

	}

	public void step2CreateList() {
		openPage(TestConstants.BASE_URL + "/list");
		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By
						.id("listxistx_link_create")));

		WebElement addListButton = this.driver
				.findElement(By.id("listxistx_link_create"));
		addListButton.click();

		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By.id("name")));

		WebElement listName = this.driver.findElement(By.id("name"));
		this.originalListName = RandomStringUtils.randomAlphabetic(10);

		listName.sendKeys(this.originalListName);

		WebElement button = this.driver.findElement(By.id("save_information"));
		button.click();

		getWebDriverWait().until(ExpectedConditions.urlContains("/list/read/id/"));
		this.listId = getIdFromURL(this.driver.getCurrentUrl());
		System.out.println("Step 2: List Added Successfully");

	}

	public boolean step3CreateListAgain() {
		openPage(TestConstants.BASE_URL + "/list");
		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By
						.id("listxistx_link_create")));

		WebElement addListButton = this.driver
				.findElement(By.id("listxistx_link_create"));
		addListButton.click();

		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By.id("name")));

		WebElement listName = this.driver.findElement(By.id("name"));
		listName.sendKeys(this.originalListName);

		WebElement button = this.driver.findElement(By.id("save_information"));
		button.click();

		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By.id("error_for_name")));

		WebElement errorMessage = this.driver.findElement(By.id("error_for_name"));
		if (errorMessage != null) {
			String style = errorMessage.getAttribute("style");
			if (style.length() == 0) {
				// This suggests the error message was displayed.
				System.out
				.println("Step 3: System Validated the error message correctly");
				return true;
			}
		}
		return false;

	}

	public void step4RenameList() {

		openPage(TestConstants.BASE_URL + "/list/edit/id/" + this.listId);

		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By.id("name")));

		WebElement listName = this.driver.findElement(By.id("name"));
		listName.clear();
		listName.sendKeys(RandomStringUtils.randomAlphabetic(10));

		WebElement button = this.driver.findElement(By.id("save_information"));
		button.click();
		System.out.println("Step 4: Renamed List Successfully");

	}

	public void step5CreateListAgain() {
		openPage(TestConstants.BASE_URL + "/list");
		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By
						.id("listxistx_link_create")));

		WebElement addListButton = this.driver
				.findElement(By.id("listxistx_link_create"));
		addListButton.click();

		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By.id("name")));

		WebElement listName = this.driver.findElement(By.id("name"));

		listName.sendKeys(this.originalListName);

		WebElement button = this.driver.findElement(By.id("save_information"));
		button.click();

		getWebDriverWait().until(ExpectedConditions.urlContains("/list/read/id/"));

		String currentURL = this.driver.getCurrentUrl();
		this.listId = getIdFromURL(currentURL);
		System.out.println("Step 5: Created A New List Again with same name");

	}

	public boolean step6And7CreatePospectAndAssignList() {
		openPage(TestConstants.BASE_URL + "/prospect");
		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By.id("pr_link_create")));

		WebElement addProspectsButton = this.driver.findElement(By.id("pr_link_create"));
		addProspectsButton.click();
		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By.id("email")));

		WebElement pemail = this.driver.findElement(By.id("email"));
		pemail.clear();
		pemail.sendKeys(RandomStringUtils.randomAlphanumeric(10) + "@"
				+ RandomStringUtils.randomAlphanumeric(10) + ".com");

		Select campaign = new Select(this.driver.findElement(By.id("campaign_id")));
		campaign.selectByVisibleText(campaign.getOptions().get(3).getText());

		Select dropdownProfile = new Select(this.driver.findElement(By.id("profile_id")));
		dropdownProfile
		.selectByVisibleText(dropdownProfile.getOptions().get(2).getText());

		WebElement openList = this.driver.findElement(By.id("toggle-inputs-lists-"));
		openList.click();
		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By
						.id("pr_fields_lists_wrapper_")));

		WebElement listDiv = this.driver.findElement(By.id("pr_fields_lists_wrapper_"));
		WebElement spanTag = listDiv.findElement(By.tagName("span"));
		spanTag.click();

		WebElement inputBox = listDiv.findElement(By.tagName("input"));
		inputBox.sendKeys(this.originalListName);
		inputBox.sendKeys(Keys.RETURN);

		WebElement createProspect = this.driver.findElement(By.name("commit"));
		createProspect.click();
		System.out
		.println("Step 6 & 7: Created a New Prospect and added the recent list");
		return true;

	}

	public boolean step8IsProspectSavedSuccessfully() {
		try {
			getWebDriverWait()
			.until(ExpectedConditions.urlContains("/prospect/read/id/"));
			System.out.println("Step 8: Prospect Saved Successfully");
			return true;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}
	}

	public void step9CreateTextOnlyEmail() throws Exception {
		openPage(TestConstants.BASE_URL + "/email/draft/edit");
		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By.id("name")));

		WebElement eName = this.driver.findElement(By.id("name"));
		eName.sendKeys(RandomStringUtils.randomAlphabetic(10));

		this.driver.findElement(By.className("asset-chooser")).click();
		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By.tagName("time")));
		// Thread.sleep(1000);

		List<WebElement> div = this.driver.findElements(By
				.xpath("//*[starts-with(@id,'ember')]"));

		div.get(0).findElement(By.tagName("div")).click();

		WebElement eChooseSelected = this.driver.findElement(By.id("select-asset"));
		eChooseSelected.click();

		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By
						.id("email_type_text_only")));

		WebElement eText = this.driver.findElement(By.id("email_type_text_only"));
		eText.click();

		WebElement eTemplate = this.driver.findElement(By.id("from_template"));
		eTemplate.click();

		WebElement emailSave = this.driver.findElement(By.id("save_information"));
		emailSave.click();

		getWebDriverWait().until(ExpectedConditions.urlContains("/email/draft/edit/id/"));

		String emailId = getIdFromURL(this.driver.getCurrentUrl());

		sendEmail(emailId);
		System.out.println("Step 9: Created the email and tried to send");

	}

	private void sendEmail(String emailId) {

		openPage(TestConstants.BASE_URL + "/email/draft/send/id/" + emailId);
		getWebDriverWait().until(
				ExpectedConditions.visibilityOfElementLocated(By
						.id("email-wizard-list-select")));

		WebElement listDiv = this.driver.findElement(By.id("email-wizard-list-select"));
		WebElement spanTag = listDiv.findElement(By.tagName("span"));
		spanTag.click();

		WebElement inputBox = listDiv.findElement(By.tagName("input"));
		inputBox.sendKeys(this.originalListName);
		inputBox.sendKeys(Keys.RETURN);

		Select sender = new Select(this.driver.findElement(By.name("a_sender[]")));
		sender.selectByVisibleText(sender.getOptions().get(3).getText());

		WebElement subjectLine = this.driver.findElement(By.id("subject_a"));
		subjectLine.sendKeys(RandomStringUtils.randomAlphabetic(10));

		this.driver.findElement(By.xpath("//a[text()='Send Now']")).click();

	}

	private String getIdFromURL(String URL) {

		String[] tokens = StringUtils.split(URL, "/");
		if ((tokens != null) && (tokens.length > 0)) {
			return tokens[tokens.length - 1];
		}
		return "";

	}

	/**
	 * @return the webDriverWait
	 */
	public WebDriverWait getWebDriverWait() {
		if (this.webDriverWait == null) {
			if (this.driver == null) {
				System.out
				.println("Cannot initialize webdriverwait without driver instance.");
				return null;
			}
			this.webDriverWait = new WebDriverWait(this.driver,
					TestConstants.TIMEOUT_FOR_WEBDRIVER_WAIT);
		}
		return this.webDriverWait;
	}

	/**
	 * @param webDriverWait the webDriverWait to set
	 */
	public void setWebDriverWait(WebDriverWait webDriverWait) {
		this.webDriverWait = webDriverWait;
	}

	public void step10Logout() {
		openPage(TestConstants.BASE_URL + "/user/logout");
		System.out.println("Step 10: Logout Successfully");

	}

}
