package bizobjects.flipkart.screens;

import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commonutils.CommonBusiness;

public class ScrCart extends CommonBusiness {

	TreeMap<String, String> scrObjects = new TreeMap<String, String>();

	public ScrCart(){

		scrObjects.put("OppoA3sRed16GB", 	"xpath://a[text()='OPPO A3s (Red, 16 GB)']");
		scrObjects.put("PlaceOrder", 		"xpath://span[text()='Place Order']");
	}

	public void clickOnButton_Link(WebDriver driver, String objectName) {
		clickOnElement(driver, scrObjects, objectName);

	}

	public boolean isElementVisible(WebDriver driver, String objectName) {
		return checkVisibilityOfElement(driver,scrObjects,objectName);
	}
	
}