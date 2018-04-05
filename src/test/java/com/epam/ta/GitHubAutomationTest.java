package com.epam.ta;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.ta.steps.Steps;

public class GitHubAutomationTest
{
	private Steps steps;
	private final String USERNAME = "testautomationuser";
	private final String PASSWORD = "Time4Death!";
	private final String CONFIRMATION_DELETE = "delete my account";
	private final String DELETE_ACCOUNT_MESSAGE = "Account successfully deleted.";
	private final String SUCCESS_CONFIRMATION = "Profile updated successfully";

	@BeforeMethod(description = "Init browser")
	public void setUp()
	{
		steps = new Steps();
		steps.initBrowser();
	}

	@Test
	public void oneCanCreateProject()
	{
		steps.loginGithub(USERNAME, PASSWORD);
		Assert.assertTrue(steps.createNewRepository("testRepo", "auto-generated test repo"));
		Assert.assertTrue(steps.currentRepositoryIsEmpty());
		// do not use lots of asserts
	}

	@Test(description = "Login to Github")
	public void oneCanLoginGithub()
	{
		steps.loginGithub(USERNAME, PASSWORD);
		Assert.assertTrue(steps.isLoggedIn(USERNAME));
	}
	@Test
	public void oneCanDeleteRepository(){
		steps.loginGithub(USERNAME, PASSWORD);
		steps.deleteRepository("TESTRepo");
	}

	@Test
	public void deleteAccount() {
		steps.loginGithub(USERNAME, PASSWORD);
		Assert.assertTrue(steps.deleteAccount(USERNAME, CONFIRMATION_DELETE).contains(DELETE_ACCOUNT_MESSAGE));
	}

	@Test
	public void enterProfileData() {
		steps.loginGithub(USERNAME, PASSWORD);
		steps.enterProfileData();
		Assert.assertTrue(steps.getSuccessEnterProfileData().contains(SUCCESS_CONFIRMATION));
	}

	@AfterMethod(description = "Stop Browser")
	public void stopBrowser()
	{
		steps.closeDriver();
	}
}
