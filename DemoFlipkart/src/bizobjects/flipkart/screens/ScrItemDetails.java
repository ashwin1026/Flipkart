package bizobjects.flipkart.screens;

import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commonutils.CommonBusiness;

public class ScrItemDetails extends CommonBusiness {

	TreeMap<String, String> scrObjects = new TreeMap<String, String>();

	public ScrItemDetails(){

		scrObjects.put("AddToCart", "xpath://button[text()='ADD TO CART']" );

	}
	
	public void clickOnButton_Link(WebDriver driver, String objectName) {
		clickOnElement(driver, scrObjects, objectName);
		
	}
	
}