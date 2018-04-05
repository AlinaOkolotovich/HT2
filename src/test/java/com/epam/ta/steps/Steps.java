package com.epam.ta.steps;

import java.util.concurrent.TimeUnit;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Steps
{
	private WebDriver driver;

	private final Logger logger = LogManager.getRootLogger();

	public void initBrowser()
	{
		driver = DriverSingleton.getDriver();
	}

	public void closeDriver()
	{
		DriverSingleton.closeDriver();
	}

	public void loginGithub(String username, String password)
	{
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage();
		loginPage.login(username, password);
	}

	public boolean isLoggedIn(String username)
	{
		LoginPage loginPage = new LoginPage(driver);
		String actualUsername = loginPage.getLoggedInUserName().trim().toLowerCase();
		logger.info("Actual username: " + actualUsername);
		return actualUsername.equals(username);
	}

	public boolean createNewRepository(String repositoryName, String repositoryDescription)
	{
		MainPage mainPage = new MainPage(driver);
		mainPage.clickOnCreateNewRepositoryButton();
		CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
		String expectedRepoName = createNewRepositoryPage.createNewRepository(repositoryName, repositoryDescription);
		return expectedRepoName.equals(createNewRepositoryPage.getCurrentRepositoryName());

	}

	public boolean currentRepositoryIsEmpty()
	{
		CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
		return createNewRepositoryPage.isCurrentRepositoryEmpty();
	}

	public void deleteRepository(String repoName){
		CreateNewRepositoryPage repositoryPage = new CreateNewRepositoryPage(driver);
		repositoryPage.createNewRepository("TEST", "DESC");
		driver.navigate().to(repositoryPage.getCurrentRepositoryUrl());
		repositoryPage.tapOnSettingsRepoLink();
		repositoryPage.tapOnDeleteRepoButton();
		repositoryPage.enterConfirmationToDelete();
		repositoryPage.submitDelete();
	}
	public String deleteAccount(String userName, String confirmationPhrase) {
		AccountPage accountPage = new AccountPage(driver);
		accountPage.openPage();
		accountPage.deleteAccount(userName, confirmationPhrase);
		String message = driver.findElement(By.tagName("body")).getText();
		return message;
	}
	public void enterProfileData() {
		UserProfilePage publicProfilePage = new UserProfilePage(driver);
		publicProfilePage.openPage();
		publicProfilePage.inputProfileData();
	}
	public String getSuccessEnterProfileData() {
		UserProfilePage publicProfilePage = new UserProfilePage(driver);
		String message = publicProfilePage.getSuccessMessage();
		return message;
	}
}
