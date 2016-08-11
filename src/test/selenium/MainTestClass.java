package test.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainTestClass {

	public static void main(String[] args) {
		WebDriver driver = getChromeWebDriver();
		try {
			TestPardot testPardot = new TestPardot(driver);

			testPardot.step1Login();
			boolean loginSuccess = testPardot.isLoginSuccess();
			if (!loginSuccess) {
				System.out.println("Could not login to the portal");
				// testPardot.closeAndQuit();
				return;
			}

			testPardot.step2CreateList();

			testPardot.step3CreateListAgain();

			testPardot.step4RenameList();

			testPardot.step5CreateListAgain();

			testPardot.step6And7CreatePospectAndAssignList();

			testPardot.step8IsProspectSavedSuccessfully();

			testPardot.step9CreateTextOnlyEmail();

			testPardot.step10Logout();

			closeAndQuit(driver);

		}
		catch (Exception ex) {
			System.out.println("Exception Occured");
			ex.printStackTrace();

		}
	}

	private static WebDriver getChromeWebDriver() {
		System.setProperty("webdriver.chrome.driver", TestConstants.DRIVER_PATH);
		WebDriver driver = new ChromeDriver();
		return driver;
	}

	private static void closeAndQuit(WebDriver driver) {
		driver.close();
		driver.quit();
		System.out.println("End of testing");

	}

}
