package bizobjects.flipkart.screens;

import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commonutils.CommonBusiness;

public class ScrDeliveryAddress extends CommonBusiness {

	TreeMap<String, String> scrObjects = new TreeMap<String, String>();

	public ScrDeliveryAddress(){

		scrObjects.put("DeliverHere", "xpath://button[text()='Deliver Here']" );

	}
	
	public void clickOnButton_Link(WebDriver driver, String objectName) {
		clickOnElement(driver, scrObjects, objectName);
		
	}
	
}