package commonutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;


public class CommonBusiness {

	public WebDriver driver = null;
	public Properties prop = null;
	public	final 	String		downloadFilepath = System.getProperty("user.dir") + "\\logs\\downloads\\";
	public String parentWindow=null;

	public WebDriver launchBrowser(String browser, String url) {

		String 		methodName	= getCallingMethod(0);
		logStartMethod(methodName);
		String projectLoc =System.getProperty("user.dir"); 
		try {

			switch (browser.toUpperCase()) {

			case "CHROME":

				System.setProperty("webdriver.chrome.driver", projectLoc + "\\lib\\chromedriver.exe");

				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("download.default_directory", downloadFilepath);					// sets the default download directory
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("profile.default_content_setting_values.automatic_downloads", 1);	// disables prompt to allow multiple downloads

				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.setExperimentalOption("prefs", chromePrefs);
				chromeOptions.setAcceptInsecureCerts(true);

				driver = new ChromeDriver(chromeOptions);			
				driver.get(url);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				parentWindow=driver.getWindowHandle();
				break;

			} 
		}catch (Exception e) {
			Reporter.log("From launchBrowser - Sorry, Because I could not launch the browser, I cannot continue.");
			e.printStackTrace();
		}
		return driver;		

	}

	public Properties readDataFile(String location) throws Exception{

		FileInputStream fis = null;
		String[][] data = null;

		try {
			fis = new FileInputStream(location);
			prop = new Properties();
			prop.load(fis);
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			fis.close();
		}
		return prop;
	}	


	public void logStartMethod(String methodName) {
		Reporter.log("Executing method "+ methodName);
	}

	public void logEndMethod(String methodName) {
		Reporter.log("Completing execution of method "+ methodName);
	}

	public static String getCallingMethod(int level) {
		return getStackMethods(Thread.currentThread().getStackTrace(), 2 + level);
	}

	private static String getStackMethods(StackTraceElement methodNames[], int level) {

		if (methodNames != null && methodNames.length >= level) {
			StackTraceElement targeMethodName = methodNames[level];
			if (targeMethodName != null) {
				return targeMethodName.getMethodName();
			}
		}

		return null;
	}

	public void clickOnElement(WebDriver driver, TreeMap<String, String> scrObjects, String objectName) {

		String 		methodName	= getCallingMethod(0);
		WebElement	webel		= null;
		Actions		actions		= new Actions(driver);
		WebDriverWait elementWait = new WebDriverWait(driver, 20, 500);

		logStartMethod(methodName);

		String recogStrategy = scrObjects.get(objectName);
		String[] stratInfo = new String[2];
		stratInfo[0] = recogStrategy.substring(0, recogStrategy.indexOf(":"));
		stratInfo[1] = recogStrategy.substring(recogStrategy.indexOf(":") + 1, recogStrategy.length());

		try {
			switch (stratInfo[0]) {
			case "id": {
				webel	= driver.findElement(By.id(stratInfo[1]));
			}
			break;

			case "xpath": {
				webel	= driver.findElement(By.xpath(stratInfo[1]));
			}
			break;

			case "name": {
				webel	= driver.findElement(By.name(stratInfo[1]));
			}
			break;

			case "text": {
				webel	= driver.findElement(By.linkText(stratInfo[1]));
			}
			break;

			case "css": {
				webel	= driver.findElement(By.cssSelector(stratInfo[1]));
			}
			break;

			}

			actions.moveToElement(webel).build().perform();
			webel.click();
		}
		catch (Exception e) {
			Reporter.log("From (" + methodName + ") Sorry, Because I received an exception while trying to click on element '" + objectName + "', I cannot continue.<br>"
					+ "The system returned: " + e.toString(), true);
			Assert.fail("Element cannot be identified");
		}

		logEndMethod(methodName);
	}

	public boolean checkVisibilityOfElement(WebDriver driver,TreeMap<String, String> scrObjects,String objectName) {

		String 		methodName	= getCallingMethod(0);
		WebElement	webel		= null;
		Actions		actions		= new Actions(driver);
		WebDriverWait elementWait = new WebDriverWait(driver, 20, 500);
		boolean visibility = false;
		logStartMethod(methodName);

		String recogStrategy = scrObjects.get(objectName);
		String[] stratInfo = new String[2];
		stratInfo[0] = recogStrategy.substring(0, recogStrategy.indexOf(":"));
		stratInfo[1] = recogStrategy.substring(recogStrategy.indexOf(":") + 1, recogStrategy.length());

		try {
			switch (stratInfo[0]) {
			case "id": {
				webel	= driver.findElement(By.id(stratInfo[1]));

			}
			break;

			case "xpath": {
				webel	= driver.findElement(By.xpath(stratInfo[1]));
			}
			break;

			case "name": {
				visibility	= driver.findElement(By.name(stratInfo[1])).isDisplayed();
			}
			break;

			case "text": {
				visibility	= driver.findElement(By.linkText(stratInfo[1])).isDisplayed();
			}
			break;

			case "css": {
				visibility	= driver.findElement(By.cssSelector(stratInfo[1])).isDisplayed();
			}
			break;

			}

		}
		catch (Exception e) {
			//Reporter.log("From (" + methodName + ") Sorry, Because I received an exception while trying to click on element '" + objectName + "', I cannot continue.<br>"
			//		+ "The system returned: " + e.toString(), true);
		}
		if(webel!=null) {
			logEndMethod(methodName);

			return true;
		}

		logEndMethod(methodName);
		return false;

	}

	public void pageScrollDown(WebDriver driver, WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);

		js.executeScript("window.scrollBy(0,-100)", "");
		/*
		Actions act = new Actions(driver);
		act.moveToElement(element).build().perform();*/
	}

	public void switchToChildWindow(WebDriver driver) {

		Set<String> windowHandles = driver.getWindowHandles();

		Iterator<String> itr = windowHandles.iterator();
		parentWindow = driver.getWindowHandle();

		while(itr.hasNext()) {
			String child_window=itr.next();

			if(!parentWindow.equals(child_window))
			{
				driver.switchTo().window(child_window);
			}
		}
	}

	public void switchToParentWindow(WebDriver driver) {

		driver.switchTo().window(parentWindow);

	}

	public void fillScreen(WebDriver driver, TreeMap<String, String> scrObjects, String objectName, String testData)  {

		String 		methodName	= getCallingMethod(0);
		WebElement	webel		= null;
		Actions		actions		= new Actions(driver);
		WebDriverWait elementWait = new WebDriverWait(driver, 20, 500);

		logStartMethod(methodName);

		String recogStrategy = scrObjects.get(objectName);
		String[] stratInfo = new String[2];
		stratInfo[0] = recogStrategy.substring(0, recogStrategy.indexOf(":"));
		stratInfo[1] = recogStrategy.substring(recogStrategy.indexOf(":") + 1, recogStrategy.length());

		try {
			switch (stratInfo[0]) {
			case "id": {
				webel	= driver.findElement(By.id(stratInfo[1]));
			}
			break;

			case "xpath": {
				webel	= driver.findElement(By.xpath(stratInfo[1]));
			}
			break;

			case "name": {
				webel	= driver.findElement(By.name(stratInfo[1]));
			}
			break;

			case "text": {
				webel	= driver.findElement(By.linkText(stratInfo[1]));
			}
			break;

			case "css": {
				webel	= driver.findElement(By.cssSelector(stratInfo[1]));
			}
			break;

			}

			String objectType = webel.getAttribute("type");

			if(objectType.equalsIgnoreCase("text")||objectType.equalsIgnoreCase("textArea")||objectType.equalsIgnoreCase("password")) {
				webel.sendKeys(testData);
			}
		}
		catch (Exception e) {
			Reporter.log("From (" + methodName + ") Sorry, Because I received an exception while trying to click on element '" + objectName + "', I cannot continue.<br>"
					+ "The system returned: " + e.toString(), true);
		}

		logEndMethod(methodName);

	}

	public void takeScreenshot(WebDriver driver) {

		String projectLoc = System.getProperty("user.dir"); 

		try {
			TakesScreenshot scrShot =((TakesScreenshot)driver);

			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile=new File(projectLoc + "\\logs\\screenshots\\lastscreen.png");
			FileUtils.copyFile(SrcFile, DestFile);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}