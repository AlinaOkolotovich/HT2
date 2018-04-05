package com.epam.ta.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.ta.utils.Utils;

public class CreateNewRepositoryPage extends AbstractPage
{
	private final String BASE_URL = "http://www.github.com/new";
	private final String GIT_HUB_URL = "http://www.github.com/";

	private final Logger logger = LogManager.getRootLogger();
	private String relativeUrlToRepo = "";

	@FindBy(xpath = "//input[@id='repository_name']")
	private WebElement inputRepositoryName;

	@FindBy(id = "repository_description")
	private WebElement inputRepositoryDescription;

	@FindBy(xpath = "//form[@id='new_repository']//button[@type='submit']")
	private WebElement buttonCreate;

	@FindBy(id = "empty-setup-clone-url")
	private WebElement labelEmptyRepoSetupOption;

	@FindBy(xpath = "//a[@data-pjax='#js-repo-pjax-container']")
	private WebElement linkCurrentRepository;

	@FindBy(xpath = "//*[@class=\"octicon octicon-gear\"]")
	private  WebElement settingsRepoLink;

	@FindBy(xpath = "//button[contains(text(),'Delete this repository')]")
	private WebElement deleteRepoButton;

	@FindBy(xpath = "(//input[@class= \"form-control input-block\"])[2]")
	private WebElement deleteConfirmationInput;

	@FindBy(xpath = "//button[contains(text(),'I understand the consequences, delete this repository')]")
	private WebElement deleteSubmitButton;

	public CreateNewRepositoryPage(WebDriver driver)
	{
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	public boolean isCurrentRepositoryEmpty()
	{
		return labelEmptyRepoSetupOption.isDisplayed();
	}

	public String createNewRepository(String repositoryName, String repositoryDescription)
	{
		String repositoryFullName = repositoryName + Utils.getRandomString(6);
		inputRepositoryName.sendKeys(repositoryFullName);
		inputRepositoryDescription.sendKeys(repositoryDescription);
		buttonCreate.click();
		return repositoryFullName;
	}

	public String getCurrentRepositoryName()
	{
		return linkCurrentRepository.getText();
	}

	public String getCurrentRepositoryUrl() {
		LoginPage login = new LoginPage(driver);
		CreateNewRepositoryPage repo = new CreateNewRepositoryPage(driver);
		String repositoryUrl = GIT_HUB_URL + login.getLoggedInUserName() + repo.getCurrentRepositoryName();
		return repositoryUrl;
	}

	public void tapOnSettingsRepoLink(){
		CreateNewRepositoryPage repo = new CreateNewRepositoryPage(driver);
		settingsRepoLink.click();
	}

	public void tapOnDeleteRepoButton(){
		CreateNewRepositoryPage repo = new CreateNewRepositoryPage(driver);
		deleteRepoButton.click();
	}

	public void enterConfirmationToDelete(){
		CreateNewRepositoryPage repo = new CreateNewRepositoryPage(driver);
		deleteConfirmationInput.sendKeys(repo.getCurrentRepositoryName());
	}

	public void submitDelete(){
		CreateNewRepositoryPage repo = new CreateNewRepositoryPage(driver);
		deleteSubmitButton.click();
	}

	public boolean repositoryNotExist(String repoUrl){
		driver.navigate().to(repoUrl);
		boolean repoIsDeleted = false;
		if(driver.findElement(By.xpath("//img[@alt=\"404 “This is not the web page you are looking for”\"]"))!=null){
			repoIsDeleted = true;
		} else {
			repoIsDeleted = false;
		}
		return repoIsDeleted;

	}


	@Override
	public void openPage()
	{
		driver.navigate().to(BASE_URL);
	}

}
